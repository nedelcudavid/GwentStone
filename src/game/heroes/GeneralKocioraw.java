package game.heroes;

import fileio.CardInput;
import game.functionalities.GameFlow;

public class GeneralKocioraw extends Hero {
    public GeneralKocioraw(final CardInput card) {
        super(card);
    }

    @Override
    public final void useAbility(final GameFlow game, final int row) {
        for (int idx = 0; idx < game.getBoard().getTable().get(row).size(); idx++) {
            game.getBoard().getTable().get(row).get(idx).setAttackDamage(game.getBoard()
                    .getTable().get(row).get(idx).getAttackDamage() + 1);
        }
    }

    @Override
    public final void useAbility(final GameFlow game, final int attackedX, final int attackedY) {
    }
}
