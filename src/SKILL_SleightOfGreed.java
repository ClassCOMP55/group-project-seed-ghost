
public class SKILL_SleightOfGreed extends Skill {
	public SKILL_SleightOfGreed () {
		super(25);
		setName("Sleight of Greed");
		setDescription("Attack for heavy damage. If fatal, gain 100 gold.");
		setvTarget("ENEMY");
		setAnimationType("NonMagicAttack");
	}
	
	public void activationEffect (Entity me, Entity target) {
		double[] dmg = me.attackOther();
		target.attackMe(dmg);
		target.attackMe(dmg);
		target.attackMe(dmg);
		
		if (!(target.getHp() > 0)) {
			CharacterSelectionPane.myInventory.setGold(CharacterSelectionPane.myInventory.getGold() + 100);
		}
		
		me.drainMana(getManaCost());
	}
	
	public String getEnemyIntentMsg(Entity me, Entity target) {
		return "Intends to attack " + target + " for " + (((Character)target).calculateDamage((Enemy)me) * 3) + " damage!";
	}
}