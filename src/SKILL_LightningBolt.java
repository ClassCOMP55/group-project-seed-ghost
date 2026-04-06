
public class SKILL_LightningBolt extends Skill {
	public SKILL_LightningBolt () {
		super(50);
		setName("Lightning Bolt");
		setDescription("Attack an enemy with a bolt of LIGHTNING damage, scaling with your ARCANE and WILLPOWER. Costs 50 mana.");
		setvTarget("ENEMY");
	}
	
	public void activationEffect (Entity me, Entity target) {
		double[] dmg = new double[] {0,0,0,0,0,0,0,0};
		int scaling = (int)Math.round(me.getStatSpread()[EntityStats.ARC.ordinal()]*0.6 + me.getStatSpread()[EntityStats.WIL.ordinal()]*0.4);
		dmg[DamageType.ELEC.ordinal()] = 10 * Math.pow(1.06, scaling);
		System.out.println(dmg[DamageType.ELEC.ordinal()]);
		target.attackMe(dmg);
		
		me.drainMana(getManaCost());
	}
	
	public String getEnemyIntentMsg(Entity me, Entity target) {
		int scaling = (int)Math.round(me.getStatSpread()[EntityStats.ARC.ordinal()]*0.6 + me.getStatSpread()[EntityStats.WIL.ordinal()]*0.4);
		return me + " intends to attack " + target + " for " + 10 * Math.pow(1.06, scaling) + " damage!";
	}
}
