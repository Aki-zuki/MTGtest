package test.effects;

import test.Cards;
import test.cards.Insect;

public class NightBugActive extends NonmanaActiveAbility
{
    public NightBugActive(){needTap = true;}
    public NightBugActive(Cards s)
    {
        super(s);
        needTap = true;
        activeManacost = "1";
    }
    public void resolved()
    {
        this.effect(null);
    }
    public void effect(Cards c)
    {
       this.source.controller.game.createToken(this.source.controller,new Insect());
    }
}
