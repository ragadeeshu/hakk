package game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Platform {
	private double x;
	private double y;
	private static BufferedImage image;
	
	static {
		try {
			image = ImageIO.read(new File("sprites/platform.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Platform(double x, double y){
		this.x = x;
		this.y = y;
	}

	
	public void draw(Graphics2D g2d){
		g2d.drawImage(image, (int)x, (int)y, null);
	}
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
}
