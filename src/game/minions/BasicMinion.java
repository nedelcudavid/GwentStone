package game.minions;
import game.functionalities.Card;
import game.functionalities.GameFlow;

public class BasicMinion extends Minion {
    public BasicMinion(final Card card) {
        super((Minion) card);
    }

    @Override
    public void useAbility(final GameFlow game, final int row) {
    }

    @Override
    public void useAbility(final GameFlow game, final int attackedX, final int attackedY) {
    }
}

