
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GRect;
import javax.swing.*;

public class CombatPane extends GraphicsPane implements ActionListener {
	
	Character party1;
	Enemy enemy1;
	private ArrayList<Entity> myEntities;
	private ArrayList<GImage> myImages;
	private ArrayList<GLabel> healthDisplays;
	boolean playersTurn;
	boolean atk;
	boolean heal;
	boolean next;
	GLabel turnLabel;
	GLabel whosTurn;
	GLabel directions;
	GObject targetObj;
	GObject targeterObj;
	int turn;
	int numTimes;
	Timer t;

	public CombatPane(MainApplication mainScreen) {
		this.mainScreen = mainScreen;
	}

	@Override
	public void showContent() {
		myEntities = new ArrayList<>();
		myImages = new ArrayList<>();
		healthDisplays = new ArrayList<>();
		t = new Timer(50, this);
		t.setInitialDelay(0);
		turn = 0;
		Character[] myArr = CharacterSelectionPane.myInvetory.getPartyMembers();
		createBackground();
		addText();
		myEntities.add(myArr[0]);
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
		
		whosTurn = new GLabel("Your Turn",title.getX()+(title.getWidth()-turnLabel.getWidth())/2,title.getY()+80);
		whosTurn.setColor(Color.BLACK);
		whosTurn.setFont("DialogInput-PLAIN-20");
		
		contents.add(whosTurn);
		mainScreen.add(whosTurn);
		
		directions = new GLabel("Directions: Choose a Action",5,title.getY()+80);
		directions.setColor(Color.BLUE);
		directions.setFont("DialogInput-PLAIN-15");
		
		contents.add(directions);
		mainScreen.add(directions);

	}
	
	public void nextCombat() {
		if (myEntities.get(turn%myEntities.size()) instanceof Character) {
			playersTurn = true;
			targeterObj = myImages.get(turn%myEntities.size());
			directions.setLabel("Driections: Choose a Action");
		}
		else {
			playersTurn = false;
			directions.setLabel("Driections: Wait for Opponent");
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
	private void displayPartyHpandMana() {
		int partySize =1;
		Character[] myArr = CharacterSelectionPane.myInvetory.getPartyMembers();
		
		for (int i = 0;i<partySize;i++) {
			GImage image = myArr[i].getSprite();
			if(partySize==1)image.setLocation(0, 450);
			else image.setLocation(0, 450-(i*image.getHeight()+20));
			contents.add(image);
			mainScreen.add(image);
		}
		
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
				healthDisplays.get(1).setLabel("Health: "+Math.round(myEntity.getHp()));
				targetObj = image;
				numTimes = 0;
				t.start();
				atk = false;
				turn++;
				turnLabel.setLabel("Turn: "+turn);
				nextCombat();
			}
		}
		
		if (mainScreen.getElementAtLocation(e.getX(), e.getY()) instanceof GImage && playersTurn ==true&& heal ==true) {
			GObject image = mainScreen.getElementAtLocation(e.getX(), e.getY());
			Entity myEntity = myEntities.get(myImages.indexOf(image));
			if (myEntity==myEntities.get(0)) {
				myEntity.setHp(myEntity.getHp()+100);
				healthDisplays.get(0).setLabel("Health: "+Math.round(myEntity.getHp()));
				heal = false;
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
			directions.setLabel("Driections: Choose a target to attack");
		}
		if (e.getKeyCode() ==KeyEvent.VK_F) {
			System.out.println("Pressed F");
			heal =true;
			directions.setLabel("Driections: Choose a target to heal");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		numTimes++;
		
		if (numTimes <= 10) targeterObj.move(15, 0);
		else if (numTimes <= 20) targeterObj.move(-15, 0);
		else if (numTimes <= 30) targetObj.move(5, 0);
		else if(numTimes <= 40) targetObj.move(-5, 0);
		else t.stop();
		
	}
	

}