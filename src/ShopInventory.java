import java.util.ArrayList;
public class ShopInventory {
	private ArrayList<ShopItem> items;
	
	public ShopInventory() {
		items = new ArrayList<>();
		generateInventory(); 
	}
	
	private void generateInventory() {
		// The Weapons
		for (int i = 0; i < 3; i++) {
			items.add(new ShopItem(new WeaponItem(true),Chance.range(80, 200)));
	    }
		
		// The Armors
		for (int i = 0; i < 3; i++) {
			items.add(new ShopItem(new ArmorItem(),Chance.range(60, 180)));
	    }
		
		 // Potions (placeholder)
		 for (ConsumableType type : ConsumableType.values()) {
		        int price;
		        switch (type) {
		            case HEALTH:
		                price = 50; // Health potion price
		                break;
		            case MANA:
		                price = 75; // Mana potion price
		                break;
		            case ELIXIR:
		                price =250; // Elixir price
		                break;
		            case VITALITY:
		            	price = 250; //Vitality price
		            	break; 
		            default:
		                price = 0;
		        }

		        items.add(new ShopItem(new ConsumableItem(type), price));
		    }


        // Mercenaries method
        items.add(new ShopItem(generateMercenary(), Chance.range(200, 400)));

	}

    private Character generateMercenary() {
    	String[] professions = {
    			"knight","samurai","thief","viking",
    			"cleric","sorcerer","paladin","ranger","marksman"
    			};
    	return new Character(Chance.choose(professions));
    	
    }
    
    public ArrayList<ShopItem> getItems() {
    	return items;
    }


}
