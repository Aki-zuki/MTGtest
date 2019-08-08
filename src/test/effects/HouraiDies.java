package test.effects;

import test.Cards;

public class HouraiDies extends Effect
{
    public HouraiDies(Cards s)
    {
        super(s);
    }
    public boolean effectAvailableCheck()
    {
        return source.place == 4;//因为将复制品放入坟墓场，本体place没变
    }
    @Override
    public void effect(Cards c)
    {
        if(effectAvailableCheck())
        {
            //TODO:把controller改成owner
           source.controller.game.removeCardFrom(source.controller,source);
           source.place = 3;
           source.controller.library.cards.add(source);
           source.controller.shuffleLibrary();
        }
    }
}
