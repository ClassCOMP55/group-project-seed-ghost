
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
	
	private ArrayList<Entity> allEntities;
	private ArrayList<GImage> allImages;
	private ArrayList<Entity> initiativeArr;
	private ArrayList<Entity> temp;
	private Character[] myArrAllies;
	private Enemy[] myArrEnemies;
	private int enemyNumber;
	private boolean skill,inventory,playersTurn,enemyTurn;
	private int turn,counter;
	private Entity currentEntity,otherEntity;
	
	Timer t;

	public CombatPane(MainApplication mainScreen) {
		this.mainScreen = mainScreen;
	}

	@Override
	public void showContent() {
		myArrAllies = CharacterSelectionPane.myInventory.getPartyMembers();
		myArrEnemies = new Enemy[3];
		allEntities = new ArrayList<>();
		allImages = new ArrayList<>();
		initiativeArr = new ArrayList<>();
		temp = new ArrayList<>();
		skill = false;
		inventory = false;
		turn = 0;
		counter = 0;
		createBackground();	
		generateEnemiesAndAllies();
		rollForInitiative();
		Skill[] mySkills =myArrAllies[0].getMySkills();
		System.out.println("Health: "+myArrEnemies[0].getHp()+"/"+myArrEnemies[0].getHpMax());
		System.out.println("Mana: "+myArrAllies[0].getMana()+"/"+myArrAllies[0].getManaMax());
		mySkills[2].activationEffect(myArrAllies[0],myArrEnemies[0]);
		System.out.println("Health: "+myArrEnemies[0].getHp()+"/"+myArrEnemies[0].getHpMax());
		System.out.println("Mana: "+myArrAllies[0].getMana()+"/"+myArrAllies[0].getManaMax());
		
	}

	@Override
	public void hideContent() {
		for(GObject item : contents) {
			mainScreen.remove(item);
		}
		contents.clear();
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
	
	private void generateEnemiesAndAllies(){
		enemyNumber = 0;
		for(int i = 0;i<myArrAllies.length;i++) {
			if (myArrAllies[i]!=null) {
				enemyNumber++;
				allEntities.add(myArrAllies[i]);
				temp.add(myArrAllies[i]);
			}
		}
		System.out.println(enemyNumber);
		for(int i = 0;i<enemyNumber;i++) {
			myArrEnemies[i] = new Enemy();
			myArrEnemies[i].setSprite("spr_HolyGhost");
			allEntities.add(myArrEnemies[i]);
			temp.add(myArrEnemies[i]);
		}
		generateImages();
	}
	
	private void generateImages(){
		
		for (Entity e:allEntities) {
			GImage image = e.getSprite();
			allImages.add(image);
		}
		setLocationandAddToScreen();
	}
	
	private Entity imageToEntity(GImage image){
		int index = allImages.lastIndexOf(image);
		Entity e = allEntities.get(index);
		return e;
	}
	
	private GImage entityToImage(Entity e){
		int index = allEntities.indexOf(e);
		GImage image = allImages.get(index);
		return image;
	}
	
	private void setLocationandAddToScreen(){
		int i = 0;
		for (Character c:myArrAllies) {
			if (c!=null) {
				GImage image = entityToImage(c);
				image.setLocation(0, (i*(600/enemyNumber))+((600/enemyNumber-image.getHeight())/2));
				contents.add(image);
				mainScreen.add(image);
				i++;
			}
		}
		
		i = 0;
		for (Enemy c:myArrEnemies) {
			if (c!=null) {
				GImage image = entityToImage(c);
				image.setLocation(800-image.getWidth(), (i*(600/enemyNumber))+((600/enemyNumber-image.getHeight())/2));
				contents.add(image);
				mainScreen.add(image);
				i++;
			}
		}	
	}
	public void rollForInitiative() {
		for (int i = 0;i<allEntities.size();i++) {
			int indexOfHighest = 0;
			for (int n = 0;n<temp.size();n++) {
				if (temp.get(indexOfHighest).getStatSpread()[3]<temp.get(n).getStatSpread()[3]){
					indexOfHighest = n;
				}
			}
			initiativeArr.add(temp.get(indexOfHighest));
			temp.remove(indexOfHighest);
		}
	}
	
	public void nextCombat() {
		counter = counter%initiativeArr.size();
		currentEntity = initiativeArr.get(counter);
		
		if (currentEntity instanceof Character) {
			playersTurn = true;
		}
		else if (currentEntity instanceof Enemy)  {
			enemyTurn = true;
		}
	}
	
	
	
	public void mouseClicked(MouseEvent e) {
		if (mainScreen.getElementAtLocation(e.getX(), e.getY()) instanceof GLabel) {
			mainScreen.switchToMapPane();
		}
		
	
	}
	
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() ==KeyEvent.VK_D) {
			
		}
		if (e.getKeyCode() ==KeyEvent.VK_F) {
			
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//numTimes++;
		
		//if (numTimes <= 10) targeterObj.move(15, 0);
		//else if (numTimes <= 20) targeterObj.move(-15, 0);
		//else if (numTimes <= 30) targetObj.move(5, 0);
		//else if(numTimes <= 40) targetObj.move(-5, 0);
		//else t.stop();
		
	}
	

}