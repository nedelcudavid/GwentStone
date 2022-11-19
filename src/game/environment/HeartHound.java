package game.environment;

import fileio.CardInput;
import game.functionalities.GameFlow;

public class HeartHound extends Environment {
    public HeartHound(final CardInput card) {
        super(card);
    }

    @Override
    public final void useAbility(final GameFlow game, final int row) {
        int biggestHealth = 0;
        int stealIdx = 0;
        for (int i = 0; i < game.getBoard().getTable().get(row).size(); i++) {
            if (game.getBoard().getTable().get(row).get(i).getHealth() > biggestHealth) {
                stealIdx = i;
            }
        }
        game.getBoard().getTable().get(Math.abs(row - 3)).add(game.getBoard()
                .getTable().get(row).get(stealIdx));
        game.getBoard().getTable().get(row).remove(stealIdx);
    }

    @Override
    public void useAbility(final GameFlow game, final int attackedX, final int attackedY) {
    }
}
