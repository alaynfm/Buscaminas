package codigo;

import javafx.scene.media.AudioClip;

/**
 * Clase utilizada para reproducir y parar la musica en el juego.
 * Utiliza javafx de java8.
 * @author TeamIGN
 *
 */
public class Musica{
	
	private static AudioClip cancionTablero;
	
	private Musica() {

	}
	
	public static void playMusica()
	{
	    try
	    {
	    	cancionTablero = new AudioClip(Musica.class.getResource("/musica/VictoryFanfare.wav").toString());
			cancionTablero.play();
	    }
	    catch (Exception exc)
	    {
	        exc.printStackTrace(System.out);
	    }
	}
	
}
