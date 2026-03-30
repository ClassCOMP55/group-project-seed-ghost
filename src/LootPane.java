import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;

public class LootPane extends GraphicsPane {
	private ArrayList<Object> lootItems;
	private ArrayList<GRect> lootBoxes;
	private ArrayList<GLabel> lootLabels;
	private GLabel descriptionLabel;

	public LootPane(MainApplication mainScreen) {
		this.mainScreen = mainScreen;
	}

	@Override
	public void showContent() {
		lootItems = new ArrayList<>();
		lootBoxes = new ArrayList<>();
		lootLabels = new ArrayList<>();
		generateLoot();
		addText();
		displayLoot();
	}

	@Override
	public void hideContent() {
		for(GObject item : contents) {
			mainScreen.remove(item);
		}
		contents.clear();
		lootBoxes.clear();
		lootLabels.clear();
	}

	 //randomly picks 1 to3, items, can be weapon armor or consumable
	 
	private void generateLoot() {
		int numItems = Chance.range(1, 3);
		for (int i = 0; i < numItems; i++) {
			int roll = Chance.range(0, 2);
			if (roll == 0) {
				lootItems.add(new WeaponItem());
			} else if (roll == 1) {
				lootItems.add(new ArmorItem());
			} else {
				ConsumableType[] types = ConsumableType.values();
				lootItems.add(new ConsumableItem(types[Chance.range(0, types.length - 1)]));
			}
		}
	}

	private void addText() {
		GLabel title = new GLabel("Claim your loot", 100, 70);
		title.setColor(Color.RED);
		title.setFont("DialogInput-PLAIN-80");
		title.setLocation((mainScreen.getWidth() - title.getWidth()) / 2, 70);

		contents.add(title);
		mainScreen.add(title);

		descriptionLabel = new GLabel("");
		descriptionLabel.setColor(Color.BLACK);
		descriptionLabel.setFont("DialogInput-PLAIN-16");
		descriptionLabel.setLocation((mainScreen.getWidth() - descriptionLabel.getWidth()) / 2, 110);
		contents.add(descriptionLabel);
		mainScreen.add(descriptionLabel);
	}

	
	 //displays loot items on screen
	 //positions are hardcoded based on how many items the each of them have
	 
