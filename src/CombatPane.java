
import java.awt.Color;
import java.awt.event.KeyEvent;
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
	private ArrayList<GLabel> healthDisplays;
	boolean playersTurn;
	boolean lost;
	boolean won;
	boolean atk;
	boolean heal;
	boolean next;
	GLabel turnLabel;
	int turn;

	public CombatPane(MainApplication mainScreen) {
		this.mainScreen = mainScreen;
	}

	@Override
	public void showContent() {
		myEntities = new ArrayList<>();
		myImages = new ArrayList<>();
		healthDisplays = new ArrayList<>();
		turn = 0;
		createBackground();
		addText();
		party1 = new Character("viking");
		myEntities.add(party1);
		enemy1 = new Enemy();
		enemy1.setSprite("spr_HolyGhost");
		myEntities.add(enemy1);
		addEntities();
		addButtons();
		nextCombat();
		
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
		
		turnLabel = new GLabel("Turn: "+turn);
		turnLabel.setColor(Color.BLACK);
		turnLabel.setFont("DialogInput-PLAIN-20");
		turnLabel.setLocation(title.getX()+(title.getWidth()-turnLabel.getWidth())/2, title.getY()+40);

		contents.add(turnLabel);
		mainScreen.add(turnLabel);

	}
	
	public void nextCombat() {
		if (myEntities.get(turn%myEntities.size()) instanceof Character) {
			playersTurn =true;
			System.out.println("your turn");
		}
		else {
			playersTurn =false;
			System.out.println("enemy turn");
			attackPlayer(myEntities.get(turn%myEntities.size()-1));
			turn++;
			turnLabel.setLabel("Turn: "+turn);
			nextCombat();
		}
	}
	
	public void attackPlayer(Entity myChar) {
		myChar.setHp(myChar.getHp()-75);
		healthDisplays.get(0).setLabel("Health: "+myChar.getHp());
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
		healthDisplays.add(healthText);
		
		contents.add(healthText);
		mainScreen.add(healthText);
	}
	
	public void addButtons() {
		GRect attackButton = new GRect(80,35);
		attackButton.setLocation(10,565 );
		attackButton.setColor(Color.BLACK);
		attackButton.setFillColor(Color.WHITE);
		attackButton.setFilled(true);
		
		contents.add(attackButton);
		mainScreen.add(attackButton);
		
		GLabel attackButtonText = new GLabel("Attack Press: D");
		attackButtonText.setFont("Arial-Bold-10");
		attackButtonText .setLocation(attackButton.getX()+(80-attackButtonText.getWidth())/2,attackButton.getY()+22.5);
		
		contents.add(attackButtonText);
		mainScreen.add(attackButtonText);
		
		GRect healButton = new GRect(80,35);
		healButton.setLocation(attackButton.getWidth()+25,565 );
		healButton.setColor(Color.BLACK);
		healButton.setFillColor(Color.WHITE);
		healButton.setFilled(true);
		
		contents.add(healButton);
		mainScreen.add(healButton);
		
		GLabel healButtonText = new GLabel("Heal Press: F");
		healButtonText.setFont("Arial-Bold-10");
		healButtonText .setLocation(healButton.getX()+(80-healButtonText.getWidth())/2,healButton.getY()+22.5);
		
		contents.add(healButtonText);
		mainScreen.add(healButtonText);
		
		
	}
	
	public void mouseClicked(MouseEvent e) {
		if (mainScreen.getElementAtLocation(e.getX(), e.getY()) instanceof GLabel) {
			mainScreen.switchToMapPane();
		}
		
		if (mainScreen.getElementAtLocation(e.getX(), e.getY()) instanceof GImage && playersTurn ==true&& atk ==true) {
			GObject image = mainScreen.getElementAtLocation(e.getX(), e.getY());
			Entity myEntity = myEntities.get(myImages.indexOf(image));
			if (myEntity==myEntities.get(1)) {
				myEntity.attackMe(myEntities.get(0).attackOther());
				healthDisplays.get(1).setLabel("Health: "+myEntity.getHp());
				atk = false;
				turn++;
				turnLabel.setLabel("Turn: "+turn);
				nextCombat();
			}
		}
		
		}
	
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() ==KeyEvent.VK_D) {
			System.out.println("Pressed D");
			atk = true;
		}
		if (e.getKeyCode() ==KeyEvent.VK_F) {
			System.out.println("Pressed F");
			heal =true;
		}
	}
	

}