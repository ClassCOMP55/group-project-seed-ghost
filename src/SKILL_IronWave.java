
public class SKILL_IronWave extends Skill {
	public SKILL_IronWave () {
		super(30);
		setName("Iron Wave");
		setDescription("Permanently increase your Strength stat by 4 and attack. Costs 30 mana.");
		setvTarget("ENEMY");
		setAnimationType("NonMagicAttack");
	}
	
	public void activationEffect (Entity me, Entity target) {
		me.getStatSpread()[EntityStats.STR.ordinal()] += 4;
		
		double[] dmg = me.attackOther();
		target.attackMe(dmg);
		
		me.drainMana(getManaCost());
	}
	
	public String getEnemyIntentMsg(Entity me, Entity target) {
		return "Intends to buff itself and attack " + target + " for " + ((Character)target).calculateDamage((Enemy)me) + " damage!";
	}
}
