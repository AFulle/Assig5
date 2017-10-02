/*
 * Jospeh Cortez
 * Lyndsay Hackett
 * Mohklis Awad
 * Ahdia Fuller
 * 
 */

import javax.swing.*;
import java.lang.Math;

public class Assig5_2
{

}
//controls the positioning of the panels and cards of the GUI
class CardTable extends JFrame
{
   // data members to establish grid layout for JPanels 
   static int MAX_CARDS_PER_HAND = 56;
   static int MAX_PLAYERS = 2;  // for now, we only allow 2 person games
   private int numCardsPerHand;
   private int numPlayers;
   public JPanel pnlComputerHand, pnlHumanHand, pnlPlayArea;
   {

   private static String[] pnlTitles = {"High Card", 
      "Computer Hand", "Player Hand", "Playing Area", "Time Played"};
   
   private JButton endGameBtn, playAgainBtn, nextRoundBtn;
   
   private JPanel pnlCpuHand, pnlHumanHand, pnlTimer, pnlOutput, 
   pnlPlayArea, pnlCardsPlayed, pnlCpuCardPlayed, pnlHumanCardPlayed;
   
   private ArrayList<JButton> playersCardsBtns = new ArrayList<JButton>();
   
   // default constructor
   // sets up the initial view, which is really
   // the table and it's main holders
   public GameView()
   {
      super();
      setTitle(pnlTitles[0]);
      setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
      setLayout(new BorderLayout());
      
      endGameBtn = new JButton("End Game");
      playAgainBtn = new JButton("Play Again");
      
      // timer panel
      pnlTimer = new JPanel();
      
      // cpu played Card
      pnlCpuCardPlayed = new JPanel(new GridLayout(2, 1));
      
      // human played Card
      pnlHumanCardPlayed = new JPanel(new GridLayout(2, 1));
      
      // area for cards played
      pnlCardsPlayed = new JPanel(new GridLayout(1, 2));
      pnlCardsPlayed.add(pnlCpuCardPlayed);
      pnlCardsPlayed.add(pnlHumanCardPlayed);
      
      // computers cards panel
      pnlCpuHand = new JPanel();
      pnlCpuHand.setLayout(new GridLayout(1, 7));
      
      // players cards panel
      pnlHumanHand = new JPanel();
      pnlHumanHand.setLayout(new GridLayout(1, 7));
      
      // output area
      pnlOutput = new JPanel(new GridLayout(3, 1));
      
      // playing area
      pnlPlayArea = new JPanel(new GridBagLayout());
      GridBagConstraints c = new GridBagConstraints();
      
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 0;
      c.gridy = 0;
      pnlPlayArea.add(pnlCpuHand, c);
      
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 0;
      c.gridy = 1;
      c.weighty = 1.0;
      pnlPlayArea.add(pnlCardsPlayed, c);
      
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 0;
      c.gridy = 2;
      pnlPlayArea.add(pnlOutput, c);
      
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 0;
      c.gridy = 3;
      pnlPlayArea.add(pnlHumanHand, c);
      
      
      // add all the major components to the screen
      this.add(pnlTimer, BorderLayout.NORTH);
      this.add(pnlPlayArea,BorderLayout.CENTER);
      this.setSize(800, 600);
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setVisible(true);
   }
   
   public void playRoundScreen(String message, Hand cpuHand, 
         Hand humanHand, int maxHandSize)
   {
      if(cpuHand == null || humanHand == null || 
            maxHandSize == 0)
         return;
      
      JLabel textOutput = new JLabel(message);
      textOutput.setHorizontalAlignment(JLabel.CENTER);
      pnlOutput.add(textOutput);
      showCpuHand(cpuHand, maxHandSize);
      showHumanHandAsButtons(humanHand, maxHandSize);
      
   }
   
   public void roundOutcomeScreen(String message, Hand cpuHand, Card cpuCard,
         String cpuName, Hand humanHand, Card humanCard, String humanName, 
         int maxHandSize)
   {
      if(cpuHand == null || humanHand == null || 
            maxHandSize == 0)
         return;
      
      JLabel textOutput = new JLabel(message);
      textOutput.setHorizontalAlignment(JLabel.CENTER);
      nextRoundBtn = new JButton("Next Round");
      pnlOutput.add(textOutput);
      pnlOutput.add(nextRoundBtn);
      showCpuPlayedCard(cpuCard, cpuName);
      showHumanPlayedCard(humanCard, humanName);
      showCpuHand(cpuHand, maxHandSize);
      showHumanHand(humanHand, maxHandSize);
      
   }
   
   public void gameOutcomeScreen(String message)
   {
      if(message == null)
         return;
      
      JLabel textOutput = new JLabel(message);
      textOutput.setHorizontalAlignment(JLabel.CENTER);
      pnlOutput.add(textOutput);
      pnlOutput.add(playAgainBtn);
      pnlOutput.add(endGameBtn);
      
   }
   
   /*
    * private helper method to repaint the UI
    * */
   public void clearUI()
   {
      pnlCpuHand.removeAll();
      pnlCpuCardPlayed.removeAll();
      pnlHumanHand.removeAll();
      pnlHumanCardPlayed.removeAll();
      pnlOutput.removeAll();
      this.getContentPane().repaint();
   }
   
   /*
    * private helper method to repaint the UI
    * */
   public void rePaintUI()
   {
      this.getContentPane().validate();
      this.getContentPane().repaint();
   }
   
   private void showCpuHand(Hand hand, int maxHandSize)
   {  
      
      for(int k = 0; k < maxHandSize; k++)
      {
         if(k > hand.getNumCards() - 1)
            pnlCpuHand.add(new JLabel(GUICard.getBlankCardIcon()));
         else
            pnlCpuHand.add(new JLabel(GUICard.getBackCardIcon()));
      }
      
   }
   
   private void showHumanHand(Hand hand, int maxHandSize)
   {
      
      for(int k = 0; k < maxHandSize; k++)
      {
         if(k > hand.getNumCards() - 1)
            pnlHumanHand.add(new JLabel(GUICard.getBlankCardIcon()));
         else
            pnlHumanHand.add(new JLabel(GUICard
                  .getIcon(hand.inspectCard(k))));
      }
   }
   
   private void showHumanHandAsButtons(Hand hand, int maxHandSize)
   {     
      playersCardsBtns.clear();
      for(int k = 0; k < maxHandSize; k++)
      {
         if(k > hand.getNumCards() - 1)
         {
            pnlHumanHand.add(new JLabel(GUICard.getBlankCardIcon()));  
         }
         else
         {
            JButton playCardBtn = new JButton(
                  GUICard.getIcon(hand.inspectCard(k)));
            playCardBtn.setBorder(BorderFactory.createEmptyBorder());
            playCardBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            playersCardsBtns.add(playCardBtn);
            
            pnlHumanHand.add(playersCardsBtns.get(k));
         }
      }
   }
   
   public void showMessageg(String title, String message)
   {  
      JOptionPane.showMessageDialog(
            this,
            message,
            title,
            JOptionPane.PLAIN_MESSAGE);
   }
   
   public int showOptionDialog(String title, String message, String[] options)
   {  
      int option = JOptionPane.showOptionDialog(
            this, 
            message,
            title, 
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]);
      
      return option;
   }
   
