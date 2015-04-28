package game;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import networking.Networking;

public class Sword {
//	private Character wielder;
	private double x;
	private double y;
	private BufferedImage image;
	private String currentImage;

	public Sword(double x, double y) {
		this.x = x;
		this.y = y;
		currentImage = "sword_attacking_left___000.png";
		try {
			String name = "sprites/" + currentImage;
			image = ImageIO.read(new File(name));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Sword(String state) {
		String[] s = state.split(Networking.SEPARATOR_ATTRIBUTE);

		this.x = Double.parseDouble(s[0]);
		this.y = Double.parseDouble(s[1]);
		this.currentImage = s[2];
	}

	public void move(double dy, double dx) {
		x += dx;
		y += dy;
	}

	public void draw(Graphics2D g2d) {
		int intx = (int) Math.round(x);
		int inty = (int) Math.round(y);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		int height = image.getHeight(null);
		g2d.drawImage(image, intx, inty - height, null);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(x);
		sb.append(Networking.SEPARATOR_ATTRIBUTE);
		sb.append(y);
		sb.append(Networking.SEPARATOR_ATTRIBUTE);
		sb.append(currentImage);
		return sb.toString();
	}

	public String getImage() {
		return currentImage;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

}
