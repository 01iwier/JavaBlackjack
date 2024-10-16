public class Card {
    private char suit;
    private String value;

    public Card(String value, char suit) {
        this.suit = suit;
        this.value = value;
    }

    public char getSuit() {
        return suit;
    }

    public String getValue() {
        return value;
    }

    public int getCardValue() {
        switch (value) {
            case "2": case "3": case "4": case "5": case "6": case "7": case "8": case "9": case "10":
                return Integer.parseInt(value);
            case "J": case "Q": case "K":
                return 10;
            case "A":
                return 11; // Treat Ace as 11, Use Hand.containsAce() method to reduce by 10 later
            default:
                return 0;
        }
    }

    public String toString() {
        String suitSymbol;
        switch (suit) {
            case 'H':
            suitSymbol = "♥";
            break;
        case 'D':
            suitSymbol = "♦";
            break;
        case 'C':
            suitSymbol = "♣";
            break;
        case 'S':
            suitSymbol = "♠";
            break;
        default:
            suitSymbol = String.valueOf(suit);
        }
        return value + suitSymbol;
    }
    
}
