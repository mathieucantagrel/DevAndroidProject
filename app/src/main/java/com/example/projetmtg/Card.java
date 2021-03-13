package com.example.projetmtg;

public class Card {

    private final String name;
    private final String manaCost;
    private final int cmc;
    private final String[] colors;
    private final String[] colorIdentity;
    private final String[] superTypes;
    private final String[] types;
    private final String[] subtypes;
    private final String rarity;
    private final String setName;
    private final String text;
    private final String flavor;
    private final String power;
    private final String toughness;

    public Card(String name, String manaCost, int cmc, String[] colors, String[] colorIdentity, String[] superTypes, String[] types, String[] subtypes, String rarity, String setName, String text, String flavor, String power, String toughness) {
        this.name = name;
        this.manaCost = manaCost;
        this.cmc = cmc;
        this.colors = colors;
        this.colorIdentity = colorIdentity;
        this.superTypes = superTypes;
        this.types = types;
        this.subtypes = subtypes;
        this.rarity = rarity;
        this.setName = setName;
        this.text = text;
        this.flavor = flavor;
        this.power = power;
        this.toughness = toughness;
    }

    public String getName() {
        return name;
    }

    public String getManaCost() {
        return manaCost;
    }

    public int getCmc() {
        return cmc;
    }

    public String[] getColors() {
        return colors;
    }

    public String[] getColorIdentity() {
        return colorIdentity;
    }

    public String[] getSuperTypes() {
        return superTypes;
    }

    public String[] getTypes() {
        return types;
    }

    public String[] getSubtypes() {
        return subtypes;
    }

    public String getRarity() {
        return rarity;
    }

    public String getSetName() {
        return setName;
    }

    public String getText() {
        return text;
    }

    public String getFlavor() {
        return flavor;
    }

    public String getPower() {
        return power;
    }

    public String getToughness() {
        return toughness;
    }
}