
public enum DamageType {
CRUSH, SLASH, PEIRCE, BLAST, FIRE, ELEC, HOLY, MAGIC;
	
	public String toString() {
		switch(this) {
			case CRUSH: return "crush";
			case SLASH: return "slash";
			case PEIRCE: return "peirce";
			case BLAST: return "blast";
			case FIRE: return "fire";
			case ELEC: return "elec";
			case HOLY: return "holy";
			case MAGIC: return "magic";
		}
		return "n/a";
	}
}
