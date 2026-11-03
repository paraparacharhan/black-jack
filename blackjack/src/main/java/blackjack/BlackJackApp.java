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

    public static final int BLACK_JACK_SCORE = 21;
    public static final int DEALER_BORDER_SCORE = 17;

    public static void drawCard(LinkedList<Integer> numberDeck, LinkedList<String> suitDeck,
            List<Integer> handOfNumberCards, List<String> handOfSuitCards) {
        handOfNumberCards.add(numberDeck.pop());
        handOfSuitCards.add(suitDeck.pop());
    }

    public static int totalScore(List<Integer> handsOfNumberCards) {
        return handsOfNumberCards.stream().mapToInt(Integer::intValue).sum();
    }

    public static boolean isBlackJack(List<Integer> handOfNumberCards, List<String> handOfSuitCards, int sum) {
        return sum == BLACK_JACK_SCORE && handOfNumberCards.size() == 2;
    }

    public static int adjustAceValue(List<Integer> handsOfNumberCards, int sum) {
        // handsOfNumberCards.stream().filter(t -> t > sum && t == 11).map(t ->
        // 1).toList();
        for (int i = 0; i < handsOfNumberCards.size(); i++) {
            if (sum > BLACK_JACK_SCORE && handsOfNumberCards.get(i) == 11) {
                handsOfNumberCards.set(i, 1);
                sum -= 10;
            }
        }
        return sum;
    }

    public static void display(String person, List<Integer> handOfNumberCards, List<String> handOfSuitCards, int sum) {
        System.out.print(person + ": ");
        // 多分joiningで綺麗にできる
        for (int i = 0; i < handOfNumberCards.size(); i++) {
            System.out.print(handOfNumberCards.get(i) + " of " + handOfSuitCards.get(i) + ", ");
        }
        System.out.println("(" + sum + ")");
    }

    public static void main(String[] args) {
        System.out.println("Game start!");

        // Deckを生成する
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

        // argumentを変換する
        // Deckをシャッフルする
        try {
            int seed = Integer.parseInt(args[0]);
            Random numberRandom = new Random(seed);
            Random suitRandom = new Random(seed);
            Collections.shuffle(deckNumberList, numberRandom);
            Collections.shuffle(deckSuitList, suitRandom);
        } catch (NumberFormatException e) {
            System.out.println(e);
        }

        // Dealerの手札を生成する
        List<Integer> dealerNumber = new ArrayList<>();
        List<String> dealerSuit = new ArrayList<>();
        int dealerIndex = 0;

        // メソッド切り出し
        drawCard(deckNumberList, deckSuitList, dealerNumber, dealerSuit);
        drawCard(deckNumberList, deckSuitList, dealerNumber, dealerSuit);

        // Dealerの手札を表示する（1枚隠し）
        System.out.println(
                "Dealer: " + dealerNumber.get(dealerIndex) + " of " + dealerSuit.get(dealerIndex) + ", [hidden]");

        // Dealerの合計を計算する
        int dealerSum = totalScore(dealerNumber);

        // Playerの手札を生成する
        List<Integer> myNumber = new ArrayList<>();
        List<String> mySuit = new ArrayList<>();
        int myIndex = 0;

        // メソッド切り出し
        drawCard(deckNumberList, deckSuitList, myNumber, mySuit);
        drawCard(deckNumberList, deckSuitList, myNumber, mySuit);

        // Playerの合計を計算する
        int mySum = totalScore(myNumber);

        // BlackJackか判定し、ステータスとする
        boolean myStatus = isBlackJack(myNumber, mySuit, mySum);

        // Aceが11か1か判定
        mySum = adjustAceValue(myNumber, mySum);

        // Playerの手札を表示する
        display("You", myNumber, mySuit, mySum);

        Scanner scanner = new Scanner(System.in);

        // hitかstandかをするゲームを開始する
        while (true) {
            // BlackJackか判定する
            if (myStatus) {
                break;
            }

            // 入力を受け取り、[h]itか[s]tandかそれ以外かを判定する
            String input = scanner.nextLine();

            // hitの場合
            if (input.equals("h")) {
                // Playerがカードを1枚引く
                System.out.println("[h]it or [s]tand? > h");

                // メソッド切り出し
                drawCard(deckNumberList, deckSuitList, myNumber, mySuit);

                // Playerの合計を計算する
                mySum = totalScore(myNumber);

                // 引いたカードを表示する
                System.out.println(
                        "You: " + myNumber.get(myNumber.size() - 1) + " of " + mySuit.get(mySuit.size() - 1) + " .");

                // Aceが11か1か判定
                mySum = adjustAceValue(myNumber, mySum);

                // Playerの手札を表示する
                display("You", myNumber, mySuit, mySum);

                // Burstか判定する
                if (mySum > BLACK_JACK_SCORE) {
                    System.out.println("You Burst");
                    return;
                }

                // standの場合
            } else if (input.equals("s")) {
                // Playerのターンを抜ける
                System.out.println("[h]it or [s]tand? > s");
                break;
            } else {
                // 不正入力としてやり直す
                System.err.println("Error input");
            }
        }

        // Aceが11か1か判定
        dealerSum = adjustAceValue(dealerNumber, dealerSum);

        // Dealerの手札を表示する
        display("Dealer", dealerNumber, dealerSuit, dealerSum);

        // BrackJackか判定し、ステータスとする
        boolean dealerStatus = isBlackJack(dealerNumber, dealerSuit, dealerSum);

        // Dealerが17以上になるまで続ける
        while (dealerSum < DEALER_BORDER_SCORE && !dealerStatus && !myStatus) {

            // メソッド切り出し
            drawCard(deckNumberList, deckSuitList, dealerNumber, dealerSuit);

            // Dealerの引いたカードを表示する
            System.out.println(
                    "Dealer: " + dealerNumber.get(dealerNumber.size() - 1) + " of "
                            + dealerSuit.get(dealerSuit.size() - 1)
                            + " .");
            // Dealerの合計を計算する
            dealerSum = totalScore(dealerNumber);

            // Aceが11か1か判定
            dealerSum = adjustAceValue(dealerNumber, dealerSum);

            // Dealerの手札を表示する
            display("Dealer", dealerNumber, dealerSuit, dealerSum);
        }

        // Burstか判定する
        if (dealerSum > BLACK_JACK_SCORE) {
            System.out.println("Dealer burst");
            return;
        }

        // 最終結果を表示する
        System.out.print("You: " + mySum + " / ");
        System.out.println("Dealer: " + dealerSum);
        if (mySum > dealerSum) {
            System.out.println("You win!");
        } else if (mySum < dealerSum) {
            System.out.println("Dealer win!");
        } else {
            // 同点でBlackJackがあるか判定
            if (myStatus && dealerStatus) {
                System.out.println("Draw");
            } else if (myStatus) {
                System.out.println("You win!");
            } else if (dealerStatus) {
                System.out.println("Dealer win!");
            } else {
                System.out.println("Draw");
            }
        }
        scanner.close();
    }
}
