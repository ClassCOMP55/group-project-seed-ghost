import java.util.ArrayList;

public class Enemy extends Entity {
	private double[] damageMultipliers, incStatusMultipliers;
	private WeaponItem atk;
	private int[] skillPattern;
	private ArrayList<Skill> skills;
	private int turn;
	private Character nextTarget,previousTarget;
	private String name;
	private double sprScale;
	
	private static final String[] HOLY_ENEMIES = new String[] {"holyghost","fairie","prunsel"};
	private static final String[] MAGE_ENEMIES = new String[] {"imagefriend","orb","chefbot","casper","magicsword","fairie"};
	private static final String[] FIRE_ENEMIES = new String[] {"imagefriend","chefbot","irongremlin","bladedevil","soosk","gunturtle"};
	private static final String[] ELEC_ENEMIES = new String[] {"imagefriend","slime","chefbot","soosk","gunturtle","zapball"};
	private static final String[] BOSS_ENEMIES = new String[] {"boss_seraphim","boss_drip","boss_mage"};
	
	private void setDefaultAttackPattern () {
		skillPattern = new int[] {0};
		skills = new ArrayList<Skill>();
		skills.add(new SKILL_BasicAttack());
	}
	
	private void setAttackPattern (ArrayList<Skill> skillArr, int[] pattern) {
		skillPattern = pattern;
		skills = skillArr;
	}
	
