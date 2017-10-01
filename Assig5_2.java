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
   private static Icon[][] iconCards = new ImageIcon[14][4]; // 14 = A thru K + joker
   private static Icon iconBack;
   static boolean iconsLoaded = false;

   // generates the image Icon array from files
   static void loadCardIcons()
   {
      
   }

   // takes Card object and returns icon for that card
   static public Icon getIcon(Card card)
   {
      
   }

   // returns the card-back image
   static public Icon getBackCardIcon() 
   {
   
   }

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
