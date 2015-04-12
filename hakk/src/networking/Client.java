package networking;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.*;

import javax.swing.JFrame;

public class Client extends JFrame implements KeyListener {
	
	String address = "localhost";
	int portNbr = 4444;
	Socket socket = null;
	InputStream inputStream = null;
	OutputStream outputStream = null;

	public Client(){
		super();
		addKeyListener(this);
		setTitle("hakk client");
		setSize(500, 400);
        setVisible(true);
	}
	
	private void connect(){
		try{
			socket = new Socket(address, portNbr);
			inputStream = socket.getInputStream();
			outputStream = socket.getOutputStream();
		} catch(IOException e){
			System.out.println(e);
			System.exit(1);
		}
		System.out.println("Connected to server successfully.");
	}

	private void run(){
		try{
			outputStream.write("hej".getBytes());
			int ch = inputStream.read();
			while(ch!=-1){
				System.out.println((char) ch);
				ch = inputStream.read();
			}
		} catch(IOException e){
			System.out.println(e);
			System.exit(1);
		}
	}
	
	private void disconnect(){
		try{
			socket.close();
		} catch(IOException e){
			System.out.println(e);
			System.exit(1);
		}
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_UP){
			System.out.println("Upp!");
		}
		if(key == KeyEvent.VK_RIGHT){
			System.out.println("Höger!");
		}
		if(key == KeyEvent.VK_DOWN){
			System.out.println("Ner!");
		}
		if(key == KeyEvent.VK_LEFT){
			System.out.println("Vänster!");
		}
	}

	public void keyReleased(KeyEvent e) {
	}
	
	public static void main(String[] args) {
		Client client = new Client();
		//client.connect();
		//client.run();
		//client.disconnect();
	}
	
}
