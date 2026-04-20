
public class SKILL_CriticalShot extends Skill {
	public SKILL_CriticalShot () {
		super(300);
		setName("Perfect Precision");
		setDescription("Fire a perfectly accurate shot at an enemy, dealing immense PIERCE and BLAST damage. Costs 300 mana.");
		setvTarget("ENEMY");
		setAnimationType("MagicAttack");
	}
	
	public void activationEffect (Entity me, Entity target) {
		double[] dmg = new double[] {0,0,0,0,0,0,0,0};
		int scaling = (int)Math.round(me.getStatSpread()[EntityStats.PRC.ordinal()]*0.4 + me.getStatSpread()[EntityStats.DEX.ordinal()]*0.4);
		double damage = 10 * Math.pow(1.06, scaling);
		dmg[DamageType.PIERCE.ordinal()] = damage * 0.5;
		dmg[DamageType.BLAST.ordinal()] = damage * 0.5;
		target.attackMe(dmg);
		
		me.drainMana(getManaCost());
	}
	
	public String getEnemyIntentMsg(Entity me, Entity target) {
		int scaling = (int)Math.round(me.getStatSpread()[EntityStats.PRC.ordinal()]*0.8 + me.getStatSpread()[EntityStats.DEX.ordinal()]*0.6);
		return "Intends to shoot the " + target + " for " + ((Character)target).calculateDamage(10 * Math.pow(1.05, scaling), DamageType.PIERCE) + " damage!";
	}
}
