
public class SKILL_Determination extends Skill {
	public SKILL_Determination () {
		super(35);
		setName("Determination");
		setDescription("Permanently increase your MANA MAX, DEXTERITY and INSTINCT by 10.");
		setvTarget("NA");
		setAnimationType("DefenseOrUtility");
	}
	
	public void activationEffect (Entity me, Entity target) {
		me.getStatSpread()[EntityStats.DEX.ordinal()] += 10;
		me.getStatSpread()[EntityStats.IST.ordinal()] += 10;
		me.setManaMax(me.getManaMax() + 10);
		me.gainMana(10);
		
		me.drainMana(getManaCost());
	}
	
	public String getEnemyIntentMsg(Entity me, Entity target) {
		return "Intends to buff itself!";
	}
}
