package blackjack;

public class Player {
    private final String name;
    private Hand hand;

    public Player(String name, Hand hand) {
        this.name = name;
        this.hand = hand;
    }

    public String name() {
        return this.name;
    }
}