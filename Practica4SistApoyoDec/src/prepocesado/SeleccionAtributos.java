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
		Instances instances = new Instances(new FileReader(args[0]));
		// Aplicamos el filtro StringToWordVector a las instancias de
		// acuerdo a las caracteristicas mencionadas en el PDF de la practica
		StringToWordVector filter = new StringToWordVector();
		filter.setInputFormat(instances);
		filter.setLowerCaseTokens(true);
		filter.setTFTransform(false);
		filter.setIDFTransform(false);
		filter.setDoNotOperateOnPerClassBasis(false);
		filter.setInvertSelection(false);
		filter.setAttributeIndices("" + (instances.attribute("class").index()));
		filter.setAttributeNamePrefix("infoGain_");
		filter.setMinTermFreq(1);
		filter.setOutputWordCounts(true);
		filter.setPeriodicPruning(-1.0);
		filter.setUseStoplist(false);
		filter.setWordsToKeep(2000);
		filter.setAttributeIndices("1");

		// Creamos nuevas instancias aplicandoles el filtro
		Instances newTrain = Filter.useFilter(instances, filter);

		// Se procede al filtrado de instancias para eliminar las "redudantes"
		AttributeSelection attributeSelection = new AttributeSelection();
		instances.setClassIndex(instances.attribute("clase").index());
		attributeSelection.setInputFormat(instances);

		// Se usa el InfoGain como attribute evaluator
		InfoGainAttributeEval infoGain = new InfoGainAttributeEval();
		attributeSelection.setEvaluator(infoGain);

		// Como metodo para buscar el subconjunto de atributos

		Ranker ranker = new Ranker();
		ranker.setGenerateRanking(true);
		//Se selecciona 0.01 para filtrar despues de comprobrar que es un 
		//valor que elimina los atributos que no aportan informacion valiosa 
		ranker.setThreshold(0.01);
		attributeSelection.setSearch(ranker);

		// Reescribimos las instancias aplicandole el InfoGain y el Ranker
		instances = Filter.useFilter(instances, attributeSelection);
		System.out.println("instancias despues del filtrado"+ instances.numInstances());
		System.out.println("Filtro InfoGain aplicado.");

		// Se guardan en los mismos ficheros desde donde se hizo la llamada
		ArffSaver saver = new ArffSaver();
		// Para el train
		saver.setInstances(instances);
		saver.setFile(new File(args[1].toString()));
		saver.writeBatch();
		System.out.println("Se le ha aplicado al archivo .arff el infoGain");
		
	}
}
