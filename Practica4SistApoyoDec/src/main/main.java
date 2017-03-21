package main;

import baseline.NaiveBayesBaseline;
import code.Archivo;
import code.TxtToArff;
import weka.core.Instances;

public class main {

	public static void main(String[] args) throws Exception {
		System.out.println((args[0]));
		
		TxtToArff ta = new TxtToArff(args[0], args[1]);
		ta.obtenerArff();
	/*	
	Archivo archivo= new Archivo();
	archivo.cargarFichero("C:/Users/Mishel/workspace/Practica4SAD/datosConvertidos/smsDev.arff");
	Instances test=archivo.generarInstancias();
	Archivo archivo2= new Archivo();
	archivo2.cargarFichero("C:/Users/Mishel/workspace/Practica4SAD/datosConvertidos/sms.arff");
	Instances train=archivo2.generarInstancias();
	NaiveBayesBaseline nb = new NaiveBayesBaseline();
	nb.clasificar(train, test);*/
	}
}
