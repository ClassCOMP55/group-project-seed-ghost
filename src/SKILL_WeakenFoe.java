
public class SKILL_WeakenFoe extends Skill {
	public SKILL_WeakenFoe () {
		super(50);
		setName("Weaken Foe");
		setDescription("Decreases all of a target's stats by -10 permanently.");
		setvTarget("ENEMY");
	}
	
	public void activationEffect (Entity me, Entity target) {
		for (int i = 0; i < target.getStatSpread().length; i++) {
			target.getStatSpread()[i] -= 10;
		}
	}
	
	public String getEnemyIntentMsg(Entity me, Entity target) {
		return me + " intends to debuff " + target + " permanently!";
	}
}
