
public class SKILL_IronWave extends Skill {
	public SKILL_IronWave () {
		super(30);
		setName("Iron Wave");
		setDescription("Permanently increase your Strength stat by 2 and attack.");
	}
	
	public void activationEffect (Entity me, Entity target) {
		me.getStatSpread()[EntityStats.STR.ordinal()] += 2;
		
		double[] dmg = me.attackOther();
		target.attackMe(dmg);
		
		me.drainMana(getManaCost());
	}
}
