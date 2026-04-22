
public class SKILL_Explode extends Skill {
	public SKILL_Explode () {
		super(0);
		setName("Explode");
		setDescription("FUKCING EXPLODES.");
		setvTarget("NA");
		setAnimationType("spr_ATTACK_blast.gif");
	}
	
	public void activationEffect (Entity me, Entity target) {
		double[] dmg = new double[] {0,0,0,0,0,0,0,0};
		int scaling = (int)Math.round(me.getStatSpread()[EntityStats.ARC.ordinal()]*0.6 + me.getStatSpread()[EntityStats.CON.ordinal()]*0.4);
		dmg[DamageType.BLAST.ordinal()] = 10 * Math.pow(1.025, scaling);
		dmg[DamageType.FIRE.ordinal()] = 10 * Math.pow(1.025, scaling);
		System.out.println(dmg[DamageType.BLAST.ordinal()] + dmg[DamageType.FIRE.ordinal()]);
		
		CombatPane currentBattle = MainApplication.combatPane;
		if (me instanceof Character) {
			for (Enemy a : currentBattle.aliveEnemies()) {
				a.attackMe(dmg);
			}
		} else if (me instanceof Enemy) {
			for (Character a : currentBattle.aliveAllies()) {
				a.attackMe(dmg);
			}
		}
		
		me.drainMana(getManaCost());
		me.hurt(999999999);
	}
	
	public String getEnemyIntentMsg(Entity me, Entity target) {
		int scaling = (int)Math.round(me.getStatSpread()[EntityStats.ARC.ordinal()]*0.6 + me.getStatSpread()[EntityStats.CON.ordinal()]*0.4);
		return "Intends to violently self-detonate for " + 20 * Math.pow(1.025, scaling) + " damage!";
	}
}
