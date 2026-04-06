
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
	
	private ArrayList<Entity> allEntities, temp,initiativeArr;
	private ArrayList<GImage> allImages;
	private ArrayList<GRect> allSkillsButton,healthBars,manaBars;
	private ArrayList<GLabel> allSkillsButtonLabels,healthLabels,manaLabels;
	
	private Character[] myArrAllies;
	private Enemy[] myArrEnemies;
	Skill[] mySkills;
	
	private GRect skillButton,inventoryButton,displayBox,extra,highlighted,mapButton,menuButton;
	private GLabel displayBoxLabel,description,TurnLabel,mapButtonLabel;
	
	private boolean skill,inventory,playersTurn,enemyTurn,forSkills,skillReady,on,won,lost;
	private int turn,counter,skillIndex,switched,enemyNumber;
	private double barSizeChar,barSizeEnemy;
	
	private Entity currentEntity,otherEntity;
	
	Timer t;
	

	public CombatPane(MainApplication mainScreen) {
		this.mainScreen = mainScreen;
	}

	@Override
	public void showContent() {
		t = new Timer(1000, this);
		hideContent();
		
		Character testChar = new Character("samurai");
		Character testChar2 = new Character("sorcerer");
		
		CharacterSelectionPane.myInventory.getPartyMembers()[1]=testChar;
		CharacterSelectionPane.myInventory.getPartyMembers()[2]=testChar2;
		allSkillsButton = new ArrayList<>();
	
		otherEntity = new Enemy();
		highlighted = new GRect(0,0);
		skill = false;
		inventory = false;
		forSkills = false;
		
		turn = 0;
		counter = 0;
		switched =0;
		
		skillButton = createButton(0,540,"Skills");
		inventoryButton = createButton(130,540,"Inventory");	
		

		
		generateEnemiesAndAllies();
		
		otherEntity = myArrEnemies[0]; //Temporary Fix for glitch
		update();
		nextCombat();
		
	}

	@Override
	public void hideContent() {
		for(GObject item : contents) {
			mainScreen.remove(item);
		}
		contents.clear();
	}
	
	/*
	 * Updates the Mana and Health Labels of Entities
	 * 
	 */
	public void update() {
		for (Entity entity:allEntities) {
			
			int index = allEntities.indexOf(entity);
			
			if (entity instanceof Enemy) {
				Enemy e = (Enemy) entity;
				manaLabels.get(index).setLabel("Mana: "+Math.round(e.getMana())+"/"+Math.round(e.getManaMax()));
				healthLabels.get(index).setLabel("Health: "+Math.round(e.getHp())+"/"+Math.round(e.getHpMax()));
				if (e.isDead()) {
					if(healthLabels.get(index).getLabel()!="Dead")initiativeArr.remove(e);
					healthLabels.get(index).setLabel("Dead");
					 yourDead(e);
				}
			}
			else if (entity instanceof Character) {
				Character c = (Character) entity;
				manaLabels.get(index).setLabel("Mana: "+Math.round(c.getMana())+"/"+Math.round(c.getManaMax()));
				healthLabels.get(index).setLabel("Health: "+Math.round(c.getHp())+"/"+Math.round(c.getHpMax()));
				updateHealthAndManaBarSize(c);
				if (!(c.getHp()>0)) {
					if(healthLabels.get(index).getLabel()!="Dead") System.out.println(c+" is dead Health: "+c.getHp());
					if(healthLabels.get(index).getLabel()!="Dead")initiativeArr.remove(c);
					healthLabels.get(index).setLabel("Dead");
					 yourDead(c);
				}
			}
		}
		
		
	}

	/*
	 * Creates Enemies based off party size
	 * Stores the Enemies in a basic array
	 */
	
	private void generateEnemiesAndAllies(){
		
		myArrAllies = CharacterSelectionPane.myInventory.getPartyMembers();
		enemyNumber = 0;
		allEntities = new ArrayList<>();
		temp = new ArrayList<>();
		
		for(int i = 0;i<myArrAllies.length;i++) {
			if (myArrAllies[i]!=null) {
				enemyNumber += 1;
				allEntities.add(myArrAllies[i]);
				temp.add(myArrAllies[i]);
			}
		}
		
		myArrEnemies = new Enemy[enemyNumber];
		
		for(int i = 0;i<enemyNumber;i++) {
			String sprite =  Chance.choose(new String[] {"holyghost","irongremlin","bladedevil","casper",
													     "magicsword","slime","orb","chefbot",
													     "boss_seraphim","boss_drip","boss_mage"});
			myArrEnemies[i] = new Enemy(sprite,0);
			allEntities.add(myArrEnemies[i]);
			temp.add(myArrEnemies[i]);
		}
		generateImages();
	}
	
	/*
	 * Stores the sprites of all Entites into an ArrayList
	 * Resizes the sprites as needed
	 */
	private void generateImages(){
		
		allImages = new ArrayList<>();
		
		for (Entity e:allEntities) {
			GImage image = e.getSprite();
			image.setSize(140, 140);
			allImages.add(image);
		}
		setLocationandAddToScreen();
	}
	
	/*
	 * Converts a GImage to its corresponding Entity
	 * @param image: the GImage that needs to be converted
	 * @return the Entity
	 */
	private Entity imageToEntity(GImage image){
		int index = allImages.lastIndexOf(image);
		Entity e = allEntities.get(index);
		return e;
	}
	
	/*
	 * Converts a Entity to its corresponding GImage
	 * @param e: the Entity that needs to be converted
	 * @return the GImage
	 */
	
	private GImage entityToImage(Entity e){
		int index = allEntities.indexOf(e);
		GImage image = allImages.get(index);
		return image;
	}
	
	/*
	 * Sets the Location for all GImages
	 */
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
		createHealthAndManaLabels();
	}
	
	/*
	 * 
	 * 
	 */
	
	public boolean checkResult() {
		won = true;
		lost = true;
		for(int i = 0;i<myArrAllies.length;i++) {
			if (myArrAllies[i]!=null) {
				if (myArrAllies[i].isDead()==false) lost = false;	
			}
		}
		
		for(int i = 0;i<myArrEnemies.length;i++) {
			if (myArrEnemies[i]!=null) {
				if (myArrEnemies[i].isDead()==false) won = false;
			}
		}
		
		if (won==true||lost==true) {
			if (won==true) {
				MapPane.currPosition.cleared();
				displayRewards();
			}
			else {
				mainScreen.switchToMapPane();
			}
			return true;
		}
		return false;
		
		
	}
	
	public void rollForInitiative() {
		
		initiativeArr = new ArrayList<>();
		
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
	
	public void EnemyAttack() {
		Enemy e = (Enemy) currentEntity;
		Character c = e.playTurn(this);
		otherEntity =c;
		description.setLabel(e+" attacks "+c.getProfession());
		description.setLocation((800-description.getWidth())/2,20);
	}
	
	public void nextCombat() {
		if (counter>0)entityToImage(currentEntity).setColor(null);
		update();
		if (checkResult()==true) return;
		
		
		counter = counter%initiativeArr.size();
		currentEntity = initiativeArr.get(counter);
		yourTurn(entityToImage(currentEntity));
		
		if (currentEntity instanceof Character) {
			playersTurn = true;
			Character c = (Character) currentEntity;
			
			if (c.getLastUsedSkill()!=null) {
				c.startTurn();
				update();
			}
			counter++;
		}
		else if (currentEntity instanceof Enemy) {
		    Timer timer = new Timer(3000, new ActionListener() {
		        @Override
		        public void actionPerformed(ActionEvent e) {
		            EnemyAttack();
		            counter++;
		            nextCombat();
		            
		        }
		    });
		    
		    timer.setRepeats(false);
		    timer.start();
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
		label.setLocation(x+(button.getWidth()-label.getWidth())/2, y+(button.getHeight()-label.getHeight())/2+15);
		
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
	
	public void createHealthAndManaLabels() {
		
		healthLabels = new ArrayList<>();
		manaLabels= new ArrayList<>();

		for (Entity e:allEntities) {
			
			GLabel health = new GLabel("Health: "+Math.round(e.getHp())+"/"+Math.round(e.getHpMax()));
			GLabel mana = new GLabel("Mana goes here");
			GImage image = entityToImage(e);
			
			health.setLocation(image.getX()+30,image.getY());
			healthLabels.add(health);
			contents.add(health);
			mainScreen.add(health);
			
			if (e instanceof Character) {
				
				Character c = (Character) e;
				
				mana = new GLabel("Mana: "+Math.round(c.getMana())+"/"+Math.round(c.getManaMax()));
				manaLabels.add(mana);
				contents.add(mana);
				mainScreen.add(mana);
				
			}
			else {
				manaLabels.add(mana);
			}
		}
		createStatsBar();
	}
	
	public void createStatsBar() {
		
		GRect health,mana,nameDisplay;
		GLabel manaLabel,name = new GLabel("Mana goes here");
		healthBars = new ArrayList<>();
		manaBars = new ArrayList<>();
		
		for (int i = 0;i<enemyNumber;i++) {
			int width = 540;
			health = new GRect(width,20);
			mana = new GRect(width,20);
			nameDisplay =  new GRect(width,20);
			name = new GLabel(myArrAllies[i].getProfession());
			
			switch(enemyNumber) {
			case 3:width =180;break;
			case 2:width =270;break;
			}
			barSizeChar = width;
			
			health.setSize(width, 20);
			mana.setSize(width, 20);
			nameDisplay.setSize(width, 20);
	
	
			health.setLocation(260+(width*i), inventoryButton.getY()+health.getHeight());
			mana.setLocation(260+(width*i), inventoryButton.getY()+health.getHeight()+20);
			nameDisplay.setLocation(260+(width*i), inventoryButton.getY());
			
			health.setFilled(true);
			health.setFillColor(Color.RED);
			mana.setFilled(true);
			mana.setFillColor(Color.CYAN);
			nameDisplay.setFilled(true);
			nameDisplay.setFillColor(Color.DARK_GRAY);
			
			healthBars.add(health);
			manaBars.add(mana);
			contents.add(health);
			mainScreen.add(health);
			contents.add(mana);
			mainScreen.add(mana);
			contents.add(nameDisplay);
			mainScreen.add(nameDisplay);
			
			healthLabels.get(i).setLocation(health.getX()+2,health.getY()+15);
			healthLabels.get(i).sendToFront();
			manaLabels.get(i).setLocation(mana.getX()+2,mana.getY()+15);
			manaLabels.get(i).sendToFront();
			
			name.setFont("DialogInput-PLAIN-18");
			name.setLocation(nameDisplay.getX()+(nameDisplay.getWidth()-name.getWidth())/2,nameDisplay.getY()+15);
			contents.add(name);
			mainScreen.add(name);
		}
		
		for (int i = 0;i<enemyNumber;i++) {
			
		}
		
	}
	public void updateHealthAndManaBarSize(Character c) {
		int index = allEntities.indexOf(c);
		GRect health = healthBars.get(index);
		GRect mana = manaBars.get(index);
		double ratio = c.getHp()/c.getHpMax();
		health.setSize(barSizeChar*ratio, health.getHeight());
		ratio = c.getMana()/c.getManaMax();
		mana.setSize(barSizeChar*ratio, mana.getHeight());
	}
	
	public boolean isDead() {
		if (otherEntity instanceof Enemy) {
			Enemy e = (Enemy) otherEntity;
			return e.isDead();
		}
		else if (otherEntity instanceof Character) {
			Character c = (Character) otherEntity;
			return c.isDead();
		}
		return false;
	}
	
	public void clearArrays() {
		allEntities.clear(); temp.clear(); initiativeArr.clear();
		allImages.clear(); allSkillsButton.clear(); healthBars.clear(); manaBars.clear();
		allSkillsButtonLabels.clear(); healthLabels.clear(); manaLabels.clear();
	}
	
	
	public void displaySkills(Character myChar) {
		
		mySkills =myChar.getMySkills();
		allSkillsButton = new ArrayList<>();
		allSkillsButtonLabels = new ArrayList<>();

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
	
	public void displayRewards() {
		
		displayBox = new GRect(340,200);
		displayBox.setLocation((800-displayBox.getWidth())/2,(600-displayBox.getHeight())/2);
		displayBox.setFilled(true);
		displayBox.setFillColor(Color.black);
        contents.add(displayBox);
		mainScreen.add(displayBox);
		
		mapButton = new GRect(160,50);
		mapButton.setLocation(displayBox.getX()+80, displayBox.getY()+130);
		mapButton.setFilled(true);
		mapButton.setFillColor(Color.DARK_GRAY);
		contents.add(mapButton);
		mainScreen.add(mapButton);
		
		mapButtonLabel = new GLabel("Continue to Map");
		mapButtonLabel.setFont("DialogInput-PLAIN-15");
		mapButtonLabel.setLocation(mapButton.getX() + (mapButton.getWidth() - mapButtonLabel.getWidth()) / 2, mapButton.getY() + (mapButton.getHeight() + mapButtonLabel.getAscent()) / 2);
		contents.add(mapButtonLabel);
		mainScreen.add(mapButtonLabel);
		
		GRect reward = new GRect(320,100);
		reward.setLocation(displayBox.getX()+10, displayBox.getY()+20);
		reward.setFilled(true);
		reward.setFillColor(Color.YELLOW);
		contents.add(reward);
		mainScreen.add(reward);
		
		GLabel rewardLabel = new GLabel("Reward goes here!");
		rewardLabel.setFont("DialogInput-PLAIN-20");
		rewardLabel.setLocation(reward.getX() + (reward.getWidth() - rewardLabel.getWidth()) / 2, reward.getY() + (reward.getHeight() + rewardLabel.getAscent()) / 2);
		contents.add(rewardLabel);
		mainScreen.add(rewardLabel);
		
		
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
	
	public Character[] aliveAllies() {
		int length =0;
		int num = 0;
		for (Character c:myArrAllies) {
			if(c!=null&&c.isDead()==false) {
				length++;
			}
		}
		Character[] myArr = new Character[length];
		
		for (Character c:myArrAllies) {
			if(c!=null&&c.isDead()==false) {
				myArr[num]=c;
				num++;
			}
		}
		return myArr;
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
				
				if (mySkills[skillIndex].preconditionsMet(currentEntity, otherEntity)) {
					if (mySkills[skillIndex].getvTarget()=="NA") {
						mySkills[skillIndex].activationEffect(currentEntity,otherEntity);
						Character c = (Character) currentEntity;
						c.setLastUsedSkill(mySkills[skillIndex]);
						nextCombat();
						
					}
					else {
						skillReady = true;
					}
					hideSkills();
				}
				else {
					description.setLabel("Not enough mana try again!");
					description.setFont("DialogInput-PLAIN-15");
					description.setLocation((800-description.getWidth())/2,20);
				}
				
			}
		}
		if (obj instanceof GImage && playersTurn == true && skillReady==true) {
			otherEntity = imageToEntity((GImage) obj);
			if (otherEntity instanceof Enemy) {
				
				mySkills[skillIndex].activationEffect(currentEntity,otherEntity);
				Character c = (Character) currentEntity;
				c.setLastUsedSkill(mySkills[skillIndex]);
				
				skill = false;
				skillReady = false;
				playersTurn = false;
				
				nextCombat();
			}
			else if (otherEntity instanceof Character && mySkills[skillIndex].getvTarget()=="CHARA") {
				mySkills[skillIndex].activationEffect(currentEntity,currentEntity);
				skill = false;
				skillReady = false;
				playersTurn = false;
				
				nextCombat();
			}
			else {
				description.setLabel("Thats not a target!");
				description.setFont("DialogInput-PLAIN-15");
				description.setLocation((800-description.getWidth())/2,20);
			}
	
		}
		update();
		if ((obj==mapButton||obj==mapButtonLabel)&&won==true) {
			clearArrays();
			mainScreen.switchToMapPane();
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
		//t.stop();
		
	}

	public ArrayList<Entity> getAllEntities() {
		return allEntities;
	}

	public void setAllEntities(ArrayList<Entity> allEntities) {
		this.allEntities = allEntities;
	}

	public ArrayList<GImage> getAllImages() {
		return allImages;
	}

	public void setAllImages(ArrayList<GImage> allImages) {
		this.allImages = allImages;
	}

	public ArrayList<Entity> getInitiativeArr() {
		return initiativeArr;
	}

	public void setInitiativeArr(ArrayList<Entity> initiativeArr) {
		this.initiativeArr = initiativeArr;
	}

	public ArrayList<Entity> getTemp() {
		return temp;
	}

	public void setTemp(ArrayList<Entity> temp) {
		this.temp = temp;
	}

	public Character[] getMyArrAllies() {
		return myArrAllies;
	}

	public void setMyArrAllies(Character[] myArrAllies) {
		this.myArrAllies = myArrAllies;
	}

	public Enemy[] getMyArrEnemies() {
		return myArrEnemies;
	}

	public void setMyArrEnemies(Enemy[] myArrEnemies) {
		this.myArrEnemies = myArrEnemies;
	}

	public int getEnemyNumber() {
		return enemyNumber;
	}

	public void setEnemyNumber(int enemyNumber) {
		this.enemyNumber = enemyNumber;
	}

	public boolean isSkill() {
		return skill;
	}

	public void setSkill(boolean skill) {
		this.skill = skill;
	}

	public boolean isInventory() {
		return inventory;
	}

	public void setInventory(boolean inventory) {
		this.inventory = inventory;
	}

	public boolean isPlayersTurn() {
		return playersTurn;
	}

	public void setPlayersTurn(boolean playersTurn) {
		this.playersTurn = playersTurn;
	}

	public boolean isEnemyTurn() {
		return enemyTurn;
	}

	public void setEnemyTurn(boolean enemyTurn) {
		this.enemyTurn = enemyTurn;
	}

	public boolean isForSkills() {
		return forSkills;
	}

	public void setForSkills(boolean forSkills) {
		this.forSkills = forSkills;
	}

	public boolean isSkillReady() {
		return skillReady;
	}

	public void setSkillReady(boolean skillReady) {
		this.skillReady = skillReady;
	}

	public boolean isOn() {
		return on;
	}

	public void setOn(boolean on) {
		this.on = on;
	}

	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public int getSkillIndex() {
		return skillIndex;
	}

	public void setSkillIndex(int skillIndex) {
		this.skillIndex = skillIndex;
	}

	public Entity getCurrentEntity() {
		return currentEntity;
	}

	public void setCurrentEntity(Entity currentEntity) {
		this.currentEntity = currentEntity;
	}

	public Entity getOtherEntity() {
		return otherEntity;
	}

	public void setOtherEntity(Entity otherEntity) {
		this.otherEntity = otherEntity;
	}

	public GRect getSkillButton() {
		return skillButton;
	}

	public void setSkillButton(GRect skillButton) {
		this.skillButton = skillButton;
	}

	public GRect getInventoryButton() {
		return inventoryButton;
	}

	public void setInventoryButton(GRect inventoryButton) {
		this.inventoryButton = inventoryButton;
	}

	public GRect getDisplayBox() {
		return displayBox;
	}

	public void setDisplayBox(GRect displayBox) {
		this.displayBox = displayBox;
	}

	public GRect getExtra() {
		return extra;
	}

	public void setExtra(GRect extra) {
		this.extra = extra;
	}

	public GRect getHighlighted() {
		return highlighted;
	}

	public void setHighlighted(GRect highlighted) {
		this.highlighted = highlighted;
	}

	public GLabel getDisplayBoxLabel() {
		return displayBoxLabel;
	}

	public void setDisplayBoxLabel(GLabel displayBoxLabel) {
		this.displayBoxLabel = displayBoxLabel;
	}

	public GLabel getDescription() {
		return description;
	}

	public void setDescription(GLabel description) {
		this.description = description;
	}

	public GLabel getTurnLabel() {
		return TurnLabel;
	}

	public void setTurnLabel(GLabel turnLabel) {
		TurnLabel = turnLabel;
	}

	public ArrayList<GRect> getAllSkillsButton() {
		return allSkillsButton;
	}

	public void setAllSkillsButton(ArrayList<GRect> allSkillsButton) {
		this.allSkillsButton = allSkillsButton;
	}

	public ArrayList<GRect> getHealthBars() {
		return healthBars;
	}

	public void setHealthBars(ArrayList<GRect> healthBars) {
		this.healthBars = healthBars;
	}

	public ArrayList<GRect> getManaBars() {
		return manaBars;
	}

	public void setManaBars(ArrayList<GRect> manaBars) {
		this.manaBars = manaBars;
	}

	public ArrayList<GLabel> getAllSkillsButtonLabels() {
		return allSkillsButtonLabels;
	}

	public void setAllSkillsButtonLabels(ArrayList<GLabel> allSkillsButtonLabels) {
		this.allSkillsButtonLabels = allSkillsButtonLabels;
	}

	public ArrayList<GLabel> getHealthLabels() {
		return healthLabels;
	}

	public void setHealthLabels(ArrayList<GLabel> healthLabels) {
		this.healthLabels = healthLabels;
	}

	public ArrayList<GLabel> getManaLabels() {
		return manaLabels;
	}

	public void setManaLabels(ArrayList<GLabel> manaLabels) {
		this.manaLabels = manaLabels;
	}

	public Skill[] getMySkills() {
		return mySkills;
	}

	public void setMySkills(Skill[] mySkills) {
		this.mySkills = mySkills;
	}

	public Timer getT() {
		return t;
	}

	public void setT(Timer t) {
		this.t = t;
	}
	

}