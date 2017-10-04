/*
 * Jospeh Cortez
 * Lyndsay Hackett
 * Mohklis Awad
 * Ahdia Fuller
 * 
 */

import javax.swing.*;
import java.util.Random;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class Assig5_3
{
   
   private static final int NUM_CARDS_PER_HAND = 7;
   private static final int NUM_PLAYERS = 2;
   private static int NUM_CARDS_IN_PLAY = 2;
   private static JLabel[] computerLabels = new JLabel[NUM_CARDS_PER_HAND];
   private static JLabel[] humanLabels = new JLabel[NUM_CARDS_PER_HAND];
   private static JLabel[] playedCardsLabels = new JLabel[NUM_PLAYERS];
   private static JLabel[] playLabelText = new JLabel[NUM_PLAYERS];

   public static void main(String[] args)
   {
      
      HighCardFramework highCardGame = getCardGameFramework();
      
      CardTable myCardTable = setupTable();
      myCardTable.setSize(800, 600);
     
      startGame(highCardGame, myCardTable);
      
   }
   
   private static HighCardFramework getCardGameFramework()
   {
      int numPacksPerDeck = 1;
      int numJokersPerPack = 0;
      int numUnusedCardsPerPack = 0;
      Card[] unusedCardsPerPack = null;

      // setup the game framework
      final HighCardFramework highCardGame = new HighCardFramework(numPacksPerDeck, numJokersPerPack, numUnusedCardsPerPack,
              unusedCardsPerPack, NUM_PLAYERS, NUM_CARDS_PER_HAND, NUM_CARDS_IN_PLAY);
      return highCardGame;
   }
   
   // Set the table
   private static CardTable setupTable()
   {
      CardTable myCardTable = new CardTable("CardTable", NUM_CARDS_PER_HAND, NUM_PLAYERS);
      myCardTable.setLocationRelativeTo(null);
      myCardTable.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      return myCardTable;
   }
   
   // Event Handler
   private static void initEventHandlers(HighCardFramework highCardGame, CardTable cardTable)
   {
      for(int i = 0; i < humanLabels.length; i++)
      {
         // remove the old mouse listeners
         for(int j = 0; i < humanLabels[i].getMouseListeners().length; j++)
         {
            humanLabels[i].removeMouseListener(humanLabels[i].getMouseListeners()[j]);
         }
         // add new mouse listener
         humanLabels[i].addMouseListener(new HighCardAdaptor(cardTable, highCardGame));
      }
   }
   
   
   public static void initPlayArea(CardTable cardTable)
   {
      for(Component component : cardTable.pnlPlayArea.getComponents())
      {
         if(component instanceof JLabel)
         {
            ((JLabel) component).setIcon(GUICard.getBackCardIcon());
         }
      }
   }
      public static void computerTurn(HighCardFramework highCardGame, CardTable cardTable, boolean computerFirst)
      {
         final Component[] inPlay = cardTable.pnlPlayArea.getComponents();
         final Hand hand = highCardGame.getHand(1);
         hand.sort();
         Card computersPick = null;

         if(computerFirst)
         {
            computersPick = hand.playCard(hand.getNumCards() - 1);
         }
         else
         {
            // find a card that can beat the card in play
            for(int i = 0; i <  hand.getNumCards(); i++)
            {
               if(valueOf(hand.inspectCard(i)) > valueOf(highCardGame.getPlayerCardInPlay()))
               {
                  computersPick = hand.playCard(i);
                  break;
               }
            }
            if(computersPick == null)
            {
               computersPick = hand.playCard();
            }
         }

         highCardGame.updateComputerCardAtPlayAreaIndex(computersPick, 1);

         if(cardTable.pnlComputerHand.getComponentCount() > 0)
         {
            cardTable.pnlComputerHand.remove(0);
         }

         for(Component component : inPlay)
         {
            final JLabel label = (JLabel)component;
            if(label.getText().equals("Computer"))
            {
               if(highCardGame.getComputerCardAtPlayAreaIndex(1) != null && !highCardGame.getComputerCardAtPlayAreaIndex(1).getErrorFlag())
               {
                  label.setIcon(GUICard.getIcon(highCardGame.getComputerCardAtPlayAreaIndex(1)));
                  return;
               }
               else
               {
                  label.setIcon(GUICard.getBackCardIcon());
               }
            }
         }
      }
      
      public static JLabel createJLabel(String labelText)
      {
         final JLabel playersCard = new JLabel(labelText, JLabel.CENTER);
         playersCard.setIcon(GUICard.getBackCardIcon());
         playersCard.setVerticalTextPosition(JLabel.BOTTOM);
         playersCard.setHorizontalTextPosition(JLabel.CENTER);
         return playersCard;
      }
      
      public static void startGame(HighCardFramework highCardGame, CardTable cardTable)
      {
         // Remove Replay Button from Play
         cardTable.replayBtn.setVisible(false);
         cardTable.replayBtn.setEnabled(false);

         // reset points
         highCardGame.playerPoints = 0;
         highCardGame.computerPoints = 0;

         highCardGame.newGame();
         if(!highCardGame.deal())
         {
            System.exit(11);
         }
         highCardGame.sortHands(); // sort the hands
         createLabels(highCardGame);
         addLabelsToPanels(cardTable);
         initPlayArea(cardTable);


         cardTable.replayBtn.addMouseListener(new MouseAdapter()
         {
            
            public void mousePressed(MouseEvent e)
            {
               startGame(highCardGame, cardTable);
            }
         });

         initEventHandlers(highCardGame, cardTable);
         cardTable.setVisible(true);
      }
      
      private static void createLabels(HighCardFramework cardGameFramework)
      {
         for(int i = 0; i < NUM_CARDS_PER_HAND; i++)
         {
            Card tempCard = cardGameFramework.getHand(0).inspectCard(i);
            humanLabels[i] = new JLabel(GUICard.getIcon(tempCard)); // Player has hand 0
            humanLabels[i].setName(tempCard.toString());
            computerLabels[i] = new JLabel(GUICard.getBackCardIcon());
         }
      }
      
      private static void addLabelsToPanels(CardTable cardTable)
      {
         // remove old icons
         cardTable.pnlHumanHand.removeAll();
         cardTable.pnlComputerHand.removeAll();
         // add new icons
         for(int i = 0; i < NUM_CARDS_PER_HAND; i++)
         {
            cardTable.pnlHumanHand.add(humanLabels[i]);
            cardTable.pnlComputerHand.add(computerLabels[i]);
         }
         if(cardTable.pnlPlayArea.getComponents().length <= 0)
         {
            cardTable.pnlPlayArea.add(createJLabel("Computer"));
            cardTable.pnlPlayArea.add(createJLabel("Player"));
         }
         cardTable.validate();
         cardTable.repaint();
      }
      
      private static int valueOf(Card card)
      {
         return new String(Card.valueRanks).indexOf(card.getValue());
      }
      
      
}

