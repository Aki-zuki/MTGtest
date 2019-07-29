package test.effects;

import test.Cards;

public class Equip extends Effect
{
    public boolean targetDetect(Cards c)
    {
        return (this.place == 4 && c.place == 4 && c.type.contains("Creature") && !c.Shroud && c.controller == this.controller //&& this.source.type.contains("Equipment")
                && (!c.HexProof && targetUniqueDetect(c)));
        //待更改
    }
    public void effect(Cards c)
    {
        if(!targetDetect(c)) return;
        this.source.attachedCard = c;
    }
}
