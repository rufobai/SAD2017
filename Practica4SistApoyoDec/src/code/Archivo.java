package code;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import weka.core.Instances;
import weka.core.converters.ArffSaver;

public class Archivo {
	private FileReader archivo;
	private Instances instancias;

	public void cargarFichero(String pRuta) {
		try {
			archivo = new FileReader(pRuta);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("La ruta del archivo es incorrecta");
		}
	}

	public void cerrarFichero(FileReader pFile) {
		try {
			pFile.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Ha habido un problema al cerrar el fichero");
		}
	}

	public void guardarFichero(String pRuta, Instances pInstancias) throws IOException {
		File archivo = new File(pRuta);
		ArffSaver saver = new ArffSaver();
		saver.setInstances(instancias);
		saver.setFile(archivo);
		saver.setDestination(archivo);
		saver.writeBatch();
	}

	public Instances generarInstancias() {
		try {
			instancias = new Instances(archivo);
			fijarAtributoClase();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("No se han podido generar las instancias");
		}
		return instancias;
	}

	public void fijarAtributoClase() {
		instancias.setClassIndex(instancias.numAttributes() - 1);
	}

	public Instances getInstancias() {
		return instancias;
	}
}
