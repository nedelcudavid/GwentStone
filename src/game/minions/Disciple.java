package game.minions;

import fileio.CardInput;
import game.functionalities.Card;
import game.functionalities.GameFlow;

public class Disciple extends Minion {
    public Disciple(final CardInput card) {
        super(card);
    }

    public Disciple(final Card card) {
        super((Minion) card);
    }

    @Override
    public final void useAbility(final GameFlow game, final int row) {
    }

    @Override
    public final void useAbility(final GameFlow game, final int attackedX, final int attackedY) {
    game.getBoard().getTable().get(attackedX).get(attackedY)
            .setHealth(game.getBoard().getTable().get(attackedX).get(attackedY).getHealth() + 2);
    }
}
