import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GLine;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GRect;

import java.util.ArrayList;

public class MapPane extends GraphicsPane {
	
	public static ArrayList<Node> mapPath;
	private ArrayList<GObject> myNodeObjects;
	private ArrayList<GRect> extraArmorButtons,extraWeaponButtons,consumablesButtons,extra,charButtons;
	private ArrayList<GLabel> extraArmorLabels,extraWeaponLabel,consumablesLabel,extraLabels,charLabels;
	private boolean forWeapon,forArmor,forConsumable,inventoryOpen,partyOpen;
	private WeaponItem weaponItem;
	private ArmorItem armorItem;
	private ConsumableItem consumableItem;
	public static Node currPosition;
	int count;
	GRect inventoryButton,closeButton;
	GLabel inventoryButtonLabel;
	
	public MapPane(MainApplication mainScreen) {
		this.mainScreen = mainScreen;
		mapPath = new ArrayList<>();
	}
	
	@Override
	public void showContent() {
		extraWeaponButtons = new ArrayList<>();
		extraWeaponLabel = new ArrayList<>();
		extraArmorButtons = new ArrayList<>();
		extraArmorLabels = new ArrayList<>();
		consumablesButtons = new ArrayList<>();
		consumablesLabel = new ArrayList<>();
		extra = new ArrayList<>();
		extraLabels = new ArrayList<>();
		myNodeObjects = new ArrayList<>();
		charButtons = new ArrayList<>();
		charLabels = new ArrayList<>();
		 partyOpen =false;
		createBackground();
		addText();
		addButtons();
		createMap();
		bossInfo();
	}

	@Override
	public void hideContent() {
		for(GObject item : contents) {
			mainScreen.remove(item);
		}
		contents.clear();
	}
	
	public static void createPath() {
		for (int i=0;i<14;i++) {
			mapPath.add(new Node(i));
		}
	}
	
	private void addText() {
		
		GLabel title = new GLabel("Map", MainApplication.WINDOW_WIDTH*(100.0/800.0), MainApplication.WINDOW_HEIGHT*(100.0/600.0));
		title.setColor(Color.RED);
		title.setFont("DialogInput-PLAIN-45");
		title.setLocation((mainScreen.getWidth() - title.getWidth()) / 2, 30);
		
		contents.add(title);
		mainScreen.add(title);
	}
	
	private void addButtons() {
		double buttonHeight = (MainApplication.WINDOW_HEIGHT/10.0);
		double buttonWidth = MainApplication.WINDOW_WIDTH*(200.0/800.0);
		
		inventoryButton = new GRect(buttonWidth,buttonHeight);
		inventoryButton.setFillColor(Color.DARK_GRAY);
		inventoryButton.setFilled(true);
		inventoryButton.setLocation(MainApplication.WINDOW_WIDTH-buttonWidth,MainApplication.WINDOW_HEIGHT-buttonHeight);
		contents.add(inventoryButton);
		mainScreen.add(inventoryButton);
		
		inventoryButtonLabel = new GLabel("Inventory");
		inventoryButtonLabel.setFont("DialogInput-BOLD-17");
		inventoryButtonLabel.setColor(Color.WHITE);
		inventoryButtonLabel.setLocation(inventoryButton.getX()+(buttonWidth-inventoryButtonLabel.getWidth())/2, inventoryButton.getY()+(buttonHeight-inventoryButtonLabel.getHeight())/2+inventoryButtonLabel.getHeight());
		contents.add(inventoryButtonLabel);
		mainScreen.add(inventoryButtonLabel);
	}
	
