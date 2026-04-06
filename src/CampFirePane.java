import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import acm.graphics.*;

public class CampFirePane extends GraphicsPane{
	 private int actionPoints;
	 private GLabel apLabel;
	 private GLabel actionMessage;
	 private HashMap<String, GRect> actionButtons;

	 
	public CampFirePane(MainApplication mainScreen) {
		this.mainScreen = mainScreen;
		 actionButtons = new HashMap<>();
	}
	
	@Override
	public void showContent() {
		actionPoints = 2;
		addText();
		addButtons();
		displayActionPoints();
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
	        contents.add(apLabel);
	        mainScreen.add(apLabel);
	    }
	    //action points used
	    private boolean useActionPoint(String actionType) {
	        if (actionPoints <= 0 || !actionButtons.containsKey(actionType)) {
	        	showMessage("No actions remaining.");
	            return false;
	        }
	        
	        if (!actionType.equals("heal") && !actionType.equals("train")) {
	            GRect button = actionButtons.get(actionType);
	            if (button != null) {
	                button.setFillColor(Color.LIGHT_GRAY);
	                button.setFilled(true);
	                actionButtons.remove(actionType);
	            }
	        }
	        
	        actionPoints--;
	        displayActionPoints();

	        return true;
	    }
	 // Handle button clicks
	    private void handleAction(String actionType) {
	    	if (actionType.equals("leave")) {
	            mainScreen.switchToMapPane();
	            return;
	        }
	    	
	    	if (actionType.equals("mercenary")) {
	            if (isPartyFull()) {
	                showMessage("Party full! Cannot recruit a new mercenary.");
	                return;
	            }
	            if (useActionPoint("mercenary")) {
	                recruitMercenary();
	            }
	            return;
	        }
	    	
	    	if (actionPoints <= 0 || !actionButtons.containsKey(actionType)) {
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
                if (useActionPoint("train")) trainParty();
                break;
            case "mercenary":
                if (useActionPoint("mercenary")) recruitMercenary();
                break;
            case "leave":
                mainScreen.switchToMapPane();
                break;
	        }   
	    }
	    
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
	        for (Character c : party) {
	            if (c != null && c.getHp() > 0) {

	                int halfHp = (int)(c.getHpMax() * 0.50);

	                if (c.getHp() < halfHp) {
	                    c.setHp(halfHp);
	                    anyHealedHalf = true;
	                } else if (c.getHp() < c.getHpMax()) {
	                    c.setHp(c.getHpMax());
	                    healedToFull = true;
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
	            showMessage("All party members already at full HP and MP!");
	            return false; 
	        }
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
	        Character newMerc = new Character(Chance.choose(new String[]{
	                "knight","samurai","thief","viking","cleric","sorcerer","paladin","ranger","marksman"
	        }));
	        PlayerInventory inv = CharacterSelectionPane.myInventory;

	        for (int i = 0; i < inv.getPartyMembers().length; i++) {
	            if (inv.getPartyMembers()[i] == null) {
	                inv.getPartyMembers()[i] = newMerc;
	                showMessage("Recruited a new mercenary: " + newMerc.getProfession());
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

	        actionMessage = new GLabel(message, 200, 500);
	        actionMessage.setFont("DialogInput-BOLD-16");
	        actionMessage.setColor(Color.CYAN);

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
	    
	      
	}
