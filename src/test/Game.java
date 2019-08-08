package test;


import test.cards.*;
import test.effects.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.TreeSet;

import static jdk.nashorn.internal.objects.NativeMath.max;
import static jdk.nashorn.internal.objects.NativeMath.min;

public class Game
{

    int cardId = 0;
    Player[] player = new Player[2];
    Player activePlayer = player[0];//主动牌手
    public Battlefield battlefield = new Battlefield();
    public Exile exile = new Exile();
    public Stack stack = new Stack();

    public Cards nullSource = new Cards();
    //public ArrayList<Cards> responseCardsArrayList = new ArrayList<>();
    //public TreeSet<Cards> responseCardsTreeSet = new TreeSet<>(

    boolean isResoving = false;
    public String biggestColorlessManacost = "9";

    public void test()
    {
        player[0] = new Player();
        player[0].game = this;
        player[1] = new Player();
        player[1].game = this;
        for(int i = 0; i < 10;++i)
        {
            Cards c = new Plain();
            c.game = this;
            c.id = cardId++;
            player[0].deck.cards.add(c);
            c = new Gedama();
            c.game = this;
            c.id = cardId++;
            player[0].deck.cards.add(c);
            c = new Island();
            c.game = this;
            c.id = cardId++;
            //player[1].deck.cards.add(c);
            c = new VillageTeacher();
            c.game = this;
            c.id = cardId++;
            player[1].deck.cards.add(c);
        }
        for(int i = 0; i < 2;++i)
        {
            player[i].createLibrary();
        }

        //Cards t = new RobeOfFireRat();
        Cards t = new NightBug();
        t.place = 2;
        t.controller = player[1];
        t.game = this;
        t.id = cardId++;
        player[1].hands.cards.add(t);
        t = new VisionaryTuning();
        t.place = 5;
        t.controller = player[1];
        t.game = this;
        t.id = cardId++;
        player[1].graveyard.cards.add(t);
        t = new Chen();
        t.place = 2;
        t.controller = player[1];
        t.game = this;
        t.id = cardId++;
        player[1].hands.cards.add(t);

        toBattleField(player[1],new Mountain());
        toBattleField(player[1],new Forest());
        toBattleField(player[1],new Forest());
        toBattleField(player[1],new Forest());
        toBattleField(player[1],new Mokou(player[1]));
        toBattleField(player[1],new Mountain());
        toBattleField(player[1],new Mountain());
        toBattleField(player[1],new Mountain());
        toBattleField(player[1],new Mountain());
        toBattleField(player[1],new Mountain());


        stateUpdate();
        takeTurn(player[1]);
        /*
        for(int i = 0; i < 3;++i)
        {
            takeTurn(player[0]);
            stateUpdate();
            takeTurn(player[1]);
            stateUpdate();
        }*/
        System.out.println(battlefield.cards.size());

        System.out.println("www");

        System.out.println("www");
        /*
        */
    }
    void toBattleField(Player p,Cards t)//测试用函数
    {
        t.place = 4;
        t.controller = p;
        t.game = this;
        t.withEntersTheBattleField(t);
        t.id = cardId++;
        battlefield.cards.add(t);
    }

    public void takeTurn(Player p)
    {
        activePlayer = p;
        untapPhase(p);
        //响应
        unkeepPhase(p);
        //响应
        drawPhase(p);
        //响应
        System.out.println("www");

        mainPhase(p);
        //响应
        stateUpdate();
        battlePhase(p);
        //响应
        mainPhase(p);
        //响应
        endPhase(p);
        //响应
        endingTurn(p);//状态检查
    }
    public void untapPhase(Player p)
    {
        //不可响应
        //替代式
        p.landUsed = 0;
        battlefield.cards.forEach(c->
            {
                if(c.controller == p)
                {
                    //替代式和阻碍式
                    c.tapped = false;
                    c.hasDealCombatDamage = false;
                    c.summonSickness = false;
                    //触发式
                }
            }

        );
    }
    public void unkeepPhase(Player p)
    {
        //响应
        priorityExchange();
    }
    public void drawPhase(Player p)
    {
        //响应
        //替代
        priorityExchange();
        drawACard(p);
    }
    public void mainPhase(Player p)
    {
        //响应 触发
        //TODO： 出牌
        //自动情况下，尝试用每一张牌
        priorityExchange();
        int size = p.hands.cards.size()-1;
        for(int i = 0; i < size;++i)
        {
            if(useACard(p,p.hands.cards.get(i)))
            {
                i--;
                size--;

            }
        }
        //if(!p.graveyard.cards.isEmpty())
        //    useACard(p,p.graveyard.cards.get(0));

        if(!battlefield.cards.get(4).activeList.isEmpty())
        {
            activeNonmanaAbility(p,battlefield.cards.get(4).activeList.get(0));
            activeNonmanaAbility(p,battlefield.cards.get(4).activeList.get(1));
        }
    }
    public void endPhase(Player p)
    {
        priorityExchange();

        //响应 触发
    }
    public void endingTurn(Player p)
    {
        //清除伤害标记
        removeDamageCounter();
        //弃牌
        //替代式
        if(p.hands.cards.size() > 7)
        {
            discardHands(p,p.hands.cards.size() - 7);
        }
    }

