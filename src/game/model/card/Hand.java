package game.model.card;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;

/**
 * Describes a single player's collection of cards.
 */
public class Hand implements Serializable
{
   /** State of a card. */
   private enum State
   {
      /** If the card has been used. */
      USED,
      /** If the card has not yet been used. */
      UNUSED
   };
   /** The mapping of cards. */
   private Map<Card, State> cards;

   /**
    * Constructor for the class Hand.
    */
   public Hand() {
      cards = new EnumMap<Card, State>(Card.class);
   }

   /**
    * Sets the card as used.
    * @param card the card to be set as used.
    */
   public void setCardAsUsed(Card card) {
      if (cards.containsKey(card)) {
         cards.remove(card);
         cards.put(card, State.USED);
      }
   }

   /**
    * Sets the card as unused.
    * @param card the card to be set as unused.
    */
   public void setCardAsUnused(Card card) {
      if (cards.containsKey(card)) {
         cards.remove(card);
         cards.put(card, State.UNUSED);
      }
   }

   /**
    * Adds a new card to the hand.
    * @param card The card to be added.
    */
   public void add(Card card) {
      cards.put(card, State.UNUSED);
   }

   /**
    * Removes a card from the hand.
    * @param card the card to be removed.
    */
   public void remove(Card card) {
      cards.remove(card);
   }

   /**
    * Checks if the card is in the hand.
    * @param card The queried card.
    * @return True if the card is in the hand, false otherwise.
    */
   public boolean hasCard(Card card) {
      return cards.containsKey(card);
   }

   /**
    * Checks if the card has already been used.
    * @param card The queried card.
    * @return true if the card has been used, false if it's not used,
    *          and true it's not in the hand.
    */
   public boolean isUsed(Card card) {
      if (hasCard(card)) {
         State state = cards.get(card);
         return state == State.USED;
      }
      else
         return false;
   }

   /**
    * Checks if the player can use this card.
    * @param card the card to be checked.
    * @return true if the player can use the card, else false.
    */
   public boolean canUse(Card card) {
      boolean hasCard = hasCard(card);
      boolean isCardUnused = !isUsed(card);
      if (hasCard && isCardUnused)
         return true;
      return false;
   }

   /**
    * Sets all of the cards back to unused state.
    */
   public void resetAllCards() {
      for (Card card : Card.values())
         if (hasCard(card))
            setCardAsUnused(card);
   }

   public int getCardCount() {
      return cards.size();
   }

   public int getCountOfCardsWithUsableEffects() {
      int nCardsWithUsableEffects = 0;
      for (Card card : cards.keySet())
         if (card.hasUsableEffect())
            nCardsWithUsableEffects++;
      return nCardsWithUsableEffects;
   }

   @Override
   public String toString() {
      StringBuilder string = new StringBuilder();

      string.append("Card count : ").
             append(cards.size()).
             append("\r\n");

      for (Map.Entry<Card, State> cardEntry :
             cards.entrySet()) {
         Card card = cardEntry.getKey();

         string.append(String.format("%11s : ", card.getName())).
                append(cardEntry.getValue().toString().toLowerCase()).
                append("\r\n");
      }

      return string.toString();
   }
}
