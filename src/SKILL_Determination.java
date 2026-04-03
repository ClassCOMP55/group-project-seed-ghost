
public class SKILL_Determination extends Skill {
	public SKILL_Determination () {
		super(35);
		setName("Determination");
		setDescription("Permanently increase your MANA MAX, DEXTERITY and INSTINCT by 5.");
	}
	
	public void activationEffect (Entity me, Entity target) {
		me.getStatSpread()[EntityStats.DEX.ordinal()] += 5;
		me.getStatSpread()[EntityStats.IST.ordinal()] += 5;
		me.setManaMax(me.getManaMax() + 5);
		me.gainMana(5);
		
		me.drainMana(getManaCost());
	}
}
