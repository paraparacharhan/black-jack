package blackjack;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class Deck {
    private LinkedList<Card> deck = new LinkedList<>();

    public void generate(Random random) {
        deck.clear();
        for (Suit s : Suit.values()) {
            for (Rank r : Rank.values()) {
                deck.add(new Card(r, s));
            }
        }
        Collections.shuffle(deck, random);
    }

    public Card draw() {
        return deck.pop();
    }

    public boolean isEmpty() {
        return deck.isEmpty();
    }
}
