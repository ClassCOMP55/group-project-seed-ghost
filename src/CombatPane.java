import java.awt.Color;

import acm.graphics.GLabel;
import acm.graphics.GObject;

public class CombatPane extends GraphicsPane{
	
	public CombatPane(MainApplication mainScreen) {
		this.mainScreen = mainScreen;
	}
	
	@Override
	public void showContent() {
	}

	@Override
	public void hideContent() {
		for(GObject item : contents) {
			mainScreen.remove(item);
		}
		contents.clear();
	}
	

}
