
public class SKILL_Recovery extends Skill {
	public SKILL_Recovery () {
		super(0);
		setName("Recovery");
		setDescription("Fully recover an ally's MANA.");
		setvTarget("CHARA");
	}
	
	public void activationEffect (Entity me, Entity target) {
		target.gainMana(99999.9);
		
		me.drainMana(getManaCost());
	}
}
