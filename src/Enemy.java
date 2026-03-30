import java.util.ArrayList;

public class Enemy extends Entity {
	private double[] damageMultipliers, incStatusMultipliers;
	private WeaponItem atk;
	private int[] skillPattern;
	private ArrayList<Skill> skills;
	private int turn;
	private Character nextTarget;
	
	/*
	 * Creates a dummy enemy
	 */
	public Enemy () {
		super(100, 100, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		setDamageMultipliers(new double[] {1,1,1,1,1,1,1,1});
		setIncStatusMultipliers(new double[] {1,1,1,1,1,1,1,1,1});
		setAtk(null);
		turn = 0;
	}
	
	public Enemy(double hpMax, double manaMax, double deathResist, int str, int dex, int prc, int ist, int con, int wil,
			int fth, int arc, double[] baseAttack, double[] statScaling, double[] baseStatus, String affinity, boolean ranged, 
			boolean magic, double[] incStatusMultipliers) {
		super(hpMax, manaMax, deathResist, str, dex, prc, ist, con, wil, fth, arc);
		
		setDamageMultipliers(new double[] {1,1,1,1,1,1,1,1});
		setIncStatusMultipliers(incStatusMultipliers);
		
		setAtk(new WeaponItem (baseAttack, statScaling, baseStatus, affinity, ranged, magic));
		turn = 0;
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
	}
	
	public double calculateDamage (double[] incDamage) {
		double rtn = 0.0;
		
		for (int i = 0; i < incDamage.length; i++) {
			rtn += (incDamage[i] * damageMultipliers[i]);
		}
		
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
	
	public void attackMe(double[] attackSpread) {
		double sum = calculateDamage(attackSpread);
		hurt(sum);
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
	 */
	public void playTurn (CombatPane combat) {
		Character[] targets = combat.getMyArrAllies();
		
		Skill now = skills.get(skillPattern[turn]);
		now.activationEffect(this, nextTarget);
		
		turn += 1;
		if (turn > skillPattern.length - 1) {
			turn = 0;
		}
		nextTarget = targets[Chance.range(0, targets.length - 1)];
	}
}
