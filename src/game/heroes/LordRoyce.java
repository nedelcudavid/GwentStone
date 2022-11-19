package game.heroes;

import fileio.CardInput;
import game.functionalities.GameFlow;

public class LordRoyce extends Hero {
    public LordRoyce(final CardInput card) {
        super(card);
    }

    @Override
    public final void useAbility(final GameFlow game, final int row) {
        int biggestATK = 0;
        int freezeIdx = 0;
        for (int idx = 0; idx < game.getBoard().getTable().get(row).size(); idx++) {
            if (game.getBoard().getTable().get(row).get(idx).getAttackDamage() > biggestATK) {
                biggestATK = game.getBoard().getTable().get(row).get(idx).getAttackDamage();
                freezeIdx = idx;
            }
        }
        game.getBoard().getFrozenCoords()[row][freezeIdx] = 1;
    }

    @Override
    public final void useAbility(final GameFlow game, final int attackedX, final int attackedY) {
    }
}
