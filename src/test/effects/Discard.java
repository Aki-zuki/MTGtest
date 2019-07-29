package test.effects;

import test.Cards;
import test.cards.VisionaryTuning;

public class Discard extends Effect
{

    public Discard(Cards s)
    {
        source = s;
    }
    public boolean effectAvailableCheck()
    {
        return source.controller.hands.cards.size() > 0;
    }
    @Override
    public void effect(Cards c)
    {
        if(effectAvailableCheck())
        {
            source.controller.game.discardTargetCard(source.controller,source.controller.chooseOneCardFrom(source.controller.hands));
            //暂且把改变去处的判断放在这里
            if(source.name.contains("Visionary Tuning"))//?
            {
                source.resolvedPlace = 6;//置入放逐
            }
        }
    }
}
