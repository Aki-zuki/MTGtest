package test.cards;

import test.Cards;
import test.effects.*;
public class RobeOfFireRat extends Cards
{
    public RobeOfFireRat()
    {
        name = "RobeOfFireRat";
        type = "Legendary_Artifact~Equipment";
        manacost = "2";
        m2c();
        //equipCost = "2";
        activeList.add(new Equip(this,"2",new RobeOfFireRatEquipEffect()));
    }

}
