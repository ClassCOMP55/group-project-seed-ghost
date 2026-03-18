
public class Character extends Entity {
	public static final int MAX_SKILLS = 4;
	
	private WeaponItem myWeapon;
	private ArmorItem myArmor;
	//private Skill[] mySkills;
	private String profession;
	
	/*
	 * Constructors
	 */
	
	/*
	 * Default; null character
	 */
	public Character () {
		super(1.0,1.0,0.0,0,0,0,0,0,0,0,0);
		
		myWeapon = null;
		myArmor = null;
		//mySkills = new Skill[]({null, null, null, null});
		profession = "none";
	}
	
	/*
	 * Robust; Full control over character creation
	 */
	public Character (double hp, double mana, double deathResist, int str, int dex, int prc, int ist, int con, int wil, int fth, int arc, WeaponItem weapon, ArmorItem armor, String profession) {
		super(hp,mana,deathResist,str,dex,prc,ist,con,wil,fth,arc);
		
		this.myWeapon = weapon;
		this.myArmor = armor;
		this.profession = profession;
	}

	/*
	 * Profession; Creates a random character based on profession
	 */
	public Character (String profession) {
		super(1.0,1.0,0.0,0,0,0,0,0,0,0,0);
		
		//WIP
	}
	
	
	/*
	 * Setters and getters
	 */
	public WeaponItem getMyWeapon() {
		return myWeapon;
	}

	public void setMyWeapon(WeaponItem myWeapon) {
		this.myWeapon = myWeapon;
	}

	public ArmorItem getMyArmor() {
		return myArmor;
	}

	public void setMyArmor(ArmorItem myArmor) {
		this.myArmor = myArmor;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public static int getMaxSkills() {
		return MAX_SKILLS;
	}
}
