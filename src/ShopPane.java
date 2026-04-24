import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;
import java.util.ArrayList;

//The ShopPane
public class ShopPane extends GraphicsPane {
	
	private ShopInventory inventory;
	private PlayerInventory playerInventory;
	private GLabel goldLabel; 
	private int selectedIndex = 0;
	private int hoverIndex = -1; 
	private GImage mercenaryImage;
	private GImage clerkImage;
	private ArrayList<GLabel> itemLabels = new ArrayList<>();
	private ArrayList<GLabel> priceLabels = new ArrayList<>();
	private ArrayList<GLabel> previewLines = new ArrayList<>();
	private GLabel bagLabel;
	private GLabel cursor;
	private GLabel equippedLabel; 
	private GLabel clerkMessage;
	private String clerkState = "idle";
	private GLabel buyBtn;
	private GLabel sellBtn;
	private GRect confirmBox;
	private GLabel confirmText;
	private GLabel yesBtn;
	private GLabel noBtn;
	private int pendingIndex;
	private boolean pendingBuy;
	private boolean isConfirmOpen = false;

	private enum ShopMode {
		BUY,
		SELL
	}

	private ShopMode currentMode = ShopMode.BUY;
	private ArrayList<Object> itemRefs = new ArrayList<>();
	
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
		addSellButton();
		addClerk();
		
		switchMode(ShopMode.BUY);

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
		 GImage bg = new GImage("spr_ShopBackground.jpeg"); 
		    bg.setLocation(0, 0);

		    bg.setSize(MainApplication.WINDOW_WIDTH, MainApplication.WINDOW_HEIGHT);
		
		GRect topBar = new GRect(600,20,185,60);
		topBar.setColor(new Color(200,200,200));
		topBar.setFillColor(Color.BLACK);
		topBar.setFilled(true);
		
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

		for (GLabel lbl : itemLabels) {
			mainScreen.remove(lbl);
			contents.remove(lbl);
		}
		itemLabels.clear();

		for (GLabel lbl : priceLabels) {
			mainScreen.remove(lbl);
			contents.remove(lbl);
		}
		priceLabels.clear();

		itemRefs.clear(); 

		int y = 190;

		for (int i = 0; i < inventory.getItems().size(); i++) {

			ShopItem item = inventory.getItems().get(i);

			itemRefs.add(item.getItem()); // ✅ FIX

			GLabel name = new GLabel(item.getDisplayName(), 40, y);
			name.setFont("DialogInput-BOLD-18");
			name.setColor(Color.YELLOW);

			GLabel price = new GLabel(item.getPrice() + "g", 780, y);
			price.setFont("DialogInput-BOLD-18");
			price.setColor(Color.YELLOW);

			itemLabels.add(name);
			priceLabels.add(price);

			final int index = i;

			name.addMouseListener(new MouseAdapter() {
			    public void mouseClicked(MouseEvent e) {
			        setSelected(index);

			        if (!isConfirmOpen) {
			            confirmTransaction(index, true);
			        }
			    }
			});

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
	    
	    updateSelectionVisual();
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

	    if (selectedIndex < 0 || itemLabels.isEmpty() || selectedIndex >= itemLabels.size()) return;

	    GLabel selected = itemLabels.get(selectedIndex);

	    cursor = new GLabel("▶", selected.getX() - 20, selected.getY());
	    cursor.setColor(Color.WHITE);
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
		if (isConfirmOpen) return;
		 if (itemLabels.size() == 0) return;

		    if (e.getKeyCode() == java.awt.event.KeyEvent.VK_DOWN) {
		        setSelected((selectedIndex + 1) % itemLabels.size());
		    }

		    if (e.getKeyCode() == java.awt.event.KeyEvent.VK_UP) {
		        setSelected((selectedIndex - 1 + itemLabels.size()) % itemLabels.size());
		    }

		    if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {

		        if (!isConfirmOpen) {
		            if (currentMode == ShopMode.SELL) {
		                confirmTransaction(selectedIndex, false);
		            } else {
		                confirmTransaction(selectedIndex, true);
		            }
		        }
		    }
	}
	
