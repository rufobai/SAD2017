package prepocesado;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.filters.Filter;
//import weka.core.stopwords.Rainbow;
import weka.filters.*;
import weka.filters.unsupervised.attribute.StringToWordVector;
import weka.filters.unsupervised.instance.SparseToNonSparse;

public class TfIdf {
	
	public TfIdf(){
		
	}
	
	public void aplicarTfIdf(String rutaTrain, String rutaDev, String rutaTest) throws Exception{
		
		if(rutaTrain.contains("train") && rutaDev.contains("dev") && rutaTest.contains("test")){
			//java -cp weka.jar weka.filters.unsupervised.attribute.StringToWordVector -b -i SMS_SpamCollection.train.arff -o t.arff -r SMS_SpamCollection.dev.arff -s te.arff -R 2 -P "sms_"
			//carga instancias
			Instances train = new Instances(new FileReader(rutaTrain));
			System.out.println(">>Datos train cargados.");
			Instances dev = new Instances(new FileReader(rutaDev));
			System.out.println(">>Datos dev cargados.");
			Instances test = new Instances(new FileReader(rutaTest));
			System.out.println(">>Datos test cargados.");
			//filtro StringToWordVector (TF-IDF, outPutWordsCounts, lowerCase, rainbow)
			StringToWordVector filterWordVector = new StringToWordVector();
			filterWordVector.setInputFormat(train);
			filterWordVector.setAttributeIndices(""+(train.attribute("text").index()+1));
			filterWordVector.setAttributeNamePrefix("sms_");
			filterWordVector.setTFTransform(true);
			filterWordVector.setIDFTransform(true);
			filterWordVector.setOutputWordCounts(true);
			filterWordVector.setWordsToKeep(2000);
			filterWordVector.setLowerCaseTokens(true);
			//filterWordVector.setStopwordsHandler(new Rainbow());
			//Batch Filtering
			Instances newTrain = Filter.useFilter(train, filterWordVector);
			Instances newDev = Filter.useFilter(dev, filterWordVector);
			Instances newTest = Filter.useFilter(test, filterWordVector);
			String nameFile = "_BowTfIdfBatch.arff";
			//comprueba modo toNonSparse
			
			/*
			if(args.length==4&&args[3].compareTo("-n")==0){
				System.out.println("Modo NonSparse.");
				SparseToNonSparse sparse= new SparseToNonSparse();
				sparse.setInputFormat(newTrain);
				newTrain = Filter.useFilter(newTrain, sparse);
				newDev = Filter.useFilter(newDev, sparse);
				newTest = Filter.useFilter(newTest, sparse);
				nameFile = "_BowTfIdfBatchNonSparse.arff";
			}else{
				System.out.println("Modo Sparse.");
			}*/
			//guarda los nuevos arff (en mismo path que los arff de entrada)
			ArffSaver newArff = new ArffSaver();
			newArff.setInstances(newTrain);
			newArff.setFile(new File(rutaTrain.toString().replace(".arff",nameFile)));
			newArff.writeBatch();
			System.out.println(">>Datos train guardados.");
			newArff.setInstances(newDev);
			newArff.setFile(new File(rutaDev.toString().replace(".arff",nameFile)));
			newArff.writeBatch();
			System.out.println(">>Datos dev guardados.");
			newArff.setInstances(newTest);
			newArff.setFile(new File(rutaTest.toString().replace(".arff",nameFile)));
			newArff.writeBatch();
			System.out.println(">>Datos test guardados.");
		}else{
			System.out.println("Necesarios 3 archivos ARFF: train, dev, test (en ese orden)");
			System.out.println("Comando esperado: 'java -jar TfIdfBatch.jar train.arff dev.arff test.arff'");
			System.out.println("Para convertir en NonSparse: 'java -jar TfIdfBatch.jar train.arff dev.arff test.arff -n'");
		}
		
		
	}

}
