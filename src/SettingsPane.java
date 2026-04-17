import java.awt.Color;
import java.awt.event.MouseEvent;
import acm.graphics.GLabel;
import acm.graphics.GLine;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GRect;

public class SettingsPane extends GraphicsPane {

	GRect backButton, mapButton, muteButton, fullscreenButton;
	GLabel backLabel, mapLabel, muteLabel, fullscreenLabel;

	GRect sfxVolumeTrackBox, sfxVolumeTrack, sfxVolumeFill;
	GOval sfxHandle;
	GLabel sfxVolumePercentLabel;
	int sfxVolumePercent = 80;

	boolean musicMuted = false;
	boolean fullscreen = false;
	boolean draggingSfxVolume = false;

	Color BG_DARK = new Color(10, 12, 28);
	Color PANEL_BG = new Color(28, 33, 62);
	Color PANEL_BORDER = new Color(55, 63, 105);
	Color TEAL = new Color(0, 210, 195);
	Color TEAL_TRACK = new Color(15, 50, 60);
	Color WHITE = new Color(240, 242, 255);
	Color GREY_TEXT = new Color(140, 152, 180);
	Color DIVIDER = new Color(55, 63, 105);
	Color HOVER_BTN = new Color(40, 55, 90);

	int panelW = 700;
	int panelH = 500;
	int panelX;
	int panelY;

	int trackStartX;
	int trackStartY;
	int trackW = 380;
	int trackH = 6;
	int handleSize = 18;

	public SettingsPane(MainApplication mainScreen) {
		this.mainScreen = mainScreen;
	}

	@Override
	public void showContent() {
		sfxVolumePercent = AudioManager.getSfxVolumePercent();
		musicMuted = AudioManager.isMusicMuted();

		panelX = (MainApplication.WINDOW_WIDTH - panelW) / 2;
		panelY = (MainApplication.WINDOW_HEIGHT - panelH) / 2;

		createBackground();
		createPanel();
		addTitle();
		addDivider(panelY + 75);
		addSfxVolumeSection();
		addMuteButton();
		addDivider(panelY + 255);
		addFullscreenButton();
		addDivider(panelY + 378);
		addMapButton();
		addBackButton();
		addVersionLabel();
	}

	@Override
	public void hideContent() {
		for (GObject item : contents) {
			mainScreen.remove(item);
		}
		contents.clear();
	}

	private void createBackground() {
		GRect bg = new GRect(MainApplication.WINDOW_WIDTH, MainApplication.WINDOW_HEIGHT);
		bg.setFilled(true);
		bg.setFillColor(BG_DARK);
		bg.setColor(BG_DARK);
		bg.setLocation(0, 0);
		contents.add(bg);
		mainScreen.add(bg);

		// nebula clouds
		GOval nebula1 = new GOval(600, 400);
		nebula1.setLocation(-100, 50);
		nebula1.setFilled(true);
		nebula1.setFillColor(new Color(60, 10, 100, 60));
		nebula1.setColor(new Color(0, 0, 0, 0));
		contents.add(nebula1);
		mainScreen.add(nebula1);

		GOval nebula2 = new GOval(500, 350);
		nebula2.setLocation(MainApplication.WINDOW_WIDTH - 400, 200);
		nebula2.setFilled(true);
		nebula2.setFillColor(new Color(0, 40, 100, 55));
		nebula2.setColor(new Color(0, 0, 0, 0));
		contents.add(nebula2);
		mainScreen.add(nebula2);

		GOval nebula3 = new GOval(450, 300);
		nebula3.setLocation(200, MainApplication.WINDOW_HEIGHT - 280);
		nebula3.setFilled(true);
		nebula3.setFillColor(new Color(80, 0, 80, 50));
		nebula3.setColor(new Color(0, 0, 0, 0));
		contents.add(nebula3);
		mainScreen.add(nebula3);

		GOval nebula4 = new GOval(350, 250);
		nebula4.setLocation(700, 50);
		nebula4.setFilled(true);
		nebula4.setFillColor(new Color(0, 60, 80, 45));
		nebula4.setColor(new Color(0, 0, 0, 0));
		contents.add(nebula4);
		mainScreen.add(nebula4);

		// stars
		java.util.Random rng = new java.util.Random(99);
		for (int i = 0; i < 200; i++) {
			int x = rng.nextInt(MainApplication.WINDOW_WIDTH);
			int y = rng.nextInt(MainApplication.WINDOW_HEIGHT);
			int bright = 120 + rng.nextInt(136);
			int size = 1;
			if (rng.nextInt(8) == 0) size = 2;
			if (rng.nextInt(25) == 0) size = 3;
			GRect star = new GRect(size, size);
			star.setLocation(x, y);
			star.setFilled(true);
			star.setFillColor(new Color(bright, bright, Math.min(bright + 40, 255)));
			star.setColor(new Color(bright, bright, Math.min(bright + 40, 255)));
			contents.add(star);
			mainScreen.add(star);
		}

		// bigger glowing stars
		for (int i = 0; i < 12; i++) {
			int x = rng.nextInt(MainApplication.WINDOW_WIDTH);
			int y = rng.nextInt(MainApplication.WINDOW_HEIGHT);
			GOval glowStar = new GOval(5, 5);
			glowStar.setLocation(x, y);
			glowStar.setFilled(true);
			glowStar.setFillColor(new Color(180, 200, 255));
			glowStar.setColor(new Color(180, 200, 255));
			contents.add(glowStar);
			mainScreen.add(glowStar);
		}
	}

