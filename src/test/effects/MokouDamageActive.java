package test.effects;

import test.Cards;

public class MokouDamageActive extends NonmanaActiveAbility
{
    public MokouDamageActive(){needTap = true;}
    public MokouDamageActive(Cards s)
    {
        super(s);
        needTap = true;
        activeManacost = "4R";
    }

    @Override
    public boolean targetDetect(Cards c)
    {
        return (c.place == 4 && c.type.contains("Creature") && !c.Shroud && targetUniqueDetect(c)
                && ((!c.HexProof) || c.controller == this.source.controller));
    }

    @Override
    public boolean targetCheck()
    {
        Cards damageTarget = this.controller.chooseOneTargetForEffect
                ((new TargetCreature(this)));
        if(damageTarget != this.source.controller.game.nullSource)
        {
            firstTargetList.add(damageTarget);
            return true;
        }
        return false;
    }

    public void resolved()
    {
        this.effect(firstTargetList.get(0));
    }
    public void effect(Cards c)
    {
        new DealsDamageToTargetCreature(this.source,5).effect(c);
        new DealsDamageToCreature(this.source,5).effect(this.source);//不需要目标
    }
}

