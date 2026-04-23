
public class SKILL_Guard extends Skill {
	ArmorItem tempArmor;
	
	public SKILL_Guard () {
		super();
		setName("Guard Self");
		setDescription("Sets your damage reduction to 95% until the start of your next turn.");
		tempArmor = null;
		setvTarget("NA");
		setAnimationType("DefenseOrUtilitySelf");
	}
	
	public void activationEffect (Entity me, Entity target) {
		tempArmor = ((Character)me).getArmor();
		((Character)me).setArmor(new ArmorItem(tempArmor));
		
		System.out.println(tempArmor);
	}
	
	public void nextTurnEffect (Entity me, Entity target) {
		((Character)me).setArmor(tempArmor);
		System.out.println("Replaced " + tempArmor);
	}
	
	public boolean preconditionsMet (Entity self, Entity target) {
		return !((Character)self).guarding();
	}
}
