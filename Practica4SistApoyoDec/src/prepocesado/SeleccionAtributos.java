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

public class SeleccionAtributos {
	public static void main(String[] args) throws Exception {
		// Se realiza la carga de instancias
		Instances instancesTrain = new Instances(new FileReader(args[0]));
		Instances instancesDev = new Instances(new FileReader(args[1]));
		Instances instancesTest = new Instances(new FileReader(args[2]));
		
		// Se procede al filtrado de instancias para eliminar las "redudantes"
		AttributeSelection attributeSelection = new AttributeSelection();
		instancesTrain.setClassIndex(instancesTrain.attribute("clase").index());
		attributeSelection.setInputFormat(instancesTrain);
		
		// Se usa el InfoGain como attribute evaluator
		InfoGainAttributeEval infoGain = new InfoGainAttributeEval();
		attributeSelection.setEvaluator(infoGain);
		
		// Como metodo para buscar el subconjunto de atributos
		
		Ranker ranker= new Ranker();
		ranker.setGenerateRanking(true);
		ranker.setNumToSelect(1500);
		attributeSelection.setSearch(ranker);
		
		//Reescribimos las instancias aplicandole el InfoGain y el Ranker
		instancesTrain = Filter.useFilter(instancesTrain, attributeSelection);	
		instancesDev = Filter.useFilter(instancesDev, attributeSelection);
		instancesTest = Filter.useFilter(instancesTest, attributeSelection);
		System.out.println("Filtro InfoGain aplicado.");
		
		// Se guardan en los mismos ficheros desde donde se hizo la llamada
		ArffSaver saver = new ArffSaver();
		// Para el train
		saver.setInstances(instancesTrain);
		saver.setFile(new File(args[0].toString().replace(".arff", "_filtroStringToWord.arff")));
		saver.writeBatch();
		System.out.println("Se ha almacenado el conjunto train con las nuevas modificaciones");
		// Para el dev
		saver.setInstances(instancesDev);
		saver.setFile(new File(args[1].toString().replace(".arff", "_filtroStringToWord.arff")));
		saver.writeBatch();
		System.out.println("Se ha almacenado el conjunto dev con las nuevas modificaciones");

		// Para el test
		saver.setInstances(instancesTest);
		saver.setFile(new File(args[2].toString().replace(".arff", "_filtroStringToWord.arff")));
		saver.writeBatch();
		System.out.println("Se ha almacenado el conjunto test con las nuevas modificaciones");
	}
}
