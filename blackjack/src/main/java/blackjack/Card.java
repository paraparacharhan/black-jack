package blackjack;

public record Card(Rank rank, Suit suit) {
    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }
}
