
public enum ConsumableType {
	HEALTH, MANA, ELIXIR, VITALITY;
	
	public String toString() {
		switch(this){
			case HEALTH : return "health";
			case MANA : return "mana";
			case ELIXIR : return "elixir";
			case VITALITY: return "vitality";
		}
		return "n/a";
	}

}
