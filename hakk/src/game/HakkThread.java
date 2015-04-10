package game;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

public class HakkThread extends Thread {
	private HakkStage stage;

	public HakkThread() {
	}

	public void run() {
		JFrame frame = new JFrame("hakk");
		stage = new HakkStage();
		frame.add(stage);
		stage.add(new Player(stage));
		frame.setSize(800, 400);
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
