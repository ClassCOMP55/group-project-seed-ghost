
public class SKILL_HolyShield extends Skill {
	private static final ArmorItem GUARD_ARMOR = new ArmorItem(false);
	ArmorItem tempArmor;
	Character tar;
	
	public SKILL_HolyShield () {
		super(5);
		setName("Shield of Faith");
		setDescription("Make a CHARACTER immune to damage until the end of your next turn.");
		tempArmor = null;
		setvTarget("CHARA");
		setAnimationType("DefenseOrUtility");
	}
	
	public void activationEffect (Entity me, Entity target) {
		tar = (Character) target;
		
		tempArmor = tar.getArmor();
		tar.setArmor(GUARD_ARMOR);
		
		System.out.println(tempArmor);
		
		me.drainMana(getManaCost());
	}
	
	public void nextTurnEffect (Entity me, Entity target) {
		tar.setArmor(tempArmor);
		System.out.println("Replaced " + tempArmor);
	}
	
	public boolean preconditionsMet (Entity self, Entity target) {
		return self.getMana() >= getManaCost() && !((Character)target).guarding();
	}
}
