import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import acm.graphics.GLabel;
import acm.graphics.GLine;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GRect;

public class LootPane extends GraphicsPane {

	private ArrayList<WeaponItem> lootItems = new ArrayList<WeaponItem>();
	private ArrayList<GRect> lootBoxes = new ArrayList<GRect>();
	private ArrayList<GLabel> lootLabels = new ArrayList<GLabel>();
	private ArrayList<GRect> takeBtns = new ArrayList<GRect>();
	private ArrayList<GLabel> takeLbls = new ArrayList<GLabel>();
	private ArrayList<Boolean> claimed = new ArrayList<Boolean>();
	private boolean anyClaimed = false;

	Color BG_DARK = new Color(10, 12, 28);
	Color PANEL_BG = new Color(28, 33, 62);
	Color PANEL_BORDER = new Color(55, 63, 105);
	Color TEAL = new Color(0, 210, 195);
	Color TEAL_TRACK = new Color(15, 50, 60);
	Color WHITE = new Color(240, 242, 255);
	Color GREY_TEXT = new Color(140, 152, 180);
	Color DIVIDER = new Color(55, 63, 105);
	Color HOVER_BTN = new Color(40, 55, 90);
	Color RED_BTN = new Color(60, 20, 45);
	Color RED_BORDER = new Color(180, 60, 100);
	Color RED_TEXT = new Color(210, 100, 140);

	public LootPane(MainApplication mainScreen) {
		this.mainScreen = mainScreen;
	}

	@Override
	public void showContent() {
		generateLoot();
		displayLoot();
	}

	@Override
	public void hideContent() {
		for (GObject item : contents) {
			mainScreen.remove(item);
		}
		contents.clear();
		lootBoxes.clear();
		lootLabels.clear();
		takeBtns.clear();
		takeLbls.clear();
		claimed.clear();
		anyClaimed = false;
	}

	private void generateLoot() {
		lootItems.clear();
		int numItems = (int)(Math.random() * 3) + 1;
		ArrayList<String> usedTypes = new ArrayList<String>();
		while (lootItems.size() < numItems) {
			WeaponItem w = new WeaponItem(true);
			if (!usedTypes.contains(w.getType())) {
				usedTypes.add(w.getType());
				lootItems.add(w);
			}
		}
	}

