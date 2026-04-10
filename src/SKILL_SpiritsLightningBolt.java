
public class SKILL_SpiritsLightningBolt extends Skill {
	public SKILL_SpiritsLightningBolt () {
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
		
		me.getStatSpread()[EntityStats.WIL.ordinal()] += 10;
		
		if (me instanceof Enemy) {
			double[] res = ((Enemy)me).getDamageMultipliers();
			for (int i = 0; i < res.length; i++) {
				((Enemy)me).getDamageMultipliers()[i] *= 0.95;
			}
		}
		
		me.drainMana(getManaCost());
	}
	
	public String getEnemyIntentMsg(Entity me, Entity target) {
		int scaling = (int)Math.round(me.getStatSpread()[EntityStats.ARC.ordinal()]*0.6 + me.getStatSpread()[EntityStats.WIL.ordinal()]*0.4);
		return "Intends to Lightning Bolt the " + target + " for " + ((Character)target).calculateDamage(10 * Math.pow(1.06, scaling), DamageType.ELEC) + " damage and buff itself!";
	}
}
