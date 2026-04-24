
public class SKILL_AllyBuffAllies extends Skill {
	public SKILL_AllyBuffAllies () {
		super(100);
		setName("Buff Allies");
		setDescription("Increases stats for all allies by +30 for this turn.");
		setvTarget("NA");
		setAnimationType("BuffAllies");
	}
	
	public void activationEffect (Entity me, Entity target) {
		Character[] currentEnemies = MainApplication.combatPane.aliveAllies();
		
		for (Character e : currentEnemies) {
			for (int i = 0; i < e.getStatSpread().length; i++) {
				e.getStatSpread()[i] += 30;
			}
		}
	}
	
	public void nextTurnEffect (Entity me, Entity target) {
		Character[] currentEnemies = MainApplication.combatPane.aliveAllies();
		
		for (Character e : currentEnemies) {
			for (int i = 0; i < e.getStatSpread().length; i++) {
				e.getStatSpread()[i] -= 30;
			}
		}
	}
	
	public String getEnemyIntentMsg(Entity me, Entity target) {
		return "Intends to buff all of its allies!";
	}
}
