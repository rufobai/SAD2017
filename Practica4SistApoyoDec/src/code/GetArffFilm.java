package code;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import weka.core.*;
import weka.core.converters.ArffSaver;
import weka.core.pmml.FieldMetaInfo.Value;
import weka.core.pmml.FieldMetaInfo.Value.Property;

public class GetArffFilm {

	static Instances data = null;
 
  public Instances createDataset(String directoryPath, String pClass, ArrayList<Attribute> atts) throws Exception {
 
	System.out.println("directorio: "+directoryPath);  

    File dir = new File(directoryPath);
    String[] files = dir.list();
    for (int i = 0; i < files.length; i++) {
      if (files[i].endsWith(".txt")) {
    try {
      double[] newInst = new double[2];
     
      newInst[0] = (double)data.attribute(0).indexOfValue(pClass);
      
      File txt = new File(directoryPath + File.separator + files[i]);
      InputStreamReader is;
      is = new InputStreamReader(new FileInputStream(txt));
      StringBuffer txtStr = new StringBuffer();
      int c;
      while ((c = is.read()) != -1) {
        txtStr.append((char)c);
      }
      newInst[1] = (double)data.attribute(1).addStringValue(txtStr.toString());
      data.add(new DenseInstance(1.0, newInst));
    } catch (Exception e) {
      System.err.println("failed to convert file: " + directoryPath + File.separator + files[i]);
    }
      }
    }
    return data;
}
 
  public static void main(String[] args) {
	  
	  if (args.length == 3 ) {
		  GetArffFilm tdta = new GetArffFilm();
      try {
    	    ArrayList<Attribute> atts = new ArrayList(2);
    	    List valores = new ArrayList(2);
    	    valores.add("pos");
    	    valores.add("neg");
    	    Attribute valor = new Attribute("valor", valores);
    	    atts.add(valor);
    	    atts.add(new Attribute("contents", (List)null));
    	    
 	data = new Instances("text_files_in_" + args[0], atts, 0);
 	Instances dataset, dataset2 = null;
 	
 	if (args[1].equalsIgnoreCase("si")) {
 		
    dataset2 = tdta.createDataset(args[0]+"/pos","pos",atts);
    dataset = tdta.createDataset(args[0]+"/neg","neg",atts);
    
 	}
 	else {
 		dataset = tdta.createDataset(args[0],"pos",atts);
 		dataset.deleteAttributeAt(0);
 		dataset.insertAttributeAt(valor,0);
 	}

 	System.out.println("instancias totales: " + dataset.numInstances());
    FileWriter grabador = new FileWriter(args[2]);
    grabador.write(dataset.toString());
    grabador.close();
 
      } catch (Exception e) {
    System.err.println(e.getMessage());
    e.printStackTrace();
      }
    } 
  else {
      System.out.println("Usage: java -jar RawText2Arff.jar <directory name> <sabemos la clase si o no> <archivo de salida>");
    }
    
}
  }
