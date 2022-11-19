package game.minions;

import fileio.CardInput;
import game.functionalities.Card;
import game.functionalities.GameFlow;

public class Miraj extends Minion {
    public Miraj(final CardInput card) {
        super(card);
    }

    public Miraj(final Card card) {
        super((Minion) card);
    }

    @Override
    public final void useAbility(final GameFlow game, final int row) {
    }

    @Override
    public final void useAbility(final GameFlow game, final int attackedX, final int attackedY) {
        int aux = getHealth();
        setHealth(game.getBoard().getTable().get(attackedX).get(attackedY).getHealth());
        game.getBoard().getTable().get(attackedX).get(attackedY).setHealth(aux);
    }
}
