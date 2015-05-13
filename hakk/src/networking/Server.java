package networking;

import game.CharacterState;
import game.SwordState;
import graphics.CharacterAnimation;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
	private static final double HIGHHEAVEN = -902;
	private static final double HEAVEN = -900;

	private ArrayList<RequestHandler> handlers;
	private HashMap<String, CharacterState> characterStates;
	private HashMap<String, SwordState> swordStates;
	private HashMap<String, String> playerNames;
	private HashMap<String, String> playerAnimations;

	public Server() {
		characterStates = new HashMap<String, CharacterState>();
		swordStates = new HashMap<String, SwordState>();
		playerNames = new HashMap<String, String>();
		playerAnimations = new HashMap<String, String>();
		handlers = new ArrayList<>();
		BitMaskResources.init();

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
				synchronized (this) {

					socket.setTcpNoDelay(true);
					socket.setReceiveBufferSize(Networking.BUFFER_SIZE);
					socket.setSendBufferSize(Networking.BUFFER_SIZE);
					InputStream inputStream = socket.getInputStream();
					OutputStream outputStream = socket.getOutputStream();
					String clientHandshake = Networking.getUpdate(inputStream);
					while (!clientHandshake.trim().startsWith(
							Networking.CLIENT_HANDSHAKE)) {
						System.out.println("Waiting for handshake");
						clientHandshake = Networking.getUpdate(inputStream);
					}
					String clientIdentity = socket.getInetAddress()
							.getHostName() + ":" + socket.getPort();
					String playerName = clientHandshake
							.split(Networking.SEPARATOR_ATTRIBUTE)[1];
					playerNames.put(clientIdentity, playerName);
					Random rng = new Random();
					playerAnimations
							.put(clientIdentity,
									CharacterAnimation.BASENAMES[rng
											.nextInt(CharacterAnimation.BASENAMES.length)]);
					Networking.send(outputStream, Networking.SERVER_HANDSHAKE + Networking.SEPARATOR_ATTRIBUTE + socket.getInetAddress().getHostName());
					RequestHandler handler = new RequestHandler(socket, this);
					handlers.add(handler);
					pool.submit(handler);
					for (RequestHandler h : handlers) {
						h.flagNewConnect();
					}
				}
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

	public synchronized void updateCharacterState(String inetAddress,
			String state) {
		CharacterState oldState = characterStates.get(inetAddress);
		if (oldState == null || ++oldState.y > HEAVEN)
			characterStates.put(inetAddress, new CharacterState(state));

	}

	public synchronized void updateSwordState(String inetAddress, String state) {
		SwordState s = new SwordState(state);
		swordStates.put(inetAddress, s);
		for (Entry<String, CharacterState> chState : characterStates.entrySet()) {
			if (!chState.getKey().equals(inetAddress)
					&& chState.getValue().isHit(s)) {
				for (RequestHandler handler : handlers) {
					handler.putDeath(chState.getKey(), chState.getValue().x,
							chState.getValue().y);
				}
				chState.getValue().y = HIGHHEAVEN;
			}
		}
	}

	public synchronized String getStates() {
		StringBuilder sb = new StringBuilder();
		for (Entry<String, CharacterState> e : characterStates.entrySet()) {
			sb.append(Networking.SEPARATOR_PLAYER);
			sb.append(e.getKey());
			sb.append(Networking.SEPARATOR_STATE);
			sb.append(e.getValue().toString().trim());
		}
		sb.append(Networking.SEPARATOR_SWORD);
		for (Entry<String, SwordState> e : swordStates.entrySet()) {
			sb.append(e.getKey());
			sb.append(Networking.SEPARATOR_STATE);
			sb.append(e.getValue().toString().trim());
			sb.append(Networking.SEPARATOR_PLAYER);
		}
		return sb.substring(1, sb.lastIndexOf(Networking.SEPARATOR_PLAYER));
	}

	public synchronized String getName(String address) {
		return playerNames.get(address);
	}

	public synchronized void disconnect(String string) {
		characterStates.remove(string);
		playerNames.remove(string);
		for (RequestHandler h : handlers) {
			h.flagNewDisconnect();
		}
	}

	public synchronized String getNameMessage() {
		StringBuilder sb = new StringBuilder();
		sb.append(Networking.MESSAGE_NEWPLAYER);
		sb.append(Networking.SEPARATOR_STATE);

		for (Entry<String, String> e : playerNames.entrySet()) {
			sb.append(e.getKey()); // ip
			sb.append(Networking.SEPARATOR_ATTRIBUTE);
			sb.append(e.getValue().trim()); // name
			sb.append(Networking.SEPARATOR_ATTRIBUTE);
			sb.append(playerAnimations.get(e.getKey()).trim()); // anim
			sb.append(Networking.SEPARATOR_PLAYER);
		}
		System.out.println("getNameMessage: "
				+ sb.substring(0, sb.lastIndexOf(Networking.SEPARATOR_PLAYER)));
		return sb.substring(0, sb.lastIndexOf(Networking.SEPARATOR_PLAYER));
	}

	public synchronized String getDisconnectMessage() {
		StringBuilder sb = new StringBuilder();
		sb.append(Networking.MESSAGE_DISCONNECT);
		sb.append(Networking.SEPARATOR_STATE);

		for (String ip : playerNames.keySet()) {
			sb.append(ip);
			sb.append(Networking.SEPARATOR_PLAYER);
		}
		System.out.println("getDisconnectMessage: "
				+ sb.substring(0, sb.lastIndexOf(Networking.SEPARATOR_PLAYER)));
		return sb.substring(0, sb.lastIndexOf(Networking.SEPARATOR_PLAYER));
	}

}
