package blackjack;

public class Dealer {
    private final String name;
    private Hand hand;

    public Dealer(String name, Hand hand) {
        this.name = name;
        this.hand = hand;
    }

    String name() {
        return this.name;
    }

    Hand hand() {
        return this.hand;
    }

    void addHand(Card card) {
        this.hand = this.hand.add(card);
    }
}
