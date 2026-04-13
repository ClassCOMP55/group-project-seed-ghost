import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;
import java.util.ArrayList;

//The ShopPane
public class ShopPane extends GraphicsPane {
	public static final int WINDOW_WIDTH = 1366;
	public static final int WINDOW_HEIGHT = 700;

	
	private ShopInventory inventory;
	private PlayerInventory playerInventory;
	private GLabel goldLabel; 
	private int selectedIndex = 0;
	private int hoverIndex = -1;
	private GLabel selectedLabel = null; 
	private ArrayList<GLabel> itemLabels = new ArrayList<>();
	private ArrayList<GLabel> previewLines = new ArrayList<>();
	private GLabel bagLabel;
	private GLabel cursor;
	private GLabel equippedLabel; 
	private GLabel clerkMessage;

	
	public ShopPane(MainApplication mainScreen) {
		this.mainScreen = mainScreen;
	}
	
	@Override
	public void showContent() {
		drawShopLayout();
		
		inventory = new ShopInventory();
		playerInventory = CharacterSelectionPane.myInventory;
		addText();
		addBuyButton();
		addClerk();
		displayItems();
		displayGold();
		drawPlayerInfo();
		addReturnButton();
	}

	@Override
	public void hideContent() {
		for(GObject item : contents) {
			mainScreen.remove(item);
		}
		contents.clear();
	}
	
	//SHOP NAME
	private void addText() {
		GLabel title = new GLabel("SHOP", 100, 70);
		title.setColor(Color.BLUE);
		title.setFont("DialogInput-PLAIN-80");
		title.setLocation((mainScreen.getWidth() - title.getWidth()) / 2, 70);
		
		contents.add(title);
		mainScreen.add(title);
	}
	//Shop Layout
	private void drawShopLayout() {
		GRect bg = new GRect(WINDOW_WIDTH, WINDOW_HEIGHT);
		bg.setFilled(true);
		bg.setFillColor(new Color(40, 60, 90));
		bg.setColor(new Color(40, 60, 90));
		
		GRect topBar = new GRect(20,20,1326,50);
		topBar.setColor(new Color(200,200,200));
		
		GRect itemPanel = new GRect(20, 150, 850, 500);
		itemPanel.setColor(Color.WHITE);
		
		 GRect infoPanel = new GRect(900, 150, 446, 500);
		 infoPanel.setColor(Color.WHITE);
		 contents.add(bg);
		 contents.add(topBar);
		 contents.add(itemPanel);
		 contents.add(infoPanel);
		 
		 mainScreen.add(bg);
		 mainScreen.add(topBar);
		 mainScreen.add(itemPanel);
		 mainScreen.add(infoPanel);
	}
	
	//DISPLAYING ITEMS
	private void displayItems() {
	    int y= 190;
	    itemLabels.clear();

	    for (int i = 0; i < inventory.getItems().size(); i++) {

	        ShopItem item = inventory.getItems().get(i);

	        GLabel name = new GLabel(item.getDisplayName(), 40, y);
	        name.setFont("DialogInput-PLAIN-18");
	        name.setColor(Color.WHITE);

	        GLabel price = new GLabel(item.getPrice() + "g", 780, y);
	        price.setFont("DialogInput-PLAIN-18");
	        price.setColor(Color.WHITE);

	        int index = i;

	        name.addMouseListener(new MouseAdapter() {

	            @Override
	            public void mouseEntered(MouseEvent e) {
	                hoverIndex = index;
	                updateHoverVisual();
	            }

	            @Override
	            public void mouseExited(MouseEvent e) {
	                hoverIndex = -1;
	                updateHoverVisual();
	            }

	            @Override
	            public void mouseClicked(MouseEvent e) {
	            	   setSelected(index);
	            }
	        });

	        itemLabels.add(name);

	        contents.add(name);
	        contents.add(price);
	        mainScreen.add(name);
	        mainScreen.add(price);

	        y += 35;
	    }

	    updateSelectionVisual();
	    drawCursor();
	}
	
