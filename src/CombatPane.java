import java.awt.Color;

import acm.graphics.GLabel;
import acm.graphics.GObject;

public class CombatPane extends GraphicsPane{
	
	public CombatPane(MainApplication mainScreen) {
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
		GLabel title = new GLabel("Combat Page", 100, 70);
		title.setColor(Color.RED);
		title.setFont("DialogInput-PLAIN-50");
		title.setLocation((mainScreen.getWidth() - title.getWidth()) / 2, 70);
		
		contents.add(title);
		mainScreen.add(title);
		
		GLabel returnLabel = new GLabel("Return button", 100, 70);
		returnLabel.setColor(Color.BLACK);
		returnLabel.setFont("DialogInput-PLAIN-20");
		returnLabel.setLocation((mainScreen.getWidth() - returnLabel.getWidth()) / 2, 270);
		
		contents.add(returnLabel);
		mainScreen.add(returnLabel);
	}
	

}
