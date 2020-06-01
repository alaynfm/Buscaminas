package interfaz;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import java.awt.FlowLayout;
import java.awt.Color;

public class Iu_Personalizar extends JFrame {

	private JPanel contentPane;
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_3;
	private JPanel panel_4;
	private JPanel panel_5;
	private JPanel panel_6;
	private JPanel panel_7;
	private JLabel lblFilas;
	private JLabel lblColumnas;
	private JButton btnOk;
	private JPanel panel_9;
	private JPanel panel_10;

	private static Iu_Personalizar miPartidaPersonalizada = new Iu_Personalizar();
	private JRadioButton rdbtnPersonalizar;
	private JRadioButton rdbtnx;
	private JRadioButton rdbtnx_1;
	private JRadioButton rdbtnx_2;
	private JLabel lblInformacion;
	private JTextField textFilas;
	private JTextField textColumnas;

	private String usuario;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Iu_Personalizar frame = new Iu_Personalizar();
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
	private Iu_Personalizar() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 240);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getPanel(), BorderLayout.NORTH);
		contentPane.add(getPanel_1(), BorderLayout.EAST);
		contentPane.add(getPanel_2(), BorderLayout.SOUTH);
		contentPane.add(getPanel_3(), BorderLayout.WEST);
		contentPane.add(getPanel_4(), BorderLayout.CENTER);
		ButtonGroup bg = new ButtonGroup();
		bg.add(rdbtnPersonalizar);
		bg.add(rdbtnx);
		bg.add(rdbtnx_1);
		bg.add(rdbtnx_2);
		this.setTitle("Ajustes de Partida");

		setLocationRelativeTo(null);
		setResizable(false);

		rdbtnx.setSelected(true); // Ponemos q el facil este seleccionado por defecto

		this.setTitle("Ajustes Partida Personalizada");
		ImageIcon imagen = new ImageIcon("img/mine.png");
		this.setIconImage(imagen.getImage());

	}

	public static Iu_Personalizar getMiPartidaPersonalizada() {
		return miPartidaPersonalizada;
	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
		}
		return panel;
	}

	private JPanel getPanel_1() {
		if (panel_1 == null) {
			panel_1 = new JPanel();
		}
		return panel_1;
	}

	private JPanel getPanel_2() {
		if (panel_2 == null) {
			panel_2 = new JPanel();
		}
		return panel_2;
	}

	private JPanel getPanel_3() {
		if (panel_3 == null) {
			panel_3 = new JPanel();
		}
		return panel_3;
	}

	private JPanel getPanel_4() {
		if (panel_4 == null) {
			panel_4 = new JPanel();
			panel_4.setLayout(new GridLayout(0, 1, 0, 0));
			panel_4.add(getPanel_7());
			panel_4.add(getPanel_5());
			panel_4.add(getPanel_6());
			panel_4.add(getPanel_9());
			panel_4.add(getPanel_10());
		}
		return panel_4;
	}

	private JPanel getPanel_5() {
		if (panel_5 == null) {
			panel_5 = new JPanel();
			panel_5.add(getRdbtnPersonalizar());
		}
		return panel_5;
	}

	private JPanel getPanel_6() {
		if (panel_6 == null) {
			panel_6 = new JPanel();
			panel_6.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 5));
			panel_6.add(getLblFilas());
			panel_6.add(getTextFilas());
			panel_6.add(getLblColumnas());
			panel_6.add(getTextColumnas());
			setPersonalizable(false);

		}
		return panel_6;
	}

	private JPanel getPanel_7() {
		if (panel_7 == null) {
			panel_7 = new JPanel();
			FlowLayout flowLayout = (FlowLayout) panel_7.getLayout();
			panel_7.add(getRdbtnx());
			panel_7.add(getRdbtnx_1());
			panel_7.add(getRdbtnx_2());
		}
		return panel_7;
	}

	private JLabel getLblFilas() {
		if (lblFilas == null) {
			lblFilas = new JLabel("Filas");
			lblFilas.setHorizontalAlignment(SwingConstants.CENTER);
			lblFilas.setVerticalAlignment(SwingConstants.CENTER);
		}
		return lblFilas;
	}

	private JLabel getLblColumnas() {
		if (lblColumnas == null) {
			lblColumnas = new JLabel("Columnas");
			lblColumnas.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return lblColumnas;
	}

	private JLabel getLblInformacion() {
		if (lblInformacion == null) {
			lblInformacion = new JLabel("informacion");
			lblInformacion.setVisible(false);
		}
		return lblInformacion;
	}

	private JPanel getPanel_9() {
		if (panel_9 == null) {
			panel_9 = new JPanel();
			panel_9.add(getLblInformacion());
		}
		return panel_9;
	}

	private JPanel getPanel_10() {
		if (panel_10 == null) {
			panel_10 = new JPanel();
			panel_10.add(getBtnOk());
		}
		return panel_10;
	}

	private JRadioButton getRdbtnPersonalizar() {
		if (rdbtnPersonalizar == null) {
			rdbtnPersonalizar = new JRadioButton("Nivel Personalizado");
			rdbtnPersonalizar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					setPersonalizable(true);
					setTxtDificultad("Tu eliges");

				}
			});
			rdbtnPersonalizar.setHorizontalAlignment(SwingConstants.LEFT);
		}
		return rdbtnPersonalizar;
	}

	private JRadioButton getRdbtnx() {
		if (rdbtnx == null) {
			rdbtnx = new JRadioButton("Nivel 1: 7x10");
			rdbtnx.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setPersonalizable(false);
					setTxtDificultad("Fácil");

				}
			});
		}
		return rdbtnx;
	}

	private JRadioButton getRdbtnx_1() {
		if (rdbtnx_1 == null) {
			rdbtnx_1 = new JRadioButton("Nivel 2: 10x15");
			rdbtnx_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setPersonalizable(false);
					setTxtDificultad("Medio");

				}
			});
		}
		return rdbtnx_1;
	}

	private JRadioButton getRdbtnx_2() {
		if (rdbtnx_2 == null) {
			rdbtnx_2 = new JRadioButton("Nivel 3: 12x25");
			rdbtnx_2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setPersonalizable(false);
					setTxtDificultad("Dificil");

				}
			});
		}
		return rdbtnx_2;
	}

