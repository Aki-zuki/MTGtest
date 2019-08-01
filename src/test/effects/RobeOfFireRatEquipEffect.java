package test.effects;

import test.Cards;

public class RobeOfFireRatEquipEffect extends Effect
{
    public void effect(Cards c)
    {
        c.toughness += 3;
        c.hexProofFrom += "Red";
        //TODO:这两个应该分开写
    }
}
