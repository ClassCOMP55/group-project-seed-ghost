
public class SKILL_PrayerOfHealing extends Skill {
	public SKILL_PrayerOfHealing () {
		super(140);
		setName("Prayer of Healing");
		setDescription("Heal a party member. Scales with FAITH.");
	}
	
	public void activationEffect (Entity me, Entity target) {
		target.heal(me.getStatSpread()[EntityStats.FTH.ordinal()]);
		
		me.drainMana(getManaCost());
	}
}
