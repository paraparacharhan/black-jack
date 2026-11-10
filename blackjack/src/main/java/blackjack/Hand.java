package blackjack;

import java.util.List;

public class Hand {
    public static final int BLACK_JACK_SCORE = 21;
    private final List<Card> hand;
    private final int totalScore;
    private final boolean blackJack;

    private Hand(List<Card> hand, int totalScore, boolean blackJack) {
        this.hand = hand;
        this.totalScore = totalScore;
        this.blackJack = blackJack;
    }

    public static Hand of(List<Card> hand) {
        return new Hand(hand, calculateTotalScore(hand), isBlackJack(hand, calculateTotalScore(hand)));
    }

    public Hand add(Card card) {
        List<Card> newHand = List.copyOf(this.hand);
        newHand.add(card);
        int totalScore = calculateTotalScore(newHand);
        boolean blackJack = isBlackJack(newHand, this.totalScore);
        return new Hand(newHand, totalScore, blackJack);
    }

    private static int calculateTotalScore(List<Card> hand) {
        int totalScore = hand.stream().mapToInt(c -> c.rank().score()).sum();

        for (Card c : hand) {
            if (totalScore > BLACK_JACK_SCORE && c.rank().score() == 11) {
                totalScore -= 10;
            }
        }
        return totalScore;
    }

    private static boolean isBlackJack(List<Card> hand, int sum) {
        return sum == BLACK_JACK_SCORE && hand.size() == 2;
    }

    // public static int adjustAceValue(List<Card> hand) {
    // // handsOfNumberCards.stream().filter(t -> t > sum && t == 11).map(t ->
    // // 1).toList();
    // int sum = totalScore(hand);
    // // for (int i = 0; i < hand.size(); i++) {
    // // if (sum > BLACK_JACK_SCORE && hand.rank.score() == 11) {
    // // hand.set(i, 1);
    // // sum -= 10;
    // // }
    // // }
    // for (Card c : hand) {
    // if (sum > BLACK_JACK_SCORE && c.rank().score() == 11) {
    // sum -= 10;
    // }
    // }
    // return sum;
    // }

    public boolean isBust() {
        return this.totalScore > BLACK_JACK_SCORE ? false : true;
    }

    public boolean blackJack() {
        return this.blackJack;
    }

    public int totalScore() {
        return this.totalScore;
    }
}