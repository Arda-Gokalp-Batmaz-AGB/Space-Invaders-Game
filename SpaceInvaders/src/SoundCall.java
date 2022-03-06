import java.io.IOException;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Allows to play sounds in the "sounds" folder depend on situation
 * @author Arda Gokalp Batmaz
 * @since Date:04.05.2021
 */
public class SoundCall {
	
	/**
	 * This method provide us to playing relevant sound
	 * @param soundName Name of the song which is going to call
	 * @param ms How many miliseconds music going to play
	 * @throws LineUnavailableException
	 * @throws IOException
	 * @throws UnsupportedAudioFileException
	 */
	public void soundList(String soundName) throws LineUnavailableException
	, IOException, UnsupportedAudioFileException
	{
		Clip clip;
		URL url = this.getClass().getResource("sounds/"+soundName+".wav");// Takes sound from the file
		AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
		clip = AudioSystem.getClip();

		clip.open(audioIn); 
		clip.start();
	}
	/**
	 * Method determines which sound is going to played depend on the parameter
	 * @param sound Name of the sound effect
	 */
	public void playSound(String sound)
	{
		// Depend on sound name calls soundList with an argument and plays a sound
		if(SpaceShip_Game.gameMuted==false)
		{
			try {
				if(sound.equals("shoot"))
				{
					soundList("shoot");
				}
				if(sound.equals("Explosion"))
				{
					soundList("Explosion");
				}
				if(sound.equals("MiniExplosion"))
				{
					soundList("MiniExplosion");
				}
				if(sound.equals("CountDown"))
				{
					soundList("CountDown");
				}
				if(sound.equals("supply"))
				{
					soundList("supply");
				}
			} catch (LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedAudioFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
