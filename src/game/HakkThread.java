package game;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

public class HakkThread extends Thread {
	private HakkStage stage;

	public HakkThread() {
		stage = new HakkStage();
	}

	public void run() {
		JFrame frame = new JFrame("Sample Frame");
		frame.add(stage);
		frame.setSize(800, 400);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		new Timer().schedule(new TimerTask() {

			public void run() {
				stage.moveBall();
				stage.repaint();
			}
		}, 0, 17);
	}
}
