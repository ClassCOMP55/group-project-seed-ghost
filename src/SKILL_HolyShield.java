
public class SKILL_HolyShield extends Skill {
	private static final ArmorItem GUARD_ARMOR = new ArmorItem(false);
	ArmorItem tempArmor;
	
	public SKILL_HolyShield () {
		super();
		setName("Shield of Faith");
		setDescription("Become immune to affinity damage until the start of your next turn.");
		tempArmor = null;
	}
	
	public void activationEffect (Entity me, Entity target) {
		tempArmor = ((Character)me).getArmor();
		((Character)me).setArmor(GUARD_ARMOR);
		
		System.out.println(tempArmor);
	}
	
	public void nextTurnEffect (Entity me, Entity target) {
		((Character)me).setArmor(tempArmor);
		System.out.println("Replaced " + tempArmor);
	}
}
