package test.effects;

import test.Cards;
import test.Player;

public class Effect extends Cards//可以找到操控者
{
    public Cards source = null;
    public Player controller;
    public Effect(Cards s)
    {
        source = s;
        controller = s.controller;
    }
    public Effect(){}
    public void effect(Cards c) { }
    public boolean effectAvailableCheck()
    {
        return true;//默认有效
    }
    public boolean targetDetect(Cards c){return false;}
    public boolean targetUniqueDetect(Cards c)
    {
        if(c.protectFrom.equals("") && c.hexProofFrom.equals("")) return true;
        else//假设只反同类型，例如反白黑，而不允许反白色吸血鬼等
        {
            if(c.protectFrom.contains("Everything") || c.hexProofFrom.contains("Everything")) return false;
            else if(c.protectFrom.contains("White") || c.protectFrom.contains("Blue") || c.protectFrom.contains("Black")
                    || c.protectFrom.contains("Red") || c.protectFrom.contains("Green") ||
                    c.hexProofFrom.contains("White") || c.hexProofFrom.contains("Blue") || c.hexProofFrom.contains("Black")
                    || c.hexProofFrom.contains("Red") || c.hexProofFrom.contains("Green"))
            {
                if(c.protectFrom.contains("White") && this.source.color.contains("W")) return false;
                if(c.protectFrom.contains("Blue") && this.source.color.contains("U")) return false;
                if(c.protectFrom.contains("Black") && this.source.color.contains("B")) return false;
                if(c.protectFrom.contains("Red") && this.source.color.contains("R")) return false;
                if(c.protectFrom.contains("Green") && this.source.color.contains("G")) return false;
                if(c.protectFrom.contains("Colorless") && this.source.color.contains("C")) return false;
                if(c.hexProofFrom.contains("White") && this.source.color.contains("W")) return false;
                if(c.hexProofFrom.contains("Blue") && this.source.color.contains("U")) return false;
                if(c.hexProofFrom.contains("Black") && this.source.color.contains("B")) return false;
                if(c.hexProofFrom.contains("Red") && this.source.color.contains("R")) return false;
                if(c.hexProofFrom.contains("Green") && this.source.color.contains("G")) return false;
                if(c.hexProofFrom.contains("Colorless") && this.source.color.contains("C")) return false;
                return true;
            }
           /* else if(c.protectFrom.contains("Creature") || c.protectFrom.contains("Instant") || c.protectFrom.contains("Sorcery")
                    || c.protectFrom.contains("Artifact") || c.protectFrom.contains("Enchantment") || c.protectFrom.contains("Planeswalker")
            ||c.hexProofFrom.contains("Creature") || c.hexProofFrom.contains("Instant") || c.hexProofFrom.contains("Sorcery")
                || c.hexProofFrom.contains("Artifact") || c.hexProofFrom.contains("Enchantment") || c.hexProofFrom.contains("Planeswalker"))
            {
                return c.type.
            }*/
            else
            {
                return !(this.source.type.contains(c.hexProofFrom) || this.source.type.contains(c.protectFrom));
            }
        }
    }
    //public boolean isLegal(Cards c){return false;}
}
