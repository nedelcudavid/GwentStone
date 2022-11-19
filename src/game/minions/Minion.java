package game.minions;

import fileio.CardInput;
import game.functionalities.Card;
import game.functionalities.HasAbility;

public abstract class Minion extends Card implements HasAbility {
    private int attackDamage;
    private int health;

    public Minion(final CardInput card) {
        super(card);
        this.attackDamage = card.getAttackDamage();
        this.health = card.getHealth();
    }

    public Minion(final Minion card) {
        super(card);
        this.attackDamage = card.getAttackDamage();
        this.health = card.getHealth();
    }

    public final int getAttackDamage() {
        return attackDamage;
    }

    public final void setAttackDamage(final int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public final int getHealth() {
        return health;
    }

    public final void setHealth(final int health) {
        this.health = health;
    }
}
