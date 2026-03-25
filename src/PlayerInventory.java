import java.util.ArrayList;

public class PlayerInventory {
	private static final int STARTING_GOLD = 99;
	private Character[] partyMembers;
	private ArrayList<ArmorItem> extraArmors;
	private ArrayList<WeaponItem> extraWeapons;
	//private Consumable[] consumables;
	//private ArrayList<Relic> relics;
	private int gold;
	
	public PlayerInventory () {
		partyMembers = new Character[3];
		extraArmors = new ArrayList<ArmorItem>();
		extraWeapons = new ArrayList<WeaponItem>();
		gold = STARTING_GOLD;
	}
	
	/*
	 * Initialize with a starting character
	 */
	public PlayerInventory (Character startingCharacter) {
		partyMembers = new Character[3];
		extraArmors = new ArrayList<ArmorItem>();
		extraWeapons = new ArrayList<WeaponItem>();
		gold = STARTING_GOLD;
		
		partyMembers[0] = startingCharacter;
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
	public int getGold() {
		return gold;
	}
	public void setGold(int gold) {
		this.gold = gold;
	}
}
