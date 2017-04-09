package code;


import java.io.FileReader;

import weka.classifiers.meta.Bagging;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.core.SerializationHelper;

//PRE: Arff con instancias de entrenamiento, clase=class
//POST: guarda model con Bagging(numTrees=5,numSlots=4,maxDepth=0)
public class GuardarBagging {
    public static void main(String[] args) throws Exception{
   	 //comprueba que se recibe un parametro
   	 if(args.length==2){
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
   		 rForest.setNumTrees(5);
   		 //cargar instancias
   		 bagg.setClassifier(rForest);
   		 bagg.buildClassifier(train);
   		 System.out.println(">>Datos train cargados.");    
   		 //crear RandomForest   	 
   		 System.out.println(">>Bagging creado.");;
   		 SerializationHelper.write(args[1], bagg);
   		 System.out.println(">>Bagging guardado.");
   	 }else{
   		 System.out.println("Necesario arff con instancias de entrenamiento y ruta donde se almacenara el modelo");
   	 }
    }
}
