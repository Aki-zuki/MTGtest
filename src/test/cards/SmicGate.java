package test.cards;

import test.Cards;
import test.Player;

public class SmicGate extends Cards
{
    public SmicGate()
    {
        name = "Smic Gate";
        type = "Land~Gate";
        manacost = "";
        m2c();

        produceManaByManaAbility = true;
        manaAbilityGenerate = "{U/G}";
    }

    @Override
    public void withEntersTheBattleField(Cards c)
    {
        if(c == this)
            this.tapped = true;
    }
}