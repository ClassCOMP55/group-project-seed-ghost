
public class SKILL_HeavyAttack extends Skill {
	Entity targ;
	
	public SKILL_HeavyAttack () {
		super();
		setName("Heavy Blow");
		setDescription("Take a turn to wind up a swing, dealing triple damage at the start of your next turn.");
		setvTarget("ENEMY");
		setAnimationType("TriSlash");
	}
	
	public void activationEffect (Entity me, Entity target) {
		targ = target;
		me.drainMana(getManaCost());
	}
	
	public void nextTurnEffect (Entity me, Entity target) {
		double[] dmg = me.attackOther();
		targ.attackMe(dmg);
		targ.attackMe(dmg);
		targ.attackMe(dmg);
		
		if (me instanceof Character) {
			Character m = (Character)me;
			System.out.println(me + " attacked " + targ + " with " + m.getWeapon());
		}
	}
	
	public String getEnemyIntentMsg(Entity me, Entity target) {
		return "Intends to attack " + target + " for " + (((Character)target).getArmor().calculateDamage(((Enemy)me).getAtk().scaledDamage(me.getStatSpread())) * 3) + " damage at the start of their next turn!";
	}
}