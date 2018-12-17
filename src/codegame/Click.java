package codegame;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Click implements Runnable {
	
//	Thread
	public Thread thread = new Thread(this);
	
//	Sounds
	private AudioInputStream ai;
	private File wavFile = new File("C:\\Users\\ceder\\Downloads\\light-switch-pull-chain-daniel_simon.wav");
	private Clip clip;
	
	public Click () {
		
	}

	@Override
	public void run() {
		try {
			ai = AudioSystem.getAudioInputStream(wavFile);
			clip = AudioSystem.getClip();
			clip.open(ai);
			clip.start();
			thread.sleep(500);
			clip.close();
			
		} catch (Exception e ) {
			e.printStackTrace();
		}
		
	}
  

}
