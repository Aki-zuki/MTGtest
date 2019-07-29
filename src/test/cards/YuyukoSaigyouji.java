package test.cards;

import test.Cards;

public class YuyukoSaigyouji extends Cards
{
    public YuyukoSaigyouji()
    {
        name = "Yuyuko Saigyouji";
        type = "Legendary_Creature~Spirit";
        manacost = "3BBB";
        convertManaCost = 6;
        m2c();
        power = 3;
        toughness = 8;
        blockLimit = 1;

        DeathTouch = true;
    }
    public boolean hasDiesCheck(Cards c)
    {
        return c == this && this.place == 4;
    }
    public void hasDies(Cards c)
    {
        System.out.println("Cherry Bloom");
    }
}
