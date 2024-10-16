import java.util.*;

public class Hand {
    private List<Card> hand = new ArrayList<>();

    public void addCard(Card card) {
        hand.add(card);
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

    public void print(boolean hideFirst) {
        for (int i = 0; i < hand.size(); i++) {
            if (i == 0 && hideFirst) {
                System.out.print("__" + " ");
            } else {
                System.out.print(hand.get(i) + " ");
            }
        }
    }

    public void clear() {
        hand.clear();
    }
    
}
