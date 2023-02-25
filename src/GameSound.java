/*
 * Ishanvi Kommula
 * GameSound.java
 * class that plays the sound of the clock
 */
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;


public class GameSound
{
	Clip clip;
	void playMusic(String musicLocation) //plays the cuckoo sound
	{
		try
		{
			File musicPath = new File(musicLocation);
			if(musicPath.exists())
			{
				AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
				clip = AudioSystem.getClip();
				clip.open(audioInput);
				clip.start();				
				long clipTimePosition = clip.getMicrosecondPosition();
				clip.stop();
				
				clip.setMicrosecondPosition(clipTimePosition);
				clip.start();
				
			}
			else
			{
				System.out.println("Can't find file");
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
 
