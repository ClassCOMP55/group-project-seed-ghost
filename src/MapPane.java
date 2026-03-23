import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GLabel;
import acm.graphics.GLine;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GRect;

import java.util.ArrayList;

public class MapPane extends GraphicsPane {
	
	private ArrayList<Node> mapPath;
	private ArrayList<GOval> myNodeObjects;
	
	public MapPane(MainApplication mainScreen) {
		this.mainScreen = mainScreen;
	}
	
	@Override
	public void showContent() {
		mapPath = new ArrayList<>();
		myNodeObjects = new ArrayList<>();
		createBackground();
		addText();
		createPath();
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
		
		GLabel shopLabel = new GLabel("Shop", 100, 70);
		shopLabel.setColor(Color.BLUE);
		shopLabel.setFont("DialogInput-PLAIN-15");
		shopLabel.setLocation(((mainScreen.getWidth() - title.getWidth()) / 2)+345, 500);
		
		contents.add(shopLabel);
		mainScreen.add(shopLabel);
		
		GLabel combatLabel = new GLabel("Combat", 100, 70);
		combatLabel.setColor(Color.RED);
		combatLabel.setFont("DialogInput-PLAIN-15");
		combatLabel.setLocation(((mainScreen.getWidth() - title.getWidth()) / 2)+345, 520);
		
		contents.add(combatLabel);
		mainScreen.add(combatLabel);
		
		GLabel lootLabel = new GLabel("Loot", 100, 70);
		lootLabel.setColor(Color.BLACK);
		lootLabel.setFont("DialogInput-PLAIN-15");
		lootLabel.setLocation(((mainScreen.getWidth() - title.getWidth()) / 2)+345, 540);
		
		contents.add(lootLabel);
		mainScreen.add(lootLabel);
		
		GLabel campFireLabel = new GLabel("Campfire", 100, 70);
		campFireLabel.setColor(Color.ORANGE);
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
		drawLines();
		
	}
	
	private void createRow(int limit, double startX, double startY) {
		for (int i = 0;i<limit;i++) {
			GOval node = new GOval(40,40);
			myNodeObjects.add(node);
			setColor(node);
			node.setLocation(startX, startY);
			contents.add(node);
			mainScreen.add(node);
			startX = startX+100;
		}
	}
	
	public void drawLines() {
		for (int e = 0;e<myNodeObjects.size()-1;e++) {
			GOval oval = myNodeObjects.get(e);
			Node node = ovalToNode(oval);
			int[] myArr = node.accessibleNodes;
			
			for (int i:myArr) {
				GOval oval2 = myNodeObjects.get(i);
				GLine line = new GLine(oval.getX()+20,oval.getY()+20,oval2.getX()+20,oval2.getY()+20);
				contents.add(line);
				mainScreen.add(line);
				oval2.sendToFront();
				
			}
			oval.sendToFront();
		}
	
	}
	
	private void createBackground() {
		GRect backGround = new GRect(800,600);
		backGround.setColor(Color.DARK_GRAY);
		backGround.setFillColor(Color.DARK_GRAY);
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
	
	public Node ovalToNode(GObject oval) {
		return mapPath.get(myNodeObjects.indexOf(oval));
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (mainScreen.getElementAtLocation(e.getX(), e.getY()) == contents.get(1)) {
			mainScreen.switchToShopPane();
		}
		if (mainScreen.getElementAtLocation(e.getX(), e.getY()) instanceof GOval) {
			GObject oval = mainScreen.getElementAtLocation(e.getX(), e.getY());
			switch(ovalToNode(oval).getType()){
			case "Shop": mainScreen.switchToMenuPane();
			case "Combat": mainScreen.switchToMenuPane();
			case "CampFire": mainScreen.switchToMenuPane();
			case "Loot": mainScreen.switchToMenuPane();
			}
		}
				
	}
		

	
}
