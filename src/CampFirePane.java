import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList; 
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import acm.graphics.*;

public class CampFirePane extends GraphicsPane{
	 private int actionPoints;
	 private GLabel apLabel;
	 private GLabel actionMessage;
	 private HashMap<String, GRect> actionButtons;
	 private HashSet<String> usedActions;
	 private Character previewMercenary;
	 private List<GObject> mercPreviewObjects;

	 
	public CampFirePane(MainApplication mainScreen) {
		this.mainScreen = mainScreen;
		 actionButtons = new HashMap<>();
		 usedActions = new HashSet<>();
		 mercPreviewObjects = new ArrayList<>();
	}
	
	public void startCampSession() {
		actionPoints = 2;
		usedActions.clear();
	}
	
	@Override
	public void showContent() {
        addBackground();
        previewMercenary = new Character(Chance.choose(new String[] {
            "knight","samurai","thief","viking","cleric",
            "sorcerer","paladin","ranger","marksman"
        }),true);
        addText();
        addButtons();
        displayActionPoints();
        displayPartyStats();
        displayMercenaryPreview();
    }

	@Override
	public void hideContent() {
		for(GObject item : contents) {
			mainScreen.remove(item);
		}
		contents.clear();
		actionButtons.clear();
	}
	
	private void addText() {
		GLabel title = new GLabel("CAMP", 100, 70);
		title.setColor(Color.ORANGE);
		title.setFont("DialogInput-PLAIN-80");
		title.setLocation((mainScreen.getWidth() - title.getWidth()) / 2, 70);
		
		contents.add(title);
		mainScreen.add(title);
		}
	
	
	//Background for Camp fire Pane 
	private void addBackground() {
	    GRect bg = new GRect(800, 600);
	    bg.setLocation(0, 0);
	    bg.setFilled(true);
	    bg.setFillColor(new Color(30, 30, 40));
	    bg.setColor(new Color(30, 30, 40));

	    contents.add(bg);
	    mainScreen.add(bg);
	}
	
	// HEAL, REVIVE, TRAIN, MERCENARY, EXIT buttons
	 private void addButtons() {
	        int x = 40;
	        int yStart = 150;  
	        int yStep = 50;

	        createButton("Heal Party (50%)", x, yStart, "heal");
	        createButton("Revive Allies (30%)", x, yStart + yStep, "revive");
	        createButton("Raise Main Stat (+10)", x, yStart + yStep * 2, "train");
	        createButton("Recruit Mercenary", x, yStart + yStep * 3, "mercenary");
	        createButton("Leave Camp", x, yStart + yStep * 4, "leave");
	    }

	    // Helper to create a button
	    private void createButton(String text, int x, int y, String actionType) {
	        int width = 300;
	        int height = 40;
	        
	        Color baseColor = Color.WHITE;
	        Color usedColor = Color.LIGHT_GRAY;

	        GRect box = new GRect(x, y - height + 10, width, height);
	        box.setFilled(true);
	        box.setFillColor(Color.WHITE);

	        GLabel label = new GLabel(text);
	        label.setFont("DialogInput-BOLD-18");
	        label.setLocation(x + (width - label.getWidth()) / 2, y);

	        box.addMouseListener(new java.awt.event.MouseAdapter() {
	            public void mouseEntered(MouseEvent e) {
	                if (actionButtons.containsKey(actionType)) {
	                    box.setFillColor(baseColor.brighter());
	                }
	            }
	            public void mouseExited(MouseEvent e) {
	            	if (actionType.equals("leave") || actionButtons.containsKey(actionType)) {
	                    box.setFillColor(baseColor);
	                } else {
	                    box.setFillColor(usedColor);
	                }
	            }
	            public void mouseClicked(MouseEvent e) { handleAction(actionType); }
	        });
	        
	        label.addMouseListener(new java.awt.event.MouseAdapter() {
	            public void mouseClicked(java.awt.event.MouseEvent e) { handleAction(actionType); }
	        });

	        contents.add(box);
	        contents.add(label);
	        mainScreen.add(box);
	        mainScreen.add(label);
	        
	        if (!actionType.equals("leave")) {
	            actionButtons.put(actionType, box);
	         
	        }
	    }

	    
	    //action points displayed
	    private void displayActionPoints() {
	        if (apLabel != null) {
	            mainScreen.remove(apLabel);
	            contents.remove(apLabel);
	        }
	        apLabel = new GLabel("Camp Actions Left: " + actionPoints, 50, 100);
	        apLabel.setFont("DialogInput-BOLD-16");
	        apLabel.setColor(Color.WHITE);
	        
	        contents.add(apLabel);
	        mainScreen.add(apLabel);
	    }
	    //action points used
	    private boolean useActionPoint(String actionType) {
	        if (actionPoints <= 0) {
	            showMessage("No actions remaining.");
	            return false;
	        }

	        if (!actionType.equals("train") && !actionType.equals("heal")
	                && usedActions.contains(actionType)) {
	            showMessage("Action already used.");
	            return false;
	        }

	        actionPoints--;

	        if (!actionType.equals("train") && !actionType.equals("heal")) {
	            usedActions.add(actionType);
	        }

	        displayActionPoints();
	        return true;
	    }
	    
