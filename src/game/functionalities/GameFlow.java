package game.functionalities;

import fileio.Input;

import java.util.ArrayList;

/** A class that keeps all the functionalities of a game. */
public class GameFlow {
    private final GameTable board;
    private final ChosenDecks chosenDecks;
    private final ChosenHeroes chosenHeroes;
    private final ArrayList<Card> handPlayerOne;
    private final ArrayList<Card> handPlayerTwo;
    private int manaPlayerOne;
    private int manaPlayerTwo;
    private int playerTurn;
    private int round;
    private int roundSwitchChecker;
    public GameFlow(final Input inputData, final int i) {

        board = new GameTable();
        //Creating the table and storing the freeze-checkers and positioning/tank references

        chosenDecks = new ChosenDecks(inputData, i);
        //Choosing the deck of each player and shuffling them

        chosenHeroes = new ChosenHeroes(inputData, i);
        //Choosing the hero of each player and storing the already-attacked checker

        handPlayerOne = new ArrayList<>();
        handPlayerTwo = new ArrayList<>();
        handPlayerOne.add(chosenDecks.getChosenDeckPlayerOne().get(0));
        handPlayerTwo.add(chosenDecks.getChosenDeckPlayerTwo().get(0));
        chosenDecks.getChosenDeckPlayerOne().remove(0);
        chosenDecks.getChosenDeckPlayerTwo().remove(0);
        //Creating the hands of the players and draw the first card of the deck for each one

        playerTurn = inputData.getGames().get(i).getStartGame().getStartingPlayer();
        round = 1;
        roundSwitchChecker = 0;
        manaPlayerOne = 1;
        manaPlayerTwo = 1;
        //Setting the variables for starting the game
    }

    public final GameTable getBoard() {
        return board;
    }

    public final ChosenDecks getChosenDecks() {
        return chosenDecks;
    }

    public final ChosenHeroes getChosenHeroes() {
        return chosenHeroes;
    }

    public final ArrayList<Card> getHandPlayerOne() {
        return handPlayerOne;
    }

    public final ArrayList<Card> getHandPlayerTwo() {
        return handPlayerTwo;
    }

    public final int getManaPlayerOne() {
        return manaPlayerOne;
    }

    public final void setManaPlayerOne(final int manaPlayerOne) {
        this.manaPlayerOne = manaPlayerOne;
    }

    public final int getManaPlayerTwo() {
        return manaPlayerTwo;
    }

    public final void setManaPlayerTwo(final int manaPlayerTwo) {
        this.manaPlayerTwo = manaPlayerTwo;
    }

    public final int getMaxManaPerRound() {
        return 10;
    }

    public final int getPlayerTurn() {
        return playerTurn;
    }

    public final void setPlayerTurn(final int playerTurn) {
        this.playerTurn = playerTurn;
    }

    public final int getRound() {
        return round;
    }

    public final void setRound(final int round) {
        this.round = round;
    }
    public final int getRoundSwitchChecker() {
        return roundSwitchChecker;
    }

    public final void setRoundSwitchChecker(final int roundSwitchChecker) {
        this.roundSwitchChecker = roundSwitchChecker;
    }
}
