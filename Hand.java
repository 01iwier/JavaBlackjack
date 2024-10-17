import java.util.*;

public class Hand {
    private ArrayList<Card> hand = new ArrayList<>();

    public void addCard(Card card) {
        hand.add(card);
    }

    public ArrayList<Card> getCards() {
        return hand;  // New method to return the list of cards
    }

    public int getTotal() {
        int total = 0;
        int numAces = 0;
        
        for (Card card : hand) {
            total += card.getCardValue();
            if (card.getValue().equals("A")) {
                numAces++;
            }
        }
        
        while (total > 21 && numAces > 0) {
            total -= 10;
            numAces--;
        }

        return total;
    }

    public int getHiddenTotal() {
        return hand.getLast().getCardValue();
    }

    public void clear() {
        hand.clear();
    }
    
}
