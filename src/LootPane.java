import java.awt.Color;

import acm.graphics.GLabel;
import acm.graphics.GObject;

public class LootPane extends GraphicsPane {
	public LootPane(MainApplication mainScreen) {
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
		GLabel title = new GLabel("Claim your loot", 100, 70);
		title.setColor(Color.RED);
		title.setFont("DialogInput-PLAIN-80");
		title.setLocation((mainScreen.getWidth() - title.getWidth()) / 2, 70);
		
		contents.add(title);
		mainScreen.add(title);
		
		GLabel lootLabel = new GLabel("Loot goes here", 100, 70);
		lootLabel.setColor(Color.BLACK);
		lootLabel.setFont("DialogInput-PLAIN-20");
		lootLabel.setLocation((mainScreen.getWidth() - lootLabel.getWidth()) / 2, 270);
		
		contents.add(lootLabel);
		mainScreen.add(lootLabel);
	}

}
// main application: crate a varialbel; called lootpane and initializie it below and then create a switch to method, methods hat say random generator
// bunch of dofferent relecs that just picks one of them