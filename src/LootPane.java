import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;

public class LootPane extends GraphicsPane {

	private ArrayList<WeaponItem> lootItems = new ArrayList<WeaponItem>();
	private ArrayList<GRect> lootBoxes = new ArrayList<GRect>();
	private ArrayList<GLabel> lootLabels = new ArrayList<GLabel>();
	private ArrayList<GRect> takeBtns = new ArrayList<GRect>();
	private ArrayList<GLabel> takeLbls = new ArrayList<GLabel>();
	private ArrayList<Boolean> claimed = new ArrayList<Boolean>();

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

		// background
		GRect bg = new GRect(MainApplication.WINDOW_WIDTH, MainApplication.WINDOW_HEIGHT);
		bg.setFilled(true);
		bg.setFillColor(Color.DARK_GRAY);
		bg.setColor(Color.DARK_GRAY);
		bg.setLocation(0, 0);
		contents.add(bg);
		mainScreen.add(bg);

		int numItems = lootItems.size();
		int boxWidth = 200;
		int boxHeight = 260;
		int startY = 150;
		int gap = 30;
		int totalWidth = (numItems * boxWidth) + ((numItems - 1) * gap);
		int screenWidth = MainApplication.WINDOW_WIDTH;
		int startX = (screenWidth - totalWidth) / 2;

		for (int i = 0; i < numItems; i++) {
			int x = startX + i * (boxWidth + gap);
			claimed.add(false);

			GRect box = new GRect(x, startY, boxWidth, boxHeight);
			box.setFilled(true);
			box.setFillColor(new Color(30, 30, 30));
			box.setColor(new Color(180, 140, 60));
			contents.add(box);
			mainScreen.add(box);
			lootBoxes.add(box);

			GLabel nameLabel = new GLabel(lootItems.get(i).toString());
			nameLabel.setFont("DialogInput-BOLD-14");
			nameLabel.setColor(new Color(220, 190, 100));
			nameLabel.setLocation(x + (boxWidth - nameLabel.getWidth()) / 2, startY + 50);
			contents.add(nameLabel);
			mainScreen.add(nameLabel);
			lootLabels.add(nameLabel);

			final int index = i;

			GRect takeBtn = new GRect(x + 25, startY + boxHeight - 55, boxWidth - 50, 35);
			takeBtn.setFilled(true);
			takeBtn.setFillColor(new Color(60, 40, 10));
			takeBtn.setColor(new Color(180, 140, 60));
			takeBtns.add(takeBtn);

			GLabel takeLbl = new GLabel("Claim");
			takeLbl.setFont("DialogInput-BOLD-13");
			takeLbl.setColor(new Color(220, 190, 100));
			takeLbl.setLocation(takeBtn.getX() + (takeBtn.getWidth() - takeLbl.getWidth()) / 2,
					takeBtn.getY() + (takeBtn.getHeight() + takeLbl.getAscent()) / 2);
			takeLbls.add(takeLbl);

			takeBtn.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseEntered(MouseEvent e) {
					if (!claimed.get(index))
						takeBtn.setFillColor(new Color(100, 70, 20));
				}
				public void mouseExited(MouseEvent e) {
					if (!claimed.get(index))
						takeBtn.setFillColor(new Color(60, 40, 10));
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

		// leave button
		int leaveBtnWidth = 180;
		int leaveX = (screenWidth - leaveBtnWidth) / 2;
		GRect leaveBtn = new GRect(leaveX, 460, leaveBtnWidth, 45);
		leaveBtn.setFilled(true);
		leaveBtn.setFillColor(new Color(60, 20, 20));
		leaveBtn.setColor(new Color(180, 60, 60));

		GLabel leaveLbl = new GLabel("Leave");
		leaveLbl.setFont("DialogInput-BOLD-16");
		leaveLbl.setColor(new Color(220, 100, 100));
		leaveLbl.setLocation(leaveBtn.getX() + (leaveBtn.getWidth() - leaveLbl.getWidth()) / 2,
				leaveBtn.getY() + (leaveBtn.getHeight() + leaveLbl.getAscent()) / 2);

		leaveBtn.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				leaveBtn.setFillColor(new Color(100, 30, 30));
			}
			public void mouseExited(MouseEvent e) {
				leaveBtn.setFillColor(new Color(60, 20, 20));
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
		if (claimed.get(index)) return;
		claimed.set(index, true);
		takeBtns.get(index).setFillColor(new Color(40, 40, 40));
		takeBtns.get(index).setColor(new Color(80, 80, 80));
		takeLbls.get(index).setLabel("Claimed");
		takeLbls.get(index).setColor(new Color(100, 100, 100));
	}
}