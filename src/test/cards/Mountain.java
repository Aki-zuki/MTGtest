package test.cards;

import test.Cards;

public class Mountain extends Cards
{
    public Mountain()
    {
        name = "Mountain";
        type = "Basic_Land~Mountain";
        manacost = "";
        m2c();

        produceManaByManaAbility = true;
        manaAbilityGenerate = "R";
    }
}