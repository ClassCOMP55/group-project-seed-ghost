import java.awt.Color;
import java.awt.event.MouseEvent;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;

public class SettingsPane extends GraphicsPane {

	GRect backButton, highlighted;
	GLabel backLabel;

	GRect volumeTrack, volumeFill;
	GLabel volumePercentLabel;
	int volumePercent = 80;

	public SettingsPane(MainApplication mainScreen) {
		this.mainScreen = mainScreen;
	}

	@Override
	public void showContent() {
		createBackground();
		addTitle();
		addVolumeSection();
		addBackButton();
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

	private void addVolumeSection() {
		GLabel volumeTitle = new GLabel("Volume");
		volumeTitle.setFont("ARIEL-PLAIN-18");
		volumeTitle.setColor(Color.BLACK);
		volumeTitle.setLocation(150, 170);
		contents.add(volumeTitle);
		mainScreen.add(volumeTitle);

		volumeTrack = new GRect(400, 22);
		volumeTrack.setLocation(150, 180);
		volumeTrack.setFilled(true);
		volumeTrack.setFillColor(Color.DARK_GRAY);
		volumeTrack.setColor(Color.BLACK);
		contents.add(volumeTrack);
		mainScreen.add(volumeTrack);

		volumeFill = new GRect(400 * volumePercent / 100, 22);
		volumeFill.setLocation(150, 180);
		volumeFill.setFilled(true);
		volumeFill.setFillColor(Color.BLACK);
		volumeFill.setColor(Color.BLACK);
		contents.add(volumeFill);
		mainScreen.add(volumeFill);

		volumePercentLabel = new GLabel(volumePercent + "%");
		volumePercentLabel.setFont("ARIEL-PLAIN-18");
		volumePercentLabel.setColor(Color.BLACK);
		volumePercentLabel.setLocation(560, 198);
		contents.add(volumePercentLabel);
		mainScreen.add(volumePercentLabel);
	}

	private void addBackButton() {
		backButton = new GRect(300, 50);
		backButton.setLocation((mainScreen.getWidth() - backButton.getWidth()) / 2, 400);
		backButton.setFilled(true);
		backButton.setFillColor(Color.DARK_GRAY);
		contents.add(backButton);
		mainScreen.add(backButton);

		backLabel = new GLabel("Back to Menu");
		backLabel.setFont("ARIEL-PLAIN-20");
		backLabel.setColor(Color.BLACK);
		backLabel.setLocation(
				backButton.getX() + (backButton.getWidth() - backLabel.getWidth()) / 2,
				backButton.getY() + (backButton.getHeight() + backLabel.getAscent()) / 2);
		contents.add(backLabel);
		mainScreen.add(backLabel);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		GObject obj = mainScreen.getElementAtLocation(e.getX(), e.getY());

		if (obj == volumeTrack || obj == volumeFill) {
			double clickX = e.getX() - volumeTrack.getX();
			volumePercent = (int) Math.round((clickX / volumeTrack.getWidth()) * 100);
			if (volumePercent < 0) volumePercent = 0;
			if (volumePercent > 100) volumePercent = 100;
			volumeFill.setSize(volumeTrack.getWidth() * volumePercent / 100, volumeFill.getHeight());
			volumePercentLabel.setLabel(volumePercent + "%");
		}
		else if (obj == backButton || obj == backLabel) {
			mainScreen.switchToMenuPane();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		GObject obj = mainScreen.getElementAtLocation(e.getX(), e.getY());
		if (obj == backButton || obj == backLabel) {
			if (highlighted != null) highlighted.setFillColor(Color.DARK_GRAY);
			highlighted = backButton;
			highlighted.setFillColor(Color.LIGHT_GRAY);
		}
		else {
			if (highlighted != null) {
				highlighted.setFillColor(Color.DARK_GRAY);
				highlighted = null;
			}
		}
	}

}