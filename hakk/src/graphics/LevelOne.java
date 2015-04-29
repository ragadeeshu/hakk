package graphics;

import game.HakkStage;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class LevelOne extends Level {


	public LevelOne() {
		try {
			background = ImageIO.read(new File("sprites/background_long.png"));
			ground = ImageIO.read(new File("sprites/ground_long.png"));
			foreground = ImageIO.read(new File("sprites/foreground.png"));
			stageWidth = ground.getWidth();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void drawBackground(Graphics2D g2d) {
		g2d.drawImage(background, -offSetX/2, HakkStage.HEIGHT-background.getHeight(), null);
		
		
	}

	@Override
	public void drawGround(Graphics2D g2d) {
		g2d.drawImage(ground, -offSetX, HakkStage.GROUNDLEVEL-ground.getHeight()/2, null);
		
	}

	@Override
	public void drawForeground(Graphics2D g2d) {
		
		g2d.drawImage(foreground, -offSetX*2, HakkStage.HEIGHT- foreground.getHeight(), null);
		
	}
}