	private void createPanel() {
		// shadow
		GRect shadow = new GRect(panelW + 20, panelH + 20);
		shadow.setLocation(panelX - 10, panelY - 10);
		shadow.setFilled(true);
		shadow.setFillColor(new Color(0, 0, 10));
		shadow.setColor(new Color(0, 0, 10));
		contents.add(shadow);
		mainScreen.add(shadow);

		// main panel
		GRect panel = new GRect(panelW, panelH);
		panel.setLocation(panelX, panelY);
		panel.setFilled(true);
		panel.setFillColor(PANEL_BG);
		panel.setColor(PANEL_BORDER);
		contents.add(panel);
		mainScreen.add(panel);

		// teal top bar
		GRect topBar = new GRect(panelW, 5);
		topBar.setLocation(panelX, panelY);
		topBar.setFilled(true);
		topBar.setFillColor(TEAL);
		topBar.setColor(TEAL);
		contents.add(topBar);
		mainScreen.add(topBar);
	}

	private void addTitle() {
		GLabel title = new GLabel("Game Settings");
		title.setFont("DialogInput-BOLD-30");
		title.setColor(WHITE);
		title.setLocation(panelX + (panelW - title.getWidth()) / 2, panelY + 58);
		contents.add(title);
		mainScreen.add(title);
	}

	private void addDivider(int y) {
		GLine divider = new GLine(panelX + 40, y, panelX + panelW - 40, y);
		divider.setColor(DIVIDER);
		contents.add(divider);
		mainScreen.add(divider);
	}

	private void addSfxVolumeSection() {
		GLabel sectionLabel = new GLabel("Sound Settings");
		sectionLabel.setFont("DialogInput-BOLD-17");
		sectionLabel.setColor(WHITE);
		sectionLabel.setLocation(panelX + 60, panelY + 112);
		contents.add(sectionLabel);
		mainScreen.add(sectionLabel);

		GLabel volumeLabel = new GLabel("Volume");
		volumeLabel.setFont("DialogInput-PLAIN-14");
		volumeLabel.setColor(GREY_TEXT);
		volumeLabel.setLocation(panelX + 75, panelY + 155);
		contents.add(volumeLabel);
		mainScreen.add(volumeLabel);

		trackStartX = panelX + 200;
		trackStartY = panelY + 145;

		// invisible hit box for dragging
		sfxVolumeTrackBox = new GRect(trackW + handleSize, 40);
		sfxVolumeTrackBox.setLocation(trackStartX - handleSize / 2, trackStartY - 17);
		sfxVolumeTrackBox.setFilled(true);
		sfxVolumeTrackBox.setFillColor(PANEL_BG);
		sfxVolumeTrackBox.setColor(PANEL_BG);
		contents.add(sfxVolumeTrackBox);
		mainScreen.add(sfxVolumeTrackBox);

		sfxVolumeTrack = new GRect(trackW, trackH);
		sfxVolumeTrack.setLocation(trackStartX, trackStartY);
		sfxVolumeTrack.setFilled(true);
		sfxVolumeTrack.setFillColor(TEAL_TRACK);
		sfxVolumeTrack.setColor(TEAL_TRACK);
		contents.add(sfxVolumeTrack);
		mainScreen.add(sfxVolumeTrack);

		sfxVolumeFill = new GRect(trackW * sfxVolumePercent / 100, trackH);
		sfxVolumeFill.setLocation(trackStartX, trackStartY);
		sfxVolumeFill.setFilled(true);
		sfxVolumeFill.setFillColor(TEAL);
		sfxVolumeFill.setColor(TEAL);
		contents.add(sfxVolumeFill);
		mainScreen.add(sfxVolumeFill);

		int handleX = trackStartX + trackW * sfxVolumePercent / 100 - handleSize / 2;
		int handleY = trackStartY - (handleSize - trackH) / 2;
		sfxHandle = new GOval(handleSize, handleSize);
		sfxHandle.setLocation(handleX, handleY);
		sfxHandle.setFilled(true);
		sfxHandle.setFillColor(TEAL);
		sfxHandle.setColor(TEAL);
		contents.add(sfxHandle);
		mainScreen.add(sfxHandle);

		sfxVolumePercentLabel = new GLabel(sfxVolumePercent + "%");
		sfxVolumePercentLabel.setFont("DialogInput-PLAIN-13");
		sfxVolumePercentLabel.setColor(GREY_TEXT);
		sfxVolumePercentLabel.setLocation(trackStartX + trackW + 20, panelY + 155);
		contents.add(sfxVolumePercentLabel);
		mainScreen.add(sfxVolumePercentLabel);
	}