	//Mouse Cursor
	private void setSelected(int index) {

	    if (itemLabels.size() == 0) return;
	    if (index < 0 || index >= itemLabels.size()) return;

	    selectedIndex = index;

	    for (GLabel l : itemLabels) {
	        l.setColor(Color.WHITE);
	    }

	    selectedLabel = itemLabels.get(index);
	    selectedLabel.setColor(Color.YELLOW);

	    drawCursor();
	    updateItemPreview();
	}
	
	private void fixSelectedIndex() {
	    if (inventory.getItems().isEmpty()) {
	        selectedIndex = -1;
	    } else if (selectedIndex >= inventory.getItems().size()) {
	        selectedIndex = inventory.getItems().size() - 1;
	    }
	}
	
	//cursor for the shop layout
	private void drawCursor() {

	    if (cursor != null) {
	        mainScreen.remove(cursor);
	        contents.remove(cursor);
	    }

	    if (selectedIndex < 0 || selectedIndex >= itemLabels.size()) return;

	    GLabel selected = itemLabels.get(selectedIndex);

	    cursor = new GLabel("▶", selected.getX() - 20, selected.getY());
	    cursor.setColor(Color.YELLOW);
	    cursor.setFont("DialogInput-BOLD-18");

	    contents.add(cursor);
	    mainScreen.add(cursor);
	}
	
	//hover over item mouse effect
	private void updateHoverVisual() {

	    for (int i = 0; i < itemLabels.size(); i++) {

	        if (i == selectedIndex) continue;

	        itemLabels.get(i).setColor(Color.WHITE);
	    }

	    if (hoverIndex >= 0
	            && hoverIndex < itemLabels.size()
	            && hoverIndex != selectedIndex) {

	        itemLabels.get(hoverIndex).setColor(Color.LIGHT_GRAY);
	    }
	}
	
