package game.minions;

import fileio.CardInput;
import game.functionalities.Card;
import game.functionalities.GameFlow;

public class TheCursedOne extends Minion {
    public TheCursedOne(final CardInput card) {
        super(card);
    }

    public TheCursedOne(final Card card) {
        super((Minion) card);
    }

    @Override
    public final void useAbility(final GameFlow game, final int row) {
    }

    @Override
    public final void useAbility(final GameFlow game, final int attackedX, final int attackedY) {
        int aux = game.getBoard().getTable().get(attackedX).get(attackedY).getHealth();
        game.getBoard().getTable().get(attackedX).get(attackedY).setHealth(game.getBoard()
                .getTable().get(attackedX).get(attackedY).getAttackDamage());
        game.getBoard().getTable().get(attackedX).get(attackedY).setAttackDamage(aux);
    }
}
