package test.effects;

import test.Cards;

public class ChenAbilityChanging extends Effect
{
    //public ChenAbilityChanging(){};
    public void effect(Cards c)
    {
        if(c.plus1plus1Counter == 0)
        {
            c.DoubleStrike = true;
        }

        //获得指示物时，为了移除连击，需要重新推导各项异能
    }
}
