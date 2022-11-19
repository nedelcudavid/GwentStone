package game.heroes;

import fileio.CardInput;
import game.functionalities.Card;
import game.functionalities.HasAbility;

public abstract class Hero extends Card implements HasAbility {
    private int health;

    public Hero(final CardInput card) {
        super(card);
        this.health = 30;
    }

    public final int getHealth() {
        return health;
    }

    public final void setHealth(final int health) {
        this.health = health;
    }
}
