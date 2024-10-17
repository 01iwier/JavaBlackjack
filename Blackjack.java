import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Blackjack extends JFrame {
    private Deck deck;
    private Hand playerHand;
    private Hand dealerHand;
    private JPanel playerPanel;
    private JPanel dealerPanel;
    private JButton hitButton;
    private JButton standButton;
    private JButton newGameButton;
    private JLabel statusLabel;
    private JLabel playerTotalLabel;
    private boolean revealDealerCard = false;

    public static void main(String[] args) {
        new Blackjack();
    }

    public Blackjack() {
        deck = new Deck();
        playerHand = new Hand();
        dealerHand = new Hand();

        setSize(1200, 850);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setTitle("Blackjack");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        playerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        dealerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        statusLabel = new JLabel("Game In Progress", JLabel.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 15));
        playerTotalLabel = new JLabel("Total", JLabel.CENTER);
        playerTotalLabel.setFont(new Font("Arial", Font.BOLD, 40));
        
        hitButton = new JButton("Hit");
        standButton = new JButton("Stand");
        newGameButton = new JButton("New Game");

        newGameButton.setEnabled(false);  // Disabled until the game ends

        hitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playerHit();
            }
        });
        
        standButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playerStand();
            }
        });

        newGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetGame();  // Reset the game when "New Game" is pressed
            }
        });

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(hitButton);
        buttonPanel.add(standButton);
        buttonPanel.add(newGameButton);

        // Using BoxLayout for better dynamic spacing for labels
        JPanel handsPanel = new JPanel();
        handsPanel.setLayout(new BoxLayout(handsPanel, BoxLayout.Y_AXIS));

        // Dealer's section (Label and cards)
        JPanel dealerSection = new JPanel();
        dealerSection.setLayout(new BoxLayout(dealerSection, BoxLayout.Y_AXIS));
        dealerSection.add(dealerPanel);

        // Player's section (Label and cards)
        JPanel playerSection = new JPanel();
        playerSection.setLayout(new BoxLayout(playerSection, BoxLayout.Y_AXIS));
        playerSection.add(playerPanel);
        playerSection.add(playerTotalLabel);

        // Adding sections to handsPanel
        handsPanel.add(dealerSection);
        handsPanel.add(playerSection);
        handsPanel.add(playerTotalLabel);

        // Add components to the main frame
        add(handsPanel, BorderLayout.CENTER);       // Player and Dealer hands
        add(buttonPanel, BorderLayout.SOUTH);       // Buttons at the bottom
        add(statusLabel, BorderLayout.NORTH);       // Status at the top
        setVisible(true);
        startGame();
    }

    public void startGame() {
        playerHand.addCard(deck.dealCard());
        dealerHand.addCard(deck.dealCard());
        playerHand.addCard(deck.dealCard());
        dealerHand.addCard(deck.dealCard());
        updateDisplay();
        checkForBlackjack();
    }

    private void checkForBlackjack() {
        boolean playerHasBlackjack = playerHand.getTotal() == 21;
        boolean dealerHasBlackjack = dealerHand.getTotal() == 21;
    
        if (playerHasBlackjack && dealerHasBlackjack) {
            statusLabel.setText("Push! Player and Dealer have Blackjack.");
            endRound();
        } else if (playerHasBlackjack) {
            statusLabel.setText("Player Blackjack!");
            endRound();
        } else if (dealerHasBlackjack) {
            revealDealerCard = true; // Reveal dealer's hidden card if they have Blackjack
            updateDisplay();
            statusLabel.setText("Dealer Blackjack!");
            endRound();
        }
    }

    private void playerHit() {
        playerHand.addCard(deck.dealCard());
        updateDisplay();
        
        if (playerHand.getTotal() > 21) {
            statusLabel.setText("Player Busted! Dealer Wins.");
            hitButton.setEnabled(false);
            standButton.setEnabled(false);
            newGameButton.setEnabled(true);
        }
    }

    private void playerStand() {
        hitButton.setEnabled(false);
        standButton.setEnabled(false);
        revealDealerCard = true;  // Reveal the hidden dealer card
        dealerTurn();
    }

    private void dealerTurn() {
        while (dealerHand.getTotal() < 17) {
            dealerHand.addCard(deck.dealCard());
            updateDisplay();
        }
        updateDisplay();
        checkWinner();
    }

    private void checkWinner() {
        int playerTotal = playerHand.getTotal();
        int dealerTotal = dealerHand.getTotal();

        if (dealerTotal > 21) {
            statusLabel.setText("Dealer Busted! Player Wins!");
        } else if (playerTotal > dealerTotal) {
            statusLabel.setText("Player Wins!");
        } else if (playerTotal < dealerTotal) {
            statusLabel.setText("Dealer Wins!");
        } else {
            statusLabel.setText("Push!");
        }
        newGameButton.setEnabled(true);
    }

    private void updateDisplay() {
        // Clear panels
        playerPanel.removeAll();
        dealerPanel.removeAll();

        // Add cards to player panel
        for (Card card : playerHand.getCards()) {
            playerPanel.add(new JLabel(getCardImage(card)));
        }

        // Add cards to dealer panel
        for (int i = 0; i < dealerHand.getCards().size(); i++) {
            if (i == 0 && !revealDealerCard) {
                // Show face-down card if it's the first card and hasn't been revealed
                dealerPanel.add(new JLabel(getCardBackImage()));
            } else {
                dealerPanel.add(new JLabel(getCardImage(dealerHand.getCards().get(i))));
            }
        }

        playerTotalLabel.setText(String.valueOf(playerHand.getTotal()));

        // Refresh the frame
        playerPanel.revalidate();
        playerPanel.repaint();
        dealerPanel.revalidate();
        dealerPanel.repaint();
    }

    private ImageIcon getCardImage(Card card) {
        String fileName = card.toString() + ".png";
        ImageIcon icon = new ImageIcon("cards/" + fileName);
        Image image = icon.getImage(); // Get the original image
        Image scaledImage = image.getScaledInstance(200, 300, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    private ImageIcon getCardBackImage() {
        ImageIcon icon = new ImageIcon("cards/card_back.png");  // Use the appropriate image for the card back
        Image image = icon.getImage(); // Get the original image
        Image scaledImage = image.getScaledInstance(200, 300, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    private void resetGame() {
        deck = new Deck();  // Create a new deck
        playerHand = new Hand();  // Create new hands for player
        dealerHand = new Hand();  // Create new hands for dealer

        statusLabel.setText("Game In Progress");
        hitButton.setEnabled(true);
        standButton.setEnabled(true);
        newGameButton.setEnabled(false);  // Disable the new game button
        revealDealerCard = false;

        startGame();  // Restart the game
    }

    private void endRound() {
        hitButton.setEnabled(false);
        standButton.setEnabled(false);
        newGameButton.setEnabled(true);
    }
}