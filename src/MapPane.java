import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GOval;

public class MapPane extends GraphicsPane {
	
	private Node[] mapPath;
	public MapPane(MainApplication mainScreen) {
		this.mainScreen = mainScreen;
	}
	
	@Override
	public void showContent() {
		addText();
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
		for (int i=0;i<mapPath.length;i++) {
			mapPath[i] = new Node(i);
		}
	}
	
	private void addText() {
		
		GLabel title = new GLabel("Map", 100, 100);
		title.setColor(Color.RED);
		title.setFont("DialogInput-PLAIN-45");
		title.setLocation((mainScreen.getWidth() - title.getWidth()) / 2, 30);
		
		contents.add(title);
		mainScreen.add(title);
		
		GLabel shopLabel = new GLabel("Shop Pane", 100, 70);
		shopLabel.setColor(Color.BLACK);
		shopLabel.setFont("DialogInput-PLAIN-15");
		shopLabel.setLocation(((mainScreen.getWidth() - title.getWidth()) / 2)+345, 500);
		
		contents.add(shopLabel);
		mainScreen.add(shopLabel);
		
		GLabel combatLabel = new GLabel("Combat Pane", 100, 70);
		combatLabel.setColor(Color.BLACK);
		combatLabel.setFont("DialogInput-PLAIN-15");
		combatLabel.setLocation(((mainScreen.getWidth() - title.getWidth()) / 2)+345, 520);
		
		contents.add(combatLabel);
		mainScreen.add(combatLabel);
		
		GLabel lootLabel = new GLabel("Loot Pane", 100, 70);
		lootLabel.setColor(Color.BLACK);
		lootLabel.setFont("DialogInput-PLAIN-15");
		lootLabel.setLocation(((mainScreen.getWidth() - title.getWidth()) / 2)+345, 540);
		
		contents.add(lootLabel);
		mainScreen.add(lootLabel);
		
		GLabel campFireLabel = new GLabel("Campfire Pane", 100, 70);
		campFireLabel.setColor(Color.BLACK);
		campFireLabel.setFont("DialogInput-PLAIN-15");
		campFireLabel.setLocation(((mainScreen.getWidth() - title.getWidth()) / 2)+345, 560);
		
		contents.add(campFireLabel);
		mainScreen.add(campFireLabel);	
	}
	
private void createMap() {
		
		createRow(1,((mainScreen.getWidth() - 40) / 2),560);
		createRow(2,((mainScreen.getWidth() - 40) / 2)-50,460);
		createRow(3,((mainScreen.getWidth() - 40) / 2)-100,360);
		createRow(5,((mainScreen.getWidth() - 40) / 2)-200,260);
		createRow(2,((mainScreen.getWidth() - 40) / 2)-50,160);
		createRow(1,((mainScreen.getWidth() - 40) / 2),60);
		
	}
	
	private void createRow(int limit, double startX, double startY) {
		for (int i = 0;i<limit;i++) {
			GOval node = new GOval(40,40);
			node.setLocation(startX, startY);
			contents.add(node);
			mainScreen.add(node);
			startX = startX+100;
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (mainScreen.getElementAtLocation(e.getX(), e.getY()) == contents.get(1)) {
			mainScreen.switchToShopPane();
		}
	}
		

	
}
