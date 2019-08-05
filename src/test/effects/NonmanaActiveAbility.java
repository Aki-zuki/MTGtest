package test.effects;

import test.Cards;
import java.util.ArrayList;

public class NonmanaActiveAbility extends Effect
{
    public NonmanaActiveAbility(){}
    public NonmanaActiveAbility(Cards s)
    {
        super(s);
    }
    public boolean needTap = false;
    public String activeManacost;
    public ArrayList<Effect> nonManaActiveCostList = new ArrayList<>();//假设只有自己会产生其他费用
    public boolean targetCheck(){return true;}//注意，默认不需要目标
    public void effect(Cards c){};
}
