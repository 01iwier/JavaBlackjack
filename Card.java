public class Card {
    private char suit;
    private String value;

    public Card(char suit, String value) {
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
                return 11;
            default:
                return 0;
        }
    }

    public String toString() {
        return value + suit;
    }
    
}
