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

    public static void drawCards(LinkedList<Integer> numberDeck, LinkedList<String> suitDeck,
            List<Integer> handOfNumberCards, List<String> handOfSuitCards) {
        handOfNumberCards.add(numberDeck.pop());
        handOfSuitCards.add(suitDeck.pop());
    }

    public static int totalScore(List<Integer> handsOfNumberCards) {
        return handsOfNumberCards.stream().mapToInt(Integer::intValue).sum();
    }

    public static boolean isBlackJack(int sum) {
        return sum == 21;
    }

    public static int checkAceScore(List<Integer> handsOfNumberCards, int sum) {
        // handsOfNumberCards.stream().filter(t -> t > sum && t == 11).map(t ->
        // 1).toList();
        for (int i = 0; i < handsOfNumberCards.size(); i++) {
            if (sum > 21 && handsOfNumberCards.get(i) == 11) {
                handsOfNumberCards.set(i, 1);
                sum -= 10;
            }
        }
        return sum;
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
        drawCards(deckNumberList, deckSuitList, dealerNumber, dealerSuit);
        drawCards(deckNumberList, deckSuitList, dealerNumber, dealerSuit);

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
        drawCards(deckNumberList, deckSuitList, myNumber, mySuit);
        drawCards(deckNumberList, deckSuitList, myNumber, mySuit);

        // Playerの合計を計算する
        int mySum = totalScore(myNumber);

        // BlackJackか判定し、ステータスとする
        boolean myStatus = isBlackJack(mySum);

        mySum = checkAceScore(myNumber, mySum);

        // Playerの手札を表示する
        System.out.print("You: ");
        // 多分joiningで綺麗にできる
        for (int i = 0; i < myNumber.size(); i++) {
            System.out.print(myNumber.get(i) + " of " + mySuit.get(i) + ", ");
        }
        System.out.println("(" + mySum + ")");

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
                drawCards(deckNumberList, deckSuitList, myNumber, mySuit);

                // Playerの合計を計算する
                mySum = totalScore(myNumber);

                // 引いたカードを表示する
                System.out.println(
                        "You: " + myNumber.get(myNumber.size() - 1) + " of " + mySuit.get(mySuit.size() - 1) + " .");

                mySum = checkAceScore(myNumber, mySum);

                // Playerの手札を表示する
                // 多分joiningで綺麗にできる
                System.out.print("You: ");
                for (int i = 0; i < myNumber.size(); i++) {
                    System.out.print(myNumber.get(i) + " of " + mySuit.get(i) + ", ");
                }
                System.out.println("(" + mySum + ")");

                // Burstか判定する
                if (mySum > 21) {
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

        dealerSum = checkAceScore(dealerNumber, dealerSum);

        // Dealerの手札を表示する
        System.out.print("Dealer: ");
        for (int i = 0; i < dealerNumber.size(); i++) {
            System.out.print(dealerNumber.get(i) + " of " + dealerSuit.get(i) + ", ");
        }
        System.out.println("(" + dealerSum + ")");

        // BrackJackか判定し、ステータスとする
        boolean dealerStatus = isBlackJack(dealerSum);

        // Dealerが17以上になるまで続ける
        while (dealerSum < 17 && !dealerStatus && !myStatus) {

            // メソッド切り出し
            drawCards(deckNumberList, deckSuitList, dealerNumber, dealerSuit);

            // Dealerの引いたカードを表示する
            System.out.println(
                    "Dealer: " + dealerNumber.get(dealerNumber.size() - 1) + " of "
                            + dealerSuit.get(dealerSuit.size() - 1)
                            + " .");
            // Dealerの合計を計算する
            dealerSum = totalScore(dealerNumber);

            dealerSum = checkAceScore(dealerNumber, dealerSum);

            // Dealerの手札を表示する
            System.out.print("Dealer: ");
            for (int i = 0; i < dealerNumber.size(); i++) {
                System.out.print(dealerNumber.get(i) + " of " + dealerSuit.get(i) + ", ");
            }
            System.out.println("(" + dealerSum + ")");
        }

        // Burstか判定する
        if (dealerSum > 21) {
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
