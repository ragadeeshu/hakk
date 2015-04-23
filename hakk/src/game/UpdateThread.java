package game;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

import networking.Client;

public class UpdateThread extends Thread {
	private HakkStage stage;
	private Client client;

	public UpdateThread(Client client, HakkStage stage, String playerName) {
		this.client = client;
		this.stage = stage;
		stage.addCharacter(client.getAddress(), new Player(stage, playerName));
		stage.addSword(client.getAddress(), new Sword(400, 220));
		stage.addName(client.getAddress(), playerName);
	}

	public void run() {
		new Timer().schedule(new TimerTask() {
			public void run() {
				stage.update(client);
			}
		}, 0, 17);
	}
}
