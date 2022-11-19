package game.functionalities;

import fileio.Input;
import game.heroes.Hero;
import game.heroes.LordRoyce;
import game.heroes.EmpressThorina;
import game.heroes.KingMudface;
import game.heroes.GeneralKocioraw;

public class ChosenHeroes {
    private final Hero heroPlayerOne;
    private final Hero heroPlayerTwo;
    private final Integer[] heroAlreadyAttacked;

    public ChosenHeroes(final Input inputData, final int i) {
        heroPlayerOne = switch (inputData.getGames().get(i).getStartGame()
                .getPlayerOneHero().getName()) {
            case "General Kocioraw" ->
                    new GeneralKocioraw(inputData.getGames().get(i).getStartGame()
                            .getPlayerOneHero());
            case "King Mudface" ->
                    new KingMudface(inputData.getGames().get(i).getStartGame()
                            .getPlayerOneHero());
            case "Empress Thorina" ->
                    new EmpressThorina(inputData.getGames().get(i).getStartGame()
                            .getPlayerOneHero());
            case "Lord Royce" ->
                    new LordRoyce(inputData.getGames().get(i).getStartGame()
                            .getPlayerOneHero());
            default -> null;
        };

        heroPlayerTwo = switch (inputData.getGames().get(i).getStartGame()
                .getPlayerTwoHero().getName()) {
            case "General Kocioraw" ->
                    new GeneralKocioraw(inputData.getGames().get(i).getStartGame()
                            .getPlayerTwoHero());
            case "King Mudface" ->
                    new KingMudface(inputData.getGames().get(i).getStartGame()
                            .getPlayerTwoHero());
            case "Empress Thorina" ->
                    new EmpressThorina(inputData.getGames().get(i).getStartGame()
                            .getPlayerTwoHero());
            case "Lord Royce" ->
                    new LordRoyce(inputData.getGames().get(i).getStartGame()
                            .getPlayerTwoHero());
            default -> null;
        };
        //Assigning the hero for each player

        heroAlreadyAttacked = new Integer[3];
        heroAlreadyAttacked[1] = 0;
        heroAlreadyAttacked[2] = 0;
        //Checker for hero if her already attacked
    }

    public final Hero getHeroPlayerOne() {
        return heroPlayerOne;
    }

    public final Hero getHeroPlayerTwo() {
        return heroPlayerTwo;
    }

    public final Integer[] getHeroAlreadyAttacked() {
        return heroAlreadyAttacked;
    }
}

