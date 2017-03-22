package baseline;

import java.io.FileReader;
import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;

public class NaiveBayesBaseline {
	// "C:\Users\Mishel\workspace\Practica4SAD\datosConvertidosAStringToWord\smsTrain_filtroStringToWord_filtroStringToWord.arff"
	// "C:\Users\Mishel\workspace\Practica4SAD\datosConvertidosAStringToWord\smsDev_filtroStringToWord_filtroStringToWord.arff"
	public static void main(String[] args) throws Exception {
		// comprueba que se reciben dos archivos, el primero train y el segundo
		// dev
		System.out.println(args[0].toString());
		System.out.println(args[1].toString());
		System.out.println(args[2].toString());
		if (args.length == 3 && args[0].contains("Train") && args[1].contains("Dev")) {
			Instances train = new Instances(new FileReader(args[0]));
			train.setClassIndex(train.attribute("clase").index());
			System.err.println(">>Datos train cargados.");
			Instances dev = new Instances(new FileReader(args[1]));
			dev.setClassIndex(dev.attribute("clase").index());
			System.err.println(">>Datos dev cargados.");

			int indiceClase = 1; // class {ham,spam}, interesa spam para
									// fmeasure

			// Cargar modelo de NaiveBayes
			NaiveBayes miNaiveBayes = (NaiveBayes) weka.core.SerializationHelper.read(args[2]);
			System.err.println(">>NaiveBayes cargado.");

			System.err.println("----Evaluación no honesta (train vs train)----");
			long inicio = System.currentTimeMillis();
			Evaluation evaluator = new Evaluation(train);
			evaluator.evaluateModel(miNaiveBayes, train);
			System.out.println("noHonesta;" + evaluator.precision(indiceClase) + ";" + evaluator.recall(indiceClase)
					+ ";" + evaluator.fMeasure(indiceClase) + ";"
					+ (double) (System.currentTimeMillis() - inicio) / 1000);

			System.err.println("----Evaluacion HoldOut (train vs dev)----");
			inicio = System.currentTimeMillis();
			evaluator = new Evaluation(train);
			evaluator.evaluateModel(miNaiveBayes, dev);
			System.out.println("holdOut;" + evaluator.precision(indiceClase) + ";" + evaluator.recall(indiceClase) + ";"
					+ evaluator.fMeasure(indiceClase) + ";" + (double) (System.currentTimeMillis() - inicio) / 1000);

			System.err.println("----Evaluación 10-Fold----");
			inicio = System.currentTimeMillis();
			evaluator = new Evaluation(train);
			evaluator.crossValidateModel(miNaiveBayes, train, 10, new Random(1));
			System.out.println("10fold;" + evaluator.precision(indiceClase) + ";" + evaluator.recall(indiceClase) + ";"
					+ evaluator.fMeasure(indiceClase) + ";" + (double) (System.currentTimeMillis() - inicio) / 1000);
		} else {
			System.out.println("Necesarios archivos train, dev y modelo NaiveBayes. (en ese orden)");
		}
	}

}
