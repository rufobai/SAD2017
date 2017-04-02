package clasificador;

import java.io.FileReader;
import java.util.ArrayList;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.meta.Bagging;
import weka.classifiers.trees.REPTree;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;

public class BaggingClasificador {
	
	public static void main(String[] args) throws Exception{ 
		if(args.length==2&&args[0].contains("train")&&args[1].contains("dev")){
			Instances train = new Instances(new FileReader(args[0]));
			train.setClassIndex(train.attribute("class").index());
			Instances dev = new Instances(new FileReader(args[1]));
			dev.setClassIndex(dev.attribute("class").index());
			
			int indiceClase=1;//class {ham,spam}, interesa spam
			Bagging  bagg = new Bagging();
			Evaluation evaluator = new Evaluation(train);
			
			long tI;
			ArrayList<Classifier> listaClasificadores = new ArrayList<Classifier>();
			REPTree rTree = new REPTree();
			rTree.buildClassifier(train);
			NaiveBayes nBayes= new NaiveBayes();
			nBayes.buildClassifier(train);
			RandomForest rForest= new RandomForest();
			rForest.buildClassifier(train);
			listaClasificadores.add(rTree);
			listaClasificadores.add(nBayes);
			listaClasificadores.add(rForest);
			for (int i = 0; i < listaClasificadores.size(); i++) {
					tI = System.currentTimeMillis();
					bagg.setBagSizePercent(100);
					bagg.setCalcOutOfBag(false);
					bagg.setNumExecutionSlots(4);
					bagg.buildClassifier(train);
					bagg.setClassifier(listaClasificadores.get(i));
					evaluator.evaluateModel(bagg, dev);
					System.out.println(listaClasificadores.get(i).toString() +";"+evaluator.fMeasure(indiceClase)+";"+(double)(System.currentTimeMillis()-tI)/1000);
					System.err.println(i);
			}
			
		}else{
			System.out.println("Necesarios archivos train y dev (en ese orden)");
		}		
		
	}
	
//	public static void main(String[] args) throws Exception{ 
//		if(args.length==2&&args[0].contains("train")&&args[1].contains("dev")){
//			Instances train = new Instances(new FileReader(args[0]));
//			train.setClassIndex(train.attribute("clase").index());
//			Instances dev = new Instances(new FileReader(args[1]));
//			dev.setClassIndex(dev.attribute("clase").index());
//			
//			int indiceClase=1;//class {ham,spam}, interesa spam
//			weka.classifiers.meta.Bagging  bagg = new weka.classifiers.meta.Bagging();
//			Evaluation evaluator = new Evaluation(train);
//			
//			long tI;
//				
//				for (int i = 1; i < 100; i = i +2) {
//					for (int j = 1; j < 100; j = j + 6) {
//						tI = System.currentTimeMillis();
//						bagg.setBagSizePercent(i);
//						bagg.setNumExecutionSlots(j);
//						bagg.buildClassifier(train);
//						evaluator.evaluateModel(bagg, dev);
//						System.out.println(i+";"+evaluator.fMeasure(indiceClase)+";"+(double)(System.currentTimeMillis()-tI)/1000);
//						System.err.println(i);
//					}
//				}
//			
//		}else{
//			System.out.println("Necesarios archivos train y dev (en ese orden)");
//		}
//	}
	
}
