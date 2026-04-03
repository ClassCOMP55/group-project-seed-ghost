import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;

public class CharacterSelectionPane extends GraphicsPane {
	
	private ArrayList<Character> myChars;
	private ArrayList<GImage> myImages;
	public static PlayerInventory myInventory;

	public CharacterSelectionPane(MainApplication mainScreen) {
		this.mainScreen = mainScreen;
	}
	
	@Override
	public void showContent() {
		myChars = new ArrayList<>();
		myImages = new ArrayList<>();
		createBackground();
		addText();
		createCharacters();
		showStats();
		
	}

	@Override
	public void hideContent() {
		for(GObject item : contents) {
			mainScreen.remove(item);
		}
		contents.clear();
	}
	
	private void addText() {
		GLabel title = new GLabel("Choose your character!", 100, 70);
		title.setColor(Color.RED);
		title.setFont("Ariel-PLAIN-50");
		title.setLocation((mainScreen.getWidth() - title.getWidth()) / 2, 70);
		
		contents.add(title);
		mainScreen.add(title);
		
	}
	
	private void createBackground() {
		GRect backGround = new GRect(800,600);
		backGround.setColor(Color.DARK_GRAY);
		backGround.setFillColor(Color.DARK_GRAY);
		backGround.setFilled(true);
		backGround.setLocation(0, 0);
		contents.add(backGround);
		mainScreen.add(backGround);
	}
	
	private void createCharacters() {
		ArrayList<String> professions = new ArrayList<>(Arrays.asList("knight","samurai","thief","viking","sorcerer","paladin","marksman","cleric","ranger"));
		for (int i = 0;i<3;i++) {
			int choice = Chance.range(0, professions.size()-1);
			Character myChar = new Character(professions.get(choice));
			professions.remove(choice);
			myChars.add(myChar);
			DrawCharacter(i);
		}
	}
	
	private void DrawCharacter(int i) {
		GImage charImage;
		switch(myChars.get(i).getProfession()) {
		case "knight": charImage = new GImage("spr_Knight.png",50+(200*i),350) ; break;
		case "samurai": charImage = new GImage("spr_Samurai.png",50+(200*i),350) ; break;
		case "thief": charImage = new GImage("spr_Thief.png",50+(200*i),350) ; break;
		case "viking": charImage = new GImage("spr_Viking.png",50+(200*i),350); break;
		case "sorcerer": charImage = new GImage("spr_Sorcerer.png",50+(200*i),350); break;
		case "paladin": charImage = new GImage("spr_Paladin.png",50+(200*i),350); break;
		case "cleric": charImage = new GImage("spr_Cleric.png",50+(200*i),350); break;
		case "ranger": charImage = new GImage("Ranger.png",50+(200*i),350); break;
		default: charImage = new GImage("spr_Marksman.png",50+(200*i),350); break;
		}
		charImage.setLocation(5+2*i*charImage.getWidth(), (600-charImage.getHeight())/2);
		myImages.add(charImage);
		contents.add(charImage);
		mainScreen.add(charImage);
	}
	
	private void showStats() {
		for (Character myChar:myChars) {
			int index = myChars.indexOf(myChar);
			GImage image = myImages.get(index);
			GRect box = new GRect(150,70);
			box.setLocation(image.getX(), image.getY()+image.getHeight());
			box.setColor(Color.BLACK);
			box.setFillColor(Color.BLACK);
			box.setFilled(true);
			contents.add(box);
			mainScreen.add(box);
			
			GLabel prof = new GLabel("Profession: "+myChar.getProfession());
			prof.setLocation(box.getX()+5, box.getY()+15);
			prof.setColor(Color.RED);
			prof.setFont("ARIEL-PLAIN-14");
			contents.add(prof);
			mainScreen.add(prof);
			
			GLabel health = new GLabel("Health: "+myChar.getHp());
			health.setLocation(box.getX()+5, box.getY()+35);
			health.setColor(Color.RED);
			health.setFont("ARIEL-PLAIN-14");
			contents.add(health);
			mainScreen.add(health);
			
			GLabel mana = new GLabel("Mana: "+myChar.getManaMax());
			mana.setLocation(box.getX()+5, box.getY()+55);
			mana.setColor(Color.RED);
			mana.setFont("ARIEL-PLAIN-14");
			contents.add(mana);
			mainScreen.add(mana);
			
			
		}
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
		if (mainScreen.getElementAtLocation(e.getX(), e.getY()) == contents.get(2)) {
			myInventory = new PlayerInventory(myChars.get(0));
			System.out.println("option 1");
			System.out.println("You are a "+myChars.get(0).getProfession());
		}
		else if (mainScreen.getElementAtLocation(e.getX(), e.getY()) == contents.get(3)) {
			myInventory = new PlayerInventory(myChars.get(1));
			System.out.println("option 2");
			System.out.println("You are a "+myChars.get(1).getProfession());
		}
		else if (mainScreen.getElementAtLocation(e.getX(), e.getY()) == contents.get(4)) {
			myInventory = new PlayerInventory(myChars.get(2));
			System.out.println("option 3");
			System.out.println("You are a "+myChars.get(2).getProfession());
		}
		
		if (mainScreen.getElementAtLocation(e.getX(), e.getY()) instanceof GImage) {
			mainScreen.switchToMapPane();
		}
	}
		

}