    public void battlePhase(Player p)
    {
        //响应
        battleStartPhase();
        //响应
        ArrayList<Cards> attackerList = attackDeclarePhase(p);
        //相应
        if(!attackerList.isEmpty())
        {
            Player opponent = p == player[0]?player[1]:player[0];
            blockDeclarePhase(opponent,attackerList);
            //响应
            firstStrikeDamagePhase();
            //响应
            normalDamagePhase();
        }
        //响应
        battleEndPhase();
    }
    public void battleStartPhase()
    {
        //触发
        //响应
    }
    public void battleEndPhase()
    {
        //触发
        //响应
        battlefield.cards.forEach(c ->
        {
            c.isBlocking = c.isAttacking = c.isBlocked = false;
            c.attackingList.clear();
            c.blockingList.clear();
            //attackable和blockable不用在此处调整
        });
        return;
    }
    public ArrayList<Cards> attackDeclarePhase(Player p)
    {
        battlefield.cards.forEach(c ->
        {
            c.attackable = c.controller == p && c.type.contains("Creature") && !c.tapped && (!c.summonSickness || c.Haste);
            //阻碍式在此处将attackable设为false
        });
        battlefield.cards.forEach(c ->
        {
            if(c.attackable)
            {
                if(c.mustAttack)
                {
                    c.isAttacking = true;
                }
                //玩家选择 TODO:碰落客
                c.isAttacking = true;
            }
        });
        ArrayList<Cards> attckerList = new ArrayList<>();
        battlefield.cards.forEach(c ->
        {
            if(c.isAttacking)
            {
                attckerList.add(c);
                c.attackingPlayer = p == player[0]?player[1]:player[0];//假设攻击另一位
                if(!c.Vigilance)
                    c.tapped = true;
                //本该多个生物堆叠一同生效，太麻烦
                ArrayList<Cards> responseCardsArrayList = (findEverywhereArrayList(p, c, "hasDeclareAttacking"));
                if(!responseCardsArrayList.isEmpty())
                {
                    if(responseCardsArrayList.size() == 1)
                    {
                        Cards resC = responseCardsArrayList.get(0);
                        resC.hasDeclareAttacking(c);
                    }
                    else
                    {
                        //TODO: 堆叠选择
                    }
                    responseCardsArrayList.clear();
                }
            }
        });
        return attckerList;
    }