	private void displayInventory() {
		
		double buttonHeight = (MainApplication.WINDOW_HEIGHT/10.0);
		double buttonWidth = MainApplication.WINDOW_WIDTH*(200.0/800.0);
		double allBoxWidth = (buttonWidth+20)*3;
		
		PlayerInventory inventory = CharacterSelectionPane.myInventory;
		ArrayList<WeaponItem> weapons = inventory.getExtraWeapons();
		ArrayList<ArmorItem> armors = inventory.getExtraArmors();
		ConsumableItem[] consumables = inventory.getConsumables();
		
		GRect box1 = new GRect(buttonWidth+20,(buttonHeight*(weapons.size()+1))+20);
		box1.setLocation((MainApplication.WINDOW_WIDTH-allBoxWidth)/2+(box1.getWidth()*0), (MainApplication.WINDOW_HEIGHT-box1.getHeight())/2);
		box1.setFilled(true);
		box1.setColor(Color.BLUE);
		contents.add(box1);
		mainScreen.add(box1);
		extra.add(box1);
		
		GRect extraRect = new GRect(buttonWidth,buttonHeight);
		extraRect.setLocation(box1.getX()+10, box1.getY()+10);
		extraRect.setFilled(true);
		extraRect.setFillColor(Color.DARK_GRAY);
		contents.add(extraRect);
		mainScreen.add(extraRect);
		extra.add(extraRect);
		
		GLabel extraLabel = new GLabel("Extra Weapons");
		extraLabel.setColor(Color.BLACK);
		extraLabel.setFont("DialogInput-Bold-17");
		extraLabel.setLocation(extraRect.getX()+(extraRect.getWidth()-extraLabel.getWidth())/2, extraRect.getY()+(extraRect.getHeight()-extraLabel.getHeight())/2+extraLabel.getHeight());
		contents.add(extraLabel);
		mainScreen.add(extraLabel);
		extraLabels.add(extraLabel);
		
		for (int i = 0;i<weapons.size();i++) {
			GRect button = new GRect(buttonWidth,buttonHeight);
			button.setLocation(extraRect.getX(), (extraRect.getY()+extraRect.getHeight())+(i*buttonHeight));
			button.setFilled(true);
			button.setFillColor(Color.DARK_GRAY);
			contents.add(button);
			mainScreen.add(button);
			extraWeaponButtons.add(button);
			
			GLabel label = new GLabel(weapons.get(i).toString()+" (Affinity:"+weapons.get(i).getAffinity()+")");
			label.setColor(Color.WHITE);
			label.setLocation(button.getX()+(button.getWidth()-label.getWidth())/2, button.getY()+(button.getHeight()-label.getHeight())/2+label.getHeight());
			contents.add(label);
			mainScreen.add(label);
			extraWeaponLabel.add(label);
		}
		
		
		
		box1 = new GRect(buttonWidth+20,(buttonHeight*(armors.size()+1))+20);
		box1.setLocation((MainApplication.WINDOW_WIDTH-allBoxWidth)/2+(box1.getWidth()*1), (MainApplication.WINDOW_HEIGHT-box1.getHeight())/2);
		box1.setFilled(true);
		box1.setColor(Color.RED);
		contents.add(box1);
		mainScreen.add(box1);
		extra.add(box1);
		
		extraRect = new GRect(buttonWidth,buttonHeight);
		extraRect.setLocation(box1.getX()+10, box1.getY()+10);
		extraRect.setFilled(true);
		extraRect.setFillColor(Color.DARK_GRAY);
		contents.add(extraRect);
		mainScreen.add(extraRect);
		extra.add(extraRect);
		
		extraLabel = new GLabel("Extra Armors");
		extraLabel.setColor(Color.BLACK);
		extraLabel.setFont("DialogInput-Bold-17");
		extraLabel.setLocation(extraRect.getX()+(extraRect.getWidth()-extraLabel.getWidth())/2, extraRect.getY()+(extraRect.getHeight()-extraLabel.getHeight())/2+extraLabel.getHeight());
		contents.add(extraLabel);
		mainScreen.add(extraLabel);
		extraLabels.add(extraLabel);
		
		for (int i = 0;i<armors.size();i++) {
			GRect button = new GRect(buttonWidth,buttonHeight);
			button.setLocation(extraRect.getX(), (extraRect.getY()+extraRect.getHeight())+(i*buttonHeight));
			button.setFilled(true);
			button.setFillColor(Color.DARK_GRAY);
			contents.add(button);
			mainScreen.add(button);
			extraArmorButtons.add(button);
			
			GLabel label = new GLabel(armors.get(i).toString()+" (Affinity:"+armors.get(i).getAffinity()+")");
			label.setLocation(button.getX()+(button.getWidth()-label.getWidth())/2, button.getY()+(button.getHeight()-label.getHeight())/2+label.getHeight());
			label.setColor(Color.WHITE);
			contents.add(label);
			mainScreen.add(label);
			extraArmorLabels.add(label);
		}
		
		box1 = new GRect(buttonWidth+20,(buttonHeight*(consumables.length+1))+20);
		box1.setLocation((MainApplication.WINDOW_WIDTH-allBoxWidth)/2+(box1.getWidth()*2), (MainApplication.WINDOW_HEIGHT-box1.getHeight())/2);
		box1.setFilled(true);
		box1.setColor(Color.GREEN);
		contents.add(box1);
		mainScreen.add(box1);
		extra.add(box1);
		
		extraRect = new GRect(buttonWidth,buttonHeight);
		extraRect.setLocation(box1.getX()+10, box1.getY()+10);
		extraRect.setFilled(true);
		extraRect.setFillColor(Color.DARK_GRAY);
		contents.add(extraRect);
		mainScreen.add(extraRect);
		extra.add(extraRect);
		
		extraLabel = new GLabel("Consumables");
		extraLabel.setColor(Color.BLACK);
		extraLabel.setFont("DialogInput-Bold-17");
		extraLabel.setLocation(extraRect.getX()+(extraRect.getWidth()-extraLabel.getWidth())/2, extraRect.getY()+(extraRect.getHeight()-extraLabel.getHeight())/2+extraLabel.getHeight());
		contents.add(extraLabel);
		mainScreen.add(extraLabel);
		extraLabels.add(extraLabel);
		
		for (int i = 0;i<consumables.length;i++) {
			GLabel label;
			GRect button = new GRect(buttonWidth,buttonHeight);
			button.setLocation(extraRect.getX(), (extraRect.getY()+extraRect.getHeight())+(i*buttonHeight));
			button.setFilled(true);
			button.setFillColor(Color.DARK_GRAY);
			contents.add(button);
			mainScreen.add(button);
			consumablesButtons.add(button);
			if (consumables[i]!=null) {
				label = new GLabel(consumables[i].getType().toString());
			}
			else {
				label = new GLabel("Empty");
			}
			label.setLocation(button.getX()+(button.getWidth()-label.getWidth())/2, button.getY()+(button.getHeight()-label.getHeight())/2+label.getHeight());
			label.setColor(Color.WHITE);
			contents.add(label);
			mainScreen.add(label);
			consumablesLabel.add(label);
		}
		
		inventoryButtonLabel.setLabel("Exit Inventory");
		inventoryButtonLabel.setLocation(inventoryButton.getX()+(buttonWidth-inventoryButtonLabel.getWidth())/2, inventoryButton.getY()+(buttonHeight-inventoryButtonLabel.getHeight())/2+inventoryButtonLabel.getHeight());
		
	}
	
