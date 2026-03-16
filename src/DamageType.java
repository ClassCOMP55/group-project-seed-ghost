
public enum DamageType {
CRUSH, SLASH, PIERCE, BLAST, FIRE, ELEC, HOLY, MAGIC;
	
	public String toString() {
		switch(this) {
			case CRUSH: return "crush";
			case SLASH: return "slash";
			case PIERCE: return "pierce";
			case BLAST: return "blast";
			case FIRE: return "fire";
			case ELEC: return "elec";
			case HOLY: return "holy";
			case MAGIC: return "magic";
		}
		return "n/a";
	}
}
