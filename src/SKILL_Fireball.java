
public class SKILL_Fireball extends Skill {
	public SKILL_Fireball () {
		super(50);
		setName("Fireball");
		setDescription("Unleash a burst of flames, dealing FIRE damage to all enemies. Scales with Arcane and Constitution.");
		setvTarget("NA");
		setAnimationType("FireAttack");
	}
	
	public void activationEffect (Entity me, Entity target) {
		double[] dmg = new double[] {0,0,0,0,0,0,0,0};
		int scaling = (int)Math.round(me.getStatSpread()[EntityStats.ARC.ordinal()]*0.6 + me.getStatSpread()[EntityStats.CON.ordinal()]*0.4);
		dmg[DamageType.FIRE.ordinal()] = 10 * Chance.softExponential(1.055, scaling, 1000, 4);
		System.out.println(dmg[DamageType.FIRE.ordinal()]);
		
		CombatPane currentBattle = MainApplication.combatPane;
		if (me instanceof Character) {
			for (Enemy a : currentBattle.aliveEnemies()) {
				a.attackMe(dmg);
			}
		} else if (me instanceof Enemy) {
			for (Character a : currentBattle.aliveAllies()) {
				a.attackMe(dmg);
			}
		}
		
		me.drainMana(getManaCost());
	}
	
	public String getEnemyIntentMsg(Entity me, Entity target) {
		int scaling = (int)Math.round(me.getStatSpread()[EntityStats.ARC.ordinal()]*0.6 + me.getStatSpread()[EntityStats.CON.ordinal()]*0.4);
		return "Intends to fireball all allies for " + 10 * Chance.softExponential(1.055, scaling, 1000, 4) + " damage!";
	}
}
