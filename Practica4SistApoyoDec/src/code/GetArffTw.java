package code;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class GetArffTw {

	private static GetArffTw mArchivo = new GetArffTw();
	private File archivo;
	private FileReader fr;
	private BufferedReader br;

	private GetArffTw() {
		archivo = null;
		fr = null;
		br = null;
	}

	public static GetArffTw getFichero() {
		return mArchivo;
	}

	public static void main(String[] args) {
		try{
			GetArffTw.getFichero().parser(args[0], args[1]);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void parser(String pArchivo,String pSalida) throws FileNotFoundException, UnsupportedEncodingException, IOException {
		PrintWriter pw = new PrintWriter(pSalida, "UTF-8");
		archivo = new File(pArchivo);
		fr = new FileReader(archivo);
		br = new BufferedReader(fr);
		int contador = 0;
		String linea;

		while ((linea = br.readLine()) != null) {
			// separa las lineas por comas y las mete en un array
			String[] parts = linea.split(",");
			// la primera vuelta mete los atributos
			if (contador == 0) {
				pw.println("@relation tweet");
				pw.println("@attribute Topic { \"microsoft\", \"google\", \"apple\", \"twitter\"}");
				pw.println(
						"@attribute Sentiment { \"UNKNOWN\",\"negative\", \"neutral\", \"irrelevant\", \"positive\"}");
				pw.println("@attribute TweetId NUMERIC");
				pw.println("@attribute fecha DATE MMM dd HH:mm:ss z yyyy");
				pw.println("@attribute TweetText string");
				pw.println("@data");
				contador++;
			}
			// la segunda mete las instancias
			else {
				contador++;
				String texto = "";
				// con estos dos if lo que hacemos es quitar lineas inservibles
				if (parts.length >= 5) {
					if (parts[0].equals("\"microsoft\"") || parts[0].equals("\"google\"")
							|| parts[0].equals("\"apple\"") || parts[0].equals("\"twitter\""))
						for (int i = 0; i < parts.length; i++) {
							if (i > 3) {
								texto = "";
								for (int k = 4; k < parts.length; k++) {
									texto = texto + parts[k];
								}
								texto = texto.replaceAll("[^\\x00-\\x7F]", "");
								texto = texto.replaceAll("[\']", "");
								texto = texto.replaceAll("[\"]", "");
								pw.print("'" + texto + "'");
								i = parts.length;
							} else {
								if (i == 3) {
									texto = dateParser(parts[i]);
									texto = texto.replaceAll("[\"]", "");
									pw.print("'" + texto + "',");
								} else {
									texto = parts[i];
									texto = texto.replaceAll("[\"]", "");
									pw.print("'" + texto + "',");
								}
							}

						} // fin del for
					pw.println(" ");
					pw.flush();
				} // fin del if
			}
		}
		pw.close();
	}

	public String dateParser(String pDate) {
		String fecha = "\"";
		String[] parts = pDate.split(" ");
		for(int i=1;i<parts.length;i++){
			fecha=fecha+parts[i]+" ";
		}
		return fecha;
	}
}