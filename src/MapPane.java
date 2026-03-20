import java.awt.Color;

import acm.graphics.GLabel;
import acm.graphics.GObject;

public class MapPane extends GraphicsPane {
	public MapPane(MainApplication mainScreen) {
		this.mainScreen = mainScreen;
	}
	
	@Override
	public void showContent() {
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
		
		GLabel title = new GLabel("Map", 100, 70);
		title.setColor(Color.RED);
		title.setFont("DialogInput-PLAIN-80");
		title.setLocation((mainScreen.getWidth() - title.getWidth()) / 2, 70);
		
		contents.add(title);
		mainScreen.add(title);
		
		GLabel shopLabel = new GLabel("The start new game button goes here", 100, 70);
		shopLabel.setColor(Color.BLACK);
		shopLabel.setFont("DialogInput-PLAIN-20");
		shopLabel.setLocation((mainScreen.getWidth() - title.getWidth()) / 2, 270);
		
		contents.add(shopLabel);
		mainScreen.add(shopLabel);
		
		GLabel combatLabel = new GLabel("The continue game button goes here", 100, 70);
		combatLabel.setColor(Color.BLACK);
		combatLabel.setFont("DialogInput-PLAIN-20");
		combatLabel.setLocation((mainScreen.getWidth() - title.getWidth()) / 2, 340);
		
		contents.add(combatLabel);
		mainScreen.add(combatLabel);
		
		GLabel lootLabel = new GLabel("The options buttom goes here", 100, 70);
		lootLabel.setColor(Color.BLACK);
		lootLabel.setFont("DialogInput-PLAIN-20");
		lootLabel.setLocation((mainScreen.getWidth() - title.getWidth()) / 2, 410);
		
		contents.add(lootLabel);
		mainScreen.add(lootLabel);
		
		GLabel campFireLabel = new GLabel("The options buttom goes here", 100, 70);
		campFireLabel.setColor(Color.BLACK);
		campFireLabel.setFont("DialogInput-PLAIN-20");
		campFireLabel.setLocation((mainScreen.getWidth() - title.getWidth()) / 2, 480);
		
		contents.add(lootLabel);
		mainScreen.add(lootLabel);
		
		
	}
	
}
