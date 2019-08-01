package test;

import test.effects.*;

import java.util.ArrayList;

public class Cards
{
    public String name;
    public String type;
    public String color = "";
    public String manacost;
    public int convertManaCost;
    public int power;
    public int toughness;
    public int oriPower;
    public int oraToughness;


    public Player owner;
    public Player controller;
    public Game game;
    public int id;

    public boolean DeathTouch = false;
    public boolean DoubleStrike = false;
    public boolean FirstStrike = false;
    public boolean Flying = false;
    public boolean Haste = false;
    public boolean HexProof = false;
    public boolean LifeLink = false;
    public boolean Reach = false;
    public boolean Shroud = false;
    public boolean Trample = false;
    public boolean Vigilance = false;

    public String protectFrom = "";
    public String hexProofFrom = "";

    //public String equipCost;
    public Cards attachedCard = null;


    public boolean summonSickness = false;
    public boolean tapped = false;
    public boolean tempTapped = false;
    public boolean produceManaByManaAbility = false;
    public String manaAbilityGenerate = "";
    public int resolvedPlace = 5;//瞬间法术结算后置于何处，默认为坟墓

    public boolean attackable = false;
    public boolean blockable = false;
    public boolean mustAttack = false;
    public boolean mustBlock = false;
    public boolean isAttacking = false;
    public boolean isBlocking = false;
    public boolean isBlocked = false;
    public ArrayList<Cards> blockingList = new ArrayList<>();//围堵者
    public ArrayList<Cards> attackingList = new ArrayList<>();//多挡
    public int blockLimit = 0;
    //public int currBlockCount = 0;
    public Player attackingPlayer;

    public boolean hasDealCombatDamage = false;//如果有先攻但因故未造成先攻，则会造成普通
    public int damageToken = 0;
    public boolean damagedByDeathTouch = false;

    public int plus1plus1Counter = 0;
    public int sub1sub1Counter = 0;

    public boolean insManacost = false;
    public boolean increseManacost = false;
    public boolean decreseManacost = false;
    public boolean insDrawACard = false;
    public boolean hasDrawACard = false;

    public ArrayList<Cards> firstTargetList = new ArrayList<>();
    //咒语或异能的目标，例如战争伤亡就有五类目标
    public ArrayList<Cards> secondTargetList = new ArrayList<>();
    public ArrayList<Cards> thirdTargetList = new ArrayList<>();
    public ArrayList<NonmanaActiveAbility> activeList = new ArrayList<>();

    public boolean nonManaAdditionCostCheck(Cards c){return false;}
    //由于太复杂了，暂且假定同种类型额外费用最多存在一种
    public void nonManaAdditionCost(Cards c){}
    public ArrayList<Effect> nonManaAdditionCostList = new ArrayList<>();

    public boolean targetCheck(){return true;}//注意，默认不需要目标
    public void targetClear()
    {firstTargetList.clear();secondTargetList.clear();thirdTargetList.clear();}

    public boolean withEntersTheBattleFieldCheck(Cards c){return false;}
    public void withEntersTheBattleField(Cards c){}
    public ArrayList<Effect> withEntersTheBattleFieldList = new ArrayList<>();

    public boolean insEntersTheBattleField = false;
    //public boolean hasEntersTheBattleField = false;
    public boolean hasEntersTheBattleFieldCheck(Cards c)
    {
        return false;
    }

//Continuous Effect TODO:把with改成check
    public boolean withCopyEffect(Cards c){ return false;}//this是否会使c产生复制效应
    public void copyEffect(Cards c){}//对其它卡片产生效应
    public ArrayList<Effect> copyEffectList = new ArrayList<>();//受到其他的效应
    public boolean withControlChangingEffect(Cards c){ return false;}
    public void controlEffect(Cards c){}
    public ArrayList<Effect> controlChangingList = new ArrayList<>();
    public boolean withTextChangingEffect(Cards c){ return false;}
    public void textChangingEffect(Cards c){}
    public ArrayList<Effect> textChangingList = new ArrayList<>();
    public boolean withTypeChangingEffect(Cards c){ return false;}
    public void typeChangingEffect(Cards c){}
    public ArrayList<Effect> typeChangingList = new ArrayList<>();
    public boolean withColorChangingEffect(Cards c){ return false;}
    public void colorChangingEffect(Cards c){}
    public ArrayList<Effect> colorChangingList = new ArrayList<>();
    public boolean withAbilityChangingEffect(Cards c){ return false;}
    public void abilityChangingEffect(Cards c){}
    public ArrayList<Effect> abilityChangingList = new ArrayList<>();
//Layer 7

    //...
    //7d,Counters
    public boolean withChangingCounters(Cards c)
    { return c.plus1plus1Counter > 0 || c.sub1sub1Counter > 0;}
    public void changingCounters(Cards c)//此函数应该只能cardsC自己调用
    {
        c.power += (plus1plus1Counter - sub1sub1Counter);
        c.toughness +=(plus1plus1Counter - sub1sub1Counter);
        //死亡状态检测最后再做
    }
    //public ArrayList<Effect> changingCountersList = new ArrayList<>();


    public int place = 0;//所处位置
    //default, library, hands, stack, battlefield, graveyard, exile, sidebox
    public void m2c()
    //根据manacost设置color
    {
        color += manacost.contains("W")?"W":"";
        color += manacost.contains("U")?"U":"";
        color += manacost.contains("B")?"B":"";
        color += manacost.contains("R")?"R":"";
        color += manacost.contains("G")?"G":"";
        if(color.equals("")) color = "C";
    }

    public boolean cardBool(Player P, Cards oriC, String request)
    {
        switch (request)
        {
            case "produceManaByManaAbility": return produceManaByManaAbility;
            case "insDrawACard": return insDrawACard;
            case "withEntersTheBattleField" :return withEntersTheBattleFieldCheck(oriC);
            case "hasEntersTheBattleField" :return hasEntersTheBattleFieldCheck(oriC);
            case "useCardPlaceCheckAllowCheck" : return useCardPlaceCheckAllowCheck(oriC);
            case "useCardPlaceCheckForbiddenCheck" : return useCardPlaceCheckForbiddenCheck(oriC);
            case "nonManaAdditionCostCheck" : return nonManaAdditionCostCheck(oriC);



        }
        return false;
    }
    void  atTheBeginningOfGame()
    {
        System.out.println("A");
    }
    public void resolved(){}
    public String insManacost(Cards c){return "";}
    public String increaseManacost(Cards c, String manacost){return "";}
    public String decreaseManacost(Cards c, String manacost){return "";}
    public void insDrawACard(Player p)
    {
        System.out.println("Kawa");
    }
    public void hasDrawACard(Player p)
    {
        System.out.println("trick");
    }
    public void hasEntersTheBattleField(Cards c) { }
    public void hasDeclareAttacking(Cards c){}
    public void hasDeclareBlocking(Cards c) {}

    public boolean useCardPlaceCheckAllowCheck(Cards c){ return false; }
    public boolean useCardPlaceCheckAllow(Cards c, boolean res) { return false; }
    public boolean useCardPlaceCheckForbiddenCheck(Cards c){ return false; }
    public boolean useCardPlaceCheckForbidden(Cards c, boolean res) {return false; }
}
