import acm.graphics.GImage;

public class Entity {
	private double hpMax, hp, manaMax, mana, deathResist;
	private int[] statSpread;
	private double[] statusLevels;
	private EntityStats mainStat;
	private GImage sprite;
	private String tooltip;
	
	public Entity (double hpMax, double manaMax, double deathResist, int str, int dex, int prc, int ist, int con, int wil, int fth, int arc) {
		this.hp = hpMax;
		this.hpMax = hpMax;
		this.mana = manaMax;
		this.manaMax = manaMax;
		this.deathResist = deathResist;
		this.sprite = null;
		
		int[] temp = new int[8];
		
		temp[EntityStats.STR.ordinal()] = str;
		temp[EntityStats.DEX.ordinal()] = dex;
		temp[EntityStats.PRC.ordinal()] = prc;
		temp[EntityStats.IST.ordinal()] = ist;
		temp[EntityStats.CON.ordinal()] = con;
		temp[EntityStats.WIL.ordinal()] = wil;
		temp[EntityStats.FTH.ordinal()] = fth;
		temp[EntityStats.ARC.ordinal()] = arc;
		
		this.statSpread = temp;
		findMainStat();
		
		generateTooltip();
	}
	
	/*
	 * This method is meant to be overriden in Enemy and Character
	 */
	public void die () {
		return;
	}
	
	/*
	 * @return whether or not the creature has zero or less hit points remaining
	 */
	public boolean isDead () {
		return hp <= 0;
	}
	
	/*
	 * Deals damage to the Entity, reducing its health
	 * 
	 * @param the amount of damage to take
	 */
	public void hurt (double damage) {
		hp -= damage;
		
		if (hp >= hpMax) {
			hp = hpMax;
		}
		
		if (isDead()) {
			
			if (Chance.coinflip(deathResist)) {
				heal(1);
			} else {
				die();
			}
		}
	}
	
	/*
	 * Heals the Entity, increasing its health up to its maximum
	 * 
	 * @param the amount of health to heal
	 */
	public void heal (double health) {
		hp += health;
		
		if (hp > hpMax) {
			hp = hpMax;
		}
	}
	
	/*
	 * Drains the Entity's mana
	 * 
	 * @param amount to drain
	 */
	public void drainMana (double amount) {
		mana -= amount;
		
		if (mana < 0) {
			mana = 0;
		}
	}
	
	/*
	 * Recovers Entity's mana
	 * 
	 * @param amount to recover
	 */
	public void gainMana (double amount) {
		mana += amount;
		
		if (mana > manaMax) {
			mana = manaMax;
		}
	}

	public double getHpMax() {
		return hpMax;
	}

	public void setHpMax(double hpMax) {
		this.hpMax = hpMax;
	}

	public double getHp() {
		return hp;
	}

	public void setHp(double hp) {
		this.hp = hp;
	}

	public double getManaMax() {
		return manaMax;
	}

	public void setManaMax(double manaMax) {
		this.manaMax = manaMax;
	}

	public double getMana() {
		return mana;
	}

	public void setMana(double mana) {
		this.mana = mana;
	}

	public double getDeathResist() {
		return deathResist;
	}

	public void setDeathResist(double deathResist) {
		this.deathResist = deathResist;
	}

	public int[] getStatSpread() {
		return statSpread;
	}

	public void setStatSpread(int[] statSpread) {
		this.statSpread = statSpread;
	}

	public EntityStats getMainStat() {
		findMainStat();
		return mainStat;
	}

	public void setMainStat(EntityStats mainStat) {
		this.mainStat = mainStat;
	}
	
	private void findMainStat () {
		int index = 0;
		int max = Integer.MIN_VALUE;
		
		for (int i = 0; i < statSpread.length; i++) {
			if (statSpread[i] > max) {
				index = i;
				max = statSpread[i];
			}
		}
		
		if (index == EntityStats.STR.ordinal()) {
			setMainStat(EntityStats.STR);
		} else if (index == EntityStats.DEX.ordinal()) {
			setMainStat(EntityStats.DEX);
		} else if (index == EntityStats.PRC.ordinal()) {
			setMainStat(EntityStats.PRC);
		} else if (index == EntityStats.IST.ordinal()) {
			setMainStat(EntityStats.IST);
		} else if (index == EntityStats.CON.ordinal()) {
			setMainStat(EntityStats.CON);
		} else if (index == EntityStats.WIL.ordinal()) {
			setMainStat(EntityStats.WIL);
		} else if (index == EntityStats.FTH.ordinal()) {
			setMainStat(EntityStats.FTH);
		} else if (index == EntityStats.ARC.ordinal()) {
			setMainStat(EntityStats.ARC);
		}
	}

	public GImage getSprite() {
		return sprite;
	}

	public void setSprite(GImage sprite) {
		this.sprite = sprite;
	}
	
	public void setSprite(String sprite) {
		this.sprite = new GImage(sprite);
	}

	public double[] getStatusLevels() {
		return statusLevels;
	}

	public void setStatusLevels(double[] statusLevels) {
		this.statusLevels = statusLevels;
	}
	
	public void applyStatusLevels (double[] statusLevels) {
		for (int i = 0; i < statusLevels.length; i++) {
			this.statusLevels[i] += statusLevels[i];
		}
	}
	
	/*
	 * Override this one.
	 */
	public double attackMe (double[] attackSpread) {
		double sum = 0;
		for (int i = 0; i < attackSpread.length; i++) {
			sum += attackSpread[i];
		}
		
		hurt(sum);
		return sum;
	}
	
	/*
	 * Override this one too
	 */
	public double[] attackOther () {
		double[] temp = new double[] {0,0,0,0,0,0,0,0};
		return temp;
	}
	
	/*
	 * Gets the tooltip
	 */
	public String getTooltip () {
		return tooltip;
	}
	
	public void generateTooltip () {
		tooltip = "ENTITY /nDoes the silly.";
	}
	
	public void generateTooltip (String in) {
		tooltip = in;
	}
}
