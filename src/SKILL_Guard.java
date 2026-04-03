
public class SKILL_Guard extends Skill {
	private static final ArmorItem GUARD_ARMOR = new ArmorItem(true);
	ArmorItem tempArmor;
	
	public SKILL_Guard () {
		super();
		setName("Guard Self");
		setDescription("Sets your damage reduction to 95% until the start of your next turn.");
		tempArmor = null;
		setvTarget("NA");
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
