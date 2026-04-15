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
	private ArrayList<GRect> allSkillsButton,healthBars,manaBars,allConsumableButton;
	private ArrayList<GLabel> allSkillsButtonLabels,healthLabels,manaLabels,allConsumableButtonLabels;
	private Character[] myArrAllies;
	private Enemy[] myArrEnemies;
	Skill[] mySkills;
	
	private GRect skillButton,inventoryButton,displayBox,extra,highlighted,mapButton,menuButton,closeButton;
	private GLabel displayBoxLabel,description,TurnLabel,mapButtonLabel,menuButtonLabel,intent,list;
	private GImage background;
	
	private boolean skill;
	private boolean inventory,playersTurn,enemyTurn,forSkills,skillReady,on,won,lost,forConsumable,skill2;
	private int turn,counter,skillIndex,switched,enemyNumber,allyNumber,test,scale;
	private double barSizeChar,barSizeEnemy,buttonHeight,buttonWidth,screenHeight,screenWidth;
	
	private Entity currentEntity,otherEntity;
	
	Timer t;
	

	public CombatPane(MainApplication mainScreen) {
		this.mainScreen = mainScreen;
		scale = 0;
	}

	@Override
	public void showContent() {
		t = new Timer(1000, this);
		hideContent();
		
		allSkillsButtonLabels = new ArrayList<>();
		allSkillsButton = new ArrayList<>();
		allConsumableButton = new ArrayList<>();
		allConsumableButtonLabels = new ArrayList<>();
	
		otherEntity = new Enemy();
		highlighted = new GRect(0,0);
		inventory = false;
		forSkills = false;
		forConsumable = false;
		skill=false;
		skill2 = false;
		
		test =0;
		turn = 0;
		counter = 0;
		switched =0;
			
		addButtons();
		
		
		generateEnemiesAndAllies();
		
		otherEntity = myArrEnemies[0]; //Temporary Fix for glitch
		update();
		nextCombat();
		
		
		background = null;
		switch (getNodeAffinity()) {
			case "combatHoly": 
				background = new GImage("spr_BACKGROUND_Holy.png");
				break;
			case "combatFire": 
				background = new GImage("spr_BACKGROUND_Fire.png");
				break;
			case "combatMagic": 
				background = new GImage("spr_BACKGROUND_Magic.png");
				break;
			case "combatLightning": 
				background = new GImage("spr_BACKGROUND_Lightning.jpg");
				break;
		}
//		int height = MainApplication.WINDOW_HEIGHT;
//		double ratio = MainApplication.WINDOW_HEIGHT / background.getHeight();
		
		background.setLocation(0, 0);
		background.setSize(MainApplication.WINDOW_WIDTH,MainApplication.WINDOW_HEIGHT);
		
		contents.add(background);
		mainScreen.add(background);
		
		background.sendToBack();
	}

	@Override
	public void hideContent() {
		for(GObject item : contents) {
			mainScreen.remove(item);
		}
		contents.clear();
	}
	
	public void addButtons() {
		screenHeight = MainApplication.WINDOW_HEIGHT;
		screenWidth = MainApplication.WINDOW_WIDTH;
		buttonHeight = (screenHeight/10.0);
		buttonWidth = screenWidth*(130.0/800.0);
		
		skillButton = createButton(0,screenHeight-buttonHeight,"Skills");
		inventoryButton = createButton(skillButton.getWidth(),screenHeight-buttonHeight,"Inventory");
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
				updateHealthAndManaBarSize(e);
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
		allEntities = new ArrayList<>();
		temp = new ArrayList<>();
		
		for(int i = 0;i<myArrAllies.length;i++) {
			if (myArrAllies[i]!=null) {
				allEntities.add(myArrAllies[i]);
				temp.add(myArrAllies[i]);
			}
		}
		
		String[] pool = getPool();
		myArrEnemies = new Enemy[pool.length];
		enemyNumber = pool.length;
		allyNumber = aliveAllies().length;
		int difficulty;
		
		if (scale==0) {
			difficulty = MapPane.currPosition.getDifficulty();
		}
		else {
			difficulty = MapPane.currPosition.getDifficulty()*(2*scale);
		}
		System.out.println("Difficulty: "+difficulty);
		

		for(int i = 0;i<pool.length;i++) {
			
			myArrEnemies[i] = new Enemy(pool[i],difficulty);
			Character[] targets = aliveAllies();
			myArrEnemies[i].setNextTarget(targets[Chance.range(0, targets.length - 1)]);
			System.out.println(myArrEnemies[i].getIntent());
			allEntities.add(myArrEnemies[i]);
			temp.add(myArrEnemies[i]);
		}
		generateImages();
	}
	
	private String[] getPool() {
		String affinity = MapPane.currPosition.getCombatAffinity();
		int index = MapPane.currPosition.getIndex();
		String[][] pool = null;
		
		if (index>10 && index !=13) {
			switch(affinity) {
			case "combatHoly": pool = Enemy.getHolyEnemiesHard();
			case "combatFire": pool = Enemy.getFireEnemiesHard();
			case "combatMagic": pool = Enemy.getMageEnemiesHard();
			case "combatLightning": pool = Enemy.getElecEnemiesHard();
			
			}
		}
		else if (index<10) {
			switch(affinity) {
			case "combatHoly": pool = Enemy.getHolyEnemiesEasy();
			case "combatFire": pool = Enemy.getFireEnemiesEasy();
			case "combatMagic": pool = Enemy.getMageEnemiesEasy();
			case "combatLightning": pool = Enemy.getElecEnemiesEasy();
			}
		}
		else {
			pool = Enemy.getBossEnemies();
		}
		
		int tier = Chance.range(0, pool.length-1);
		return pool[tier];
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
		try {
		int index = allEntities.indexOf(e);
		GImage image = allImages.get(index);
		return image;
	} catch (ArrayIndexOutOfBoundsException a) {
		System.out.println("");
	}
		return null;
	}
	
	/*
	 * Sets the Location for all GImages
	 */
	private void setLocationandAddToScreen(){
		int i = 0;
		for (Character c:myArrAllies) {
			if (c!=null) {
				GImage image = entityToImage(c);
				if (i==0) image.setLocation(0, (i*(screenHeight/allyNumber))+((screenHeight/allyNumber-image.getHeight())/2));
				else if (i==1) image.setLocation(0, (i*(screenHeight/allyNumber))+((screenHeight/allyNumber-image.getHeight())/2)-10);
				else if (i==2) image.setLocation(0, (i*(screenHeight/allyNumber))+((screenHeight/allyNumber-image.getHeight())/2)-45);
				contents.add(image);
				mainScreen.add(image);
				i++;
			}
		}
		
		i = 0;
		for (Enemy c:myArrEnemies) {
			if (c!=null) {
				GImage image = entityToImage(c);
				if (i==0) image.setLocation(screenWidth-image.getWidth(), (i*(screenHeight/enemyNumber))+((screenHeight/enemyNumber-image.getHeight())/2)+20);
				if (i==1) image.setLocation(screenWidth-image.getWidth(), (i*(screenHeight/enemyNumber))+((screenHeight/enemyNumber-image.getHeight())/2)-10);
				if (i==2) image.setLocation(screenWidth-image.getWidth(), (i*(screenHeight/enemyNumber))+((screenHeight/enemyNumber-image.getHeight())/2)-50);
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
				if (myArrAllies[i].getHp()>0) {
					lost = false;	
				}
				else {
					myArrAllies[i].setHp(0);
				}
			}
		}
		
		for(int i = 0;i<myArrEnemies.length;i++) {
			if (myArrEnemies[i]!=null) {
				if (myArrEnemies[i].getHp()>0) won = false;
			}
		}
		
		if (won==true||lost==true) {
			if (won==true) {
				MapPane.currPosition.cleared();
				displayRewards();
			}
			else {
				displayGameOver();
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
		description.setLocation((screenWidth-description.getWidth())/2,screenHeight*(20.0/600.0));
	}
	
	public void nextCombat() {
		if (counter>0)entityToImage(currentEntity).setColor(null);
		update();
		
		
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
		            return;
		            
		        }
		    });
		    
		    timer.setRepeats(false);
		    timer.start();
		}
		if (checkResult()==true) return;
	}
	
	public GRect createButton(double x,double y,String str){
		GRect button = new GRect(buttonWidth,buttonHeight);
		if (forSkills==true || forConsumable == true) button.setSize(screenWidth*(320.0/800.0), buttonHeight);
		button.setLocation(x,y);
		button.setFilled(true);
        button.setFillColor(Color.DARK_GRAY);
        contents.add(button);
		mainScreen.add(button);
		
		GLabel label = new GLabel(str);
	
		label.setFont("DialogInput-PLAIN-15");
		label.setLocation(x+(button.getWidth()-label.getWidth())/2, y+(button.getHeight()-label.getHeight())/2+15);
		
		if (str == "Skill List"||str == "Consumable List") list = label;
		if (forSkills==true && str != "Skill List") allSkillsButtonLabels.add(label);
		if (forConsumable==true && str != "Consumable List") allConsumableButtonLabels.add(label);
		
		contents.add(label);
		mainScreen.add(label);
		return button;
	}
	
	public void addText() {
		description = new GLabel("Click a Action");
		description.setColor(Color.GREEN);
		intent = new GLabel("");
		description.setFont("DialogInput-PLAIN-15");
		intent.setFont("DialogInput-PLAIN-15");
		intent.setColor(Color.GREEN);
		description.setLocation((screenWidth-description.getWidth())/2,screenHeight*(50.0/600.0));
		intent.setLocation((screenWidth-intent.getWidth())/2, screenHeight *(70.0/600.0));
		contents.add(description);
		mainScreen.add(description);
		contents.add(intent);
		mainScreen.add(intent);
		
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
		double barHeight = inventoryButton.getHeight()/3.0;
		
		
		for (int i = 0;i<allyNumber;i++) {
			double width = screenWidth*(540.0/800);
			health = new GRect(width,barHeight);
			mana = new GRect(width,barHeight);
			nameDisplay =  new GRect(width,barHeight);
			name = new GLabel(myArrAllies[i].getProfession());
			
			switch(allyNumber) {
			case 3:width = screenWidth*(180.0/800);break;
			case 2:width = screenWidth*(270.0/800);break;
			}
			barSizeChar = width;
			
			health.setSize(width, barHeight);
			mana.setSize(width, barHeight);
			nameDisplay.setSize(width, barHeight);
	
			nameDisplay.setLocation(buttonWidth*2.0+(width*i), inventoryButton.getY());
			health.setLocation(buttonWidth*2.0+(width*i), nameDisplay.getY()+nameDisplay.getHeight());
			mana.setLocation(buttonWidth*2.0+(width*i), health.getY()+health.getHeight());
			
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
		
		barSizeEnemy = 137;
		for (int i = 0;i<enemyNumber;i++) {
			health = new GRect(barSizeEnemy,15);
			int index = allEntities.indexOf(myArrEnemies[i]);
			GLabel healthLabel = healthLabels.get(index);
			GImage image = allImages.get(index);
			health.setLocation(allImages.get(index).getX(), allImages.get(index).getY());
			healthLabel.setLocation(health.getX(), health.getY()+15);
			health.setColor(Color.RED);
			health.setFilled(true);
			healthBars.add(health);
			contents.add(health);
			mainScreen.add(health);
			healthLabel.sendToFront();
		}
		
	}
	public void updateHealthAndManaBarSize(Character c) {
		try {
		int index = allEntities.indexOf(c);
		GRect health = healthBars.get(index);
		GRect mana = manaBars.get(index);
		double ratio = c.getHp()/c.getHpMax();
		health.setSize(barSizeChar*ratio, health.getHeight());
		ratio = c.getMana()/c.getManaMax();
		mana.setSize(barSizeChar*ratio, mana.getHeight());
		}
		catch (ArrayIndexOutOfBoundsException e) {
			System.out.println();
		}
	}
	
	
	public void updateHealthAndManaBarSize(Enemy e) {
		int index = allEntities.indexOf(e);
		GRect health = healthBars.get(index);
		double ratio = e.getHp()/e.getHpMax();
		health.setSize(barSizeEnemy*ratio, health.getHeight());
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
		
		skill = true;
		double skillWidth = screenWidth*(320.0/800.0);
		mySkills =myChar.getMySkills();
		allSkillsButton = new ArrayList<>();

		displayBox = new GRect(skillWidth+20,buttonHeight*(mySkills.length+2)+20);
		displayBox.setLocation((screenWidth-displayBox.getWidth())/2,(screenHeight-displayBox.getHeight())/2);
		displayBox.setFilled(true);
		displayBox.setFillColor(new Color(37,99,235));
        contents.add(displayBox);
		mainScreen.add(displayBox);
		
		forSkills = true;
		extra = createButton(displayBox.getX()+10,displayBox.getY()+10,"Skill List");
		
		for (int i = 0;i<mySkills.length;i++) {
			String label = mySkills[i].getName();
			double manaCost = mySkills[i].getManaCost();
			if (manaCost > 0) {
				label += " (Costs " + (int)manaCost + " mana)";
			}
			GRect skill1 = createButton(extra.getX(),(extra.getY()+extra.getHeight())+(i*buttonHeight),label);
			allSkillsButton.add(skill1);
		}
		closeButton = createButton(extra.getX(),extra.getY()+(buttonHeight*(mySkills.length+1)),"Close");
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
		contents.remove(list);
		mainScreen.remove(list);
		contents.remove(closeButton);
		mainScreen.remove(closeButton);
		allSkillsButtonLabels.clear();
		forSkills =false;
	}
		
	public void showConsumables() {
		double consumableWidth = screenWidth*(320.0/800.0);
		ConsumableItem[] myConsumables = CharacterSelectionPane.myInventory.getConsumables();
		forConsumable = true;
		
		
		displayBox = new GRect(consumableWidth+20,buttonHeight*(myConsumables.length+2)+20);
		displayBox.setLocation((screenWidth-displayBox.getWidth())/2,(screenHeight-displayBox.getHeight())/2);
		displayBox.setFilled(true);
		displayBox.setFillColor(Color.MAGENTA);
        contents.add(displayBox);
		mainScreen.add(displayBox);
		
		
		extra = createButton(displayBox.getX()+10,displayBox.getY()+10,"Consumable List");
		
		for (int i = 0;i<myConsumables.length;i++) {
			if (myConsumables[i]!=null) {
				
				String label = myConsumables[i].getType().toString();
				GRect rect = createButton(extra.getX(),(extra.getY()+extra.getHeight())+(i*buttonHeight),label);
				allConsumableButton.add(rect);
				
			}
			else {
				GRect rect = createButton((displayBox.getX())+((displayBox.getWidth()-consumableWidth)/2),displayBox.getY()+10+(i*buttonHeight)+buttonHeight,"Empty");
				allConsumableButton.add(rect);
			}
			
		}
		closeButton = createButton(extra.getX(),extra.getY()+(buttonHeight*(myConsumables.length+1)),"Close");
	}
	
	public void hideConsumables() {
		for (GRect rect:allConsumableButton) {
			contents.remove(rect);
			mainScreen.remove(rect);
		}
		allConsumableButton.clear();
		
		for (GLabel label:allConsumableButtonLabels) {
			contents.remove(label);
			mainScreen.remove(label);
		}
		contents.remove(displayBox);
		mainScreen.remove(displayBox);
		contents.remove(extra);
		mainScreen.remove(extra);
		contents.remove(list);
		mainScreen.remove(list);
		contents.remove(closeButton);
		mainScreen.remove(closeButton);
		allConsumableButtonLabels.clear();
		forConsumable =false;
	}
	
	public void displayRewards() {
		String[] possibleRewards = {"weapon","armor"};
		String rewardType = Chance.choose(possibleRewards);
		WeaponItem weapon = getWeaponReward();
		ArmorItem armor = getArmorReward();
		int gold = Chance.range(150, 200);
		
		displayBox = new GRect(screenWidth*(340.0/800.0),screenHeight*(200.0/600.0));
		displayBox.setLocation((screenWidth-displayBox.getWidth())/2,(screenHeight-displayBox.getHeight())/2);
		displayBox.setFilled(true);
		displayBox.setFillColor(new Color(11,35,64));
        contents.add(displayBox);
		mainScreen.add(displayBox);
		
		GRect reward = new GRect(displayBox.getWidth(),displayBox.getHeight()/2);
		reward.setLocation(displayBox.getX(), displayBox.getY());
		reward.setFilled(true);
		reward.setFillColor(new Color(47, 49, 51));
		contents.add(reward);
		mainScreen.add(reward);
		
		GLabel rewardTitle = new GLabel("Rewards");
		rewardTitle.setFont("SERIF-PLAIN-40");
		rewardTitle.setLocation(reward.getX()+(reward.getWidth()-rewardTitle.getWidth())/2, reward.getY()+rewardTitle.getHeight());
		contents.add(rewardTitle);
		mainScreen.add(rewardTitle);
		
		GLabel rewardItem =null;
		
		switch(rewardType) {
		case "weapon":
			rewardItem = new GLabel("Weapon Found!: "+weapon.toString());
			CharacterSelectionPane.myInventory.addWeapon(weapon);
			break;
		default:
			rewardItem = new GLabel("Armor Found!: "+armor.toString());
			CharacterSelectionPane.myInventory.addArmor(armor);
			break;
		}
		
		int oldGold = CharacterSelectionPane.myInventory.getGold();
		CharacterSelectionPane.myInventory.setGold(oldGold+gold);
		
		rewardItem.setFont("DialogInput-PLAIN-15");
		rewardItem.setLocation(reward.getX()+(reward.getWidth()-rewardItem.getWidth())/2, rewardTitle.getY()+rewardItem.getHeight()+rewardItem.getHeight()/2);
		rewardItem.setColor(new Color(192,192,192));
		contents.add(rewardItem);
		mainScreen.add(rewardItem);
		
		GLabel goldLabel = new GLabel("Gold Earned: "+gold);
		goldLabel.setFont("DialogInput-PLAIN-15");
		goldLabel.setLocation(reward.getX()+(reward.getWidth()-goldLabel.getWidth())/2,rewardItem.getY()+goldLabel.getHeight());
		goldLabel.setColor(Color.YELLOW);
		contents.add(goldLabel);
		mainScreen.add(goldLabel);
		
		mapButton = new GRect(screenWidth*(160.0/800.0),screenHeight *(50.0/600.0));
		mapButton.setLocation(displayBox.getX()+(displayBox.getWidth()-mapButton.getWidth())/2,reward.getY()+reward.getHeight()+reward.getHeight()/4);
		mapButton.setFilled(true);
		mapButton.setFillColor(new Color(47, 49, 51));
		contents.add(mapButton);
		mainScreen.add(mapButton);
		
		mapButtonLabel = new GLabel("Continue to Map");
		mapButtonLabel.setFont("DialogInput-PLAIN-15");
		mapButtonLabel.setLocation(mapButton.getX() + (mapButton.getWidth() - mapButtonLabel.getWidth()) / 2, mapButton.getY() + (mapButton.getHeight() + mapButtonLabel.getAscent()) / 2);
		contents.add(mapButtonLabel);
		mainScreen.add(mapButtonLabel);
		
		
	}
	
	public WeaponItem getWeaponReward() {
		String type = MapPane.currPosition.getCombatAffinity();
		WeaponItem weapon = null;
		
		switch(type) {
		case "combatMagic":
			weapon = new WeaponItem("magic");
			break;
		case "combatFire":
			weapon = new WeaponItem("fire");
			break;
		case "combatHoly":
			weapon = new WeaponItem("holy");
			break;
		case "combatLightning":
			weapon = new WeaponItem("lightning");
			break;	
		}
		return weapon;
	}
	
	public ArmorItem getArmorReward() {
		String type = MapPane.currPosition.getCombatAffinity();
		ArmorItem armor = null;
		
		switch(type) {
		case "combatMagic":
			armor = new ArmorItem("magic");
			break;
		case "combatFire":
			armor = new ArmorItem("fire");
			break;
		case "combatHoly":
			armor = new ArmorItem("holy");
			break;
		case "combatLightning":
			armor = new ArmorItem("lightning");
			break;	
		}
		return armor;
	}
		
	
	public void displayGameOver() {
		GRect backround = new GRect(MainApplication.WINDOW_WIDTH,MainApplication.WINDOW_HEIGHT);
		backround.setFilled(true);
		backround.setFillColor(Color.RED);
		contents.add(backround);
		mainScreen.add(backround);
		
		GLabel label = new GLabel("Game Over!");
		label.setFont("SERIF-PLAIN-150");
		label.setLocation((MainApplication.WINDOW_WIDTH-label.getWidth())/2,label.getHeight());
		contents.add(label);
		mainScreen.add(label);
		
		menuButton = new GRect(MainApplication.WINDOW_WIDTH*(200.0/800.0),MainApplication.WINDOW_HEIGHT*(70.0/600.0));
		menuButton.setLocation((MainApplication.WINDOW_WIDTH-menuButton.getWidth())/2, MainApplication.WINDOW_HEIGHT/2);
		menuButton.setFilled(true);
		menuButton.setFillColor(Color.DARK_GRAY);
		contents.add(menuButton);
		mainScreen.add(menuButton);
		menuButton.sendToFront();
		
		menuButtonLabel = new GLabel("Return to Menu");
		menuButtonLabel.setFont("DialogInput-PLAIN-25");
		menuButtonLabel.setLocation(menuButton.getX() + (menuButton.getWidth() - menuButtonLabel.getWidth()) / 2, menuButton.getY() + (menuButton.getHeight() + menuButtonLabel.getAscent()) / 2);
		contents.add(menuButtonLabel);
		mainScreen.add(menuButtonLabel);
		
		
		
		
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
	
	public Enemy[] aliveEnemies() {
		int length =0;
		int num = 0;
		for (Enemy e :myArrEnemies) {
			if(e!=null&&e.isDead()==false) {
				length++;
			}
		}
		Enemy[] myArr = new Enemy[length];
		
		for (Enemy e :myArrEnemies) {
			if(e!=null&&e.isDead()==false) {
				myArr[num]=e;
				num++;
			}
		}
		return myArr;
	}
	
	public void mouseClicked(MouseEvent e) {
		test++;
		if (test==3) {
			System.out.println();
		}
		GObject obj = mainScreen.getElementAtLocation(e.getX(), e.getY());
		if (obj == skillButton && playersTurn == true && forConsumable == false && skill ==false) {
			displaySkills((Character) currentEntity);
		}
		else if (obj == inventoryButton && playersTurn == true && forSkills == false && forConsumable == false) {
			showConsumables();
		}
		else if (obj == closeButton && skill == true) {
			hideSkills();
			skill =false;
		}
		
		else if (allSkillsButton.size()!=0 && playersTurn == true && skill==true) {
			
			if (allSkillsButton.contains(obj)) {
				skillIndex = allSkillsButton.indexOf(obj);
				
				if (mySkills[skillIndex].preconditionsMet(currentEntity, currentEntity)) {
					if (mySkills[skillIndex].getvTarget()=="NA") {
						mySkills[skillIndex].activationEffect(currentEntity,otherEntity);
						skill = false;
						Character c = (Character) currentEntity;
						c.setLastUsedSkill(mySkills[skillIndex]);
						hideSkills();
						nextCombat();
						return;
						
					}
					else {
						hideSkills();
						skillReady = true;
					}
				}
				else {
					description.setLabel("Not enough mana try again!");
					description.setFont("DialogInput-PLAIN-15");
					description.setLocation((screenWidth-description.getWidth())/2,screenHeight*(20.0/600.0));
				}
				
			}
		}
		
		if (allConsumableButton.size()!=0 && playersTurn == true && forConsumable==true) {
			if (allConsumableButton.contains(obj)) {
				if (CharacterSelectionPane.myInventory.getConsumables()[allConsumableButton.indexOf(obj)]!=null) {
					
					CharacterSelectionPane.myInventory.getConsumables()[allConsumableButton.indexOf(obj)].use(currentEntity);
					CharacterSelectionPane.myInventory.getConsumables()[allConsumableButton.indexOf(obj)] = null;
					
					GRect button = (GRect) obj;
					GLabel label = allConsumableButtonLabels.get(allConsumableButton.indexOf(obj));
					label.setLabel("Empty");
					label.setLocation(button.getX()+(button.getWidth()-label.getWidth())/2, button.getY()+(button.getHeight()-label.getHeight())/2+15);
					update();
					
				}
				else {
					description.setLabel("Empty try again!");
					description.setFont("DialogInput-PLAIN-15");
				}
				
			}
			else if (obj == closeButton) {
				hideConsumables();
			}
		}
		
		if (obj instanceof GImage && playersTurn == true && skillReady==true) {
			otherEntity = imageToEntity((GImage) obj);
			if (otherEntity instanceof Enemy && mySkills[skillIndex].getvTarget()=="ENEMY") {
				
				mySkills[skillIndex].activationEffect(currentEntity,otherEntity);
				Character c = (Character) currentEntity;
				c.setLastUsedSkill(mySkills[skillIndex]);
				skill =false;
				skillReady = false;
				playersTurn = false;
				
				nextCombat();
				return;
			}
			else if (otherEntity instanceof Character && mySkills[skillIndex].getvTarget()=="CHARA") {
				mySkills[skillIndex].activationEffect(currentEntity,otherEntity);
				skill = false;
				skillReady = false;
				playersTurn = false;
				Character c = (Character) currentEntity;
				c.setLastUsedSkill(mySkills[skillIndex]);
				
				nextCombat();
				return;
			}
			else {
				description.setLabel("Thats not a target!");
				description.setFont("DialogInput-PLAIN-15");
				description.setLocation((screenWidth-description.getWidth())/2,screenHeight*(20.0/600.0));
			}
	
		}
		update();
		if ((obj==mapButton||obj==mapButtonLabel)&&won==true && MapPane.currPosition.isBoss==false) {
			entityToImage(currentEntity).setColor(null);
			clearArrays();
			mainScreen.switchToMapPane();
		}
		else if ((obj==mapButton||obj==mapButtonLabel)&&won==true && MapPane.currPosition.isBoss== true) {
			entityToImage(currentEntity).setColor(null);
			clearArrays();
			scale++;
			mainScreen.switchToMapPane();
			MapPane.mapPath.clear();
			MapPane.createPath();
			MapPane.currPosition = MapPane.mapPath.get(0);
			
		}
		else if ((obj==menuButton||obj==menuButtonLabel)&&lost==true) {
			clearArrays();
			mainScreen.switchToMenuPane();
		}
		
	}
	
	public void mouseMoved(MouseEvent e) {
		
		GObject obj = mainScreen.getElementAtLocation(e.getX(), e.getY());
		
		if (skill == true && allSkillsButton.contains(obj)) {
			if (highlighted !=(GRect) obj) highlighted.setFillColor(Color.DARK_GRAY);
			highlighted =(GRect) obj;
			highlighted.setFillColor(Color.LIGHT_GRAY);
			int index = allSkillsButton.indexOf(highlighted);
			Character c = (Character) currentEntity;
			Skill temp = c.getMySkills()[index];
			description.setLabel(temp.getDescription());
			description.setFont("DialogInput-PLAIN-12");
			description.setLocation((screenWidth-description.getWidth())/2,screenHeight*(20.0/600.0));
			if (index==2) description.setLocation((screenWidth-description.getWidth())/2,screenHeight*(10.0/600.0));
		}
		else if (skill == true && closeButton == obj) {
			
			if (highlighted !=(GRect) obj) highlighted.setFillColor(Color.DARK_GRAY);
			highlighted =(GRect) obj;
			highlighted.setFillColor(Color.LIGHT_GRAY);
		}
		else if (forConsumable == true && allConsumableButton.contains(obj)) {
			
			if (highlighted !=(GRect) obj) highlighted.setFillColor(Color.DARK_GRAY);
			highlighted =(GRect) obj;
			highlighted.setFillColor(Color.LIGHT_GRAY);
		}
		else if (forConsumable == true && obj == closeButton) {
			
			if (highlighted !=(GRect) obj) highlighted.setFillColor(Color.DARK_GRAY);
			highlighted =(GRect) obj;
			highlighted.setFillColor(Color.LIGHT_GRAY);
		}
		
		else if (skill==false && inventory==false && playersTurn == true ) {
			if (obj==skillButton) {
				if (highlighted !=(GRect) obj) highlighted.setFillColor(Color.DARK_GRAY);
				skillButton.setFillColor(Color.LIGHT_GRAY);
				highlighted = skillButton;
			}
			else if (obj==inventoryButton) {
				if (highlighted !=(GRect) obj) highlighted.setFillColor(Color.DARK_GRAY);
				inventoryButton.setFillColor(Color.LIGHT_GRAY);
				highlighted = inventoryButton;
			}
			else {
				highlighted.setFillColor(Color.DARK_GRAY);
			}
		}
		else {
			highlighted.setFillColor(Color.DARK_GRAY);
		}
		
		if (obj instanceof GImage && obj != background) {
		
			Entity potentialEnemy = imageToEntity((GImage) obj);
			if (potentialEnemy instanceof Enemy) {
				Enemy enemy = (Enemy) potentialEnemy;
				intent.setLabel(enemy.getIntent());
				intent.setLocation((screenWidth-intent.getWidth())/2, screenHeight*(70.0/600.0));
			}
			
		}
		else {
			intent.setLabel("");
			intent.setLocation((screenWidth-intent.getWidth())/2, screenHeight*(70.0/600.0));
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
	
	public String getNodeAffinity () {
		return MapPane.currPosition.getCombatAffinity();
	}
}
