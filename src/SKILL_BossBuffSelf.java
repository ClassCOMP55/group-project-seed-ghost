
public class SKILL_BossBuffSelf extends Skill {
	public SKILL_BossBuffSelf () {
		super();
		setName("Buff Self");
		setDescription("Increases all stats by +10 for this character.");
		setvTarget("NA");
	}
	
	public void activationEffect (Entity me, Entity target) {
		for (int i = 0; i < me.getStatSpread().length; i++) {
			me.getStatSpread()[i] += 10;
		}
	}
}