	private void  hideInventory() {
		
		double buttonHeight = (MainApplication.WINDOW_HEIGHT/10.0);
		double buttonWidth = MainApplication.WINDOW_WIDTH*(200.0/800.0);
		for (int i = 0;i<extraWeaponButtons.size();i++) {
			
			contents.remove(extraWeaponButtons.get(i));
			mainScreen.remove(extraWeaponButtons.get(i));
			
			contents.remove(extraWeaponLabel.get(i));
			mainScreen.remove(extraWeaponLabel.get(i));
		}
		extraWeaponButtons.clear();
		extraWeaponLabel.clear();
		
		
		for (int i = 0;i<extraArmorButtons.size();i++) {
			
			contents.remove(extraArmorButtons.get(i));
			mainScreen.remove(extraArmorButtons.get(i));
			
			contents.remove(extraArmorLabels.get(i));
			mainScreen.remove(extraArmorLabels.get(i));
		}
		extraArmorButtons.clear();
		extraArmorLabels.clear();
		
		for (int i = 0;i<consumablesButtons.size();i++) {
			
			contents.remove(consumablesButtons.get(i));
			mainScreen.remove(consumablesButtons.get(i));
			
			contents.remove(consumablesLabel.get(i));
			mainScreen.remove(consumablesLabel.get(i));
		}
		consumablesButtons.clear();
		consumablesLabel.clear();
		
		for (int i = 0;i<extra.size();i++) {
			
			contents.remove(extra.get(i));
			mainScreen.remove(extra.get(i));
		}
		
		extra.clear();
		
		for (int i = 0;i<extraLabels.size();i++) {
			contents.remove(extraLabels.get(i));
			mainScreen.remove(extraLabels.get(i));
		}
		
		extraLabels.clear();
		
		inventoryButtonLabel.setLabel("Exit Inventory");
		inventoryButtonLabel.setLocation(inventoryButton.getX()+(buttonWidth-inventoryButtonLabel.getWidth())/2, inventoryButton.getY()+(buttonHeight-inventoryButtonLabel.getHeight())/2+inventoryButtonLabel.getHeight());
		
	}
	
