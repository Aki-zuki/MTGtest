package test.effects;

import test.Cards;

public class TargetCreature extends Effect
{
    public TargetCreature(Cards s)
    {
        super(s);
    }
    

    
    
    public boolean targetDetect(Cards c)//不考虑卡娅等效应（太麻烦
    {
        return (c.place == 4 && c.type.contains("Creature") && !c.Shroud && targetUniqueDetect(c)
                && (!c.HexProof || c.controller == this.controller));
        //TODO: 保护的话自己也不行
    }
}
