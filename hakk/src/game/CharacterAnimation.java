package game;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class CharacterAnimation {
	private static final int NUM_RUNNING = 11;
	private ArrayList<Image> running;
	private int current;
	private String baseName;
	private static HashMap<String, BufferedImage> resources;
	
	static{
		resources= new HashMap<>();
	}
	public static BufferedImage getImage(String name){
		return resources.get(name);
	}

	public CharacterAnimation(String baseName) {
		running = new ArrayList<Image>();
		BufferedImage img = null;
		for (int i = 0; i < NUM_RUNNING; i++) {
//			System.out.println("sprites"+File.pathSeparator+baseName+"runni__"+String.format("%03d", i)+".png");
			try {
				String name = "sprites/"+baseName+"runni__"+String.format("%03d", i)+".png";
				img = ImageIO.read(new File(name));
				resources.put(name, img);
			} catch (IOException e) {
				e.printStackTrace();
			}
			running.add(img);
		}
		current = 0;
	}

	public void run() {
		current = (current + 1) % NUM_RUNNING;
	}
	public String getCurrentImageName(){
		return baseName +String.format("runni__%03d.png", current);
	}

	public Image getImage() {
		return running.get(current);
	}

}
