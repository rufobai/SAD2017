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
	public static void main(String[] args) throws Exception {
		// comprueba que se recibe un parametro
		if (args.length == 2 && args[0].contains(".arff") && args[1].contains(".model")) {
			// Se cargan las instancias
			Instances trainSet = new Instances(new FileReader(args[0]));
			trainSet.setClassIndex(trainSet.attribute("class").index());
			NaiveBayes naiveBayes = new NaiveBayes();
			naiveBayes.buildClassifier(trainSet);
			SerializationHelper.write(args[1], naiveBayes);
			System.out.println("Se ha creado el modelo en "+ args[1]);
		} else {
			System.out.println("Necesario arff con instancias de entrenamiento "
					+ "y la ruta donde se guardará el model. Es decir, '.arff' y '.model' en ese orden.");
		}
	}
}
