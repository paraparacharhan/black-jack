package blackjack;

import java.util.Scanner;

public record Player(String name, Hand hand, Scanner scanner) {
    public Player(String name, Hand hand, Scanner scanner) {
        this.name = name;
        this.hand = hand;
        this.scanner = scanner;
    }

    public String input() {
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("h") || input.equals("s")) {
                return input;
            }
            System.err.println("h or s");
        }
    }
}