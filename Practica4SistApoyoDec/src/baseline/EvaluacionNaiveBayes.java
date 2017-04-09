package baseline;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;

public class EvaluacionNaiveBayes {
public static void main(String[] args) throws Exception {
	
	if(args.length==3&&args[0].contains("train")&&args[1].contains("dev")){
		Instances train = new Instances(new FileReader(args[0]));
		train.setClassIndex(train.attribute("class").index());
		System.err.println(">>Datos train cargados.");
		Instances dev = new Instances(new FileReader(args[1]));
		dev.setClassIndex(dev.attribute("class").index());
		System.err.println(">>Datos dev cargados.");
		
		int indiceClase=0; //class {ham,spam}, interesa spam para fmeasure
		
		//Cargar modelo de NaiveBayes
		NaiveBayes miNaiveBayes = (NaiveBayes) weka.core.SerializationHelper.read(args[2]);
		System.err.println(">>NaiveBayes cargado.");
					
		
		System.err.println("----Evaluacion HoldOut (train vs dev)----");
		long inicio = System.currentTimeMillis();
		Evaluation evaluator= new Evaluation(train);
		evaluator.evaluateModel(miNaiveBayes, dev);
		System.out.println("holdOut;"+evaluator.precision(indiceClase)+";"+evaluator.recall(indiceClase)+";"+evaluator.fMeasure(indiceClase)+";weight" + evaluator.weightedFMeasure()+";"+ evaluator.weightedPrecision()+";"+evaluator.weightedRecall()+(double)(System.currentTimeMillis()-inicio)/1000);
		
		System.err.println("----Evaluación 10-Fold----");
		inicio = System.currentTimeMillis();
		evaluator= new Evaluation(train);
		evaluator.crossValidateModel(miNaiveBayes, train, 10, new Random(1));
		System.out.println("\n10fold;"+evaluator.precision(indiceClase)+";"+evaluator.recall(indiceClase)+";"+evaluator.fMeasure(indiceClase)+";"+(double)(System.currentTimeMillis()-inicio)/1000);
	}else{
		System.out.println("Necesarios archivos train, dev y modelo NaiveBayes. (en ese orden)");
	}
}
}
