package codegame;

import java.io.File;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Siren implements Runnable {
	
//	Thread
	public Thread thread = new Thread(this);
	
//	Sounds
	private AudioInputStream ai;
	private File wavFile = new File("C:\\Users\\ceder\\Downloads\\Woop Woop-SoundBible.com-198943467.wav");
	private Clip clip;
	
	public Siren () {
		
	}

	@Override
	public void run() {
		try {
			ai = AudioSystem.getAudioInputStream(wavFile);
			clip = AudioSystem.getClip();
			clip.open(ai);
			clip.start();
			thread.sleep(2000);
			clip.close();
			
		} catch (Exception e ) {
			e.printStackTrace();
		}
		
	}
  
}
