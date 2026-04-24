
public class SKILL_ShatteringShot extends Skill {
	public SKILL_ShatteringShot () {
		super(200);
		setName(".50 Caliber");
		setDescription("Shoot an enemy, lowering their defenses.");
		setvTarget("ENEMY");
		setAnimationType("50 Cal");
	}
	
	public void activationEffect (Entity me, Entity target) {
		double[] dmg = me.attackOther();
		target.attackMe(dmg);
		
		if (me instanceof Character) {
			for (int i = 0; i < 8; i++) {
				((Enemy)target).getDamageMultipliers()[i] += 0.15;
			}
		} else if (me instanceof Enemy) {
			for (int i = 0; i < 8; i++) {
				((Character)target).getArmor().getIncDamageMultipliers()[i] += 0.15;
			}
		}
		
		me.drainMana(getManaCost());
	}
	
	public String getEnemyIntentMsg(Entity me, Entity target) {
		return "Intends to debuff and attack " + target + " for " + ((Character)target).calculateDamage((Enemy)me) + " damage!";
	}
}