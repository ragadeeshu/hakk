package game;

import java.awt.image.BufferStrategy;
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
		frame.createBufferStrategy(2);
		final BufferStrategy strategy = frame.getBufferStrategy();
		
		new Timer().scheduleAtFixedRate(new TimerTask() {

			public void run() {
				stage.doPhysics();
				stage.draw(strategy);
			}
		}, 0, 17);
	}
}
