import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import acm.graphics.*;

public class CampFirePane extends GraphicsPane{
	public CampFirePane(MainApplication mainScreen) {
		this.mainScreen = mainScreen;
	}
	
	@Override
	public void showContent() {
		addText();
		addButtons();
	}

	@Override
	public void hideContent() {
		for(GObject item : contents) {
			mainScreen.remove(item);
		}
		contents.clear();
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
	        int x = 200;
	        int yStart = 200;
	        int yStep = 60;

	        createButton("Heal Party (50%)", x, yStart, "heal");
	        createButton("Revive Allies (30%)", x, yStart + yStep, "revive");
	        createButton("Train Party (+10)", x, yStart + yStep * 2, "train");
	        createButton("Recruit Mercenary", x, yStart + yStep * 3, "mercenary");
	        createButton("Leave Camp", x, yStart + yStep * 4, "leave");
	    }

	    // Helper to create a button
	    private void createButton(String text, int x, int y, String actionType) {
	        int width = 300;
	        int height = 40;

	        GRect box = new GRect(x, y - height + 10, width, height);
	        box.setFilled(true);
	        box.setFillColor(Color.DARK_GRAY);

	        GLabel label = new GLabel(text);
	        label.setFont("DialogInput-BOLD-18");
	        label.setLocation(x + (width - label.getWidth()) / 2, y);

	        box.addMouseListener(new java.awt.event.MouseAdapter() {
	            public void mouseEntered(java.awt.event.MouseEvent e) { box.setFillColor(Color.GRAY); }
	            public void mouseExited(java.awt.event.MouseEvent e) { box.setFillColor(Color.DARK_GRAY); }
	            public void mouseClicked(java.awt.event.MouseEvent e) { handleAction(actionType); }
	        });

	        label.addMouseListener(new java.awt.event.MouseAdapter() {
	            public void mouseClicked(java.awt.event.MouseEvent e) { handleAction(actionType); }
	        });

	        contents.add(box);
	        contents.add(label);
	        mainScreen.add(box);
	        mainScreen.add(label);
	    }

	    // Handle button clicks
	    private void handleAction(String actionType) {
	        switch (actionType) {
	            case "heal": healParty(); break;
	            case "revive": reviveAllies(); break;
	            case "train": trainParty(); break;
	            case "mercenary": recruitMercenary(); break;
	            case "leave": mainScreen.switchToMapPane(); break;
	        }
	    }

	    // Heal party to 75%
	    private void healParty() {
	        Character[] party = CharacterSelectionPane.myInventory.getPartyMembers();
	        for (Character c : party) {
	            if (c != null && c.getHp() > 0) {
	                c.setHp(c.getHpMax() * 0.50);
	            }
	        }
	        System.out.println("Party healed to 50%");
	    }

	    // Revive allies to 30%
	    private void reviveAllies() {
	        Character[] party = CharacterSelectionPane.myInventory.getPartyMembers();
	        for (Character c : party) {
	            if (c != null && c.getHp() <= 0) {
	                c.setHp(c.getHpMax() * 0.30);
	            }
	        }
	        System.out.println("Defeated allies revived to 30%");
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
	        System.out.println("Party primary stats increase +10");
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
	                System.out.println("Recruited a new mercenary: " + newMerc.getProfession());
	                return;
	            }
	        }

	        System.out.println("Party full! Mercenary cannot join");
	    }
	}
