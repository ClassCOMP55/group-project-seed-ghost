
public class SKILL_EnemyBuffAllies extends Skill {
	public SKILL_EnemyBuffAllies () {
		super();
		setName("Buff Allies");
		setDescription("Increases stats for all allies by +15 and heal them.");
		setvTarget("NA");
	}
	
	public void activationEffect (Entity me, Entity target) {
		Enemy[] currentEnemies = MainApplication.combatPane.getMyArrEnemies();
		
		for (Enemy e : currentEnemies) {
			for (int i = 0; i < e.getStatSpread().length; i++) {
				e.getStatSpread()[i] += 15;
			}
			e.heal(30);
		}
	}
}