	public void displayParty() {
		
		partyOpen = true;
		double buttonHeight = (MainApplication.WINDOW_HEIGHT/10.0);
		double buttonWidth = MainApplication.WINDOW_WIDTH*(200.0/800.0);
		PlayerInventory inventory = CharacterSelectionPane.myInventory;
		int partySize = 0;
		Character[] myParty = inventory.getPartyMembers();
		
		for (int i = 0;i<myParty.length;i++){
			if (myParty[i]!=null) partySize++;
		}
		
		GRect box1 = new GRect(buttonWidth+20,(buttonHeight*(partySize+1))+20);
		box1.setLocation((MainApplication.WINDOW_WIDTH-box1.getWidth())/2+(box1.getWidth()*0), (MainApplication.WINDOW_HEIGHT-box1.getHeight())/2);
		box1.setFilled(true);
		box1.setColor(Color.MAGENTA);
		contents.add(box1);
		mainScreen.add(box1);
		extra.add(box1);
		
		GRect extraRect = new GRect(buttonWidth,buttonHeight);
		extraRect.setLocation(box1.getX()+10, box1.getY()+10);
		extraRect.setFilled(true);
		extraRect.setFillColor(Color.DARK_GRAY);
		contents.add(extraRect);
		mainScreen.add(extraRect);
		extra.add(extraRect);
		
		GLabel extraLabel = new GLabel("Select a Party Member to use the item");
		extraLabel.setLocation(extraRect.getX()+(extraRect.getWidth()-extraLabel.getWidth())/2, extraRect.getY()+(extraRect.getHeight()-extraLabel.getHeight())/2+extraLabel.getHeight());
		contents.add(extraLabel);
		mainScreen.add(extraLabel);
		extraLabels.add(extraLabel);
		
		for (int i = 0;i<partySize;i++) {
			GRect button = new GRect(buttonWidth,buttonHeight);
			button.setLocation(extraRect.getX(), (extraRect.getY()+extraRect.getHeight())+(i*buttonHeight));
			button.setFilled(true);
			button.setFillColor(Color.DARK_GRAY);
			contents.add(button);
			mainScreen.add(button);
			charButtons.add(button);
			
			GLabel label = new GLabel(myParty[i].toString());
			label.setLocation(button.getX()+(button.getWidth()-label.getWidth())/2, button.getY()+(button.getHeight()-label.getHeight())/2+label.getHeight());
			contents.add(label);
			mainScreen.add(label);
			charLabels.add(label);
		}
	}
	public void hideParty() {
		partyOpen = false;
		
		for (GRect rect:charButtons) {
			mainScreen.remove(rect);
			contents.remove(rect);
		}
		charButtons.clear();
		
		for (GLabel label:charLabels) {
			mainScreen.remove(label);
			contents.remove(label);
		}
		charLabels.clear();
		
		for (GRect rect: extra) {
			
			contents.remove(rect);
			mainScreen.remove(rect);
		}
		
		extra.clear();
		
		for (GLabel label:extraLabels) {
			mainScreen.remove(label);
			contents.remove(label);
		}
		
		extraLabels.clear();
	}
	
	
	
private void createMap() {
	count =0;
		double screenHeight = MainApplication.WINDOW_HEIGHT;
		createRow(1,((mainScreen.getWidth() - 40) / 2),screenHeight * (550.0/600.0));
		createRow(2,((mainScreen.getWidth() - 40) / 2)-50,screenHeight * (450.0/600.0));
		createRow(3,((mainScreen.getWidth() - 40) / 2)-100,screenHeight * (350.0/600.0));
		createRow(5,((mainScreen.getWidth() - 40) / 2)-200,screenHeight * (250.0/600.0));
		createRow(2,((mainScreen.getWidth() - 40) / 2)-50,screenHeight * (150.0/600.0));
		createRow(1,((mainScreen.getWidth() - 40) / 2),screenHeight * (50.0/600.0));
		drawLines();
		
	}

