
public enum ConsumableType {
	HEALTH, MANA, ELIXIR, VITALITY;
	
	public String toString() {
		switch(this){
			case HEALTH : return "Heal 30% of target's max HP";
			case MANA : return "Full mana restore for target";
			case ELIXIR : return "40% HP + full mana restore for target";
			case VITALITY: return "Permanently increase Max Mana and Max HP";
		}
		return "n/a";
	}

}
