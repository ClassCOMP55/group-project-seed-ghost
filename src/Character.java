public class Character extends Entity {
	public static final int MAX_SKILLS = 4;
	
	private WeaponItem myWeapon;
	private ArmorItem myArmor;
	private Skill[] mySkills;
	private String profession;
	private String[] allowedArmors;
	private Skill lastUsedSkill;
	
	/*
	 * Constructors
	 */
	
	/*
	 * Default; null character
	 * 
	 * It's completely basic
	 */
	public Character () {
		super(1.0,1.0,0.0,0,0,0,0,0,0,0,0);
		
		myWeapon = null;
		myArmor = null;
		setMySkills(new Skill[]{new SKILL_BasicAttack()});
		profession = "none";
		allowedArmors = new String[] {"light"};
		
		setSprite("spr_Knight");
	}
	
	/*
	 * Robust; Full control over character creation
	 */
	public Character (double hp, double mana, double deathResist, int str, int dex, int prc, int ist, int con, int wil, int fth, int arc, WeaponItem weapon, ArmorItem armor, String profession) {
		super(1.0,1.0,0.0,0,0,0,0,0,0,0,0);
		
		this.myWeapon = weapon;
		this.myArmor = armor;
		this.profession = profession;
		
		applyProfession(profession);
		
		setHpMax(hp);
		setHp(hp);
		setManaMax(mana);
		setMana(mana);
		setDeathResist(deathResist);
		
		int[] stats = new int[] {str, dex, prc, ist, con, wil, fth, arc};
		setStatSpread(stats);
	}

	/*
	 * Profession; Creates a random character based on profession
	 */
	public Character (String profession) {
		super(1.0,1.0,0.0,0,0,0,0,0,0,0,0);
		
		applyProfession(profession);
	}
	
	private void applyProfession (String profession) {
		this.profession = profession.toLowerCase();
		String tempSprite = "spr_Knight";
		
		double tempHp = 1.0;
		double tempMn = 1.0;
		double tempDr = 0.0;
		
		int tempStr = (int)Chance.range(0, 15);
		int tempDex = (int)Chance.range(0, 15);
		int tempPrc = (int)Chance.range(0, 15);
		int tempIst = (int)Chance.range(0, 15);
		int tempCon = (int)Chance.range(0, 15);
		int tempWil = (int)Chance.range(0, 15);
		int tempFth = (int)Chance.range(0, 15);
		int tempArc = (int)Chance.range(0, 15);
		
		switch (profession) {
			case "knight": 
				tempHp = Chance.range(200, 300);
				tempMn = Chance.range(10, 100);
				tempDr = 0.25;
				
				tempStr = (int)Chance.range(40, 90);
				tempDex = (int)Chance.range(20, 50);
				tempCon = (int)Chance.range(20, 40);
				tempSprite = "spr_Knight";
				allowedArmors = new String[] {"light", "medium","heavy"};
				
				myArmor = new ArmorItem(Chance.choose(allowedArmors), "null");
				myWeapon = new WeaponItem(Chance.choose(new String[] {"greatsword","shortsword","polearm"}), "null");
			break;
			case "samurai":
				tempHp = Chance.range(150, 300);
				tempMn = Chance.range(40, 100);
				tempDr = 0.3;
				
				tempDex = (int)Chance.range(30, 60);
				tempPrc = (int)Chance.range(30, 60);
				tempIst = (int)Chance.range(10, 50);
				tempWil = (int)Chance.range(20, 40);
				tempSprite = "spr_Samurai";
				allowedArmors = new String[] {"light", "medium"};
				
				myArmor = new ArmorItem(Chance.choose(allowedArmors), "null");
				myWeapon = new WeaponItem(Chance.choose(new String[] {"katana","dagger","rifle"}), "null");
			break;
			case "thief":
				tempHp = Chance.range(100, 250);
				tempMn = Chance.range(50, 90);
				tempDr = 0.1;
				
				tempDex = (int)Chance.range(30, 90);
				tempPrc = (int)Chance.range(30, 90);
				tempFth = 0;
				tempSprite = "spr_Thief";
				allowedArmors = new String[] {"light"};
				myWeapon = new WeaponItem(Chance.choose(new String[] {"dagger","pistol","crossbow"}), "null");
				
				myArmor = new ArmorItem(Chance.choose(allowedArmors), "null");
			break;
			case "viking":
				tempHp = Chance.range(200, 300);
				tempMn = Chance.range(10, 100);
				tempDr = 0.5;
				
				tempStr = (int)Chance.range(60, 90);
				tempCon = (int)Chance.range(40, 60);
				allowedArmors = new String[] {"light", "medium","heavy"};
				
				myArmor = new ArmorItem(Chance.choose(allowedArmors), "null");
				myWeapon = new WeaponItem(Chance.choose(new String[] {"axe","greatsword","hammer"}), "null");
			break;
			case "cleric":
				tempHp = Chance.range(70, 150);
				tempMn = Chance.range(100, 300);
				tempDr = 0.15;
				
				tempWil = (int)Chance.range(40, 50);
				tempFth = (int)Chance.range(60, 100);
				tempArc = (int)Chance.range(10, 20);
				allowedArmors = new String[] {"light", "medium"};
				
				myArmor = new ArmorItem(Chance.choose(allowedArmors), "null");
				myWeapon = new WeaponItem(Chance.choose(new String[] {"talisman","stave","hammer"}), "holy");
			break;
			case "sorcerer":
				tempHp = Chance.range(40, 100);
				tempMn = Chance.range(100, 300);
				tempDr = 0.1;
				
				tempWil = (int)Chance.range(40, 50);
				tempFth = (int)Chance.range(10, 20);
				tempArc = (int)Chance.range(60, 100);
				tempSprite = "spr_Sorcerer";
				allowedArmors = new String[] {"light"};
				
				myArmor = new ArmorItem(Chance.choose(allowedArmors), "null");
				myWeapon = new WeaponItem(Chance.choose(new String[] {"stave","talisman"}), "magic");
			break;
			case "paladin":
				tempHp = Chance.range(200, 400);
				tempMn = Chance.range(50, 300);
				tempDr = 0.3;
				
				tempStr = (int)Chance.range(40, 60);
				tempCon = (int)Chance.range(10, 30);
				tempFth = (int)Chance.range(50, 80);
				
				tempSprite = "spr_Paladin";
				
				allowedArmors = new String[] {"light", "medium","heavy"};
				
				myArmor = new ArmorItem(Chance.choose(allowedArmors), "null");
				myWeapon = new WeaponItem(Chance.choose(new String[] {"talisman","greatsword","hammer"}), "holy");
			break;
			case "ranger":
				tempHp = Chance.range(75, 150);
				tempMn = Chance.range(75, 150);
				tempDr = 0.2;
				
				tempPrc = (int)Chance.range(40, 70);
				tempDex = (int)Chance.range(40, 70);
				allowedArmors = new String[] {"light", "medium"};
				
				myArmor = new ArmorItem(Chance.choose(allowedArmors), "null");
				myWeapon = new WeaponItem(Chance.choose(new String[] {"longbow","shortbow","crossbow","bomb"}), "null");
			break;
			case "marksman":
				tempHp = Chance.range(100, 200);
				tempMn = Chance.range(5, 10);
				tempDr = 0.225;
				
				tempPrc = (int)Chance.range(60, 160);
				tempSprite = "spr_Marksman";
				allowedArmors = new String[] {"light"};
				myArmor = new ArmorItem(Chance.choose(allowedArmors), "null");
				myWeapon = new WeaponItem(Chance.choose(new String[] {"pistol","rifle","crossbow"}), "null");
			break;
		}
		
		setHpMax(tempHp);
		setHp(tempHp);
		
		setManaMax(tempMn);
		setMana(tempMn);
		
		setDeathResist(tempDr);
		
		int[] stats = new int[] {tempStr, tempDex, tempPrc, tempIst, tempCon, tempWil, tempFth, tempArc};
		setStatSpread(stats);
		setSprite(tempSprite);
	}
	
	/*
	 * Setters and getters
	 */
	public WeaponItem getWeapon() {
		return myWeapon;
	}

	public void setWeapon(WeaponItem myWeapon) {
		this.myWeapon = myWeapon;
	}

	public ArmorItem getArmor() {
		return myArmor;
	}
	
	/*
	 * Sets a character's armor
	 * 
	 * @param ArmorItem; the armor to be added
	 * @return Boolean; If the character can't wear this armor, return false
	 */
	public boolean setArmor(ArmorItem myArmor) {
		String weight = myArmor.getWeight();
		for (int i = 0; i < allowedArmors.length; i++) {
			if (weight == allowedArmors[i]) {
				this.myArmor = myArmor;
				return true;
			}
		}
		return false;
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

	public Skill[] getMySkills() {
		return mySkills;
	}

	public void setMySkills(Skill[] mySkills) {
		this.mySkills = mySkills;
	}
	
	public void attackMe (double[] attackSpread) {
		double sum = myArmor.calculateDamage(attackSpread);
		hurt(sum);
	}
	
	public double[] attackOther () {
		return myWeapon.scaledDamage(getStatSpread());
	}

	public Skill getLastUsedSkill() {
		return lastUsedSkill;
	}

	public void setLastUsedSkill(Skill lastUsedSkill) {
		this.lastUsedSkill = lastUsedSkill;
	}
	
	public void startTurn () {
		lastUsedSkill.nextTurnEffect(this, null);
	}
}
