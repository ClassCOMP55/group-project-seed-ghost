import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;

public class CharacterSelectionPane extends GraphicsPane {
	
	private ArrayList<Character> myChars;
	private ArrayList<GImage> myImages;
	private ArrayList<GLabel> infoLabels,extraLabels;
	private ArrayList<GRect> infoButtons,extraRects;
	private GRect close,highlighted;
	private GLabel closeLabel;
	private GLabel str;
	private GImage works;
	private int strClicks = 0;
	private boolean open;
	public static boolean active;
	public static PlayerInventory myInventory;

	public CharacterSelectionPane(MainApplication mainScreen) {
		this.mainScreen = mainScreen;
	}
	
	@Override
	public void showContent() {
		MapPane.mapPath.clear();
		MapPane.createPath();
		MapPane.currPosition = MapPane.mapPath.get(0);
		
		active = false;
		myChars = new ArrayList<>();
		myImages = new ArrayList<>();
		infoLabels = new ArrayList<>();
		infoButtons = new ArrayList<>();
		extraLabels = new ArrayList<>();
		extraRects = new ArrayList<>();
		highlighted = new GRect(0,0);
		open = false;
		createBackground();
		addText();
		createCharacters();
		nameAndInfoDisplay();
	}

	@Override
	public void hideContent() {
		for(GObject item : contents) {
			mainScreen.remove(item);
		}
		contents.clear();
	}
	
	private void addText() {
		GLabel title = new GLabel("Choose your character!", 100, 70);
		title.setColor(Color.RED);
		title.setFont("Ariel-PLAIN-50");
		title.setLocation((mainScreen.getWidth() - title.getWidth()) / 2, 70);
		
		contents.add(title);
		mainScreen.add(title);
		
	}
	
	private void createBackground() {
		GImage background = new GImage("CharacterSelectionBackground.jpeg");
		background.setSize(1366,700);
		background.setLocation(0, 0);
		contents.add(background);
		mainScreen.add(background);
	}
	
	private void createCharacters() {
		ArrayList<String> professions = new ArrayList<>(Arrays.asList("knight","samurai","thief","viking","sorcerer","paladin","marksman","cleric","ranger"));
		for (int i = 0;i<3;i++) {
			int choice = Chance.range(0, professions.size()-1);
			Character myChar = new Character(professions.get(choice));
			professions.remove(choice);
			myChars.add(myChar);
			DrawCharacter(i);
		}
	}
	
	private void DrawCharacter(int i) {
		GImage charImage;
		
		switch(myChars.get(i).getProfession()) {
		case "knight":
			charImage = new GImage("spr_Knight.png");
			break;
		case "samurai":
			charImage = new GImage("spr_Samurai.png");
			break;
		case "thief":
			charImage = new GImage("spr_Thief.png");
			break;
		case "viking":
			charImage = new GImage("spr_Viking.png");
			break;
		case "sorcerer":
			charImage = new GImage("spr_Sorcerer.png");
			break;
		case "paladin":
			charImage = new GImage("spr_Paladin.png");
			break;
		case "cleric":
			charImage = new GImage("spr_Cleric.png");
			break;
		case "ranger":
			charImage = new GImage("spr_RangerUpdated.png");
			charImage.setSize(162, 145);
			break;
		default:
			charImage = new GImage("spr_Marksman.png");
			break;
		}
		
		double height = (MainApplication.WINDOW_HEIGHT-charImage.getHeight())/2;
		double width = MainApplication.WINDOW_WIDTH*((5.0+2.0*i*charImage.getWidth())/800.0);
		charImage.scale(1.3, 1.3);
		charImage.setLocation(width, height);
		myImages.add(charImage);
		contents.add(charImage);
		mainScreen.add(charImage);
	}
	
