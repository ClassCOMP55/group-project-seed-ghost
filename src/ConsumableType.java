
public enum ConsumableType {
	HEALTH, MANA, ELIXIR;
	
	public String toString() {
		switch(this){
			case HEALTH : return "health";
			case MANA : return "mana";
			case ELIXIR : return "elixir";
		}
		return "n/a";
	}

}
