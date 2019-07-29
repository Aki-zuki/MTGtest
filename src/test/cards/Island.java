package test.cards;

import test.Cards;

public class Island extends Cards
{
    public Island()
    {
        name = "Island";
        type = "Basic_Land~Island";
        manacost = "";
        m2c();

        produceManaByManaAbility = true;
        manaAbilityGenerate = "U";
    }
}