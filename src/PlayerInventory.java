import java.util.ArrayList;

public class PlayerInventory {
	private static final int STARTING_GOLD = 99;
	private static final int MAX_PARTY_MEMBERS = 3;
	private static final int MAX_CONSUMABLES = 3;
	private Character[] partyMembers;
	private ConsumableItem[] consumables;
	private ArrayList<ArmorItem> extraArmors;
	private ArrayList<WeaponItem> extraWeapons;
	//private ArrayList<Relic> relics;
	private int gold;
	
	public PlayerInventory () {
		partyMembers = new Character[MAX_PARTY_MEMBERS];
		extraArmors = new ArrayList<ArmorItem>();
		extraWeapons = new ArrayList<WeaponItem>();
		consumables = new ConsumableItem[MAX_CONSUMABLES];
		
		gold = STARTING_GOLD;
	}
	
	/*
	 * Initialize with a starting character
	 */
	public PlayerInventory (Character startingCharacter) {
		partyMembers = new Character[3];
		extraArmors = new ArrayList<ArmorItem>();
		extraWeapons = new ArrayList<WeaponItem>();
		consumables = new ConsumableItem[MAX_CONSUMABLES];
		gold = STARTING_GOLD;
		
		partyMembers[0] = startingCharacter;
	}
	
	//Armor item store inventory
	public void addArmor(ArmorItem armor) {
        extraArmors.add(armor);  
    }
	//Weapon item store Inventory
	public void addWeapon(WeaponItem weapon) {
	        extraWeapons.add(weapon); 
	}
	//Consumables store in inventory
	public void addConsumable(ConsumableItem consumable) {
        for (int i = 0; i < consumables.length; i++) {
            if (consumables[i] == null) { 
                consumables[i] = consumable;
                System.out.println("Added consumable: " + consumable.getType());
                return;
            }
        }
        System.out.println("No space for more consumables.");
    }
	
	
	
	public Character[] getPartyMembers() {
		return partyMembers;
	}
	public void setPartyMembers(Character[] partyMembers) {
		this.partyMembers = partyMembers;
	}
	public ArrayList<ArmorItem> getExtraArmors() {
		return extraArmors;
	}
	public void setExtraArmors(ArrayList<ArmorItem> extraArmors) {
		this.extraArmors = extraArmors;
	}
	public ArrayList<WeaponItem> getExtraWeapons() {
		return extraWeapons;
	}
	public void setExtraWeapons(ArrayList<WeaponItem> extraWeapons) {
		this.extraWeapons = extraWeapons;
	}
	public ConsumableItem[] getConsumables() {
	    return consumables;
	}

	
	public int getGold() {
		return gold;
	}
	public void setGold(int gold) {
		this.gold = gold;
	}
}
