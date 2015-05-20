package notifications;

import game.HakkStage;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Score {
	private static final Color NOTIFICAITON_COLOR = Color.GREEN;
	private int score;
	private String name;

	public Score(String name, int score) {
		this.name = name;
		this.score = score;
	}

	public void draw(Graphics2D g2d, int pos) {
		String message = getString();
		FontMetrics fm = g2d.getFontMetrics();
		Rectangle2D rect = fm.getStringBounds(message, g2d);
		g2d.setPaint(new Color(0.0f, 0.0f, 0.0f, 0.6f));
		g2d.fill(new Rectangle2D.Double(HakkStage.WIDTH - 207, pos - rect.getHeight() + 2, rect
				.getWidth() + 14, rect.getHeight() + 2));

		g2d.setColor(NOTIFICAITON_COLOR);
		g2d.drawString(message, HakkStage.WIDTH - 200, pos);

	}

	private String getString() {
		return name + ": " + score;
	}

}
