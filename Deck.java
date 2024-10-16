import java.util.*;

public class Deck {
    private List<Card> cards = new ArrayList<>();

    public Deck() {
        resetDeck();
    }

    public void resetDeck() {
        char[] suits = {'C', 'D', 'H', 'S'};
        String[] values = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};

        for (char suit : suits) {
            for (String value : values) {
                for (int i = 0; i < 6; i++) { // Play with 6 decks
                    cards.add(new Card(suit, value));
                }
            }
        }
        shuffle();
    }

    public Card dealCard() {
        if (!cards.isEmpty()) {
            return cards.removeFirst();
        } else {
            resetDeck();
            return dealCard();
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public void print() {
        for (Card card : cards) {
            System.out.print(card.toString() + " ");
        }
    }
    
}
