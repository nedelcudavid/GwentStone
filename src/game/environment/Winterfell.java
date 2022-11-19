package game.environment;

import fileio.CardInput;
import game.functionalities.GameFlow;

public class Winterfell extends Environment {
    public Winterfell(final CardInput card) {
        super(card);
    }

    @Override
    public final void useAbility(final GameFlow game, final int row) {
        for (int i = 0; i < game.getBoard().getTable().get(row).size(); i++) {
            if (game.getBoard().getTable().get(row).get(i) != null) {
                game.getBoard().getFrozenCoords()[row][i] = 1;
            }
        }
    }

    @Override
    public void useAbility(final GameFlow game, final int attackedX, final int attackedY) {
    }
}