//class CardGameFramework  ----------------------------------------------------
class CardGameFramework
{
 private static final int MAX_PLAYERS = 50;

 private int numPlayers;
 private int numPacks;            // # standard 52-card packs per deck
                                  // ignoring jokers or unused cards
 private int numJokersPerPack;    // if 2 per pack & 3 packs per deck, get 6
 private int numUnusedCardsPerPack;  // # cards removed from each pack
 private int numCardsPerHand;        // # cards to deal each player
 private Deck deck;               // holds the initial full deck and gets
                                  // smaller (usually) during play
 private Hand[] hand;             // one Hand for each player
 private Card[] unusedCardsPerPack;   // an array holding the cards not used
                                      // in the game.  e.g. pinochle does not
                                      // use cards 2-8 of any suit

 public CardGameFramework( int numPacks, int numJokersPerPack,
       int numUnusedCardsPerPack,  Card[] unusedCardsPerPack,
       int numPlayers, int numCardsPerHand)
 {
    int k;

    // filter bad values
    if (numPacks < 1 || numPacks > 6)
       numPacks = 1;
    if (numJokersPerPack < 0 || numJokersPerPack > 4)
       numJokersPerPack = 0;
    if (numUnusedCardsPerPack < 0 || numUnusedCardsPerPack > 50) //  > 1 card
       numUnusedCardsPerPack = 0;
    if (numPlayers < 1 || numPlayers > MAX_PLAYERS)
       numPlayers = 4;
    // one of many ways to assure at least one full deal to all players
    if  (numCardsPerHand < 1 ||
          numCardsPerHand >  numPacks * (52 - numUnusedCardsPerPack)
          / numPlayers )
       numCardsPerHand = numPacks * (52 - numUnusedCardsPerPack) / numPlayers;

    // allocate
    this.unusedCardsPerPack = new Card[numUnusedCardsPerPack];
    this.hand = new Hand[numPlayers];
    for (k = 0; k < numPlayers; k++)
       this.hand[k] = new Hand();
    deck = new Deck(numPacks);

    // assign to members
    this.numPacks = numPacks;
    this.numJokersPerPack = numJokersPerPack;
    this.numUnusedCardsPerPack = numUnusedCardsPerPack;
    this.numPlayers = numPlayers;
    this.numCardsPerHand = numCardsPerHand;
    for (k = 0; k < numUnusedCardsPerPack; k++)
       this.unusedCardsPerPack[k] = unusedCardsPerPack[k];

    // prepare deck and shuffle
    newGame();
 }

