package graphics;

import game.HakkStage;
import game.Platform;

import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class LevelOne extends Level {

	public LevelOne() {
		try {
			background = ImageIO.read(new File("sprites/background_long.png"));
			ground = ImageIO.read(new File("sprites/ground_long.png"));
			foreground = ImageIO.read(new File("sprites/foreground.png"));
			stageWidth = ground.getWidth();
			platforms = new ArrayList<Platform>();
			platforms.add(new Platform(200, HakkStage.GROUNDLEVEL - 100));
			platforms.add(new Platform(500, HakkStage.GROUNDLEVEL - 200));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void drawBackground(Graphics2D g2d) {
		g2d.drawImage(background, -offSetX / 2, (int) (HakkStage.HEIGHT
				- background.getHeight() + offSetY * 1.5), null);
		if (offSetX > HakkStage.LEVEL_WIDTH - 2 * HakkStage.WIDTH) {
			g2d.drawImage(
					background,
					-offSetX / 2 + background.getWidth(),
					(int) (HakkStage.HEIGHT - background.getHeight() + offSetY * 1.5),
					null);
		} else if (offSetX < 0) {
			g2d.drawImage(
					background,
					-offSetX / 2 - background.getWidth(),
					(int) (HakkStage.HEIGHT - background.getHeight() + offSetY * 1.5),
					null);
		}

	}

	@Override
	public void drawGround(Graphics2D g2d) {
		g2d.drawImage(ground, -offSetX, HakkStage.HEIGHT - ground.getHeight()
				+ offSetY, null);
		if (offSetX > HakkStage.LEVEL_WIDTH - HakkStage.WIDTH) {
			g2d.drawImage(ground, -offSetX + ground.getWidth(),
					HakkStage.HEIGHT - ground.getHeight() + offSetY, null);
		} else if (offSetX < 0) {
			g2d.drawImage(ground, -offSetX - ground.getWidth(),
					HakkStage.HEIGHT - ground.getHeight() + offSetY, null);
		}
	}

	@Override
	public void drawForeground(Graphics2D g2d) {

		g2d.drawImage(foreground, -offSetX * 2, (int) (HakkStage.HEIGHT
				- foreground.getHeight() + offSetY / 1.5), null);
		if (offSetX > HakkStage.LEVEL_WIDTH - HakkStage.WIDTH) {
			g2d.drawImage(
					foreground,
					-offSetX * 2 + foreground.getWidth(),
					(int) (HakkStage.HEIGHT - foreground.getHeight() + offSetY / 1.5),
					null);
		} else if (offSetX < 0) {
			g2d.drawImage(
					foreground,
					-offSetX * 2 - foreground.getWidth(),
					(int) (HakkStage.HEIGHT - foreground.getHeight() + offSetY / 1.5),
					null);
		}

	}

	@Override
	public void drawPlatforms(Graphics2D g2d, int xOffset, int yOffset) {
		for(Platform platform : platforms){
			platform.draw(g2d, xOffset, yOffset);
		}
	}

	@Override
	public ArrayList<Platform> getPlatforms() {
		return platforms;
	}

	@Override
	public int hitPlatform(double charX, double charY, int charWidth) {
		for(Platform platform : platforms){
			int y = platform.hitPlatform(charX, charY, charWidth);
			if(y != 0){
				//System.out.println(y);
				return y;
			}
		}
		return 0;
	}
	
}
