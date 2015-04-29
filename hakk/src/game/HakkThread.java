package game;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

public class HakkThread extends Thread {
	private HakkStage stage;

	public HakkThread(HakkStage stage) {
		this.stage = stage;
	}

	public void run() {
		JFrame frame = new JFrame("hakk");
		frame.add(stage);
		frame.setSize(HakkStage.WIDTH, HakkStage.HEIGHT);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		new Timer().schedule(new TimerTask() {

			public void run() {
				stage.doPhysics();
				stage.repaint();
			}
		}, 0, 17);
	}
}
