package codigo;

import java.util.LinkedList;
import java.util.Queue;

import interfaz.Iu_Juego;

public class Tablero {

	private static Tablero mTablero;

	private Casilla[][] tablero;

	private Tablero() {
	}

	public static Tablero getTablero() {
		if (mTablero == null) {
			mTablero = new Tablero();
		}
		return mTablero;
	}

	/***********************************************************************
	                   *Creacion de tablero Casillas *
	 ************************************************************************/

	public void generarTablero(int filas, int columnas, int bombas, Iu_Juego juego, int primeraF, int primeraC) {
		// TODO - implement Tablero.generarTablero
		
		eliminarTablero();
		tablero = new Casilla[filas][columnas];
		ListaCasillas listaCasillas = new ListaCasillas();
		ListaCasillas listaBombas = new ListaCasillas();
		// meter casillas normales
		for (int fila = 0; fila < tablero.length; fila++) {
			for (int columna = 0; columna < tablero[0].length; columna++) {

				Casilla casilla = new Casilla(fila, columna, 0, juego);
				tablero[fila][columna] = casilla;

				if ((casilla.getFila() == primeraF && casilla.getcolumna() == primeraC)
						|| (casilla.getFila() == primeraF - 1 && casilla.getcolumna() == primeraC)
						|| (casilla.getFila() == primeraF + 1 && casilla.getcolumna() == primeraC)
						|| (casilla.getFila() == primeraF && casilla.getcolumna() == primeraC + 1)
						|| (casilla.getFila() == primeraF && casilla.getcolumna() == primeraC - 1)
						|| (casilla.getFila() == primeraF + 1 && casilla.getcolumna() == primeraC + 1)
						|| (casilla.getFila() == primeraF + 1 && casilla.getcolumna() == primeraC - 1)
						|| (casilla.getFila() == primeraF - 1 && casilla.getcolumna() == primeraC + 1)
						|| (casilla.getFila() == primeraF - 1 && casilla.getcolumna() == primeraC - 1)) {

					// Cuando hacemos click la casilla sea vacia
					// No ponemos en la lista, asi no podra ser bomba ni ella, ni las de alrededor

				} else {

					listaCasillas.anadirCasilla(fila + "" + columna + "", casilla);
				}
			}
		}

		// anadimos las bombas
		/*
		 * Al a�adir las bombas hay q tener en cuenta que no se a�ada una encima de
		 * la otra a la hora de incrementar el contador se incrementaria x2
		 *
		 */
		for (int f = 0; f < bombas; f++) {

			Casilla casilla = listaCasillas.getCasillaAleatoria();
			Casilla bomba = new Casilla(casilla.getFila(), casilla.getcolumna(), -1, juego);
			tablero[casilla.getFila()][casilla.getcolumna()] = bomba;
			listaBombas.anadirCasilla(casilla.getFila() + "" + casilla.getcolumna() + "", casilla);
			listaCasillas.eliminarCasillla(casilla.getFila() + "" + casilla.getcolumna() + "");
			getMinasAlrededor(casilla.getFila(), casilla.getcolumna());
		}
	}

	private boolean posicionValida(int fila, int columna) {
		if (fila < tablero.length && fila >= 0) {
			if (columna < tablero[0].length && columna >= 0)
				return true;
			else
				return false;
		} else
			return false;
	}

	private void getMinasAlrededor(int fila, int columna) {
		// TODO Auto-generated method stub
		if (posicionValida(fila + 1, columna)) {
			(tablero[fila + 1][columna]).incrementarNumMinas();
		}
		if (posicionValida(fila + 1, columna - 1)) {
			(tablero[fila + 1][columna - 1]).incrementarNumMinas();
		}
		if (posicionValida(fila + 1, columna + 1)) {
			(tablero[fila + 1][columna + 1]).incrementarNumMinas();
		}
		if (posicionValida(fila - 1, columna)) {
			(tablero[fila - 1][columna]).incrementarNumMinas();
		}
		if (posicionValida(fila - 1, columna - 1)) {
			(tablero[fila - 1][columna - 1]).incrementarNumMinas();
		}
		if (posicionValida(fila - 1, columna + 1)) {
			(tablero[fila - 1][columna + 1]).incrementarNumMinas();
		}
		if (posicionValida(fila, columna + 1)) {
			(tablero[fila][columna + 1]).incrementarNumMinas();
		}
		if (posicionValida(fila, columna - 1)) {
			(tablero[fila][columna - 1]).incrementarNumMinas();
		}
	}

