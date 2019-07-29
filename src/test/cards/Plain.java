package test.cards;

import test.Cards;

public class Plain extends Cards
{
    public Plain()
    {
        name = "Plain";
        type = "Basic_Land~Plain";
        manacost = "";
        m2c();

        produceManaByManaAbility = true;
        manaAbilityGenerate = "W";
    }
}