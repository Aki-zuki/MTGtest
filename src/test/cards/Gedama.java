package test.cards;

import test.Cards;

public class Gedama extends Cards
{
    public Gedama()
    {
        name = "Gedama";
        type = "Creature~Gedama";
        manacost = "W";
        convertManaCost = 1;
        m2c();
        power = 2;
        toughness = 1;
        blockLimit = 1;

    }
}
