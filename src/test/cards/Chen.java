package test.cards;

import test.Cards;
import test.effects.*;
public class Chen extends Cards
{
    public Chen()
    {
        name = "Chen";
        type = "Legendary_Creature~Cat";
        manacost = "RG";
        convertManaCost = 2;
        m2c();
        oriPower = power = 1;
        oraToughness = toughness = 1;
        blockLimit = 1;

        abilityChangingList.add(new ChenAbilityChanging());
    }
    public boolean withEntersTheBattleFieldCheck(Cards c)
    {
        return c == this;
    }
    //这个就不用id了
    public void withEntersTheBattleField(Cards c)
    {
        withEntersTheBattleFieldList.add(new Riot());
        withEntersTheBattleFieldList.add(new Riot());
    }

    /*public boolean withAbilityChangingEffect(Cards c)
    {
        //return c == this; NOTE:有趣的是，由于状态检查中会用新人换旧人，这个条件判断不出来
        return c.id == this.id;
    }
    public void abilityChangingEffect(Cards c)
    {
        c.abilityChangingList.add(new ChenAbilityChanging());
    }*/

}
