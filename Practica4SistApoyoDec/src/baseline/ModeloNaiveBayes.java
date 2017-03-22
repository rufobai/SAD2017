package baseline;

import java.io.FileReader;

import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;
import weka.core.SerializationHelper;

public class ModeloNaiveBayes {
	/**
	 * 
	 * @param el archivo .arff con el que se creara el modelo
	 * @throws Exception
	 */
	// "C:\Users\Mishel\workspace\Practica4SAD\datosConvertidosAStringToWord\smsTrain_filtroStringToWord_filtroStringToWord.arff"
	public static void main(String[] args) throws Exception {
		// comprueba que se recibe un parametro
		if (args.length == 1) {
			// Se cargan las instancias
			Instances trainSet = new Instances(new FileReader(args[0]));
			trainSet.setClassIndex(trainSet.numAttributes()-1);
			// Se crea NaiveBayes con valores por defecto Weka
			NaiveBayes naiveBayes = new NaiveBayes();
			naiveBayes.buildClassifier(trainSet);
			System.out.println("NaiveBayes creado.");
			SerializationHelper.write("miModelo.model", naiveBayes);
			System.out.println("NaiveBayes guardado.");
		} else {
			System.out.println("Necesario arff con instancias de entrenamiento.");
		}
	}
}