	//Key button options
	@Override
	public void keyPressed(java.awt.event.KeyEvent e) {

		 if (itemLabels.size() == 0) return;

		    if (e.getKeyCode() == java.awt.event.KeyEvent.VK_DOWN) {
		        setSelected((selectedIndex + 1) % itemLabels.size());
		    }

		    if (e.getKeyCode() == java.awt.event.KeyEvent.VK_UP) {
		        setSelected((selectedIndex - 1 + itemLabels.size()) % itemLabels.size());
		    }

		    if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
		        buyItem(selectedIndex);
		    }
	}
	
	//update the selection for locked choice
	private void updateSelectionVisual() {

	    for (int i = 0; i < itemLabels.size(); i++) {
	        itemLabels.get(i).setColor(Color.WHITE);
	    }

	    if (selectedIndex >= 0 && selectedIndex < itemLabels.size()) {
	        selectedLabel = itemLabels.get(selectedIndex);
	        selectedLabel.setColor(Color.YELLOW);
	    }
	}
	
	//Separates the long item names
	private String[] splitName(String name, int maxChars) {

	    if (name == null) return new String[]{""};

	    if (name.length() <= maxChars) {
	        return new String[]{name};
	    }
	    int breakIndex = name.lastIndexOf(" ", maxChars);

	    if (breakIndex == -1) {
	        breakIndex = maxChars;
	    }

	    String line1 = name.substring(0, breakIndex);
	    String line2 = name.substring(breakIndex + 1);

	    return new String[]{line1, line2};
	}
	
	//Updates the items and shows its description
	private void updateItemPreview() {
		if (inventory.getItems().isEmpty()) return;
	    if (selectedIndex < 0 || selectedIndex >= inventory.getItems().size()) return;

	    if (inventory.getItems().size() == 0) return;

	    ShopItem item = inventory.getItems().get(selectedIndex);

	    String text = "";

	    Object obj = item.getItem();

	    if (obj instanceof WeaponItem) {
	        WeaponItem w = (WeaponItem) obj;
	        text += " Type: " + w.getType()
	              + " | Affinity: " + w.getAffinity()
	              + " | Base DMG: " + w.getBaseDamage();
	    }

	    else if (obj instanceof ArmorItem) {
	        ArmorItem a = (ArmorItem) obj;
	        text += " Weight: " + a.getWeight()
	              + " | Affinity: " + a.getAffinity();
	    }

	    else if (obj instanceof ConsumableItem) {
	        ConsumableItem c = (ConsumableItem) obj;
	        text += " Effect: " + c.getType();
	    }
	    else if (obj instanceof Character) {
	        Character c = (Character) obj;
	        text +=" Profession: " + c.getProfession();
	    }

	    int startX = 920;
	    int startY = 330;
	    int lineHeight = 18;
	    
	    for (GLabel lbl : previewLines) {
	        mainScreen.remove(lbl);
	        contents.remove(lbl);
	    }
	    previewLines.clear();
	    
	    String name = item.getDisplayName();
	    String[] nameLines = splitName(name, 28);
	    int currentY = startY;

	    for (int i = 0; i < nameLines.length; i++) {

	        GLabel line = new GLabel(nameLines[i], startX, currentY);
	        line.setColor(Color.YELLOW);
	        line.setFont("DialogInput-BOLD-14");

	        previewLines.add(line);
	        contents.add(line);
	        mainScreen.add(line);
	        
	        currentY += lineHeight;
	    }
	    
	    String[] lines = text.split("\\|");

	    for (int i = 0; i < lines.length; i++) {
	        String lineText = lines[i].trim();

	        GLabel line = new GLabel(lineText, startX, currentY);
	        line.setColor(Color.YELLOW);
	        line.setFont("DialogInput-BOLD-14");

	        previewLines.add(line);
	        contents.add(line);
	        mainScreen.add(line);

	        currentY += lineHeight;
	     }
	}
	
	//Displays Player info
	private void drawPlayerInfo() {

	    int weaponCount = playerInventory.getExtraWeapons().size();
	    int armorCount = playerInventory.getExtraArmors().size();

	    int consumableCount = 0;
	    for (ConsumableItem c : playerInventory.getConsumables()) {
	        if (c != null) consumableCount++;
	    }

	    int totalItems = weaponCount + armorCount + consumableCount;

	    int partyCount = 0;
	    for (Character c : playerInventory.getPartyMembers()) {
	        if (c != null) {
	            partyCount++;
	        }
	    }

	    equippedLabel = new GLabel("Party: " + partyCount,920, 230);
	    bagLabel = new GLabel("Bag: " + totalItems, 920, 260);

	    equippedLabel.setFont("DialogInput-PLAIN-16");
	    bagLabel.setFont("DialogInput-PLAIN-16");

	    equippedLabel.setColor(Color.WHITE);
	    bagLabel.setColor(Color.WHITE);

	    contents.add(equippedLabel);
	    contents.add(bagLabel);

	    mainScreen.add(equippedLabel);
	    mainScreen.add(bagLabel);
	}
	
	//Updates player party size
	private void updatePlayerInfo() {

	    if (equippedLabel == null || bagLabel == null) return;

	    int weaponCount = playerInventory.getExtraWeapons().size();
	    int armorCount = playerInventory.getExtraArmors().size();

	    int consumableCount = 0;
	    for (ConsumableItem c : playerInventory.getConsumables()) {
	        if (c != null) consumableCount++;
	    }

	    int totalItems = weaponCount + armorCount + consumableCount;

	    int partyCount = 0;
	    for (Character c : playerInventory.getPartyMembers()) {
	        if (c != null) {
	            partyCount++;
	        }
	    }
	    equippedLabel.setLabel("Party: " + partyCount);
	    bagLabel.setLabel("Bag: " + totalItems);
	}

	//BUYING ITEMS SYSTEM
	private void buyItem(int index) {
	    if (inventory.getItems().isEmpty()) return;
	    if (index < 0 || index >= inventory.getItems().size()) return;

	    ShopItem shopItem = inventory.getItems().get(index);
	    int price = shopItem.getPrice();

	    if (playerInventory.getGold() < price) {
	    	setClerkMessage("YOU AINT GOT ENOUGH GOLD!");
	        return;
	    }

	    boolean success = shopItem.giveTo(playerInventory);

	    if (!success) {
	    	setClerkMessage("Could not complete purchase! Dija mess up somethin?");
	        return;
	    }

	    playerInventory.setGold(playerInventory.getGold() - price);
	    Object obj = shopItem.getItem();

	    if (obj instanceof WeaponItem) {
	        setClerkMessage("pack a punch that thing first!");
	    }
	    else if (obj instanceof ArmorItem) {
	        setClerkMessage("That piece shud serve ya well!");
	    }
	    else if (obj instanceof ConsumableItem) {
	        setClerkMessage("Good for a tough fight!");
	    }
	    else if (obj instanceof Character) {
	        setClerkMessage("Well you got sum one to join ya..huh");
	    }

	    System.out.println("Bought: " + shopItem.getDisplayName());

	    updateGoldDisplay();
	    updatePlayerInfo();
	    
	    fixSelectedIndex();
	    displayItems();
	}

	//Shows gold amount after buying
	private void updateGoldDisplay() {
	    if (goldLabel == null) return;

	    int currentGold = playerInventory.getGold();
	    goldLabel.setLabel("Gold: " + currentGold + "g");
	}
	
	//Display Total Amount of GOLD 
	private void displayGold() {
	    int currentGold = playerInventory.getGold();

	    goldLabel = new GLabel("Gold: " + currentGold, 920, 190);
	    goldLabel.setFont("DialogInput-BOLD-18");
	    goldLabel.setColor(Color.WHITE);

	    contents.add(goldLabel);
	    mainScreen.add(goldLabel);
	}
	
	//The old man clerk
	private void addClerk() {
	    clerkMessage = new GLabel("What would you like to buy?", 200, 120);
	    clerkMessage.setFont("DialogInput-BOLD-18");
	    clerkMessage.setColor(Color.WHITE);
	    

	    contents.add( clerkMessage);
	    mainScreen.add(clerkMessage);
	}
	//Clerk Message
	private void setClerkMessage(String msg) {
	    if (clerkMessage != null) {
	        mainScreen.remove(clerkMessage);
	        contents.remove(clerkMessage);
	    }

	    clerkMessage = new GLabel(msg, 200, 120);
	    clerkMessage.setFont("DialogInput-BOLD-18");
	    clerkMessage.setColor(Color.WHITE);

	    contents.add(clerkMessage);
	    mainScreen.add(clerkMessage);
	}
	
	//buy button
	private void addBuyButton() {
	    GLabel buyBtn = new GLabel("BUY", 920, 300);
	    buyBtn.setFont("DialogInput-BOLD-20");
	    buyBtn.setColor(Color.GREEN);

	    buyBtn.addMouseListener(new MouseAdapter() {
	        public void mouseClicked(MouseEvent e) {
	            if (selectedIndex != -1) {
	                buyItem(selectedIndex);
	            }
	        }
	    });

	    contents.add(buyBtn);
	    mainScreen.add(buyBtn);
	}

	//return button
	private void addReturnButton() {
	    GLabel returnBtn = new GLabel("Leave Shop", 980, 300); 
	    returnBtn.setFont("DialogInput-BOLD-20");
	    returnBtn.setColor(Color.RED);

	    returnBtn.addMouseListener(new MouseAdapter() {
	        public void mouseClicked(MouseEvent e) {
	            mainScreen.switchToMapPane();
	        }
	    });

	    contents.add(returnBtn);
	    mainScreen.add(returnBtn);
	    MapPane.currPosition.cleared();
	}
}
//work in progress