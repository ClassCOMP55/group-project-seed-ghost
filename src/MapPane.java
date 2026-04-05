import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GLine;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GRect;

import java.util.ArrayList;

public class MapPane extends GraphicsPane {
	
	private ArrayList<Node> mapPath;
	private ArrayList<GObject> myNodeObjects;
	public static Node currPosition;
	int count;
	
	public MapPane(MainApplication mainScreen) {
		this.mainScreen = mainScreen;
		mapPath = new ArrayList<>();
		createPath();
		currPosition = mapPath.get(0);
	}
	
	@Override
	public void showContent() {
		myNodeObjects = new ArrayList<>();
		createBackground();
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
	}
	
private void createMap() {
	count =0;
		
		createRow(1,((mainScreen.getWidth() - 40) / 2),550);
		createRow(2,((mainScreen.getWidth() - 40) / 2)-50,450);
		createRow(3,((mainScreen.getWidth() - 40) / 2)-100,350);
		createRow(5,((mainScreen.getWidth() - 40) / 2)-200,250);
		createRow(2,((mainScreen.getWidth() - 40) / 2)-50,150);
		createRow(1,((mainScreen.getWidth() - 40) / 2),50);
		drawLines();
		
	}

	public void drawLines() {
		for (int e = 0;e<myNodeObjects.size()-1;e++) {
			GImage image = mapPath.get(e).getSprite();
			Node node = mapPath.get(e);
			int[] myArr = node.accessibleNodes;
		
			for (int i:myArr) {
				GImage image2 = (GImage) myNodeObjects.get(i);
				GLine line = new GLine(image.getX()+25,image.getY()+33,image2.getX()+25,image2.getY()+33);
				line.setLineWidth(3);
				line.setColor(Color.RED);
				contents.add(line);
				mainScreen.add(line);
				image2.sendToFront();
			
			}
			image.sendToFront();
		}
	}
	
	private void createRow(int limit, double startX, double startY) {
		for (int i = 0;i<limit;i++) {
			GImage node = mapPath.get(count).getSprite();
			myNodeObjects.add(node);
			node.setLocation(startX, startY);
			contents.add(node);
			mainScreen.add(node);
			startX = startX+100;
			count++;
		}
	}
	
	private void createBackground() {
		GRect backGround = new GRect(800,600);
		backGround.setColor(Color.BLACK);
		backGround.setFillColor(Color.BLACK);
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
	
	public Node ovalToNode(GObject obj) {
		return mapPath.get(myNodeObjects.indexOf(obj));
	}
	
	public GObject nodeToOval(Node node) {
		return myNodeObjects.get(mapPath.indexOf(node));
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		GObject obj = mainScreen.getElementAtLocation(e.getX(), e.getY());
		if (mainScreen.getElementAtLocation(e.getX(), e.getY()) == contents.get(1)) {
			mainScreen.switchToShopPane();
		}
		if (myNodeObjects.contains(obj)) {
			GObject oval = mainScreen.getElementAtLocation(e.getX(), e.getY());
			
			if (currPosition.hasAccess(myNodeObjects.indexOf(oval))==true) {
				currPosition = ovalToNode(oval);
				switch(ovalToNode(oval).getType()){
				case "Shop": mainScreen.switchToShopPane(); break;
				case "Combat": mainScreen.switchToCombatPane(); break;
				case "CampFire": mainScreen.switchToCampFirePane(); break;
				case "Loot": mainScreen.switchToLootPane(); break;
				}
			}
			else if (obj == nodeToOval(currPosition) && currPosition.isCleared()==false) {
				
				switch(ovalToNode(oval).getType()){
				case "Shop": mainScreen.switchToShopPane(); break;
				case "Combat": mainScreen.switchToCombatPane(); break;
				case "CampFire": mainScreen.switchToCampFirePane(); break;
				case "Loot": mainScreen.switchToLootPane(); break;
				}
				
			}
			//&& currPosition.isCleared()
			
		}
				
	}
		

	
}
