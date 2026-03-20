import java.awt.Color;
import java.awt.Label;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import acm.graphics.*;

//The ShopPane
public class ShopPane extends GraphicsPane {
	public ShopPane(MainApplication mainScreen) {
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
		GLabel title = new GLabel("SHOP", 100, 70);
		title.setColor(Color.BLUE);
		title.setFont("DialogInput-PLAIN-80");
		title.setLocation((mainScreen.getWidth() - title.getWidth()) / 2, 70);
		
		contents.add(title);
		mainScreen.add(title);
		}
}
//work in progress