	private void updateSfxVolume(int mouseX) {
		double clickX = mouseX - trackStartX;
		sfxVolumePercent = (int) Math.round((clickX / trackW) * 100);
		if (sfxVolumePercent < 0) sfxVolumePercent = 0;
		if (sfxVolumePercent > 100) sfxVolumePercent = 100;

		mainScreen.remove(sfxVolumeFill);
		sfxVolumeFill.setSize(trackW * sfxVolumePercent / 100, trackH);
		mainScreen.add(sfxVolumeFill);

		int newHandleX = trackStartX + trackW * sfxVolumePercent / 100 - handleSize / 2;
		int handleY = trackStartY - (handleSize - trackH) / 2;
		mainScreen.remove(sfxHandle);
		sfxHandle.setLocation(newHandleX, handleY);
		mainScreen.add(sfxHandle);

		sfxVolumePercentLabel.setLabel(sfxVolumePercent + "%");
		AudioManager.setSfxVolumePercent(sfxVolumePercent);
	}

	private void addMuteButton() {
		GLabel musicRowLabel = new GLabel("Music");
		musicRowLabel.setFont("DialogInput-PLAIN-14");
		musicRowLabel.setColor(GREY_TEXT);
		musicRowLabel.setLocation(panelX + 75, panelY + 210);
		contents.add(musicRowLabel);
		mainScreen.add(musicRowLabel);

		muteButton = new GRect(150, 40);
		muteButton.setLocation(panelX + 200, panelY + 190);
		muteButton.setFilled(true);
		muteButton.setFillColor(TEAL_TRACK);
		muteButton.setColor(TEAL);
		contents.add(muteButton);
		mainScreen.add(muteButton);

		muteLabel = new GLabel(musicMuted ? "OFF" : "ON");
		muteLabel.setFont("DialogInput-BOLD-14");
		muteLabel.setColor(TEAL);
		muteLabel.setLocation(
				muteButton.getX() + (muteButton.getWidth() - muteLabel.getWidth()) / 2,
				muteButton.getY() + (muteButton.getHeight() + muteLabel.getAscent()) / 2);
		contents.add(muteLabel);
		mainScreen.add(muteLabel);
	}

	private void toggleMute() {
		musicMuted = !musicMuted;
		AudioManager.setMusicMuted(musicMuted);
		muteLabel.setLabel(musicMuted ? "OFF" : "ON");
		muteLabel.setLocation(
				muteButton.getX() + (muteButton.getWidth() - muteLabel.getWidth()) / 2,
				muteButton.getY() + (muteButton.getHeight() + muteLabel.getAscent()) / 2);
	}

	private void addFullscreenButton() {
		GLabel displayLabel = new GLabel("Display");
		displayLabel.setFont("DialogInput-BOLD-17");
		displayLabel.setColor(WHITE);
		displayLabel.setLocation(panelX + 60, panelY + 292);
		contents.add(displayLabel);
		mainScreen.add(displayLabel);

		GLabel fsRowLabel = new GLabel("Fullscreen");
		fsRowLabel.setFont("DialogInput-PLAIN-14");
		fsRowLabel.setColor(GREY_TEXT);
		fsRowLabel.setLocation(panelX + 75, panelY + 340);
		contents.add(fsRowLabel);
		mainScreen.add(fsRowLabel);

		fullscreenButton = new GRect(150, 40);
		fullscreenButton.setLocation(panelX + 200, panelY + 320);
		fullscreenButton.setFilled(true);
		fullscreenButton.setFillColor(TEAL_TRACK);
		fullscreenButton.setColor(TEAL);
		contents.add(fullscreenButton);
		mainScreen.add(fullscreenButton);

		fullscreenLabel = new GLabel(fullscreen ? "ON" : "OFF");
		fullscreenLabel.setFont("DialogInput-BOLD-14");
		fullscreenLabel.setColor(TEAL);
		fullscreenLabel.setLocation(
				fullscreenButton.getX() + (fullscreenButton.getWidth() - fullscreenLabel.getWidth()) / 2,
				fullscreenButton.getY() + (fullscreenButton.getHeight() + fullscreenLabel.getAscent()) / 2);
		contents.add(fullscreenLabel);
		mainScreen.add(fullscreenLabel);
	}

