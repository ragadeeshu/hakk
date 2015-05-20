package networking;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.LinkedList;

public class RequestHandler implements Runnable {

	private Socket socket;
	private Server server;
	private InputStream inputStream = null;
	private OutputStream outputStream = null;
	private boolean newConnect;
	private boolean newDisconnect;
	private String hostIdentifier;
	private LinkedList<Death> deaths;

	public RequestHandler(Socket socket, Server server) {
		this.socket = socket;
		this.server = server;
		newConnect = false;
		newDisconnect = false;
		hostIdentifier = socket.getInetAddress().getHostName() + ":"
				+ socket.getPort();
		deaths = new LinkedList<>();
	}

	@Override
	public void run() {

		System.out.println("New connection from " + hostIdentifier);
		try {
			inputStream = socket.getInputStream();
			outputStream = socket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		boolean connected = true;

		while (connected) {
			try {
				byte[] bytes = new byte[Networking.BUFFER_SIZE];
				inputStream.read(bytes);
				String[] input = new String(bytes).trim().split(
						Networking.SEPARATOR_SWORD);
				String characterState = input[0];
				String swordState = input[1];
				server.updateCharacterState(hostIdentifier, characterState);
				server.updateSwordState(hostIdentifier, swordState);
				StringBuilder state = new StringBuilder();
				synchronized (this) {
					state.append(server.getStates());
					state.append(Networking.SEPARATOR_MESSAGES);
					if (newConnect) {
						state.append(server.getNameMessage());
						newConnect = false;
					}
					if (newDisconnect) {
						state.append(server.getDisconnectMessage());
						newDisconnect = false;
					}
				}
				synchronized (deaths) {
					if (!deaths.isEmpty()) {
						state.append(Networking.MESSAGE_DEATH);
						state.append(Networking.SEPARATOR_STATE);
						state.append(deaths.poll().message());
						while (!deaths.isEmpty()) {
							state.append(Networking.SEPARATOR_PLAYER);
							state.append(deaths.poll().message());
						}
					}
				}
				outputStream.write(state.toString().getBytes());
				outputStream.flush();
			} catch (IOException | ArrayIndexOutOfBoundsException e) {

				connected = false;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("Client " + hostIdentifier + " disconnected");
		server.disconnect(socket.getInetAddress().getHostName() + ":"
				+ socket.getPort());

	}

	public synchronized void flagNewConnect() {
		newConnect = true;
	}

	public synchronized void flagNewDisconnect() {
		newDisconnect = true;
	}

	public void putDeath(String inetAddress, String murderer, int score,
			double x, double y) {
		synchronized (deaths) {
			deaths.push(new Death(inetAddress, murderer, score, x, y));
		}
	}
}
