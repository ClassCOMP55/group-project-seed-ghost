
public class SKILL_BasicAttack extends Skill {
	public SKILL_BasicAttack () {
		super();
		setName("Strike");
		setDescription("Attack with your main weapon.");
		setvTarget("ENEMY");
	}
	
	public void activationEffect (Entity me, Entity target) {
		double[] dmg = me.attackOther();
		target.attackMe(dmg);
		
		if (me instanceof Character) {
			Character m = (Character)me;
			System.out.println(me + " attacked " + target + " with " + m.getWeapon());
		}
	}
	
	public String getEnemyIntentMsg(Entity me, Entity target) {
		return me + " intends to attack " + target + " for " + (((Character)target).getArmor().calculateDamage(((Enemy)me).getAtk().scaledDamage(me.getStatSpread()))) + " damage!";
	}
}