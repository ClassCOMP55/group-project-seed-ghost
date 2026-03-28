
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
	private boolean skill,inventory,playersTurn,enemyTurn,forSkills;
	private int turn,counter;
	private Entity currentEntity,otherEntity;
	private GRect skillButton,inventoryButton;
	private ArrayList<GRect> allSkillsButton;
	private ArrayList<GLabel> allSkillsButtonLabels;
	Skill[] mySkills;
	
	Timer t;

	public CombatPane(MainApplication mainScreen) {
		this.mainScreen = mainScreen;
	}

	@Override
	public void showContent() {
		Character testChar = new Character("samurai");
		Character testChar2 = new Character("sorcerer");
		CharacterSelectionPane.myInventory.getPartyMembers()[1]=testChar;
		CharacterSelectionPane.myInventory.getPartyMembers()[2]=testChar2;
		myArrAllies = CharacterSelectionPane.myInventory.getPartyMembers();
		myArrEnemies = new Enemy[3];
		allEntities = new ArrayList<>();
		allImages = new ArrayList<>();
		initiativeArr = new ArrayList<>();
		temp = new ArrayList<>();
		allSkillsButton = new ArrayList<>();
		allSkillsButtonLabels = new ArrayList<>();
		skill = false;
		inventory = false;
		turn = 0;
		counter = 0;
		skillButton = createButton(0,540,"Skills");
		inventoryButton = createButton(130,540,"Inventory");	
		generateEnemiesAndAllies();
		rollForInitiative();
		nextCombat();
		
	}

	@Override
	public void hideContent() {
		for(GObject item : contents) {
			mainScreen.remove(item);
		}
		contents.clear();
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
				if (i==0) image.setLocation(0, (i*(600/enemyNumber))+((600/enemyNumber-image.getHeight())/2));
				else if (i==1) image.setLocation(0, (i*(600/enemyNumber))+((600/enemyNumber-image.getHeight())/2)-10);
				else if (i==2) image.setLocation(0, (i*(600/enemyNumber))+((600/enemyNumber-image.getHeight())/2)-45);
				contents.add(image);
				mainScreen.add(image);
				i++;
			}
		}
		
		i = 0;
		for (Enemy c:myArrEnemies) {
			if (c!=null) {
				GImage image = entityToImage(c);
				if (i==0) image.setLocation(800-image.getWidth(), (i*(600/enemyNumber))+((600/enemyNumber-image.getHeight())/2));
				if (i==1) image.setLocation(800-image.getWidth(), (i*(600/enemyNumber))+((600/enemyNumber-image.getHeight())/2)-10);
				if (i==2) image.setLocation(800-image.getWidth(), (i*(600/enemyNumber))+((600/enemyNumber-image.getHeight())/2)-45);
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
	
	public GRect createButton(double x,double y,String str){
		GRect button = new GRect(130,60);
		if (forSkills==true) button.setSize(320, 60);
		button.setLocation(x,y);
		button.setFilled(true);
        button.setFillColor(Color.DARK_GRAY);
        contents.add(button);
		mainScreen.add(button);
		
		GLabel label = new GLabel(str);
		label.setFont("DialogInput-PLAIN-15");
		if (forSkills==true) label.setFont("DialogInput-PLAIN-15");
		label.setLocation(button.getX()+(button.getWidth()-label.getWidth())/2, button.getY()+(button.getHeight()-label.getHeight())/2+15);
		if (forSkills==true) allSkillsButtonLabels.add(label);
		contents.add(label);
		mainScreen.add(label);
		return button;
	}
	
	public void displaySkills(Character myChar) {
		mySkills =myChar.getMySkills();
		GRect displayBox = new GRect(340,320);
		displayBox.setLocation((800-displayBox.getWidth())/2,(600-displayBox.getHeight())/2);
		displayBox.setFilled(true);
		displayBox.setFillColor(Color.black);
        contents.add(displayBox);
		mainScreen.add(displayBox);
		
		forSkills = true;
		createButton((displayBox.getX())+10,displayBox.getY()+10,"Skill List");
		for (int i = 0;i<mySkills.length;i++) {
			GRect skill1 = createButton((displayBox.getX())+10,displayBox.getY()+10+(i*60)+60,mySkills[i].getName());
			allSkillsButton.add(skill1);
			
		}
	}
	public void hideSkills() {
		for (GRect rect:allSkillsButton) {
			contents.remove(rect);
			mainScreen.remove(rect);
		}
		allSkillsButton.clear();
		
		for (GLabel label:allSkillsButtonLabels) {
			contents.remove(label);
			mainScreen.remove(label);
			allSkillsButtonLabels.remove(label);
		}
		allSkillsButtonLabels.clear();
	}
	
	
	
	public void mouseClicked(MouseEvent e) {
		if (mainScreen.getElementAtLocation(e.getX(), e.getY()) == skillButton && playersTurn == true) {
			displaySkills((Character) currentEntity);
			
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