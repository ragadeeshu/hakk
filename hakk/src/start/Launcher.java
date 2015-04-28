package start;

import game.HakkStage;
import game.HakkThread;
import game.UpdateThread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import networking.Client;

public class Launcher {

	public static void main(String[] args) throws InterruptedException {
//		System.out.println("Enter server address: ");
		Scanner scan = new Scanner(System.in);
//		String serverAddress = scan.nextLine();
		String serverAddress = "127.0.0.1";
		String playerName = "Player " + new SimpleDateFormat("mm.ss").format(new Date());
//		System.out.println("Enter playername:");
//		String playerName = scan.nextLine();
		Client client = new Client(serverAddress, playerName);
		HakkStage stage = new HakkStage();
		new HakkThread(stage).start();
		new UpdateThread(client, stage, playerName).start();
	}
}
