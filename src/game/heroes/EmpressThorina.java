package game.heroes;

import fileio.CardInput;
import game.functionalities.GameFlow;

public class EmpressThorina extends Hero {
    public EmpressThorina(final CardInput card) {
        super(card);
    }

    @Override
    public final void useAbility(final GameFlow game, final int row) {
        int biggestHP = 0;
        int destroyIdx = 0;
        for (int idx = 0; idx < game.getBoard().getTable().get(row).size(); idx++) {
            if (game.getBoard().getTable().get(row).get(idx).getHealth() > biggestHP) {
                biggestHP = game.getBoard().getTable().get(row).get(idx).getHealth();
                destroyIdx = idx;
            }
        }
        game.getBoard().getTable().get(row).remove(destroyIdx);
    }

    @Override
    public final void useAbility(final GameFlow game, final int attackedX, final int attackedY) {
    }
}
