
public class SKILL_Drain extends Skill {
	public SKILL_Drain () {
		super(40);
		setName("Drain");
		setDescription("Attack with your weapon and heal equal to half the damage dealt. Reduce your target's constitution by 10.");
	}
	
	public void activationEffect (Entity me, Entity target) {
		double[] dmg = me.attackOther();
		double healing = target.attackMe(dmg) / 2;
		
		target.getStatSpread()[EntityStats.CON.ordinal()] -= 10;
		me.heal(healing);
		
		me.drainMana(getManaCost());
	}
}
