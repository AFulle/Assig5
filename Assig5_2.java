/*
 * Jospeh Cortez
 * Lyndsay Hackett
 * Mohklis Awad
 * Ahdia Fuller
 * 
 */

import javax.swing.*;
import java.util.Random;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;


public class Assig5_2 {

    private static final int NUM_CARDS_PER_HAND = 7;
    private static final int NUM_PLAYERS = 2;
    private static JLabel[] computerLabels = new JLabel[NUM_CARDS_PER_HAND];
    private static JLabel[] humanLabels = new JLabel[NUM_CARDS_PER_HAND];
    private static JLabel[] playedCardsLabels = new JLabel[NUM_PLAYERS];
    private static JLabel[] playLabelText = new JLabel[NUM_PLAYERS];


    private static Card generateRandomCard() {
        Random random = new Random();
        return new Card(GUICard.cardsValues[random.nextInt(GUICard.cardsValues.length)],
                Suit.values()[random.nextInt(GUICard.cardsSuits.length)]);
    }

    public static void main(String[] args) {
        CardTable myCardTable = new CardTable("CardTable", NUM_CARDS_PER_HAND, NUM_PLAYERS);
        myCardTable.setSize(800, 600);
        myCardTable.setLocationRelativeTo(null);
        myCardTable.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        for (int i = 0; i < NUM_CARDS_PER_HAND; i++) {
            computerLabels[i] = new JLabel(GUICard.getBackCardIcon());
            humanLabels[i] = new JLabel(GUICard.getIcon(generateRandomCard()));
            myCardTable.pnlComputerHand.add(computerLabels[i]);
            myCardTable.pnlHumanHand.add(humanLabels[i]);
        }

        playedCardsLabels[0] = new JLabel(GUICard.getIcon(generateRandomCard()));
        playedCardsLabels[1] = new JLabel(GUICard.getIcon(generateRandomCard()));
        playLabelText[0] = new JLabel("Computer", JLabel.CENTER);
        playLabelText[1] = new JLabel("Computer", JLabel.CENTER);
        myCardTable.pnlPlayArea.add(playedCardsLabels[0]);
        myCardTable.pnlPlayArea.add(playedCardsLabels[1]);
        myCardTable.pnlPlayArea.add(playLabelText[0]);
        myCardTable.pnlPlayArea.add(playLabelText[1]);

        myCardTable.setVisible(true);
    }
}

class CardTable extends JFrame {

    private static int MAX_CARDS_PER_HAND = 56;
    private static int MAX_PLAYERS = 2;

    private int numCardsPerHand;
    private int numPlayers;

    public JPanel pnlComputerHand, pnlHumanHand, pnlPlayArea;

    public CardTable(String title, int numCardsPerHand, int numPlayers) throws HeadlessException {
        this.numCardsPerHand = numCardsPerHand;
        this.numPlayers = numPlayers;

        setTitle(title);
        setLayout(new BorderLayout());
        pnlComputerHand = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        pnlComputerHand.setBorder(new TitledBorder("Computer Hand"));
        pnlPlayArea = new JPanel(new GridLayout(2, 2, 5, 0));
        pnlPlayArea.setBorder(new TitledBorder("Playing Area"));
        pnlHumanHand = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        pnlHumanHand.setBorder(new TitledBorder("Your Hand"));

        add(pnlComputerHand, BorderLayout.NORTH);
        add(pnlPlayArea, BorderLayout.CENTER);
        add(pnlHumanHand, BorderLayout.SOUTH);
    }

    public int getNumCardsPerHand() {
        return numCardsPerHand;
    }

    public int getNumPlayers() {
        return numPlayers;
    }
}

class GUICard {

    private static Icon[][] iconCards = new ImageIcon[14][4];
    private static Icon iconBack;
    static boolean iconsLoaded = false;

    static final char[] cardsValues = new char[]{'A', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'X'};
    static final char[] cardsSuits = new char[] {'C', 'D', 'H', 'S'};
    static final String cardBack = "BK";

    static void loadCardIcons() {
        if (iconsLoaded) return;
        for (int k = 0; k < cardsValues.length; k++) {
            for (int j = 0; j < cardsSuits.length; j++)
                iconCards[k][j] = new ImageIcon(String.format("images/%s%s.gif", turnIntIntoCardValue(k), turnIntIntoCardSuit(j)));
        }
        iconBack = new ImageIcon(String.format("images/%s.gif", cardBack));
        iconsLoaded = true;
    }

    static public Icon getIcon(Card card) {
        loadCardIcons();
        return iconCards[valueAsInt(card)][suitAsInt(card)];
    }

    static public Icon getBackCardIcon() {
        loadCardIcons();
        return iconBack;
    }

