package game.functionalities;

import fileio.Input;
import game.environment.Firestorm;
import game.environment.HeartHound;
import game.environment.Winterfell;

import game.minions.TheRipper;
import game.minions.Miraj;
import game.minions.Disciple;
import game.minions.TheCursedOne;
import game.minions.Berserker;
import game.minions.Sentinel;
import game.minions.Warden;
import game.minions.Goliath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class ChosenDecks {
    private final ArrayList<Card> chosenDeckPlayerOne;
    private final ArrayList<Card> chosenDeckPlayerTwo;

    public ChosenDecks(final Input inputData, final int i) {
        chosenDeckPlayerOne = new ArrayList<>();
        int playerOneDeckIdx = inputData.getGames().get(i).getStartGame().getPlayerOneDeckIdx();

        for (int j = 0;
             j < inputData.getPlayerOneDecks().getDecks().get(playerOneDeckIdx).size(); j++) {

            Card currentCard = switch (inputData.getPlayerOneDecks()
                    .getDecks().get(playerOneDeckIdx).get(j).getName()) {
                case "Firestorm" ->
                        new Firestorm(inputData.getPlayerOneDecks()
                                .getDecks().get(playerOneDeckIdx).get(j));
                case "Heart Hound" ->
                        new HeartHound(inputData.getPlayerOneDecks()
                                .getDecks().get(playerOneDeckIdx).get(j));
                case "Winterfell" ->
                        new Winterfell(inputData.getPlayerOneDecks()
                                .getDecks().get(playerOneDeckIdx).get(j));
                case "Warden" ->
                        new Warden(inputData.getPlayerOneDecks().getDecks()
                                .get(playerOneDeckIdx).get(j));
                case "The Ripper" ->
                        new TheRipper(inputData.getPlayerOneDecks().getDecks()
                                .get(playerOneDeckIdx).get(j));
                case "Miraj" ->
                        new Miraj(inputData.getPlayerOneDecks().getDecks()
                                .get(playerOneDeckIdx).get(j));
                case "The Cursed One" ->
                        new TheCursedOne(inputData.getPlayerOneDecks().getDecks()
                                .get(playerOneDeckIdx).get(j));
                case "Disciple" ->
                        new Disciple(inputData.getPlayerOneDecks().getDecks()
                                .get(playerOneDeckIdx).get(j));
                case "Sentinel" ->
                        new Sentinel(inputData.getPlayerOneDecks().getDecks()
                                .get(playerOneDeckIdx).get(j));
                case "Berserker" ->
                        new Berserker(inputData.getPlayerOneDecks().getDecks()
                                .get(playerOneDeckIdx).get(j));
                case "Goliath" ->
                        new Goliath(inputData.getPlayerOneDecks().getDecks()
                                .get(playerOneDeckIdx).get(j));
                default -> null;
            };
            chosenDeckPlayerOne.add(currentCard);
        }

        chosenDeckPlayerTwo = new ArrayList<>();
        int playerTwoDeckIdx = inputData.getGames().get(i).getStartGame().getPlayerTwoDeckIdx();

        for (int j = 0;
             j < inputData.getPlayerTwoDecks().getDecks().get(playerTwoDeckIdx).size(); j++) {

            Card currentCard = switch (inputData.getPlayerTwoDecks().getDecks()
                    .get(playerTwoDeckIdx).get(j).getName()) {

                case "Firestorm" ->
                        new Firestorm(inputData.getPlayerTwoDecks().getDecks()
                                .get(playerTwoDeckIdx).get(j));
                case "Heart Hound" ->
                        new HeartHound(inputData.getPlayerTwoDecks().getDecks()
                                .get(playerTwoDeckIdx).get(j));
                case "Winterfell" ->
                        new Winterfell(inputData.getPlayerTwoDecks().getDecks()
                                .get(playerTwoDeckIdx).get(j));
                case "Warden" ->
                        new Warden(inputData.getPlayerTwoDecks().getDecks()
                                .get(playerTwoDeckIdx).get(j));
                case "The Ripper" ->
                        new TheRipper(inputData.getPlayerTwoDecks().getDecks()
                                .get(playerTwoDeckIdx).get(j));
                case "Miraj" ->
                        new Miraj(inputData.getPlayerTwoDecks().getDecks()
                                .get(playerTwoDeckIdx).get(j));
                case "The Cursed One" ->
                        new TheCursedOne(inputData.getPlayerTwoDecks()
                                .getDecks().get(playerTwoDeckIdx).get(j));
                case "Disciple" ->
                        new Disciple(inputData.getPlayerTwoDecks().getDecks()
                                .get(playerTwoDeckIdx).get(j));
                case "Sentinel" ->
                        new Sentinel(inputData.getPlayerTwoDecks().getDecks()
                                .get(playerTwoDeckIdx).get(j));
                case "Berserker" ->
                        new Berserker(inputData.getPlayerTwoDecks().getDecks()
                                .get(playerTwoDeckIdx).get(j));
                case "Goliath" ->
                        new Goliath(inputData.getPlayerTwoDecks().getDecks()
                                .get(playerTwoDeckIdx).get(j));
                default -> null;
            };
            chosenDeckPlayerTwo.add(currentCard);
        }
        //Assigning class to each card and add it to chosen decks of the players

        Random rnd = new Random(inputData.getGames().get(i).getStartGame().getShuffleSeed());
        Collections.shuffle(chosenDeckPlayerOne, rnd);
        rnd.setSeed(inputData.getGames().get(i).getStartGame().getShuffleSeed());
        Collections.shuffle(chosenDeckPlayerTwo, rnd);
        //Shuffling the chosen decks
    }

    public final ArrayList<Card> getChosenDeckPlayerOne() {
        return chosenDeckPlayerOne;
    }

    public final ArrayList<Card> getChosenDeckPlayerTwo() {
        return chosenDeckPlayerTwo;
    }
}
