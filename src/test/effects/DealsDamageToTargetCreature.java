package test.effects;

import test.Cards;

public class DealsDamageToTargetCreature extends Effect
{
    public int damage = 0;
    public DealsDamageToTargetCreature(Cards s, int d)
    {
        super(s);
        damage = d;
    }
    @Override
    public void effect(Cards c)
    {
        if(new TargetCreature(this.source).targetDetect(c))//再次检查合法性
            source.controller.game.dealDamageToCreature(source,c,damage);
    }
}
