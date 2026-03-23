import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import acm.graphics.*;
//This is the main menu page of our game
public class MenuPane extends GraphicsPane {
	public MenuPane(MainApplication mainScreen) {
		this.mainScreen = mainScreen;
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
		title.setColor(Color.RED);
		title.setFont("DialogInput-PLAIN-80");
		title.setLocation((mainScreen.getWidth() - title.getWidth()) / 2, 70);
		
		contents.add(title);
		mainScreen.add(title);
		
		GLabel newGame = new GLabel("The start new game button goes here", 100, 70);
		newGame.setColor(Color.BLACK);
		newGame.setFont("DialogInput-PLAIN-20");
		newGame.setLocation((mainScreen.getWidth() - newGame.getWidth()) / 2, 270);
		
		contents.add(newGame);
		mainScreen.add(newGame);
		
		GLabel continueGame = new GLabel("The continue game button goes here", 100, 70);
		continueGame.setColor(Color.BLACK);
		continueGame.setFont("DialogInput-PLAIN-20");
		continueGame.setLocation((mainScreen.getWidth() - continueGame.getWidth()) / 2, 340);
		
		contents.add(continueGame);
		mainScreen.add(continueGame);
		
		GLabel options = new GLabel("The options buttom goes here", 100, 70);
		options.setColor(Color.BLACK);
		options.setFont("DialogInput-PLAIN-20");
		options.setLocation((mainScreen.getWidth() - options.getWidth()) / 2, 410);
		
		contents.add(options);
		mainScreen.add(options);
		
		
	}
	
	private void createBackground() {
		GRect backGround = new GRect(800,600);
		backGround.setColor(Color.DARK_GRAY);
		backGround.setFillColor(Color.DARK_GRAY);
		backGround.setFilled(true);
		backGround.setLocation(0, 0);
		contents.add(backGround);
		mainScreen.add(backGround);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (mainScreen.getElementAtLocation(e.getX(), e.getY()) == contents.get(2)) {
			mainScreen.switchToCharacterSelectionPane();
		}
		
		else if (mainScreen.getElementAtLocation(e.getX(), e.getY()) == contents.get(4)) {
			mainScreen.switchToSettingsPane();
		}
	}
	


}
