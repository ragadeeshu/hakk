package networking;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class RequestHandler implements Runnable {

	private Socket socket;
	private Server server;
	private ObjectInputStream inputStream = null;
	private ObjectOutputStream outputStream = null;

	public RequestHandler(Socket socket, Server server) {
		this.socket = socket;
		this.server = server;
	}

	@Override
	public void run() {

		System.out.println("New connection from "
				+ socket.getInetAddress().getHostName() + ":"
				+ socket.getPort() + ". Local port: " + socket.getLocalPort());

		try {
			inputStream = new ObjectInputStream(socket.getInputStream());
			outputStream = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		boolean connected = true;

		while (connected) {
			try {
				Object obj = inputStream.readObject();
				server.updateCharacterState(socket.getInetAddress()
						.getHostAddress(), obj);
				outputStream.writeObject(server.getStates());
				outputStream.flush();
			} catch (ClassNotFoundException | IOException e) {
				System.out.println("Client "
						+ socket.getInetAddress().getHostAddress()
						+ " disconnected");
				connected = false;
			}
		}

	}

}
