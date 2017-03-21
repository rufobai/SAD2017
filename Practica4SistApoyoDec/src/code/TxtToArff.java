package code;

import java.io.BufferedReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class TxtToArff {
	
	
	private String rutaIn;
	private String rutaOut;
	
	
	public TxtToArff(String pRutaIn, String pRutaOut) {
		rutaIn = pRutaIn;
		rutaOut = pRutaOut;
		
	}
	
	public void obtenerArff(){
		try{
			if(!rutaIn.isEmpty() && !rutaOut.isEmpty()){
				BufferedReader in = new BufferedReader(new FileReader(rutaIn));
				BufferedWriter out = new BufferedWriter(new FileWriter(rutaOut));
				String line;
				int id=0;
				int indi = 0;
				if(rutaIn.indexOf(".dev") != -1){
					indi = rutaIn.indexOf(".dev");
				}else if(rutaIn.indexOf(".train") != -1){
					indi = rutaIn.indexOf(".train");
				}else if(rutaIn.indexOf(".test_blind") != -1){
					indi = rutaIn.indexOf(".test_blind");
				}
				
				String rela = rutaIn.substring(0, indi);
				int cont = 0;
				String sTexto = in.readLine();
						
				while (sTexto.indexOf(",") > -1) {
					sTexto = sTexto.substring(sTexto.indexOf(
					   ",")+",".length(),sTexto.length());
						      cont++; 
				}
				
				System.out.println("Numero de apariciones: " + cont);
						
				
				out.write("@relation " + rela + " \n\n");
				out.write("@attribute id NUMERIC\n");
				out.write("@attribute text string\n");
				out.write("@attribute class {ham,spam}\n\n");
				out.write("@data\n\n");
				//archivo test_blind
				if(rutaIn.contains("test_blind")){
					while((line = in.readLine()) != null){
						//formato: id,'text',?
						//elimina los char ' de los SMS porque en weka da problemas
					    out.write(id+","+"'"+line.substring(4,line.length()).replace("'", "")+"',?\n");
					    id++;
					}
				}
				//archivos train y dev
				else{
					while((line = in.readLine()) != null){
						//formato: id,'text',class
						//elimina los char ' de los SMS porque en weka da problemas
					    out.write(id+","+"'"+line.substring(4,line.length()).replace("'", "")+"',"+line.substring(0,4)+"\n");
					    id++;
					}
				}	
				in.close();
				out.close();
			}
			else{
				System.out.println("Se necesitan dos parametros, primero el txt y segundo el archivo arff donde guardar la informacion.");
				System.out.println("Comando esperado: java -jar getARFF.jar file.txt file.arff");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
