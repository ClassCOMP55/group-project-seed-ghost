
public class SKILL_PrayerOfHealing extends Skill {
	public SKILL_PrayerOfHealing () {
		super(40);
		setName("Prayer of Healing");
		setDescription("Heal a party member, scales with FAITH. Costs 40 mana.");
		setvTarget("CHARA");
		setAnimationType("Buff");
	}
	
	public void activationEffect (Entity me, Entity target) {
		target.heal(me.getStatSpread()[EntityStats.FTH.ordinal()] * 2);
		
		me.drainMana(getManaCost());
	}
}
