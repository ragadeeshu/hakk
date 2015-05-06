package graphics;

import game.HakkStage;
import game.Platform;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class Level {
	protected int offSetX;
	protected int offSetY;
	protected int stageWidth;
	public static final int SCROLL_WINDOW = 200;

	protected BufferedImage background;
	protected BufferedImage ground;
	protected BufferedImage foreground;
	protected ArrayList<Platform> platforms;

	public abstract void drawBackground(Graphics2D g2d);

	public abstract void drawGround(Graphics2D g2d);

	public abstract void drawForeground(Graphics2D g2d);
	
	public abstract void drawPlatforms(Graphics2D g2d, int xOffset, int yOffset);
	
	public abstract ArrayList<Platform> getPlatforms();
	
	public abstract int hitPlatform(double charX, double charY, int charWidth);

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

		offSetY = (int) (Math
				.min(((-y + CharacterAnimation.GROUND_OFFSET + HakkStage.GROUNDLEVEL)
						/ (HakkStage.LEVEL_HEIGHT - HakkStage.HEIGHT + HakkStage.GROUNDLEVEL) * (HakkStage.LEVEL_HEIGHT - HakkStage.HEIGHT)),
						300) * 2 / 3);
	}

	public int getXOffset() {
		return offSetX;
	}

	public int getYOffset() {
		return offSetY;
	}

}
