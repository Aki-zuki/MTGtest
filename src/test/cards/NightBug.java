package test.cards;

import test.Cards;
import test.effects.NightBugActive;
import test.effects.NonmanaActiveAbility;

public class NightBug extends Cards
{
    //int activeAbilityId = -1;
    public NonmanaActiveAbility naa = null;
    public NightBug()
    {
        name = "NightBug";
        type = "Legendary_Creature~Insect";
        manacost = "G";
        convertManaCost = 1;
        m2c();
        oriPower = power = 1;
        oraToughness = toughness = 1;
        blockLimit = 1;
        Haste = true;
       // abilityChangingList.add()
    }

    @Override
    public boolean withEntersTheBattleFieldCheck(Cards c)
    {
        return(c.id == this.id || this.place == 4 && c.type.contains("Insect"));//&&优先于||
    }
    @Override
    public void withEntersTheBattleField(Cards c)
    {
        if(c.id == this.id || this.place == 4 && c.type.contains("Insect"))//不借助abilityChanging，便于删除
        {
            //if(this.activeAbilityId == -1)
            if(this.naa == null)
            {
                NonmanaActiveAbility naa = new NightBugActive(c);
                //this.activeAbilityId = naa.eid;
                this.naa = naa;
                c.activeList.add(naa);
            }
            else
            {
                c.activeList.add(naa);
            }
        }
    }

    @Override
    public boolean withLeavesTheBattleFieldCheck(Cards c)
    {
        return (c.id == this.id);
    }

    @Override
    public void withLeavesTheBattleField(Cards c)
    {
        //删除所有人的此效果
        this.controller.game.battlefield.cards.forEach(d ->
        {
            if(d.activeList.contains(naa))
            {
                d.activeList.remove(naa);
            }
        });
    }
}
