
public class ConsumableItem {

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
                // Full HP + full mana restore for target
                target.heal(target.getHpMax());
                target.gainMana(target.getManaMax());
                break;
        }
    }
}