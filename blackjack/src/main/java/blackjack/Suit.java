package blackjack;

public enum Suit {
    HEARTS("Hearts"),
    SPADES("Spades"),
    CLUBS("Clubs"),
    DIAMONDS("Diamonds");

    private final String label;

    Suit(String label) {
        this.label = label;
    }

    public String label() {
        return label;
    }
}