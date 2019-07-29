package test.cards;

import test.Cards;

public class TenguAssembles extends Cards
{
    public TenguAssembles()
    {
        name = "Tengu Assembles";
        type = "Creature~Wolf_Tengu_Worriers";
        manacost = "1WWW";
        convertManaCost = 4;
        m2c();
        oriPower = power = 4;
        oraToughness = toughness = 4;
        blockLimit = 1;

        Vigilance = true;
    }
}
