package test;

import java.util.ArrayList;
import java.util.Collections;

public class Library
{
    public ArrayList<Cards> cards;
    public Library(Deck d)
    {
        //System.arraycopy(d.cards,0,cards,0,d.cards.length);
        cards = new ArrayList<>(d.cards);
        Collections.shuffle(cards);
    }
}
