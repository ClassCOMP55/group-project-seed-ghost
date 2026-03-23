
public class SKILL_BasicAttack extends Skill {
	public SKILL_BasicAttack () {
		super();
		setName("Strike");
	}
	
	public void activationEffect (Entity me, Entity target) {
		int[] statSpread;
		double[] initDamage;
		double[] initStatus;
		
		if (me instanceof Character) {
			Character self = (Character)me;
			
			WeaponItem atk = self.getWeapon();
			statSpread = self.getStatSpread();
			
			initDamage = atk.scaledDamage(statSpread);
			initStatus = atk.scaledStatus(statSpread);
		} else {
			Enemy self = (Enemy)me;
			
			WeaponItem atk = self.getAtk();
			statSpread = self.getStatSpread();
			
			initDamage = atk.scaledDamage(statSpread);
			initStatus = atk.scaledStatus(statSpread);
		}
		
		double finDamage = 0.0;
		double[] finStatus;
		if (me instanceof Character) {
			ArmorItem armor = ((Character) me).getArmor();
			
			finDamage = armor.calculateDamage(initDamage);
			finStatus = armor.calculateStatus(initStatus);
		} else {
			finDamage = ((Enemy) me).calculateDamage(initDamage);
			finStatus = ((Enemy) me).calculateStatus(initStatus);
		}
		
		target.hurt(finDamage);
		target.applyStatusLevels(finStatus);
	}
}
