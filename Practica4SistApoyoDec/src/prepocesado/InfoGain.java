package prepocesado;

import java.io.File;
import java.io.FileReader;

import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.filters.Filter;
import weka.filters.supervised.attribute.AttributeSelection;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class InfoGain {
	public static void main(String[] args) throws Exception {
		if (args[0].contains("train") && args[1].contains("dev") && args[2].contains("test")) {
			// Carga archivos
			Instances train = new Instances(new FileReader(args[0]));
			System.out.println(">>Datos train cargados.");
			Instances dev = new Instances(new FileReader(args[1]));
			System.out.println(">>Datos dev cargados.");
			Instances test = new Instances(new FileReader(args[2]));
			System.out.println(">>Datos test cargados.");

			// filtro BOW (outPutWordsCounts, lowerCase, rainbow)
			StringToWordVector filterWordVector = new StringToWordVector();
			filterWordVector.setInputFormat(train);
			filterWordVector.setAttributeIndices("" + (train.attribute("class").index()));
			filterWordVector.setAttributeNamePrefix("sms_");
			filterWordVector.setTFTransform(false);
			filterWordVector.setIDFTransform(false);
			filterWordVector.setOutputWordCounts(true);
			filterWordVector.setWordsToKeep(2000);
			filterWordVector.setLowerCaseTokens(true);
			// Batch Filtering
			Instances newTrain = Filter.useFilter(train, filterWordVector);
			Instances newDev = Filter.useFilter(dev, filterWordVector);
			Instances newTest = Filter.useFilter(test, filterWordVector);
			System.out.println("Filtro BOW aplicado.");

			// filtro FSS-Infogain
			AttributeSelection atributeSelection = new AttributeSelection();
			newTrain.setClassIndex(newTrain.attribute("class").index());
			atributeSelection.setInputFormat(newTrain);
			// InfoGain (valores por defecto de Weka)
			InfoGainAttributeEval infoGain = new InfoGainAttributeEval();
			infoGain.setBinarizeNumericAttributes(false);
			infoGain.setMissingMerge(true);
			atributeSelection.setEvaluator(infoGain);
			// Ranker
			Ranker ranker = new Ranker();
			ranker.setGenerateRanking(true);
			ranker.setThreshold(0.01);
			atributeSelection.setSearch(ranker);
			// Batch Filtering
			newTrain = Filter.useFilter(newTrain, atributeSelection);
			newDev = Filter.useFilter(newDev, atributeSelection);
			newTest = Filter.useFilter(newTest, atributeSelection);
			System.out.println("Filtro InfoGain aplicado.");

			// guarda los nuevo arff (en mismo path que los arff de entrada)
			ArffSaver newArff = new ArffSaver();
			newArff.setInstances(newTrain);
			newArff.setFile(new File(args[0].toString().replace(".arff", "_BowInfoGainBatch.arff")));
			newArff.writeBatch();
			System.out.println(">>Datos train guardados.");
			newArff.setInstances(newDev);
			newArff.setFile(new File(args[1].toString().replace(".arff", "_BowInfoGainBatch.arff")));
			newArff.writeBatch();
			System.out.println(">>Datos dev guardados.");
			newArff.setInstances(newTest);
			newArff.setFile(new File(args[2].toString().replace(".arff", "_BowInfoGainBatch.arff")));
			newArff.writeBatch();
			System.out.println(">>Datos test guardados.");
		} else {
			System.out.println("Necesarios 3 archivos ARFF: train, dev, test (en ese orden)");
			System.out.println("Comando esperado: 'java -jar BowInfoGainBatch.jar train.arff dev.arff test.arff'");
		}
	}
}