//la interfaz Iu_Personalizada solo se comunica con la interfaz Iu_partida para decir de cuanto crear el tablero de botones
	/*
	 * Tamaño minimo del ablero 4X4
	 * 
	 * 
	 */

	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton("Ok");
			btnOk.setBackground(Color.LIGHT_GRAY);
			btnOk.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String filas;
					String columnas;
					String bombas;

					if (rdbtnPersonalizar.isSelected()) {

						Integer f, c, b, n;
						f = Integer.parseInt(getTextFilas().getText());
						c = Integer.parseInt(getTextColumnas().getText());

						// Filas
						if (f < 7) {
							f = 7;
						} else if (f > 30) {
							f = 30;
						}
						// Columnas
						if (c < 8) {
							c = 8;
						} else if (c > 30) {
							c = 30;
						}
						// Bombas
						b = (f * c) / 5;

						filas = String.valueOf(f);
						columnas = String.valueOf(c);
						bombas = String.valueOf(b);

					} else if (rdbtnx.isSelected()) {
						filas = 7 + "";
						columnas = 10 + "";
						bombas = 10 + "";

					} else if (rdbtnx_1.isSelected()) {
						filas = 10 + "";
						columnas = 15 + "";
						bombas = 30 + "";

					} else {
						filas = 12 + "";
						columnas = 25 + "";
						bombas = 75 + "";
					}

					try {

						int f = Integer.parseInt(filas);
						int c = Integer.parseInt(columnas);
						int b = Integer.parseInt(bombas);
						String usu = usuario;
						if (f > 0 && c > 0 && (b < f * c && b > 0)) {
							(new Iu_Juego()).crearPartidaPersonalizada(f, c, b, usu);

							setVisible(false);
						} else {
							JOptionPane.showMessageDialog(null,
									"Valores erroneos, por favor comprueba los valores de nuevo", "Error",
									JOptionPane.ERROR_MESSAGE);
							restablecerValoresDefabrica();
						}

					} catch (NumberFormatException excepcion) {
						// System.out.println("Por favor introduce numeros");
						JOptionPane.showMessageDialog(null, "Por favor introduce n�meros", "Error",
								JOptionPane.ERROR_MESSAGE);
					}

				}
			});
		}
		return btnOk;
	}

	private void setPersonalizable(boolean estado) {
		getLblFilas().setEnabled(estado);
		getLblColumnas().setEnabled(estado);
	}

	private void setTxtDificultad(String txt) {

		if (txt != null) {
			getLblInformacion().setText("Dificultad : " + txt);
		} else {
			getLblInformacion().setText("Dificultad : Ninguna");
		}
		getLblInformacion().setVisible(true);
	}

	private void restablecerValoresDefabrica() {
		getTextFilas().setText(null);
		getTextColumnas().setText(null);
	}

	private JTextField getTextFilas() {
		if (textFilas == null) {
			textFilas = new JTextField();
			textFilas.setColumns(10);
		}
		return textFilas;
	}

	private JTextField getTextColumnas() {
		if (textColumnas == null) {
			textColumnas = new JTextField();
			textColumnas.setColumns(10);
		}
		return textColumnas;
	}

	public void setUsuario(String pUsuario) {
		usuario = pUsuario;
	}
}