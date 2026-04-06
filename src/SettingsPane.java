import java.awt.Color;
import java.awt.event.MouseEvent;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;

public class SettingsPane extends GraphicsPane {

	public SettingsPane(MainApplication mainScreen) {
		this.mainScreen = mainScreen;
	}

	@Override
	public void showContent() {
		createBackground();
		addTitle();
	}

	@Override
	public void hideContent() {
		for (GObject item : contents) {
			mainScreen.remove(item);
		}
		contents.clear();
	}

	private void createBackground() {
		GRect bg = new GRect(800, 600);
		bg.setFilled(true);
		bg.setFillColor(Color.RED);
		bg.setColor(Color.RED);
		bg.setLocation(0, 0);
		contents.add(bg);
		mainScreen.add(bg);
	}

	private void addTitle() {
		GLabel title = new GLabel("Settings");
		title.setFont("SERIF-PLAIN-90");
		title.setColor(Color.BLACK);
		title.setLocation((mainScreen.getWidth() - title.getWidth()) / 2, 95);
		contents.add(title);
		mainScreen.add(title);
	}

}
