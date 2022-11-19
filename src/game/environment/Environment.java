package game.environment;

import fileio.CardInput;
import game.functionalities.Card;
import game.functionalities.HasAbility;

public abstract class Environment extends Card implements HasAbility {
    public Environment(final CardInput card) {
        super(card);
    }
}