	public void drawLines() {
		for (int e = 0;e<myNodeObjects.size()-1;e++) {
			GImage image = mapPath.get(e).getSprite();
			Node node = mapPath.get(e);
			int[] myArr = node.accessibleNodes;
		
			for (int i:myArr) {
				GImage image2 = (GImage) myNodeObjects.get(i);
				GLine line = new GLine(image.getX()+25,image.getY()+33,image2.getX()+25,image2.getY()+33);
				line.setLineWidth(3);
				line.setColor(Color.RED);
				contents.add(line);
				mainScreen.add(line);
				image2.sendToFront();
			
			}
			image.sendToFront();
		}
	}
	
	private void createRow(int limit, double startX, double startY) {
		for (int i = 0;i<limit;i++) {
			GImage node = mapPath.get(count).getSprite();
			myNodeObjects.add(node);
			node.setLocation(startX, startY);
			contents.add(node);
			mainScreen.add(node);
			startX = startX+100;
			count++;
		}
	}
	
	private void createBackground() {
		GImage background = new GImage("MapTowerBackground.jpeg");
		background.setSize(1366,700);
		background.setLocation(0, 0);
		contents.add(background);
		mainScreen.add(background);
	}
	
	private void bossInfo() {
		GRect box = new GRect(182,182);
		box.setLocation(0,0);
		box.setFilled(true);
		box.setFillColor(new Color(0,0,0));
		contents.add(box);
		mainScreen.add(box);
		
		GRect infoBox = new GRect(222,182);
		infoBox.setLocation(box.getWidth(),box.getY());
		infoBox.setFilled(true);
		infoBox.setFillColor(new Color(0,0,0));
		contents.add(infoBox);
		mainScreen.add(infoBox);
		
		Enemy boss = mapPath.get(13).boss;
		GImage bossImage = boss.getSprite();
		bossImage.setSize(162, 162);
		bossImage.setLocation(10,10);
		contents.add(bossImage);
		mainScreen.add(bossImage);
		
		GLabel bossTitle = new GLabel("Boss");
		bossTitle.setColor(Color.RED);
		bossTitle.setFont("ARIEL-BOLD-25");
		bossTitle.setLocation(infoBox.getX(), infoBox.getY()+bossTitle.getHeight());
		contents.add(bossTitle);
		mainScreen.add(bossTitle);
		
		GLabel name = new GLabel(". Name: "+boss.getName());
		name.setColor(Color.WHITE);
		name.setFont("ARIEL-BOLD-13");
		name.setLocation(bossTitle.getX(), bossTitle.getY()+name.getHeight()*2);
		contents.add(name);
		mainScreen.add(name);
		
		GLabel res = new GLabel(resistances(boss.getName()));
		res.setColor(Color.WHITE);
		res.setFont("ARIEL-BOLD-13");
		res.setLocation(name.getX(), name.getY()+res.getHeight()*2);
		contents.add(res);
		mainScreen.add(res);
		
		GLabel vul = new GLabel(vulnerabilities(boss.getName()));
		vul.setColor(Color.WHITE);
		vul.setFont("ARIEL-BOLD-13");
		vul.setLocation(res.getX(), res.getY()+vul.getHeight()*2);
		contents.add(vul);
		mainScreen.add(vul);
		
		GLabel atk = new GLabel(attackType(boss.getName()));
		atk.setColor(Color.WHITE);
		atk.setFont("ARIEL-BOLD-13");
		atk.setLocation(vul.getX(), vul.getY()+atk.getHeight()*2);
		contents.add(atk);
		mainScreen.add(atk);
		 	
	}
	
