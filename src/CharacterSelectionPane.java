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
	public static PlayerInventory myInventory;

	public CharacterSelectionPane(MainApplication mainScreen) {
		this.mainScreen = mainScreen;
	}
	
	@Override
	public void showContent() {
		MapPane.mapPath.clear();
		MapPane.createPath();
		MapPane.currPosition = MapPane.mapPath.get(0);
		
		myChars = new ArrayList<>();
		myImages = new ArrayList<>();
		createBackground();
		addText();
		createCharacters();
		showStats();
		
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
		GRect backGround = new GRect(MainApplication.WINDOW_WIDTH,MainApplication.WINDOW_HEIGHT);
		backGround.setColor(Color.DARK_GRAY);
		backGround.setFillColor(Color.DARK_GRAY);
		backGround.setFilled(true);
		backGround.setLocation(0, 0);
		contents.add(backGround);
		mainScreen.add(backGround);
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
		case "knight": charImage = new GImage("spr_Knight.png",50+(200*i),350) ; break;
		case "samurai": charImage = new GImage("spr_Samurai.png",50+(200*i),350) ; break;
		case "thief": charImage = new GImage("spr_Thief.png",50+(200*i),350) ; break;
		case "viking": charImage = new GImage("spr_Viking.png",50+(200*i),350); break;
		case "sorcerer": charImage = new GImage("spr_Sorcerer.png",50+(200*i),350); break;
		case "paladin": charImage = new GImage("spr_Paladin.png",50+(200*i),350); break;
		case "cleric": charImage = new GImage("spr_Cleric.png",50+(200*i),350); break;
		case "ranger": charImage = new GImage("spr_RangerUpdated.png",50+(200*i),350);charImage.setSize(150, 150); break;
		default: charImage = new GImage("spr_Marksman.png",50+(200*i),350); break;
		}
		double width = MainApplication.WINDOW_WIDTH*((5.0+2.0*i*charImage.getWidth())/800.0);
		charImage.scale(1.3, 1.3);
		charImage.setLocation(width, (600-charImage.getHeight())/2);
		myImages.add(charImage);
		contents.add(charImage);
		mainScreen.add(charImage);
	}
	
	private void showStats() {
		for (Character myChar:myChars) {
			int index = myChars.indexOf(myChar);
			GImage image = myImages.get(index);
			GRect box = new GRect(150,185);
			box.setLocation(image.getX(), image.getY()+image.getHeight());
			box.setColor(Color.BLACK);
			box.setFillColor(Color.BLACK);
			box.setFilled(true);
			contents.add(box);
			mainScreen.add(box);
			
			GLabel prof = new GLabel("Profession: "+myChar.getProfession());
			prof.setLocation(box.getX()+5, box.getY()+15);
			prof.setColor(Color.RED);
			prof.setFont("ARIEL-PLAIN-14");
			contents.add(prof);
			mainScreen.add(prof);
			
			GLabel health = new GLabel("Health: "+myChar.getHp());
			health.setLocation(box.getX()+5, box.getY()+30);
			health.setColor(Color.RED);
			health.setFont("ARIEL-PLAIN-14");
			contents.add(health);
			mainScreen.add(health);
			
			GLabel mana = new GLabel("Mana: "+myChar.getManaMax());
			mana.setLocation(box.getX()+5, box.getY()+45);
			mana.setColor(Color.RED);
			mana.setFont("ARIEL-PLAIN-14");
			contents.add(mana);
			mainScreen.add(mana);
			
			GLabel weapon = new GLabel("Weapon: "+myChar.getWeapon().getType());
			weapon.setLocation(box.getX()+5, box.getY()+60);
			weapon.setColor(Color.RED);
			weapon.setFont("ARIEL-PLAIN-14");
			contents.add(weapon);
			mainScreen.add(weapon);
			
			int[] stats = myChar.getStatSpread();
			
			GLabel str = new GLabel("Strength: "+stats[0]);
			str.setLocation(box.getX()+5, box.getY()+75);
			str.setColor(Color.RED);
			str.setFont("ARIEL-PLAIN-14");
			contents.add(str);
			mainScreen.add(str);
			
			GLabel dex = new GLabel("Dexterity: "+stats[1]);
			dex.setLocation(box.getX()+5, box.getY()+90);
			dex.setColor(Color.RED);
			dex.setFont("ARIEL-PLAIN-14");
			contents.add(dex);
			mainScreen.add(dex);
			
			GLabel prc = new GLabel("Precison: "+stats[2]);
			prc.setLocation(box.getX()+5, box.getY()+105);
			prc.setColor(Color.RED);
			prc.setFont("ARIEL-PLAIN-14");
			contents.add(prc);
			mainScreen.add(prc);
			
			GLabel ist = new GLabel("Instinct: "+stats[3]);
			ist.setLocation(box.getX()+5, box.getY()+120);
			ist.setColor(Color.RED);
			ist.setFont("ARIEL-PLAIN-14");
			contents.add(ist);
			mainScreen.add(ist);
			
			GLabel con = new GLabel("Constitution: "+stats[4]);
			con.setLocation(box.getX()+5, box.getY()+135);
			con.setColor(Color.RED);
			con.setFont("ARIEL-PLAIN-14");
			contents.add(con);
			mainScreen.add(con);
			
			GLabel wil = new GLabel("Willpower: "+stats[5]);
			wil.setLocation(box.getX()+5, box.getY()+150);
			wil.setColor(Color.RED);
			wil.setFont("ARIEL-PLAIN-14");
			contents.add(wil);
			mainScreen.add(wil);
			
			GLabel fth = new GLabel("Faith: "+stats[6]);
			fth.setLocation(box.getX()+5, box.getY()+165);
			fth.setColor(Color.RED);
			fth.setFont("ARIEL-PLAIN-14");
			contents.add(fth);
			mainScreen.add(fth);
			
			GLabel arc = new GLabel("Arcane: "+stats[7]);
			arc.setLocation(box.getX()+5, box.getY()+180);
			arc.setColor(Color.RED);
			arc.setFont("ARIEL-PLAIN-14");
			contents.add(arc);
			mainScreen.add(arc);
			
			
		}
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
		if (mainScreen.getElementAtLocation(e.getX(), e.getY()) == contents.get(2)) {
			myInventory = new PlayerInventory(myChars.get(0));
			System.out.println("option 1");
			System.out.println("You are a "+myChars.get(0).getProfession());
		}
		else if (mainScreen.getElementAtLocation(e.getX(), e.getY()) == contents.get(3)) {
			myInventory = new PlayerInventory(myChars.get(1));
			System.out.println("option 2");
			System.out.println("You are a "+myChars.get(1).getProfession());
		}
		else if (mainScreen.getElementAtLocation(e.getX(), e.getY()) == contents.get(4)) {
			myInventory = new PlayerInventory(myChars.get(2));
			System.out.println("option 3");
			System.out.println("You are a "+myChars.get(2).getProfession());
		}
		
		if (mainScreen.getElementAtLocation(e.getX(), e.getY()) instanceof GImage) {
			mainScreen.switchToMapPane();
		}
	}
		

}
