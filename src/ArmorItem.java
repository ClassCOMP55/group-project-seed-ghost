
public class ArmorItem {
	private double[] incDamageMultipliers, incDamageArmor, incStatusMultipliers;
	private String weight, affinity;
	private boolean metal, cloth, magic, guarding = false;
	private int purchaseCost;
	
	public ArmorItem (boolean guardarmor) {
		setDefaults();
		
		setWeight("light");
		setAffinity("null");
		setMetal(false);
		setMagic(false);
		setCloth(false);
		guarding = true;
		
		if (guardarmor) {
			incDamageMultipliers = new double[] {0.15,0.15,0.15,0.15,0.15,0.15,0.15,0.15};
		} else {
			incDamageMultipliers = new double[] {0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
		}
	}
	
	public boolean guarding () {
		return guarding;
	}
	
	public ArmorItem () {
		setDefaults();
		
		setWeight(Chance.choose(new String[] {"light","medium","heavy"}));
		setAffinity(Chance.choose(new String[] {"null","null","null","null","null","null","null","Fire","Lightning","Magic","Holy","Ballistics","Crushing","Slashing"}));
		setMetal(Chance.coinflip(0.3));
		setMagic(Chance.coinflip(0.1));
		setCloth(Chance.coinflip(0.5));
		
		applyModifiers();
	}
	
	public ArmorItem (String weight, String affinity) {
		setDefaults();
		
		setWeight(weight);
		setAffinity(affinity);
		setMetal(Chance.coinflip(0.3));
		setMagic(Chance.coinflip(0.1));
		setCloth(Chance.coinflip(0.5));
		
		applyModifiers();
	}
	
	public ArmorItem (String weight, String affinity, boolean metal, boolean magic, boolean cloth) {
		setDefaults();
		
		setWeight(weight);
		setAffinity(affinity);
		setMetal(metal);
		setMagic(magic);
		setCloth(cloth);
		
		applyModifiers();
	}
	
	private void setDefaults () {
		incDamageMultipliers = new double[] {1,1,1,1,1,1,1,1};
		incDamageArmor       = new double[] {0,0,0,0,0,0,0,0};
		incStatusMultipliers = new double[] {1,1,1,1,1,1,1,1,1};
		
		setPurchaseCost(0);
	}
	
	public void applyModifiers () {
		switch (getWeight()) {
			case "light":
				changeDamageMultipliers(0.025);
				setCloth(true);
				setPurchaseCost(Chance.range(10, 30));
			break;
			case "medium":
				changeDamageMultipliers(0.05);
				changeArmor(1);
				setPurchaseCost(Chance.range(80, 200));
			break;
			case "heavy":
				changeDamageMultipliers(0.1);
				setMetal(true);
				changeArmor(3);
				setPurchaseCost(Chance.range(300, 500));
			break;
		}
		
		switch (getAffinity()) {
			case "Fire":
				changeDamageMultiplier(DamageType.FIRE, 0.5);
				addArmor(DamageType.FIRE, 2);
				addArmor(DamageType.HOLY, -2);
				setPurchaseCost((int) (getPurchaseCost() * Chance.range(0.8, 1.4)));
			break;
			case "Lightning":
				changeDamageMultiplier(DamageType.ELEC, 0.5);
				addArmor(DamageType.ELEC, 2);
				addArmor(DamageType.MAGIC, -2);
				setPurchaseCost((int) (getPurchaseCost() * Chance.range(0.8, 1.4)));
			break;
			case "Magic":
				changeDamageMultiplier(DamageType.MAGIC, 0.5);
				addArmor(DamageType.MAGIC, 2);
				addArmor(DamageType.ELEC, -2);
				setPurchaseCost((int) (getPurchaseCost() * Chance.range(1.2, 1.4)));
			break;
			case "Holy":
				changeDamageMultiplier(DamageType.HOLY, 0.5);
				addArmor(DamageType.HOLY, 2);
				addArmor(DamageType.FIRE, -2);
				setPurchaseCost((int) (getPurchaseCost() * Chance.range(0.8, 2.0)));
			break;
			case "Ballistics":
				changeDamageMultiplier(DamageType.PIERCE, 0.5);
				addArmor(DamageType.PIERCE, 2);
				addArmor(DamageType.CRUSH, -2);
				setPurchaseCost((int) (getPurchaseCost() * Chance.range(1.0, 1.4)));
			break;
			case "Slashing":
				changeDamageMultiplier(DamageType.SLASH, 0.5);
				addArmor(DamageType.SLASH, 2);
				addArmor(DamageType.PIERCE, -2);
				setPurchaseCost((int) (getPurchaseCost() * Chance.range(1.0, 1.4)));
			break;
			case "Crushing":
				changeDamageMultiplier(DamageType.CRUSH, 0.5);
				addArmor(DamageType.CRUSH, 2);
				addArmor(DamageType.SLASH, -2);
				setPurchaseCost((int) (getPurchaseCost() * Chance.range(0.5, 1.2)));
			break;
		}
		
		if (isMetal()) {
			changeDamageMultiplier(DamageType.SLASH, 0.1);
			changeDamageMultiplier(DamageType.PIERCE, 0.1);
			if (getAffinity() == "Lightning") {
				changeDamageMultiplier(DamageType.ELEC, -0.5);
			} else {
				changeDamageMultiplier(DamageType.ELEC, -1.5);
			}
			addArmor(DamageType.ELEC, -3);
			addArmor(DamageType.HOLY, 2);
			
			setPurchaseCost(getPurchaseCost() + Chance.range(50, 100));
		}
		
		if (isCloth()) {
			changeDamageMultipliers(0.05);
			if (getAffinity() == "Fire") {
				changeDamageMultiplier(DamageType.FIRE, -0.5);
			} else {
				changeDamageMultiplier(DamageType.FIRE, -1.5);
			}
			addArmor(DamageType.FIRE, -3);
			addArmor(DamageType.MAGIC, 2);
			setPurchaseCost(getPurchaseCost() + Chance.range(-200, 100));
		}
	}
	
	private void changeDamageMultiplier (DamageType type, double amt) {
		incDamageMultipliers[type.ordinal()] -= amt;
		if (incDamageMultipliers[type.ordinal()] < 0) {
			incDamageMultipliers[type.ordinal()] = 0;
		}
	}
	
	private void changeDamageMultipliers (double amt) {
		if (isMagic()) {
			changeAllDamageMultipliers(amt);
		} else {
			changePhysicalDamageMultipliers(amt);
		}
	}
	
	private void changeArmor (double amt) {
		if (isMagic()) {
			setAllArmor(amt);
		} else {
			changePhysicalArmor(amt);
		}
	}
	
	private void addArmor (DamageType type, double amt) {
		incDamageArmor[type.ordinal()] += amt;
	}
	
	private void setAllArmor (double amt) {
		for (int i = 0; i < incDamageArmor.length; i++) {
			incDamageArmor[i] += amt;
		}
	}
	
	private void changePhysicalArmor (double amt) {
		incDamageArmor[DamageType.CRUSH.ordinal()]  += amt;
		incDamageArmor[DamageType.SLASH.ordinal()]  += amt;
		incDamageArmor[DamageType.PIERCE.ordinal()] += amt;
		incDamageArmor[DamageType.BLAST.ordinal()]  += amt * 0.5;
	}
	
	private void changePhysicalDamageMultipliers (double amt) {
		incDamageMultipliers[DamageType.CRUSH.ordinal()]  -= amt;
		incDamageMultipliers[DamageType.SLASH.ordinal()]  -= amt;
		incDamageMultipliers[DamageType.PIERCE.ordinal()] -= amt;
		incDamageMultipliers[DamageType.BLAST.ordinal()]  -= amt * 0.5;
	}
	
	private void changeAllDamageMultipliers (double amt) {
		for (int i = 0; i < incDamageMultipliers.length; i++) {
			incDamageMultipliers[i] -= amt;
		}
	}
	
	public String toString () {
		String name = "";
		
		if (isCloth()) {
			name += "Padded ";
		}
		if (isMetal()) {
			switch (getAffinity()) {
				case "Holy":
					name += "Golden ";
				break;
				case "Lightning":
					name += "Bronze ";
				break;
				case "Magic":
					name += "Mythril ";
				break;
				case "Crushing":
					name += "Adamantine ";
				break;
				case "Fire":
					name += "Iron ";
				break;
				default:
					name += "Steel ";
				break;
			}
		}
		
		switch (getWeight()) {
			case "light":
				name += "Robe";
			break;
			case "medium":
				name += "Brigandine";
			break;
			case "heavy":
				name += "Platemail";
			break;
			default:
				name += "Chestpiece";
			break;
		}
		
		if (getAffinity() != "null") {
			name += " of " + getAffinity() + " Protection";
		}
		if (isMagic()) {
			name += "+";
		}
		
		return name;
	}
	
	public void printStats () {
		System.out.println(this);
		
		String rtn = "";
		
		double tst = 1 - incDamageMultipliers[DamageType.SLASH.ordinal()];
		int tst2 = (int)incDamageArmor[DamageType.SLASH.ordinal()];
		tst = (double)Math.round(tst * 1000) / 1000;
		if ((tst != 0) || (tst2 != 0)) { rtn += "DMG Slash   : " + tst + " (" + tst2 + " flat) \n";}
		tst = 1 - incDamageMultipliers[DamageType.CRUSH.ordinal()];
		tst2 = (int)incDamageArmor[DamageType.CRUSH.ordinal()];
		tst = (double)Math.round(tst * 1000) / 1000;
		if ((tst != 0) || (tst2 != 0)) { rtn += "DMG Crush   : " + tst + " (" + tst2 + " flat) \n";}
		tst = 1 - incDamageMultipliers[DamageType.PIERCE.ordinal()];
		tst2 = (int)incDamageArmor[DamageType.PIERCE.ordinal()];
		tst = (double)Math.round(tst * 1000) / 1000;
		if ((tst != 0) || (tst2 != 0)) { rtn += "DMG Pierce  : " + tst + " (" + tst2 + " flat) \n";}
		tst = 1 - incDamageMultipliers[DamageType.BLAST.ordinal()];
		tst2 = (int)incDamageArmor[DamageType.BLAST.ordinal()];
		tst = (double)Math.round(tst * 1000) / 1000;
		if ((tst != 0) || (tst2 != 0)) { rtn += "DMG Blast   : " + tst + " (" + tst2 + " flat) \n";}
		tst = 1 - incDamageMultipliers[DamageType.FIRE.ordinal()];
		tst2 = (int)incDamageArmor[DamageType.FIRE.ordinal()];
		tst = (double)Math.round(tst * 1000) / 1000;
		if ((tst != 0) || (tst2 != 0)) { rtn += "DMG Fire    : " + tst + " (" + tst2 + " flat) \n";}
		tst = 1 - incDamageMultipliers[DamageType.HOLY.ordinal()];
		tst2 = (int)incDamageArmor[DamageType.HOLY.ordinal()];
		tst = (double)Math.round(tst * 1000) / 1000;
		if ((tst != 0) || (tst2 != 0)) { rtn += "DMG Holy    : " + tst + " (" + tst2 + " flat) \n";}
		tst = 1 - incDamageMultipliers[DamageType.ELEC.ordinal()];
		tst2 = (int)incDamageArmor[DamageType.ELEC.ordinal()];
		tst = (double)Math.round(tst * 1000) / 1000;
		if ((tst != 0) || (tst2 != 0)) { rtn += "DMG Electric: " + tst + " (" + tst2 + " flat) \n";}
		tst = 1 - incDamageMultipliers[DamageType.MAGIC.ordinal()];
		tst2 = (int)incDamageArmor[DamageType.MAGIC.ordinal()];
		tst = (double)Math.round(tst * 1000) / 1000;
		if ((tst != 0) || (tst2 != 0)) { rtn += "DMG Magic   : " + tst + " (" + tst2 + " flat) \n";}
		
		System.out.println(rtn);
	}
	
	public double calculateDamage (double[] incDamage) {
		double rtn = 0.0;
		
		for (int i = 0; i < incDamage.length; i++) {
			rtn += (incDamage[i] * incDamageMultipliers[i]) - incDamageArmor[i];
		}
		
		return rtn;
	}
	
	public double[] calculateStatus (double[] initStatus) {
		for (int i = 0; i < initStatus.length; i++) {
			initStatus[i] *= incStatusMultipliers[i];
		}
		
		return initStatus;
	}
	
	public double[] getIncDamageMultipliers() {
		return incDamageMultipliers;
	}
	public void setIncDamageMultipliers(double[] incDamageMultipliers) {
		this.incDamageMultipliers = incDamageMultipliers;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public boolean isMetal() {
		return metal;
	}
	public void setMetal(boolean metal) {
		this.metal = metal;
	}
	public boolean isCloth() {
		return cloth;
	}
	public void setCloth(boolean cloth) {
		this.cloth = cloth;
	}
	public boolean isMagic() {
		return magic;
	}
	public void setMagic(boolean magic) {
		this.magic = magic;
	}
	public double[] getIncStatusMultipliers() {
		return incStatusMultipliers;
	}
	public void setIncStatusMultipliers(double[] incStatusMultipliers) {
		this.incStatusMultipliers = incStatusMultipliers;
	}
	public String getAffinity() {
		return affinity;
	}
	public void setAffinity(String affinity) {
		this.affinity = affinity;
	}

	public int getPurchaseCost() {
		return purchaseCost;
	}

	public void setPurchaseCost(int purchaseCost) {
		this.purchaseCost = purchaseCost;
	}
	
}
