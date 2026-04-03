
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
	}
	
	public void activationEffect (Entity me, Entity target) {
		tempArmor = ((Character)target).getArmor();
		((Character)target).setArmor(GUARD_ARMOR);
		
		tar = (Character) target;
		
		System.out.println(tempArmor);
	}
	
	public void nextTurnEffect (Entity me, Entity target) {
		tar.setArmor(tempArmor);
		System.out.println("Replaced " + tempArmor);
	}
}
