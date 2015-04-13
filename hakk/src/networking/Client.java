package networking;

import java.io.*;
import java.net.*;

public class Client {
	
	private String address = "";
	private int portNbr = 4444;
	private Socket socket = null;
	private InputStream inputStream = null;
	private OutputStream outputStream = null;

	public Client(String serverAddress){
		this.connect(serverAddress);
		this.run();
		this.disconnect();
	}
	
	private void connect(String serverAddress){
		System.out.println("haba");
		try{
			socket = new Socket(serverAddress, portNbr);
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

}
