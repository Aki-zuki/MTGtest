package test.cards;

import test.*;
import test.effects.DrawACard;
import test.effects.Effect;

public class VillageTeacher extends Cards
{
    public VillageTeacher()
    {
        name = "Village Teacher";
        type = "Creature~Human";
        manacost = "2U";
        convertManaCost = 3;
        m2c();
        power = 1;
        toughness = 3;
        blockLimit = 1;

        //hasEntersTheBattleField = true;
    }

    public boolean hasEntersTheBattleFieldCheck(Cards c)
    {
        return c == this && this.place == 4;
    }
    public Effect hasEntersTheBattleField(Cards c)
    {
        return new DrawACard(this.controller);
    }
}
