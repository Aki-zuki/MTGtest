package test.effects;

import test.Cards;

public class Riot extends Effect
{
    public void effect(Cards c)
    {
        int op = c.game.playerChooseFromTwoOptions(c.controller,c.game.nullSource,c.game.nullSource);
        if(op == 0)
        {
            c.plus1plus1Counter += 1;
            //改变攻防在后面持续性效应
        }
        else
        {
            //c.Haste = true;
            c.abilityChangingList.add(new Haste());
        }
    }
}
