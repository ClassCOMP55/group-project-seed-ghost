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
		newGameButton = new GRect(300,50);
		continueGameButton = new GRect(300,50);
		settingsButton = new GRect(300,50);
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
	
	private void addText() {
		GLabel title = new GLabel("FateBound", 100, 70);
		title.setColor(Color.BLACK);
		title.setFont("SERIF-PLAIN-110");
		title.setLocation((mainScreen.getWidth() - title.getWidth()) / 2, 130);
		
		contents.add(title);
		mainScreen.add(title);
		
		newGameButton.setLocation((mainScreen.getWidth() - newGameButton.getWidth()) / 2, 240);
		newGameButton.setFillColor(Color.DARK_GRAY);
		newGameButton.setFilled(true);
		
		contents.add(newGameButton);
		mainScreen.add(newGameButton);
		
		newGame = new GLabel("New Game", 100, 70);
		newGame.setColor(Color.BLACK);
		newGame.setFont("ARIEL-PLAIN-20");
		newGame.setLocation(newGameButton.getX()+(newGameButton.getWidth()-newGame.getWidth())/2, newGameButton.getY()+(newGameButton.getHeight()+newGame.getAscent())/2);
		
		contents.add(newGame);
		mainScreen.add(newGame);
		
		continueGameButton.setLocation((mainScreen.getWidth() - newGameButton.getWidth()) / 2, newGameButton.getY()+70);
		continueGameButton.setFillColor(Color.DARK_GRAY);
		continueGameButton.setFilled(true);
		
		contents.add(continueGameButton);
		mainScreen.add(continueGameButton);
		
		
		continueGame = new GLabel("Continue Game", 100, 70);
		continueGame.setColor(Color.BLACK);
		continueGame.setFont("ARIEL-PLAIN-20");
		continueGame.setLocation(continueGameButton.getX()+(continueGameButton.getWidth()-continueGame.getWidth())/2, continueGameButton.getY()+(continueGameButton.getHeight()+continueGame.getAscent())/2);
		
		
		contents.add(continueGame);
		mainScreen.add(continueGame);
		
		settingsButton.setLocation((mainScreen.getWidth() - continueGameButton.getWidth()) / 2, continueGameButton.getY()+70);
		settingsButton.setFillColor(Color.DARK_GRAY);
		settingsButton.setFilled(true);
		
		contents.add(settingsButton);
		mainScreen.add(settingsButton);
		
		options = new GLabel("Settings", 100, 70);
		options.setColor(Color.BLACK);
		options.setFont("ARIEL-PLAIN-20");
		options.setLocation(settingsButton.getX()+(settingsButton.getWidth()-options.getWidth())/2, settingsButton.getY()+(settingsButton.getHeight()+options.getAscent())/2);
		
		contents.add(options);
		mainScreen.add(options);
		
		
	}
	
	private void createBackground() {
		GRect backGround = new GRect(MainApplication.WINDOW_WIDTH,MainApplication.WINDOW_HEIGHT);
		backGround.setColor(Color.RED);
		backGround.setFillColor(Color.RED);
		backGround.setFilled(true);
		backGround.setLocation(0, 0);
		contents.add(backGround);
		mainScreen.add(backGround);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		GObject obj = mainScreen.getElementAtLocation(e.getX(), e.getY());
		if (obj== newGameButton||obj==newGame) {
			mainScreen.switchToCharacterSelectionPane();
		}
		
		else if (obj == settingsButton||obj==options) {
			mainScreen.switchToSettingsPane();
		}
		else if (obj == continueGameButton||obj==continueGame) {
			mainScreen.switchToCharacterSelectionPane();
		}
	}
	
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
