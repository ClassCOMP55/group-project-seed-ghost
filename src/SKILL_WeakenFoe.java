
public class SKILL_WeakenFoe extends Skill {
	public SKILL_WeakenFoe () {
		super(20);
		setName("Weaken Foe");
		setDescription("Decreases all of a target's stats by -15 permanently.");
		setvTarget("ENEMY");
		setAnimationType("NonMagicAttack");
	}
	
	public void activationEffect (Entity me, Entity target) {
		for (int i = 0; i < target.getStatSpread().length; i++) {
			target.getStatSpread()[i] -= 30;
		}
	}
	
	public String getEnemyIntentMsg(Entity me, Entity target) {
		return "Intends to debuff " + target + " permanently!";
	}
}
