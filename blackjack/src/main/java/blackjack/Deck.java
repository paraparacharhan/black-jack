package blackjack;

import java.util.Collections;
import java.util.LinkedList;

public class Deck {
    private LinkedList<Card> deck = new LinkedList<>();

    public void generate() {
        for (Suit s : Suit.values()) {
            for (Rank r : Rank.values()) {
                deck.add(new Card(r, s));
            }
        }
        Collections.shuffle(deck);
    }
}
