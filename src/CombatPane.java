
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GRect;

public class CombatPane extends GraphicsPane{
	
	Character party1;
	Enemy enemy1;
	private ArrayList<Entity> myEntities;
	private ArrayList<GImage> myImages;

	public CombatPane(MainApplication mainScreen) {
		this.mainScreen = mainScreen;
	}

	@Override
	public void showContent() {
		myEntities = new ArrayList<>();
		myImages = new ArrayList<>();
		createBackground();
		addText();
		party1 = new Character("viking");
		myEntities.add(party1);
		enemy1 = new Enemy();
		enemy1.setSprite("spr_HolyGhost");
		myEntities.add(enemy1);
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
		
		GLabel returnButton = new GLabel("Return");
		returnButton.setColor(Color.BLACK);
		returnButton.setFont("DialogInput-PLAIN-20");
		returnButton.setLocation(720, 580);

		contents.add(returnButton);
		mainScreen.add(returnButton);

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
		for (Entity entity:myEntities) {
			GImage image = entity.getSprite();
			myImages.add(image);
			if (entity instanceof Character) {
				image.setLocation(0, 200);
			}
			else {
				image.setLocation(800-image.getWidth(), 200);
			}
			addHpAndMana(image);
			contents.add(image);
			mainScreen.add(image);
		}
	}
	
	
	private void addHpAndMana(GImage mySprite) {
		Entity myEntity = myEntities.get(myImages.indexOf(mySprite));
		GRect partyHealth = new GRect(100,15);
		partyHealth.setLocation(mySprite.getX()+50, mySprite.getY()+mySprite.getHeight()+15);
		partyHealth.setColor(Color.BLACK);
		partyHealth.setFillColor(Color.RED);
		partyHealth.setFilled(true);
		
		contents.add(partyHealth);
		mainScreen.add(partyHealth);
		
		GLabel healthText = new GLabel("Health: "+myEntity.getHp());
		healthText.setLocation(partyHealth.getX(), partyHealth.getY());
		healthText.setFont("Arial-Bold-15");
		
		contents.add(healthText);
		mainScreen.add(healthText);
	}
	
	
	public void mouseClicked(MouseEvent e) {
		if (mainScreen.getElementAtLocation(e.getX(), e.getY()) instanceof GLabel) {
			mainScreen.switchToMapPane();
		}
		
		}
	

}