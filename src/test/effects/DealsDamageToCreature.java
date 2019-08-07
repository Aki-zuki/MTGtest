package test.effects;

import test.Cards;

public class DealsDamageToCreature extends Effect//与target区别在于不需要指定目标
{
    public int damage = 0;
    public DealsDamageToCreature(Cards s, int d)
    {
        super(s);
        damage = d;
    }
    @Override
    public void effect(Cards c)
    {
        if(c.place == 4 && c.type.contains("Creature"))//再次检查合法性
            source.controller.game.dealDamageToCreature(source,c,damage);
    }
}
