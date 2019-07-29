package test.effects;

import test.Cards;
import test.cards.VisionaryTuning;

public class TapTargetCreature extends Effect

{
    public TapTargetCreature(Cards s)
    {
        super(s);
    }
    @Override
    public void effect(Cards c)
    {
        if(new TargetCreature(this.source).targetDetect(c))//再次检查合法性
            c.tapped = true;//TODO: tap时机响应
    }
}
