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
				String state = new String(bytes);
				server.updateCharacterState(socket.getInetAddress()
						.getHostName() + ":" + socket.getPort(), state);
				outputStream.write(server.getStates().getBytes());
				outputStream.flush();
			} catch ( IOException e) {
				connected = false;
			}
		}
		System.out.println("Client "
				+ socket.getInetAddress().getHostName() + ":"
				+ socket.getPort() + " disconnected");
		server.disconnect(socket.getInetAddress().getHostName() + ":"
				+ socket.getPort());

	}

}
