package networking;

import java.io.*;
import java.net.*;

public class Client {
	
	String address = "localhost";
	int portNbr = 4444;
	Socket socket = null;
	InputStream inputStream = null;
	OutputStream outputStream = null;

	public Client(){
		this.connect();
		this.run();
		this.disconnect();
	}
	
	private void connect(){
		System.out.println("haba");
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

}
