package test.cards;

import test.Cards;

public class Forest extends Cards
{
    public Forest()
    {
        name = "Forest";
        type = "Basic_Land~Forest";
        manacost = "";
        m2c();

        produceManaByManaAbility = true;
        manaAbilityGenerate = "G";
    }
}