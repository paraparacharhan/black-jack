package blackjack;

import java.util.Scanner;

public class Player {
    private final String name;
    private Hand hand;
    private final Scanner scanner;

    public Player(String name, Hand hand, Scanner scanner) {
        this.name = name;
        this.hand = hand;
        this.scanner = scanner;
    }

    public String input() {
        while (true) {
            System.out.print("[h]it or [s]tand? > ");
            String input = scanner.nextLine();
            if (input.equals("h") || input.equals("s")) {
                return input;
            }
            System.err.println("h or s");
        }
    }

    String name() {
        return this.name;
    }

    Hand hand() {
        return this.hand;
    }

    void addHand(Card card) {
        this.hand = this.hand.add(card);
    }
}
