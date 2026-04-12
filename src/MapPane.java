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
	
	private ArrayList<Node> mapPath;
	private ArrayList<GObject> myNodeObjects;
	private ArrayList<GRect> extraArmorButtons,extraWeaponButtons,consumablesButtons,extra;
	private ArrayList<GLabel> extraArmorLabels,extraWeaponLabel,consumablesLabel,extraLabels;
	private boolean forWeapon,forArmor,forConsumable,inventoryOpen;
	public static Node currPosition;
	int count;
	GRect inventoryButton,closeButton;
	GLabel inventoryButtonLabel;
	
	public MapPane(MainApplication mainScreen) {
		this.mainScreen = mainScreen;
		mapPath = new ArrayList<>();
		createPath();
		currPosition = mapPath.get(0);
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
		createBackground();
		addText();
		addButtons();
		createMap();
	}

	@Override
	public void hideContent() {
		for(GObject item : contents) {
			mainScreen.remove(item);
		}
		contents.clear();
	}
	
	public void createPath() {
		for (int i=0;i<14;i++) {
			mapPath.add(new Node(i));
		}
	}
	
	private void addText() {
		
		GLabel title = new GLabel("Map", 100, 100);
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
		WeaponItem testWeapon = new WeaponItem(true);
		weapons.add(testWeapon);
		ArmorItem testArmor = new ArmorItem(true);
		armors.add(testArmor);
		
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
		extraWeaponButtons.clear();
		
		
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
		GRect backGround = new GRect(MainApplication.WINDOW_WIDTH,MainApplication.WINDOW_HEIGHT);
		backGround.setColor(Color.BLACK);
		backGround.setFillColor(Color.BLACK);
		backGround.setFilled(true);
		backGround.setLocation(0, 0);
		contents.add(backGround);
		mainScreen.add(backGround);
	}
	
	private void setColor(GOval oval) {
		String type =mapPath.get(myNodeObjects.lastIndexOf(oval)).getType();
		switch(type) {
		case "Shop":
			oval.setColor(Color.BLUE);
			oval.setFillColor(Color.BLUE);
			break;
		case "CampFire":
			oval.setColor(Color.ORANGE);
			oval.setFillColor(Color.ORANGE);
			break;
		case "Loot":
			oval.setColor(Color.BLACK);
			oval.setFillColor(Color.BLACK);
			break;
		default:
			oval.setColor(Color.RED);
			oval.setFillColor(Color.RED);
			break;
		}
		oval.setFilled(true);
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
		if (mainScreen.getElementAtLocation(e.getX(), e.getY()) == contents.get(1)) {
			mainScreen.switchToShopPane();
		}
		else if (obj == inventoryButton && inventoryOpen == false) {
			displayInventory();
			inventoryOpen = true;
		}
		else if (obj == inventoryButton && inventoryOpen == true) {
			hideInventory();
			inventoryOpen = false;
		}
	
		if (myNodeObjects.contains(obj)) {
			GObject oval = mainScreen.getElementAtLocation(e.getX(), e.getY());
			
			if (currPosition.hasAccess(myNodeObjects.indexOf(oval))==true) {
				currPosition = ovalToNode(oval);
				switch(ovalToNode(oval).getType()){
				case "Shop": mainScreen.switchToShopPane(); break;
				case "Combat": mainScreen.switchToCombatPane(); break;
				case "CampFire": mainScreen.switchToCampFirePane(); break;
				case "Loot": mainScreen.switchToLootPane(); break;
				}
			}
			else if (obj == nodeToOval(currPosition) && currPosition.isCleared()==false) {
				
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
