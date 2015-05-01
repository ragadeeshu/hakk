package graphics;

import game.HakkStage;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class Level {
	protected int offSetX;
	protected int offSetY;
	protected int stageWidth;
	public static final int SCROLL_WINDOW = 200;

	protected BufferedImage background;
	protected BufferedImage ground;
	protected BufferedImage foreground;

	public abstract void drawBackground(Graphics2D g2d);

	public abstract void drawGround(Graphics2D g2d);

	public abstract void drawForeground(Graphics2D g2d);

	public void computeOffset(double x, double y) {
		if (offSetX < 0 && x > HakkStage.LEVEL_WIDTH / 2) {
			offSetX += HakkStage.LEVEL_WIDTH;
		} else if (offSetX > HakkStage.LEVEL_WIDTH - HakkStage.WIDTH
				&& x < HakkStage.LEVEL_WIDTH / 2) {
			offSetX -= HakkStage.LEVEL_WIDTH;
		}
		int window = (int) (x - offSetX - HakkStage.WIDTH / 2);

		if (Math.abs(window) > SCROLL_WINDOW) {
			if (window < 0) {
				offSetX += window + SCROLL_WINDOW;
			} else {
				offSetX += window - SCROLL_WINDOW;

			}

		}


	}

	public int getXOffset() {
		return offSetX;
	}

}
