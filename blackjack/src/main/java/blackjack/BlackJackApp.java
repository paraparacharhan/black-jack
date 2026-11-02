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
        System.out.println("Game start!");

        List<String> suit = List.of("Hearts", "Spades", "Clubs", "Diamonds");
        LinkedList<Integer> deckNumberList = new LinkedList<>(
                Arrays.asList(11, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10,
                        11, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10,
                        11, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10,
                        11, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10));
        LinkedList<String> deckSuitList = new LinkedList<>();

        for (int i = 0; i < 52; i++) {
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
        int dealerSum = dealerNumber.get(0) + dealerNumber.get(1);

        List<Integer> myNumber = new ArrayList<>();
        List<String> mySuit = new ArrayList<>();
        int myIndex = 0;

        myNumber.add(deckNumberList.pop());
        mySuit.add(deckSuitList.pop());
        myNumber.add(deckNumberList.pop());
        mySuit.add(deckSuitList.pop());
        int mySum = myNumber.get(myIndex) + myNumber.get(myIndex + 1);
        boolean myStatus = false;
        if (mySum == 21) {
            myStatus = true;
        }
        // int mySum = myNumber.get(myIndex) + myNumber.get(myIndex + 1);

        // System.out.println(
        // "You: " + myNumber.get(dealerIndex) + " of " + mySuit.get(dealerIndex) + ", "
        // + myNumber.get(dealerIndex + 1) + " of " + mySuit.get(dealerIndex + 1) + " ("
        // + mySum + ")");
        for (int i = 0; i < myNumber.size(); i++) {
            if (mySum > 21 && myNumber.get(i) == 11) {
                myNumber.set(i, 1);
                mySum -= 10;
            }

        }

        System.out.print("You: ");
        // 多分joiningで綺麗にできる
        for (int i = 0; i < myNumber.size(); i++) {
            System.out.print(myNumber.get(i) + " of " + mySuit.get(i) + ", ");
        }
        System.out.println("(" + mySum + ")");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            if (myStatus) {
                break;
            }
            String input = scanner.nextLine();
            if (input.equals("h")) {
                System.out.println("[h]it or [s]tand? > h");
                myNumber.add(deckNumberList.pop());
                mySuit.add(deckSuitList.pop());

                mySum += myNumber.get(myNumber.size() - 1);
                System.out.println(
                        "You: " + myNumber.get(myNumber.size() - 1) + " of " + mySuit.get(mySuit.size() - 1) + " .");
                for (int i = 0; i < myNumber.size(); i++) {
                    if (mySum > 21 && myNumber.get(i) == 11) {
                        myNumber.set(i, 1);
                        mySum -= 10;
                    }

                }
                // 多分joiningで綺麗にできる
                System.out.print("You: ");
                for (int i = 0; i < myNumber.size(); i++) {
                    System.out.print(myNumber.get(i) + " of " + mySuit.get(i) + ", ");
                }
                System.out.println("(" + mySum + ")");
                if (mySum > 21) {
                    System.out.println("You Burst");
                    return;
                }
            } else if (input.equals("s")) {
                System.out.println("[h]it or [s]tand? > s");
                break;
            } else {
                System.err.println("Error input");
            }
        }

        for (int i = 0; i < dealerNumber.size(); i++) {
            if (dealerSum > 21 && dealerNumber.get(i) == 11) {
                dealerNumber.set(i, 1);
                dealerSum -= 10;
            }

        }
        System.out.print("Dealer: ");
        for (int i = 0; i < dealerNumber.size(); i++) {
            System.out.print(dealerNumber.get(i) + " of " + dealerSuit.get(i) + ", ");
        }
        System.out.println("(" + dealerSum + ")");

        boolean dealerStatus = false;
        if (dealerSum == 21) {
            dealerStatus = true;
        }
        while (dealerSum < 17 && !dealerStatus && !myStatus) {

            dealerNumber.add(deckNumberList.pop());
            dealerSuit.add(deckSuitList.pop());

            System.out.println(
                    "Dealer: " + dealerNumber.get(dealerNumber.size() - 1) + " of "
                            + dealerSuit.get(dealerSuit.size() - 1)
                            + " .");
            dealerSum += dealerNumber.get(dealerNumber.size() - 1);

            for (int i = 0; i < dealerNumber.size(); i++) {
                if (dealerSum > 21 && dealerNumber.get(i) == 11) {
                    dealerNumber.set(i, 1);
                    dealerSum -= 10;
                }

            }

            System.out.print("Dealer: ");
            for (int i = 0; i < dealerNumber.size(); i++) {
                System.out.print(dealerNumber.get(i) + " of " + dealerSuit.get(i) + ", ");
            }
            System.out.println("(" + dealerSum + ")");
        }
        if (dealerSum > 21) {
            System.out.println("Dealer burst");
            return;
        }

        System.out.print("You: " + mySum + " / ");
        System.out.println("Dealer: " + dealerSum);
        if (mySum > dealerSum) {
            System.out.println("You win!");
        } else if (mySum < dealerSum) {
            System.out.println("Dealer win!");
        } else {
            if (myNumber.size() == 2 && mySum == 21 && dealerNumber.size() == 2 && dealerSum == 21) {
                System.out.println("Draw");
            } else if (myNumber.size() == 2 && mySum == 21) {
                System.out.println("You win!");
            } else if (dealerNumber.size() == 2 && dealerSum == 21) {
                System.out.println("Dealer win!");
            } else {
                System.out.println("Draw");
            }
        }
        scanner.close();
    }
}