	/*
	 * Creates a dummy enemy
	 */
	public Enemy () {
		super(100, 100, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		setDamageMultipliers(new double[] {1,1,1,1,1,1,1,1});
		setIncStatusMultipliers(new double[] {1,1,1,1,1,1,1,1,1});
		setAtk(null);
		turn = 0;
		setDefaultAttackPattern();
		name = "dummy";
		sprScale = 1;
	}
	
	public Enemy(double hpMax, double manaMax, double deathResist, int str, int dex, int prc, int ist, int con, int wil,
			int fth, int arc, double[] baseAttack, double[] statScaling, double[] baseStatus, String affinity, boolean ranged, 
			boolean magic, double[] incStatusMultipliers, String name) {
		super(hpMax, manaMax, deathResist, str, dex, prc, ist, con, wil, fth, arc);
		
		setDamageMultipliers(new double[] {1,1,1,1,1,1,1,1});
		setIncStatusMultipliers(incStatusMultipliers);
		
		setAtk(new WeaponItem (baseAttack, statScaling, ranged, magic));
		turn = 0;
		setDefaultAttackPattern();
		this.name = name;
		sprScale = 1;
	}
	
	/*
	 * Makes a test enemy for combat page
	 */
	
	public Enemy(String sprite) {
		
		super(150, 150, 10, 10, 10, 10, 10, 10, 10, 10, 10);
		setDamageMultipliers(new double[] {1,1,1,1,1,1,1,1});
		setIncStatusMultipliers(new double[] {1,1,1,1,1,1,1,1,1});
		
		setAtk(new WeaponItem (true));
		super.setSprite(sprite);
		setDefaultAttackPattern();
		
		name = "test";
		sprScale = 1;
	}
	
	/*
	 * Specific Enemy constructor
	 */
	public Enemy (String id, int scaling) {
		int str = scaling, dex = scaling, prc = scaling, ist = scaling, 
			con = scaling, wil = scaling, fth = scaling, arc = scaling;
		double HP = 100.0, Mana = 100000.0, DR = 0.0;
		
		this.name = id;
		
		double[] weaponDamage = new double[] {0,0,0,0,0,0,0,0};
		double[] weaponScales = new double[] {0,0,0,0,0,0,0,0};
		double[] damageResist = new double[] {1,1,1,1,1,1,1,1};
		boolean ranged = false, magic = false;
		String spr = "spr_HolyGhost.png";
		
		int[] defSkillP = new int[] {0};
		ArrayList<Skill> defSkill = new ArrayList<Skill> ();
		
		sprScale = 1;
		
		switch (id.toLowerCase()) {
		case "prunsel":
			damageResist[DamageType.HOLY.ordinal()] = 0.0;
			damageResist[DamageType.PIERCE.ordinal()] = 2.0;
			damageResist[DamageType.SLASH.ordinal()] = 2.0;
			damageResist[DamageType.MAGIC.ordinal()] = 0.2;
			
			weaponDamage[DamageType.HOLY.ordinal()] = 10;
			weaponScales[EntityStats.FTH.ordinal()] = 3.0;
			fth = 10 + (scaling * 2);
			con = 30 + (int)(scaling * 1.5);
			spr = "spr_Prunsel.png";
			
			name = "PRUNSEL";
			
			defSkillP = new int[] {0,1,2,1};
			defSkill.add(new SKILL_EnemyBuffAllies());
			defSkill.add(new SKILL_BasicAttack());
			defSkill.add(new SKILL_HeavyAttack());
			
			HP = 143.0;
		break;	
		case "holyghost":
				damageResist[DamageType.HOLY.ordinal()] = 0.0;
				damageResist[DamageType.PIERCE.ordinal()] = 0.2;
				damageResist[DamageType.SLASH.ordinal()] = 0.2;
				damageResist[DamageType.CRUSH.ordinal()] = 0.2;
				damageResist[DamageType.BLAST.ordinal()] = 0.2;
				
				weaponDamage[DamageType.HOLY.ordinal()] = 10;
				weaponScales[EntityStats.FTH.ordinal()] = 3.0;
				fth = 10 + (scaling * 2);
				con = 30 + (int)(scaling * 1.5);
				spr = "spr_HolyGhost.png";
				
				name = "Holy Ghost";
				
				defSkillP = new int[] {0,1,2,1};
				defSkill.add(new SKILL_EnemyBuffAllies());
				defSkill.add(new SKILL_SelfSacrifice());
				defSkill.add(new SKILL_BasicAttack());
				
				HP = 143.0;
			break;
			case "bladedevil":
				damageResist[DamageType.HOLY.ordinal()] = 1.5;
				damageResist[DamageType.PIERCE.ordinal()] = 0.8;
				damageResist[DamageType.SLASH.ordinal()] = 0.65;
				damageResist[DamageType.CRUSH.ordinal()] = 0.75;
				damageResist[DamageType.BLAST.ordinal()] = 0.4;
				damageResist[DamageType.FIRE.ordinal()] = 0.5;
				
				weaponDamage[DamageType.SLASH.ordinal()] = 60;
				weaponDamage[DamageType.FIRE.ordinal()] = 20;
				weaponScales[EntityStats.STR.ordinal()] = 1.0;
				str = 10 + (scaling * 3);
				ist = 0;
				spr = "spr_BladeDevil.gif";
				
				name = "Blade Devil";
				
				sprScale = 2;
				
				HP = 350;
			break;
			case "magicsword":
				damageResist[DamageType.MAGIC.ordinal()] = 0.8;
				damageResist[DamageType.FIRE.ordinal()] = 0.0;
				damageResist[DamageType.ELEC.ordinal()] = 0.0;
				damageResist[DamageType.HOLY.ordinal()] = 0.0;
				
				weaponDamage[DamageType.SLASH.ordinal()] = 20;
				weaponDamage[DamageType.MAGIC.ordinal()] = 20;
				weaponScales[EntityStats.ARC.ordinal()] = 1.2;
				arc = 10 + (scaling * 2);
				spr = "spr_CombatNode_MAGIC.png";
				
				name = "Magic Sword";
				
				HP = 120;
			break;
			case "casper":
				damageResist[DamageType.HOLY.ordinal()] = 2.5;
				damageResist[DamageType.PIERCE.ordinal()] = 0.2;
				damageResist[DamageType.SLASH.ordinal()] = 0.2;
				damageResist[DamageType.CRUSH.ordinal()] = 0.2;
				damageResist[DamageType.BLAST.ordinal()] = 0.2;
				
				weaponDamage[DamageType.MAGIC.ordinal()] = 20;
				weaponScales[EntityStats.FTH.ordinal()] = 1.0;
				weaponScales[EntityStats.ARC.ordinal()] = 1.0;
				arc = 10 + (scaling * 2);
				spr = "spr_Spectre.png";
				
				HP = 60.0;
			break;
			case "slime":
				damageResist[DamageType.PIERCE.ordinal()] = 0.2;
				damageResist[DamageType.SLASH.ordinal()] = 2.0;
				damageResist[DamageType.CRUSH.ordinal()] = 0.2;
				damageResist[DamageType.BLAST.ordinal()] = 0.8;
				
				weaponDamage[DamageType.CRUSH.ordinal()] = 30;
				weaponScales[EntityStats.STR.ordinal()] = 0.95;
				str = 10 + (scaling * 2);
				spr = "spr_Slime.png";
				
				HP = 80.0;
			break;
			case "irongremlin":
				damageResist[DamageType.PIERCE.ordinal()] = 0.4;
				damageResist[DamageType.SLASH.ordinal()] = 0.4;
				damageResist[DamageType.CRUSH.ordinal()] = 1.2;
				damageResist[DamageType.BLAST.ordinal()] = 1.2;
				damageResist[DamageType.FIRE.ordinal()] = 0.2;
				damageResist[DamageType.ELEC.ordinal()] = 1.5;
				
				weaponDamage[DamageType.SLASH.ordinal()] = 20;
				weaponDamage[DamageType.CRUSH.ordinal()] = 20;
				weaponScales[EntityStats.STR.ordinal()] = 5.0;
				str = 0;
				ist = 0;
				spr = "spr_IronGremlin.gif";
				
				name = "Iron Gremlin";
				
				defSkillP = new int[] {0};
				defSkill.add(new SKILL_IronWave());
				
				HP = 500;
			break;
			case "orb":
				damageResist[DamageType.MAGIC.ordinal()] = -2.0;
				
				weaponDamage[DamageType.BLAST.ordinal()] = 40;
				weaponDamage[DamageType.MAGIC.ordinal()] = 40;
				weaponScales[EntityStats.ARC.ordinal()] = 1.0;
				arc = (int)(scaling * 2.5);
				ist = 20 * scaling;
				spr = "spr_EvilPulsatingOrb.gif";
				
				name = "EVIL PULSATING ORB OF DOOM AND SUFFERING";
				
				defSkillP = new int[] {0};
				defSkill.add(new SKILL_WeakenFoe());
				
				HP = 120;
			break;
			case "chefbot":
				damageResist[DamageType.SLASH.ordinal()] = 0.5;
				damageResist[DamageType.ELEC.ordinal()] = 2.5;
				damageResist[DamageType.FIRE.ordinal()] = 0.25;
				
				weaponDamage[DamageType.SLASH.ordinal()] = 50;
				weaponScales[EntityStats.DEX.ordinal()] = 1.0;
				dex = (int)(scaling * 2.5);
				wil = (int)(scaling * 2.5);
				arc = (int)(scaling * 4.0);
				ist = 1;
				spr = "spr_ChefBot.png";
				
				name = "ChefBot 9000";
				
				defSkillP = new int[] {0,2,1};
				defSkill.add(new SKILL_BasicAttack());
				defSkill.add(new SKILL_EnemyBuffAllies());
				defSkill.add(new SKILL_LightningBolt());
				
				HP = 200;
			break;
			case "imagefriend":
				damageResist[DamageType.SLASH.ordinal()] = 0.0;
				damageResist[DamageType.PIERCE.ordinal()] = 0.0;
				damageResist[DamageType.CRUSH.ordinal()] = 0.0;
				damageResist[DamageType.BLAST.ordinal()] = 0.0;
				damageResist[DamageType.HOLY.ordinal()] = 2.5;
				
				weaponDamage[DamageType.MAGIC.ordinal()] = 50;
				weaponScales[EntityStats.ARC.ordinal()] = 1.0;
				arc = (int)(scaling * 2.0);
				ist = 1;
				spr = "spr_imagefriend.gif";
				
				name = "???";
				
				HP = 125;
			break;
			case "soosk":
				damageResist[DamageType.PIERCE.ordinal()] = 2.5;
				damageResist[DamageType.SLASH.ordinal()] = 0.8;
				damageResist[DamageType.CRUSH.ordinal()] = 0.8;
				damageResist[DamageType.BLAST.ordinal()] = 10.0;
				damageResist[DamageType.FIRE.ordinal()] = 10.0;
				damageResist[DamageType.MAGIC.ordinal()] = 0.2;
				
				weaponDamage[DamageType.SLASH.ordinal()] = 40;
				weaponScales[EntityStats.STR.ordinal()] = 1.0;
				str = scaling * 4;
				spr = "spr_Soosk.png";
				
				HP = 1800.0;
			break;
			case "fairie":
				damageResist[DamageType.HOLY.ordinal()] = 1.2;
				
				weaponDamage[DamageType.MAGIC.ordinal()] = 20;
				weaponDamage[DamageType.FIRE.ordinal()] = 20;
				weaponScales[EntityStats.CON.ordinal()] = 0.2;
				weaponScales[EntityStats.ARC.ordinal()] = 0.2;
				con = 10 + (scaling * 2);
				arc = 10 + (scaling * 2);
				spr = "spr_EvilintheFairiesGremlinCreature.gif";
				
				defSkillP = new int[] {0,1,2,1};
				defSkill.add(new SKILL_Fireball());
				defSkill.add(new SKILL_EnemyBuffAllies());
				defSkill.add(new SKILL_BasicAttack());
				
				HP = 60.0;
			break;
			case "gunturtle":
				damageResist[DamageType.PIERCE.ordinal()] = 0.0;
				damageResist[DamageType.BLAST.ordinal()] = 0.2;
				
				weaponDamage[DamageType.PIERCE.ordinal()] = 200;
				spr = "spr_GangstaTurtle.png";
				
				defSkillP = new int[] {1,1,0};
				defSkill.add(new SKILL_BasicAttack());
				defSkill.add(new SKILL_BossBuffSelf());
				
				HP = 20.0;
			break;
			case "zapball":
				damageResist[DamageType.ELEC.ordinal()] = -2.0;
				damageResist[DamageType.FIRE.ordinal()] = 2.0;
				damageResist[DamageType.CRUSH.ordinal()] = 0.5;
				damageResist[DamageType.SLASH.ordinal()] = 0.5;
				damageResist[DamageType.PIERCE.ordinal()] = 0.5;
				
				weaponDamage[DamageType.ELEC.ordinal()] = 30;
				weaponScales[EntityStats.WIL.ordinal()] = 1.0;
				wil = 10 + (scaling * 2);
				spr = "spr_ball.gif";
				
				defSkillP = new int[] {1,1,1,0};
				defSkill.add(new SKILL_BasicAttack());
				defSkill.add(new SKILL_BossBuffSelf());
				
				HP = 70.0;
			break;
			case "boss_seraphim":
				damageResist[DamageType.HOLY.ordinal()] = 0.0;
				damageResist[DamageType.FIRE.ordinal()] = 1.8;
				
				weaponDamage[DamageType.HOLY.ordinal()] = 90;
				weaponScales[EntityStats.STR.ordinal()] = 1.0;
				weaponScales[EntityStats.FTH.ordinal()] = 1.0;
				str = (int)(scaling * 2);
				fth = (int)(scaling * 2);
				arc = (int)(scaling * 8);
				ist = 100;
				spr = "spr_BOSS_Seraphim.png";
				
				name = "The Seraphim Vassel";
				
				sprScale = 2;
				
				defSkillP = new int[] {0,1,1};
				defSkill.add(new SKILL_BasicAttack());
				defSkill.add(new SKILL_BossBuffSelf());
				
				HP = 1200;
			break;
			case "boss_mage":
				damageResist[DamageType.MAGIC.ordinal()] = 0.0;
				damageResist[DamageType.BLAST.ordinal()] = 2.5;
				damageResist[DamageType.HOLY.ordinal()] = 0.5;
				damageResist[DamageType.FIRE.ordinal()] = 0.5;
				damageResist[DamageType.ELEC.ordinal()] = 0.5;
				
				weaponDamage[DamageType.MAGIC.ordinal()] = 80;
				weaponDamage[DamageType.PIERCE.ordinal()] = 40;
				weaponScales[EntityStats.ARC.ordinal()] = 1.0;
				arc = (int)(scaling * 2.0);
				wil = scaling * 3;
				ist = 30;
				spr = "spr_BOSS_GreatMage.png";
				
				name = "Supreme Mage";
				
				sprScale = 2;
				
				defSkillP = new int[] {2,0,1,3};
				defSkill.add(new SKILL_BasicAttack());
				defSkill.add(new SKILL_BossBuffSelf());
				defSkill.add(new SKILL_LightningBolt());
				defSkill.add(new SKILL_Fireball());
				
				HP = 1200;
			break;
			case "boss_drip":
				damageResist[DamageType.MAGIC.ordinal()] = 2.0;
				damageResist[DamageType.BLAST.ordinal()] = 0.0;
				damageResist[DamageType.HOLY.ordinal()] = 0.0;
				damageResist[DamageType.FIRE.ordinal()] = 2.0;
				damageResist[DamageType.ELEC.ordinal()] = 0.0;
				damageResist[DamageType.SLASH.ordinal()] = 0.2;
				
				weaponDamage[DamageType.SLASH.ordinal()] = 0;
				weaponScales[EntityStats.DEX.ordinal()] = 1.0;
				dex = (int)(scaling * 2.0);
				wil = 30;
				arc = 50;
				ist = 60;
				spr = "spr_BOSS_Hornet.png";
				
				name = "Completely Original Boss";
				
				defSkillP = new int[] {1,0,1,0,1,2};
				defSkill.add(new SKILL_BasicAttack());
				defSkill.add(new SKILL_BossBuffSelf());
				defSkill.add(new SKILL_LightningBolt());
				
				sprScale = 2.5;
				
				HP = 1200;
			break;
		}
		
		if (defSkill.isEmpty()) {
			defSkill.add(new SKILL_BasicAttack());
		}
		
		HP *= 1 + (scaling / 10);
		
		WeaponItem KILL = new WeaponItem (weaponDamage, weaponScales, ranged, magic);
		super(HP, Mana, DR, str, dex, prc, ist, con, wil, fth, arc);
		setDamageMultipliers(damageResist);
		setAtk(KILL);
		setSprite(spr);
		turn = 0;
		
		setAttackPattern(defSkill, defSkillP);
	}
	
	public String getIntent () {
		return skills.get(skillPattern[turn]).getEnemyIntentMsg(this, nextTarget);
	}
	
	public double calculateDamage (double[] incDamage) {
		double rtn = 0.0;
		
		for (int i = 0; i < incDamage.length; i++) {
			rtn += (incDamage[i] * damageMultipliers[i]);
		}
		
		System.out.println(rtn);
		return rtn;
	}
	
	public double[] calculateStatus (double[] incStatuses) {
		for (int i = 0; i < incStatuses.length; i++) {
			incStatuses[i] *= incStatusMultipliers[i];
		}
		
		return incStatuses;
	}

	public double[] getDamageMultipliers() {
		return damageMultipliers;
	}

	public void setDamageMultipliers(double[] damageMultipliers) {
		this.damageMultipliers = damageMultipliers;
	}

	public WeaponItem getAtk() {
		return atk;
	}

	public void setAtk(WeaponItem atk) {
		this.atk = atk;
	}

	public double[] getIncStatusMultipliers() {
		return incStatusMultipliers;
	}

	public void setIncStatusMultipliers(double[] incStatusMultipliers2) {
		this.incStatusMultipliers = incStatusMultipliers2;
	}
	
	public double attackMe(double[] attackSpread) {
		double sum = calculateDamage(attackSpread);
		hurt(sum);
		return sum;
	}
	
	public double[] attackOther () {
		return atk.scaledDamage(getStatSpread());
	}

	public int[] getSkillPattern() {
		return skillPattern;
	}

	public void setSkillPattern(int[] skillPattern) {
		this.skillPattern = skillPattern;
	}

	public ArrayList<Skill> getSkills() {
		return skills;
	}

	public void setSkills(ArrayList<Skill> skills) {
		this.skills = skills;
	}
	
	public void addSkill (Skill e) {
		skills.add(e);
	}
	
	/*
	 * The method an enemy should call when they take their turn.
	 * 
	 * @param The combat being put through
	 * @return The target that was attacked
	 */
	public Character playTurn (CombatPane combat) {
		Character[] targets = combat.aliveAllies();
		
		nextTarget = targets[Chance.range(0, targets.length - 1)];
		
		Skill now = skills.get(skillPattern[turn]);
		now.activationEffect(this, nextTarget);
		
		turn += 1;
		if (turn > skillPattern.length - 1) {
			turn = 0;
		}
		previousTarget = nextTarget;
		
		return previousTarget;
	}
	
	public String toString () {
		return name.substring(0,1).toUpperCase() + name.substring(1);
	}

	public double getSprScale() {
		return sprScale;
	}

	public void setSprScale(double sprScale) {
		this.sprScale = sprScale;
	}

	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

	public Character getNextTarget() {
		return nextTarget;
	}

	public void setNextTarget(Character nextTarget) {
		this.nextTarget = nextTarget;
	}

	public Character getPreviousTarget() {
		return previousTarget;
	}

	public void setPreviousTarget(Character previousTarget) {
		this.previousTarget = previousTarget;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static String[] getHolyEnemies() {
		return HOLY_ENEMIES;
	}

	public static String[] getMageEnemies() {
		return MAGE_ENEMIES;
	}

	public static String[] getFireEnemies() {
		return FIRE_ENEMIES;
	}

	public static String[] getElecEnemies() {
		return ELEC_ENEMIES;
	}

	public static String[] getBossEnemies() {
		return BOSS_ENEMIES;
	}
}