   public void showCpuPlayedCard(Card card, String name)
   {
      pnlCpuCardPlayed.add(new JLabel(GUICard.getIcon(card)));
      pnlCpuCardPlayed.add(new JLabel(name));
   }
   
   public void showHumanPlayedCard(Card card, String name)
   {
      pnlHumanCardPlayed.add(new JLabel(GUICard.getIcon(card)));
      pnlHumanCardPlayed.add(new JLabel(name));
   }
   
   public void playersCardActionListener(int index, ActionListener l)
   {
      playersCardsBtns.get(index).setCursor(new Cursor(Cursor.HAND_CURSOR));
      playersCardsBtns.get(index).addActionListener(l);
   }
   
   public void nextRoundActionListener(ActionListener l)
   {
      nextRoundBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
      nextRoundBtn.addActionListener(l);
   }
   
   public void endActionListener(ActionListener l)
   {
      endGameBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
      endGameBtn.addActionListener(l);
   }
   
   public void playAgainActionListener(ActionListener l)
   {
      playAgainBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
      playAgainBtn.addActionListener(l);
   }
}

   // constructor to filter input and add panels to JFrame
   CardTable(String title, int numCardsPerHand, int numPlayers)
   {
      
   }

   // assessors for instance variables
   public int getNumPlayers() 
   {
      return numPlayers;
   }

   public int getNumCardsPerHand()
   {
      return numCardsPerHand;
   }

}


