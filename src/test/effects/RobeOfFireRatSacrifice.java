package test.effects;

import test.Cards;

import java.util.ArrayList;

public class RobeOfFireRatSacrifice extends NonmanaActiveAbility
{
    int choose = -1;        //0代表打5，1代表打2

    public RobeOfFireRatSacrifice(){}
    public RobeOfFireRatSacrifice(Cards s)
    {
        nonManaActiveCostList.add(new SacrificeItself(s));
        source = s;
        controller = s.controller;
        activeManacost = "1RR";
    }
    //public ArrayList<Effect> nonManaActiveCostList = new ArrayList<Effect>();//假设只有自己会产生其他费用
    public boolean targetDetect(Cards c)
    {
        return (c.place == 4 && c.type.contains("Creature") && !c.Shroud && targetUniqueDetect(c)
                && ((!c.HexProof) || c.controller == this.controller));
    }
    public boolean targetCheck()
    {
        int res = this.source.game.playerChooseFromTwoOptions(this.source.controller,this.source,this.source);
        if(res == 1)//群体
        {
            choose = res;
            return true;
        }
        Cards c = this.source.controller.chooseOneTargetForEffect(this);
        if(c != this.source.game.nullSource)
        {
            firstTargetList.add(c);
            choose = res;
            return true;
        }
        else
        {
            return false;//通过上面函数选出来的一定满足下面条件
        }
                //(c.place == 4 && c.type.contains("Creature") && !c.Shroud && targetUniqueDetect(c)
            //&& ((!c.HexProof) || c.controller == this.controller));
    }
    public void resolved()
    {
        if(choose == 0)
            this.effect(firstTargetList.get(0));
        else
            this.effect(null);
    }

    public void effect(Cards c)//进入堆叠
    {
        if(choose == 0)
        {
            if(!targetDetect(c)) return;
            new DealsDamageToTargetCreature(this.source,5).effect(c);
        }
        else
        {
            this.source.controller.game.battlefield.cards.forEach(
                    d-> new DealsDamageToCreature(this.source,2).effect(d)
            );
        }
    };
}
