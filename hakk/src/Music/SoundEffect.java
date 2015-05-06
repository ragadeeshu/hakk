package Music;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

import javazoom.jl.player.Player;

public class SoundEffect extends Thread {

	private String fileLocation;
	private Player player;

	public SoundEffect(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	public synchronized void notifyPlayer() {
		notifyAll();
	}

	public synchronized void play() {
		try {
			FileInputStream fis = new FileInputStream(fileLocation);
			BufferedInputStream bis = new BufferedInputStream(fis);
			player = new Player(bis);
			player.play();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while (true) {
			synchronized (this) {
				try {
					wait();
					play();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}
