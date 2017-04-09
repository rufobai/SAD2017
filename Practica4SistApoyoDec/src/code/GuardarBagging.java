package code;


import java.io.FileReader;

import weka.classifiers.meta.Bagging;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.core.SerializationHelper;

//PRE: Arff con instancias de entrenamiento, clase=class
//POST: guarda model con RandomForest(numTrees=55,numSlots=4,maxDepth=0)
public class GuardarBagging {
	public static void main(String[] args) throws Exception{
		//comprueba que se recibe un parametro
		if(args.length==1){
			Instances train = new Instances(new FileReader(args[0]));
			train.setClassIndex(train.attribute("class").index());
			
			Bagging bagg;
			bagg = new Bagging();
			bagg.setBagSizePercent(100);
			bagg.setCalcOutOfBag(false);
			bagg.setNumExecutionSlots(10);
			
			RandomForest rForest = new RandomForest();
			rForest.setNumExecutionSlots(10);
			rForest.buildClassifier(train);
			rForest.setNumTrees(500);
			//cargar instancias
			
			System.out.println(">>Datos train cargados.");	
			//crear RandomForest		
			System.out.println(">>RandomForest creado.");;
			SerializationHelper.write("miModelo.model", rForest);
			System.out.println(">>RandomForest guardado.");
		}else{
			System.out.println("Necesario arff con instancias de entrenamiento.");
		}
	}
}