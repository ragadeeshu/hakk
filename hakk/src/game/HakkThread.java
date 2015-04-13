package game;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

import networking.Client;

public class HakkThread extends Thread {
	private HakkStage stage;
	private Client client;

	public HakkThread(Client client) {
		this.client = client;
	}

	public void run() {
		JFrame frame = new JFrame("hakk");
		stage = new HakkStage();
		frame.add(stage);
		stage.add(client.getAddress(), new Player(stage));
		frame.setSize(900, 500);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		new Timer().schedule(new TimerTask() {

			public void run() {
				stage.update(client);
				stage.doPhysics();
				stage.repaint();
			}
		}, 0, 17);
	}
}
