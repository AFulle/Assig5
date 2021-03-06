/*
 * Jospeh Cortez
 * Lyndsay Hackett
 * Mohklis Awad
 * Ahdia Fuller
 * 
 */

import javax.swing.*;
import java.awt.*;

public class Assig5
{
   // static for the 57 icons and their corresponding labels
   static final int NUM_CARD_IMAGES = 57; // 52 + 4 jokers + 1 back-of-card image
   static Icon[] icon = new ImageIcon[NUM_CARD_IMAGES];
      
   static void loadCardIcons()
   {
      // build the file names ("AC.gif", "2C.gif", "3C.gif", "TC.gif", etc.)
      // in a SHORT loop.  For each file name, read it in and use it to
      // instantiate each of the 57 Icons in the icon[] array.
      
      int i = 0; // Used to track index position of icon array.
      
      // Loop through the suit then available card values and add them to the icon array.
      for (int j = 0; j < 4; j++)
      {
         for (int k = 0; k < 14; k++)
         {
            icon[i] = new ImageIcon("images/" + turnIntIntoCardValue(k) + turnIntIntoCardSuit(j) + ".gif");
            i++;
         }
      }
      
      // Define and add the back-of-card to icon array - index was already incremented in for loop
      icon[i] = new ImageIcon("images/BK.gif");
   }
   
   // turns 0 - 13 into "A", "2", "3", ... "Q", "K", "X"
   static String turnIntIntoCardValue(int k)
   {
      String[] cardValue = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "X"};
      
      // Verify the passed int is within the number of items in cardValue
      if (k >= 0 && k <= 13)
      {
         return cardValue[k];
      }
      else
      {
         return "";
      }
   }
   
   // turns 0 - 3 into "C", "D", "H", "S"
   static String turnIntIntoCardSuit(int j)
   {
      String [] cardSuit = {"C", "D", "H", "S"};
      
      // Verify the passed int is within the number of items in cardSuit
      if (j >= 0 && j <= 3)
      {
         return cardSuit[j];
      }
      else
      {
         return "";
      }
   }
   
   // main to throw all JLabels out 
   public static void main(String[] args)
   {
      int k;
      
      // prepare the image icon array
      loadCardIcons();
      
      // establish main frame in which program will run
      JFrame frmMyWindow = new JFrame("Card Room");
      frmMyWindow.setSize(1150, 650);
      frmMyWindow.setLocationRelativeTo(null);
      frmMyWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      // set up layout which will control placement of buttons, etc.
      FlowLayout layout = new FlowLayout(FlowLayout.CENTER, 5, 20);   
      frmMyWindow.setLayout(layout);
      
      // prepare the image label array
      JLabel[] labels = new JLabel[NUM_CARD_IMAGES];
      for (k = 0; k < NUM_CARD_IMAGES; k++)
         labels[k] = new JLabel(icon[k]);
      
      // place your 3 controls into frame
      for (k = 0; k < NUM_CARD_IMAGES; k++)
         frmMyWindow.add(labels[k]);

      // show everything to the user
      frmMyWindow.setVisible(true);
   }

}

