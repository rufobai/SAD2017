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

public class StringToWord {
	/**
	 * 
	 * @param conjunto
	 *            train, conjunto dev, conjunto test en ese orden
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		if (args.length == 2) {
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
			filter.setAttributeNamePrefix("stwAttribute_");
			filter.setMinTermFreq(1);
			filter.setOutputWordCounts(true);
			filter.setPeriodicPruning(-1.0);
			filter.setUseStoplist(false);
			filter.setWordsToKeep(2000);
			filter.setAttributeIndices("1");

			// Creamos nuevas instancias aplicandoles el filtro
			Instances newTrain = Filter.useFilter(instances, filter);

			// Se guardan en los mismos ficheros desde donde se hizo la llamada
			ArffSaver saver = new ArffSaver();
			
			// Para el train
			saver.setInstances(newTrain);
			saver.setFile(new File(args[1].toString()));
			saver.writeBatch();
			System.out.println("Se ha creado el archivo en " + args[1].toString());
			
		} else {
			System.out.println("Has instroducido "+args.length+ " paramatros. Debes introducir dos parametros, el 1º correspondiente al archivo"+
		" al que se le debe aplicar el filtro y 2º la ruta donde se debe guardar el nuevo");
		}
	}
}