	private void displayLoot() {
		// galaxy background
		GRect bg = new GRect(MainApplication.WINDOW_WIDTH, MainApplication.WINDOW_HEIGHT);
		bg.setFilled(true);
		bg.setFillColor(BG_DARK);
		bg.setColor(BG_DARK);
		bg.setLocation(0, 0);
		contents.add(bg);
		mainScreen.add(bg);

		GOval nebula1 = new GOval(600, 400);
		nebula1.setLocation(-100, 50);
		nebula1.setFilled(true);
		nebula1.setFillColor(new Color(60, 10, 100, 60));
		nebula1.setColor(new Color(0, 0, 0, 0));
		contents.add(nebula1);
		mainScreen.add(nebula1);

		GOval nebula2 = new GOval(500, 350);
		nebula2.setLocation(MainApplication.WINDOW_WIDTH - 400, 200);
		nebula2.setFilled(true);
		nebula2.setFillColor(new Color(0, 40, 100, 55));
		nebula2.setColor(new Color(0, 0, 0, 0));
		contents.add(nebula2);
		mainScreen.add(nebula2);

		GOval nebula3 = new GOval(450, 300);
		nebula3.setLocation(200, MainApplication.WINDOW_HEIGHT - 280);
		nebula3.setFilled(true);
		nebula3.setFillColor(new Color(80, 0, 80, 50));
		nebula3.setColor(new Color(0, 0, 0, 0));
		contents.add(nebula3);
		mainScreen.add(nebula3);

		java.util.Random rng = new java.util.Random(77);
		for (int i = 0; i < 200; i++) {
			int x = rng.nextInt(MainApplication.WINDOW_WIDTH);
			int y = rng.nextInt(MainApplication.WINDOW_HEIGHT);
			int bright = 120 + rng.nextInt(136);
			int size = 1;
			if (rng.nextInt(8) == 0) size = 2;
			if (rng.nextInt(25) == 0) size = 3;
			GRect star = new GRect(size, size);
			star.setLocation(x, y);
			star.setFilled(true);
			star.setFillColor(new Color(bright, bright, Math.min(bright + 40, 255)));
			star.setColor(new Color(bright, bright, Math.min(bright + 40, 255)));
			contents.add(star);
			mainScreen.add(star);
		}

		for (int i = 0; i < 12; i++) {
			int x = rng.nextInt(MainApplication.WINDOW_WIDTH);
			int y = rng.nextInt(MainApplication.WINDOW_HEIGHT);
			GOval glowStar = new GOval(5, 5);
			glowStar.setLocation(x, y);
			glowStar.setFilled(true);
			glowStar.setFillColor(new Color(180, 200, 255));
			glowStar.setColor(new Color(180, 200, 255));
			contents.add(glowStar);
			mainScreen.add(glowStar);
		}

		// panel
		int numItems = lootItems.size();
		int cardW = 180;
		int cardH = 220;
		int gap = 30;
		int totalCardsW = numItems * cardW + (numItems - 1) * gap;
		int panelW = Math.max(500, totalCardsW + 120);
		int panelH = 420;
		int panelX = (MainApplication.WINDOW_WIDTH - panelW) / 2;
		int panelY = (MainApplication.WINDOW_HEIGHT - panelH) / 2;

		GRect shadow = new GRect(panelW + 20, panelH + 20);
		shadow.setLocation(panelX - 10, panelY - 10);
		shadow.setFilled(true);
		shadow.setFillColor(new Color(0, 0, 10));
		shadow.setColor(new Color(0, 0, 10));
		contents.add(shadow);
		mainScreen.add(shadow);

		GRect panel = new GRect(panelW, panelH);
		panel.setLocation(panelX, panelY);
		panel.setFilled(true);
		panel.setFillColor(PANEL_BG);
		panel.setColor(PANEL_BORDER);
		contents.add(panel);
		mainScreen.add(panel);

		GRect topBar = new GRect(panelW, 5);
		topBar.setLocation(panelX, panelY);
		topBar.setFilled(true);
		topBar.setFillColor(TEAL);
		topBar.setColor(TEAL);
		contents.add(topBar);
		mainScreen.add(topBar);

		GLabel title = new GLabel("Loot");
		title.setFont("DialogInput-BOLD-30");
		title.setColor(WHITE);
		title.setLocation(panelX + (panelW - title.getWidth()) / 2, panelY + 50);
		contents.add(title);
		mainScreen.add(title);

		GLine divider1 = new GLine(panelX + 40, panelY + 65, panelX + panelW - 40, panelY + 65);
		divider1.setColor(DIVIDER);
		contents.add(divider1);
		mainScreen.add(divider1);

		GLabel bannerLbl = new GLabel("Only one treasure can be taken. Choose wisely.");
		bannerLbl.setFont("DialogInput-PLAIN-13");
		bannerLbl.setColor(GREY_TEXT);
		bannerLbl.setLocation(panelX + (panelW - bannerLbl.getWidth()) / 2, panelY + 90);
		contents.add(bannerLbl);
		mainScreen.add(bannerLbl);

		// loot cards
		int startX = panelX + (panelW - totalCardsW) / 2;
		int cardY = panelY + 110;

		for (int i = 0; i < numItems; i++) {
			int x = startX + i * (cardW + gap);
			claimed.add(false);

			GRect box = new GRect(cardW, cardH);
			box.setLocation(x, cardY);
			box.setFilled(true);
			box.setFillColor(new Color(20, 25, 50));
			box.setColor(TEAL);
			contents.add(box);
			mainScreen.add(box);
			lootBoxes.add(box);

			GRect cardTopBar = new GRect(cardW, 3);
			cardTopBar.setLocation(x, cardY);
			cardTopBar.setFilled(true);
			cardTopBar.setFillColor(TEAL);
			cardTopBar.setColor(TEAL);
			contents.add(cardTopBar);
			mainScreen.add(cardTopBar);

			GLabel nameLabel = new GLabel(lootItems.get(i).toString());
			nameLabel.setFont("DialogInput-BOLD-13");
			nameLabel.setColor(WHITE);
			nameLabel.setLocation(x + (cardW - nameLabel.getWidth()) / 2, cardY + 35);
			contents.add(nameLabel);
			mainScreen.add(nameLabel);
			lootLabels.add(nameLabel);

			GLabel typeLabel = new GLabel(lootItems.get(i).getType());
			typeLabel.setFont("DialogInput-PLAIN-11");
			typeLabel.setColor(GREY_TEXT);
			typeLabel.setLocation(x + (cardW - typeLabel.getWidth()) / 2, cardY + 55);
			contents.add(typeLabel);
			mainScreen.add(typeLabel);

			String tag = "";
			if (lootItems.get(i).isRanged()) tag += "Ranged ";
			if (lootItems.get(i).isMagic()) tag += "Magic";
			if (!tag.isEmpty()) {
				GLabel tagLabel = new GLabel(tag.trim());
				tagLabel.setFont("DialogInput-PLAIN-11");
				tagLabel.setColor(new Color(0, 180, 170));
				tagLabel.setLocation(x + (cardW - tagLabel.getWidth()) / 2, cardY + 72);
				contents.add(tagLabel);
				mainScreen.add(tagLabel);
			}

			final int index = i;

			GRect takeBtn = new GRect(cardW - 30, 34);
			takeBtn.setLocation(x + 15, cardY + cardH - 48);
			takeBtn.setFilled(true);
			takeBtn.setFillColor(TEAL_TRACK);
			takeBtn.setColor(TEAL);
			takeBtns.add(takeBtn);

			GLabel takeLbl = new GLabel("Claim");
			takeLbl.setFont("DialogInput-BOLD-13");
			takeLbl.setColor(TEAL);
			takeLbl.setLocation(
					takeBtn.getX() + (takeBtn.getWidth() - takeLbl.getWidth()) / 2,
					takeBtn.getY() + (takeBtn.getHeight() + takeLbl.getAscent()) / 2);
			takeLbls.add(takeLbl);

			takeBtn.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseEntered(MouseEvent e) {
					if (!anyClaimed && !claimed.get(index))
						takeBtn.setFillColor(HOVER_BTN);
				}
				public void mouseExited(MouseEvent e) {
					if (!anyClaimed && !claimed.get(index))
						takeBtn.setFillColor(TEAL_TRACK);
				}
				public void mouseClicked(MouseEvent e) {
					takeItem(index);
				}
			});
			takeLbl.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					takeItem(index);
				}
			});

			contents.add(takeBtn);
			mainScreen.add(takeBtn);
			contents.add(takeLbl);
			mainScreen.add(takeLbl);
		}

		GLine divider2 = new GLine(panelX + 40, panelY + panelH - 65, panelX + panelW - 40, panelY + panelH - 65);
		divider2.setColor(DIVIDER);
		contents.add(divider2);
		mainScreen.add(divider2);

		int leaveBtnWidth = 180;
		int leaveX = panelX + (panelW - leaveBtnWidth) / 2;
		
		GRect leaveBtn = new GRect(leaveBtnWidth, 38);
		leaveBtn.setLocation(leaveX, panelY + panelH - 52);
		leaveBtn.setFilled(true);
		leaveBtn.setFillColor(RED_BTN);
		leaveBtn.setColor(RED_BORDER);

		GLabel leaveLbl = new GLabel("Leave");
		leaveLbl.setFont("DialogInput-BOLD-14");
		leaveLbl.setColor(RED_TEXT);
		leaveLbl.setLocation(
				leaveBtn.getX() + (leaveBtn.getWidth() - leaveLbl.getWidth()) / 2,
				leaveBtn.getY() + (leaveBtn.getHeight() + leaveLbl.getAscent()) / 2);

		leaveBtn.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				leaveBtn.setFillColor(new Color(90, 30, 60));
			}
			public void mouseExited(MouseEvent e) {
				leaveBtn.setFillColor(RED_BTN);
			}
			public void mouseClicked(MouseEvent e) {
				mainScreen.switchToMapPane();
			}
		});
		leaveLbl.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				mainScreen.switchToMapPane();
			}
		});

		contents.add(leaveBtn);
		mainScreen.add(leaveBtn);
		contents.add(leaveLbl);
		mainScreen.add(leaveLbl);
	}

	private void takeItem(int index) {
		if (anyClaimed) return;
		if (claimed.get(index)) return;
		anyClaimed = true;
		claimed.set(index, true);

		// save weapon to player inventory weapon array
		CharacterSelectionPane.myInventory.addWeapon(lootItems.get(index));
		showInventoryMessage(lootItems.get(index).toString() + " added to inventory!");

		takeBtns.get(index).setFillColor(new Color(30, 30, 30));
		takeBtns.get(index).setColor(new Color(60, 60, 60));
		takeLbls.get(index).setLabel("Claimed");
		takeLbls.get(index).setColor(GREY_TEXT);

		for (int i = 0; i < takeBtns.size(); i++) {
			if (i != index) {
				takeBtns.get(i).setFillColor(new Color(30, 30, 30));
				takeBtns.get(i).setColor(new Color(60, 60, 60));
				takeLbls.get(i).setColor(GREY_TEXT);
			}
		}
	}

	private void showInventoryMessage(String message) {
		GRect msgBg = new GRect(MainApplication.WINDOW_WIDTH, 38);
		msgBg.setLocation(0, MainApplication.WINDOW_HEIGHT - 42);
		msgBg.setFilled(true);
		msgBg.setFillColor(new Color(15, 18, 38));
		msgBg.setColor(TEAL);
		contents.add(msgBg);
		mainScreen.add(msgBg);

		GLabel msgLbl = new GLabel(message);
		msgLbl.setFont("DialogInput-BOLD-13");
		msgLbl.setColor(TEAL);
		msgLbl.setLocation(
				(MainApplication.WINDOW_WIDTH - msgLbl.getWidth()) / 2,
				MainApplication.WINDOW_HEIGHT - 42 + (38 + msgLbl.getAscent()) / 2);
		contents.add(msgLbl);
		mainScreen.add(msgLbl);
	}
}