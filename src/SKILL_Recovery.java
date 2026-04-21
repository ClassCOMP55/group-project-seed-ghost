
public class SKILL_Recovery extends Skill {
	public SKILL_Recovery () {
		super(0);
		setName("Recovery");
		setDescription("Fully recover an ally's MANA.");
		setvTarget("CHARA");
	}
	
	public void activationEffect (Entity me, Entity target) {
		target.gainMana(me.getStatSpread()[me.getMainStat().ordinal()] * 2);
		
		me.drainMana(getManaCost());
	}
}
