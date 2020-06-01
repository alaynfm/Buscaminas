package codigo;

import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class ListaCasillas {

	private HashMap<String, Casilla> lista;
	public ListaCasillas() {
		lista = new HashMap<String, Casilla>();
	}
	
	/* Para las casillas normales utilizaremos como clave cod = c + fila + columna
	   para las bombas utilizaremos como clave cod = b + fila + columna; */
	
	public void anadirCasilla(String cod, Casilla casilla) {
		lista.put(cod, casilla);
	}

	public void eliminarCasillla(String cod) {
		if (!lista.isEmpty()) {
			lista.remove(cod);
		}
	}

	public Casilla getCasillaAleatoria() {
		Collection<Casilla> values = lista.values();
		Casilla[] targetArray = values.toArray(new Casilla[values.size()]);
		int columna = ThreadLocalRandom.current().nextInt(0,targetArray.length-1);
		return targetArray[columna];
	}

	public int size() {
		return lista.size();
	}
	
	public boolean estaCasilla(String cod) {
		Boolean esta = false;
		if (lista.containsKey(cod)) {esta = true;}
		return esta;
	}
}