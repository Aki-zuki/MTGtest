package test.effects;

import test.Cards;

public class DealsDamageToCreature extends Effect
{
    public int damage = 0;
    public DealsDamageToCreature(Cards s, int d)
    {
        source = s;
        damage = d;
    }
    @Override
    public void effect(Cards c)
    {
        if(new TargetCreature(this.source).targetDetect(c))//再次检查合法性
            source.controller.game.dealDamageToCreature(source,c,damage);
    }
}
