import java.awt.Color;
import java.awt.event.MouseEvent;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;

public class SettingsPane extends GraphicsPane {

	GRect backButton, muteButton, fullscreenButton, resetButton;
	GLabel backLabel, muteLabel, fullscreenLabel, resetLabel;

	GRect volumeTrack, volumeFill;
	GLabel volumePercentLabel;
	int volumePercent = 80;

	boolean muted = false;
	boolean fullscreen = false;

	public SettingsPane(MainApplication mainScreen) {
		this.mainScreen = mainScreen;
	}

	@Override
	public void showContent() {
		createBackground();
		addTitle();
		addVolumeSection();
		addMuteButton();
		addFullscreenButton();
		addBackButton();
		addResetButton();
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
		bg.setFillColor(Color.DARK_GRAY);
		bg.setColor(Color.DARK_GRAY);
		bg.setLocation(0, 0);
		contents.add(bg);
		mainScreen.add(bg);
	}

	private void addTitle() {
		GLabel title = new GLabel("Settings");
		title.setFont("DialogInput-BOLD-70");
		title.setColor(new Color(220, 190, 100));
		title.setLocation((mainScreen.getWidth() - title.getWidth()) / 2, 95);
		contents.add(title);
		mainScreen.add(title);
	}

	private void addVolumeSection() {
		GLabel volumeTitle = new GLabel("Volume");
		volumeTitle.setFont("DialogInput-BOLD-16");
		volumeTitle.setColor(new Color(220, 190, 100));
		volumeTitle.setLocation(150, 175);
		contents.add(volumeTitle);
		mainScreen.add(volumeTitle);

		// box around the bar so it looks cleaner
		GRect trackBox = new GRect(420, 42);
		trackBox.setLocation(140, 183);
		trackBox.setFilled(true);
		trackBox.setFillColor(new Color(30, 30, 30));
		trackBox.setColor(new Color(180, 140, 60));
		contents.add(trackBox);
		mainScreen.add(trackBox);

		volumeTrack = new GRect(400, 22);
		volumeTrack.setLocation(150, 193);
		volumeTrack.setFilled(true);
		volumeTrack.setFillColor(new Color(60, 60, 60));
		volumeTrack.setColor(new Color(60, 60, 60));
		contents.add(volumeTrack);
		mainScreen.add(volumeTrack);

		volumeFill = new GRect(400 * volumePercent / 100, 22);
		volumeFill.setLocation(150, 193);
		volumeFill.setFilled(true);
		volumeFill.setFillColor(new Color(180, 140, 60));
		volumeFill.setColor(new Color(180, 140, 60));
		contents.add(volumeFill);
		mainScreen.add(volumeFill);

		volumePercentLabel = new GLabel(volumePercent + "%");
		volumePercentLabel.setFont("DialogInput-BOLD-16");
		volumePercentLabel.setColor(new Color(220, 190, 100));
		volumePercentLabel.setLocation(565, 210);
		contents.add(volumePercentLabel);
		mainScreen.add(volumePercentLabel);

		// attach listener directly to the track like LootPane does
		volumeTrack.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				updateVolume(e.getX());
			}
		});
		volumeFill.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				updateVolume(e.getX());
			}
		});
		trackBox.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				updateVolume(e.getX());
			}
		});
	}

	private void updateVolume(int mouseX) {
		double clickX = mouseX - volumeTrack.getX();
		volumePercent = (int) Math.round((clickX / volumeTrack.getWidth()) * 100);
		if (volumePercent < 0) volumePercent = 0;
		if (volumePercent > 100) volumePercent = 100;
		volumeFill.setSize(volumeTrack.getWidth() * volumePercent / 100, volumeFill.getHeight());
		volumePercentLabel.setLabel(volumePercent + "%");
	}

	private void addMuteButton() {
		muteButton = new GRect(300, 50);
		muteButton.setLocation((mainScreen.getWidth() - muteButton.getWidth()) / 2, 255);
		muteButton.setFilled(true);
		muteButton.setFillColor(new Color(30, 30, 30));
		muteButton.setColor(new Color(180, 140, 60));
		contents.add(muteButton);
		mainScreen.add(muteButton);

		muteLabel = new GLabel("Sound: ON");
		muteLabel.setFont("DialogInput-BOLD-16");
		muteLabel.setColor(new Color(220, 190, 100));
		muteLabel.setLocation(
				muteButton.getX() + (muteButton.getWidth() - muteLabel.getWidth()) / 2,
				muteButton.getY() + (muteButton.getHeight() + muteLabel.getAscent()) / 2);
		contents.add(muteLabel);
		mainScreen.add(muteLabel);

		muteButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				toggleMute();
			}
			public void mouseEntered(MouseEvent e) {
				muteButton.setFillColor(new Color(60, 40, 10));
			}
			public void mouseExited(MouseEvent e) {
				muteButton.setFillColor(new Color(30, 30, 30));
			}
		});
		muteLabel.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				toggleMute();
			}
		});
	}

	private void toggleMute() {
		muted = !muted;
		if (muted) {
			muteLabel.setLabel("Sound: OFF");
		}
		else {
			muteLabel.setLabel("Sound: ON");
		}
		recenterLabel(muteLabel, muteButton);
	}

	private void addFullscreenButton() {
		fullscreenButton = new GRect(300, 50);
		fullscreenButton.setLocation((mainScreen.getWidth() - fullscreenButton.getWidth()) / 2, 325);
		fullscreenButton.setFilled(true);
		fullscreenButton.setFillColor(new Color(30, 30, 30));
		fullscreenButton.setColor(new Color(180, 140, 60));
		contents.add(fullscreenButton);
		mainScreen.add(fullscreenButton);

		fullscreenLabel = new GLabel("Fullscreen: OFF");
		fullscreenLabel.setFont("DialogInput-BOLD-16");
		fullscreenLabel.setColor(new Color(220, 190, 100));
		fullscreenLabel.setLocation(
				fullscreenButton.getX() + (fullscreenButton.getWidth() - fullscreenLabel.getWidth()) / 2,
				fullscreenButton.getY() + (fullscreenButton.getHeight() + fullscreenLabel.getAscent()) / 2);
		contents.add(fullscreenLabel);
		mainScreen.add(fullscreenLabel);

		fullscreenButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				toggleFullscreen();
			}
			public void mouseEntered(MouseEvent e) {
				fullscreenButton.setFillColor(new Color(60, 40, 10));
			}
			public void mouseExited(MouseEvent e) {
				fullscreenButton.setFillColor(new Color(30, 30, 30));
			}
		});
		fullscreenLabel.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				toggleFullscreen();
			}
		});
	}

	private void toggleFullscreen() {
		fullscreen = !fullscreen;
		java.awt.Window window = javax.swing.SwingUtilities.getWindowAncestor(mainScreen.getGCanvas());
		if (window instanceof java.awt.Frame) {
			java.awt.Frame frame = (java.awt.Frame) window;
			if (fullscreen) {
				frame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
			}
			else {
				frame.setExtendedState(java.awt.Frame.NORMAL);
			}
		}
		if (fullscreen) {
			fullscreenLabel.setLabel("Fullscreen: ON");
		}
		else {
			fullscreenLabel.setLabel("Fullscreen: OFF");
		}
		recenterLabel(fullscreenLabel, fullscreenButton);
	}

	private void addBackButton() {
		backButton = new GRect(300, 50);
		backButton.setLocation((mainScreen.getWidth() - backButton.getWidth()) / 2, 395);
		backButton.setFilled(true);
		backButton.setFillColor(new Color(30, 30, 30));
		backButton.setColor(new Color(180, 140, 60));
		contents.add(backButton);
		mainScreen.add(backButton);

		backLabel = new GLabel("Back to Menu");
		backLabel.setFont("DialogInput-BOLD-16");
		backLabel.setColor(new Color(220, 190, 100));
		backLabel.setLocation(
				backButton.getX() + (backButton.getWidth() - backLabel.getWidth()) / 2,
				backButton.getY() + (backButton.getHeight() + backLabel.getAscent()) / 2);
		contents.add(backLabel);
		mainScreen.add(backLabel);

		backButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				mainScreen.switchToMenuPane();
			}
			public void mouseEntered(MouseEvent e) {
				backButton.setFillColor(new Color(60, 40, 10));
			}
			public void mouseExited(MouseEvent e) {
				backButton.setFillColor(new Color(30, 30, 30));
			}
		});
		backLabel.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				mainScreen.switchToMenuPane();
			}
		});
	}

	private void addResetButton() {
		// red so its obvious its a destructive action
		resetButton = new GRect(300, 50);
		resetButton.setLocation((mainScreen.getWidth() - resetButton.getWidth()) / 2, 465);
		resetButton.setFilled(true);
		resetButton.setFillColor(new Color(60, 20, 20));
		resetButton.setColor(new Color(180, 60, 60));
		contents.add(resetButton);
		mainScreen.add(resetButton);

		resetLabel = new GLabel("New Game / Reset");
		resetLabel.setFont("DialogInput-BOLD-16");
		resetLabel.setColor(new Color(220, 100, 100));
		resetLabel.setLocation(
				resetButton.getX() + (resetButton.getWidth() - resetLabel.getWidth()) / 2,
				resetButton.getY() + (resetButton.getHeight() + resetLabel.getAscent()) / 2);
		contents.add(resetLabel);
		mainScreen.add(resetLabel);

		resetButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				CharacterSelectionPane.myInventory = null;
				mainScreen.switchToCharacterSelectionPane();
			}
			public void mouseEntered(MouseEvent e) {
				resetButton.setFillColor(new Color(100, 30, 30));
			}
			public void mouseExited(MouseEvent e) {
				resetButton.setFillColor(new Color(60, 20, 20));
			}
		});
		resetLabel.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				CharacterSelectionPane.myInventory = null;
				mainScreen.switchToCharacterSelectionPane();
			}
		});
	}

	private void recenterLabel(GLabel label, GRect button) {
		label.setLocation(
				button.getX() + (button.getWidth() - label.getWidth()) / 2,
				button.getY() + (button.getHeight() + label.getAscent()) / 2);
	}

}