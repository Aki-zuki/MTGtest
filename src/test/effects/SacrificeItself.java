package test.effects;

import test.Cards;

public class SacrificeItself extends Effect
{

    public SacrificeItself(Cards s)
    {
        source = s;
        controller = s.controller;

    }
    public boolean effectAvailableCheck()
    {
        return source.place == 4;
    }
    @Override
    public void effect(Cards c)
    {
        if(effectAvailableCheck())
        {
            //source.controller.game.discardTargetCard(source.controller,source.controller.chooseOneCardFrom(source.controller.hands));
            source.game.sacrificeTargetPermanent(this.source,this.source);
        }
    }
}
