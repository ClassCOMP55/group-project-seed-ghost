
public class WeaponItem {
	private String affinity, type;
	private boolean ranged, magic;
	private double[] baseAttack, statScaling, baseStatus;
	private int purchaseCost;
	
	/*
	 * Weapon item, creates weapon's stats based on the type and affinity
	 * 
	 * @param type: Greatsword, Shortsword, etc.
	 * @param affinity: Fire, Elec, Holy, Magic
	 */
	public WeaponItem (String type, String affinity) {
		this.type = type;
		this.affinity = affinity;
		
		baseAttack = new double[] {0,0,0,0,0,0,0,0};
		statScaling = new double[] {0,0,0,0,0,0,0,0};
		baseStatus = new double[] {0,0,0,0,0,0,0,0,0};
		
		setMagic(false);
		setRanged(false);
		
		applyWeaponType();
		applyAffinity();
	}
	
	/*
	 * WeaponItem, creates a null weapon
	 */
	public WeaponItem () {
		affinity = null;
		type = null;
		
		ranged = false;
		magic = false;
		
		baseAttack = new double[] {0,0,0,0,0,0,0,0};
		statScaling = new double[] {0,0,0,0,0,0,0,0};
		baseStatus = new double[] {0,0,0,0,0,0,0,0,0};
		
		setPurchaseCost(0);
	}
	
	/*
	 * WeaponItem, specifically for enemies
	 */
	public WeaponItem (double[] baseAttack, double[] statScaling, double[] baseStatus, String affinity, boolean ranged, boolean magic) {
		type = "Enemy Attack";
		
		setRanged(ranged);
		setMagic(magic);
		
		setBaseAttack(baseAttack);
		setStatScaling(statScaling);
		setBaseStatus(baseStatus);
	}
	
	/*
	 * WeaponItem, creates a fully random weapon
	 * 
	 * @param boolean: It actually doesn't matter if its true or false
	 */
	public WeaponItem (boolean actuallythisdoesntdoanythingitjustsignalstotheconstructortorunadifferentcreationthingy) {
		this.type = Chance.choose(new String[] {"greatsword","shortsword","katana","dagger","polearm","axe","hammer","longbow","shortbow","crossbow","rifle","pistol","stave","talisman","bomb"});
		this.affinity = Chance.choose(new String[] {"null","null","null","null","null","null","null","null","fire","elec","holy","magic"});
		
		baseAttack = new double[] {0,0,0,0,0,0,0,0};
		statScaling = new double[] {0,0,0,0,0,0,0,0};
		baseStatus = new double[] {0,0,0,0,0,0,0,0,0};
		
		setMagic(false);
		setRanged(false);
		
		applyWeaponType();
		applyAffinity();
	}
	
