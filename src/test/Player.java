package test;

import test.effects.Effect;

import java.util.ArrayList;
import java.util.Random;

public class Player extends Cards
//继承Card使其便于被指定为目标
{
    public int life = 20;
    Deck deck = new Deck();
    Library library;
    public Hands hands = new Hands();
    Graveyard graveyard = new Graveyard();
    String manapool = "";
    public Game game;

    int landUsed = 0;
    int landUsingLimit = 1;

    public void createLibrary()
    {
        library = new Library(deck);
        library.cards.forEach(c ->
        {
            c.controller = this;
            c.place = 1;
        });
    }
    public void drawACard()//replacement prevents this function
    {
        if(library.cards.isEmpty())
        {
            //libraryHasNoCard(this);
            // TODO: 空
            System.out.println("Hoo");
            return;
        }
        Cards c = library.cards.get(0);
        library.cards.remove(0);
        c.place = 2;
        hands.cards.add(c);
        System.out.println(hands.cards.get(0));
    }

    public boolean takePriority()
    {
        //TODO:checkWhatToDo
        //做点什么
        return true;//代表什么都没做
    }


    public Cards chooseOneTargetForEffect( Effect e)
    {
        ArrayList<Cards> targetableCards = new ArrayList<>();
        game.battlefield.cards.forEach(c ->
        {
            if(e.targetDetect(c)) targetableCards.add(c);
        });
        game.exile.cards.forEach(c ->
        {
            if(e.targetDetect(c)) targetableCards.add(c);
        });
        game.stack.cards.forEach(c ->
        {
            if(e.targetDetect(c)) targetableCards.add(c);
        });
        for(Player p:game.player)
        {
            p.graveyard.cards.forEach(c ->
            {
                if(e.targetDetect(c)) targetableCards.add(c);
            });
            p.library.cards.forEach(c ->
            {
                if(e.targetDetect(c)) targetableCards.add(c);
            });
            p.hands.cards.forEach(c ->
            {
                if(e.targetDetect(c)) targetableCards.add(c);
            });
        }
        if(targetableCards.isEmpty())
        {
            return game.nullSource;//非法，回溯
        }
        int r = new Random().nextInt(targetableCards.size());
        return targetableCards.get(r);
    }
    public Cards chooseOneCardFrom(Hands h)//TODO:统一为zone
    {
        return(h.cards.get(new Random().nextInt(h.cards.size())));//玩家选择
    }
    public ArrayList<Cards> sortCardArray(ArrayList<Cards> al)
    {
        //TODO:排序触发顺序
        return al;
    }

/*
    public boolean payByManapool(String manacost)
    {
        String mp = new String(manapool);
        String mc = new String(manacost);
        if(mc.contains("{"))
        {
            int index = mc.indexOf("{");
            String mc1 = mc.substring(0,index) + mc.substring(index+1,index+2) + mc.substring(index+5);
            String mc2 = mc.substring(0,index) + mc.substring(index+3,index+4) + mc.substring(index+5);
            return (payByManapool(mc1) || payByManapool(mc2));
        }
        String C = "W";
        while(!C.equals("C"))
        {
            while(mc.contains(C) && mp.contains(C))
            {
                mc = mc.replaceFirst(C,"");
                mp = mp.replaceFirst(C,"");
            }
            switch (C)
            {
                case "W":   C = "U";break;
                case "U":   C = "B";break;
                case "B":   C = "R";break;
                case "R":   C = "G";break;
                case "G":   C = "C";break;
                default:    System.out.println("payByManapool出错");
            }
        }
        C = biggestColorlessManacost;
        while(!C.equals("0"))
        {
            if(mc.contains(C) && !mp.isEmpty())
            {
                mc = mc.replaceFirst(C,String.valueOf(Integer.valueOf(C) - 1));//将C替换为C-1
                mp = mp.substring(1);//去掉mp中第一个;
            }
            C = C.replaceFirst(C,String.valueOf(Integer.valueOf(C) - 1));
        }
        if(mc.contains("0"))
            mc = mc.replaceFirst("0","");
        if(mc.isEmpty())
        {
            manapool = mp;
            return true;
        }
        return false;
    }
*/
}



