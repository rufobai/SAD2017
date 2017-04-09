package baseline;

import java.io.FileReader;

import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;

public class ClasificarNaiveBayes {
	public static void main(String[] args) throws Exception {
		//comprueba que se recibe un parametro
		if(args.length==2 && args[0].contains(".arff") && args[1].contains(".model")){
		
			Instances unlabeled = new Instances(new FileReader(args[0]));
			unlabeled.setClassIndex(unlabeled.attribute("class").index());
			System.out.println(">>Instancias cargadas.");
			NaiveBayes miNaiveBayes = (NaiveBayes) weka.core.SerializationHelper.read(args[1]);
			System.out.println(">>NaiveBayes cargado.\n");
			int cont=0;
			System.out.println("Instancias clasificadas del archivo "+args[0].toString());
			System.out.println("Modelo usado "+args[1]);
			System.out.println("Instancias SPAM:");
			for(int i=0;i<unlabeled.numInstances();i++){
				System.out.println(unlabeled.get(0));
				double pred = miNaiveBayes.classifyInstance(unlabeled.instance(i));
				if(unlabeled.classAttribute().value((int)pred).compareTo("spam")==0){
					System.out.println((i+1));
					cont++;
				}
			}
			System.out.println("\nTotal de instancias SPAM: "+cont);
		}else{
			System.out.println("Necesario arff con instancias para clasificar y el modelo con NaiveBayes.");
		}
	
	}
}