class GUICard
{
   private static Icon[][] iconCards = new ImageIcon[14][4];
   private static Icon iconBack;
   private static Icon iconBlank;
   static boolean iconsLoaded = false;


   /*
    * loads the  Icons for each card value
    * */
   static void loadCardIcons()
   {
      char[] value = {'A', '2', '3', '4', '5', '6', '7', '8', '9', 'T',
                      'J', 'Q', 'K', 'X'};
      char[] suit = {'C', 'D', 'H', 'S'};
      String fileName = "";

      for (int i = 0; i < suit.length; i++)
         for (int j = 0; j < value.length; j++)
         {
            fileName = "";
            fileName += String.valueOf(value[j]) + String.valueOf(suit[i])
               + ".gif";
            iconCards[j][i] = new ImageIcon("images/" + fileName);
         }
      iconBack = new ImageIcon("images/BK.gif");
      iconBlank = new ImageIcon("images/blank.gif");
      iconsLoaded = true;
   }

   /*
    * getter to get an Icon for a requested card
    * */
   static public Icon getIcon(Card card)
   {

      if (!iconsLoaded)
         loadCardIcons();

      int cardValue = valueAsInt(card.getValue());
      int suitValue = suitAsInt(card.getSuit());

      return iconCards[cardValue][suitValue];
   }//end method getIcon

   /*
    * getter to get a back of card icon
    * */
   static public Icon getBackCardIcon()
   {
      if (!iconsLoaded)
         loadCardIcons();

      return iconBack;
   }

   /*
    * getter to get a blank card holder
    * */
   static public Icon getBlankCardIcon()
   {
      if (!iconsLoaded)
         loadCardIcons();

      return iconBlank;
   }

   /*
    * returns a cards value as an int
    * */
   static int valueAsInt(char cardValue)
   {
      int value = 0;

      for (int i = 0; i < Card.cardValue.length; i++)
         if (cardValue == Card.cardValue[i])
         {
            value = i;
            break;
         }

      return value;
   }//end method valueAsInt

   /*
    * returns a cards suit as an int
    * */
   static int suitAsInt(Card.Suit suit)
   {
      if (suit.equals(Card.Suit.CLUBS))
         return 0;
      else if (suit.equals(Card.Suit.DIAMONDS))
         return 1;
      else if (suit.equals(Card.Suit.HEARTS))
         return 2;
      else
         return 3;
   }//end method suitAsInt

}


//Card Class
class Card
{
 enum Suit {clubs, diamonds, hearts, spades};
 private char value;
 private Suit suit;
 private boolean errorFlag;

 public Card(char value, Suit suit)
 {
    set(value, suit);
 }
 
 // Copy Constructor
 public Card(Card aCard)
 {
    if (aCard == null)      // Not a real card
    {
      System.out.println("Fatal Error.");
      System.exit(0);
    }
    
    /*
     *  Want to check this
     */
    value = aCard.value;
    suit = aCard.suit;
 }

 //  method to display the card if valid and Invalid Card otherwise
 @Override
 public String toString()
 {
    if (errorFlag)
    {
       return "***Invalid Card***"; 
    }
    return String.format("%s of %s", value, suit);
 }

 // mutator that accepts legal values 
 public boolean set(char value, Suit suit)
 {
    if (isValid(value, suit)) 
    {
       this.value = value;
       this.suit = suit;
       errorFlag = false;
    }
    else
    {
       errorFlag = true;
    }
    return errorFlag;
 }

 // accessor for suit
 public Suit getSuit()
 {
    return suit;
 }

 // accessor for value
 public char getValue()
 {
    return value;
 }

 // checks if all fields are identical 
 public boolean equals(Card card)
 {
    return suit.equals(card.suit) && value == card.value;
 }

 // checks if card value is valid
 private boolean isValid(char value, Suit suit)
 {
    switch(value)
    {
    case 'A':
    case '2':
    case '3':
    case '4':
    case '5':
    case '6':
    case '7':
    case '8':
    case '9':
    case 'T':
    case 'J':
    case 'Q':
    case 'K':
       return true;
    default:
       return false;
    }  
 }
}

