package test.effects;

import test.Cards;

public class DrawACard extends Effect
{
    public DrawACard(Cards s)
    {
        super(s);
    }
    public boolean effectAvailableCheck()
    {
        return true;
    }
    public void resolved()
    {
        effect(this.source.controller);
    }
    @Override
    public void effect(Cards c)
    {
        source.controller.game.drawACard(source.controller);
    }
}
