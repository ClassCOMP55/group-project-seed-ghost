
public class SKILL_LightningBolt extends Skill {
	public SKILL_LightningBolt () {
		super(100);
		setName("Lightning Bolt");
		setDescription("Attack an enemy with a bolt of LIGHTNING damage, scaling with your ARCANE and WILLPOWER. Costs 100 mana.");
	}
	
	public void activationEffect (Entity me, Entity target) {
		double[] dmg = new double[] {0,0,0,0,0,0,0,0};
		int scaling = (int) (me.getStatSpread()[EntityStats.ARC.ordinal()]*0.7 + me.getStatSpread()[EntityStats.WIL.ordinal()]*0.3);
		dmg[DamageType.ELEC.ordinal()] = 10 * Math.pow(1.034, scaling);
		target.attackMe(dmg);
	}
}
