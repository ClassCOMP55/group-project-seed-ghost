
public class SKILL_SelfSacrifice extends Skill {
	private static final int SELF_DAMAGE = 30;
	
	public SKILL_SelfSacrifice () {
		super(10);
		setName("Self Sacrifice");
		setDescription("Take " + SELF_DAMAGE + " damage to deal huge HOLY damage to a foe. Scales with FAITH and CONSTITUTION.");
		setvTarget("ENEMY");
		setAnimationType("SelfSac");
	}
	
	public void activationEffect (Entity me, Entity target) {
		me.hurt(SELF_DAMAGE);
		
		double[] dmg = new double[] {0,0,0,0,0,0,0,0};
		int scaling = (int) (me.getStatSpread()[EntityStats.FTH.ordinal()]*0.5 + me.getStatSpread()[EntityStats.CON.ordinal()]*0.5);
		dmg[DamageType.HOLY.ordinal()] = 12 * Chance.softExponential(1.07, scaling, 2000, 4);
		target.attackMe(dmg);
		
		me.drainMana(getManaCost());
	}
	
	public boolean preconditionsMet (Entity self, Entity target) {
		return self.getMana() >= getManaCost() && self.getHp() > SELF_DAMAGE;
	}
	
	public String getEnemyIntentMsg(Entity me, Entity target) {
		int scaling = (int) (me.getStatSpread()[EntityStats.FTH.ordinal()]*0.5 + me.getStatSpread()[EntityStats.CON.ordinal()]*0.5);
		return "Intends to sacrifice " + SELF_DAMAGE + " HP and attack " + target + " for " + ((Character)target).calculateDamage(12 * Chance.softExponential(1.07, scaling, 2000, 4), DamageType.HOLY) + " damage!";
	}
}
