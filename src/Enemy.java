public class Enemy extends Entity {
	private double[] damageMultipliers;
	
	/*
	 * Creates a dummy enemy
	 */
	public Enemy () {
		super(100, 100, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		setDamageMultipliers(new double[] {1,1,1,1,1,1,1,1});
	}
	
	public Enemy(double hpMax, double manaMax, double deathResist, int str, int dex, int prc, int ist, int con, int wil,
			int fth, int arc) {
		super(hpMax, manaMax, deathResist, str, dex, prc, ist, con, wil, fth, arc);
		
		setDamageMultipliers(new double[] {1,1,1,1,1,1,1,1});
	}

	public double[] getDamageMultipliers() {
		return damageMultipliers;
	}

	public void setDamageMultipliers(double[] damageMultipliers) {
		this.damageMultipliers = damageMultipliers;
	}
	
}
