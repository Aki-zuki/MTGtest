package test;

import test.cards.Forest;
import test.cards.Island;
import test.cards.Mountain;

import java.util.ArrayList;
import java.util.Random;

public class Main
{

    public static void main(String[] args)
    {
        /*for(int i = 0; i < 10;++i)
        {
            Random r = new Random();
            int a = (r.nextInt(6)+1);
            int b = r.nextInt(6)+1;
            if(a+b > 11) i--;
            else System.out.println(a+b);
        }
        int max = 0;
        Random r = new Random();
        for(int i = 0; i <9;++i)
        {
            max += r.nextInt(10)+1;
        }
        System.out.println(max);*/
        ArrayList<Cards> a = new ArrayList<>();
        ArrayList<Cards> b = new ArrayList<>();
        Cards c = new Forest();
        a.add(c);
        b.add(new Mountain());
        b.add(c);
        a.add(new Island());
        a.addAll(b);
        a.remove(c);
        Game g = new Game();
        g.test();
    }
}