	private void showStats(Character myChar) {
		
		if (works != null) {
		    mainScreen.remove(works);
		    contents.remove(works);
		    works = null;
		}
		
		GRect border = new GRect(300,400);
		double x = (MainApplication.WINDOW_WIDTH - border.getWidth()) / 2.0;
		double y = (MainApplication.WINDOW_HEIGHT - border.getHeight()) / 2.0;
		
		border.setLocation(x, y);
		border.setFillColor(Color.BLACK);
		border.setColor(Color.BLACK);
		border.setFilled(true);
		contents.add(border);
		mainScreen.add(border);
		extraRects.add(border);
		
		GRect box = new GRect(280,300);
		box.setLocation(x+(border.getWidth()-box.getWidth())/2, y+10);
		box.setFillColor(Color.DARK_GRAY);
		box.setColor(Color.BLACK);
		box.setFilled(true);
		contents.add(box);
		mainScreen.add(box);
		extraRects.add(box);
		
		close = new GRect(280,70);
		close.setLocation(box.getX(), box.getY()+box.getHeight()+10);
		close.setColor(Color.BLACK);
		close.setFillColor(Color.DARK_GRAY);
		close.setFilled(true);
		contents.add(close);
		mainScreen.add(close);
		
		closeLabel = new GLabel("Close");
		closeLabel.setFont("ARIEL-Bold-15");
		closeLabel.setLocation(close.getX()+(close.getWidth()-closeLabel.getWidth())/2,close.getY()+(close.getHeight()+closeLabel.getAscent())/2);
		contents.add(closeLabel);
		mainScreen.add(closeLabel);
		
		GLabel title = new GLabel("Stats");
		title.setFont("ARIEL-Bold-30");
		title.setLocation(box.getX()+5, box.getY()+title.getHeight());
		title.setColor(Color.BLACK);
		contents.add(title);
		mainScreen.add(title);
		extraLabels.add(title);
		
		GLabel health = new GLabel(". Health: "+myChar.getHp());
		health.setFont("ARIEL-Bold-15");
		health.setLocation(box.getX()+5, title.getY()+health.getHeight()+5);
		health.setColor(Color.WHITE);
		contents.add(health);
		mainScreen.add(health);
		extraLabels.add(health);
		
		GLabel mana = new GLabel(". Mana: "+myChar.getManaMax());
		mana.setFont("ARIEL-Bold-15");
		mana.setLocation(box.getX()+5, health.getY()+mana.getHeight());
		mana.setColor(Color.WHITE);
		contents.add(mana);
		mainScreen.add(mana);
		extraLabels.add(mana);
		
		GLabel weapon = new GLabel(". Weapon: "+myChar.getWeapon().toString());
		weapon.setFont("ARIEL-Bold-15");
		weapon.setLocation(box.getX()+5, mana.getY()+weapon.getHeight());
		weapon.setColor(Color.WHITE);
		contents.add(weapon);
		mainScreen.add(weapon);
		extraLabels.add(weapon);
		
		GLabel armor = new GLabel(". Armor: "+myChar.getArmor().toString());
		armor.setFont("ARIEL-Bold-15");
		armor.setLocation(box.getX()+5, weapon.getY()+armor.getHeight());
		armor.setColor(Color.WHITE);
		contents.add(armor);
		mainScreen.add(armor);
		extraLabels.add(armor);
		
		int[] stats = myChar.getStatSpread();
		
		str = new GLabel(". Strength: "+stats[0]);
		str.setFont("ARIEL-Bold-15");
		str.setLocation(box.getX()+5, armor.getY()+str.getHeight());
		str.setColor(Color.WHITE);
		contents.add(str);
		mainScreen.add(str);
		extraLabels.add(str);
		
		works = new GImage("theone.gif");
		works.setVisible(false);
		works.setLocation(str.getX() + 120, str.getY() - 40);

		contents.add(works);
		mainScreen.add(works);
		
		GLabel dex = new GLabel(". Dexterity: "+stats[1]);
		dex.setFont("ARIEL-Bold-15");
		dex.setLocation(box.getX()+5,str.getY()+dex.getHeight());
		dex.setColor(Color.WHITE);
		contents.add(dex);
		mainScreen.add(dex);
		extraLabels.add(dex);
		
		GLabel prc = new GLabel(". Precison: "+stats[2]);
		prc.setFont("ARIEL-Bold-15");
		prc.setLocation(box.getX()+5, dex.getY()+prc.getHeight());
		prc.setColor(Color.WHITE);
		contents.add(prc);
		mainScreen.add(prc);
		extraLabels.add(prc);
		
		GLabel ist = new GLabel(". Instinct: "+stats[3]);
		ist.setFont("ARIEL-Bold-15");
		ist.setLocation(box.getX()+5,  prc.getY()+ist.getHeight());
		ist.setColor(Color.WHITE);
		contents.add(ist);
		mainScreen.add(ist);
		extraLabels.add(ist);
		
		GLabel con = new GLabel(". Constitution: "+stats[4]);
		con.setFont("ARIEL-Bold-15");
		con.setLocation(box.getX()+5, ist.getY()+con.getHeight());
		con.setColor(Color.WHITE);
		contents.add(con);
		mainScreen.add(con);
		extraLabels.add(con);
		
		GLabel wil = new GLabel(". Willpower: "+stats[5]);
		wil.setFont("ARIEL-Bold-15");
		wil.setLocation(box.getX()+5,con.getY()+wil.getHeight());
		wil.setColor(Color.WHITE);
		contents.add(wil);
		mainScreen.add(wil);
		extraLabels.add(wil);
		
		GLabel fth = new GLabel(". Faith: "+stats[6]);
		fth.setFont("ARIEL-Bold-15");
		fth.setLocation(box.getX()+5,wil.getY()+fth.getHeight());
		fth.setColor(Color.WHITE);
		contents.add(fth);
		mainScreen.add(fth);
		extraLabels.add(fth);
		
		GLabel arc = new GLabel(". Arcane: "+stats[7]);
		arc.setFont("ARIEL-Bold-15");
		arc.setLocation(box.getX()+5, fth.getY()+arc.getHeight());
		arc.setColor(Color.WHITE);
		contents.add(arc);
		mainScreen.add(arc);
		extraLabels.add(arc);
	}
	
