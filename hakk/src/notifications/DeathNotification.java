package notifications;

public class DeathNotification extends Notification {

	private String killed;
	private String killer;

	public DeathNotification(String killed, String killer) {
		super();
		this.killed = killed;
		this.killer = killer;

	}

	@Override
	protected String getString() {
		return String.format("%s was slain by %s.", killed, killer);

	}

}
