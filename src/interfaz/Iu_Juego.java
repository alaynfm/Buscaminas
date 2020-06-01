package interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.Observable;
import java.util.Observer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import codigo.Casilla;
import codigo.Musica;
import codigo.Puntuacion;
import codigo.Tablero;

public class Iu_Juego extends JFrame implements Observer, ComponentListener {

	private JPanel contentPane;
	private JMenuBar menuBar;
	private JPanel panel_4;
	private JPanel panel_5;
	private JPanel panel_6;
	private JPanel panel_7;
	private JPanel panel_9;
	private JPanel panel_10;
	private JPanel panel_11;
	private JPanel panel_12;
	private JButton lblCarita;
	private JMenuItem facil;
	private JMenuItem medio;
	private JMenuItem dificil;
	private JMenuItem personal;
	private JMenuItem volverAEmpezar;
	private JMenuItem exit;
	private JLabel btnNewButton;
	private JLabel btnNewButton_1;
	private JLabel btnNewButton_2;
	private int cont = 0;
	private Timer timer;
	private JButton[][] tablero;
	private int fila;
	private int columna;
	private int bombas;
	private int casillasVacias;
	private int tamanoX;
	private int tamanoY;
	private String dificultad;
	private boolean primerClick; // Este booleano lo utilizamos para gestionar el primer click, si es true
									// generamos el tablero.
	private boolean finJuego;
	private String usuario;

