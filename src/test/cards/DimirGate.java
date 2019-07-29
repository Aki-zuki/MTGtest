package test.cards;

import test.Cards;
import test.Player;

public class DimirGate extends Cards
{
    public DimirGate()
    {
        name = "Dimir Gate";
        type = "Land~Gate";
        manacost = "";
        m2c();

        produceManaByManaAbility = true;
        manaAbilityGenerate = "{U/B}";

    }
    @Override
    public void withEntersTheBattleField(Cards c)
    {
        if(c == this)
            this.tapped = true;
    }
}