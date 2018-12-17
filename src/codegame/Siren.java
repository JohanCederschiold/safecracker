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
	private Clip clip;
	
	public Siren () {
		
	}

	@Override
	public void run() {
		try {
			ai = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource("alarm.wav"));
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
