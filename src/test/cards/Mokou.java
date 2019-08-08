package test.cards;

import test.Cards;
import test.Player;
import test.effects.*;
public class Mokou extends Cards
{
    public Mokou()
    {
        name = "Mokou";
        type = "Legendary_Creature~Human";
        manacost = "3RR";
        convertManaCost = 5;
        m2c();
        oriPower = power = 5;
        oraToughness = toughness = 5;
        blockLimit = 1;

        activeList.add(new MokouRegenerate(this));
        activeList.add(new MokouDamageActive(this));
    }
    public Mokou(Player p)
    {
        name = "Mokou";
        type = "Legendary_Creature~Human";
        controller = p;
        manacost = "3RR";
        convertManaCost = 5;
        m2c();
        oriPower = power = 5;
        oraToughness = toughness = 5;
        blockLimit = 1;

        activeList.add(new MokouRegenerate(this));
        activeList.add(new MokouDamageActive(this));

    }
    public boolean hasLeavesTheBattleFieldCheck(Cards c)
    {
        return c.id == this.id;
    }
    public Effect hasLeavesTheBattleField(Cards c)
    {
        return new HouraiDies(c);
    }
}
