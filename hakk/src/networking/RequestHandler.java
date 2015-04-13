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

	public RequestHandler(Socket socket) {
		this.socket = socket;
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
		// new Timer().schedule(new TimerTask() {

		// public void run() {
		while (true) {
			try {
				server.updateCharacterState(socket.getInetAddress().toString(),
						inputStream.readObject());
				outputStream.writeObject(server.getStates());
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		}
		// }, 0, 17);

	}

}
