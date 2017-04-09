package clasificador;

import java.io.FileReader;
import java.util.ArrayList;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.meta.Bagging;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.REPTree;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;

public class BaggingClasificador {

	/*
	 * public static void main2(String[] args) throws Exception { if
	 * (args.length == 2 && args[0].contains("train") &&
	 * args[1].contains("dev")) { Instances train = new Instances(new
	 * FileReader(args[0]));
	 * train.setClassIndex(train.attribute("class").index()); Instances dev =
	 * new Instances(new FileReader(args[1]));
	 * dev.setClassIndex(dev.attribute("class").index());
	 * 
	 * int indiceClase = 1;// class {ham,spam}, interesa spam Bagging bagg;
	 * Evaluation evaluator = new Evaluation(train);
	 * 
	 * long tI; ArrayList<Classifier> listaClasificadores = new
	 * ArrayList<Classifier>(); REPTree rTree = new REPTree();
	 * rTree.buildClassifier(train); NaiveBayes nBayes = new NaiveBayes();
	 * nBayes.buildClassifier(train); RandomForest rForest = new RandomForest();
	 * rForest.setNumTrees(200); rForest.setNumExecutionSlots(10);
	 * rForest.buildClassifier(train); J48 j48 = new J48();
	 * j48.buildClassifier(train); listaClasificadores.add(rTree);
	 * listaClasificadores.add(nBayes); listaClasificadores.add(rForest);
	 * listaClasificadores.add(j48);
	 * 
	 * for (int i = 0; i < listaClasificadores.size(); i++) { bagg = new
	 * Bagging(); tI = System.currentTimeMillis(); bagg.setBagSizePercent(100);
	 * bagg.setCalcOutOfBag(false); bagg.setNumExecutionSlots(10);
	 * bagg.setClassifier(listaClasificadores.get(i));
	 * bagg.buildClassifier(train); evaluator.evaluateModel(bagg, dev);
	 * System.out.println( evaluator.fMeasure(indiceClase) + ";" + (double)
	 * (System.currentTimeMillis() - tI) / 1000); }
	 * 
	 * } else {
	 * System.out.println("Necesarios archivos train y dev (en ese orden)"); }
	 * 
	 * }
	 */

	public static void main(String[] args) throws Exception {
		
		if (args.length == 2 && args[0].contains("train") && args[1].contains("dev")) {
			System.out.println("Empieza la ejecucion");
			Instances train = new Instances(new FileReader(args[0]));
			train.setClassIndex(train.attribute("class").index());
			Instances dev = new Instances(new FileReader(args[1]));
			dev.setClassIndex(dev.attribute("class").index());

			int indiceClase = 1;// class {ham,spam}, interesa spam
			Bagging bagg;
			Evaluation evaluator = new Evaluation(train);
			long tI;
			ArrayList<Classifier> listaClasificadores = new ArrayList<Classifier>();
			REPTree rTree = new REPTree();
			rTree.buildClassifier(train);
			NaiveBayes nBayes = new NaiveBayes();
			nBayes.buildClassifier(train);
			RandomForest rForest = new RandomForest();
			rForest.setNumExecutionSlots(10);
			rForest.buildClassifier(train);
			J48 j48 = new J48();
			listaClasificadores.add(rTree);
			listaClasificadores.add(nBayes);
			listaClasificadores.add(rForest);
			listaClasificadores.add(j48);
			System.out.println("Empieza la evaluacion");
			for (int i = 0; i < listaClasificadores.size(); i++) {
				System.out.println("Ejecucion Nº: " + i);
				if (i == 2) {
					System.out.println("Ejecucion randomForest");
					int[] numT = { 5, 50, 100, 175, 250, 325, 400, 500 };
					for (int j : numT) {
						System.out.println("RandomForest con: " + j + " arboles");
						rForest.setNumTrees(j);
						bagg = new Bagging();
						tI = System.currentTimeMillis();
						bagg.setBagSizePercent(100);
						bagg.setCalcOutOfBag(false);
						bagg.setNumExecutionSlots(10);
						bagg.setClassifier(listaClasificadores.get(i));
						bagg.buildClassifier(train);
						evaluator.evaluateModel(bagg, dev);
						System.out.println(evaluator.fMeasure(indiceClase) + ";"
								+ (double) (System.currentTimeMillis() - tI) / 1000);

					}

				} 
				else if (i == 3) {
					System.out.println("J48");
					bagg= new Bagging();
					tI = System.currentTimeMillis();
					for (float j = 0.05f; j < 1.0f; j+=0.05f) {
						System.out.println("J48 con confidence: " + j + " arboles");
						j48.setConfidenceFactor(j);
						j48.setMinNumObj(2);
						j48.setNumFolds(3);
						j48.buildClassifier(train);
						bagg.setBagSizePercent(100);
						bagg.setCalcOutOfBag(false);
						bagg.setNumExecutionSlots(1);
						bagg.setClassifier(listaClasificadores.get(i));
						bagg.buildClassifier(train);
						evaluator.evaluateModel(bagg, dev);
						System.out.println("J48 con "+ j+ " " +evaluator.fMeasure(indiceClase) + ";"
								+ (double) (System.currentTimeMillis() - tI) / 1000);
					}
				} else {

					bagg = new Bagging();
					tI = System.currentTimeMillis();
					bagg.setBagSizePercent(100);
					bagg.setCalcOutOfBag(false);
					bagg.setNumExecutionSlots(10);
					bagg.setClassifier(listaClasificadores.get(i));
					bagg.buildClassifier(train);
					evaluator.evaluateModel(bagg, dev);
					System.out.println("holdOut--> precision: "+evaluator.precision(indiceClase)+" ; recall: "+evaluator.recall(indiceClase)+"; fMeasure: "+evaluator.fMeasure(indiceClase)+";"+(double)(System.currentTimeMillis()-tI)/1000);
				}
			}

		} else {
			System.out.println("Necesarios archivos train y dev (en ese orden)");
		}

	}

}
