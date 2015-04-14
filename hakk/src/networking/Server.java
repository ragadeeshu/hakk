package networking;

import game.CharacterState;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
	private HashMap<String, String> states;

	public Server() {
		states = new HashMap<String, String>();

		ServerSocket serverSocket = null;
		int portNbr = 4444;

		try {
			serverSocket = new ServerSocket(portNbr);
		} catch (IOException e) {
			System.out.println(e);
			System.exit(1);
		}

		System.out.println("Server setup successfully.");
		System.out.println("Waiting for clients to connect...");

		ExecutorService pool = Executors.newFixedThreadPool(4);

		while (true) {
			Socket socket = null;
			try {
				socket = serverSocket.accept();
				pool.submit(new RequestHandler(socket, this));
			} catch (IOException e) {
				System.out.println(e);
				try {
					socket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				System.exit(1);
			}
		}

	}

	public static void main(String[] args) {
		Server server = new Server();
	}

	public synchronized void updateCharacterState(String inetAddress,
			String state) {
		// System.out.println(inetAddress + " updated");
		states.put(inetAddress, state);

	}

	public synchronized String getStates() {
		StringBuilder sb = new StringBuilder();
		for (Entry<String, String> e : states.entrySet()) {
			sb.append(";");
			sb.append(e.getKey());
			sb.append("%");
			sb.append(e.getValue().trim());
		}
		sb.append(":END");
		System.out.println(sb.toString());
		return sb.substring(1);
	}

}