	public void hideStats() {
		for (GRect rect: extraRects) {
			contents.remove(rect);
			mainScreen.remove(rect);
		}
		extraRects.clear();
		
		for (GLabel label: extraLabels) {
			contents.remove(label);
			mainScreen.remove(label);
		}
		extraLabels.clear();
		
		if (works != null) {
		    mainScreen.remove(works);
		    contents.remove(works);
		    works = null;
		}
		
		mainScreen.remove(close);
		contents.remove(close);
		mainScreen.remove(closeLabel);
		contents.remove(closeLabel);
	}
	
	public void nameAndInfoDisplay(){
		for (Character c:myChars) {
			
			int index = myChars.indexOf(c);
			GImage image = myImages.get(index);
			
			GLabel name = new GLabel(c.toString());
			name.setFont(("ARIEL-BOLD-13"));
			
			GRect box = new GRect(name.getWidth()+10,name.getHeight()+10);
			box.setFilled(true);
			box.setFillColor(Color.DARK_GRAY);
			box.setLocation(image.getX()+(image.getWidth()-box.getWidth())/2, image.getY()-name.getHeight()-10);
			contents.add(box);
			mainScreen.add(box);
			
			double centerX = box.getX() + box.getWidth() / 2;
			double centerY = box.getY() + box.getHeight() / 2;
			double x = centerX - name.getWidth() / 2;
			double y = centerY + name.getHeight() / 2 - name.getDescent();

			name.setLocation(x,y);
			contents.add(name);
			mainScreen.add(name);
			
			GLabel info = new GLabel("More Information");
			info.setFont(("ARIEL-BOLD-18"));
			info.setColor(Color.WHITE);
			
			GRect infoBox = new GRect(info.getWidth()+10,info.getHeight()+20);
			infoBox.setFilled(true);
			infoBox.setFillColor(Color.DARK_GRAY);
			infoBox.setLocation(image.getX()+(image.getWidth()-infoBox.getWidth())/2, image.getY()+image.getHeight()+10);
			contents.add(infoBox);
			mainScreen.add(infoBox);
			
			centerX = infoBox.getX() + infoBox.getWidth() / 2;
			centerY = infoBox.getY() + infoBox.getHeight() / 2;
			x = centerX - info.getWidth() / 2;
			y = centerY + info.getHeight() / 2 - info.getDescent();
			
			info.setLocation(x,y);
			contents.add(info);
			mainScreen.add(info);
			
			infoButtons.add(infoBox);
			infoLabels.add(info);	
		}
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		GObject obj = mainScreen.getElementAtLocation(e.getX(), e.getY());
		
		if (mainScreen.getElementAtLocation(e.getX(), e.getY()) == contents.get(2)) {
			myInventory = new PlayerInventory(myChars.get(0));
			System.out.println("option 1");
			System.out.println("You are a "+myChars.get(0).getProfession());
			active = true;
			mainScreen.switchToMapPane();
		}
		else if (mainScreen.getElementAtLocation(e.getX(), e.getY()) == contents.get(3)) {
			myInventory = new PlayerInventory(myChars.get(1));
			System.out.println("option 2");
			System.out.println("You are a "+myChars.get(1).getProfession());
			active = true;
			mainScreen.switchToMapPane();
		}
		else if (mainScreen.getElementAtLocation(e.getX(), e.getY()) == contents.get(4)) {
			myInventory = new PlayerInventory(myChars.get(2));
			System.out.println("option 3");
			System.out.println("You are a "+myChars.get(2).getProfession());
			active = true;
			mainScreen.switchToMapPane();
		}
		else if (infoButtons.contains(obj) && open == false) {
			int index = infoButtons.indexOf(obj);
			showStats(myChars.get(index));
			open = true;
		}
		else if (infoLabels.contains(obj) && open == false) {
			int index = infoLabels.indexOf(obj);
			showStats(myChars.get(index));
			open = true;
		}
		else if (obj == close || obj == closeLabel) {
			hideStats();
			open = false;
		}
		else if (obj == str) {
		    strClicks++;

		    if (strClicks >= 5 && works != null) {
		        works.setVisible(true);
		        System.out.println("Easter egg unlocked!");
		    }
		}
		else if (obj == works && works != null && works.isVisible()) {
		    works.setVisible(false);
		    strClicks = 0;
		}
		
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		
		GObject obj = mainScreen.getElementAtLocation(e.getX(), e.getY());
		
		if (infoButtons.contains(obj)) {
			int index = infoButtons.indexOf(obj);
			infoButtons.get(index).setFillColor(Color.LIGHT_GRAY);
			highlighted = infoButtons.get(index);
		}
		else if (infoLabels.contains(obj)) {
			int index = infoLabels.indexOf(obj);
			infoButtons.get(index).setFillColor(Color.LIGHT_GRAY);
			highlighted = infoButtons.get(index);
		}
		else if(obj == close || obj == closeLabel){
			close.setFillColor(Color.LIGHT_GRAY);
			highlighted = close;
		}
		else {
			highlighted.setFillColor(Color.DARK_GRAY);
		}
		
	}
		

}
