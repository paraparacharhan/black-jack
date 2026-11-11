package blackjack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Hello world!
 */
public class BlackJackApp {
    public static void main(String[] args) {
        Random random = new Random();
        if (args.length > 0) {
            try {
                int seed = Integer.parseInt(args[0]);
                random = new Random(seed);
            } catch (NumberFormatException e) {
                System.out.println(e);
            }
        }

        Deck deck = new Deck();
        deck.generate(random);

        BlackJack blackJack = new BlackJack();
        System.out.println("Game start!");
        blackJack.play(deck);

    }
}
