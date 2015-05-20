package start;

import game.HakkStage;
import game.HakkThread;
import game.Player;
import game.UpdateThread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import networking.Client;
import Music.MusicPlayer;

public class ClientLauncher {

	public static void main(String[] args) throws InterruptedException {
		// Scanner scan = new Scanner(System.in);

		String serverAddress = "127.0.0.1";
		serverAddress = JOptionPane.showInputDialog("Enter server address:");
		// System.out.println("Enter server address: ");
		// String serverAddress = scan.nextLine();

		String playerName = "Player "
				+ new SimpleDateFormat("mm:ss").format(new Date());

		// System.out.println("Enter playername (alphanumerical): ");
		playerName = JOptionPane
				.showInputDialog("Enter playername (alphanumerical): ");
		while (!playerName.matches("[A-Za-z0-9\\s]+")) {
			playerName = JOptionPane
					.showInputDialog("Enter playername (alphanumerical): ");

		}
		if(serverAddress==null || playerName==null){
			JOptionPane.showMessageDialog(null, "Invalid input",
					"Input error", JOptionPane.ERROR_MESSAGE);
		}
		// String playerName = scan.nextLine();

		// scan.close();
		Client client = null;
		try {
			client = new Client(serverAddress, playerName);
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(null, "Could not connect to server",
					"Connection error", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
		JFrame frame = new JFrame("hakk");
		frame.setSize(HakkStage.WIDTH, HakkStage.HEIGHT);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		HakkStage stage = new HakkStage();
		frame.add(stage);
		frame.validate();
		frame.repaint();
		System.out.println("I am " + client.getAddress());
		stage.addPlayerCharacter(client.getAddress(), new Player(stage,
				playerName));
		new HakkThread(stage).start();
		new UpdateThread(client, stage, playerName).start();
		System.out.println("Music by Gichco from http://opengameart.org/");

		new MusicPlayer("/resources/bgm/cave-loop.mp3", true).start();

	}
}
