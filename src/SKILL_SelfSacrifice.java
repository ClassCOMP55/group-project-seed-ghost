
public class SKILL_SelfSacrifice extends Skill {
	private static final int SELF_DAMAGE = 50;
	
	public SKILL_SelfSacrifice () {
		super(10);
		setName("Self Sacrifice");
		setDescription("Take " + SELF_DAMAGE + " damage to deal huge HOLY damage to a foe. Scales with FAITH and CONSTITUTION.");
		setvTarget("ENEMY");
	}
	
	public void activationEffect (Entity me, Entity target) {
		me.hurt(SELF_DAMAGE);
		
		double[] dmg = new double[] {0,0,0,0,0,0,0,0};
		int scaling = (int) (me.getStatSpread()[EntityStats.FTH.ordinal()]*0.5 + me.getStatSpread()[EntityStats.CON.ordinal()]*0.5);
		dmg[DamageType.HOLY.ordinal()] = 10 * Math.pow(1.05, scaling);
		target.attackMe(dmg);
		
		me.drainMana(getManaCost());
	}
	
	public boolean preconditionsMet (Entity self, Entity target) {
		return self.getMana() >= getManaCost() && self.getHp() > SELF_DAMAGE;
	}
}
