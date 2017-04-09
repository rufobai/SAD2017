package clasificador;

import java.io.FileReader;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.meta.Bagging;
import weka.core.Instances;

public class AplicarBagging {

    
    public static void main(String[] args) throws Exception {
   	 // comprueba que se reciben dos archivos, el primero train, el segundo
   	 // dev por ultimo el .model
   	 if (args.length == 2) {
   		 // Se cargan las instancias test
   		 Instances test = new Instances(new FileReader(args[0]));
   		 test.setClassIndex(test.attribute("class").index());
   	 
   		 int indiceClase = 1; // class {ham,spam} se usara spam
   		 // Cargar modelo de NaiveBayes
   		 Bagging bagg = (Bagging) weka.core.SerializationHelper.read(args[1]);
   		 
   		 int cont = 0;
   		 for (int i = 0; i < test.numInstances(); i++) {
   			 //System.out.println("La instancia nº: " + i + " es de tipo:" + bagg.classifyInstance(test.get(i)));
   			 System.out.println("Vueltas:" + i + " " + test.get(i));
   			 double pred = bagg.classifyInstance(test.instance(i));
   			 if(test.classAttribute().value((int)pred).compareTo("spam")==0){
   				 System.out.println((i+1));
   				 cont++;
   			 }   			 
   		 }
   		 System.out.println("\nTotal de instancias SPAM: "+cont);
   		 
   	 } else {
   		 System.out.println("Necesarios archivos train, dev y modelo NaiveBayes. (en ese orden)");
   	 }
    }
}