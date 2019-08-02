package test.effects;

import test.Cards;

public class Equip extends NonmanaActiveAbility
{
    public Effect equipEffect = null;
    //public Equip(){}
    public Equip(Cards s, String equipCost, Effect eE)
    {
        source = s;
        controller = s.controller;
        activeManacost = equipCost;
        equipEffect = eE;
    }
    public boolean targetDetect(Cards c)//检查目标
    {
        return (this.source.place == 4 && c.place == 4 && c.type.contains("Creature") && !c.Shroud && c.controller == this.source.controller
                //&& this.source.type.contains("Equipment")
               // && (!c.HexProof && targetUniqueDetect(c))
        );
        //待更改
    }
    public void resolved()
    {
        effect(this.firstTargetList.get(0));
    }
    public void effect(Cards c)
    {
        if(!targetDetect(c)) return;
        Cards ori = this.source.attachedCard;
        if(ori != null )
        {
            for(int i = 0; i < ori.abilityChangingList.size();++i)
            {
                Effect e = ori.abilityChangingList.get(i);
                if(e.eid == equipEffect.eid)
                {
                    ori.abilityChangingList.remove(equipEffect);
                    break;
                }
            }
        }
        this.source.attachedCard = c;
        c.abilityChangingList.add(equipEffect);
    }
    public boolean targetCheck()//选择目标
    {
        Cards equipTarget = this.source.controller.chooseOneTargetForEffect(this);//this指示controller
        if(equipTarget != this.source.game.nullSource)//player找得到game
        {
            firstTargetList.add(equipTarget);
            return true;
        }
        return false;
    }

}