	//update the selection for locked choice
	private void updateSelectionVisual() {

	    for (int i = 0; i < itemLabels.size(); i++) {
	        GLabel label = itemLabels.get(i);

	        if (i == selectedIndex) {
	            label.setFont("DialogInput-BOLD-18");
	            label.setColor(Color.WHITE); 
	        } else {
	            label.setFont("DialogInput-BOLD-18");
	            label.setColor(Color.YELLOW); 
	        }
	    }
	}
	
	@SuppressWarnings("unused")
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
		if (mercenaryImage != null) {
		    mainScreen.remove(mercenaryImage);
		    contents.remove(mercenaryImage);
		    mercenaryImage = null;
		}

	    if (currentMode == ShopMode.SELL) {
	        if (itemRefs.isEmpty()) return;
	        if (selectedIndex < 0 || selectedIndex >= itemRefs.size()) return;
	    } else {
	        if (inventory.getItems().isEmpty()) return;
	        if (selectedIndex < 0 || selectedIndex >= inventory.getItems().size()) return;
	    }

	    Object obj;

	    if (currentMode == ShopMode.SELL) {
	        obj = itemRefs.get(selectedIndex);
	    } else {
	        obj = inventory.getItems().get(selectedIndex).getItem();
	    }

	    String text = "";

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

