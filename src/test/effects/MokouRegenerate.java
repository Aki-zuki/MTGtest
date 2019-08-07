package test.effects;

import test.Cards;
import test.cards.Insect;

public class MokouRegenerate extends NonmanaActiveAbility
{
    public MokouRegenerate(){needTap = true;}
    public MokouRegenerate(Cards s)
    {
        super(s);
        activeManacost = "R";
    }
    public void resolved()
    {
        this.effect(null);
    }
    public void effect(Cards c)
    {
        this.source.regenerate = true;
    }
}
