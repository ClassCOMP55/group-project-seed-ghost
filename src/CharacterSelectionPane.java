import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

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
		
		GLabel mapLabel = new GLabel("Go to Map", 100, 70);
		mapLabel.setColor(Color.BLUE);
		mapLabel.setFont("DialogInput-PLAIN-20");
		mapLabel.setLocation((mainScreen.getWidth() - mapLabel.getWidth()) / 2, 270);
		
		contents.add(mapLabel);
		mainScreen.add(mapLabel);
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
		String[] professions = {
    			"knight","samurai","thief","viking",
    			"cleric","sorcerer","paladin","ranger","marksman"
    			};
		for (int i = 0;i<3;i++) {
			Character myChar = new Character(Chance.choose(professions));
			myChars.add(myChar);
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (mainScreen.getElementAtLocation(e.getX(), e.getY()) == contents.get(2)) {
			mainScreen.switchToMapPane();
		}
	}
		

}