 // constructor overload/default for game like bridge
 public CardGameFramework()
 {
    this(1, 0, 0, null, 4, 13);
 }

 public Hand getHand(int k)
 {
    // hands start from 0 like arrays

    // on error return automatic empty hand
    if (k < 0 || k >= numPlayers)
       return new Hand();

    return hand[k];
 }

 public Card getCardFromDeck() { return deck.dealCard(); }

 public int getNumCardsRemainingInDeck() { return deck.getNumCards(); }

 public void newGame()
 {
    int k, j;

    // clear the hands
    for (k = 0; k < numPlayers; k++)
       hand[k].resetHand();

    // restock the deck
    deck.init(numPacks);

    // remove unused cards
    for (k = 0; k < numUnusedCardsPerPack; k++)
       deck.removeCard( unusedCardsPerPack[k] );

    // add jokers
    for (k = 0; k < numPacks; k++)
       for ( j = 0; j < numJokersPerPack; j++)
          deck.addCard( new Card('X', Suit.values()[j]) );

    // shuffle the cards
    deck.shuffle();
 }

 public boolean deal()
 {
    // returns false if not enough cards, but deals what it can
    int k, j;
    boolean enoughCards;

    // clear all hands
    for (j = 0; j < numPlayers; j++)
       hand[j].resetHand();

    enoughCards = true;
    for (k = 0; k < numCardsPerHand && enoughCards ; k++)
    {
       for (j = 0; j < numPlayers; j++)
          if (deck.getNumCards() > 0)
             hand[j].takeCard( deck.dealCard() );
          else
          {
             enoughCards = false;
             break;
          }
    }

    return enoughCards;
 }

 void sortHands()
 {
    int k;

    for (k = 0; k < numPlayers; k++)
       hand[k].sort();
 }

 Card playCard(int playerIndex, int cardIndex)
 {
    // returns bad card if either argument is bad
    if (playerIndex < 0 ||  playerIndex > numPlayers - 1 ||
        cardIndex < 0 || cardIndex > numCardsPerHand - 1)
    {
       //Creates a card that does not work
       return new Card('M', Suit.Spades);      
    }
 
    // return the card played
    return hand[playerIndex].playCard(cardIndex);
 
 }


 boolean takeCard(int playerIndex)
 {
    // returns false if either argument is bad
    if (playerIndex < 0 || playerIndex > numPlayers - 1)
       return false;
   
     if (deck.getNumCards() <= 0)
        return false;

     return hand[playerIndex].takeCard(deck.dealCard());
 }

}

class HighCardFramework extends CardGameFramework
{
   private static final int PLAYER_INDEX = 0;
   public boolean playerWonLast = true;
   public int playerPoints = 0;
   public int computerPoints = 0;
   private int cardsInPlayArea;
   private Card[] playArea;

   public HighCardFramework(int numPacks, int numJokersPerPack,
                            int numUnusedCardsPerPack,  Card[] unusedCardsPerPack,
                            int numPlayers, int numCardsPerHand, int cardsInPlayArea)
   {
      super(numPacks, numJokersPerPack,
              numUnusedCardsPerPack, unusedCardsPerPack,
              numPlayers, numCardsPerHand);
      this.cardsInPlayArea = cardsInPlayArea;
      playArea = new Card[cardsInPlayArea];
   }

