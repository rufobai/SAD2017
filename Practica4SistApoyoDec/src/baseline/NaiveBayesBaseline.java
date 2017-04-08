package baseline;

import java.io.FileReader;
import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;

public class NaiveBayesBaseline {

	public static void main(String[] args) throws Exception {
		//comprueba que se reciben dos archivos, el primero train, el segundo dev por ultimo el .model
		if(args.length==3&&args[0].contains("train")&&args[1].contains("dev")){
			//Se cargan las instancias train
			Instances train = new Instances(new FileReader(args[0]));
			train.setClassIndex(train.attribute("class").index());
			//Se cargan las instancias dev
			Instances dev = new Instances(new FileReader(args[1]));
			dev.setClassIndex(dev.attribute("class").index());			
			int indiceClase=1; //class {ham,spam} se usara spam
			//Cargar modelo de NaiveBayes
			NaiveBayes miNaiveBayes = (NaiveBayes) weka.core.SerializationHelper.read(args[2]);
			//Se usara la evaluacion 10 fold para evaluar el modelo
			System.err.println("Evaluacion 10-Fold");
			Evaluation evaluator = new Evaluation(train);
			long inicio = System.currentTimeMillis();
			inicio = System.currentTimeMillis();
			evaluator= new Evaluation(train);
			evaluator.crossValidateModel(miNaiveBayes, train, 10, new Random(1));
			System.out.println("10fold;"+evaluator.fMeasure(indiceClase)+";"+(double)(System.currentTimeMillis()-inicio)/1000);
		}else{
			System.out.println("Necesarios archivos train, dev y modelo NaiveBayes. (en ese orden)");
		}
	}
	}
