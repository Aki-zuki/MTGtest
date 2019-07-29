package test.effects;

import test.Cards;

public class Haste extends Effect
{
    public void effect(Cards c)
    {
        c.Haste = true;
        //天生就有的不需要这么搞，在每次状态检查中会保留
        //通过起事等方式获得的需要在此记录
    }
}