   public void newGame()
   {
      super.newGame();
      playerWonLast = true;
   }

   public boolean updatePlayerCardInPlay(Card playedCard)
   {
      playArea[PLAYER_INDEX] = playedCard;
      return playedCard.equals(playArea[PLAYER_INDEX]);
   }

   public boolean updateComputerCardAtPlayAreaIndex(Card playedCard, int index)
   {
      if(index > 0 && index < cardsInPlayArea)
      {
         playArea[index] = playedCard;
         return true;
      }
      else
      {
         return false;
      }
   }

   public Card getComputerCardAtPlayAreaIndex(int index)
   {
      if(index > 0 && index < cardsInPlayArea)
      {
         return playArea[index];
      }
      else
      {
         return new Card('?', Suit.Spades);
      }
   }

   public Card getPlayerCardInPlay()
   {
      return playArea[PLAYER_INDEX];
   }
}

class HighCardAdaptor extends MouseAdapter
{
   private final CardTable cardTable;
   private final HighCardFramework highCardGame;

   public HighCardAdaptor(final CardTable cardTable, final HighCardFramework highCardGame)
   {
      this.cardTable = cardTable;
      this.highCardGame = highCardGame;
   }

   public void mousePressed(MouseEvent e)
   {
      String message = "";
      final String clickedName = e.getComponent().getName();
      

      //Identifies name of clicked JLabel and finds associated card in player deck and plays
      for(int i = 0; i < highCardGame.getHand(0).getNumCards(); i++)
      {
         if(highCardGame.getHand(0).inspectCard(i).toString().equals(clickedName))
         {
            highCardGame.updatePlayerCardInPlay(highCardGame.getHand(0).playCard(i)); //Move card from hand to play area
         }
      }
      update(e);
      draw();

      if(highCardGame.playerWonLast)
      {
         Assig5_3.computerTurn(highCardGame, cardTable, false);
         draw();
      }
      if(!(highCardGame.playerWonLast = playerWins()))
      {
         Assig5_3.computerTurn(highCardGame, cardTable, true);
         draw();
      }

      if(highCardGame.getHand(0).getNumCards() <= 0 || highCardGame.getHand(1).getNumCards() <= 0)
      {
         cardTable.replayBtn.setEnabled(true);
         cardTable.replayBtn.setVisible(true);
         message = highCardGame.playerPoints >= highCardGame.computerPoints ? "Player wins the match" : "Computer wins the match";
         JOptionPane.showMessageDialog(cardTable, message);
      }
   }

   private boolean playerWins()
   {
      boolean playerWonCurrent = false; 
      String message = "";

      // Check who won
      if(Card.cardValue(highCardGame.getPlayerCardInPlay()) <
              Card.cardValue(highCardGame.getComputerCardAtPlayAreaIndex(1)))
      {
         message = "Player wins the round!";
         playerWonCurrent = true;
         highCardGame.playerPoints++;
      }
      else if(Card.cardValue(highCardGame.getPlayerCardInPlay()) ==
              Card.cardValue(highCardGame.getComputerCardAtPlayAreaIndex(1)))
      {
         message = "The round is a tie!";
      }
      else
      {
         message = "Computer wins the round!";
         highCardGame.computerPoints++;
      }
      JOptionPane.showMessageDialog(cardTable, message);
      Assig5_3.initPlayArea(cardTable);
      return playerWonCurrent;
   }

   private void update(MouseEvent e)
   {
      JLabel playerCard = null;
      for(Component component : cardTable.pnlPlayArea.getComponents())
      {
         if(((JLabel) component).getText().equals("Player"))
         {
            playerCard = (JLabel) component;
            playerCard.setIcon(((JLabel)e.getComponent()).getIcon()); // update player icon in play area
            break;
         }
      }
      cardTable.pnlHumanHand.remove(e.getComponent()); // remove card from player hand
      cardTable.pnlPlayArea.add(playerCard);
   }

   private void draw()
   {
      cardTable.revalidate();
      cardTable.repaint();
   }
}