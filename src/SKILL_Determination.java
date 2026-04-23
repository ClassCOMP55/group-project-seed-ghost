
public class SKILL_Determination extends Skill {
	public SKILL_Determination () {
		super(50);
		setName("Determination");
		setDescription("Attack all enemies and permanently buff self.");
		setvTarget("NA");
		setAnimationType("BuffAndAttackAll");
	}
	
	public void activationEffect (Entity me, Entity target) {
		me.getStatSpread()[EntityStats.DEX.ordinal()] += 10;
		me.getStatSpread()[EntityStats.IST.ordinal()] += 10;
		me.setManaMax(me.getManaMax() + 10);
		me.gainMana(10);
		
		double[] dmg = me.attackOther();
		
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
		return "Intends to buff itself and make an AoE attack!";
	}
}