	 // Handle button clicks
	    private void handleAction(String actionType) {
	    	if (actionType.equals("leave")) {
	            mainScreen.switchToMapPane();
	            MapPane.currPosition.isCleared(); 
	            return;
	        }
	    	
	    	if (actionPoints <= 0) {
	    		showMessage("No actions remaining! Leave the Camp");
	            return;
	          }
	    	
	    	
	        switch (actionType) {
	        case "heal":
                if (healParty()) {
                	useActionPoint("heal");
                }
                break;
            case "revive":
                if (reviveAllies()) {
                	useActionPoint("revive");
                	
                }
                break;
            case "train":
                if (useActionPoint("train")) { 
                	trainParty();
                	
                }
                break;
            case "mercenary":
            	if (isPartyFull()) {
                    showMessage("Party full!");
                    return;
                }
                if (useActionPoint("mercenary")) {
                	recruitMercenary();
                	displayMercenaryPreview();
                }
                break;
	        }
	        
	        displayPartyStats();
	    }
	    
	 
	    //checks to see if party is full
	    private boolean isPartyFull() {
	        Character[] party = CharacterSelectionPane.myInventory.getPartyMembers();
	        for (Character c : party) {
	            if (c == null) return false;
	        }
	        return true;
	    }
	    
	    // Heal party to 50%
	    private boolean healParty() {
	        Character[] party = CharacterSelectionPane.myInventory.getPartyMembers();
	        boolean anyHealedHalf = false;
	        boolean healedToFull = false; 
	        boolean healthChange = false;
	        for (Character c : party) {
	            if (c != null && c.getHp() > 0) {

	                int halfHp = (int)(c.getHpMax() * 0.50);
	                

	                if (c.getHp() < halfHp) {
	                    c.setHp(halfHp);
	                    anyHealedHalf = true;
	                    healthChange = true;
	                } else if (c.getHp() < c.getHpMax()) {
	                    c.setHp(c.getHpMax());
	                    healedToFull = true;
	                    healthChange = true;
	                }
	                c.gainMana(c.getManaMax() - c.getMana());
	            }
	        }

	        if (anyHealedHalf) {
	        	 showMessage("Party recovered 50% hp and Full MP");
	            return true;
	        } else if (healedToFull) {
	        	 showMessage("Party fully recovered HP and MP");
	            return true;
	        } else {
	            showMessage("Party already has full HP and MP!");
	            
	        }
	        return healthChange; 
	    }

	    // Revive allies to 30%
	    private boolean reviveAllies() {
	        Character[] party = CharacterSelectionPane.myInventory.getPartyMembers();
	        boolean anyRevived = false; 
	        for (Character c : party) {
	        	if (c != null && c.getHp() <= 0) {
	                c.setHp(c.getHpMax() * 0.30);
	                anyRevived = true;
	            }
	        }

	        if (anyRevived) {
	        	 showMessage("Defeated allies revived to 30% hp");
	            return true;
	        } else {
	        	 showMessage("All party members are still alive!");
	            return false; 
	        }
	    }