//Hand class
class Hand
{
 public static int MAX_CARDS = 100;
 private Card[] myCards;
 private int numCards;
 private int numUsed;       // Number of indeces currently in use
 private boolean errorFlag;
 
 // Default Constructor
 public Hand()
 {
    myCards = new Card[MAX_CARDS];
    numCards = 1;
 }
 
 // Copy constructor
 public Hand(Hand object)
 {
    int lengthOfArrays = object.myCards.length;
    this.myCards = new Card[lengthOfArrays];
    for (int i =0; i < lengthOfArrays; i++)
       this.myCards[i] = new Card(object.myCards[i]);
 }
 
 // Method that removes all cards from the hand
 public void resetHand()
 {
    myCards = new Card[MAX_CARDS];
    numUsed = 0;
 }
 
 /* Method that adds a card to the next available position in the myCards
  * array
  */
 public boolean takeCard(Card card)
 {
    if (numUsed > myCards.length)
    {
       System.out.println("Error: The hand is full.");
       return false;
    }
    else
    {
         myCards[numUsed] = new Card(card);
         numUsed++;
         return true;
    }
 }
 
 // returns and removes the card in the top occupied position of the array.
 public Card playCard()
 {
    int topCard = numUsed - 1;
    numUsed--;
    return myCards[topCard];
 }
 
 // Accessor for an individual card
 public Card inspectCard(int k)
 {
    if ((k >= 0) && (k <= numCards))
    {
       return myCards[k];
    }
    else
    {
       Card bogusCard = new Card('G', Card.Suit.hearts);
       return bogusCard;
    }
 }
 
 // Converts hand to a string and displays the entire String
 public String toString()
 {
    String handString = "";
    if (errorFlag)
    {
       return "***Invalid Hand***"; 
    }
    for (int i =0; i < numUsed; i++)
    {
       handString = handString + " " + myCards[i] + " /";
       if (i % 6 == 0 && i != 0)
       {
          handString = handString +"\n";
       }
    }
    return handString; 
 }
 
 // Mutator method for numCards
 public void setNumCards(int num)
 {
    numCards = num;
 }
 
 // Accessor method for numCards
 public int getNumCards()
 {
    return numCards;
 }
 
 public int getNumUsed()
 {
    return numUsed;
 }
 
}

/*
*  Deck class
*/
class Deck {

public final int MAX_CARDS = 6*52;

private static Card[] masterPack;

private Card[] cards;
private int topCard;
private int numPacks;

// Constructor
public Deck() 
{
   this.numPacks = 1;

      // populate masterPack
      allocateMasterPack();
    init(1);
}

// Constructor
public Deck(int numPacks) 
{
    init(numPacks);
}

// Method to initialize Deck
public void init(int numPacks) 
{
    allocateMasterPack();
    this.numPacks = numPacks;
    cards = new Card[52*numPacks];
    int i = 0;
    for (int j = 0; j < numPacks; j++) 
    {
        for (int k = 0; k < 52; k++) 
        {
            cards[i++] = masterPack[k];
        }
    }
    this.topCard = 52 * numPacks - 1;
}

// Method to shuffle Deck
public void shuffle() 
{
    for (int i = 0; i < cards.length; i++) 
    {
        Card original = cards[i];
        int j = (int)(Math.random() * cards.length);
        cards[i] = cards[j];
        cards[j] = original;
    }
}

// Method to deal Card from Deck
public Card dealCard() 
{
    if (topCard >= 0) 
    {
        return cards[topCard--];
    } else {
        return null;
    }
}

// Method to get index of top Card
public int getTopCard() 
{
    return topCard;
}

// Method to inspect Card
public Card inspectCard(int k) 
{
    if (k < 0 || k >= topCard) 
    {
        return new Card('0', Card.Suit.spades);
    } else 
    {
        return cards[k];
    }
}

// Method to allocate Master Deck
private static void allocateMasterPack() 
{
  if (masterPack == null) 
  {
    masterPack = new Card[52];
    Card.Suit[] suits = {Card.Suit.clubs, Card.Suit.diamonds, Card.Suit.hearts, Card.Suit.spades};
    String values = "A23456789TJQK";
    int i = 0;
    for (Card.Suit suit: suits) 
    {
      for (char value: values.toCharArray()) 
      {
        Card card = new Card(value, suit);
        masterPack[i++] = card;
      }
    }
  }
}  
}
