package game.functionalities;

import fileio.CardInput;

import java.util.ArrayList;

public abstract class Card implements HasAbility {
    private int mana;
    private String description;
    private ArrayList<String> colors;
    private String name;

    public Card(final CardInput card) {
        this.mana = card.getMana();
        this.description = card.getDescription();
        this.colors = card.getColors();
        this.name = card.getName();
    }

    public Card(final Card card) {
        this.mana = card.getMana();
        this.description = card.getDescription();
        this.colors = card.getColors();
        this.name = card.getName();
    }

    public final int getMana() {
        return mana;
    }
    public final String getDescription() {
        return description;
    }
    public final ArrayList<String> getColors() {
        return colors;
    }
    public final String getName() {
        return name;
    }
}
