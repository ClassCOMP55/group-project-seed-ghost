
public enum StatusEffects {

POISON, BLEED, BLIND, PARALYZE, FEAR, SLEEP, MADNESS, CONFUSE, CHARM;
	
	public String toString() {
		switch(this) {
			case POISON: return "poison";
			case BLEED: return "bleed";
			case BLIND: return "blind";
			case PARALYZE: return "paralyze";
			case FEAR: return "fear";
			case SLEEP: return "sleep";
			case MADNESS: return "madness";
			case CONFUSE: return "confuse";
			case CHARM: return "charm";
		}
		return "n/a";
	}
}
