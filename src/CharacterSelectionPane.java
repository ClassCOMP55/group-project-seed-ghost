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

	public CharacterSelectionPane(MainApplication mainScreen) {
		this.mainScreen = mainScreen;
	}
	
	@Override
	public void showContent() {
		myChars = new ArrayList<>();
		createBackground();
		addText();
		createCharacters();
		
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
		title.setFont("DialogInput-PLAIN-50");
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
		ArrayList<String> professions = new ArrayList<>(Arrays.asList("knight","samurai","thief","viking","sorcerer","paladin","marksman"));
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
		case "knight": charImage = new GImage("spr_Knight.png",50+(200*i),300) ; break;
		case "samurai": charImage = new GImage("spr_Samurai.png",50+(200*i),300) ; break;
		case "thief": charImage = new GImage("spr_Thief.png",50+(200*i),300) ; break;
		case "viking": charImage = new GImage("spr_Viking.png",50+(200*i),300); break;
		case "sorcerer": charImage = new GImage("spr_Sorcerer.png",50+(200*i),300); break;
		case "paladin": charImage = new GImage("spr_Paladin.png",50+(200*i),300); break;
		default: charImage = new GImage("spr_Marksman.png",50+(200*i),300); break;
		}
		contents.add(charImage);
		mainScreen.add(charImage);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (mainScreen.getElementAtLocation(e.getX(), e.getY()) instanceof GImage) {
			mainScreen.switchToMapPane();
		}
	}
		

}