	private void displayLoot() {
		int numItems = lootItems.size();
		int boxWidth = 160;
		int boxHeight = 180;
		int startY = 150;
		int[] xPositions;

		if (numItems == 1) {
			xPositions = new int[] {320};
		} else if (numItems == 2) {
			xPositions = new int[] {220, 420};
		} else {
			xPositions = new int[] {120, 320, 520};
		}

		for (int i = 0; i < numItems; i++) {
			int x = xPositions[i];

			GRect box = new GRect(x, startY, boxWidth, boxHeight);
			box.setFilled(true);
			box.setFillColor(Color.DARK_GRAY);
			box.setColor(Color.WHITE);
			contents.add(box);
			mainScreen.add(box);
			lootBoxes.add(box);

			GLabel nameLabel = new GLabel(getItemName(lootItems.get(i)));
			nameLabel.setFont("DialogInput-PLAIN-13");
			nameLabel.setColor(Color.WHITE);
			nameLabel.setLocation(x + (boxWidth - nameLabel.getWidth()) / 2, startY + 30);
			contents.add(nameLabel);
			mainScreen.add(nameLabel);
			lootLabels.add(nameLabel);

			GLabel typeLabel = new GLabel(getItemType(lootItems.get(i)));
			typeLabel.setFont("DialogInput-PLAIN-11");
			typeLabel.setColor(Color.LIGHT_GRAY);
			typeLabel.setLocation(x + (boxWidth - typeLabel.getWidth()) / 2, startY + 50);
			contents.add(typeLabel);
			mainScreen.add(typeLabel);

			final int index = i;
			GRect takeBtn = new GRect(x + 30, startY + boxHeight - 50, 100, 35);
			takeBtn.setFilled(true);
			takeBtn.setFillColor(Color.DARK_GRAY);
			takeBtn.setColor(Color.WHITE);

			GLabel takeLbl = new GLabel("Take");
			takeLbl.setFont("DialogInput-BOLD-14");
			takeLbl.setColor(Color.WHITE);
			takeLbl.setLocation(takeBtn.getX() + (takeBtn.getWidth() - takeLbl.getWidth()) / 2,
					takeBtn.getY() + (takeBtn.getHeight() - takeLbl.getHeight()) / 2 + 12);

			takeBtn.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseEntered(MouseEvent e) { takeBtn.setFillColor(Color.GRAY); }
				public void mouseExited(MouseEvent e)  { takeBtn.setFillColor(Color.DARK_GRAY); }
				public void mouseClicked(MouseEvent e) { takeItem(index, takeBtn); }
			});
			takeLbl.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(MouseEvent e) { takeItem(index, takeBtn); }
			});

			contents.add(takeBtn);
			mainScreen.add(takeBtn);
			contents.add(takeLbl);
			mainScreen.add(takeLbl);
		}


		GRect leaveBtn = new GRect(300, 460, 200, 50);
		leaveBtn.setFilled(true);
		leaveBtn.setFillColor(Color.DARK_GRAY);
		leaveBtn.setColor(Color.WHITE);

		GLabel leaveLbl = new GLabel("Leave");
		leaveLbl.setFont("DialogInput-BOLD-18");
		leaveLbl.setColor(Color.WHITE);
		leaveLbl.setLocation(leaveBtn.getX() + (leaveBtn.getWidth() - leaveLbl.getWidth()) / 2,
				leaveBtn.getY() + (leaveBtn.getHeight() - leaveLbl.getHeight()) / 2 + 15);

		leaveBtn.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(MouseEvent e) { leaveBtn.setFillColor(Color.GRAY); }
			public void mouseExited(MouseEvent e)  { leaveBtn.setFillColor(Color.DARK_GRAY); }
			public void mouseClicked(MouseEvent e) { mainScreen.switchToMapPane(); }
		});
		leaveLbl.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent e) { mainScreen.switchToMapPane(); }
		});

		contents.add(leaveBtn);
		mainScreen.add(leaveBtn);
		contents.add(leaveLbl);
		mainScreen.add(leaveLbl);
	}


	private void takeItem(int index, GRect btn) {
		if (index >= lootItems.size()) return;
		Object item = lootItems.get(index);

		if (item instanceof ConsumableItem) {
			ConsumableItem c = (ConsumableItem) item;
			Character[] party = CharacterSelectionPane.myInventory.getPartyMembers();
			for (Character member : party) {
				if (member != null && !member.isDead()) {
					c.use(member);
					setDescription("Used " + getItemName(item) + " on " + member.getProfession() + "!");
					break;
				}
			}
		} else {
			// TODO add to inventory once thats done
			setDescription("Picked up " + getItemName(item) + "!");
			System.out.println("Picked up: " + getItemName(item));
		}

		// gray it out so they cant take it twice
		btn.setFillColor(Color.LIGHT_GRAY);
		btn.setColor(Color.LIGHT_GRAY);
	}

	private void setDescription(String text) {
		descriptionLabel.setLabel(text);
		descriptionLabel.setLocation((mainScreen.getWidth() - descriptionLabel.getWidth()) / 2, 110);
	}

	private String getItemName(Object item) {
		if (item instanceof WeaponItem)     return ((WeaponItem) item).toString();
		if (item instanceof ArmorItem)      return ((ArmorItem) item).toString();
		if (item instanceof ConsumableItem) {
			switch (((ConsumableItem) item).getType()) {
				case HEALTH: return "Health Potion";
				case MANA:   return "Mana Potion";
				case ELIXIR: return "Elixir";
				default:     return "Potion";
			}
		}
		return "Unknown Item";
	}

	private String getItemType(Object item) {
		if (item instanceof WeaponItem)     return "Weapon";
		if (item instanceof ArmorItem)      return "Armor";
		if (item instanceof ConsumableItem) return "Consumable";
		return "";
	}
}
// main application: crate a __varialbel__; called __lootpane__ and __initializie__ it below and then create a switch to method, methods hat say random generator
// bunch of __dofferent__ __relecs__ that just picks one of them this is what i currently have in that