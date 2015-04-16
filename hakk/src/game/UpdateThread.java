package game;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

import networking.Client;

public class UpdateThread extends Thread {
	private HakkStage stage;
	private Client client;

	public UpdateThread(Client client, HakkStage stage) {
		this.client = client;
		this.stage = stage;
		stage.add(client.getAddress(), new Player(stage));

	}

	public void run() {
		new Timer().schedule(new TimerTask() {
			public void run() {
				stage.update(client);
			}
		}, 0, 17);
	}
}
