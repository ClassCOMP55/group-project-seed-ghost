import java.awt.Color;
import java.awt.Label;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import acm.graphics.*;

//The ShopPane
public class ShopPane extends GraphicsPane {
	private ShopInventory inventory;
	private Character player; 
	
	private ArrayList<GLabel> itemLabels;
	
	public ShopPane(MainApplication mainScreen) {
		this.mainScreen = mainScreen;
	}
	
	//Fix this to make a constructor 
	//public ShopPane(MainApplication map, Character player) {
	//	super();
	//	this.menu
	//	this.player = player; 
		
	//	inventory = new ShopInventory();
	//	inventory.generateInventory();
	//	
	//	itemsLabels = new ArrayList<>();
		
	//}
	
	@Override
	public void showContent() {
		showContent(); 
	//	itemLabels.clear();
		
	/*	GLabel title = new GLabel("Shop (Gold: " + player.getGold()+")");
		title.setLocation(5.,30);
		contents.add(title);
		
		int y = 60;
		List<ShopItem> items = inventory.getItems();
		
		for(int i = 0; i < items.size(); i++) {
			ShopItem item = items.get(i);
			
			String text = i + ": " + item.getName() + " - " item.getPrice() + "g"; 
			GLabel label = new GLabel(text);
			label.setLocation(50, y);
			
			itemLabels.add(label);
			contents.add(label);
			
			y += 30; 
			}
		for(GObject obj : contents) {
			mainScreen.add(obj);
			}
	*/}
	
	private void addText() {
		GLabel title = new GLabel("SHOP", 100, 70);
		title.setColor(Color.BLUE);
		title.setFont("DialogInput-PLAIN-80");
		title.setLocation((mainScreen.getWidth() - title.getWidth()) / 2, 70);
		
		contents.add(title);
		mainScreen.add(title);
		}
	
	//@Override 
/*	public void mouseClicked(MouseEvent e) {
		double mouseX = e.getX();
		double mouseY = e.getY();
		
		for(int i = 0; i < itemLabels.size(); i++) {
			GLabel label = itemLabels.get(i);
			
			if (label.contains(mouseX, mouseY)) {
				attemptPurchase(i);
				break; 
			}
		}
	}
	
	private void attempPurchase(int index) {
		List<ShopItem> items = inventory.getItems();
		
		if (index < 0 || index >= items.size()) return;
		
		ShopItem selected - items.get(index);
		
		if(player.getGold()< selected.getPrice()) {
			System.out.println("Not Enough GOLD!!");
			return;
		}
		player.setGold(player.getGold() - selected.getPrice()); 
		
		giveItemToPlayer(selected);
		items.remove(index);
		
		hideContent();
		showContent(); 
	}
	private void giveItemToPlayer(ShopItem item) {
		switch(item.getType()) {
		case "weapon": player.setMyWeapon((WeaponItem)item.getItem());
		System.out.println("Equipped new weapon: "+ item.getName());
		break;
		
		case "armor": player.setMyArmor((ArmorItem)item.getItem());
		System.out.println("Equipped new armor: "+ item.getName());
		break;
		
		case "potion": 
			System.out.println("Potion purchased: "+ item.getName());
		break;
		
		case "relic":
			System.out.println("Relic purchased: "+ item.getName());
			break; 
			
		case "mercenary": 
			System.out.println("Mercenary Hired: "+ item.getName());
			break; 
			
		}
	}*/
}
//work in progress