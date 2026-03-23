import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;

public class CharacterSelectionPane extends GraphicsPane {

	public CharacterSelectionPane(MainApplication mainScreen) {
		this.mainScreen = mainScreen;
	}
	
	@Override
	public void showContent() {
		createBackground();
		addText();
		addCharacters();
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
		title.setColor(Color.BLUE);
		title.setFont("DialogInput-PLAIN-50");
		title.setLocation((mainScreen.getWidth() - title.getWidth()) / 2, 70);
		
		contents.add(title);
		mainScreen.add(title);
		
	}
	
	private void createBackground() {
		GRect backGround = new GRect(800,600);
		backGround.setColor(Color.BLACK);
		backGround.setFillColor(Color.BLACK);
		backGround.setFilled(true);
		backGround.setLocation(0, 0);
		contents.add(backGround);
		mainScreen.add(backGround);
	}
	
	private void addCharacters() {
		GImage characterImage1 = new GImage("spr_Knight.png", 60, 250);
		contents.add(characterImage1);
		mainScreen.add(characterImage1);
		
		GImage characterImage2 = new GImage("spr_Samurai.png", 310, 250);
		contents.add(characterImage2);
		mainScreen.add(characterImage2);
		
		GImage characterImage3 = new GImage("spr_Sorcerer.png", 560, 250);
		contents.add(characterImage3);
		mainScreen.add(characterImage3);
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (mainScreen.getElementAtLocation(e.getX(), e.getY()) instanceof GImage) {
			mainScreen.switchToMapPane();
		}
	}
		

}
