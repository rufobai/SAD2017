package clasificador;

import java.io.FileReader;

import weka.classifiers.evaluation.Evaluation;
import weka.core.Instances;

public class Bagging {
	
	public static void main(String[] args) throws Exception{ 
		if(args.length==2&&args[0].contains("train")&&args[1].contains("dev")){
			Instances train = new Instances(new FileReader(args[0]));
			train.setClassIndex(train.attribute("class").index());
			Instances dev = new Instances(new FileReader(args[1]));
			dev.setClassIndex(dev.attribute("class").index());
			
			int indiceClase=1;//class {ham,spam}, interesa spam
			weka.classifiers.meta.Bagging  bagg = new weka.classifiers.meta.Bagging();
			Evaluation evaluator = new Evaluation(train);
			
			long tI;
				
				for (int i = 1; i < 100; i++) {
					tI = System.currentTimeMillis();
					bagg.setBagSizePercent(i);
					bagg.setNumExecutionSlots(4);
					bagg.buildClassifier(train);
					evaluator.evaluateModel(bagg, dev);
					System.out.println(i+";"+evaluator.fMeasure(indiceClase)+";"+(double)(System.currentTimeMillis()-tI)/1000);
					System.err.println(i);
				}
			
		}else{
			System.out.println("Necesarios archivos train y dev (en ese orden)");
		}		
		
	}
	
	public static void main2(String[] args) throws Exception{ 
		if(args.length==2&&args[0].contains("train")&&args[1].contains("dev")){
			Instances train = new Instances(new FileReader(args[0]));
			train.setClassIndex(train.attribute("class").index());
			Instances dev = new Instances(new FileReader(args[1]));
			dev.setClassIndex(dev.attribute("class").index());
			
			int indiceClase=1;//class {ham,spam}, interesa spam
			weka.classifiers.meta.Bagging  bagg = new weka.classifiers.meta.Bagging();
			Evaluation evaluator = new Evaluation(train);
			
			long tI;
				
				for (int i = 1; i < 100; i = i +2) {
					for (int j = 1; j < 100; j = j + 5) {
						tI = System.currentTimeMillis();
						bagg.setBagSizePercent(i);
						bagg.setNumExecutionSlots(j);
						bagg.buildClassifier(train);
						evaluator.evaluateModel(bagg, dev);
						System.out.println(i+";"+evaluator.fMeasure(indiceClase)+";"+(double)(System.currentTimeMillis()-tI)/1000);
						System.err.println(i);
					}
<<<<<<< HEAD
=======
					
>>>>>>> branch 'master' of https://github.com/rufobai/SAD2017.git
				}
			
		}else{
			System.out.println("Necesarios archivos train y dev (en ese orden)");
		}
	

	}
	
}
