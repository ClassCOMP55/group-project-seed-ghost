
public class SKILL_PrayerOfHealing extends Skill {
	public SKILL_PrayerOfHealing () {
		super(140);
		setName("Prayer of Healing");
		setName("Heal self or an ally. Scales with FAITH.");
	}
	
	public void activationEffect (Entity me, Entity target) {
		target.heal(me.getStatSpread()[EntityStats.FTH.ordinal()]);
		
		me.drainMana(getManaCost());
	}
}
