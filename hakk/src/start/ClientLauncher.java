package start;

import game.HakkStage;
import game.HakkThread;
import game.Player;
import game.UpdateThread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import Music.MusicPlayer;
import networking.Client;

public class ClientLauncher {

	public static void main(String[] args) throws InterruptedException {
		Scanner scan = new Scanner(System.in);

		// System.out.println("Enter server address: ");
		// String serverAddress = scan.nextLine();
		String serverAddress = "127.0.0.1";

		// System.out.println("Enter playername (alphanumerical): ");
		// while (!scan.hasNext("[A-Za-z0-9\\s]+")) {
		// System.out.println("THAT'S NOT VERY ALPHANUMERICAL. Try again: ");
		// scan.nextLine();
		// }
		// String playerName = scan.nextLine();
		String playerName = "Player "
				+ new SimpleDateFormat("mm:ss").format(new Date());

		scan.close();
		Client client = new Client(serverAddress, playerName);
		HakkStage stage = new HakkStage();
		stage.addPlayerCharacter(client.getAddress(), new Player(stage,
				playerName));
		new HakkThread(stage).start();
		new UpdateThread(client, stage, playerName).start();
		System.out.println("Music by Gichco from http://opengameart.org/");
		MusicPlayer musicPlayer = new MusicPlayer("bgm/cave-loop.mp3", true);
		musicPlayer.start();
	}
}
