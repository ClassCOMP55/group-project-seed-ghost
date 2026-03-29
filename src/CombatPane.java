
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GLine;
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
	private boolean skill,inventory,playersTurn,enemyTurn,forSkills,skillReady,on;
	private int turn,counter,skillIndex;
	private Entity currentEntity,otherEntity;
	private GRect skillButton,inventoryButton,displayBox,extra,highlighted;
	private GLabel displayBoxLabel,description,TurnLabel;
	private ArrayList<GRect> allSkillsButton;
	private ArrayList<GLabel> allSkillsButtonLabels,healthLabels;
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
		healthLabels = new ArrayList<>();
		otherEntity = new Enemy();
		highlighted = new GRect(0,0);
		skill = false;
		inventory = false;
		turn = 0;
		counter = 0;
		skillButton = createButton(0,540,"Skills");
		inventoryButton = createButton(130,540,"Inventory");	
		generateEnemiesAndAllies();
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
			image.setSize(140, 140);
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
				if (i==0) image.setLocation(800-image.getWidth(), (i*(600/enemyNumber))+((600/enemyNumber-image.getHeight())/2)+20);
				if (i==1) image.setLocation(800-image.getWidth(), (i*(600/enemyNumber))+((600/enemyNumber-image.getHeight())/2)-10);
				if (i==2) image.setLocation(800-image.getWidth(), (i*(600/enemyNumber))+((600/enemyNumber-image.getHeight())/2)-50);
				contents.add(image);
				mainScreen.add(image);
				i++;
			}
		}
		addText();
		rollForInitiative();
		createHealthLabels();
	}
	
	public void checkResult() {
		boolean won = true;
		boolean lost = true;
		for(int i = 0;i<myArrAllies.length;i++) {
			if (myArrAllies[i]!=null) {
				if (myArrAllies[i].isDead()==false) lost = false;	
			}
		}
		
		for(int i = 0;i<myArrEnemies.length;i++) {
			if (myArrEnemies[i].isDead()==false) won = false;
		}
		
		if (won==true) {
			mainScreen.switchToMapPane();
		}
		if (lost==true) {
			mainScreen.switchToMenuPane();
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
		checkResult();
		if (counter>0)entityToImage(currentEntity).setColor(null);
		counter = counter%initiativeArr.size();
		currentEntity = initiativeArr.get(counter);
		yourTurn(entityToImage(currentEntity));
		
		if (currentEntity instanceof Character) {
			playersTurn = true;
			counter++;
		}
		else if (currentEntity instanceof Enemy)  {
			enemyTurn = true;
			counter++;
			nextCombat();
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
	public void addText() {
		description = new GLabel("Click a Action");
		description.setFont("DialogInput-PLAIN-15");
		description.setLocation((800-description.getWidth())/2,50);
		contents.add(description);
		mainScreen.add(description);
		
	}
	
	public void createHealthLabels() {
		for (Entity e:allEntities) {
			GLabel health = new GLabel(e.getHp()+"/"+e.getHp());
			GImage image = entityToImage(e);
			health.setLocation(image.getX()+50,image.getY());
			healthLabels.add(health);
			contents.add(health);
			mainScreen.add(health);
		}
	}
	
	public void displaySkills(Character myChar) {
		mySkills =myChar.getMySkills();
		displayBox = new GRect(340,320);
		displayBox.setLocation((800-displayBox.getWidth())/2,(600-displayBox.getHeight())/2);
		displayBox.setFilled(true);
		displayBox.setFillColor(Color.black);
        contents.add(displayBox);
		mainScreen.add(displayBox);
		
		forSkills = true;
		extra = createButton((displayBox.getX())+10,displayBox.getY()+10,"Skill List");
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
		}
		contents.remove(displayBox);
		mainScreen.remove(displayBox);
		contents.remove(extra);
		mainScreen.remove(extra);
		allSkillsButtonLabels.clear();
	}
	
	public void yourTurn(GImage image) {
		image.setColor(Color.YELLOW);
	}
	
	public void yourDead(Entity entity) {
		GImage image = entityToImage(entity);
		GLine line1 = new GLine(image.getX(),image.getY(),image.getX()+image.getWidth(),image.getY()+image.getHeight());
		line1.setColor(Color.RED);
		line1.setLineWidth(4);
		GLine line2 = new GLine(image.getX()+image.getWidth(),image.getY(),image.getX(),image.getY()+image.getHeight());
		line2.setColor(Color.RED);
		line2.setLineWidth(4);
		contents.add(line1);
		mainScreen.add(line1);
		contents.add(line2);
		mainScreen.add(line2);
	}
	
	public void mouseClicked(MouseEvent e) {
		GObject obj = mainScreen.getElementAtLocation(e.getX(), e.getY());
		if (obj == skillButton && playersTurn == true) {
			displaySkills((Character) currentEntity);
			skill = true;	
		}
		if (allSkillsButton.size()!=0 && playersTurn == true && skill==true) {
			if (allSkillsButton.contains(obj)) {
				skillIndex = allSkillsButton.indexOf(obj);
				if (mySkills[skillIndex].getName()=="Prayer of Healing"||mySkills[skillIndex].getName()=="Guard Self") {
					mySkills[skillIndex].activationEffect(currentEntity,otherEntity);
					int index = allEntities.indexOf(currentEntity);
					Character c = (Character) currentEntity;
					healthLabels.get(index).setLabel(c.getHp()+"/"+c.getHp());
					nextCombat();
				}
				else {
					skillReady = true;
				}
				hideSkills();
			}
		}
		if (obj instanceof GImage && playersTurn == true && skillReady==true) {
			otherEntity = imageToEntity((GImage) obj);
			System.out.println(otherEntity.getHp());
			if (otherEntity instanceof Enemy) {
				mySkills[skillIndex].activationEffect(currentEntity,otherEntity);
				int index = allEntities.indexOf(otherEntity);
				Enemy enemy = (Enemy) otherEntity;
				healthLabels.get(index).setLabel(enemy.getHp()+"/"+enemy.getHp());
				System.out.println(otherEntity.getHp());
				if (enemy.isDead()) {
					healthLabels.get(index).setLabel("Dead");
					healthLabels.get(index).setLocation(healthLabels.get(index).getX()+10,healthLabels.get(index).getY());
					yourDead(otherEntity);
					initiativeArr.remove(otherEntity);
					skill = false;
					skillReady = false;
					playersTurn = false;
				}
				nextCombat();
			}
			else {
				description.setLabel("Thats not a target!");
				description.setFont("DialogInput-PLAIN-15");
				description.setLocation((800-description.getWidth())/2,20);
			}
	
		}
	}
	
	public void mouseMoved(MouseEvent e) {
		GObject obj = mainScreen.getElementAtLocation(e.getX(), e.getY());
		if (skill = true && allSkillsButton.contains(obj)) {
			if (highlighted !=(GRect) obj) highlighted.setFillColor(Color.DARK_GRAY);
			highlighted =(GRect) obj;
			highlighted.setFillColor(Color.LIGHT_GRAY);
			int index = allSkillsButton.indexOf(highlighted);
			Character c = (Character) currentEntity;
			description.setLabel(c.getMySkills()[index].getDescription());
			description.setFont("DialogInput-PLAIN-12");
			description.setLocation((800-description.getWidth())/2,20);
			if (index==2) description.setLocation((800-description.getWidth())/2,10);
		}
		else {
			highlighted.setFillColor(Color.DARK_GRAY);
		}
		if (skill==false && inventory==false && playersTurn == true ) {
			if (obj==skillButton) {
				skillButton.setFillColor(Color.LIGHT_GRAY);
			}
			else if (obj==inventoryButton) {
				inventoryButton.setFillColor(Color.LIGHT_GRAY);
			}
			else {
				skillButton.setFillColor(Color.DARK_GRAY);
				inventoryButton.setFillColor(Color.DARK_GRAY);
			}
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