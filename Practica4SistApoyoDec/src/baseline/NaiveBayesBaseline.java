package baseline;

import java.io.FileReader;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;
import weka.core.SerializationHelper;

public class NaiveBayesBaseline {

	public static void main(String[] args) throws Exception {
		if (args.length == 2 && args[0].contains(".arff") && args[1].contains(".arff")) {
			// Se cargan las instancias
			Instances instancias = new Instances(new FileReader(args[0]));
			instancias.setClassIndex(instancias.attribute("class").index());
			// Cargamos el modelo que habremos creado con anterioridad
			NaiveBayes nb = new NaiveBayes();
			Evaluation eval = new Evaluation(instancias);
			//Se guardara en el array de opciones la ruta donde esta 
			//las instancias de entrenamiento y las de test
			String[] options = weka.core.Utils.splitOptions("-t "+ args[0] + " -T " + args [1]);
			eval.evaluateModel(nb, options);
			System.out.println(eval.evaluateModel(nb, options));
			System.out.println(eval.fMeasure(1));
		} else {
			System.out.println("Necesario arff con instancias de entrenamiento y en segundo lugar las que "
					+ "hay que testear");
		}
	}
}
