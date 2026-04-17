public class Character extends Entity {
	public static final int MAX_SKILLS = 4;
	public static final double MAX_HEALTH_UNIVERSAL_BOOSTER = 1.5;
	
	private WeaponItem myWeapon;
	private ArmorItem myArmor;
	private Skill[] mySkills;
	private String profession;
	private String[] allowedArmors;
	private Skill lastUsedSkill;
	private int recruitCost;
	private String name;
	
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
		
		generateName();
		
		setRecruitCost(0);
		
		myWeapon = null;
		myArmor = null;
		setMySkills(new Skill[]{new SKILL_BasicAttack(), new SKILL_Guard(), new SKILL_LightningBolt(), new SKILL_PrayerOfHealing()});
		profession = "none";
		allowedArmors = new String[] {"light"};
		
		setSprite("spr_Knight.png");
	}
	
	/*
	 * Robust; Full control over character creation
	 */
	public Character (double hp, double mana, double deathResist, int str, int dex, int prc, int ist, int con, int wil, int fth, int arc, WeaponItem weapon, ArmorItem armor, String profession) {
		super(1.0,1.0,0.0,0,0,0,0,0,0,0,0);
		
		generateName();
		
		this.myWeapon = weapon;
		this.myArmor = armor;
		this.profession = profession;
		
		setMySkills(new Skill[]{new SKILL_BasicAttack(), new SKILL_Guard(), new SKILL_LightningBolt(), new SKILL_PrayerOfHealing()});
		applyProfession(profession, new String [] {"null"});
		
		setHpMax(hp);
		setHp(hp);
		setManaMax(mana);
		setMana(mana);
		setDeathResist(deathResist);
		
		int[] stats = new int[] {str, dex, prc, ist, con, wil, fth, arc};
		setStatSpread(stats);
		
		setRecruitCost((int)((getHpMax() + getManaMax() + myWeapon.getPurchaseCost() + myArmor.getPurchaseCost()) * (1 + getDeathResist())));
	}

	/*
	 * Profession; Creates a random character based on profession
	 */
	public Character (String profession) {
		super(1.0,1.0,0.0,0,0,0,0,0,0,0,0);
		
		generateName();
		
		setMySkills(new Skill[]{new SKILL_BasicAttack(), new SKILL_Guard(), new SKILL_LightningBolt(), new SKILL_PrayerOfHealing()});
		applyProfession(profession, new String [] {"null"});
		
		setRecruitCost((int)((getHpMax() + getManaMax() + myWeapon.getPurchaseCost() + myArmor.getPurchaseCost()) * (1 + getDeathResist())));
	}
	
	/*
	 * Profession; Creates a random character based on profession and allowing affinities in weaponry
	 */
	public Character (String profession, Boolean affinitiesAllowed) {
		super(1.0,1.0,0.0,0,0,0,0,0,0,0,0);
		
		generateName();
		
		setMySkills(new Skill[]{new SKILL_BasicAttack(), new SKILL_Guard(), new SKILL_LightningBolt(), new SKILL_PrayerOfHealing()});
		applyProfession(profession, new String[] {"null","null","holy","fire","elec","magic"});
		
		setRecruitCost((int)((getHpMax() + getManaMax() + myWeapon.getPurchaseCost() + myArmor.getPurchaseCost()) * (1 + getDeathResist())));
	}
	
	private void applyProfession (String profession, String[] allowedAffinities) {
		this.profession = profession.toLowerCase();
		String tempSprite = "spr_Knight.png";
		String tempTooltip = "Player Character";
		
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
				tempHp = Chance.range(200, 400);
				tempMn = Chance.range(100, 200);
				tempDr = 0.25;
				
				tempStr = (int)Chance.range(40, 90);
				tempDex = (int)Chance.range(20, 50);
				tempCon = (int)Chance.range(20, 40);
				tempSprite = "spr_Knight.png";
				allowedArmors = new String[] {"light", "medium","heavy"};
				
				mySkills = new Skill[] {new SKILL_BasicAttack(), new SKILL_Guard(), new SKILL_IronWave(), new SKILL_Taunt()};
				
				myArmor = new ArmorItem(Chance.choose(allowedArmors), "null");
				myWeapon = new WeaponItem(Chance.choose(new String[] {"greatsword","shortsword","polearm"}), Chance.choose(allowedAffinities));
			break;
			case "samurai":
				tempHp = Chance.range(150, 400);
				tempMn = Chance.range(100, 400);
				tempDr = 0.3;
				
				tempDex = (int)Chance.range(30, 60);
				tempPrc = (int)Chance.range(30, 60);
				tempIst = (int)Chance.range(10, 50);
				tempWil = (int)Chance.range(20, 40);
				tempSprite = "spr_Samurai.png";
				allowedArmors = new String[] {"light", "medium"};
				
				mySkills = new Skill[] {new SKILL_BasicAttack(), new SKILL_Guard(), new SKILL_Determination(), new SKILL_Recovery()};
				
				myArmor = new ArmorItem(Chance.choose(allowedArmors), "null");
				myWeapon = new WeaponItem(Chance.choose(new String[] {"katana","dagger","rifle"}), Chance.choose(allowedAffinities));
			break;
			case "thief":
				tempHp = Chance.range(100, 250);
				tempMn = Chance.range(100, 200);
				tempDr = 0.1;
				
				tempDex = (int)Chance.range(30, 90);
				tempPrc = (int)Chance.range(30, 90);
				tempFth = 0;
				tempSprite = "spr_Thief.png";
				allowedArmors = new String[] {"light"};
				myWeapon = new WeaponItem(Chance.choose(new String[] {"dagger","pistol","crossbow"}), Chance.choose(allowedAffinities));
				myArmor = new ArmorItem(Chance.choose(allowedArmors), "null");
				
				mySkills = new Skill[] {new SKILL_ProneShot(), new SKILL_Recovery(), new SKILL_Assassinate(), new SKILL_SleightOfGreed()};
			break;
			case "viking":
				tempHp = Chance.range(300, 400);
				tempMn = Chance.range(100, 200);
				tempDr = 0.5;
				
				tempStr = (int)Chance.range(60, 90);
				tempCon = (int)Chance.range(40, 60);
				allowedArmors = new String[] {"light", "medium","heavy"};
				
				mySkills = new Skill[] {new SKILL_HeavyAttack(), new SKILL_IronWave(), new SKILL_Drain(), new SKILL_Recovery()};
				
				myArmor = new ArmorItem(Chance.choose(allowedArmors), "null");
				myWeapon = new WeaponItem(Chance.choose(new String[] {"axe","greatsword","hammer"}), Chance.choose(allowedAffinities));
				tempSprite = "spr_Viking.png";
			break;
			case "cleric":
				tempHp = Chance.range(170, 250);
				tempMn = Chance.range(150, 300);
				tempDr = 0.15;
				
				tempWil = (int)Chance.range(40, 50);
				tempFth = (int)Chance.range(60, 120);
				tempArc = (int)Chance.range(10, 50);
				tempStr = (int)Chance.range(30, 50);
				allowedArmors = new String[] {"light", "medium"};
				
				mySkills = new Skill[] {new SKILL_BasicAttack(), new SKILL_HolyShield(), new SKILL_Recovery(), new SKILL_PrayerOfHealing()};
				
				myArmor = new ArmorItem(Chance.choose(allowedArmors), "null");
				myWeapon = new WeaponItem(Chance.choose(new String[] {"talisman","stave","hammer"}), Chance.choose(allowedAffinities));
				tempSprite = "spr_Cleric.png";
			break;
			case "sorcerer":
				tempHp = Chance.range(70, 120);
				tempMn = Chance.range(150, 300);
				tempDr = 0.1;
				
				tempWil = (int)Chance.range(40, 50);
				tempFth = (int)Chance.range(40, 90);
				tempArc = (int)Chance.range(60, 100);
				tempSprite = "spr_Sorcerer.png";
				allowedArmors = new String[] {"light"};
				
				mySkills = new Skill[] {new SKILL_BasicAttack(), new SKILL_HolyShield(), new SKILL_LightningBolt(), new SKILL_Fireball()};
				
				myArmor = new ArmorItem(Chance.choose(allowedArmors), "null");
				myWeapon = new WeaponItem(Chance.choose(new String[] {"stave","talisman"}), "magic");
			break;
			case "paladin":
				tempHp = Chance.range(200, 400);
				tempMn = Chance.range(150, 250);
				tempDr = 0.3;
				
				tempStr = (int)Chance.range(40, 60);
				tempCon = (int)Chance.range(10, 30);
				tempFth = (int)Chance.range(50, 80);
				
				tempSprite = "spr_Paladin.png";
				
				allowedArmors = new String[] {"light", "medium","heavy"};
				
				mySkills = new Skill[] {new SKILL_HeavyAttack(), new SKILL_HolyShield(), new SKILL_SelfSacrifice(), new SKILL_IronWave()};
				
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
				
				tempSprite = "spr_RangerUpdated.png";
				
				mySkills = new Skill[] {new SKILL_BasicAttack(), new SKILL_Guard(), new SKILL_Recovery(), new SKILL_WeakenFoe()};
				
				myArmor = new ArmorItem(Chance.choose(allowedArmors), "null");
				myWeapon = new WeaponItem(Chance.choose(new String[] {"longbow","shortbow","crossbow","bomb"}), Chance.choose(allowedAffinities));
			break;
			case "marksman":
				tempHp = Chance.range(150, 250);
				tempMn = Chance.range(400, 1000);
				tempDr = 0.225;
				
				tempPrc = (int)Chance.range(50, 120);
				tempDex = (int)Chance.range(30, 60);
				tempIst = (int)Chance.range(100, 120);
				tempSprite = "spr_Marksman.png";
				allowedArmors = new String[] {"light"};
				myArmor = new ArmorItem(Chance.choose(allowedArmors), "null");
				myWeapon = new WeaponItem(Chance.choose(new String[] {"pistol","rifle","crossbow"}), Chance.choose(allowedAffinities));
				
				mySkills = new Skill[] {new SKILL_ProneShot(), new SKILL_ShatteringShot(), new SKILL_CriticalShot(), new SKILL_Determination()};
			break;
		}
		
		tempHp *= MAX_HEALTH_UNIVERSAL_BOOSTER;
		
		setHpMax(tempHp);
		setHp(tempHp);
		
		setManaMax(tempMn);
		setMana(tempMn);
		
		setDeathResist(tempDr);
		
		int[] stats = new int[] {tempStr, tempDex, tempPrc, tempIst, tempCon, tempWil, tempFth, tempArc};
		setStatSpread(stats);
		setSprite(tempSprite);
		
		tempTooltip = toString() + "\n" + myWeapon + ": " + myWeapon.scaledDamageFlat(stats) + "\n" + myArmor + ": " + myArmor.printStats();
		
		generateTooltip(tempTooltip);
	}
	
	private void generateName () {
		name = Chance.choose(new String[] {"Jake","Austin","Loretta","Gloria","Among","Mina","Celeste",
										   "Charlie","Negar","Isaac","Lance","Yeshua","Steve","Bob",
									       "Jack","Omori","Sunny","Aubrey","Hero","Nakoa","Seraphina",
									       "Gargamel","Gimley","Gandalf","Sauruman","Saul","Mari","Marianne",
									       "Lucille","Sara","Nora","Chione","Aisha","Alexis","Alex","Christine","Kris",
									       "Susie","Ralsei","W.D.","Yuki","Makima","Jolyne","Trish","Diavolo","Veneto",
									       "Jotaro","Joseph","Josuke","Jorge","Johnny","Jonathan"}) 
				+ " " + 
			   Chance.choose(new String[] {"Una","Gaster","Beckett","Smith","White","Hamilton","Burr","Prosecco",
					   					   "Pane'e","Luther","Swinson","Franks","Ochoa","Vang","Green","Suzuki","Sora",
					   					   "Brando","Reyes","Reed","Malicious","Vasquez","Beauforte","Goodman","Hungary",
					   					   "Chance","Black","Grey","Yellow","Blue","Winston","Kvitko","Us","Cujoh","Parton",
					   					   "Gift","Destiny","Waterfall","Rivers","Katana","Bloodsteel","Windsworn","Zaptastic",
					   					   "Fantastic","Chungus","Corporeal","Swiss","Vengeance","Blessed","Aeshma","Wongo",
					   					   "Joestar"});
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
	/* 
	 * Finds the main stat of the character
	 * 
	 * @return The index of the character's highest stat.
	 */
	public int getPrimaryStatIndex() {
	    return getMainStat().ordinal();
	}  
	//Added increase Stat helper function
	public void increaseStat(int index, int value) {
	    int[] stats = getStatSpread(); 
	    if (index >= 0 && index < stats.length) {
	        stats[index] += value;
	        setStatSpread(stats);    
	    }
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
	
	public double attackMe (double[] attackSpread) {
		double sum = myArmor.calculateDamage(attackSpread);
		hurt(sum);
		return sum;
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

	public int getRecruitCost() {
		return recruitCost;
	}

	public void setRecruitCost(int recruitCost) {
		this.recruitCost = recruitCost;
	}
	
	public String toString () {
		return name + " the " + profession.substring(0, 1).toUpperCase() + profession.substring(1);
	}
	
	public boolean guarding () {
		return myArmor.guarding();
	}
	
	public int calculateDamage (Enemy op) {
		return (int)getArmor().calculateDamage(op.getAtk().scaledDamage(op.getStatSpread()));
	}
	
	public int calculateDamage (double damage, DamageType dType) {
		double [] arr = new double[] {0,0,0,0,0,0,0,0};
		arr[dType.ordinal()] = damage;
		
		return (int)getArmor().calculateDamage(arr);
	}
}
