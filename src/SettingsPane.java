import java.awt.Color;

import acm.graphics.GLabel;
import acm.graphics.GObject;

public class SettingsPane extends GraphicsPane {
	
	public SettingsPane(MainApplication mainScreen) {
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
		GLabel title = new GLabel("Settings Page", 100, 70);
		title.setColor(Color.BLUE);
		title.setFont("DialogInput-PLAIN-80");
		title.setLocation((mainScreen.getWidth() - title.getWidth()) / 2, 70);
		
		contents.add(title);
		mainScreen.add(title);
	}

}
