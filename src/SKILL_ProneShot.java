
public class SKILL_ProneShot extends Skill {
	private static final ArmorItem GUARD_ARMOR = new ArmorItem(true);
	ArmorItem tempArmor;
	
	public SKILL_ProneShot () {
		super();
		setName("Reposition");
		setDescription("Guard for 95% damage and make an attack against an enemy.");
		tempArmor = null;
		setvTarget("ENEMY");
		setAnimationType("Prone shot");
	}
	
	public void activationEffect (Entity me, Entity target) {
		tempArmor = ((Character)me).getArmor();
		((Character)me).setArmor(GUARD_ARMOR);
		
		target.attackMe(me.attackOther());
		
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