    static char turnIntIntoCardValue(int k) {
        return cardsValues[k];
    }

    static char turnIntIntoCardSuit(int j) {
        return cardsSuits[j];
    }

    private static int valueAsInt(Card card) {
        for (int i = 0; i < cardsValues.length; i++) {
            if (cardsValues[i] == card.getValue())
                return i;
        }
        return -1;
    }

    private static int suitAsInt(Card card) {
        for (int i = 0; i < cardsSuits.length; i++) {
            if (card.getSuit().toString().charAt(0) == cardsSuits[i])
                return i;
        }
        return -1;
    }
}

class Card {

    private char value;
    private Suit suit;
    private boolean errorFlag;

    public static char[] valueRanks = new char[]{'A', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'X'};

    // Constructor
    public Card() {
        set('A', Suit.Spades);
    }

    // Constructor
    public Card(char value, Suit suit) {
        set(value, suit);
    }

    // Method to get String representation of Card
    public String toString() {
        if (errorFlag) {
            return "** illegal **";
        } else {
            return value + " of " + suit;
        }
    }

    // Method to set value and suit of Card
    public void set(char value, Suit suit) {
        this.value = value;
        this.suit = suit;
        this.errorFlag = !isValid(value, suit);
    }

    // Method to get value of Card
    public char getValue() {
        return value;
    }

    // Method to get Suit of Card
    public Suit getSuit() {
        return suit;
    }

    // Method to get errorFlag of Card
    public boolean getErrorFlag() {
        return errorFlag;
    }

    // Method to check equality of two Card objects
    public boolean equals(Card card) {
        return (getValue() == card.getValue() && getSuit() == card.getSuit());
    }

    // Method to check if value and suit are valid
    private boolean isValid(char value, Suit suit) {
        String values = "A23456789TJQKX";
        return (values.indexOf(value) != -1);
    }

    static void arraySort(Card[] cards, int arraySize) {
        Card temp;
        //track if changes were made during iteration
        boolean changesMade;
        for (int i = 0; i < arraySize; i++) {
            changesMade = false;
            for (int j = 1; j < arraySize; j++) {
                //go through the elements of valueRank array
                //and compare every two cards to every one
                for (char valueRank : valueRanks) {
                    //if left card's rank is found - this two cards
                    //are in correct position
                    if (cards[j-1].getValue() == valueRank)
                        break;
                    //if right card's rank is found - swap tho cards
                    if (cards[j].getValue() == valueRank) {
                        temp = cards[j];
                        cards[j] = cards[j-1];
                        cards[j-1] = temp;
                        changesMade = true;
                    }
                }
            }
            //if changes were not made - finish sorting
            if (!changesMade) break;
        }
    }

    // Main method which executes test code for Card
    public static void main(String[] args) {
        Card card1 = new Card('A', Suit.Spades);
        Card card2 = new Card('T', Suit.Clubs);
        Card card3 = new Card('Z', Suit.Hearts);
        System.out.println(card1);
        System.out.println(card2);
        System.out.println(card3);

        System.out.println();

        card2.set('U', Suit.Clubs);
        card3.set('5', Suit.Hearts);
        System.out.println(card1);
        System.out.println(card2);
        System.out.println(card3);
    }
  
}

enum Suit {
  Spades, Hearts, Diamonds, Clubs
}

class Deck {
 
    public final int MAX_CARDS = 6*52;
    
    private static Card[] masterPack;
    
    private Card[] cards;
    private int topCard;
    private int numPacks;
    
    // Constructor
    public Deck() {
        init(1);
    }
    
    // Constructor
    public Deck(int numPacks) {
        init(numPacks);
    }
    
    // Method to initialize Deck
    public void init(int numPacks) {
        allocateMasterPack();
        this.numPacks = numPacks;
        cards = new Card[56*numPacks];
        int i = 0;
        for (int j = 0; j < numPacks; j++) {
            for (int k = 0; k < 56; k++) {
                cards[i++] = masterPack[k];
            }
        }
        this.topCard = 56 * numPacks - 1;
    }
    
    // Method to shuffle Deck
    public void shuffle() {
        for (int i = 0; i < cards.length; i++) {
            Card original = cards[i];
            int j = (int)(Math.random() * cards.length);
            cards[i] = cards[j];
            cards[j] = original;
        }
    }
    
    // Method to deal Card from Deck
    public Card dealCard() {
        if (topCard >= 0) {
            return cards[topCard--];
        } else {
            return null;
        }
    }
    
    // Method to get index of top Card
    public int getTopCard() {
        return topCard;
    }
    
    // Method to inspect Card
    public Card inspectCard(int k) {
        if (k < 0 || k >= topCard) {
            return new Card('0', Suit.Spades);
        } else {
            return cards[k];
        }
    }
    
