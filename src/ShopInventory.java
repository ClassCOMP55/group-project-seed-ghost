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
			items.add(new ShopItem(new WeaponItem(true), Chance.range(80, 200)));
	    }
		
		// The Armors
		for (int i = 0; i < 3; i++) {
			items.add(new ShopItem(new ArmorItem(), Chance.range(60, 180)));
	    }
		
		// Potions
		for (ConsumableType type : ConsumableType.values()) {
	        int price;
	        switch (type) {
	            case HEALTH:
	                price = 50;
	                break;
	            case MANA:
	                price = 75;
	                break;
	            case ELIXIR:
	                price = 250;
	                break;
	            case VITALITY:
	            	price = 250;
	            	break; 
	            default:
	                price = 0;
	        }

	        items.add(new ShopItem(new ConsumableItem(type), price));
	    }

        // Mercenary
		 items.add(generateMercenaryItem());
	}

	private ShopItem generateMercenaryItem() {

        String[] professions = {
            "knight","samurai","thief","viking",
            "cleric","sorcerer","paladin","ranger","marksman"
        };

        return new ShopItem(
            new Character(Chance.choose(professions), true),
            Chance.range(200, 400)
        );
    }

    
    public ArrayList<ShopItem> getItems() {
    	return items;
    }
}