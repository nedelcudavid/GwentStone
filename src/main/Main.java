package main;

import checker.Checker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import checker.CheckerConstants;
import com.fasterxml.jackson.databind.node.ObjectNode;

import fileio.Coordinates;
import fileio.Input;
import game.functionalities.Card;
import game.functionalities.GameFlow;
import game.environment.Environment;
import game.environment.HeartHound;

import game.heroes.EmpressThorina;
import game.heroes.GeneralKocioraw;
import game.heroes.KingMudface;
import game.heroes.LordRoyce;
import game.minions.Disciple;
import game.minions.Minion;
import game.minions.Miraj;
import game.minions.TheRipper;
import game.minions.TheCursedOne;
import  game.minions.BasicMinion;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implementation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * DO NOT MODIFY MAIN METHOD
     * Call the checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(CheckerConstants.TESTS_PATH);
        Path path = Paths.get(CheckerConstants.RESULT_PATH);

        if (Files.exists(path)) {
            File resultFile = new File(String.valueOf(path));
            for (File file : Objects.requireNonNull(resultFile.listFiles())) {
                file.delete();
            }
            resultFile.delete();
        }
        Files.createDirectories(path);

        for (File file : Objects.requireNonNull(directory.listFiles())) {
            String filepath = CheckerConstants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getName(), filepath);
            }
        }

        Checker.calculateScore();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Input inputData = objectMapper.readValue(new File(CheckerConstants.TESTS_PATH + filePath1),
                Input.class);

        ArrayNode output = objectMapper.createArrayNode();

        //Entry point of implementation:

        int playerOneWins = 0;
        int playerTwoWins = 0;
        int totalGamesPlayed = 0;

        //We start by adding a for to go through each game (in case there are multiple ones)
        for (int i = 0; i < inputData.getGames().size(); i++) {

            GameFlow game = new GameFlow(inputData, i);
            totalGamesPlayed++;
            //Making all preparation for starting the game

            //The round starts and the commands (from input) are called one by one through a for
            for (int j = 0; j < inputData.getGames().get(i).getActions().size(); j++) {

                ObjectNode action = objectMapper.createObjectNode();

                String command = inputData.getGames().get(i).getActions().get(j).getCommand();
                int handIdx = inputData.getGames().get(i).getActions().get(j).getHandIdx();
                int cardAttackerX = 0;
                int cardAttackerY = 0;
                int cardAttackedX = 0;
                int cardAttackedY = 0;
                Coordinates attackerCoords =
                        inputData.getGames().get(i).getActions().get(j).getCardAttacker();
                Coordinates attackedCoords =
                        inputData.getGames().get(i).getActions().get(j).getCardAttacked();

                if (attackerCoords != null) {
                    cardAttackerX = inputData.getGames().get(i).getActions().get(j)
                            .getCardAttacker().getX();
                    cardAttackerY = inputData.getGames().get(i).getActions().get(j)
                            .getCardAttacker().getY();
                }
                if (attackedCoords != null) {
                    cardAttackedX = inputData.getGames().get(i).getActions().get(j)
                            .getCardAttacked().getX();
                    cardAttackedY = inputData.getGames().get(i).getActions().get(j)
                            .getCardAttacked().getY();
                }
                int affectedRow = inputData.getGames().get(i).getActions().get(j).getAffectedRow();
                int playerIdx = inputData.getGames().get(i).getActions().get(j).getPlayerIdx();
                int x = inputData.getGames().get(i).getActions().get(j).getX();
                int y = inputData.getGames().get(i).getActions().get(j).getY();
                //Extracting all inputs in order to use them easier

                //We get the commands one by one and implement it
                switch (command) {
                    case "getPlayerDeck" -> {
                        action.put("command", command);
                        if (playerIdx == 1) {
                            action.put("playerIdx", 1);
                            ArrayList<Card> deepCopyDeck =
                                    new ArrayList<>(game.getChosenDecks().getChosenDeckPlayerOne());
                            action.putPOJO("output", deepCopyDeck);
                        } else {
                            action.put("playerIdx", 2);
                            ArrayList<Card> deepCopyDeck =
                                    new ArrayList<>(game.getChosenDecks().getChosenDeckPlayerTwo());
                            action.putPOJO("output", deepCopyDeck);
                        }
                        output.add(action);
                    }

                    case "getPlayerHero" -> {
                        action.put("command", command);
                        ObjectNode heroForDisplay = objectMapper.createObjectNode();
                        if (playerIdx == 1) {
                            action.put("playerIdx", 1);
                            heroForDisplay.put("mana",
                                    game.getChosenHeroes().getHeroPlayerOne().getMana());
                            heroForDisplay.put("description",
                                    game.getChosenHeroes().getHeroPlayerOne().getDescription());
                            heroForDisplay.putPOJO("colors",
                                    game.getChosenHeroes().getHeroPlayerOne().getColors());
                            heroForDisplay.put("name",
                                    game.getChosenHeroes().getHeroPlayerOne().getName());
                            heroForDisplay.put("health",
                                    game.getChosenHeroes().getHeroPlayerOne().getHealth());
                        } else {
                            action.put("playerIdx", 2);
                            heroForDisplay.put("mana",
                                    game.getChosenHeroes().getHeroPlayerTwo().getMana());
                            heroForDisplay.put("description",
                                    game.getChosenHeroes().getHeroPlayerTwo().getDescription());
                            heroForDisplay.putPOJO("colors",
                                    game.getChosenHeroes().getHeroPlayerTwo().getColors());
                            heroForDisplay.put("name",
                                    game.getChosenHeroes().getHeroPlayerTwo().getName());
                            heroForDisplay.put("health",
                                    game.getChosenHeroes().getHeroPlayerTwo().getHealth());
                        }
                        action.putPOJO("output", heroForDisplay);
                        output.add(action);
                    }
                    case "getPlayerTurn" -> {
                        action.put("command", command);
                        action.put("output", game.getPlayerTurn());
                        output.add(action);
                    }
                    case "endPlayerTurn" -> {
                        if (game.getPlayerTurn() == 1) {
                            game.setPlayerTurn(2);
                            for (int row = 2; row < game.getBoard().getMaxRows(); row++) {
                                for (int column = 0;
                                     column < game.getBoard().getMaxColumns(); column++) {
                                    game.getBoard().getFrozenCoords()[row][column] = 0;
                                }
                            }
                            game.getChosenHeroes().getHeroAlreadyAttacked()[1] = 0;
                        } else {
                            game.setPlayerTurn(1);
                            for (int row = 0; row < 2; row++) {
                                for (int column = 0;
                                     column < game.getBoard().getMaxColumns(); column++) {
                                    game.getBoard().getFrozenCoords()[row][column] = 0;
                                }
                            }
                            game.getChosenHeroes().getHeroAlreadyAttacked()[2] = 0;
                        }
                        /* Player turn changes, frozen and already-attack checkers are reset
                        to 0 for minions and hero
                         */

                        if (game.getRoundSwitchChecker() == 1) {
                            game.setRoundSwitchChecker(0);
                            game.setRound(game.getRound() + 1);
                            if (game.getChosenDecks().getChosenDeckPlayerOne().size() > 0) {
                                game.getHandPlayerOne().add(game.getChosenDecks()
                                        .getChosenDeckPlayerOne().get(0));
                                game.getChosenDecks().getChosenDeckPlayerOne().remove(0);
                            }
                            if (game.getChosenDecks().getChosenDeckPlayerTwo().size() > 0) {
                                game.getHandPlayerTwo().add(game.getChosenDecks()
                                        .getChosenDeckPlayerTwo().get(0));
                                game.getChosenDecks().getChosenDeckPlayerTwo().remove(0);
                            }
                            if (game.getRound() < game.getMaxManaPerRound()) {
                                game.setManaPlayerOne(game.getManaPlayerOne() + game.getRound());
                                game.setManaPlayerTwo(game.getManaPlayerTwo() + game.getRound());
                            } else {
                                game.setManaPlayerOne(game.getManaPlayerOne()
                                        + game.getMaxManaPerRound());
                                game.setManaPlayerTwo(game.getManaPlayerTwo()
                                        + game.getMaxManaPerRound());
                            }
                        } else {
                            game.setRoundSwitchChecker(game.getRoundSwitchChecker() + 1);
                        }
                        //Round changes, card is given from deck and mana is given to the players
                    }
                    case "placeCard" -> {
                        switch (game.getPlayerTurn()) {
                            case 1:
                                if (game.getHandPlayerOne().get(handIdx) instanceof Environment) {
                                    action.put("command", command);
                                    action.put("handIdx", handIdx);
                                    action.put("error",
                                            "Cannot place environment card on table.");
                                    output.add(action);
                                } else if (game.getHandPlayerOne().get(handIdx).getMana()
                                        > game.getManaPlayerOne()) {
                                    action.put("command", command);
                                    action.put("handIdx", handIdx);
                                    action.put("error",
                                            "Not enough mana to place card on table.");
                                    output.add(action);
                                } else if (game.getBoard().getPositionBack()
                                        .contains(game.getHandPlayerOne().get(handIdx).getName())
                                        && game.getBoard().getTable().get(3).size()
                                        == game.getBoard().getMaxColumns()) {
                                    action.put("command", command);
                                    action.put("handIdx", handIdx);
                                    action.put("error",
                                            "Cannot place card on table since row is full.");
                                    output.add(action);
                                } else if (game.getBoard().getPositionFront()
                                        .contains(game.getHandPlayerOne().get(handIdx).getName())
                                        && game.getBoard().getTable().get(2).size()
                                        == game.getBoard().getMaxColumns()) {
                                    action.put("command", command);
                                    action.put("handIdx", handIdx);
                                    action.put("error",
                                            "Cannot place card on table since row is full.");
                                    output.add(action);
                                } else if (game.getBoard().getPositionBack()
                                        .contains(game.getHandPlayerOne().get(handIdx).getName())) {
                                    Minion cardForPlacing =
                                            specifyingTypeOfMinion(inputData, i, game, j, 1);
                                    game.getBoard().getTable().get(3).add(cardForPlacing);
                                    game.setManaPlayerOne(game.getManaPlayerOne()
                                            - game.getHandPlayerOne().get(handIdx).getMana());
                                    game.getHandPlayerOne().remove(handIdx);
                                } else {
                                    Minion cardForPlacing =
                                            specifyingTypeOfMinion(inputData, i, game, j, 1);
                                    game.getBoard().getTable().get(2).add(cardForPlacing);
                                    game.setManaPlayerOne(game.getManaPlayerOne()
                                            - game.getHandPlayerOne().get(handIdx).getMana());
                                    game.getHandPlayerOne().remove(handIdx);
                                }
                                break;
                            case 2:
                                if (game.getHandPlayerTwo().get(handIdx) instanceof Environment) {
                                    action.put("command", command);
                                    action.put("handIdx", handIdx);
                                    action.put("error",
                                            "Cannot place environment card on table.");
                                    output.add(action);
                                } else if (game.getHandPlayerTwo().get(handIdx).getMana()
                                        > game.getManaPlayerTwo()) {
                                    action.put("command", command);
                                    action.put("handIdx", handIdx);
                                    action.put("error",
                                            "Not enough mana to place card on table.");
                                    output.add(action);
                                } else if (game.getBoard().getPositionBack()
                                        .contains(game.getHandPlayerTwo().get(handIdx).getName())
                                        && game.getBoard().getTable().get(0).size()
                                        == game.getBoard().getMaxColumns()) {
                                    action.put("command", command);
                                    action.put("handIdx", handIdx);
                                    action.put("error",
                                            "Cannot place card on table since row is full.");
                                    output.add(action);
                                } else if (game.getBoard().getPositionFront()
                                        .contains(game.getHandPlayerTwo().get(handIdx).getName())
                                        && game.getBoard().getTable().get(1).size()
                                        == game.getBoard().getMaxColumns()) {
                                    action.put("command", command);
                                    action.put("handIdx", handIdx);
                                    action.put("error",
                                            "Cannot place card on table since row is full.");
                                    output.add(action);
                                } else if (game.getBoard().getPositionBack()
                                        .contains(game.getHandPlayerTwo().get(handIdx).getName())) {
                                    Minion cardForPlacing =
                                            specifyingTypeOfMinion(inputData, i, game, j, 2);
                                    game.getBoard().getTable().get(0).add(cardForPlacing);
                                    game.setManaPlayerTwo(game.getManaPlayerTwo()
                                            - game.getHandPlayerTwo().get(handIdx).getMana());
                                    game.getHandPlayerTwo().remove(handIdx);
                                } else {
                                    Minion cardForPlacing =
                                            specifyingTypeOfMinion(inputData, i, game, j, 2);
                                    game.getBoard().getTable().get(1).add(cardForPlacing);
                                    game.setManaPlayerTwo(game.getManaPlayerTwo()
                                            - game.getHandPlayerTwo().get(handIdx).getMana());
                                    game.getHandPlayerTwo().remove(handIdx);
                                }
                                break;
                            default:System.out.println("Player turn works wrong!");
                        }
                        //We check every invalid case and if none applies we place the card
                    }
                    case "getCardsInHand" -> {
                        action.put("command", command);
                        if (playerIdx == 1) {
                            action.put("playerIdx", 1);
                            ArrayList<Card> deepCopyHand =
                                    new ArrayList<>(game.getHandPlayerOne());
                            action.putPOJO("output", deepCopyHand);
                        } else {
                            action.put("playerIdx", 2);
                            ArrayList<Card> deepCopyHand =
                                    new ArrayList<>(game.getHandPlayerTwo());
                            action.putPOJO("output", deepCopyHand);
                        }
                        output.add(action);
                    }
                    case "getCardsOnTable" -> {
                        action.put("command", command);

                        ArrayList<ArrayList<Card>> deepCopyTable = new ArrayList<>();
                        ArrayList<Card> deepCopyRow0 =
                                new ArrayList<>(game.getBoard().getTable().get(0));
                        ArrayList<Card> deepCopyRow1 =
                                new ArrayList<>(game.getBoard().getTable().get(1));
                        ArrayList<Card> deepCopyRow2 =
                                new ArrayList<>(game.getBoard().getTable().get(2));
                        ArrayList<Card> deepCopyRow3 =
                                new ArrayList<>(game.getBoard().getTable().get(3));
                        deepCopyTable.add(deepCopyRow0);
                        deepCopyTable.add(deepCopyRow1);
                        deepCopyTable.add(deepCopyRow2);
                        deepCopyTable.add(deepCopyRow3);

                        action.putPOJO("output", deepCopyTable);
                        output.add(action);
                    }
                    case "getPlayerMana" -> {
                        action.put("command", command);
                        if (playerIdx == 1) {
                            action.put("playerIdx", 1);
                            action.put("output", game.getManaPlayerOne());
                        } else {
                            action.put("playerIdx", 2);
                            action.put("output", game.getManaPlayerTwo());
                        }
                        output.add(action);
                    }
                    case "getEnvironmentCardsInHand" -> {
                        action.put("command", command);
                        if (playerIdx == 1) {
                            action.put("playerIdx", 1);
                            ArrayList<Card> handDeepCopy = new ArrayList<>();
                            for (int k = 0; k < game.getHandPlayerOne().size(); k++) {
                                if (game.getHandPlayerOne().get(k) instanceof Environment) {
                                    handDeepCopy.add(game.getHandPlayerOne().get(k));
                                }
                            }
                            action.putPOJO("output", handDeepCopy);
                        } else {
                            action.put("playerIdx", 2);
                            ArrayList<Card> handDeepCopy = new ArrayList<>();
                            for (int k = 0; k < game.getHandPlayerTwo().size(); k++) {
                                if (game.getHandPlayerTwo().get(k) instanceof Environment) {
                                    handDeepCopy.add(game.getHandPlayerTwo().get(k));
                                }
                            }
                            action.putPOJO("output", handDeepCopy);
                        }
                        output.add(action);
                    }
                    case "getCardAtPosition" -> {
                        action.put("command", command);
                        if (x + 1 <= game.getBoard().getTable().size()
                                && y + 1 <= game.getBoard().getTable().get(x).size()) {
                            action.put("x", x);
                            action.put("y", y);
                            Minion deepCopyCardAtPosition =
                                    new BasicMinion(game.getBoard().getTable().get(x).get(y));
                            action.putPOJO("output", deepCopyCardAtPosition);
                        } else {
                            action.put("x", x);
                            action.put("y", y);
                            action.put("output", "No card available at that position.");
                        }
                        output.add(action);
                    }
                    case "useEnvironmentCard" -> {
                        switch (game.getPlayerTurn()) {
                            case 1:
                                if (!(game.getHandPlayerOne().get(handIdx)
                                        instanceof Environment)) {
                                    action.put("command", command);
                                    action.put("handIdx", handIdx);
                                    action.put("affectedRow", affectedRow);
                                    action.put("error",
                                            "Chosen card is not of type environment.");
                                    output.add(action);
                                } else if (game.getHandPlayerOne().get(handIdx).getMana()
                                        > game.getManaPlayerOne()) {
                                    action.put("command", command);
                                    action.put("handIdx", handIdx);
                                    action.put("affectedRow", affectedRow);
                                    action.put("error",
                                            "Not enough mana to use environment card.");
                                    output.add(action);
                                } else if (affectedRow == 2 || affectedRow == 3) {
                                    action.put("command", command);
                                    action.put("handIdx", handIdx);
                                    action.put("affectedRow", affectedRow);
                                    action.put("error",
                                            "Chosen row does not belong to the enemy.");
                                    output.add(action);
                                } else if (game.getHandPlayerOne().get(handIdx).getClass()
                                        == HeartHound.class && (game.getBoard().getTable()
                                        .get(Math.abs(affectedRow - 3)).size()
                                        == game.getBoard().getMaxColumns())) {
                                    action.put("command", command);
                                    action.put("handIdx", handIdx);
                                    action.put("affectedRow", affectedRow);
                                    action.put("error",
                                            "Cannot steal enemy card "
                                                    + "since the player's row is full.");
                                    output.add(action);
                                } else {
                                    game.getHandPlayerOne().get(handIdx)
                                            .useAbility(game, affectedRow);
                                    game.setManaPlayerOne(game.getManaPlayerOne()
                                            - game.getHandPlayerOne().get(handIdx).getMana());
                                    game.getHandPlayerOne().remove(handIdx);
                                    removeMinionOnRowIfDied(game, affectedRow);
                                }
                                break;
                            case 2:
                                if (!(game.getHandPlayerTwo().get(handIdx)
                                        instanceof Environment)) {
                                    action.put("command", command);
                                    action.put("handIdx", handIdx);
                                    action.put("affectedRow", affectedRow);
                                    action.put("error",
                                            "Chosen card is not of type environment.");
                                    output.add(action);
                                } else if (game.getHandPlayerTwo().get(handIdx).getMana()
                                        > game.getManaPlayerTwo()) {
                                    action.put("command", command);
                                    action.put("handIdx", handIdx);
                                    action.put("affectedRow", affectedRow);
                                    action.put("error",
                                            "Not enough mana to use environment card.");
                                    output.add(action);
                                } else if ((affectedRow == 1) || (affectedRow == 0)) {
                                    action.put("command", command);
                                    action.put("handIdx", handIdx);
                                    action.put("affectedRow", affectedRow);
                                    action.put("error",
                                            "Chosen row does not belong to the enemy.");
                                    output.add(action);
                                } else if ((game.getHandPlayerTwo().get(handIdx).getClass()
                                        == HeartHound.class) && (game.getBoard().getTable()
                                        .get(Math.abs(affectedRow - 3)).size()
                                        == game.getBoard().getMaxColumns())) {
                                    action.put("command", command);
                                    action.put("handIdx", handIdx);
                                    action.put("affectedRow", affectedRow);
                                    action.put("error",
                                            "Cannot steal enemy card"
                                                    + " since the player's row is full.");
                                    output.add(action);
                                } else {
                                    game.getHandPlayerTwo().get(handIdx)
                                            .useAbility(game, affectedRow);
                                    game.setManaPlayerTwo(game.getManaPlayerTwo()
                                            - game.getHandPlayerTwo().get(handIdx).getMana());
                                    game.getHandPlayerTwo().remove(handIdx);
                                    removeMinionOnRowIfDied(game, affectedRow);
                                }
                                break;
                            default: System.out.println("Player turn works wrong!");
                        }
                        /*We check every invalid case and if none applies we use the environment
                        card and check if any minion died in order to remove it from table
                        */
                    }
                    case "getFrozenCardsOnTable" -> {
                        action.put("command", command);

                        ArrayList<Minion> deepCopyFrozenTable = new ArrayList<>();

                        for (int row = 0; row < game.getBoard().getTable().size(); row++) {
                            for (int column = 0;
                                 column < game.getBoard().getTable().get(row).size(); column++) {
                                if ((game.getBoard().getFrozenCoords()[row][column] == 1)
                                        && (game.getBoard().getTable().get(row).get(column)
                                        != null)) {
                                    deepCopyFrozenTable
                                            .add(game.getBoard().getTable().get(row).get(column));
                                }
                            }
                        }

                        action.putPOJO("output", deepCopyFrozenTable);
                        output.add(action);
                    }
                    case "cardUsesAttack" -> {
                        int tankExistenceCheck = 0;
                        switch (game.getPlayerTurn()) {
                            case 1 -> {
                                for (int idx = 0;
                                     idx < game.getBoard().getTable().get(1).size(); idx++) {
                                    if (game.getBoard().getTanks()
                                            .contains(game.getBoard().getTable()
                                                    .get(1).get(idx).getName())) {
                                        tankExistenceCheck = 1;
                                        break;
                                    }
                                }
                                if (game.getBoard().getFrozenCoords()
                                        [cardAttackerX][cardAttackerY] == 1) {
                                    action.put("command", command);
                                    action.putPOJO("cardAttacker", attackerCoords);
                                    action.putPOJO("cardAttacked", attackedCoords);
                                    action.put("error",
                                            "Attacker card is frozen.");
                                    output.add(action);
                                } else if (game.getBoard().getFrozenCoords()
                                        [cardAttackerX][cardAttackerY] == 2) {
                                    action.put("command", command);
                                    action.putPOJO("cardAttacker", attackerCoords);
                                    action.putPOJO("cardAttacked", attackedCoords);
                                    action.put("error",
                                            "Attacker card has already attacked this turn.");
                                    output.add(action);
                                } else if (cardAttackedX == 3 || cardAttackedX == 2
                                        || game.getBoard().getTable()
                                        .get(cardAttackedX).get(cardAttackedY) == null) {
                                    action.put("command", command);
                                    action.putPOJO("cardAttacker", attackerCoords);
                                    action.putPOJO("cardAttacked", attackedCoords);
                                    action.put("error",
                                            "Attacked card does not belong to the enemy.");
                                    output.add(action);
                                } else {
                                    tankCheckerAttackUse(output, game, action, command,
                                            cardAttackerX, cardAttackerY, cardAttackedX,
                                            cardAttackedY, attackerCoords, attackedCoords,
                                            tankExistenceCheck);
                                }
                            }
                            case 2 -> {
                                for (int idx =
                                     0; idx < game.getBoard().getTable().get(2).size(); idx++) {
                                    if (game.getBoard().getTanks()
                                            .contains(game.getBoard().getTable()
                                                    .get(2).get(idx).getName())) {
                                        tankExistenceCheck = 1;
                                        break;
                                    }
                                }
                                if (game.getBoard().getFrozenCoords()
                                        [cardAttackerX][cardAttackerY] == 1) {
                                    action.put("command", command);
                                    action.putPOJO("cardAttacker", attackerCoords);
                                    action.putPOJO("cardAttacked", attackedCoords);
                                    action.put("error",
                                            "Attacker card is frozen.");
                                    output.add(action);
                                } else if (game.getBoard().getFrozenCoords()
                                        [cardAttackerX][cardAttackerY] == 2) {
                                    action.put("command", command);
                                    action.putPOJO("cardAttacker", attackerCoords);
                                    action.putPOJO("cardAttacked", attackedCoords);
                                    action.put("error",
                                            "Attacker card has already attacked this turn.");
                                    output.add(action);
                                } else if (cardAttackedX == 0 || cardAttackedX == 1
                                        || game.getBoard().getTable()
                                        .get(cardAttackedX).get(cardAttackedY) == null) {
                                    action.put("command", command);
                                    action.putPOJO("cardAttacker", attackerCoords);
                                    action.putPOJO("cardAttacked", attackedCoords);
                                    action.put("error",
                                            "Attacked card does not belong to the enemy.");
                                    output.add(action);
                                } else {
                                    tankCheckerAttackUse(output, game, action, command,
                                            cardAttackerX, cardAttackerY, cardAttackedX,
                                            cardAttackedY, attackerCoords, attackedCoords,
                                            tankExistenceCheck);
                                }
                            }
                            default -> System.out.println("Player turn works wrong!");
                        }
                        /*We check every invalid case and if none applies the attack is happening,
                         and we check if any minion died in order to remove it from table
                         */
                    }
                    case "cardUsesAbility" -> {
                        int tankExistenceCheck = 0;

                        switch (game.getPlayerTurn()) {
                            case 1 -> {
                                for (int idx = 0;
                                     idx < game.getBoard().getTable().get(1).size(); idx++) {
                                    if (game.getBoard().getTanks().contains(game.getBoard()
                                            .getTable().get(1).get(idx).getName())) {
                                        tankExistenceCheck = 1;
                                        break;
                                    }
                                }
                                if (game.getBoard().getFrozenCoords()
                                        [cardAttackerX][cardAttackerY] == 1) {
                                    action.put("command", command);
                                    action.putPOJO("cardAttacker", attackerCoords);
                                    action.putPOJO("cardAttacked", attackedCoords);
                                    action.put("error",
                                            "Attacker card is frozen.");
                                    output.add(action);
                                } else if (game.getBoard().getFrozenCoords()
                                        [cardAttackerX][cardAttackerY] == 2) {
                                    action.put("command", command);
                                    action.putPOJO("cardAttacker", attackerCoords);
                                    action.putPOJO("cardAttacked", attackedCoords);
                                    action.put("error",
                                            "Attacker card has already attacked this turn.");
                                    output.add(action);
                                } else if ((cardAttackedX == 3 || cardAttackedX == 2
                                        || game.getBoard().getTable().get(cardAttackedX)
                                        .get(cardAttackedY) == null) && !(game.getBoard()
                                        .getTable().get(cardAttackerX).get(cardAttackerY)
                                        .getName().equals("Disciple"))) {
                                    action.put("command", command);
                                    action.putPOJO("cardAttacker", attackerCoords);
                                    action.putPOJO("cardAttacked", attackedCoords);
                                    action.put("error",
                                            "Attacked card does not belong to the enemy.");
                                    output.add(action);
                                } else if ((cardAttackedX == 0 || cardAttackedX == 1
                                        || game.getBoard().getTable().get(cardAttackedX)
                                        .get(cardAttackedY) == null)
                                        && game.getBoard().getTable().get(cardAttackerX)
                                        .get(cardAttackerY).getName().equals("Disciple")) {
                                    action.put("command", command);
                                    action.putPOJO("cardAttacker", attackerCoords);
                                    action.putPOJO("cardAttacked", attackedCoords);
                                    action.put("error",
                                            "Attacked card does not "
                                                    + "belong to the current player.");
                                    output.add(action);
                                } else {
                                    checkForTanksAndUsesAbility(inputData, output, i, game, j,
                                            action, command, cardAttackerX, cardAttackerY,
                                            cardAttackedX, cardAttackedY, tankExistenceCheck);
                                }
                            }
                            case 2 -> {
                                for (int idx =
                                     0; idx < game.getBoard().getTable().get(2).size(); idx++) {
                                    if (game.getBoard().getTanks().contains(game.getBoard()
                                            .getTable().get(2).get(idx).getName())) {
                                        tankExistenceCheck = 1;
                                        break;
                                    }
                                }
                                if (game.getBoard().getFrozenCoords()
                                        [cardAttackerX][cardAttackerY] == 1) {
                                    action.put("command", command);
                                    action.putPOJO("cardAttacker", attackerCoords);
                                    action.putPOJO("cardAttacked", attackedCoords);
                                    action.put("error",
                                            "Attacker card is frozen.");
                                    output.add(action);
                                } else if (game.getBoard().getFrozenCoords()
                                        [cardAttackerX][cardAttackerY] == 2) {
                                    action.put("command", command);
                                    action.putPOJO("cardAttacker", attackerCoords);
                                    action.putPOJO("cardAttacked", attackedCoords);
                                    action.put("error",
                                            "Attacker card has already attacked this turn.");
                                    output.add(action);
                                } else if ((cardAttackedX == 0 || cardAttackedX == 1
                                        || game.getBoard().getTable().get(cardAttackedX)
                                        .get(cardAttackedY) == null)
                                        && !(game.getBoard().getTable().get(cardAttackerX)
                                        .get(cardAttackerY).getName().equals("Disciple"))) {
                                    action.put("command", command);
                                    action.putPOJO("cardAttacker", attackerCoords);
                                    action.putPOJO("cardAttacked", attackedCoords);
                                    action.put("error",
                                            "Attacked card does not belong to the enemy.");
                                    output.add(action);
                                } else if ((cardAttackedX == 2 || cardAttackedX == 3
                                        || game.getBoard().getTable().get(cardAttackedX)
                                        .get(cardAttackedY) == null)
                                        && game.getBoard().getTable().get(cardAttackerX)
                                        .get(cardAttackerY).getName().equals("Disciple")) {
                                    action.put("command", command);
                                    action.putPOJO("cardAttacker", attackerCoords);
                                    action.putPOJO("cardAttacked", attackedCoords);
                                    action.put("error",
                                            "Attacked card does not "
                                                    + "belong to the current player.");
                                    output.add(action);
                                } else {
                                    checkForTanksAndUsesAbility(inputData, output, i, game, j,
                                            action, command, cardAttackerX, cardAttackerY,
                                            cardAttackedX, cardAttackedY, tankExistenceCheck);
                                }
                            }
                            default -> System.out.println("SPlayer turn works wrong!");
                        }
                        /*We check every invalid case and if none applies we use the minion ability
                        and check if any minion died in order to remove it from table
                         */
                    }
                    case "useHeroAbility" -> {
                        switch (game.getPlayerTurn()) {
                            case 1 -> {
                                if (game.getManaPlayerOne()
                                        < game.getChosenHeroes().getHeroPlayerOne().getMana()) {
                                    action.put("command", command);
                                    action.put("affectedRow", affectedRow);
                                    action.put("error",
                                            "Not enough mana to use hero's ability.");
                                    output.add(action);
                                } else if (game.getChosenHeroes().getHeroAlreadyAttacked()[1]
                                        == 1) {
                                    action.put("command", command);
                                    action.put("affectedRow", affectedRow);
                                    action.put("error",
                                            "Hero has already attacked this turn.");
                                    output.add(action);
                                } else if ((game.getChosenHeroes().getHeroPlayerOne()
                                        instanceof LordRoyce || game.getChosenHeroes()
                                        .getHeroPlayerOne() instanceof EmpressThorina)
                                        && (affectedRow == 2 || affectedRow == 3)) {
                                    action.put("command", command);
                                    action.put("affectedRow", affectedRow);
                                    action.put("error",
                                            "Selected row does not belong to the enemy.");
                                    output.add(action);
                                } else if ((game.getChosenHeroes().getHeroPlayerOne()
                                        instanceof GeneralKocioraw || game.getChosenHeroes()
                                        .getHeroPlayerOne() instanceof KingMudface)
                                        && (affectedRow == 0 || affectedRow == 1)) {
                                    action.put("command", command);
                                    action.put("affectedRow", affectedRow);
                                    action.put("error",
                                            "Selected row does not belong to the current player.");
                                    output.add(action);
                                } else {
                                    game.getChosenHeroes().getHeroPlayerOne()
                                            .useAbility(game, affectedRow);
                                    game.getChosenHeroes().getHeroAlreadyAttacked()[1] = 1;
                                    game.setManaPlayerOne(game.getManaPlayerOne()
                                            - game.getChosenHeroes().getHeroPlayerOne().getMana());
                                }
                            }
                            case 2 -> {
                                if (game.getManaPlayerTwo()
                                        < game.getChosenHeroes().getHeroPlayerTwo().getMana()) {
                                    action.put("command", command);
                                    action.put("affectedRow", affectedRow);
                                    action.put("error",
                                            "Not enough mana to use hero's ability.");
                                    output.add(action);
                                } else if (game.getChosenHeroes().getHeroAlreadyAttacked()[2]
                                        == 1) {
                                    action.put("command", command);
                                    action.put("affectedRow", affectedRow);
                                    action.put("error",
                                            "Hero has already attacked this turn.");
                                    output.add(action);
                                } else if ((game.getChosenHeroes().getHeroPlayerTwo()
                                        instanceof LordRoyce || game.getChosenHeroes()
                                        .getHeroPlayerTwo() instanceof EmpressThorina)
                                        && (affectedRow == 0 || affectedRow == 1)) {
                                    action.put("command", command);
                                    action.put("affectedRow", affectedRow);
                                    action.put("error",
                                            "Selected row does not belong to the enemy.");
                                    output.add(action);
                                } else if ((game.getChosenHeroes().getHeroPlayerTwo()
                                        instanceof GeneralKocioraw || game.getChosenHeroes()
                                        .getHeroPlayerTwo() instanceof KingMudface)
                                        && (affectedRow == 2 || affectedRow == 3)) {
                                    action.put("command", command);
                                    action.put("affectedRow", affectedRow);
                                    action.put("error",
                                            "Selected row does not belong to the current player.");
                                    output.add(action);
                                } else {
                                    game.getChosenHeroes().getHeroPlayerTwo()
                                            .useAbility(game, affectedRow);
                                    game.getChosenHeroes().getHeroAlreadyAttacked()[2] = 1;
                                    game.setManaPlayerTwo(game.getManaPlayerTwo()
                                            - game.getChosenHeroes().getHeroPlayerTwo().getMana());
                                }
                            }
                            default -> System.out.println("Player turn works wrong!");
                        }
                        /*We check every invalid case and if none applies we use the hero ability
                        and check if any minion died in order to remove it from table
                         */
                    }
                    case "useAttackHero" -> {
                        int tankExistenceCheck = 0;
                        switch (game.getPlayerTurn()) {
                            case 1 -> {
                                for (int idx =
                                     0; idx < game.getBoard().getTable().get(1).size(); idx++) {
                                    if (game.getBoard().getTanks().contains(game.getBoard()
                                            .getTable().get(1).get(idx).getName())) {
                                        tankExistenceCheck = 1;
                                        break;
                                    }
                                }
                                if (game.getBoard().getFrozenCoords()
                                        [cardAttackerX][cardAttackerY] == 1) {
                                    action.put("command", command);
                                    action.putPOJO("cardAttacker", attackerCoords);
                                    action.put("error", ""
                                            + "Attacker card is frozen.");
                                    output.add(action);
                                } else if (game.getBoard().getFrozenCoords()
                                        [cardAttackerX][cardAttackerY] == 2) {
                                    action.put("command", command);
                                    action.putPOJO("cardAttacker", attackerCoords);
                                    action.put("error",
                                            "Attacker card has already attacked this turn.");
                                    output.add(action);
                                } else if (tankExistenceCheck == 1) {
                                    action.put("command", command);
                                    action.putPOJO("cardAttacker", attackerCoords);
                                    action.put("error",
                                            "Attacked card is not of type 'Tank'.");
                                    output.add(action);
                                } else {
                                    game.getChosenHeroes().getHeroPlayerTwo().setHealth(game
                                            .getChosenHeroes().getHeroPlayerTwo().getHealth()
                                            - game.getBoard().getTable().get(cardAttackerX)
                                            .get(cardAttackerY).getAttackDamage());
                                    game.getBoard().getFrozenCoords()
                                            [cardAttackerX][cardAttackerY] = 2;
                                    if (game.getChosenHeroes().getHeroPlayerTwo().getHealth()
                                            <= 0) {
                                        action.put("gameEnded",
                                                "Player one killed the enemy hero.");
                                        output.add(action);
                                        playerOneWins++;
                                    }
                                }
                            }
                            case 2 -> {
                                for (int idx = 0;
                                     idx < game.getBoard().getTable().get(2).size(); idx++) {
                                    if (game.getBoard().getTanks().contains(game.getBoard()
                                            .getTable().get(2).get(idx).getName())) {
                                        tankExistenceCheck = 1;
                                        break;
                                    }
                                }
                                if (game.getBoard().getFrozenCoords()
                                        [cardAttackerX][cardAttackerY] == 1) {
                                    action.put("command", command);
                                    action.putPOJO("cardAttacker", attackerCoords);
                                    action.put("error",
                                            "Attacker card is frozen.");
                                    output.add(action);
                                } else if (game.getBoard().getFrozenCoords()
                                        [cardAttackerX][cardAttackerY] == 2) {
                                    action.put("command", command);
                                    action.putPOJO("cardAttacker", attackerCoords);
                                    action.put("error",
                                            "Attacker card has already attacked this turn.");
                                    output.add(action);
                                } else if (tankExistenceCheck == 1) {
                                    action.put("command", command);
                                    action.putPOJO("cardAttacker", attackerCoords);
                                    action.put("error",
                                            "Attacked card is not of type 'Tank'.");
                                    output.add(action);
                                } else {
                                    game.getChosenHeroes().getHeroPlayerOne().setHealth(game
                                            .getChosenHeroes().getHeroPlayerOne().getHealth()
                                            - game.getBoard().getTable().get(cardAttackerX)
                                            .get(cardAttackerY).getAttackDamage());
                                    game.getBoard().getFrozenCoords()
                                            [cardAttackerX][cardAttackerY] = 2;
                                    if (game.getChosenHeroes().getHeroPlayerOne().getHealth()
                                            <= 0) {
                                        action.put("gameEnded",
                                                "Player two killed the enemy hero.");
                                        output.add(action);
                                        playerTwoWins++;
                                    }
                                }
                            }
                            default -> System.out.println("Player turn works wrong!");
                        }
                        /*We check every invalid case and if none applies we attack the hero
                        and check if someone wins in order to add it to score
                         */
                    }
                    case "getTotalGamesPlayed" -> {
                        action.put("command", command);
                        action.put("output", totalGamesPlayed);
                        output.add(action);
                    }
                    case "getPlayerOneWins" -> {
                        action.put("command", command);
                        action.put("output", playerOneWins);
                        output.add(action);
                    }
                    case "getPlayerTwoWins" -> {
                        action.put("command", command);
                        action.put("output", playerTwoWins);
                        output.add(action);
                    }
                    default -> System.out.println("You need to implement " + command);
                }
            }
        }
        //We take each command with a switch, fill the node action with what is needed and
        //add it to the node-array 'output'

        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(filePath2), output);
    }

    private static void removeMinionOnRowIfDied(final GameFlow game, final int affectedRow) {
        int cnt = 0;
        while (cnt < game.getBoard().getTable().get(affectedRow).size()) {
            if (game.getBoard().getTable().get(affectedRow).get(cnt)
                    .getHealth() == 0) {
                game.getBoard().getTable().get(affectedRow).remove(cnt);
            } else {
                cnt++;
            }
        }
    }
    //Function to remove a minion of a row if he died (to avoid duplicate code)

    private static void tankCheckerAttackUse(final ArrayNode output, final GameFlow game,
                                             final ObjectNode action, final String command,
                                             final int cardAttackerX, final int cardAttackerY,
                                             final int cardAttackedX, final int cardAttackedY,
                                             final Coordinates attackerCoords,
                                             final Coordinates attackedCoords,
                                             final int tankExistenceCheck) {
        if (tankExistenceCheck == 1 && !game.getBoard().getTanks()
                .contains(game.getBoard().getTable()
                        .get(cardAttackedX).get(cardAttackedY).getName())) {
            action.put("command", command);
            action.putPOJO("cardAttacker", attackerCoords);
            action.putPOJO("cardAttacked", attackedCoords);
            action.put("error",
                    "Attacked card is not of type 'Tank'.");
            output.add(action);
        } else {
            cardUsesAttack(game, cardAttackerX, cardAttackerY,
                    cardAttackedX, cardAttackedY);
        }
    }
    //Function to check if tanks exist and if attacked card is a tank (to avoid duplicate code)

    private static void cardUsesAttack(final GameFlow game, final int cardAttackerX,
                                       final int cardAttackerY, final int cardAttackedX,
                                       final int cardAttackedY) {
        game.getBoard().getTable().get(cardAttackedX).get(cardAttackedY).setHealth(game.getBoard()
                .getTable().get(cardAttackedX).get(cardAttackedY).getHealth() - game.getBoard()
                .getTable().get(cardAttackerX).get(cardAttackerY).getAttackDamage());
        game.getBoard().getFrozenCoords()[cardAttackerX][cardAttackerY] = 2;
        if (game.getBoard().getTable().get(cardAttackedX).get(cardAttackedY).getHealth() <= 0) {
            game.getBoard().getTable().get(cardAttackedX).remove(cardAttackedY);
        }
    }
    //Function for card attack and remove minion if died (to avoid duplicate code)

    private static Minion specifyingTypeOfMinion(final Input inputData, final int i,
                                                 final GameFlow game, final int j,
                                                 final int player) {
        int handIdx = inputData.getGames().get(i).getActions().get(j).getHandIdx();
        Minion cardForPlacing;
        if (player == 1) {
            cardForPlacing = switch (game.getHandPlayerOne().get(handIdx).getName()) {
                case "Disciple" ->
                        new Disciple(game.getHandPlayerOne().get(handIdx));
                case "The Ripper" ->
                        new TheRipper(game.getHandPlayerOne().get(handIdx));
                case "Miraj" ->
                        new Miraj(game.getHandPlayerOne().get(handIdx));
                case "The Cursed One" ->
                        new TheCursedOne(game.getHandPlayerOne().get(handIdx));
                default -> new BasicMinion(game.getHandPlayerOne().get(handIdx));
            };
        } else {
            cardForPlacing = switch (game.getHandPlayerTwo().get(handIdx).getName()) {
                case "Disciple" ->
                        new Disciple(game.getHandPlayerTwo().get(handIdx));
                case "The Ripper" ->
                        new TheRipper(game.getHandPlayerTwo().get(handIdx));
                case "Miraj" ->
                        new Miraj(game.getHandPlayerTwo().get(handIdx));
                case "The Cursed One" ->
                        new TheCursedOne(game.getHandPlayerTwo().get(handIdx));
                default -> new BasicMinion(game.getHandPlayerTwo().get(handIdx));
            };
        }
        return cardForPlacing;
    }
    //Function for specifying the minion type when is being placed (to avoid duplicate code)

    private static void  checkForTanksAndUsesAbility(final Input inputData, final ArrayNode output,
                                                     final int i, final GameFlow game, final int j,
                                                     final ObjectNode action, final String command,
                                                     final int cardAttackerX, final int cardAttackerY,
                                                     final int cardAttackedX, final int cardAttackedY,
                                                     final int tankExistenceCheck) {
        if (tankExistenceCheck == 1 && !game.getBoard().getTanks().contains(game.getBoard()
                .getTable().get(cardAttackedX).get(cardAttackedY).getName())
                && !game.getBoard().getTable().get(cardAttackerX).get(cardAttackerY).getName()
                .equals("Disciple")) {
            action.put("command", command);
            action.putPOJO("cardAttacker",
                    inputData.getGames().get(i).getActions().get(j).getCardAttacker());
            action.putPOJO("cardAttacked",
                    inputData.getGames().get(i).getActions().get(j).getCardAttacked());
            action.put("error", "Attacked card is not of type 'Tank'.");
            output.add(action);
        } else {
            game.getBoard().getTable().get(cardAttackerX).get(cardAttackerY)
                    .useAbility(game, cardAttackedX, cardAttackedY);

            game.getBoard().getFrozenCoords()[cardAttackerX][cardAttackerY] = 2;
            if (game.getBoard().getTable().get(cardAttackedX).get(cardAttackedY).getHealth() <= 0) {
                game.getBoard().getTable().get(cardAttackedX).remove(cardAttackedY);
            }
        }
    }
    //Function for minion to use ability in case there are no tanks or if it attacks a tank
    // (to avoid duplicate code)
}
