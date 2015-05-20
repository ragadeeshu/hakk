package notifications;

import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.LinkedList;

public class NotificationHandler {

	private LinkedList<Notification> notifications;

	public NotificationHandler() {
		this.notifications = new LinkedList<>();
	}

	public synchronized void put(Notification notification) {
		notifications.add(notification);
	}

	public synchronized void update() {
		Iterator<Notification> iter = notifications.iterator();
		while (iter.hasNext()) {
			if (iter.next().update())
				iter.remove();
		}
	}

	public synchronized void draw(Graphics2D g2d) {
		int pos = 20;
		Iterator<Notification> iter = notifications.iterator();
		while (iter.hasNext()) {
			iter.next().draw(g2d, pos);
			pos += 20;
		}
	}

}
