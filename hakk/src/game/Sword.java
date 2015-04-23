package game;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sword {
	private Character wielder;
	private double x;
	private double y;
	private BufferedImage image;
	
	public Sword(){
		try {
			image = ImageIO.read(new File("sprites/sword1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public void move(double dy, double dx){
		x += dx;
		y +=dy;
	}
	
	public void draw(Graphics2D g2d) {
		int intx = (int) Math.round(x);
		int inty = (int) Math.round(y);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawImage(image, intx, inty, null);
	}

}
