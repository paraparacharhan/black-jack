package blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Hello world!
 */
public class BlackJackApp {
    public static void main(String[] args) {
        System.out.println("Game start!");

        List<String> suit = List.of("Hearts", "Spades", "Clubs", "Diamonds");
        LinkedList<Integer> deckNumberList = new LinkedList<>();
        LinkedList<String> deckSuitList = new LinkedList<>();

        for (int i = 0; i < 52; i++) {
            deckNumberList.add(i % 13 + 1);
            deckSuitList.add(suit.get(i / 13));
        }
        // System.out.println(deckNumberList);
        // System.out.println(deckNumberList.size());
        // System.out.println(deckSuitList);
        // System.out.println(deckSuitList.size());

        try {
            int seed = Integer.parseInt(args[0]);
            Random numberRandom = new Random(seed);
            Random suitRandom = new Random(seed);
            Collections.shuffle(deckNumberList, numberRandom);
            Collections.shuffle(deckSuitList, suitRandom);
        } catch (NumberFormatException e) {
            System.out.println(e);
        }
        // System.out.println(deckNumberList);
        // System.out.println(deckNumberList.size());
        // System.out.println(deckSuitList);
        // System.out.println(deckSuitList.size());
        List<Integer> dealerNumber = new ArrayList<>();
        List<String> dealerSuit = new ArrayList<>();
        int dealerIndex = 0;

        dealerNumber.add(deckNumberList.pop());
        dealerSuit.add(deckSuitList.pop());
        dealerNumber.add(deckNumberList.pop());
        dealerSuit.add(deckSuitList.pop());
        System.out.println(
                "Dealer: " + dealerNumber.get(dealerIndex) + " of " + dealerSuit.get(dealerIndex) + ", [hidden]");

        List<Integer> myNumber = new ArrayList<>();
        List<String> mySuit = new ArrayList<>();
        int myIndex = 0;

        myNumber.add(deckNumberList.pop());
        mySuit.add(deckSuitList.pop());
        myNumber.add(deckNumberList.pop());
        mySuit.add(deckSuitList.pop());

        int mySum = myNumber.get(myIndex) + myNumber.get(myIndex + 1);

        System.out.println(
                "You: " + myNumber.get(dealerIndex) + " of " + mySuit.get(dealerIndex) + ", "
                        + myNumber.get(dealerIndex + 1) + " of " + mySuit.get(dealerIndex + 1) + " ("
                        + mySum + ")");

        System.out.print("[h]it or [s]tand? > ");

    }
}