	public String resistances(String name) {
		String str = "";
		
		switch (name) {
		case "Completely Original Boss": str = ". Resistances: All Affinities";
		break;
		case "Supreme Mage": str = ". Resistances: Magic";
		break;
		case "Seraphim Vassal": str = ". Resistances: Holy";
		break;
		case "Death Knight": str = ". Resistances: Fire";
		break;
		case "Spirit of Storms": str = ". Resistances: Electric";
		break;
		}
		return str;
	}
	
	public String vulnerabilities(String name) {
		String str = "";
		
		switch (name) {
		case "Completely Original Boss": str = ". Vulnerabilities: None";
		break;
		case "Supreme Mage": str = ". Vulnerabilities: Blast";
		break;
		case "Seraphim Vassal": str = ". Vulnerabilities: Fire";
		break;
		case "Death Knight": str = ". Vulnerabilities: Electric + Holy";
		break;
		case "Spirit of Storms": str = ". Vulnerabilities: Magic";
		break;
		}
		return str;
	}
	
	public String attackType(String name) {
		String str = "";
		
		switch (name) {
		case "Completely Original Boss": str = ". Attack Type: Slashing";
		break;
		case "Supreme Mage": str = ". Attack Type: Magic";
		break;
		case "Seraphim Vassal": str = ". Attack Type: Holy";
		break;
		case "Death Knight": str = ". Attack Type: Slashing + Fire";
		break;
		case "Spirit of Storms": str = ". Attack Type: Electric + Blast";
		break;
		}
		return str;
	}
	
	public Node ovalToNode(GObject obj) {
		return mapPath.get(myNodeObjects.indexOf(obj));
	}
	
