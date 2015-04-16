package game;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

import networking.Client;

public class HakkThread extends Thread {
	private HakkStage stage;

	public HakkThread(HakkStage stage) {
		this.stage = stage;
	}

	public void run() {
		JFrame frame = new JFrame("hakk");
		frame.add(stage);
		frame.setSize(900, 500);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		new Timer().schedule(new TimerTask() {

			public void run() {
				stage.doPhysics();
				stage.repaint();
			}
		}, 0, 17);
	}
}
