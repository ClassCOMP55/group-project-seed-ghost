
public enum EntityStats {
	STR, DEX, PRC, IST, CON, WIL, FTH, ARC;
	
	public String toString(){
		switch(this) {
			case STR: return "strength";
			case DEX: return "dexterity";
			case PRC: return "precision";
			case IST: return "instinct";
			case CON: return "constitution";
			case WIL: return "willpower";
			case FTH: return "faith";
			case ARC: return "arcane";
		}
		return "n/a";
	}
}
