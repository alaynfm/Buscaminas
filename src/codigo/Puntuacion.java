package codigo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class Puntuacion<T> {

	private static Puntuacion miPuntuacion = new Puntuacion();
	LinkedListJ<String>[] datos;

	private Puntuacion() {

		datos = new LinkedListJ[3];
		// initializing
		for (int i = 0; i < 3; i++) {
			datos[i] = new LinkedListJ<String>();
		}
		leerdatosFichero();
	}

	public static Puntuacion getPuntuacion() {
		return miPuntuacion;

	}

	// metodo guardar datos en el fichero
	public void leerdatosFichero() {

		

		/*
		 * leemos los datos dependiendo de la dificultad separamos cada dificultad en un
		 * array
		 */

		try {
			Scanner input = new Scanner(new File("bd/bd.txt"));

			while (input.hasNextLine()) {
				String line = input.nextLine();
				String[] donde = line.split("\\s+--->\\s+");

				if ((donde[0]).toLowerCase().equalsIgnoreCase("facil"))
					datos[0].anadirNuevo(line);
				else if (donde[0].toLowerCase().equalsIgnoreCase("dificil"))
					datos[2].anadirNuevo(line);
				else
					datos[0].anadirNuevo(line);

			}
			input.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}

	}

	// metodo para leer los datos del fichero
	public void guardarFichero(String nivel, String jugador, int puntuacion, int tiempo) {

		int n;

		if (nivel.toLowerCase().equals("facil"))
			n = 0;
		else if (nivel.toLowerCase().equals("dificil"))
			n = 2;
		else
			n = 1;

		datos[n].anadirNuevo(nivel+  " ---> " + jugador +  " ---> " + puntuacion +  " ---> " + tiempo);
		FileWriter fichero = null;
		PrintWriter pw = null;
		
		
		try {
			fichero = new FileWriter("bd/bd.txt");
			pw = new PrintWriter(fichero);

			for (int i = 0; i < datos.length; i++) {
				
				Node<String> first = datos[i].getFirst();
				
				while(first != null) {
					pw.print(first.data);
					pw.println();
					first = first.next;
				}
			}
			
		} catch (Exception e) {

		} finally {
			try {
				if (null != fichero) {
					fichero.close();
					pw.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}


	public LinkedListJ<String> getFacil(){
		return datos[0];
	}
	public LinkedListJ<String> getMedio(){
		return datos[1];
	}
	public LinkedListJ<String> getDificil(){
		return datos[2];
	}
	
	public static void main(String[] args) {

		Puntuacion.miPuntuacion.guardarFichero("Facil","Alain" ,1300, 10);
		Puntuacion.miPuntuacion.guardarFichero("Facil","Alain" ,100, 10);

		Puntuacion.miPuntuacion.guardarFichero("medio","Alain" ,99, 10);
		Puntuacion.miPuntuacion.guardarFichero("medio","Alain" ,100, 10);
		Puntuacion.miPuntuacion.guardarFichero("medio","Alain" ,1300, 10);
		
		
		Puntuacion.miPuntuacion.guardarFichero("dificil","Alain" ,100, 10);
		Puntuacion.miPuntuacion.guardarFichero("dificil","Alain" ,1300, 10);
		Puntuacion.miPuntuacion.guardarFichero("dificil","Alain" ,99, 10);
	}
}
