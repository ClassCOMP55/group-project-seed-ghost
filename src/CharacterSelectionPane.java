import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GLabel;
import acm.graphics.GObject;

public class CharacterSelectionPane extends GraphicsPane {

	public CharacterSelectionPane(MainApplication mainScreen) {
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
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (mainScreen.getElementAtLocation(e.getX(), e.getY()) == contents.get(1)) {
			mainScreen.switchToMapPane();
		}
	}
		

}
