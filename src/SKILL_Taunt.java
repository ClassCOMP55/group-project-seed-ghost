
public class SKILL_Taunt extends Skill {
	public SKILL_Taunt () {
		super(15);
		setName("Taunt");
		setDescription("Heal 50 HP. All enemies lose 30 strength and will target the character who uses this skill next turn.");
		setvTarget("NA");
		setAnimationType("Debuff");
	}
	
	public void activationEffect (Entity me, Entity target) {
		CombatPane currentBattle = MainApplication.combatPane;
		for (Enemy a : currentBattle.aliveEnemies()) {
			a.getStatSpread()[EntityStats.STR.ordinal()] -= 30;
			a.getStatSpread()[EntityStats.DEX.ordinal()] -= 30;
			a.setNextTarget((Character)me);
		}
		
		me.heal(50);
		
		me.drainMana(getManaCost());
	}
}
