
import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GRect;

public class CombatPane extends GraphicsPane{

	public CombatPane(MainApplication mainScreen) {
		this.mainScreen = mainScreen;
	}

	@Override
	public void showContent() {
		createBackground();
		addText();
		addEntities();
	}

	@Override
	public void hideContent() {
		for(GObject item : contents) {
			mainScreen.remove(item);
		}
		contents.clear();
	}

	private void addText() {
		GLabel title = new GLabel("Combat Page", 100, 70);
		title.setColor(Color.RED);
		title.setFont("DialogInput-PLAIN-50");
		title.setLocation((mainScreen.getWidth() - title.getWidth()) / 2, 70);

		contents.add(title);
		mainScreen.add(title);

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
	private void addEntities() {
		GImage party1 = new GImage("spr_Viking.png",0,200);
		party1.setLocation(0, 200);
		contents.add(party1);
		mainScreen.add(party1);
		addHpAndMana(party1);
		
		GImage enemy1 = new GImage("spr_HolyGhost.png",400,300);
		enemy1.setLocation(800-enemy1.getWidth(), 200);
		contents.add(enemy1);
		mainScreen.add(enemy1);
		addHpAndMana(enemy1);
	}
	
	private void addHpAndMana(GImage entity) {
		GRect partyHealth = new GRect(100,15);
		partyHealth.setLocation(entity.getX()+50, entity.getY()+entity.getHeight());
		partyHealth.setColor(Color.BLACK);
		partyHealth.setFillColor(Color.RED);
		partyHealth.setFilled(true);
		contents.add(partyHealth);
		mainScreen.add(partyHealth);
	}
	
	
	public void mouseClicked(MouseEvent e) {
		if (mainScreen.getElementAtLocation(e.getX(), e.getY()) instanceof GImage) {
			mainScreen.switchToMapPane();
		}
		
		}
	

}