    public void chooseToblock(Cards attacker, Cards blocker)
    {
        //可行性分析在都选择后确定
        if(blocker.attackingList.size() < blocker.blockLimit)
        {
            blocker.isBlocking = true;
            //blocker.currBlockCount += 1;
            blocker.attackingList.add(attacker);
            attacker.isBlocked = true;
            attacker.blockingList.add(blocker);
        }
    }
    public void blockDeclarePhase(Player p, ArrayList<Cards> attackerList)
    {
        //前面内容保证attackList不为空
        battlefield.cards.forEach(c ->
        {
            c.blockable = c.controller == p && c.type.contains("Creature") && !c.tapped;
            //阻碍式
        });
        battlefield.cards.forEach(c ->
        {
            if(c.blockable)
            {
                //TODO: 玩家选择阻挡
                chooseToblock(attackerList.get(
                        new Random().nextInt(attackerList.size())
                        //0
                ),c);//尝试随机阻挡一个
            }
        });
        battlefield.cards.forEach(c ->
        {
            if(c.isBlocking)
            {
                ArrayList<Cards> responseCardsArrayList =
                        (findEverywhereArrayList(p, c, "hasDeclareBlocking"));
                if(!responseCardsArrayList.isEmpty())
                {
                    if(responseCardsArrayList.size() == 1)
                    {
                        Cards resC = responseCardsArrayList.get(0);
                        resC.hasDeclareBlocking(c);
                    }
                    else
                    {
                        //TODO: 堆叠选择
                    }
                    responseCardsArrayList.clear();
                }
            }
        });
    }
    public boolean attackLegal(Player p, ArrayList<Cards> attackerList)//TODO:~
    {
        //TODO: 攻击合法性检查
        return true;
    }
    public boolean blockLegal(Player p,ArrayList<Cards> attackerList)
    {
        //TODO: 阻挡合法性检查
        for (int i = 0; i < attackerList.size(); ++i)
        {
            Cards c = attackerList.get(i);
            if (c.Flying && !c.blockingList.isEmpty())
            {
                for (int j = 0; j < c.blockingList.size(); j++)
                {
                    Cards d = c.blockingList.get(j);
                    if (!d.Reach && !d.Flying)
                    {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    public void dealCombatDamageToPlayer(Cards c, Player p, int damage)
    {
        //替代式
        dealDamageToPlayer(c,p, damage);
        c.hasDealCombatDamage = true;
        //触发式
    }
    public void dealDamageToPlayer(Cards c, Player p,int damage)
    {
        //替代
        damage = damage;
        //替代
        p.life -= damage;
        if(c.LifeLink)
        {
            gainLife(c,c.controller,damage);
        }
        //触发
    }
    public void dealCombatDamageToCreature(Cards c, Cards creature,int damage)
    {
        //替代
        dealDamageToCreature(c,creature,damage);
        c.hasDealCombatDamage = true;
        //触发

    }
    public void dealDamageToCreature(Cards c, Cards creature,int damage)
    {
        //替代
        damage = damage;
        //替代
        //阻止
        creature.damageToken += damage;
        creature.damagedByDeathTouch |= c.DeathTouch;
        if(c.LifeLink)
        {
            gainLife(c,c.controller,damage);
        }
        //触发
    }
    public void gainLife(Cards c, Player p,int count)
    {
        //替代
        p.life += count;
        //触发
    }
    public void assignAttackingDamage(Cards c, int damage)//TODO:改为玩家分配
    {
        for(int i = 0; i < c.blockingList.size();++i)
        {
            if(damage <= 0) break;
            Cards d = c.blockingList.get(i);
            if(d.toughness - d.damageToken <= 0 || d.damagedByDeathTouch) continue;//越过已死之人
            if (!c.DeathTouch)
            {
                if (damage >= d.toughness - d.damageToken)
                {
                    dealCombatDamageToCreature(c, d, d.toughness - d.damageToken);
                    damage -= (d.toughness - d.damageToken);
                }
                else
                {
                    dealCombatDamageToCreature(c, d, damage);
                    damage = 0;
                }
            }
            else
            {
                dealCombatDamageToCreature(c, d, 1);//顶部条件保证了damage>=1
                damage -= 1;
            }
        }
        if(damage > 0)
        {
            if(c.Trample)
                dealCombatDamageToPlayer(c,c.attackingPlayer,damage);
            else
            {
                if(!c.blockingList.isEmpty())
                    dealCombatDamageToCreature(c,c.blockingList.get(c.blockingList.size()-1),damage);
                //可能由于先攻而导致无生物在阻挡

            }
            //否则过量damage分配给最后一个
        }
    }
    public void assignBlockingDamage(Cards c, int damage)//TODO:改为玩家分配
    {
        for(int i = 0; i < c.attackingList.size();++i)
        {
            if(damage <= 0) break;
            Cards d = c.attackingList.get(i);
            if (!c.DeathTouch)
            {
                if (damage >= d.toughness - d.damageToken)
                {
                    dealCombatDamageToCreature(c, d, d.toughness - d.damageToken);
                    damage -= (d.toughness - d.damageToken);
                }
                else
                {
                    dealCombatDamageToCreature(c, d, damage);
                    damage = 0;
                }
            }
            else
            {
                dealCombatDamageToCreature(c, d, 1);//顶部条件保证了damage>=1
                damage -= 1;
            }
        }
        if(damage > 0)
        {
            if(!c.blockingList.isEmpty())
                dealCombatDamageToCreature(c,c.attackingList.get(c.attackingList.size()-1),damage);
            damage = 0;
        }
    }
    public void firstStrikeDamagePhase()
    {
        battlefield.cards.forEach(c ->
        {
            if(c.isAttacking)
            {
                if(c.FirstStrike || c.DoubleStrike)
                {
                    if(!c.isBlocked)
                    {
                        //替代式
                        dealCombatDamageToPlayer(c,c.attackingPlayer,c.power);
                    }
                    else
                    {
                        //玩家选择
                        assignAttackingDamage(c,c.power);
                    }
                }
            }
        });
        battlefield.cards.forEach(c ->
        {
            if(c.isBlocking)
            {
                if(c.FirstStrike || c.DoubleStrike)
                {
                    assignBlockingDamage(c,c.power);
                }
            }
        });
        deathCheck();
    }
    public void normalDamagePhase()
    {
        battlefield.cards.forEach(c ->
        {
            if(c.isAttacking)
            {
                if(!c.hasDealCombatDamage || c.DoubleStrike)
                {
                    if(!c.isBlocked)
                    {
                        //替代式
        dealCombatDamageToPlayer(c,c.attackingPlayer,c.power);
    }
                    else
                    {
                        //玩家选择
                        assignAttackingDamage(c,c.power);
                    }
                }
            }
        });
        battlefield.cards.forEach(c ->
        {
            if(c.isBlocking)
            {
                if(!c.hasDealCombatDamage || c.DoubleStrike)
                {
                    assignBlockingDamage(c,c.power);
                }
            }
        });
        deathCheck();
    }

    public void priorityExchange()
    {
        boolean p1pass = false;
        boolean p2pass = false;
        Player p = activePlayer;
        Player q = activePlayer == player[0]?player[1]:player[0];
        while(!p1pass || !p2pass)
        {
            p1pass = false;
            p2pass = false;
            while(!p1pass)
            {
                stateUpdate();
                p1pass = p.takePriority();
            }
            while(!p2pass)
            {
                stateUpdate();
                p2pass = q.takePriority();
                if(!p2pass) p1pass = false;
            }
        }
        if(!isResoving && !stack.cards.isEmpty())
        {
            stackResolve();
        }
    }
    public void addTriggerAbilityToStack(Player p,Cards c,String s)//p代表先做选择的
    {
        Player q = p == player[0]?player[1]:player[0];
        ArrayList<Effect> responseCardsArrayList = findTriggerAbilityEverywhere(p, c, s);//TODO:假设player不会对fEAL产生影响fndvryrrylst
        ArrayList<Effect> pList = new ArrayList<>();
        ArrayList<Effect> qList = new ArrayList<>();
        for (Effect d: responseCardsArrayList)
        {
            if(d.controller == p) pList.add(d);
            else if(d.controller == q) qList.add(d);
            else System.out.println("未知玩家");
        }
        pList = p.sortEffectArray(pList);
        qList = q.sortEffectArray(qList);
        for(Effect d:pList)
        {
            d.place = 3;
            stack.cards.add(d);
        }
        for(Effect d:qList)
        {
            d.place = 3;
            stack.cards.add(d);
        }
    }
    public Cards resetToBasicCard(Cards c)
    {
        Cards temp;
/*
        try{
            temp = c.getClass().newInstance();
            temp.game = c.game;
            temp.id = c.id;
            temp.place = c.place;
            temp.controller = c.controller;
            temp.plus1plus1Counter = c.plus1plus1Counter;
            temp.sub1sub1Counter = c.sub1sub1Counter;
            temp.damageToken = c.damageToken;
            temp.tapped = c.tapped;
            temp.summonSickness = c.summonSickness;
            temp.attachedCard = c.attachedCard;
            //不再清空各list
            temp.copyEffectList = c.copyEffectList;
            temp.withEntersTheBattleFieldList = c.withEntersTheBattleFieldList;
            temp.controlChangingList = c.controlChangingList;
            temp.typeChangingList = c.typeChangingList;
            temp.textChangingList = c.textChangingList;
            temp.colorChangingList = c.colorChangingList;
            temp.abilityChangingList = c.abilityChangingList;
            return temp;
        }
        catch (InstantiationException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        System.out.println("重置错误");
*/
        try{
            temp = c.getClass().newInstance();
            c.power = temp.power;
            c.toughness = temp.toughness;
            c.hexProofFrom = temp.hexProofFrom;
            c.protectFrom = temp.protectFrom;
            return c;
        }
        catch (InstantiationException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        return c;
    }
    public void stateUpdate()
    {
        //TODO:增减landLimit效应等等等等
        int size = battlefield.cards.size();
        int effectLength;
        Cards c;
        Cards d;
        for(int i = 0; i < size;++i)
        {
            c = battlefield.cards.get(i);
            c = resetToBasicCard(c);
            /*for(int j = 0; j < size;++j)
            {
                d = battlefield.cards.get(j);
                if (d.withCopyEffect(c))
                {
                    d.copyEffect(c);
                }
            }*/
            if (!c.copyEffectList.isEmpty())
            {
                effectLength = c.copyEffectList.size();
                for(int k = 0;k < effectLength;++k)
                {
                    Effect e = c.copyEffectList.get(k);
                    e.effect(c);
                }
            }
            //之所以要分着写是因为可能前面的某个效应会给后面的效应带来新的产生源
            //其他的同理...TODO:~
            /*for(int j = 0; j < size;++j)
            {
                d = battlefield.cards.get(j);
                if (d.withAbilityChangingEffect(c))
                {
                    d.abilityChangingEffect(c);
                }
            }*/
            if (!c.abilityChangingList.isEmpty())
            {
                effectLength = c.abilityChangingList.size();
                for(int k = 0;k < effectLength;++k)
                {
                    Effect e = c.abilityChangingList.get(k);
                    e.effect(c);
                }
            }
            //第七层
            //a
            //b
            //c
            //d
            //指示物是特殊的，只需要排除重复的即可
            if(c.withChangingCounters(c))
            {
                if(c.plus1plus1Counter > 0 && c.sub1sub1Counter > 0)
                {
                    if(c.plus1plus1Counter > c.sub1sub1Counter)
                    {
                        c.plus1plus1Counter -= c.sub1sub1Counter;
                        c.sub1sub1Counter = 0;
                    }
                    else
                    {
                        c.sub1sub1Counter -= c.plus1plus1Counter;
                        c.plus1plus1Counter = 0;
                    }
                }
                c.changingCounters(c);
            }

            battlefield.cards.set(i,c);
        }
        deathCheck();
        if(battlefield.cards.size() != size)
            stateUpdate();
    }
    public void deathCheck()
    {
        int size = battlefield.cards.size();
        for (int i = 0; i < size; ++i)
        {
            Cards c = battlefield.cards.get(i);
            if(!c.type.contains("Creature")) continue;
            if (c.damageToken >= c.toughness)
            {
                if(destroy(nullSource, c))
                {
                    size --;
                    i--;
                }
            }
            else if (c.damageToken >= 1 && c.damagedByDeathTouch)
            {
                if(destroy(nullSource, c))
                {
                    size --;
                    i--;
                }
            }
            //if(size != battlefield.cards.size())
            //按理说不会出现需要二次检查的情况
        }
    }
    public boolean destroy(Cards source,Cards target)
    {
        //替代式，消灭失败返回false
        if(target.toughness > 0)//如果没有防御力则重生和不灭都无效
        {
            if(target.Undestruble)
            {
                return false;
            }
            if(target.regenerate)
            {
                target.regenerate = false;
                target.tapped = true;
                target.isBlocking = false;
                target.isAttacking = false;
                target.isBlocked = false;
                target.damageToken = 0;
                target.damagedByDeathTouch = false;
                for (Cards c : battlefield.cards)
                {
                    if(c.attackingList.contains(target))
                        c.attackingList.remove(target);
                    if(c.blockingList.contains(target))
                        c.blockingList.contains(target);
                }
                return false;
            }
        }
        //重生时要将其移出战斗
        ArrayList<Cards> responseList = findEverywhereArrayList(source.controller,target,"withLeavesTheBattleFieldCheck");
        while(!responseList.isEmpty())
        {
            Cards t = responseList.get(0);
            t.withLeavesTheBattleField(target);
            responseList.remove(0);
        }
        while(!target.withLeavesTheBattleFieldList.isEmpty())
        {
            target.withLeavesTheBattleFieldList.get(0).effect(target);
            target.withLeavesTheBattleFieldList.remove(0);
        }
        try{
            Cards temp = target.getClass().newInstance();//应该这么写，追溯的时候去找target不找temp
            temp.place = 5;
            target.controller.graveyard.cards.add(temp);
        }
        catch (InstantiationException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        if(target.isAttacking)
        {
            target.blockingList.forEach(c ->
                    c.attackingList.remove(target));
        }
        if(target.isBlocking)
        {
            target.attackingList.forEach(c ->
                    c.blockingList.remove(target));
        }//将其移出战斗
        removeCardFrom(target.controller,target);
        //触发式
        return true;
    }
    public boolean sacrificeTargetPermanent(Object source, Cards target)
    {
        ArrayList<Cards> responseList = findEverywhereArrayList(target.controller,target,"withLeavesTheBattleFieldCheck");
        while(!responseList.isEmpty())
        {
            Cards t = responseList.get(0);
            t.withLeavesTheBattleField(target);
            responseList.remove(0);
        }
        while(!target.withLeavesTheBattleFieldList.isEmpty())
        {
            target.withLeavesTheBattleFieldList.get(0).effect(target);
            target.withLeavesTheBattleFieldList.remove(0);
        }
        try{
            Cards temp = target.getClass().newInstance();
            temp.place = 5;
            target.controller.graveyard.cards.add(temp);
        }
        catch (InstantiationException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        if(target.isAttacking)
        {
            target.blockingList.forEach(c ->
                    c.attackingList.remove(target));
        }
        if(target.isBlocking)
        {
            target.attackingList.forEach(c ->
                    c.blockingList.remove(target));
        }//将其移出战斗
        removeCardFrom(target.controller,target);
        //触发式
        return true;
    }
    public void removeDamageCounter()
    {
        battlefield.cards.forEach(c ->
        {
            c.damageToken = 0;
            c.damagedByDeathTouch = false;
        });
    }

    public ArrayList<Effect>findTriggerAbilityEverywhere(Player P, Cards oriC,String s)
    {
        ArrayList<Effect> resList = new ArrayList<>();
        battlefield.cards.forEach(c ->
        {
            if (c.cardBool(P, oriC, s)) resList.add(c.getEffect(P, oriC, s));
        });
        exile.cards.forEach(c ->
        {
            if (c.cardBool(P, oriC, s)) resList.add(c.getEffect(P, oriC, s));
        });
        stack.cards.forEach(c ->
        {
            if (c.cardBool(P, oriC, s)) resList.add(c.getEffect(P, oriC, s));
        });
        for (Player p : player)
        {
            p.graveyard.cards.forEach(c ->
            {
                if (c.cardBool(P, oriC, s)) resList.add(c.getEffect(P, oriC, s));
            });
        }
        return resList;
    }

    public ArrayList<Cards> findEverywhereArrayList(Player P,Cards oriC, String s)//TODO: 将其与后续结合
        {
            ArrayList<Cards> responseCardsArrayList = new ArrayList<>();
            battlefield.cards.forEach(c ->
            {
                if(c.cardBool(P,oriC,s))  responseCardsArrayList.add(c);
            });
            exile.cards.forEach(c ->
            {
                if(c.cardBool(P,oriC,s))  responseCardsArrayList.add(c);
            });
            stack.cards.forEach(c ->
            {
                if(c.cardBool(P,oriC,s))  responseCardsArrayList.add(c);
            });
            for(Player p:player)
            {
                p.graveyard.cards.forEach(c ->
                {
                    if(c.cardBool(P,oriC,s))  responseCardsArrayList.add(c);
                });
                // p.library.cards.forEach(c ->
                //{
                //    if(c.cardBool(P,oriC,s))  responseCardsArrayList.add(c);
                //});
                //p.hands.cards.forEach(c ->
                //{
                //    if(c.cardBool(P,oriC,s))  responseCardsArrayList.add(c);
                //});
            }
            return responseCardsArrayList;
        }
    public TreeSet<Cards> findEverywhereTreeSet(Player P,Cards oriC, String s)
    {
        TreeSet<Cards> responseCardsTreeSet = new TreeSet<>(
                new Comparator<Cards>()
            {
                public int compare(Cards c1,Cards c2)
                {
                    String temp1 = c1.manaAbilityGenerate;
                    String temp2 = c2.manaAbilityGenerate;
                    if(temp1.length() != temp2.length()) return temp1.length() - temp2.length();//长的放后面，先产单色
                    if(temp1.contains("W")) return 1;
                    if(temp2.contains("W")) return -1;
                    if(temp1.contains("R")) return 1;
                    if(temp2.contains("R")) return -1;
                    if(temp1.contains("B")) return 1;
                    if(temp2.contains("B")) return -1;
                    if(temp1.contains("U")) return 1;
                    if(temp2.contains("U")) return -1;
                    if(temp1.contains("G")) return 1;
                    if(temp2.contains("G")) return -1;
                    return 1;
                }
            }
        );
        battlefield.cards.forEach(c ->
        {
            if(c.cardBool(P,oriC,s))  responseCardsTreeSet.add(c);
        });
        exile.cards.forEach(c ->
        {
            if(c.cardBool(P,oriC,s))  responseCardsTreeSet.add(c);
        });
        for(Player p:player)
        {
            p.graveyard.cards.forEach(c ->
            {
                if(c.cardBool(P,oriC,s))  responseCardsTreeSet.add(c);
            });
            p.hands.cards.forEach(c ->
            {
                if(c.cardBool(P,oriC,s))  responseCardsTreeSet.add(c);
            });
        }
        return responseCardsTreeSet;
    }

    public boolean startGetManaSource(Player p, String manacost)
    {
        return getManaSource(p,manacost,findEverywhereTreeSet(p,new Cards(),"produceManaByManaAbility"));
    }
    public boolean getManaSource(Player p, String manacost, TreeSet<Cards> responseCardsTreeSet)//暂时假定最多产一费
    {
        String mc = manacost;
        if(mc.contains("{"))
        {
            int index = mc.indexOf("{");
            String mc1 = mc.substring(0,index) + mc.substring(index+1,index+2) + mc.substring(index+5);
            String mc2 = mc.substring(0,index) + mc.substring(index+3,index+4) + mc.substring(index+5);
            return (getManaSource(p,mc1,responseCardsTreeSet) || getManaSource(p,mc2,responseCardsTreeSet));
        }
        for (Cards c : responseCardsTreeSet)
        {
            if(mc.isEmpty())
            {
                break;
            }
            String mag = c.manaAbilityGenerate;
            if (c.place != 4|| c.controller!=p || c.tapped || c.tempTapped) continue;
            //假定横置不产,只在战场产,且不会产复数 TODO:写成Cards独有函数
            if (mag.length() == 1)//只产一色
            {
                if (mc.contains(mag))
                {
                    c.tempTapped = true;
                    mc = mc.replaceFirst(mag,"");//单色不需要回溯
                }
                //continue;
            }
            else if(mag.contains("{") && mag.length() == 5)//产2色
            {
                int index = mag.indexOf("{");
                boolean res = false;
                String c1 = mag.substring(index+1,index+2);
                String c2 = mag.substring(index+3,index+4);
                if(mc.contains(c1))
                {
                    c.tempTapped = true;
                    mc = mc.replaceFirst(c1,"");
                   if(getManaSource(p,mc,responseCardsTreeSet))
                   {
                       return true;
                   }
                   else
                   {
                       mc = mc + c1;//回溯,tempTapped在递归getManaSource已回溯
                   }
                }
                if(mc.contains(c2))
                {
                    c.tempTapped = true;
                    mc = mc.replaceFirst(c2,"");
                    if(getManaSource(p,mc,responseCardsTreeSet))
                    {
                        return true;
                    }
                    else
                    {
                        mc = mc + c2;//回溯,tempTapped在递归getManaSource已回溯
                    }
                }
                //continue;
            }
            else
            {
                //TODO: 三色四色五色仿照2色即可
            }
        }
        //如果有色要求已被满足，则尝试任意颜色要求
        for (Cards c : responseCardsTreeSet)
        {
            if(mc.isEmpty()) break;
            if(mc.contains("W") || mc.contains("U") || mc.contains("B") || mc.contains("R") || mc.contains("G") || mc.contains("C")) break;//不考虑S
            if(c.place != 4|| c.controller!=p || c.tapped || c.tempTapped) continue;//假定横置不产,只在战场产 TODO:写成Cards独有函数~
            String mag = c.manaAbilityGenerate;
            String bcm = biggestColorlessManacost;
            while(Integer.valueOf(mc) != 0)
            {
                if(Integer.valueOf(mc).equals(Integer.valueOf(bcm)))
                {
                    c.tempTapped = true;
                    mc = mc.replaceFirst(bcm,String.valueOf(Integer.valueOf(bcm) - 1));//将C替换为C-1
                    bcm = bcm.replaceFirst(bcm,String.valueOf(Integer.valueOf(bcm) - 1));
                    break;
                }
                else
                {
                    bcm = bcm.replaceFirst(bcm,String.valueOf(Integer.valueOf(bcm) - 1));
                }
            }
            if(Integer.valueOf(mc) == 0)
                mc = mc.replaceFirst("0","");

        }
        if(mc.isEmpty())
        {
            for(Cards cc : responseCardsTreeSet)
            {
                if(cc.tempTapped)
                {
                    cc.tempTapped = false;
                    cc.tapped = true;
                }
            }
            return true;
        }
        else
        {
            for(Cards cc : responseCardsTreeSet)
            {
                if(cc.tempTapped = true)
                {
                    cc.tempTapped = false;
                }
            }

            return false;
        }
    }
    public void removeCardFrom(Player p, Cards c)
    {
        switch (c.place)
        {
            case 1://library
                p.library.cards.remove(c);
                break;
            case 2://hand
                p.hands.cards.remove(c);
                break;
            case 3://stack
                stack.cards.remove(c);
                break;
            case 4://battlefield
                battlefield.cards.remove(c);
                break;
            case 5:
                p.graveyard.cards.remove(c);
                break;
            case 6:
                exile.cards.remove(c);
                break;
            default:
                System.out.println("移除错误");
        }

    }
    public boolean useACard(Player p, Cards c)
    {
        if(!useCardPlaceCheck(p,c)) return false;//区域不允许
        //TODO: 使用时机检测
        if(c.type.contains("Land"))
        {
            if(p.landUsed >= p.landUsingLimit) return false;
            ArrayList<Cards> responseCardsArrayList = findEverywhereArrayList(p,c,"withEntersTheBattleField");
            while(!responseCardsArrayList.isEmpty())
            {
                responseCardsArrayList.get(0).withEntersTheBattleField(c);
                responseCardsArrayList.remove(0);
            }
            removeCardFrom(p,c);
            c.place = 4;
            c.summonSickness = true;//给地ss，但是不让其生效
            battlefield.cards.add(c);

            findEverywhereArrayList(p,c,"hasEntersTheBattleField");
            if(!responseCardsArrayList.isEmpty())
            {
                if(responseCardsArrayList.size() == 1)
                {
                    responseCardsArrayList.get(0).hasEntersTheBattleField(c);
                }
                else
                {
                    //TODO: 堆叠选择
                }
                responseCardsArrayList.clear();
            }
            return true;
        }
        else
        {
            return castACard(p,c);
            //TODO :失败处理
        }

    }
    private boolean useCardPlaceCheck(Player p, Cards c)
    {
        boolean res = c.place == 2 && p == c.controller;
        ArrayList<Cards> responseCardsArrayList = findEverywhereArrayList(p,c,"useCardPlaceCheckAllowCheck");//先允许后否决
        while(!responseCardsArrayList.isEmpty())
        {
            res = responseCardsArrayList.get(0).useCardPlaceCheckAllow(c,res);
            responseCardsArrayList.remove(0);
        }
        responseCardsArrayList = findEverywhereArrayList(p,c,"useCardPlaceCheckForbiddenCheck");//先允许后否决
        while(!responseCardsArrayList.isEmpty())
        {
            res = responseCardsArrayList.get(0).useCardPlaceCheckForbidden(c,res);
            responseCardsArrayList.remove(0);
        }
        return res;
    }
    public boolean castACard(Player p, Cards c)
    {
        if (!c.targetCheck())
        {
            c.targetClear();
            return false;
        }
        //reveal
        //非法术力的额外费用

            ArrayList<Cards> responseCardsArrayList = findEverywhereArrayList(p,c,"nonManaAdditionCostCheck");
            while(!responseCardsArrayList.isEmpty())
            {
                responseCardsArrayList.get(0).nonManaAdditionCost(c);
                responseCardsArrayList.remove(0);
            }
            for(int i = 0; i < c.nonManaAdditionCostList.size();++i)//由于有动态变化的可能性，不建议提前确定size
            {
                Effect e = c.nonManaAdditionCostList.get(i);
                if(!e.effectAvailableCheck())//支付不起
                {
                    c.targetClear();
                    c.nonManaAdditionCostList.clear();
                    return false;
                }
                //c.nonManaAdditionCostList.remove(0);
            }

        String mc = c.manacost;
        responseCardsArrayList = findEverywhereArrayList(p,c,"insManacost");
        if(!responseCardsArrayList.isEmpty())
        {
            if(responseCardsArrayList.size() == 1)
            {
                Cards tc = responseCardsArrayList.get(0);
                mc = tc.insManacost(c);
            }
            else
            {
                //TODO: 堆叠选择
            }
        }
        responseCardsArrayList.clear();
        responseCardsArrayList = findEverywhereArrayList(p,c,"increaseManacost");
        while(!responseCardsArrayList.isEmpty())
        {
            Cards tc = responseCardsArrayList.get(0);
            mc = tc.increaseManacost(c,mc);
            responseCardsArrayList.remove(0);
        }
        responseCardsArrayList.clear();
        responseCardsArrayList = findEverywhereArrayList(p,c,"decreaseManacost");
            while(!responseCardsArrayList.isEmpty())
            {
                Cards tc = responseCardsArrayList.get(0);
                mc = tc.decreaseManacost(c,mc);
                responseCardsArrayList.remove(0);
            }
        if(startGetManaSource(p,mc))
        {
            //支付额外费用
            while(!c.nonManaAdditionCostList.isEmpty())
            {
                c.nonManaAdditionCostList.get(0).effect(c);
                c.nonManaAdditionCostList.remove(0);
            }
            removeCardFrom(p,c);
            c.place = 3;
            stack.cards.add(c);
            priorityExchange();
            return true;
        }
        //不成功需要回溯
        c.targetClear();
        return false;//TODO: 阻止性效应
    }
    public boolean activeNonmanaAbility(Player p,  NonmanaActiveAbility c)//起动式异能与用卡基本一致
    {
        if(c.needTap && (c.tapped || (c.source.type.contains("Creature") && !c.source.Haste && c.source.summonSickness)))
        {
            return false;
        }
        if (!c.targetCheck())
        {
            c.targetClear();
            return false;
        }
        //reveal
        //非法术力的额外费用
        if(!c.nonManaActiveCostList.isEmpty())
        {
            for(int i = 0; i < c.nonManaActiveCostList.size();++i)//由于有动态变化的可能性，不建议提前确定size
            {
                Effect e = c.nonManaActiveCostList.get(i);
                if(!e.effectAvailableCheck())//支付不起
                {
                    c.targetClear();
                    c.nonManaActiveCostList.clear();
                    return false;
                }
                //c.nonManaAdditionCostList.remove(0);
            }
        }

        String mc = c.activeManacost;
        //TODO:增减

        if(startGetManaSource(p,mc))
        {
            //支付额外费用
            if(c.needTap && !c.source.tapped)
            {
                c.source.tapped = true;
            }
            while(!c.nonManaActiveCostList.isEmpty())
            {
                c.nonManaActiveCostList.get(0).effect(c);
                c.nonManaActiveCostList.remove(0);
            }
            //removeCardFrom(p,c);
            c.place = 3;
            stack.cards.add(c);
            //stackResolve();
            return true;
        }
        //不成功需要回溯
        c.targetClear();
        return false;//TODO: 阻止性效应
    }
    public void moveToResolvedPlace(Cards c)
    {
        removeCardFrom(c.controller,c);
        switch (c.resolvedPlace)
        {
            case 1:c.place = 1;c.controller.library.cards.add(c);break;
            case 2:c.place = 2;c.controller.hands.cards.add(c);break;
            //3
            //4
            case 5:c.place = 5;c.controller.graveyard.cards.add(c);break;
            case 6:c.place = 6;c.controller.game.exile.cards.add(c);break;
            default: System.out.println("结算位置报错");
        }
    }
    public boolean stackResolve()
    {
        if(stack.cards.isEmpty()) return true;
        while(!stack.cards.isEmpty())
        {
            //响应,双方让过后
            isResoving = true;
            Cards c = stack.cards.get(stack.cards.size()-1);
            if(c instanceof Effect)
            {
                //有效性检查交给effect自己
                ((Effect) c).resolved();
                removeCardFrom(((Effect) c).controller,c);
                //生效
            }
            else if
            (
                    c.type.contains("Creature") ||
                    c.type.contains("Land") ||
                    c.type.contains("Enchantment") ||
                    c.type.contains("Artifact") ||
                    c.type.contains("Planeswalker")
            )
            {
                //entersTheBattleField TODO: 可能有替代式
               ArrayList<Cards> responseCardsArrayList = findEverywhereArrayList(c.controller,c,"withEntersTheBattleField");
                while(!responseCardsArrayList.isEmpty())
                {
                    responseCardsArrayList.get(0).withEntersTheBattleField(c);
                    responseCardsArrayList.remove(0);
                }
                c.withEntersTheBattleFieldList.forEach(e->
                        e.effect(c));

                removeCardFrom(c.controller,c);
                c.place = 4;
                c.summonSickness = true;
                battlefield.cards.add(c);
                addTriggerAbilityToStack(c.controller,c,"hasEntersTheBattleField");
            }
            else if(c.type.contains("Instant") || c.type.contains("Sorcery"))
            {
                c.resolved();
                moveToResolvedPlace(c);
            }
            else
            {
                System.out.println("未知类型");
                return false;
            }
            priorityExchange();
            //咒语结算过程中没有状态检查W
        }
        isResoving = false;
        return true;
    }

    public void createToken(Player p,Cards c)
    {
        //替代式和阻碍式
        c.controller = p;
        c.game = this;
        c.id = cardId++;
        ArrayList<Cards> responseCardsArrayList = findEverywhereArrayList(c.controller,c,"withEntersTheBattleField");
        while(!responseCardsArrayList.isEmpty())
        {
            responseCardsArrayList.get(0).withEntersTheBattleField(c);
            responseCardsArrayList.remove(0);
        }
        c.withEntersTheBattleFieldList.forEach(e->
                e.effect(c));

        //removeCardFrom(c.controller,c);
        c.place = 4;
        c.summonSickness = true;
        battlefield.cards.add(c);
        addTriggerAbilityToStack(c.controller,c,"hasEntersTheBattleField");
    }
    public void drawACard(Player p)
    {
        ArrayList<Cards> responseCardsArrayList = findEverywhereArrayList(p,
                //有问题的
                new Cards(),
                "insDrawACard");
        if(!responseCardsArrayList.isEmpty())
        {
            if(responseCardsArrayList.size() == 1)
            {
                Cards c = responseCardsArrayList.get(0);
                c.insDrawACard(p);
            }
            else
            {
                //TODO: 堆叠选择
            }
            responseCardsArrayList.clear();
        }
        else
        {
            //System.out.println("wwww");
            p.drawACard();
            //System.out.println("www");
            findEverywhereArrayList(p,new Cards(), "hasDrawACard");
            if(!responseCardsArrayList.isEmpty())
            {
                if(responseCardsArrayList.size() == 1)
                {
                    Cards c = responseCardsArrayList.get(0);
                    c.hasDrawACard(p);
                }
                else
                {
                    //TODO: 堆叠选择
                }
                responseCardsArrayList.clear();
            }
        }
    }
    public void discardHands(Player p, int number)
    {
        //玩家选择
        if(number < p.hands.cards.size())
        {
            ArrayList<Cards> disList = new ArrayList<>();
            while(disList.size() < number)
            {
                //玩家操作
                Random r = new Random();
                disList.add(p.hands.cards.get(r.nextInt(p.hands.cards.size())));//随机弃一张
            }
            while(!disList.isEmpty())
            {
                discardTargetCard(p,disList.get(0));
            }
        }
        else
        {
            while(!p.hands.cards.isEmpty())
            {
                discardTargetCard(p,p.hands.cards.get(0));
            }
        }

    }
    public void discardTargetCard(Player p, Cards c)
    {
        //替代式
        p.graveyard.cards.add(c);
        removeCardFrom(p,c);
        //触发式
    }
    public int playerChooseFromTwoOptions(Player p, Object op1,Object op2)
    {
        //TODO:选啊
        return new Random().nextInt(2);
    }
}
