
public class SKILL_BladeStorm extends Skill {
	public SKILL_BladeStorm () {
		super(60);
		setName("Blade Storm");
		setDescription("Deal 10 SLASHING damage to a random enemy for each point you have in ARCANE.");
		setvTarget("NA");
		setAnimationType("BladeStorm");
	}
	
	public void activationEffect (Entity me, Entity target) {
		double[] dmg = new double[] {0,0,0,0,0,0,0,0};
		dmg[DamageType.SLASH.ordinal()] = 10.0;
		System.out.println(dmg[DamageType.SLASH.ordinal()] * me.getStatSpread()[EntityStats.ARC.ordinal()]);
		
		CombatPane currentBattle = MainApplication.combatPane;
		if (me instanceof Character) {
			for (int i = 0; i < me.getStatSpread()[EntityStats.ARC.ordinal()]; i++) {
				if (currentBattle.aliveEnemies().length > 0) {
					currentBattle.aliveEnemies()[Chance.range(0, currentBattle.aliveEnemies().length - 1)].attackMe(dmg);
				}
			}
		} else if (me instanceof Enemy) {
			for (int i = 0; i < me.getStatSpread()[EntityStats.ARC.ordinal()]; i++) {
				if (currentBattle.aliveAllies().length > 0) {
					currentBattle.aliveAllies()[Chance.range(0, currentBattle.aliveAllies().length - 1)].attackMe(dmg);
				}
			}
		}
		
		me.drainMana(getManaCost());
	}
	
	public String getEnemyIntentMsg(Entity me, Entity target) {
		return "Intends to slice and dice all allies for " + (int)(10 * me.getStatSpread()[EntityStats.ARC.ordinal()]) + " damage!";
	}
}
