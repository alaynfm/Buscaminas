package codigo;

import java.util.Observable;
import java.util.Observer;

import interfaz.Iu_Juego;

public class Casilla extends Observable{

	private int fila;
	private int columna;
	private int numMinas;
	private Estado estado;

	// Constructora vacia
	public Casilla() {
		super();
		addObserver((Observer) this);

	}

	public Casilla(int filas, int columnas, int bombas, Iu_Juego juego) {
		// Inicializamos las variables con el estado inicial como cerrado
		super();
		numMinas = bombas;
		fila = filas;
		columna = columnas;
		estado = new Cerrada();	
		addObserver(juego);
	}

	public void cambiarEstado(Estado pEstado) {
		/*
		 * Cambia el estado de la casilla por el estado actual Se le tiene que pasar por
		 * parametro dcho o derecho, o izq o izquierda como paramentro
		 */

		if (estado.getEstado() != pEstado.getEstado()) {
			estado = pEstado;
			setChanged();
			notifyObservers(new int[] {fila,columna});
		}
	}

	public void clickDer() {
		estado.clickDer(fila, columna);
	}

	public void clickIzq() {
		estado.clickIzq(fila, columna);
	}

	public boolean esBomba() {
		// Comprueba si la casilla es una bomba
		return this.numMinas == -1;
	}

	public int getNumMinas() {
		// Devuelve el numero de minas que haya en la partid
		return this.numMinas;
	}

	public void incrementarNumMinas() {
		// Aumenta el numero de minas total
		if (this.numMinas != -1) {
			this.numMinas++;
		} else {
		}

	}

	public int getFila() {
		return this.fila;
	}

	public int getcolumna() {
		return this.columna;
	}

	public int getEstado() {
		// 0 abierta, 1 bandera, 2 cerrada;
		// La utilizamos en Iu_Partida para conocer el estado de la casilla.

		return estado.getEstado();
	}

}