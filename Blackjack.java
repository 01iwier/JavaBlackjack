import java.util.Scanner;

public class Blackjack {
    private Deck deck;
    private Hand playerHand;
    private Hand dealerHand;

    public Blackjack() {
        deck = new Deck();
        playerHand = new Hand();
        dealerHand = new Hand();
    }

    public static void main(String[] args) {
        Blackjack gameInstance = new Blackjack();
        gameInstance.startGame();
    }

    public void print(boolean hide) {
        System.out.print("\nDealer: ");
        dealerHand.print(hide);
        if (hide) {
            System.out.println("(" + dealerHand.getHiddenTotal() + ")");
        } else {
            System.out.println("(" + dealerHand.getTotal() + ")");
        }
        System.out.print("Player: ");
        playerHand.print(false);
        System.out.println("(" + playerHand.getTotal() + ")\n");
    }

    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        boolean quit = false;

        while (!quit) {
            boolean playerBust = false;
            playerHand.clear();
            dealerHand.clear();

            for (int i = 0; i < 2; i++) {
                playerHand.addCard(deck.dealCard());
                dealerHand.addCard(deck.dealCard());
            }

            while (true) {
                print(true);
                if (playerHand.getTotal() > 21) {
                    System.out.println("PLAYER BUST");
                    System.out.println("DEALER WINS\n");
                    playerBust = true;
                    break;
                }
                System.out.println("Hit or Stand? (h/s)");
                String action = scanner.nextLine();
                if (action.equalsIgnoreCase("h")) {
                    playerHand.addCard(deck.dealCard());
                } else if (action.equalsIgnoreCase("s")) {
                    break;
                } else if (action.equalsIgnoreCase("q")) {
                    quit = true;
                    playerBust = true;
                    break;
                }
            }

            if (!playerBust) {
                print(false);
                while (dealerHand.getTotal() < 17) {
                    dealerHand.addCard(deck.dealCard());
                    print(false);
                }
                int dealerTotal = dealerHand.getTotal();
                if (dealerTotal > 21) {
                    print(false);
                    System.out.println("DEALER BUST");
                    System.out.println("YOU WIN!\n");
                } else {
                    int playerTotal = playerHand.getTotal();
                    if (playerTotal > dealerTotal) {
                        System.out.println(playerTotal + " beats " + dealerTotal);
                        System.out.println("YOU WIN!\n");
                    } else if (dealerTotal > playerTotal) {
                        System.out.println(dealerTotal + " beats " + playerTotal);
                        System.out.println("DEALER WINS\n");
                    } else {
                        System.out.println(dealerTotal + " = " + playerTotal);
                        System.out.println("PUSH\n");
                    }
                }
            }
        }
        scanner.close();
    }
}
