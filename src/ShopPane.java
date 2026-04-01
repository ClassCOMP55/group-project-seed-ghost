import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import acm.graphics.GLabel;
import acm.graphics.GObject;

//The ShopPane
public class ShopPane extends GraphicsPane {
	
	private ShopInventory inventory;
	
	public ShopPane(MainApplication mainScreen) {
		this.mainScreen = mainScreen;
	}
	
	@Override
	public void showContent() {
		addText();
		inventory = new ShopInventory();
		displayItems();
		addClerk();
		addReturnButton();
	}

	@Override
	public void hideContent() {
		for(GObject item : contents) {
			mainScreen.remove(item);
		}
		contents.clear();
	}
	
	//SHOP NAME
	private void addText() {
		GLabel title = new GLabel("SHOP", 100, 70);
		title.setColor(Color.BLUE);
		title.setFont("DialogInput-PLAIN-80");
		title.setLocation((mainScreen.getWidth() - title.getWidth()) / 2, 70);
		
		contents.add(title);
		mainScreen.add(title);
		}
	//DISPLAYING ITEMS
	 private void displayItems() {
	        int y = 150;

	        for (int i = 0; i < inventory.getItems().size(); i++) {
	            ShopItem item = inventory.getItems().get(i);

	            GLabel label = new GLabel(
	                item.getDisplayName() + " - " + item.getPrice() + "g",
	                100, y
	            );

	            label.setFont("DialogInput-PLAIN-18");

	            int index = i;

	            label.addMouseListener(new MouseAdapter() {
	                public void mouseClicked(MouseEvent e) {
	                    buyItem(index);
	                }
	            });

	            contents.add(label);
	            mainScreen.add(label);

	            y += 40;
	        }
	    }
	 
	 //BUYING ITEMS SYSTEM
	 private void buyItem(int index) {
		 ShopItem item = inventory.getItems().get(index);
		 
		 System.out.println("Bought: " + item.getDisplayName() + 
				 " for " + item.getPrice() + " gold");
		 
		 //mainScreen.addToInventory(item.getItem())
		 //mainScreen.spendGold(item.getPrice());
		 }
	 
	 //The old man clerk
	 private void addClerk() {
		 GLabel clerk = new GLabel("Clerk: \"What you want! Buy what ya needs...\"", 100, 100);
	        clerk.setFont("DialogInput-ITALIC-20");
	        clerk.setColor(Color.DARK_GRAY);

	        contents.add(clerk);
	        mainScreen.add(clerk);
		 
	 }
	 //return button
	 private void addReturnButton() {
		 GLabel returnBtn = new GLabel("Leave Shop", 0, 0);
	        returnBtn.setFont("DialogInput-BOLD-20");
	        returnBtn.setColor(Color.RED);

	        returnBtn.setLocation(
	            (mainScreen.getWidth() - returnBtn.getWidth()) / 2,
	            mainScreen.getHeight() - 50
	        );
	        

	        returnBtn.addMouseListener(new MouseAdapter() {
	            public void mouseClicked(MouseEvent e) {
	                mainScreen.switchToMapPane();
	            }
	        });

	        contents.add(returnBtn);
	        mainScreen.add(returnBtn);
	        MapPane.currPosition.cleared();
	    }


}
//work in progress