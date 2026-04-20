
public class SKILL_Assassinate extends Skill {
	public SKILL_Assassinate () {
		super(50);
		setName("Assassinate");
		setDescription("If the target is at full hit points, deal huge damage and lower their damage resistances. Otherwise just deals big damage.");
		setvTarget("ENEMY");
		setAnimationType("NonMagicAttack");
	}
	
	public void activationEffect (Entity me, Entity target) {
		double[] dmg = me.attackOther();
		boolean assassinate = (target.getHpMax() == target.getHp());
		
		target.attackMe(dmg);
		target.attackMe(dmg);
		
		if (assassinate) {
			target.attackMe(dmg);
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
		}
		
		me.drainMana(getManaCost());
	}
	
	public String getEnemyIntentMsg(Entity me, Entity target) {
		if (target.getHpMax() == target.getHp()) {
			return "Intends to attack " + target + " for " + (((Character)target).calculateDamage((Enemy)me) * 4) + " damage and permanently debuff their armor!";
		}
		return "Intends to attack " + target + " for " + (((Character)target).calculateDamage((Enemy)me) * 2) + " damage!";
	}
}