	public GObject nodeToOval(Node node) {
		return myNodeObjects.get(mapPath.indexOf(node));
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		GObject obj = mainScreen.getElementAtLocation(e.getX(), e.getY());
		
		if (obj == inventoryButton && inventoryOpen == false && partyOpen ==false) {
			displayInventory();
			inventoryOpen = true;
		}
		else if (obj == inventoryButton && inventoryOpen == true) {
			
			double buttonHeight = (MainApplication.WINDOW_HEIGHT/10.0);
			double buttonWidth = MainApplication.WINDOW_WIDTH*(200.0/800.0);
			
			hideInventory();
				
			inventoryOpen = false;
			inventoryButtonLabel.setLabel("Inventory");
			inventoryButtonLabel.setLocation(inventoryButton.getX()+(buttonWidth-inventoryButtonLabel.getWidth())/2, inventoryButton.getY()+(buttonHeight-inventoryButtonLabel.getHeight())/2+inventoryButtonLabel.getHeight());
		}
		else if (obj == inventoryButton && partyOpen == true) {
			
			double buttonHeight = (MainApplication.WINDOW_HEIGHT/10.0);
			double buttonWidth = MainApplication.WINDOW_WIDTH*(200.0/800.0);
			
			hideParty();
			
			inventoryButtonLabel.setLabel("Inventory");
			inventoryButtonLabel.setLocation(inventoryButton.getX()+(buttonWidth-inventoryButtonLabel.getWidth())/2, inventoryButton.getY()+(buttonHeight-inventoryButtonLabel.getHeight())/2+inventoryButtonLabel.getHeight());
		}
		else if (inventoryOpen==true && extraWeaponButtons.contains(obj)) {
			int index = extraWeaponButtons.indexOf(obj);
			hideInventory();
			forWeapon = true;
			displayParty();
			PlayerInventory inventory = CharacterSelectionPane.myInventory;
			 weaponItem = inventory.getExtraWeapons().get(index);
			inventoryOpen = false;
		}
		else if (inventoryOpen==true && extraArmorButtons.contains(obj)) {
			int index = extraArmorButtons.indexOf(obj);
			hideInventory();
			forArmor = true;
			displayParty();
			PlayerInventory inventory = CharacterSelectionPane.myInventory;
			 armorItem = inventory.getExtraArmors().get(index);
			inventoryOpen = false;
		}
		else if (inventoryOpen==true && consumablesButtons.contains(obj)) {
			int index = consumablesButtons.indexOf(obj);
			PlayerInventory inventory = CharacterSelectionPane.myInventory;
			 consumableItem = inventory.getConsumables()[index];
			 
			 if (consumableItem!=null) {
				 hideInventory();
					forConsumable = true;
					displayParty();
					inventoryOpen = false;
			 }
		}

		if (charButtons.contains(obj)&& forWeapon ==true) {
			int index = charButtons.indexOf(obj);
			Character c = CharacterSelectionPane.myInventory.getPartyMembers()[index];
			
			System.out.println("Old weapon "+c.getWeapon().toString());
			WeaponItem temp = c.getWeapon();
			c.setWeapon(weaponItem);
			System.out.println("New weapon "+c.getWeapon().toString());
			
			CharacterSelectionPane.myInventory.getExtraWeapons().add(temp);
			CharacterSelectionPane.myInventory.getExtraWeapons().remove(weaponItem);
			
			hideParty();
			forWeapon =false;
		}
		else if (charButtons.contains(obj)&& forArmor ==true) {
			
			int index = charButtons.indexOf(obj);
			Character c = CharacterSelectionPane.myInventory.getPartyMembers()[index];
			
			System.out.println("Old Armor "+c.getArmor().toString());
			ArmorItem temp = c.getArmor();
			c.setArmor(armorItem);
			System.out.println("New Armor "+c.getArmor().toString());
			
			CharacterSelectionPane.myInventory.getExtraArmors().add(temp);
			CharacterSelectionPane.myInventory.getExtraArmors().remove(armorItem);
			
			hideParty();
			forArmor =false;
		}
		else if (charButtons.contains(obj)&& forConsumable ==true) {
			int index = charButtons.indexOf(obj);
			Character c = CharacterSelectionPane.myInventory.getPartyMembers()[index];

			System.out.println(consumableItem.getType().toString()+" Used on: "+c);
			consumableItem.use(c);
			consumableItem =null;
			
			hideParty();
			forConsumable =false;
		}
		
		if (myNodeObjects.contains(obj)) {
			GObject oval = mainScreen.getElementAtLocation(e.getX(), e.getY());
			
			if (currPosition.hasAccess(myNodeObjects.indexOf(oval))==true) {
				currPosition = ovalToNode(oval);
				GameSounds.playMapNodeAttach();
				switch(ovalToNode(oval).getType()){
				case "Shop": mainScreen.switchToShopPane(); break;
				case "Combat": mainScreen.switchToCombatPane(); break;
				case "CampFire": mainScreen.switchToCampFirePane(); break;
				case "Loot": mainScreen.switchToLootPane(); break;
				}
			}
			else if (obj == nodeToOval(currPosition) && currPosition.isCleared()==false) {
				GameSounds.playMapNodeAttach();
				switch(ovalToNode(oval).getType()){
				
				case "Shop": mainScreen.switchToShopPane(); break;
				case "Combat": mainScreen.switchToCombatPane(); break;
				case "CampFire": mainScreen.switchToCampFirePane(); break;
				case "Loot": mainScreen.switchToLootPane(); break;
				}
				
			}
			//&& currPosition.isCleared()
			
		}
				
	}
		

	
}
