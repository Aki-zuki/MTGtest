package test.cards;

import test.Cards;
import test.effects.*;
public class RobeOfFireRat extends Cards
{
    Effect equipEffect;

    public RobeOfFireRat()
    {
        name = "Robe Of Fire Rat";
        type = "Legendary_Artifact~Equipment";
        manacost = "2";
        m2c();
        //equipCost = "2";
        equipEffect = new RobeOfFireRatEquipEffect();

        activeList.add(new Equip(this,"2",equipEffect));//TODO:如何对敌
        activeList.add(new RobeOfFireRatSacrifice(this));



    }
    public boolean withLeavesTheBattleFieldCheck(Cards c)
    {
        return this.attachedCard != null && (c.id == this.id || c.id == this.attachedCard.id) ;
    }
    public void withLeavesTheBattleField(Cards c)
    {
        //不需要借助arrayList，直接拿走
        if(c.id == this.id)
        {
            if(c.abilityChangingList.contains(equipEffect))
            {
                c.abilityChangingList.remove(equipEffect);
            }
        }
        this.attachedCard = null;
    }


}
