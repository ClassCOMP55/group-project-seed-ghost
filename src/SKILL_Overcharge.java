
public class SKILL_Overcharge extends Skill {
	public SKILL_Overcharge () {
		super();
		setName("Overcharge");
		setDescription("Spend all of your MANA to deal enormous damage to all foes. Scales with Arcane.");
		setvTarget("NA");
		setAnimationType("Explode");
	}
	
	public void activationEffect (Entity me, Entity target) {
		double[] dmg = new double[] {0,0,0,0,0,0,0,0};
		int scaling = (int)Math.round(me.getStatSpread()[EntityStats.ARC.ordinal()] + me.getMana()*0.1);
		dmg[DamageType.MAGIC.ordinal()] = 10 * Chance.softExponential(1.055, scaling, 2000, 1.2);
		System.out.println(dmg[DamageType.MAGIC.ordinal()]);
		
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
		
		me.drainMana(me.getMana());
	}
	
	public String getEnemyIntentMsg(Entity me, Entity target) {
		int scaling = (int)Math.round(me.getStatSpread()[EntityStats.ARC.ordinal()]*0.6 + me.getStatSpread()[EntityStats.CON.ordinal()]*0.4);
		return "Intends to fireball all allies for " + (int)(10 * Chance.softExponential(1.055, scaling, 3000, 1.2)) + " damage!";
	}
	
	public boolean preconditionsMet (Entity self, Entity target) {
		return self.getMana() > 0;
	}
}
