package Music;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

import javazoom.jl.player.Player;

public class MusicPlayer extends Thread {

	private String fileLocation;
	private boolean loop;
	private Player player;

	public MusicPlayer(String fileLocation, boolean loop) {
		this.fileLocation = fileLocation;
		this.loop = loop;
	}

	public void run() {
		try {
			do {
				InputStream fis = getClass().getResourceAsStream(fileLocation);
				BufferedInputStream bis = new BufferedInputStream(fis);
				player = new Player(bis);
				player.play();
			} while (loop);
		} catch (Exception ioe) {
			ioe.printStackTrace();
		}
	}

	public void close() {
		loop = false;
		player.close();
		this.interrupt();
	}
}