	private JLabel lblTiempoC;
	private JLabel lblTiempoD;
	private JLabel lblTiempoU;
	private Musica musica;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Iu_Juego frame = new Iu_Juego();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Iu_Juego() {

		// Constructora del Iu_Juego

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 12 * 29, (12 * 29));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getPanel_4_1(), BorderLayout.CENTER);
		contentPane.add(getPanel_9(), BorderLayout.NORTH);
		contentPane.add(getPanel_10(), BorderLayout.SOUTH);
		contentPane.add(getPanel_11(), BorderLayout.WEST);
		contentPane.add(getPanel_12(), BorderLayout.EAST);

		// Para centrar frame en la mitad de la pantalla
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Partida
		this.setTitle("Buscaminas");
		ImageIcon imagen = new ImageIcon("img/greymine.png");
		this.setIconImage(imagen.getImage());

		// Para el tamaño de las casillas en la interfaz
		tamanoX = 30;
		tamanoY = 30;

		// Para el menu
		setJMenuBar(getMenuBar_1()); // Menu

		// Metodo para cada vez que se redimensiona la ventana se cambie el tamaño
		addComponentListener(this);
		primerClick = true;
		finJuego = false;
		musica = null;
	}

	/***********************************************************************
	 * Patron Observer *
	 ************************************************************************/
	@Override
	public void update(Observable o, Object arg) {

		if (o instanceof Casilla) {
			Casilla casilla = (Casilla) o;
			int x = casilla.getFila();
			int y = casilla.getcolumna();
			pintarPosicion(x, y);

			Boolean fin = Tablero.getTablero().destaparCasillas(x, y);
			if (fin) {
				perderPartida();
				timer.stop();
			}
			
		}
	}

	/***********************************************************************
	 * Creacion de tablero *
	 ************************************************************************/

	private void crearTablero(int filas, int columnas) {

		// Borramos el tablero creado;
		Tablero.getTablero().eliminarTablero();

		// Damos tamano al panel_4 donde van a estar los botones
		panel_4.setSize(((columnas) * tamanoX), ((filas) * tamanoY));

		// Damos tamano al panel principal
		setSize(panel_11.getWidth() + panel_12.getWidth() + panel_4.getWidth() + 26,
				panel_9.getHeight() + panel_10.getHeight() + panel_4.getHeight() + 80);

		// Iniciamos contador de las bombas
		contadorBombas();

		// Iniciamos contador de tiempo
		if (timer != null) {
			cont = 0;
			ImageIcon imgD = new ImageIcon("img/r0.png");
			java.awt.Image timerD = imgD.getImage();
			java.awt.Image sizeD = timerD.getScaledInstance(20, 25, 0);
			ImageIcon contador = new ImageIcon(sizeD);
			getLblTiempoC().setIcon(contador);
			getLblTiempoD().setIcon(contador);
			getLblTiempoU().setIcon(contador);

			redimensionarContadorTimer();
			timer.stop();
			timer.restart();
		}

		contadorTimer();

		// guardamos los datos por si tenemos que reiniciar
		fila = filas;
		columna = columnas;

		if (dificultad.equals("f")) {
			this.bombas = 10;
		} else if (this.dificultad.equals("m")) {
			this.bombas = 30;
		} else if (this.dificultad.equals("d")) {
			this.bombas = 75;
		} else {
			this.bombas = (fila * columna) / 5;
		}

		getPanel_4_1().removeAll();
		tablero = new JButton[fila][columna];

		// Para poder pasar como parametro la interfaz cuando hacemos click (patron
		// Observer)
		Iu_Juego interfazJuego = this;

		for (int f = 0; f < fila; f++) {
			for (int c = 0; c < columna; c++) {
				JButton jb = new JButton();
				jb.setBackground(Color.LIGHT_GRAY);
				jb.setBorderPainted(true);
				tablero[f][c] = jb;
				tablero[f][c].addMouseListener(new MouseAdapter() {

					// Para aplicar el patron estate state
					@Override
					public void mouseClicked(MouseEvent arg0) {
						if (!finJuego) {
							int j = (int) (jb.getX() / (tablero[0][1]).getX());
							int j2 = (int) (jb.getY() / (tablero[1][1].getY() - 6));

							if (arg0.getButton() == 1) {
								if (primerClick) {
									// Primera vez que hacemos getClick
									// generamos el tablero de casillas, haciendo que la posicion que hemos hecho
									// click no sea bomba
									Tablero.getTablero().generarTablero(tablero.length, tablero[0].length, bombas,
											interfazJuego, j2, j);
									casillasVacias = ((tablero.length * tablero[0].length) - bombas);
									primerClick = false;
									if (timer == null)
										iniciarTimer();
								}
								Tablero.getTablero().getClickIzq(j2, j);
								
							} else if (arg0.getButton() == 3) {
								if (primerClick) {
									//No hacemos nada el primer click tiene que ser izq
								} else {
									if (Tablero.getTablero().getCasillaEstado(j2, j) != 2 || bombas > 0) {
										Tablero.getTablero().getClickDer(j2, j);
										if (timer == null)
											iniciarTimer();
									}
								}
							}
						}
					}
				});
				getPanel_4_1().add(jb);

			} // for
		} // for

		// Metodo para redimensionar la interfaz
		ordenar();
	}

	/***********************************************************************
	 * Para redimensionar la interfaz
	 ************************************************************************/
	@Override
	public void componentResized(ComponentEvent arg0) {

		ordenar();
		redimensionarContadorBombas();
		redimensionarContadorTimer();

	}

	// Metodo para ajustar los botones a la ventana
	private void ordenar() {

		try {
			int anchoTotal = panel_4.getWidth();
			int altoTotal = panel_4.getHeight();
			int tx = anchoTotal / columna;
			int ty = altoTotal / fila;

			int x = 0;
			int y = 6;
			for (int f = 0; f < tablero.length; f++) {
				for (int c = 0; c < tablero[0].length; c++) {
					tablero[f][c].setBounds(x, y, tx, ty);
					x = x + tx;
				} // for
				x = 0;
				y = y + ty;

			} // for
			pintarTablero(tx, ty);

		} catch (Exception e) {
			// Si no podemos pintar no pasa nada
			// El tablero no estara generado todavia
		}

	}

	/***********************************************************************
	 * Metodos para pintar el tablero
	 ************************************************************************/

	private void pintarTablero(int tX, int tY) {
		// Metodo para pintar todo el tablero

		ImageIcon imagen = null;

		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[0].length; j++) {

				try {

					int estado = Tablero.getTablero().getCasillaEstado(i, j);
					int num = Tablero.getTablero().getNumPos(i, j);

					if (estado == 0) {
						if (num == -1) {
							imagen = new ImageIcon("img/mine.png");
							// Mensaje de que ha pulsado una mina, pierde la partida
						} else {
							imagen = new ImageIcon("img/" + num + ".png");
						}
					} else if (estado == 1 && this.bombas > 0) {
						imagen = new ImageIcon("img/flagged.png");
					} else {
						imagen = new ImageIcon("img/covered.png");
					}

				} catch (Exception e) {

					// Todavia no se ha generado el Tablero
					// Lo pintamos todo como covered
					imagen = new ImageIcon("img/covered.png");

				}

				java.awt.Image conversion = imagen.getImage();
				java.awt.Image tamano = conversion.getScaledInstance(tablero[0][0].getWidth(), tablero[0][0].getWidth(),
						0);
				ImageIcon fin = new ImageIcon(tamano);
				tablero[i][j].setIcon(fin);
			}
		}
	}

	private void pintarPosicion(int f, int c) {

		// metodo para pintar un boton
		/*
		 * Este metodo lo vamos a utilizar para el patron Observer Cada vez que una
		 * casilla cambia de estado la pintaremos con el nuevo estado
		 */

		int estado = Tablero.getTablero().getCasillaEstado(f, c);
		ImageIcon imagen = new ImageIcon("img/covered.png");
		;
		int num = Tablero.getTablero().getNumPos(f, c);
		if (estado == 2) {
			if (this.bombas >= 0) {
				imagen = new ImageIcon("img/covered.png");
				bombas++;
				contadorBombas();
			}
		} else if (estado == 1) {
			if (this.bombas > 0) {
				imagen = new ImageIcon("img/flagged.png");
				bombas--;
				contadorBombas();
			}
		} else if (estado == 0) {
			if (num == -1) {
				imagen = new ImageIcon("img/mine.png");
				bombas--;
				contadorBombas();
				// Mensaje de que ha pulsado una mina, pierde la partida
			} else {
				imagen = new ImageIcon("img/" + num + ".png");
				tablero[f][c].setEnabled(true);
				this.casillasVacias--; // restamos uno a las casillas vacias, para comprobar cuando se ha ganado el
										// juego
				if (casillasVacias == 0) { // si es 0, has ganado la partida
					ganarPartida();
					musica.playMusica();
				}
			}
		}

		java.awt.Image conversion = imagen.getImage();
		java.awt.Image tamano = conversion.getScaledInstance(tamanoX, tamanoY, 0);
		ImageIcon fin = new ImageIcon(tamano);
		tablero[f][c].setIcon(fin);
		actualizarTablero(getPanel_4_1());
	}

	/***********************************************************************
	 * Metodos Para el contador de bombas *
	 ************************************************************************/
	// Para poner el numero de bombas que hay en la interfaz
	private void contadorBombas() {

		if (bombas < 0)
			bombas = 0;

		int centenas = bombas / 100;
		int decenas = (bombas - (centenas * 100)) / 10;
		int unidades = bombas - (centenas * 100 + decenas * 10);

		ImageIcon img1 = new ImageIcon("img/r" + decenas + ".png");
		ImageIcon img2 = new ImageIcon("img/r" + unidades + ".png");
		ImageIcon img0 = new ImageIcon("img/r" + centenas + ".png");

		java.awt.Image conversion0 = img0.getImage();
		java.awt.Image tamano0 = conversion0.getScaledInstance(20, 25, 0);
		ImageIcon fin = new ImageIcon(tamano0);

		java.awt.Image conversion1 = img1.getImage();
		java.awt.Image tamano1 = conversion1.getScaledInstance(20, 25, 0);
		ImageIcon fin1 = new ImageIcon(tamano1);

		java.awt.Image conversion2 = img2.getImage();
		java.awt.Image tamano2 = conversion2.getScaledInstance(20, 25, 0);
		ImageIcon fin2 = new ImageIcon(tamano2);

		getBtnNewButton().setIcon(fin);
		getBtnNewButton_1().setIcon(fin1);
		getBtnNewButton_2().setIcon(fin2);

		redimensionarContadorBombas();

	}

	private void redimensionarContadorBombas() {
		int width = (getPanel_9().getWidth()) / 3;
		int inicio = (50 * width) / 100;

		getBtnNewButton().setBounds(inicio - 30, 1, 20, panel_9.getHeight());
		getBtnNewButton_1().setBounds(inicio - 10, 1, 20, panel_9.getHeight());
		getBtnNewButton_2().setBounds(inicio + 10, 1, 20, panel_9.getHeight());

	}

	/***********************************************************************
	 * Finalizar el Juego *
	 ************************************************************************/
	private void perderPartida() {
		timer.stop();

		for (int i = 0; i < tablero.length; i++) {

			for (int j = 0; j < tablero[0].length; j++) {

				// Recorremos todo el tablero
				Integer estado;
				ImageIcon imagen;
				estado = Tablero.getTablero().getCasillaEstado(i, j);

				// Comprobamos las casillas que son bomba y no estan marcadas con bandera
				if (Tablero.getTablero().tableroEsBomba(i, j)) {

					if (estado == 2) {
						imagen = new ImageIcon("img/greymine.png");
						java.awt.Image conversion = imagen.getImage();
						java.awt.Image tamano = conversion.getScaledInstance(tamanoX, tamanoY, 0);
						ImageIcon fin = new ImageIcon(tamano);
						tablero[i][j].setIcon(fin);
						actualizarTablero(getPanel_4_1());
					}

					// Comprobamos las casillas que no son bomba y estan marcadas con bandera
				} else if (estado == 1) {
					imagen = new ImageIcon("img/nomine.png");
					java.awt.Image conversion = imagen.getImage();
					java.awt.Image tamano = conversion.getScaledInstance(tamanoX, tamanoY, 0);
					ImageIcon fin = new ImageIcon(tamano);
					tablero[i][j].setIcon(fin);
					actualizarTablero(getPanel_4_1());
				}
			}
		}
		// Cambiamos el icono de la Carita
		ImageIcon caraPerder = new ImageIcon("img/deadsmiley.png");
		lblCarita.setIcon(caraPerder);
		actualizarTablero(getPanel_4_1());

		// Bloqueamos el tablero
		finJuego = true;
	}

	private void ganarPartida() {

		timer.stop();

		for (int i = 0; i < tablero.length; i++) {

			for (int j = 0; j < tablero[0].length; j++) {

				// Recorremos todo el tablero para comprobar, que casillas no estan marcadas con
				// bandera, y son minas, para marcarlas
				Integer estado;
				ImageIcon imagen;
				estado = Tablero.getTablero().getCasillaEstado(i, j);

				if (Tablero.getTablero().tableroEsBomba(i, j)) {

					if (estado == 2) {
						imagen = new ImageIcon("img/flagged.png");
						java.awt.Image conversion = imagen.getImage();
						java.awt.Image tamano = conversion.getScaledInstance(tamanoX, tamanoY, 0);
						ImageIcon fin = new ImageIcon(tamano);
						tablero[i][j].setIcon(fin);
						actualizarTablero(getPanel_4_1());
					}
				}
			}
		}
		// Cambiamos el icono de la Carita
		ImageIcon caraPerder = new ImageIcon("img/sunglasses.png");
		lblCarita.setIcon(caraPerder);
		actualizarTablero(getPanel_4_1());

		// Calculamos la puntuacion del ganador, y la introducimos en la BD
		String usu = usuario; // Seteamos el usuario ganador

		Integer nivel = 0; // Seteamos el nivel y dificultad
		String dif = "n";
		if (dificultad.equals("f")) {
			nivel = 1;
			dif = "Facil";
		} else if (dificultad.equals("m")) {
			nivel = 2;
			dif = "Medio";
		} else if (dificultad.equals("d")) {
			nivel = 3;
			dif = "Dificil";
		} else if (dificultad.equals("p")) {
			nivel = 4;
			dif = "Personalizado";
		}
		nivel = nivel * 100;

		Integer puntuacion = ((fila * columna) * nivel) / cont; // Seteamos la puntuaci�n ganadora

		Puntuacion.getPuntuacion().guardarFichero(dif, usuario, puntuacion, cont);

		// Bloqueamos el tablero
		finJuego = true;
	}

	/***********************************************************************
	 * Metodos para el timer *
	 ************************************************************************/

	private Timer iniciarTimer() {
		// Inicia el contador del timer

		cont = 0;

		if (timer == null) {
			timer = new Timer(1000, new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					cont++;
					contadorTimer();
				}
			});
			timer.start();
		}
		return timer;
	}

	private void contadorTimer() {

		int centenas = cont / 100;
		int decenas = (cont - (centenas * 100)) / 10;
		int unidades = cont - (centenas * 100 + decenas * 10);

		ImageIcon imgD = new ImageIcon("img/r" + decenas + ".png");
		ImageIcon imgU = new ImageIcon("img/r" + unidades + ".png");
		ImageIcon imgC = new ImageIcon("img/r" + centenas + ".png");

		java.awt.Image timerC = imgC.getImage();
		java.awt.Image sizeC = timerC.getScaledInstance(20, 25, 0);
		ImageIcon centena = new ImageIcon(sizeC);

		java.awt.Image timerD = imgD.getImage();
		java.awt.Image sizeD = timerD.getScaledInstance(20, 25, 0);
		ImageIcon decena = new ImageIcon(sizeD);

		java.awt.Image timerU = imgU.getImage();
		java.awt.Image sizeU = timerU.getScaledInstance(20, 25, 0);
		ImageIcon unidad = new ImageIcon(sizeU);

		getLblTiempoC().setIcon(centena);
		getLblTiempoD().setIcon(decena);
		getLblTiempoU().setIcon(unidad);

		redimensionarContadorTimer();

	}

	private void redimensionarContadorTimer() {

		int width = (getPanel_9().getWidth()) / 3;
		int inicio = (50 * width) / 100;

		getLblTiempoC().setBounds(inicio - 30, 1, 20, panel_9.getHeight());
		getLblTiempoD().setBounds(inicio - 10, 1, 20, panel_9.getHeight());
		getLblTiempoU().setBounds(inicio + 10, 1, 20, panel_9.getHeight());

	}

	/***********************************************************************
	 * Metodos fanfarria *
	 ************************************************************************/
	/*
	 * private void fanfarre() { AudioInputStream musica; try { musica =
	 * AudioSystem.getAudioInputStream(this.getClass().getResource(
	 * "musica/VictoryFanfare.wav")); //AudioStream audio = new AudioStream(musica);
	 * //AudioPlayer.player.start(); Clip clip = AudioSystem.getClip();
	 * clip.start(); }catch(Exception e) { e.printStackTrace(); } }
	 */

	/***********************************************************************
	 * Metodos para la partida personalizada *
	 ************************************************************************/
	public void crearPartidaPersonalizada(int f, int c, int b, String pUsuario) {

		this.usuario = pUsuario;
		bombas = b;
		if (bombas == 10)
			dificultad = "f";

		else if (bombas == 30)
			dificultad = "m";
		else if (bombas == 75)
			dificultad = "d";
		else
			dificultad = "p";
		crearTablero(f, c);

		ordenar();
		Iu_Personalizar.getMiPartidaPersonalizada().setVisible(false);
		// Hemos decidido que guarde la informacion, por lo que no hay que hacer dispose
		setVisible(true);

	}

	/***********************************************************************
	 * Para reiniciar la partida, mismos parametros *
	 ************************************************************************/

	private JButton getLblCarita() {
		if (lblCarita == null) {
			lblCarita = new JButton("");
			lblCarita.setBackground(Color.LIGHT_GRAY);
			ImageIcon icon = new ImageIcon("img/smiley.png");
			lblCarita.setIcon(icon);
			lblCarita.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					// Resetea la cara
					lblCarita.setBackground(Color.LIGHT_GRAY);
					ImageIcon icon = new ImageIcon("img/smiley.png");
					lblCarita.setIcon(icon);

					// creamos una nueva Partida
					primerClick = true;
					finJuego = false;
					crearTablero(fila, columna);

					// Volvemos a activar el primer click

					ordenar();
					contadorBombas();

					if (timer != null)
						timer.stop();
					timer = null;

				}
			});

		}

		return lblCarita;
	}

	/***********************************************************************
	 * labels, JButton, jTxext ..... *
	 ************************************************************************/

	@Override
	public void componentHidden(ComponentEvent arg0) {

	}

	@Override
	public void componentMoved(ComponentEvent arg0) {

	}

	@Override
	public void componentShown(ComponentEvent arg0) {

	}

	private JMenuBar getMenuBar_1() {

		if (menuBar == null) {

			menuBar = new JMenuBar();
			JMenu juego = new JMenu();
			juego.setText("Juego");
			JMenu ayuda = new JMenu();
			ayuda.setText("Ayuda");

			this.setJMenuBar(menuBar);
			juego.add(getFacil());
			juego.add(getMedio());
			juego.add(getDificil());
			juego.add(getPersonalizada());
			juego.add(getVolver());

			ayuda.add(getExit());
			menuBar.add(juego);
			menuBar.add(ayuda);
		}
		return menuBar;
	}

	private JMenuItem getFacil() {
		if (facil == null) {
			facil = new JMenuItem();
			facil.setText("Facil");
			facil.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					bombas = 10;
					crearTablero(7, 10);

				}
			});
		}
		return facil;
	}

	private JMenuItem getMedio() {
		if (medio == null) {
			medio = new JMenuItem();
			medio.setText("Medio");
			medio.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					bombas = 30;
					crearTablero(10, 15);

				}
			});
		}
		return medio;
	}

	private JMenuItem getDificil() {
		if (dificil == null) {
			dificil = new JMenuItem();
			dificil.setText("Dificil");
			dificil.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					bombas = 75;
					crearTablero(12, 25);

				}
			});
		}
		return dificil;
	}

	private JMenuItem getPersonalizada() {
		if (personal == null) {
			personal = new JMenuItem();
			personal.setText("Personalizada");
			personal.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// LLamar a un nuevo jFrame para crear la partida personalizada
					dispose();
					Iu_Personalizar.getMiPartidaPersonalizada().setVisible(true);

				}
			});

		}
		return personal;
	}

	private JMenuItem getVolver() {
		if (volverAEmpezar == null) {
			volverAEmpezar = new JMenuItem();
			volverAEmpezar.setText("Nueva Partida");
			volverAEmpezar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					crearTablero(fila, columna);
					bombas = (fila * columna) / 5;
					contadorBombas();
				}
			});
		}
		return volverAEmpezar;
	}

	private JMenuItem getExit() {
		if (exit == null) {
			exit = new JMenuItem();
			exit.setText("Salir");
			exit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					dispose();
					Iu_LogIn.getMiLogin().setVisible(true);
				}
			});
		}
		return exit;
	}

	private JPanel getPanel_4_1() {
		if (panel_4 == null) {
			panel_4 = new JPanel();
			panel_4.setBackground(Color.WHITE);
			panel_4.setLayout(null);
		}
		return panel_4;
	}

	private JPanel getPanel_9() {
		if (panel_9 == null) {
			panel_9 = new JPanel();
			panel_9.setSize(getWidth(), 10);
			panel_9.setBorder(null);
			panel_9.setLayout(new GridLayout(0, 3, 0, 0));
			panel_9.add(getPanel_5());
			panel_9.add(getPanel_6());
			panel_9.add(getPanel_7());
		}
		return panel_9;
	}

	private JPanel getPanel_5() {
		if (panel_5 == null) {
			panel_5 = new JPanel();
			panel_5.setBackground(Color.LIGHT_GRAY);
			panel_5.setBorder(null);
			panel_5.setLayout(null);
			panel_5.add(getBtnNewButton());
			panel_5.add(getBtnNewButton_1());
			panel_5.add(getBtnNewButton_2());
		}
		return panel_5;
	}

	private JPanel getPanel_6() {
		if (panel_6 == null) {
			panel_6 = new JPanel();
			panel_6.setBackground(Color.LIGHT_GRAY);

			panel_6.add(getLblCarita());

		}
		return panel_6;
	}

	private JPanel getPanel_7() {
		if (panel_7 == null) {
			panel_7 = new JPanel();
			panel_7.setBackground(Color.LIGHT_GRAY);
			panel_7.setBorder(null);
			panel_7.setLayout(null);
			panel_7.add(getLblTiempoC());
			panel_7.add(getLblTiempoD());
			panel_7.add(getLblTiempoU());

		}
		return panel_7;
	}

	private JPanel getPanel_10() {
		if (panel_10 == null) {
			panel_10 = new JPanel();
			panel_10.setSize(getWidth(), 10);
		}
		return panel_10;
	}

	private JPanel getPanel_11() {
		if (panel_11 == null) {
			panel_11 = new JPanel();
			panel_11.setSize(10, getHeight());
		}
		return panel_11;
	}

	private JPanel getPanel_12() {
		if (panel_12 == null) {
			panel_12 = new JPanel();
			panel_12.setSize(10, getHeight());
		}
		return panel_12;
	}

	private JLabel getBtnNewButton() {
		if (btnNewButton == null) {
			btnNewButton = new JLabel("");
			btnNewButton.setBackground(Color.LIGHT_GRAY);
			btnNewButton.setBounds(3, 1, 14, 23);
			btnNewButton.setEnabled(true);

		}
		return btnNewButton;
	}

	private JLabel getBtnNewButton_1() {
		if (btnNewButton_1 == null) {
			btnNewButton_1 = new JLabel("");
			btnNewButton_1.setBackground(Color.LIGHT_GRAY);
			btnNewButton_1.setBounds(17, 1, 14, 23);
			btnNewButton_1.setEnabled(true);

		}
		return btnNewButton_1;
	}

	private JLabel getBtnNewButton_2() {
		if (btnNewButton_2 == null) {
			btnNewButton_2 = new JLabel("");
			btnNewButton_2.setBackground(Color.LIGHT_GRAY);
			btnNewButton_2.setBounds(31, 1, 14, 23);
			btnNewButton_2.setEnabled(true);
		}
		return btnNewButton_2;
	}

	private JLabel getLblTiempoC() {
		if (lblTiempoC == null) {
			lblTiempoC = new JLabel("");
			lblTiempoC.setBackground(Color.LIGHT_GRAY);
			lblTiempoC.setBounds(21, 1, 14, 23);
			lblTiempoC.setEnabled(true);
		}
		return lblTiempoC;
	}

	private JLabel getLblTiempoD() {
		if (lblTiempoD == null) {
			lblTiempoD = new JLabel("");
			lblTiempoD.setBackground(Color.LIGHT_GRAY);
			lblTiempoD.setBounds(45, 1, 14, 23);
			lblTiempoD.setEnabled(true);
		}
		return lblTiempoD;
	}

	private JLabel getLblTiempoU() {
		if (lblTiempoU == null) {
			lblTiempoU = new JLabel("");
			lblTiempoU.setBackground(Color.LIGHT_GRAY);
			lblTiempoU.setBounds(62, 1, 14, 23);
			lblTiempoU.setEnabled(true);
		}
		return lblTiempoU;
	}

	private void actualizarTablero(JPanel panel) {
		SwingUtilities.updateComponentTreeUI(panel);
	}
}