	private void toggleFullscreen() {
		fullscreen = !fullscreen;

		java.awt.Window window = javax.swing.SwingUtilities.getWindowAncestor(mainScreen.getGCanvas());
		if (window instanceof java.awt.Frame) {
			java.awt.Frame frame = (java.awt.Frame) window;
			if (fullscreen) {
				java.awt.Dimension screen = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
				frame.setLocation(0, 0);
				frame.setSize(screen.width, screen.height);
			} else {
				frame.setSize(MainApplication.WINDOW_WIDTH, MainApplication.WINDOW_HEIGHT);
				frame.setLocationRelativeTo(null);
			}
		}

		fullscreenLabel.setLabel(fullscreen ? "ON" : "OFF");
		fullscreenLabel.setLocation(
				fullscreenButton.getX() + (fullscreenButton.getWidth() - fullscreenLabel.getWidth()) / 2,
				fullscreenButton.getY() + (fullscreenButton.getHeight() + fullscreenLabel.getAscent()) / 2);
	}

	private void addMapButton() {
		mapButton = new GRect(200, 42);
		mapButton.setLocation(panelX + 60, panelY + 400);
		mapButton.setFilled(true);
		mapButton.setFillColor(TEAL_TRACK);
		mapButton.setColor(TEAL);
		contents.add(mapButton);
		mainScreen.add(mapButton);

		mapLabel = new GLabel("Back to Map");
		mapLabel.setFont("DialogInput-BOLD-14");
		mapLabel.setColor(TEAL);
		mapLabel.setLocation(
				mapButton.getX() + (mapButton.getWidth() - mapLabel.getWidth()) / 2,
				mapButton.getY() + (mapButton.getHeight() + mapLabel.getAscent()) / 2);
		contents.add(mapLabel);
		mainScreen.add(mapLabel);
	}

	private void addBackButton() {
		backButton = new GRect(200, 42);
		backButton.setLocation(panelX + panelW - 260, panelY + 400);
		backButton.setFilled(true);
		backButton.setFillColor(new Color(60, 20, 45));
		backButton.setColor(new Color(180, 60, 100));
		contents.add(backButton);
		mainScreen.add(backButton);

		backLabel = new GLabel("Back to Menu");
		backLabel.setFont("DialogInput-BOLD-14");
		backLabel.setColor(new Color(210, 100, 140));
		backLabel.setLocation(
				backButton.getX() + (backButton.getWidth() - backLabel.getWidth()) / 2,
				backButton.getY() + (backButton.getHeight() + backLabel.getAscent()) / 2);
		contents.add(backLabel);
		mainScreen.add(backLabel);
	}

	private void addVersionLabel() {
		GLabel version = new GLabel("FateBound v1.0");
		version.setFont("DialogInput-PLAIN-11");
		version.setColor(new Color(70, 80, 115));
		version.setLocation(panelX + panelW - version.getWidth() - 20, panelY + panelH - 15);
		contents.add(version);
		mainScreen.add(version);
	}

	private boolean isInside(GRect rect, double x, double y) {
		if (rect == null) return false;
		return x >= rect.getX() && x <= rect.getX() + rect.getWidth()
				&& y >= rect.getY() && y <= rect.getY() + rect.getHeight();
	}

	private void resetButtonColors() {
		muteButton.setFillColor(TEAL_TRACK);
		fullscreenButton.setFillColor(TEAL_TRACK);
		mapButton.setFillColor(TEAL_TRACK);
		backButton.setFillColor(new Color(60, 20, 45));
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		double x = e.getX();
		double y = e.getY();

		if (isInside(sfxVolumeTrackBox, x, y)) {
			updateSfxVolume((int) x);
			return;
		}
		if (isInside(muteButton, x, y)) {
			toggleMute();
			return;
		}
		if (isInside(fullscreenButton, x, y)) {
			toggleFullscreen();
			return;
		}
		if (isInside(mapButton, x, y)) {
			mainScreen.switchToMapPane();
			return;
		}
		if (isInside(backButton, x, y)) {
			mainScreen.switchToMenuPane();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		double x = e.getX();
		double y = e.getY();
		draggingSfxVolume = isInside(sfxVolumeTrackBox, x, y);
		if (draggingSfxVolume) {
			updateSfxVolume((int) x);
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (draggingSfxVolume) {
			updateSfxVolume(e.getX());
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		draggingSfxVolume = false;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		double x = e.getX();
		double y = e.getY();
		resetButtonColors();

		if (isInside(muteButton, x, y)) muteButton.setFillColor(HOVER_BTN);
		else if (isInside(fullscreenButton, x, y)) fullscreenButton.setFillColor(HOVER_BTN);
		else if (isInside(mapButton, x, y)) mapButton.setFillColor(HOVER_BTN);
		else if (isInside(backButton, x, y)) backButton.setFillColor(new Color(90, 30, 60));
	}
}