	/***********************************************************************
	                        *Destapar Casillas *
	 ************************************************************************/
	public boolean destaparCasillas(int fila, int columna) {
		Boolean fin = false; // Booleano para saber, si la casilla clicada era una mina
		if (tablero[fila][columna].getEstado() == 0) { //Condicion para abrir las casillas cuando el estado sea abierto

			Queue<Casilla> porVisitar = new LinkedList<Casilla>(); // Creamos la lista de casillas por visitar, para ver
																	// si
																	// hay que abrirlas o no

			Casilla act = tablero[fila][columna]; // Buscamos la casilla en el tablero
			ListaCasillas listaCasillas = new ListaCasillas();
			listaCasillas.anadirCasilla(act.getFila() + "" + act.getcolumna() + "", act);
			// act.cambiarEstado(new Abierto()); // Abrimos la casilla

			if (act.esBomba()) { // Si la casilla abierta es una bomba, se termina el juego
				fin = true;
			} else { // Sino es bomba comprobamos si al rededor: 1.La casilla esta en el tablero --
						// 2.Si es una bomba -- 3.Si la casilla actual no tiene bombas al rededor
				if (posicionValida(fila + 1, columna) && !tablero[fila + 1][columna].esBomba()
						&& tablero[fila][columna].getNumMinas() == 0 && tablero[fila + 1][columna].getEstado() == 2) {
					porVisitar.add(tablero[fila + 1][columna]);
				}
				if (posicionValida(fila + 1, columna - 1) && !tablero[fila + 1][columna - 1].esBomba()
						&& tablero[fila][columna].getNumMinas() == 0
						&& tablero[fila + 1][columna - 1].getEstado() == 2) {
					porVisitar.add(tablero[fila + 1][columna - 1]);
				}
				if (posicionValida(fila + 1, columna + 1) && !tablero[fila + 1][columna + 1].esBomba()
						&& tablero[fila][columna].getNumMinas() == 0
						&& tablero[fila + 1][columna + 1].getEstado() == 2) {
					porVisitar.add(tablero[fila + 1][columna + 1]);
				}
				if (posicionValida(fila - 1, columna) && !tablero[fila - 1][columna].esBomba()
						&& tablero[fila][columna].getNumMinas() == 0 && tablero[fila - 1][columna].getEstado() == 2) {
					porVisitar.add(tablero[fila - 1][columna]);
				}
				if (posicionValida(fila - 1, columna - 1) && !tablero[fila - 1][columna - 1].esBomba()
						&& tablero[fila][columna].getNumMinas() == 0
						&& tablero[fila - 1][columna - 1].getEstado() == 2) {
					porVisitar.add(tablero[fila - 1][columna - 1]);
				}
				if (posicionValida(fila - 1, columna + 1) && !tablero[fila - 1][columna + 1].esBomba()
						&& tablero[fila][columna].getNumMinas() == 0
						&& tablero[fila - 1][columna + 1].getEstado() == 2) {
					porVisitar.add(tablero[fila - 1][columna + 1]);
				}
				if (posicionValida(fila, columna + 1) && !tablero[fila][columna + 1].esBomba()
						&& tablero[fila][columna].getNumMinas() == 0 && tablero[fila][columna + 1].getEstado() == 2) {
					porVisitar.add(tablero[fila][columna + 1]);
				}
				if (posicionValida(fila, columna - 1) && !tablero[fila][columna - 1].esBomba()
						&& tablero[fila][columna].getNumMinas() == 0 && tablero[fila][columna - 1].getEstado() == 2) {
					porVisitar.add(tablero[fila][columna - 1]);
				}
				// Una vez comprobadas las casillas de al rededor, buscaremos todas las casillas
				// que no tengan minas al rededor, contiguas a la original
				while (!porVisitar.isEmpty()) {

					act = porVisitar.remove(); // Sacamos la casilla de la lista de pendientes
					listaCasillas.anadirCasilla(act.getFila() + "" + act.getcolumna() + "", act);
					act.cambiarEstado(new Abierto()); // La abrimos
					fila = act.getFila(); // Cogemos su fila
					columna = act.getcolumna(); // Cogemos su columna

					if (posicionValida(fila + 1, columna) && !tablero[fila + 1][columna].esBomba()
							&& tablero[fila][columna].getNumMinas() == 0 && tablero[fila + 1][columna].getEstado() == 2
							&& !listaCasillas.estaCasilla(act.getFila() + "" + act.getcolumna() + "")) {
						porVisitar.add(tablero[fila + 1][columna]);
					}
					if (posicionValida(fila + 1, columna - 1) && !tablero[fila + 1][columna - 1].esBomba()
							&& tablero[fila][columna].getNumMinas() == 0
							&& tablero[fila + 1][columna - 1].getEstado() == 2
							&& !listaCasillas.estaCasilla(act.getFila() + "" + act.getcolumna() + "")) {
						porVisitar.add(tablero[fila + 1][columna - 1]);
					}
					if (posicionValida(fila + 1, columna + 1) && !tablero[fila + 1][columna + 1].esBomba()
							&& tablero[fila][columna].getNumMinas() == 0
							&& tablero[fila + 1][columna + 1].getEstado() == 2
							&& !listaCasillas.estaCasilla(act.getFila() + "" + act.getcolumna() + "")) {
						porVisitar.add(tablero[fila + 1][columna + 1]);
					}
					if (posicionValida(fila - 1, columna) && !tablero[fila - 1][columna].esBomba()
							&& tablero[fila][columna].getNumMinas() == 0 && tablero[fila - 1][columna].getEstado() == 2
							&& !listaCasillas.estaCasilla(act.getFila() + "" + act.getcolumna() + "")) {
						porVisitar.add(tablero[fila - 1][columna]);
					}
					if (posicionValida(fila - 1, columna - 1) && !tablero[fila - 1][columna - 1].esBomba()
							&& tablero[fila][columna].getNumMinas() == 0
							&& tablero[fila - 1][columna - 1].getEstado() == 2
							&& !listaCasillas.estaCasilla(act.getFila() + "" + act.getcolumna() + "")) {
						porVisitar.add(tablero[fila - 1][columna - 1]);
					}
					if (posicionValida(fila - 1, columna + 1) && !tablero[fila - 1][columna + 1].esBomba()
							&& tablero[fila][columna].getNumMinas() == 0
							&& tablero[fila - 1][columna + 1].getEstado() == 2
							&& !listaCasillas.estaCasilla(act.getFila() + "" + act.getcolumna() + "")) {
						porVisitar.add(tablero[fila - 1][columna + 1]);
					}
					if (posicionValida(fila, columna + 1) && !tablero[fila][columna + 1].esBomba()
							&& tablero[fila][columna].getNumMinas() == 0 && tablero[fila][columna + 1].getEstado() == 2
							&& !listaCasillas.estaCasilla(act.getFila() + "" + act.getcolumna() + "")) {
						porVisitar.add(tablero[fila][columna + 1]);
					}
					if (posicionValida(fila, columna - 1) && !tablero[fila][columna - 1].esBomba()
							&& tablero[fila][columna].getNumMinas() == 0 && tablero[fila][columna - 1].getEstado() == 2
							&& !listaCasillas.estaCasilla(act.getFila() + "" + act.getcolumna() + "")) {
						porVisitar.add(tablero[fila][columna - 1]);
					}
				}
			}
		}
		return fin;
	}

	public boolean tableroEsBomba(int fila, int col) {
		boolean bomba = false;
		if(this.tablero[fila][col].esBomba()) {
			bomba = true;
		}
		return bomba;
	}
	public int getNumPos(int fila, int columna) {
		return (tablero[fila][columna]).getNumMinas();
	}

	public Casilla getCasilla(int fila, int columna) {
		return tablero[fila][columna];
	}

	public Integer getCasillaEstado(int fila, int columna) {
		return tablero[fila][columna].getEstado();
	}

	public Casilla[][] getMatriz() {
		return tablero;
	}

	public void getClickIzq(int Y, int X) {
		this.tablero[Y][X].clickIzq();
	}

	public void getClickDer(int Y, int X) {
		this.tablero[Y][X].clickDer();
	}

	public void eliminarTablero() {
		// TODO Auto-generated method stub
		tablero = null;

	}
}
