package blackjack;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class BlackJack {
    public static final int BLACK_JACK_SCORE = 21;
    public static final int DEALER_BORDER_SCORE = 17;

    public void play(Deck deck) {
        List<Card> dealerHand = new ArrayList<>();
        dealerHand.add(deck.draw());
        dealerHand.add(deck.draw());

        Dealer dealer = new Dealer("Dealer", Hand.of(dealerHand));

        displayDealerFirstHand(dealer);

        List<Card> playerHand = new ArrayList<>();
        playerHand.add(deck.draw());
        playerHand.add(deck.draw());

        Scanner scanner = new Scanner(System.in);
        Player player = new Player("You", Hand.of(playerHand), scanner);

        displayHand(player.name(), player.hand());

        while (!player.hand().isBust() && !player.hand().blackJack()) {
            String playerChoice = player.input();

            if (playerChoice.equals("h")) {

                Card hitCard = deck.draw();
                player.receive(hitCard);

                displayHitCard(player.name(), hitCard);
                displayHand(player.name(), player.hand());
            } else if (playerChoice.equals("s")) {
                break;
            }
        }

        if (player.hand().isBust()) {
            System.out.println("You Bust");
            return;
        }

        displayHand(dealer.name(), dealer.hand());

        while (dealer.hand().totalScore() < DEALER_BORDER_SCORE && !dealer.hand().blackJack()
                && !player.hand().blackJack()) {
            Card drawCard = deck.draw();
            dealer.receive(drawCard);

            displayHitCard(dealer.name(), drawCard);

            displayHand(dealer.name(), dealer.hand());
        }

        if (dealer.hand().isBust()) {
            System.out.println("Dealer bust");
            return;
        }

        displayResult(player, dealer);

    }

    private static void displayResult(Player player, Dealer dealer) {
        System.out.print(player.name() + ": " + player.hand().totalScore() + " / ");
        System.out.println(dealer.name() + ": " + dealer.hand().totalScore());

        if (player.hand().blackJack() && dealer.hand().blackJack()) {
            System.out.println("Draw");
        } else if (player.hand().totalScore() > dealer.hand().totalScore() || player.hand().blackJack()) {
            System.out.println("You win!");
        } else if (player.hand().totalScore() < dealer.hand().totalScore() || dealer.hand().blackJack()) {
            System.out.println("Dealer win!");
        } else {
            System.out.println("Draw");
        }
    }

    private void displayDealerFirstHand(Dealer dealer) {
        System.out.println(dealer.name() + ": " + dealer.hand().cards().stream().limit(1)
                .map(c -> c.rank().label() + " of " + c.suit().label()).collect(Collectors.joining(", "))
                + ", [hidden]");
    }

    private void displayHand(String name, Hand hand) {
        System.out.println(name + ": " + hand.cards().stream()
                .map(c -> c.rank().label() + " of " + c.suit().label()).collect(Collectors.joining(", ")) + " ("
                + hand.totalScore()
                + ")");
    }

    public void displayHitCard(String name, Card card) {
        System.out.println(name + ": " + card.rank().label() + " of " + card.suit().label() + " .");
    }
}
