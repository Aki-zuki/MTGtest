package test.cards;

import test.Cards;
import test.effects.DealsDamageToCreature;
import test.effects.Discard;
import test.effects.TapTargetCreature;
import test.effects.TargetCreature;

public class VisionaryTuning extends Cards
{
    public VisionaryTuning()
    {
        name = "Visionary Tuning";
        type = "Instant";
        manacost = "1{U/R}{U/R}";
        convertManaCost = 3;
        m2c();
    }
    public boolean targetCheck()
    {
        Cards tapTarget = this.controller.chooseOneTargetForEffect
                ((new TargetCreature(this)));//this指示controller
        if(tapTarget != game.nullSource)//player找得到game
        {
            Cards damageTarget = this.controller.chooseOneTargetForEffect
                    ((new TargetCreature(this)));
            if(damageTarget != game.nullSource)
            {
                firstTargetList.add(tapTarget);
                secondTargetList.add(damageTarget);
                return true;
            }
        }
        return false;
    }
    public boolean nonManaAdditionCostCheck(Cards c)
    {
        return (c.id == this.id && this.place == 5);
    }
    public void nonManaAdditionCost(Cards c)
    {
        nonManaAdditionCostList.add(new Discard(this));
    }

    @Override
    public boolean useCardPlaceCheckAllowCheck(Cards c)
    {
        return c.id == this.id && c.place == 5 && c.controller == this.controller;
    }
    @Override
    public boolean useCardPlaceCheckAllow(Cards c,boolean res)
    {
        return res || (c.id == this.id && c.place == 5 && c.controller == this.controller);
    }

    public void resolved()
    {
        new TapTargetCreature(this).effect(firstTargetList.get(0));
        new DealsDamageToCreature(this,2).effect(secondTargetList.get(0));
    }
}