	        text += " Profession: " + c.getProfession()
	              + " | HP: " + c.getHp() + "/" + c.getHpMax()
	              + " | MP: " + c.getMana() + "/" + c.getManaMax()
	              + " | Weapon: " + c.getWeapon()
	              + " | Armor: " + c.getArmor();
	    }

	    int startX = 920;
	    int startY = 330;
	    int lineHeight = 18;

	    for (GLabel lbl : previewLines) {
	        mainScreen.remove(lbl);
	        contents.remove(lbl);
	    }
	    previewLines.clear();

	    String[] lines = text.split("\\|");
	    int currentY = startY;

	    for (String lineText : lines) {
	        GLabel line = new GLabel(lineText.trim(), startX, currentY);
	        line.setColor(Color.YELLOW);
	        line.setFont("DialogInput-BOLD-14");

	        previewLines.add(line);
	        contents.add(line);
	        mainScreen.add(line);

	        currentY += lineHeight;
	    }

	    if (obj instanceof Character) {
	        Character c = (Character) obj;

	        String imgPath = getMercenaryImagePath(c.getProfession());
	        mercenaryImage = new GImage(imgPath);

	        mercenaryImage.setLocation(startX, currentY + 10);
	        mercenaryImage.setSize(150, 150);

	        contents.add(mercenaryImage);
	        mainScreen.add(mercenaryImage);
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
	    bagLabel = new GLabel("Bag: " + totalItems + " (Consumables: " + consumableCount + "/3)", 920, 260);

	    equippedLabel.setFont("DialogInput-BOLD-16");
	    bagLabel.setFont("DialogInput-BOLD-16");

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
	    bagLabel.setLabel("Bag: " + totalItems + " (Consumables: " + consumableCount + "/3)");
	}
	
	//checks to see if the Consumables is full
	private boolean isConsumablesFull() {
	    int count = 0;
	    for (ConsumableItem c : playerInventory.getConsumables()) {
	        if (c != null) count++;
	    }
	    return count >= 3;
	}

	//BUYING ITEMS SYSTEM
	private void buyItem(int index) {
	    if (inventory.getItems().isEmpty()) return;
	    if (index < 0 || index >= inventory.getItems().size()) return;

	    ShopItem shopItem = inventory.getItems().get(index);
	    int price = shopItem.getPrice();

	    if (playerInventory.getGold() < price) {
	        setClerkMessage("YOU AINT GOT ENOUGH GOLD!", "mad");
	        return;
	    }

	    Object obj = shopItem.getItem();

	    if (obj instanceof Character) {
	        if (isPartyFull()) {
	            setClerkMessage("Your party is full!", "mad");
	            return;
	        }
	    }

	
	    if (obj instanceof ConsumableItem) {
	        if (isConsumablesFull()) {
	            setClerkMessage("Yer consumable bag is full! (Max 3 consumables)","annoyed");
	            return;
	        }
	    }

	    boolean success = shopItem.giveTo(playerInventory);

	    if (!success) {
	        setClerkMessage("Could not complete purchase! Dija mess up somethin?","annoyed");
	        return;
	    }

	    playerInventory.setGold(playerInventory.getGold() - price);
	    updateGoldDisplay();

	  
	    if (obj instanceof WeaponItem) {
	        setClerkMessage("Pack a punch that thing first!","mad");
	    }
	    else if (obj instanceof ArmorItem) {
	        setClerkMessage("That piece shud serve ya well!", "happy");
	    }
	    else if (obj instanceof ConsumableItem) {
	        setClerkMessage("Good for a tough fight!","happy");
	    }
	    else if (obj instanceof Character) {

	        setClerkMessage("Well you got sum one to join ya..huh","surprised");

	        inventory.getItems().set(index, generateMercenaryItem());

	        displayItems();
	        selectedIndex = index;
	        updateItemPreview();
	        drawCursor();
	        updatePlayerInfo();

	        return;
	    }

	    System.out.println("Bought: " + shopItem.getDisplayName());

	    updatePlayerInfo();
	    fixSelectedIndex();
	    displayItems();
	}

	//Mercenary Generate for shop
	private ShopItem generateMercenaryItem() {

	    String[] professions = {
	        "knight","samurai","thief","viking",
	        "cleric","sorcerer","paladin","ranger","marksman"
	    };

	    ArrayList<String> existingProfessions = new ArrayList<>();

	    for (ShopItem item : inventory.getItems()) {
	        if (item.getItem() instanceof Character) {
	            Character c = (Character) item.getItem();
	            existingProfessions.add(c.getProfession());
	        }
	    }

	    String newProfession;
	    int attempts = 0;

	    do {
	        newProfession = Chance.choose(professions);
	        attempts++;
	        
	        if (attempts > 20) break;

	    } while (existingProfessions.contains(newProfession));

	    return new ShopItem(
	        new Character(newProfession, true),
	        Chance.range(200, 400)
	    );
	}
	
	//Checks to see if party is full
	private boolean isPartyFull() {
	    Character[] party = playerInventory.getPartyMembers();

	    for (Character c : party) {
	        if (c == null) {
	            return false;
	        }
	    }
	    return true;
	}
	
	//image of mercenary
	private String getMercenaryImagePath(String profession) {
	    switch (profession.toLowerCase()) {
	        case "knight": return "spr_knight.png";
	        case "samurai": return "spr_samurai.png";
	        case "thief": return "spr_thief.png"; 
	        case "viking": return "spr_viking.png";
	        case "cleric": return "spr_cleric.png";
	        case "sorcerer": return "spr_sorcerer.png";
	        case "paladin": return "spr_paladin.png";
	        case "ranger": return "spr_RangerUpdated.png";
	        case "marksman": return "spr_marksman.png";
	        default: return "spr_Knight.png";
	    }
	}

	//Shows gold amount after buying
	private void updateGoldDisplay() {
	    if (goldLabel == null) return;

	    goldLabel.setLabel("Gold: " + playerInventory.getGold() + "g");
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
	    clerkMessage = new GLabel("What would you like to buy?(Press Enter Key to Purchase)", 200, 120);
	    clerkMessage.setFont("DialogInput-BOLD-18");
	    clerkMessage.setColor(Color.WHITE);
	    
	    clerkImage = new GImage("spr_ClerkIdle.gif"); 
	    clerkImage.setLocation(50, 40); 
	    clerkImage.setSize(120, 120);

	    contents.add(clerkMessage);
	    contents.add(clerkImage);

	    mainScreen.add(clerkMessage);
	    mainScreen.add(clerkImage);
	}
	//Clerk Message
	private void setClerkMessage(String msg, String state) {
	    clerkState = state;

	    if (clerkMessage != null) {
	        mainScreen.remove(clerkMessage);
	        contents.remove(clerkMessage);
	    }

	    clerkMessage = new GLabel(msg, 200, 120);
	    clerkMessage.setFont("DialogInput-BOLD-18");
	    clerkMessage.setColor(Color.WHITE);

	    contents.add(clerkMessage);
	    mainScreen.add(clerkMessage);

	    updateClerkImage();
	}
	//Clerk Expressions
	private void updateClerkImage() {

	    if (clerkImage != null) {
	        mainScreen.remove(clerkImage);
	        contents.remove(clerkImage);
	    }

	    String imgPath = "spr_ClerkIdle.gif";

	    switch (clerkState) {
	        case "mad": 
	            imgPath = "spr_ClerkMad.gif";
	            break;
	        case "annoyed":
	            imgPath = "spr_ClerkAnnoy.gif";
	            break;
	        case "happy":
	            imgPath = "spr_ClerkHappy.gif";
	            break;
	        case "surprised":
	            imgPath = "spr_ClerkSurprise.gif";
	            break;
	    }

	    clerkImage = new GImage(imgPath);
	    clerkImage.setLocation(50, 40);
	    clerkImage.setSize(120, 120);

	    contents.add(clerkImage);
	    mainScreen.add(clerkImage);
	}
	
	//sell displays player Items:
	private void displayPlayerItems() {

	    for (GLabel lbl : itemLabels) {
	        mainScreen.remove(lbl);
	        contents.remove(lbl);
	    }
	    itemLabels.clear();

	    for (GLabel lbl : priceLabels) {
	        mainScreen.remove(lbl);
	        contents.remove(lbl);
	    }
	    priceLabels.clear();

	    itemRefs.clear();

	    int y = 190;

	    // Weapons
	    for (WeaponItem w : playerInventory.getExtraWeapons()) {

	    	GLabel name = new GLabel(w.toString(), 40, y);
	    	name.setFont("DialogInput-BOLD-18");
	    	name.setColor(Color.YELLOW);

	    	GLabel price = new GLabel(getSellPrice(w) + "g", 780, y);
	    	price.setFont("DialogInput-BOLD-18");
	    	price.setColor(Color.YELLOW);

	        int index = itemRefs.size();
	        itemRefs.add(w);

	        addSellListeners(name, index);

	        itemLabels.add(name);
	        priceLabels.add(price);

	        contents.add(name);
	        contents.add(price);
	        mainScreen.add(name);
	        mainScreen.add(price);

	        y += 35;
	    }

	    // Armors
	    for (ArmorItem a : playerInventory.getExtraArmors()) {

	    	GLabel name = new GLabel(a.toString(), 40, y);
	    	name.setFont("DialogInput-BOLD-18");
	    	name.setColor(Color.YELLOW);

	    	GLabel price = new GLabel(getSellPrice(a) + "g", 780, y);
	    	price.setFont("DialogInput-BOLD-18");
	    	price.setColor(Color.YELLOW);

	        int index = itemRefs.size();
	        itemRefs.add(a);

	        addSellListeners(name, index);

	        itemLabels.add(name);
	        priceLabels.add(price);

	        contents.add(name);
	        contents.add(price);
	        mainScreen.add(name);
	        mainScreen.add(price);

	        y += 35;
	    }

	    // Mercenaries
	    for (Character c : playerInventory.getPartyMembers()) {
	        if (c == null) continue;

	        GLabel name = new GLabel("Mercenary: " + c.getProfession(), 40, y);
	        name.setFont("DialogInput-BOLD-18");
	        name.setColor(Color.YELLOW);

	        GLabel price = new GLabel(getSellPrice(c) + "g", 780, y);
	        price.setFont("DialogInput-BOLD-18");
	        price.setColor(Color.YELLOW);

	        int index = itemRefs.size();
	        itemRefs.add(c);

	        addSellListeners(name, index);

	        itemLabels.add(name);
	        priceLabels.add(price);

	        contents.add(name);
	        contents.add(price);
	        mainScreen.add(name);
	        mainScreen.add(price);

	        y += 35;
	    }

	    if (itemRefs.isEmpty()) {
	        selectedIndex = -1;
	    } else if (selectedIndex >= itemRefs.size()) {
	        selectedIndex = itemRefs.size() - 1;
	    }

	    updateSelectionVisual();
	    drawCursor();
	    updateItemPreview();
	}
	
	//handles clicking sell item:
	private void addSellListeners(GLabel label, int index) {

	    label.addMouseListener(new MouseAdapter() {

	    	@Override
	    	public void mouseClicked(MouseEvent e) {
	    	    setSelected(index);

	    	    if (!isConfirmOpen) {
	    	        confirmTransaction(index, false);
	    	    }
	    	}

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
	    });
	}
	
	//sell method:
	private void sellItem(int index) {

	    if (itemRefs.isEmpty()) return;
	    if (index < 0 || index >= itemRefs.size()) return;

	    Object obj = itemRefs.get(index);

	    if (obj instanceof Character) {
	        if (!canSellCharacter()) {
	            setClerkMessage("You must keep at least one mercenary!", "mad");
	            return;
	        }
	    }

	    int price = getSellPrice(obj);

	    playerInventory.setGold(playerInventory.getGold() + price);
	    updateGoldDisplay();


	    if (obj instanceof WeaponItem) {
	        removeWeapon((WeaponItem) obj);
	    }
	    else if (obj instanceof ArmorItem) {
	        removeArmor((ArmorItem) obj);
	    }
	    else if (obj instanceof Character) {
	        removeMercenary((Character) obj);
	    }

	    applySellBoost(obj);

	    displayPlayerItems();
	    updatePlayerInfo();

	    hoverIndex = -1;
	    selectedIndex = 0;

	    updateItemPreview();
	    updateSelectionVisual();
	    drawCursor();
	}
	
	//method to check if there is only one character in party
	private boolean canSellCharacter() {
	    Character[] party = playerInventory.getPartyMembers();

	    int count = 0;
	    for (Character c : party) {
	        if (c != null) count++;
	    }


	    return count > 1;
	}
	
	//sell price:
	private int getSellPrice(Object obj) {
	    if (obj instanceof WeaponItem) {
	        return ((WeaponItem)obj).getPurchaseCost() / 2;
	    }
	    if (obj instanceof ArmorItem) {
	        return ((ArmorItem)obj).getPurchaseCost() / 2;
	    }
	    if (obj instanceof Character) {
	        return 150;
	    }
	    return 10;
	    
	}
	
	//boost stats after selling
	private void applySellBoost(Object soldItem) {

	    Character[] party = playerInventory.getPartyMembers();
	    Random rand = new Random();

	    if (soldItem instanceof WeaponItem || soldItem instanceof ArmorItem) {

	        String[] statNames = {"STR","DEX","PRC","IST","CON","WIL","FTH","ARC"};

	        StringBuilder message = new StringBuilder("▲ STATS BOOSTS +2 APPLIED: ");

	        for (Character member : party) {
	            if (member == null) continue;

	            int randomStat = rand.nextInt(statNames.length);
	            member.increaseStat(randomStat, 2);

	            message.append(member.getProfession())
	                   .append(" +")
	                   .append(statNames[randomStat])
	                   .append(", ");
	        }     
 
	        if (message.length() > 2) {
	            message.setLength(message.length() - 2);
	        }

	        setClerkMessage(message.toString(), "happy");
	    }

	    else if (soldItem instanceof Character) {

	        for (Character member : party) {
	            if (member == null) continue;

	            member.setHpMax(member.getHpMax() + 10);
	            member.setHp(member.getHpMax());

	            member.setManaMax(member.getManaMax() + 10);
	            member.setMana(member.getManaMax());
	        }

	        setClerkMessage(
	            "▲ MERCENARY RETIRED! +10 HP / +10 MP FOR ALL! ▲",
	            "surprised"
	        );
	    }
	}

	//sells mercenary:
	private void removeMercenary(Character c) {
	    Character[] party = playerInventory.getPartyMembers();

	    for (int i = 0; i < party.length; i++) {
	        if (party[i] == c) {
	            party[i] = null;
	            return;
	        }
	    }
	}
	
	//remove weapon
	private void removeWeapon(WeaponItem w) {
	    playerInventory.getExtraWeapons().remove(w);
	}
	
	//remove armor
	private void removeArmor(ArmorItem a) {
	    playerInventory.getExtraArmors().remove(a);
	}

	
	//buy button
	private void addBuyButton() {
		buyBtn = new GLabel("BUY", 920, 300);
		buyBtn.setFont("DialogInput-BOLD-20");
		buyBtn.setColor(Color.GREEN);

		buyBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				switchMode(ShopMode.BUY);
			}
		});

		contents.add(buyBtn);
		mainScreen.add(buyBtn);
	}
	
	//sell button
	private void addSellButton() {
		sellBtn = new GLabel("SELL", 980, 300);
		sellBtn.setFont("DialogInput-BOLD-20");
		sellBtn.setColor(Color.ORANGE);

		sellBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				switchMode(ShopMode.SELL);
			}
		});

		contents.add(sellBtn);
		mainScreen.add(sellBtn);
	}
	
	//sell and buy mode
	private void switchMode(ShopMode mode) {

	    currentMode = mode;
	    selectedIndex = 0;
	    hoverIndex = -1;

	    if (currentMode == ShopMode.BUY) {
	        displayItems();
	    } else {
	        displayPlayerItems();
	    }

	    updateItemPreview();

	    if (mode == ShopMode.BUY) {
	        setClerkMessage("What would you like to buy?(Press Enter key to Purchase)", "idle");
	    } else {
	        setClerkMessage("Whatcha sellin'? I'll make it worth yer while...(Press Enter key to SELL)", "idle");
	    }
	}
	
	//pop up confirm
	private void confirmTransaction(int index, boolean isBuy) {

	    if (isConfirmOpen) return; // prevents stacking popups
	    isConfirmOpen = true;

	    pendingIndex = index;
	    pendingBuy = isBuy;

	    confirmBox = new GRect(500, 250, 300, 120);
	    confirmBox.setFilled(true);
	    confirmBox.setFillColor(Color.BLACK);
	    confirmBox.setColor(Color.WHITE);

	    confirmText = new GLabel(
	    	    isBuy ? "Confirm Purchase?" : "Confirm Sale?",
	    	    550, 285
	    	);
	    confirmText.setColor(Color.WHITE);
	    confirmText.setFont("DialogInput-BOLD-20");

	    yesBtn = new GLabel("YES", 540, 320);
	    yesBtn.setColor(Color.GREEN);
	    yesBtn.setFont("DialogInput-BOLD-18");

	    noBtn = new GLabel("NO", 700, 320);
	    noBtn.setColor(Color.RED);
	    noBtn.setFont("DialogInput-BOLD-18");

	    yesBtn.addMouseListener(new MouseAdapter() {
	        public void mouseClicked(MouseEvent e) {
	            executeTransaction();
	            removeConfirmBox();
	        }
	    });

	    noBtn.addMouseListener(new MouseAdapter() {
	        public void mouseClicked(MouseEvent e) {
	            removeConfirmBox();
	        }
	    });

	    contents.add(confirmBox);
	    contents.add(confirmText);
	    contents.add(yesBtn);
	    contents.add(noBtn);

	    mainScreen.add(confirmBox);
	    mainScreen.add(confirmText);
	    mainScreen.add(yesBtn);
	    mainScreen.add(noBtn);
	}
	
	//pop up
	private void executeTransaction() {
	    if (pendingBuy) {
	        buyItem(pendingIndex);
	    } else {
	        sellItem(pendingIndex);
	    }
	}
	
	//clear pop up
	private void removeConfirmBox() {

	    isConfirmOpen = false;

	    mainScreen.remove(confirmBox);
	    mainScreen.remove(confirmText);
	    mainScreen.remove(yesBtn);
	    mainScreen.remove(noBtn);

	    contents.remove(confirmBox);
	    contents.remove(confirmText);
	    contents.remove(yesBtn);
	    contents.remove(noBtn);
	}

	//return button
	private void addReturnButton() {
	    GLabel returnBtn = new GLabel("Leave Shop", 1100, 300); 
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