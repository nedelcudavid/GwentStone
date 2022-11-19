package game.minions;

import fileio.CardInput;
import game.functionalities.GameFlow;

public class Warden extends Minion {
    public Warden(final CardInput card) {
        super(card);
    }

    @Override
    public final void useAbility(final GameFlow game, final int row) {
    }

    @Override
    public final void useAbility(final GameFlow game, final int attackedX, final int attackedY) {
    }
}
