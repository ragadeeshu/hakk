package networking;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class RequestHandler implements Runnable {

	private Socket socket;
	private Server server;
	private InputStream inputStream = null;
	private OutputStream outputStream = null;
	private boolean newName;

	public RequestHandler(Socket socket, Server server) {
		this.socket = socket;
		this.server = server;
		newName = false;
	}

	@Override
	public void run() {

		System.out.println("New connection from "
				+ socket.getInetAddress().getHostName() + ":"
				+ socket.getPort() + ". Local port: " + socket.getLocalPort());
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
				String[] input = new String(bytes).split("&");
				String characterState = input[0];
				String swordState = input[1];
				server.updateCharacterState(socket.getInetAddress()
						.getHostName() + ":" + socket.getPort(), characterState);
				// System.out.println("got charup");
				server.updateSwordState(socket.getInetAddress().getHostName()
						+ ":" + socket.getPort(), swordState);
				// System.out.println("got srtuff");
				String state;
				synchronized (this) {
					if (newName) {
						System.out.println("gttomg ma,es");
						state = server.getStates() + server.getNameMessage();
						System.out.println("Server sending:" + state);
						newName = false;
					} else
						state = server.getStates();
				}
				outputStream.write(state.trim().getBytes());
				outputStream.flush();
				// System.out.println("sent stuff");
			} catch (Exception e) {
				connected = false;
			}
		}
		System.out.println("Client " + socket.getInetAddress().getHostName()
				+ ":" + socket.getPort() + " disconnected");
		server.disconnect(socket.getInetAddress().getHostName() + ":"
				+ socket.getPort());

	}

	public synchronized void flagNewName() {
		newName = true;

	}

}
