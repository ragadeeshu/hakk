package notifications;

import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.TreeMap;

public class Scorekeeper {

	private TreeMap<String, Score> notifications;

	public Scorekeeper() {
		this.notifications = new TreeMap<>();
	}

	public synchronized void put(String identificator, Score score) {
		notifications.put(identificator, score);
	}
	
	public synchronized void remove(String identificator) {
		notifications.remove(identificator);
	}


	public synchronized void draw(Graphics2D g2d) {
		int pos = 20;
		Iterator<Score> iter = notifications.values().iterator();
		while (iter.hasNext()) {
			iter.next().draw(g2d, pos);
			pos += 20;
		}
	}

}
