package blackjack;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * BlackJack
 */
public class BlackJack {
    public static final int BLACK_JACK_SCORE = 21;
    public static final int DEALER_BORDER_SCORE = 17;

    public void play(Deck deck) {
        List<Card> dealerHand = new ArrayList<>();
        dealerHand.add(deck.draw());
        dealerHand.add(deck.draw());

        // dealer.hand().of(dealerHand);
        Dealer dealer = new Dealer("Dealer", Hand.of(dealerHand));

        System.out.println("dealer~~~");

        List<Card> playerHand = new ArrayList<>();
        playerHand.add(deck.draw());
        playerHand.add(deck.draw());

        Scanner scanner = new Scanner(System.in);
        Player player = new Player("Player", Hand.of(playerHand), scanner);

        System.out.println("You~~~");

        while (!player.hand().isBust() && !player.hand().blackJack()) {
            // if (player.hand().blackJack()) {
            // break;
            // }

            System.out.println("[h]it or [s]tand? > ");
            String choise = player.input();

            if (choise.equals("h")) {
                player.hand().add(deck.draw());

                System.out.println("you~~~");
            } else if (choise.equals("s")) {
                break;
            }
        }

        System.out.println("Dealer~~~");

        while (dealer.hand().totalScore() < DEALER_BORDER_SCORE && !dealer.hand().blackJack()
                && !player.hand().blackJack()) {
            dealer.hand().add(deck.draw());

            System.out.println("Dealer~~~");

            System.out.println("Dealer~~~");
        }

        if (dealer.hand().isBust()) {
            System.out.println("Dealer bust");
            return;
        }

        displayResult(player, dealer);

    }

    public static void displayResult(Player player, Dealer dealer) {
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

}