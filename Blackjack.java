import java.util.Scanner;

public class Blackjack {
    private Deck deck;
    private Hand playerHand;
    private Hand dealerHand;

    public static void main(String[] args) {
        Blackjack gameInstance = new Blackjack();
        gameInstance.startGame();
    }

    public Blackjack() {
        deck = new Deck();
        playerHand = new Hand();
        dealerHand = new Hand();
    }

    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        boolean playerBust = false;


    }
}
