package game.functionalities;

public interface HasAbility {

    /** Ability of environmental cards and heros */
    void useAbility(GameFlow game, int row);

    /** Ability of legendary minions and druids*/
    void useAbility(GameFlow game, int attackedX, int attackedY);
}
