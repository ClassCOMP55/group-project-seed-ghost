
public class SKILL_Fireball extends Skill {
	public SKILL_Fireball () {
		super(50);
		setName("Fireball");
		setDescription("Unleash a burst of flames, dealing FIRE damage to all enemies. Scales with Arcane and Constitution.");
		setvTarget("NA");
	}
	
	public void activationEffect (Entity me, Entity target) {
		double[] dmg = new double[] {0,0,0,0,0,0,0,0};
		int scaling = (int)Math.round(me.getStatSpread()[EntityStats.ARC.ordinal()]*0.6 + me.getStatSpread()[EntityStats.CON.ordinal()]*0.4);
		dmg[DamageType.FIRE.ordinal()] = 10 * Math.pow(1.055, scaling);
		System.out.println(dmg[DamageType.FIRE.ordinal()]);
		
		CombatPane currentBattle = MainApplication.combatPane;
		for (Enemy a : currentBattle.getMyArrEnemies()) {
			a.attackMe(dmg);
		}
		
		me.drainMana(getManaCost());
	}
}
