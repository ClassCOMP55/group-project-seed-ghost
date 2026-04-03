
public class SKILL_PrayerOfHealing extends Skill {
	public SKILL_PrayerOfHealing () {
		super(70);
		setName("Prayer of Healing");
		setDescription("Heal a party member, scales with FAITH. Costs 70 mana.");
		setvTarget("CHARA");
	}
	
	public void activationEffect (Entity me, Entity target) {
		target.heal(me.getStatSpread()[EntityStats.FTH.ordinal()]);
		
		me.drainMana(getManaCost());
	}
}
