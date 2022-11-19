package game.functionalities;

import game.minions.Minion;

import java.util.ArrayList;

public class GameTable {
    private final ArrayList<ArrayList<Minion>> table;
    private final Integer[][] frozenCoords;
    private final ArrayList<String> positionBack;
    private final ArrayList<String> positionFront;
    private final ArrayList<String> tanks;

    public GameTable() {
        ArrayList<Minion> row0 = new ArrayList<>();
        ArrayList<Minion> row1 = new ArrayList<>();
        ArrayList<Minion> row2 = new ArrayList<>();
        ArrayList<Minion> row3 = new ArrayList<>();
        table = new ArrayList<>();
        table.add(row0);
        table.add(row1);
        table.add(row2);
        table.add(row3);
        //Building the game table

        frozenCoords = new Integer[getMaxRows()][getMaxColumns()];
        for (int row = 0; row < getMaxRows(); row++) {
            for (int column = 0; column < getMaxColumns(); column++) {
                frozenCoords[row][column] = 0;
            }
        }
        //Storing coordinates for frozen cards on the table

        positionBack = new ArrayList<>();
        positionBack.add("Sentinel");
        positionBack.add("Berserker");
        positionBack.add("The Cursed One");
        positionBack.add("Disciple");
        //Storing minions that must be placed in back position for reference

        positionFront = new ArrayList<>();
        positionFront.add("The Ripper");
        positionFront.add("Miraj");
        positionFront.add("Goliath");
        positionFront.add("Warden");
        //Storing minions that must be placed in front position for reference

        tanks = new ArrayList<>();
        tanks.add("Goliath");
        tanks.add("Warden");
        //Storing the card tanks for reference
    }

    public final ArrayList<ArrayList<Minion>> getTable() {
        return table;
    }

    public final Integer[][] getFrozenCoords() {
        return frozenCoords;
    }

    public final int getMaxColumns() {
        return 5;
    }
    public final int getMaxRows() {
        return 4;
    }

    public final ArrayList<String> getPositionBack() {
        return positionBack;
    }

    public final ArrayList<String> getPositionFront() {
        return positionFront;
    }

    public final ArrayList<String> getTanks() {
        return tanks;
    }
}