	    // Train party +10 primary stat
	    private void trainParty() {
	        Character[] party = CharacterSelectionPane.myInventory.getPartyMembers();
	        for (Character c : party) {
	            if (c != null) {
	                int primaryStatIndex = c.getPrimaryStatIndex(); 
	                c.increaseStat(primaryStatIndex, 10); 
	            }
	        }
	        showMessage("Party primary stats increase +10");
	    }

	    // Recruit a free random mercenary
	    private void recruitMercenary() {
	        PlayerInventory inv = CharacterSelectionPane.myInventory;

	        for (int i = 0; i < inv.getPartyMembers().length; i++) {
	            if (inv.getPartyMembers()[i] == null) {
	                inv.getPartyMembers()[i] = previewMercenary;
	                showMessage("Recruited a new mercenary: " + previewMercenary.getProfession());
	                previewMercenary = new Character(Chance.choose(new String[]{
	                        "knight","samurai","thief","viking","cleric",
	                        "sorcerer","paladin","ranger","marksman"
	                    }));
	                displayMercenaryPreview();
	                return;
	            }
	        }

	        showMessage("Party full! Mercenary cannot join");
	    }
	    
	    //Displays the messages on the pane
	   
	    private void showMessage(String message) {
	        if (actionMessage != null) {
	            mainScreen.remove(actionMessage);
	            contents.remove(actionMessage);
	        }

	        actionMessage = new GLabel(message, 400,100);
	        actionMessage.setFont("DialogInput-BOLD-16");
	        actionMessage.setColor(Color.WHITE);

	        contents.add(actionMessage);
	        mainScreen.add(actionMessage);

	        new Thread(() -> {
	            try {
	                Thread.sleep(2000);
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	            if (actionMessage != null) {
	                mainScreen.remove(actionMessage);
	                contents.remove(actionMessage);
	                actionMessage = null;
	            }
	        }).start();
	    }
	    
	    //Preview screen for Recruit Mercenary
	    private void displayMercenaryPreview() {
	        for (GObject obj : mercPreviewObjects) {
	            mainScreen.remove(obj);
	            contents.remove(obj);
	        }
	        mercPreviewObjects.clear();

	        if (previewMercenary == null) return;

	        int x = 599, y = 375;
	        GRect box = new GRect(199, 200);
	        box.setLocation(x, y);
	        box.setColor(Color.BLACK);
	        box.setFillColor(Color.BLACK);
	        box.setFilled(true);
	        mercPreviewObjects.add(box);
	        contents.add(box);
	        mainScreen.add(box);
	        
	        String prof = previewMercenary.getProfession();
	        String imagePath;
	        
	        switch(prof) {
	        case "knight": imagePath = "spr_Knight.png"; break;
	        case "samurai": imagePath = "spr_Samurai.png"; break;
	        case "thief": imagePath = "spr_Thief.png"; break;
	        case "viking": imagePath = "spr_Viking.png"; break;
	        case "sorcerer": imagePath = "spr_Sorcerer.png"; break;
	        case "paladin": imagePath = "spr_Paladin.png"; break;
	        case "cleric": imagePath = "spr_Cleric.png"; break;
	        case "ranger": imagePath = "spr_RangerUpdated.png"; break;
	        default: imagePath = "spr_Marksman.png"; break;
	        
	        }

	        try {
	            GImage mercImg = new GImage(imagePath);
	            mercImg.setLocation(x + 25, y + 20);
	            mercImg.setSize(100, 80);
	            
	            mercImg.setLocation(
	                    x + (200 - mercImg.getWidth()) / 2,
	                    y - mercImg.getHeight() - 10
	                );
	            
	            mercPreviewObjects.add(mercImg);
	            contents.add(mercImg);
	            mainScreen.add(mercImg);
	        } catch (Exception e) {
	            System.out.println("Missing image: " + imagePath);
	        }
	        
	        WeaponItem w = previewMercenary.getWeapon();

	        String[] labels = {
	            "Mercenary To Recruit",
	            "Profession: " + previewMercenary.getProfession(),
	            "HP: " + previewMercenary.getHpMax(),
	            "MP: " + previewMercenary.getManaMax(),
	            "Weapon: " + w.toString()
	        };

	        for (int i = 0; i < labels.length; i++) {
	            GLabel label = new GLabel(labels[i], x + 5, y + 15 + (i * 15));
	            label.setColor(i == 0 ? Color.YELLOW : Color.RED);
	            label.setFont(new java.awt.Font("DialogInput", java.awt.Font.PLAIN, i == 0 ? 12 : 12));
	            mercPreviewObjects.add(label);
	            contents.add(label);
	            mainScreen.add(label);
	        }

	        int[] stats = previewMercenary.getStatSpread();
	        String[] statNames = {"STR","DEX","PRC","INS","CON","WIL","FTH","ARC"};
	        for (int i = 0; i < stats.length; i++) {
	            GLabel stat = new GLabel(statNames[i] + ": " + stats[i], x + 5, y + 90 + (i * 13));
	            stat.setColor(Color.RED);
	            stat.setFont(new java.awt.Font("DialogInput", java.awt.Font.PLAIN, 11));
	            mercPreviewObjects.add(stat);
	            contents.add(stat);
	            mainScreen.add(stat);
	        }
	    }
	    
	    //Displays the Party Stats
	    private void displayPartyStats() {
	        Character[] party = CharacterSelectionPane.myInventory.getPartyMembers();

	        int startX = 3;   
	        int startY = 375;
	        int spacingX = 201; 

	        for (int i = 0; i < party.length; i++) {
	            Character c = party[i];

	            if (c == null) continue;

	            int x = startX + (i * spacingX);
	            int y = startY;

	            GRect box = new GRect(199, 200);
	            box.setLocation(x, y);
	            box.setColor(Color.BLACK);
	            box.setFillColor(Color.BLACK);
	            box.setFilled(true);

	            contents.add(box);
	            mainScreen.add(box);

	            // Profession
	            GLabel prof = new GLabel("Profession: " + c.getProfession());
	            prof.setLocation(x + 5, y + 15);
	            prof.setColor(Color.RED);
	            prof.setFont("DialogInput-PLAIN-12");
	            contents.add(prof);
	            mainScreen.add(prof);

	            // HP
	            GLabel health = new GLabel("HP: " + c.getHp() + "/" + c.getHpMax());
	            health.setLocation(x + 5, y + 30);
	            health.setColor(Color.RED);
	            health.setFont("DialogInput-PLAIN-12");
	            contents.add(health);
	            mainScreen.add(health);

	            // Mana
	            GLabel mana = new GLabel("MP: " + c.getMana() + "/" + c.getManaMax());
	            mana.setLocation(x + 5, y + 45);
	            mana.setColor(Color.RED);
	            mana.setFont("DialogInput-PLAIN-12");
	            contents.add(mana);
	            mainScreen.add(mana);

	            // Weapon
	            GLabel weapon = new GLabel("Weapon: " + c.getWeapon());
	            weapon.setLocation(x + 5, y + 60);
	            weapon.setColor(Color.RED);
	            weapon.setFont("DialogInput-PLAIN-12");
	            contents.add(weapon);
	            mainScreen.add(weapon);

	            int[] stats = c.getStatSpread();

	            String[] statNames = {
	                "STR", "DEX", "PRC", "INS",
	                "CON", "WIL", "FTH", "ARC"
	            };

	            for (int j = 0; j < stats.length; j++) {
	                GLabel stat = new GLabel(statNames[j] + ": " + stats[j]);
	                stat.setLocation(x + 5, y + 75 + (j * 13));
	                stat.setColor(Color.RED);
	                stat.setFont("DialogInput-PLAIN-11");

	                contents.add(stat);
	                mainScreen.add(stat);
	            }
	        }
	    }
	      
	}
