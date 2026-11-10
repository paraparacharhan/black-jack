package blackjack;

public record Dealer(String name, Hand hand) {
    public Dealer(String name, Hand hand) {
        this.name = name;
        this.hand = hand;
    }
}