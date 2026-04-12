
public class ShopItem {
	private Object item;
	private int price;
	
	public ShopItem(Object item, int price) {
		this.item = item;
		this.price = price; 
		
	}
	
	public Object getItem() {
		return item; 
	}
	
	public int getPrice() {
		return price; 
	}
	  
	public String getDisplayName() {
		if(item instanceof WeaponItem) {
			WeaponItem w =(WeaponItem) item;
			if (w.getAffinity().equals("null")) {
                return capitalize(w.getType());
            } else {
                return capitalize(w.getType()) + " of " + capitalize(w.getAffinity());
            }
		}
		if (item instanceof ArmorItem) {
			return item.toString();
	    }
		
		 if (item instanceof ConsumableItem) {
		        ConsumableItem c = (ConsumableItem) item;
		        switch(c.getType()) {
		            case HEALTH: return "Potion";       
		            case MANA:   return "Mana Potion"; 
		            case ELIXIR: return "Elixir";   
		            case VITALITY: return "Vitality";
		        }
		    }

	    if (item instanceof Character) {
	    	Character c = (Character) item;
	    	return "Mercenary: " + capitalize(c.getProfession());
	    }
	    return item.toString();
	    }
	 private String capitalize(String str) {
		 return str.substring(0,1).toUpperCase() + str.substring(1);
	 }


}
