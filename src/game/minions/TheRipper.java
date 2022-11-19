package game.minions;

import fileio.CardInput;
import game.functionalities.Card;
import game.functionalities.GameFlow;

public class TheRipper extends Minion {
    public TheRipper(final CardInput card) {
        super(card);
    }
    public TheRipper(final Card card) {
        super((Minion) card);
    }

    @Override
    public final void useAbility(final GameFlow game, final int row) {
    }

    @Override
    public final void useAbility(final GameFlow game, final int attackedX, final int attackedY) {
        if (game.getBoard().getTable().get(attackedX).get(attackedY).getAttackDamage() < 2) {
            game.getBoard().getTable().get(attackedX).get(attackedY).setAttackDamage(0);
        } else {
            game.getBoard().getTable().get(attackedX).get(attackedY).setAttackDamage(game
                    .getBoard().getTable().get(attackedX).get(attackedY).getAttackDamage() - 2);
        }
    }
}
