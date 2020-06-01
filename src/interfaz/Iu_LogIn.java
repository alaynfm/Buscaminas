package interfaz;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import codigo.Musica;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class Iu_LogIn extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private static Iu_LogIn miLogin = new Iu_LogIn();
	private Image imagenFondo;
	private URL fondo;
	private Musica musica = null;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Iu_LogIn frame = new Iu_LogIn();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static Iu_LogIn getMiLogin() {
		return miLogin;
	}

	/**
	 * Create the frame.
	 */
	private Iu_LogIn() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.setTitle("Registrar");
		ImageIcon imagen = new ImageIcon("img/mine.png");
		this.setIconImage(imagen.getImage());
		
	    
	  
		setResizable(false);
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.EAST);

		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.SOUTH);

		JPanel panel_3 = new JPanel();
		contentPane.add(panel_3, BorderLayout.WEST);

		JPanel panel_4 = new JPanel();
		contentPane.add(panel_4, BorderLayout.CENTER);
		panel_4.setLayout(new GridLayout(3, 0, 0, 0));

		JPanel panel_6 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_6.getLayout();
		flowLayout.setHgap(34);
		flowLayout.setVgap(56);
		panel_4.add(panel_6);

		JLabel lblNewLabel = new JLabel("Nombre");
		panel_6.add(lblNewLabel);

		textField = new JTextField();
		panel_6.add(textField);
		textField.setColumns(10);

		JPanel panel_7 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_7.getLayout();
		flowLayout_1.setVgap(45);
		panel_4.add(panel_7);

		JButton btnNewButton = new JButton("Aceptar");
		btnNewButton.setBackground(Color.LIGHT_GRAY);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Iu_Personalizar.getMiPartidaPersonalizada().setVisible(true);
				Iu_Personalizar.getMiPartidaPersonalizada().setUsuario(textField.getText());
				setVisible(false);

			}
		});
		panel_7.add(btnNewButton);

		JPanel panel_5 = new JPanel();
		panel_4.add(panel_5);

		JButton btnNewButton_1 = new JButton("Ver Highscores");
		btnNewButton_1.setBackground(Color.LIGHT_GRAY);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Puntuacion.getMiPuntuacion().setVisible(true);
				(new Iu_HighScores()).setVisible(true);
				setVisible(false);
			}
		});
		panel_5.add(btnNewButton_1);
		paint(getGraphics());
	}
	
}