	private void applyWeaponType () {
		switch (type) {
			case "greatsword": 
				baseAttack[DamageType.SLASH.ordinal()] = Chance.range(30,50);
				statScaling[EntityStats.STR.ordinal()] = 1;
				setPurchaseCost(Chance.range(150, 200));
			break;
			case "shortsword":
				baseAttack[DamageType.SLASH.ordinal()] = Chance.range(10,30);
				statScaling[EntityStats.STR.ordinal()] = 0.5;
				statScaling[EntityStats.DEX.ordinal()] = 0.5;
				setPurchaseCost(Chance.range(50, 100));
			break;
			case "katana":
				baseAttack[DamageType.SLASH.ordinal()] = Chance.range(15,35);
				statScaling[EntityStats.PRC.ordinal()] = 0.2;
				statScaling[EntityStats.DEX.ordinal()] = 1.1;
				baseStatus[StatusEffects.BLEED.ordinal()] = 20;
				setPurchaseCost(Chance.range(50, 100));
			break;
			case "dagger":
				baseAttack[DamageType.PIERCE.ordinal()] = Chance.range(10,20);
				statScaling[EntityStats.DEX.ordinal()] = 2;
				baseStatus[StatusEffects.BLEED.ordinal()] = 20;
				setPurchaseCost(Chance.range(20, 50));
			break;
			case "polearm":
				baseAttack[DamageType.SLASH.ordinal()] = Chance.range(35,45);
				statScaling[EntityStats.DEX.ordinal()] = 1;
				setPurchaseCost(Chance.range(50, 150));
			break;
			case "axe":
				baseAttack[DamageType.SLASH.ordinal()] = Chance.range(30,50);
				statScaling[EntityStats.STR.ordinal()] = 0.7;
				statScaling[EntityStats.DEX.ordinal()] = 0.7;
				setPurchaseCost(Chance.range(20, 50));
			break;
			case "hammer":
				baseAttack[DamageType.CRUSH.ordinal()] = Chance.range(20,60);
				statScaling[EntityStats.STR.ordinal()] = 1.1;
				setPurchaseCost(Chance.range(20, 50));
			break;
			case "longbow":
				baseAttack[DamageType.PIERCE.ordinal()] = Chance.range(25,45);
				statScaling[EntityStats.STR.ordinal()] = 0.7;
				statScaling[EntityStats.DEX.ordinal()] = 0.8;
				statScaling[EntityStats.PRC.ordinal()] = 0.7;
				setRanged(true);
				setPurchaseCost(Chance.range(50, 100));
			break;
			case "shortbow":
				baseAttack[DamageType.PIERCE.ordinal()] = Chance.range(20,40);
				statScaling[EntityStats.DEX.ordinal()] = 1;
				statScaling[EntityStats.PRC.ordinal()] = 1;
				setRanged(true);
				setPurchaseCost(Chance.range(20, 50));
			break;
			case "crossbow":
				baseAttack[DamageType.PIERCE.ordinal()] = Chance.range(35,65);
				statScaling[EntityStats.PRC.ordinal()] = 1.5;
				setRanged(true);
				setPurchaseCost(Chance.range(100, 150));
			break;
			case "pistol":
				baseAttack[DamageType.PIERCE.ordinal()] = Chance.range(50,70);
				statScaling[EntityStats.DEX.ordinal()] = 0.4;
				statScaling[EntityStats.PRC.ordinal()] = 0.8;
				setRanged(true);
				setPurchaseCost(Chance.range(300, 500));
			break;
			case "rifle":
				baseAttack[DamageType.PIERCE.ordinal()] = Chance.range(60,100);
				statScaling[EntityStats.DEX.ordinal()] = 0.5;
				statScaling[EntityStats.PRC.ordinal()] = 1.0;
				setRanged(true);
				setPurchaseCost(Chance.range(400, 600));
			break;
			case "stave":
				baseAttack[DamageType.MAGIC.ordinal()] = Chance.range(10,70);
				statScaling[EntityStats.WIL.ordinal()] = 0.1;
				statScaling[EntityStats.ARC.ordinal()] = 0.9;
				baseStatus[StatusEffects.CONFUSE.ordinal()] = 8;
				setRanged(true);
				setMagic(true);
				setPurchaseCost(Chance.range(100, 200));
			break;
			case "talisman":
				baseAttack[DamageType.HOLY.ordinal()] = Chance.range(10,70);
				statScaling[EntityStats.WIL.ordinal()] = 0.1;
				statScaling[EntityStats.FTH.ordinal()] = 0.9;
				baseStatus[StatusEffects.CHARM.ordinal()] = 8;
				setRanged(true);
				setMagic(true);
				setPurchaseCost(Chance.range(100, 200));
			break;
			case "bomb":
				baseAttack[DamageType.BLAST.ordinal()] = Chance.range(40,70);
				baseAttack[DamageType.FIRE.ordinal()] = Chance.range(10,30);
				baseAttack[DamageType.CRUSH.ordinal()] = Chance.range(10,30);
				statScaling[EntityStats.CON.ordinal()] = 0.005;
				statScaling[EntityStats.WIL.ordinal()] = 0.005;
				statScaling[EntityStats.DEX.ordinal()] = 0.005;
				statScaling[EntityStats.PRC.ordinal()] = 0.005;
				baseStatus[StatusEffects.BLIND.ordinal()] = 10;
				setRanged(true);
				setPurchaseCost(Chance.range(300, 500));
			break;
			default:
			break;
		}
	}
	
	private void applyAffinity () {
		double scl = 0;
		double add = 0;
		
		if (affinity != "null") {
			double affinityStrength = Chance.range(0.4,0.6);
			
			setPurchaseCost((int) (getPurchaseCost() * 1.2));
			
			if (isMagic()) {
				affinityStrength = 1.0;
			} else if (isRanged()) {
				affinityStrength = Chance.range(0.05,1.0);
			}
			
			if (!isMagic()) {
				scl = reduceScalings(affinityStrength);
			}
			add = (getBaseDamage() * 0.15) + reduceDamage(affinityStrength);
		}
		
		switch (affinity) {
			case "fire":
				baseAttack[DamageType.FIRE.ordinal()] += add;
				statScaling[EntityStats.CON.ordinal()] += scl;
			break;
			case "holy":
				baseAttack[DamageType.HOLY.ordinal()] += add;
				statScaling[EntityStats.FTH.ordinal()] += scl;
				baseStatus[StatusEffects.CHARM.ordinal()] += 8;
			break;
			case "elec":
				baseAttack[DamageType.ELEC.ordinal()] += add;
				statScaling[EntityStats.WIL.ordinal()] += scl;
				baseStatus[StatusEffects.PARALYZE.ordinal()] += 8;
			break;
			case "magic":
				baseAttack[DamageType.MAGIC.ordinal()] += add;
				statScaling[EntityStats.ARC.ordinal()] += scl;
				baseStatus[StatusEffects.CONFUSE.ordinal()] += 8;
			break;
		}
	}
	
