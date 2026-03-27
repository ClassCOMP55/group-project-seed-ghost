
public class SKILL_BasicAttack extends Skill {
	public SKILL_BasicAttack () {
		super();
		setName("Strike");
		setDescription("Attack with your main weapon.");
	}
	
	public void activationEffect (Entity me, Entity target) {
		double[] dmg = me.attackOther();
		target.attackMe(dmg);
	}
}