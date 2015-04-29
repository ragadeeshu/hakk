package graphics;

import game.HakkStage;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class Level {
	protected int offSetX;
	protected int offSetY;
	protected int stageWidth;

	protected BufferedImage background;
	protected BufferedImage ground;
	protected BufferedImage foreground;

	public abstract void drawBackground(Graphics2D g2d);

	public abstract void drawGround(Graphics2D g2d);

	public abstract void drawForeground(Graphics2D g2d);

	public void computeOffset(double x, double y) {
		if (x < 0) {
			x += stageWidth;
		}
		int window = (int) (x - offSetX - HakkStage.WIDTH / 2);

		if (Math.abs(window) > 200) {
			if (window < 0) {
				offSetX += window + 200;
			} else {
				offSetX += window - 200;

			}

		}
		if (offSetX < 0) {
			offSetX += ground.getWidth();
		} else if (offSetX > ground.getWidth()) {
			offSetX -= ground.getWidth();
		}

	}

	public int getXOffset() {
		return offSetX;
	}

}