	private double reduceScalings (double perc) {
		double reducedBy = 0;
		for (int i = 0; i < statScaling.length; i++) {
			reducedBy += statScaling[i] * perc;
			statScaling[i] *= (1 - perc);
		}
		return reducedBy;
	}
	
	private double reduceDamage (double perc) {
		double reducedBy = 0;
		for (int i = 0; i < baseAttack.length; i++) {
			if (baseAttack[i] > 0) {
				double temp = baseAttack[i];
				reducedBy += temp * perc;
				baseAttack[i] *= (1 - perc);
			}
		}
		
		return reducedBy;
	}

	public String getAffinity() {
		return affinity;
	}

	public void setAffinity(String affinity) {
		this.affinity = affinity;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isRanged() {
		return ranged;
	}

	public void setRanged(boolean ranged) {
		this.ranged = ranged;
	}

	public boolean isMagic() {
		return magic;
	}

	public void setMagic(boolean magic) {
		this.magic = magic;
	}

	public double[] getBaseAttack() {
		return baseAttack;
	}

	public void setBaseAttack(double[] baseAttack) {
		this.baseAttack = baseAttack;
	}

	public double[] getStatScaling() {
		return statScaling;
	}

	public void setStatScaling(double[] statScaling) {
		this.statScaling = statScaling;
	}

	public double[] getBaseStatus() {
		return baseStatus;
	}

	public void setBaseStatus(double[] baseStatus) {
		this.baseStatus = baseStatus;
	}
	
	public double getBaseDamage () {
		int t = 0;
		
		for (double k : baseAttack) {
			t += k;
		}
		
		return t;
	}
	
	/*
	 * Returns the amount of damage the weapon does after stat scaling
	 * 
	 * @param double[] the party member's stats
	 * @return double[] all damage members after scaling
	 */
	public double[] scaledDamage (int[] stats) {
		double[] scaling = getStatScaling();
		
		double addDmg = 0;
		for (int i = 0; i < stats.length; i++) {
			addDmg += scaling[i] * stats[i];
		}
		
		double[] dmg = getBaseAttack();
		double tldmg = getBaseDamage();
		
		for (int i = 0; i < dmg.length; i++) {
			double perc = dmg[i] / tldmg;
			
			dmg[i] += addDmg * perc;
		}
		
		return dmg;
	}
	
	/*
	 * Returns the amount of status application the weapon does after scaling.
	 * Different statuses scale with different entity stats, thereby making this abomination.
	 * 
	 * @param double[] the party member's stats
	 * @return double[] all status members after scaling
	 */
	public double[] scaledStatus (int[] stats) {
		double[] scaling = getStatScaling();
		
		double[] wamu = getBaseStatus();
		
		wamu[StatusEffects.POISON.ordinal()] *= scalingShifter(scaling[EntityStats.IST.ordinal()]);
		wamu[StatusEffects.BLEED.ordinal()] *= scalingShifter(scaling[EntityStats.DEX.ordinal()]);
		wamu[StatusEffects.BLIND.ordinal()] *= scalingShifter(scaling[EntityStats.IST.ordinal()]);
		wamu[StatusEffects.PARALYZE.ordinal()] *= scalingShifter(scaling[EntityStats.WIL.ordinal()]);
		wamu[StatusEffects.FEAR.ordinal()] *= scalingShifter(scaling[EntityStats.FTH.ordinal()]);
		wamu[StatusEffects.SLEEP.ordinal()] *= scalingShifter(scaling[EntityStats.ARC.ordinal()]);
		wamu[StatusEffects.MADNESS.ordinal()] *= scalingShifter(scaling[EntityStats.ARC.ordinal()]);
		wamu[StatusEffects.CONFUSE.ordinal()] *= scalingShifter(scaling[EntityStats.ARC.ordinal()]);
		wamu[StatusEffects.CHARM.ordinal()] *= scalingShifter(scaling[EntityStats.FTH.ordinal()]);
		
		return wamu;
	}
	
	private double scalingShifter (double input) {
		return (input / 100) + 1;
	}

	public int getPurchaseCost() {
		return purchaseCost;
	}

	public void setPurchaseCost(int purchaseCost) {
		this.purchaseCost = purchaseCost;
	}
}
