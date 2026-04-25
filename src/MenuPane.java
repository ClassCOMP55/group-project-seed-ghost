import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import acm.graphics.*;
//This is the main menu page of our game
public class MenuPane extends GraphicsPane {
	
	GRect newGameButton,continueGameButton,settingsButton,highlighted;
	GLabel newGame,continueGame,options;
	
	public MenuPane(MainApplication mainScreen) {
		this.mainScreen = mainScreen;
		newGameButton = new GRect(MainApplication.WINDOW_WIDTH*(250.0/800.0),MainApplication.WINDOW_HEIGHT*(50.0/600.0));
		continueGameButton = new GRect(MainApplication.WINDOW_WIDTH*(250.0/800.0),MainApplication.WINDOW_HEIGHT*(50.0/600.0));
		settingsButton = new GRect(MainApplication.WINDOW_WIDTH*(250.0/800.0),MainApplication.WINDOW_HEIGHT*(50.0/600.0));
	}
	
	@Override
	public void showContent() {
		createBackground();
		addText();
	}

	@Override
	public void hideContent() {
		for(GObject item : contents) {
			mainScreen.remove(item);
		}
		contents.clear();
	}
	
	/*
	 * Adds text to the screen
	 */
	private void addText() {
		
		newGameButton.setLocation((mainScreen.getWidth() - newGameButton.getWidth()) / 2, 290);
		newGameButton.setFillColor(Color.DARK_GRAY);
		newGameButton.setFilled(true);
		
		contents.add(newGameButton);
		mainScreen.add(newGameButton);
		
		newGame = new GLabel("New Game", MainApplication.WINDOW_WIDTH*(100.0/800.0), MainApplication.WINDOW_HEIGHT*(70.0/600.0));
		newGame.setColor(Color.WHITE);
		newGame.setFont("ARIEL-PLAIN-25");
		newGame.setLocation(newGameButton.getX()+(newGameButton.getWidth()-newGame.getWidth())/2, newGameButton.getY()+(newGameButton.getHeight()+newGame.getAscent())/2);
		
		contents.add(newGame);
		mainScreen.add(newGame);
		
		continueGameButton.setLocation((mainScreen.getWidth() - newGameButton.getWidth()) / 2, newGameButton.getY()+MainApplication.WINDOW_HEIGHT*(70.0/600.0));
		continueGameButton.setFillColor(Color.DARK_GRAY);
		continueGameButton.setFilled(true);
		
		contents.add(continueGameButton);
		mainScreen.add(continueGameButton);
		
		
		continueGame = new GLabel("Continue Game", 100, 70);
		continueGame.setColor(Color.WHITE);
		continueGame.setFont("ARIEL-PLAIN-25");
		continueGame.setLocation(continueGameButton.getX()+(continueGameButton.getWidth()-continueGame.getWidth())/2, continueGameButton.getY()+(continueGameButton.getHeight()+continueGame.getAscent())/2);
		
		
		contents.add(continueGame);
		mainScreen.add(continueGame);
		
		settingsButton.setLocation((mainScreen.getWidth() - continueGameButton.getWidth()) / 2, continueGameButton.getY()+MainApplication.WINDOW_HEIGHT*(70.0/600.0));
		settingsButton.setFillColor(Color.DARK_GRAY);
		settingsButton.setFilled(true);
		
		contents.add(settingsButton);
		mainScreen.add(settingsButton);
		
		options = new GLabel("Settings", 100, 70);
		options.setColor(Color.WHITE);
		options.setFont("ARIEL-PLAIN-25");
		options.setLocation(settingsButton.getX()+(settingsButton.getWidth()-options.getWidth())/2, settingsButton.getY()+(settingsButton.getHeight()+options.getAscent())/2);
		
		contents.add(options);
		mainScreen.add(options);
		
		
	}
	
	/*
	 * Adds background to the screen
	 */
	private void createBackground() {
		GImage background = new GImage("spr_MenuBackground.jpeg");
		background.setSize(1366,700);
		background.setLocation(0, 0);
		contents.add(background);
		mainScreen.add(background);
	}
	
	/*
	 * Handles mouse clicked actions
	 * Just used to click buttons and switch pages
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		GObject obj = mainScreen.getElementAtLocation(e.getX(), e.getY());
		if (obj== newGameButton||obj==newGame) {
			mainScreen.switchToCharacterSelectionPane();
		}
		
		else if (obj == settingsButton||obj==options) {
			mainScreen.switchToSettingsPane();
		}
		else if ((obj == continueGameButton||obj==continueGame) && CharacterSelectionPane.active == true) {
			mainScreen.switchToMapPane();
		}
	}
	
	/*
	 * Handles mouse moved actions
	 * Only used to highlight buttons
	 */
	public void mouseMoved(MouseEvent e) {
		GObject obj = mainScreen.getElementAtLocation(e.getX(), e.getY());
		
		if (obj== newGameButton||obj==newGame) {
			highlighted = newGameButton;
			highlighted.setFillColor(Color.LIGHT_GRAY);
		}
		
		else if (obj == settingsButton||obj==options) {
			highlighted = settingsButton;
			highlighted.setFillColor(Color.LIGHT_GRAY);
		}
		else if (obj == continueGameButton||obj==continueGame) {
			highlighted = continueGameButton;
			highlighted.setFillColor(Color.LIGHT_GRAY);
		}
		else if (highlighted!=null) {
			highlighted.setFillColor(Color.DARK_GRAY);
		}
	}
	


}
