package notifications;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public abstract class Notification {
	private static final Color NOTIFICAITON_COLOR = Color.GREEN;
	private int timeout;

	protected Notification() {
		this.timeout = 600;
	}

	public boolean update() {
		if (--timeout <= 0)
			return true;
		return false;
	}

	public void draw(Graphics2D g2d, int pos) {
		String message = getString();
		FontMetrics fm = g2d.getFontMetrics();
		Rectangle2D rect = fm.getStringBounds(message, g2d);
		g2d.setPaint(new Color(0.0f, 0.0f, 0.0f, 0.6f));
		g2d.fill(new Rectangle2D.Double(4, pos - rect.getHeight() + 2, rect
				.getWidth() + 14, rect.getHeight() + 2));

		g2d.setColor(NOTIFICAITON_COLOR);
		g2d.drawString(message, 10, pos);

	}

	protected abstract String getString();
}