    public boolean addCard(Card card) {
        if (cards.length > topCard) {
            cards[++topCard] = card;
            return true;
        }
        return false;
    }
    
    public boolean removeCard(Card card) {
        for (int i = 0; i < cards.length; i++) {
            if (cards[i].equals(card)) {
                cards[i] = cards[topCard--];
                return true;
            }
        }
        return false;
    }
    
    public void sort() {
        Card.arraySort(cards, topCard + 1);
    }
    
    public int getNumCards() {
        return topCard + 1;
    }
    
    // Method to allocate Master Deck
    private static void allocateMasterPack() {
        if (masterPack == null) {
            masterPack = new Card[56];
            Suit[] suits = {Suit.Clubs, Suit.Diamonds, Suit.Hearts, Suit.Spades};
            String values = "A23456789TJQKX";
            int i = 0;
            for (Suit suit: suits) {
                for (char value: values.toCharArray()) {
                    Card card = new Card(value, suit);
                    masterPack[i++] = card;
                }
            }
        }
    }
    
    // Main method which executes test code for Deck
    public static void main(String[] args) {
        System.out.println("Deck of 2 packs of cards:");
        Deck deck = new Deck(2);
        System.out.println("Dealing all unshuffled cards");
        while (deck.getTopCard() >= 0) {
            Card card = deck.dealCard();
            System.out.print(card + " / ");
        }
        System.out.println();
        deck = new Deck(2);
        deck.shuffle();
        System.out.println("Dealing all SHUFFLED cards");
        while (deck.getTopCard() >= 0) {
            Card card = deck.dealCard();
            System.out.print(card + " / ");
        }
        System.out.println("\n");
        System.out.println("Deck of 1 pack of cards:");
        deck = new Deck(1);
        System.out.println("Dealing all unshuffled cards");
        while (deck.getTopCard() >= 0) {
            Card card = deck.dealCard();
            System.out.print(card + " / ");
        }
        System.out.println();
        deck = new Deck(1);
        deck.shuffle();
        System.out.println("Dealing all SHUFFLED cards");
        while (deck.getTopCard() >= 0) {
            Card card = deck.dealCard();
            System.out.print(card + " / ");
        }
        System.out.println();      
    }
  
}

class Hand {
    
    public static final int MAX_CARDS = 50;
    
    private Card[] myCards;
    private int numCards;
    
    // Constructor
    public Hand() {
        resetHand();
    }
    
    // Method to reset Hand
    public void resetHand() {
        this.myCards = new Card[MAX_CARDS];
        this.numCards = 0;
    }
    
    // Method to take Card and add to Hand
    public boolean takeCard(Card card) {
        if (numCards < MAX_CARDS) {
            myCards[numCards++] = card;
            return true;
        } else {
            return false;
        }
    }
    
    // Method to play Card from Hand
    public Card playCard(int index) {
        if (index < numCards) {
            return myCards[index];
        } else {
            return null;
        }
    }
    
    // Method to get String representation of Hand
    public String toString() {
        String result = "Hand = ( ";
        if (numCards > 0) {
            for (int i = 0; i < numCards; i++) {
                result += myCards[i].toString() + ", ";
            }
            result = result.substring(0,result.length()-2);
        }
        result += " )";
        return result;
    }
    
    // Method to get number of cards in Hand
    public int getNumCards() {
        return numCards;
    }
    
    // Method to inspect Card in Hand
    public Card inspectCard(int k) {
        if (k < 0 || k >= numCards) {
            return new Card('0', Suit.Spades);
        } else {
            return myCards[k];
        }
    }

    void sort() {
        Card.arraySort(myCards, numCards);
    }

    // Main method which executes test code for Hand
    public static void main(String[] args) {
        Card[] cards = new Card[3];
        cards[0] = new Card('3', Suit.Clubs);
        cards[1] = new Card('T', Suit.Clubs);
        cards[2] = new Card('9', Suit.Hearts);
        Hand hand = new Hand();
        int i = 0;
        while (hand.getNumCards() < Hand.MAX_CARDS) {
            hand.takeCard(cards[i % 3]);
            i++;
        }
        System.out.println("After deal");
        System.out.println(hand);
        System.out.println();
        System.out.println("Testing inspectCard()");
        System.out.println(hand.inspectCard(Hand.MAX_CARDS-1));
        System.out.println(hand.inspectCard(Hand.MAX_CARDS));
        System.out.println();
        while (hand.getNumCards() > 0) {
            Card card = hand.playCard(0);
            System.out.println("Playing " + card);
        }
        System.out.println();
        System.out.println("After playing all cards");
        System.out.println(hand);
    }
    
}
