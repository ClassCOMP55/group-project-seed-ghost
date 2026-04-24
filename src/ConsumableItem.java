
public class ConsumableItem {
	private static final int VITALITY_GAINS = 10;
    private ConsumableType type;
    public ConsumableItem(ConsumableType type) {
        this.type = type;
    }

    public ConsumableType getType() {
        return type;
    }
  
    /*
     * Use on a single target (the player or mercenary)
     */
    public void use(Entity target) {
    	AudioManager.playSfxOnce("Equip A Weapon.wav");
        switch (type) {

            case HEALTH:
                // Heal 30% of target's max HP
                double healAmount = target.getHpMax() * 0.30;
                target.heal(healAmount);
                break;

            case MANA:
                // Full mana restore for target
                target.gainMana(target.getManaMax());
                break;

            case ELIXIR:
                // 40% HP + full mana restore for target
                target.heal(target.getHpMax() * 0.4);
                target.gainMana(target.getManaMax());
                break;
            
            case VITALITY:
            	// Permanently increase Max Mana and Max HP
            	target.setHpMax(target.getHpMax() + VITALITY_GAINS);
            	target.setManaMax(target.getManaMax() + VITALITY_GAINS);
            	target.heal(VITALITY_GAINS);
            	target.gainMana(VITALITY_GAINS);
        }
    }
}