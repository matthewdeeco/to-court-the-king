package game.model.card;

import game.model.player.InvalidPlayerCountException;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;

/**
 * Stores the number of remaining cards for each card.
 */
public class CardCounter implements Serializable
{
   /** Defines an infinite number of remaining cards. */
   public static final int INFINITE = -1;
   /** The mapping of cards to its remaining number. */
   private Map<Card, Integer> remainingCards;
   /** The number of players in the game. */
   private int playerCount;

   public CardCounter(int playerCount) {
      this.playerCount = playerCount;
      remainingCards = new EnumMap<Card, Integer>(Card.class);
      addCards();
   }

   private void addCards() {
      for (Card card : Card.values())
         addCard(card);
   }

   private void addCard(Card card) {
      try {
         int remaining = computeRemaining(card);
         remainingCards.put(card, remaining);
      }
      catch (InvalidRankException ex) {
      }
      catch (InvalidPlayerCountException ex) {
      }
   }

   /**
    * Computes the remaining number of cards for a card.
    * @param card the new card to be computed.
    * @return the remaining number of cards computed.
    * @throws InvalidRankException when the rank of the card is invalid.
    */
   private int computeRemaining(Card card) throws InvalidRankException, InvalidPlayerCountException {
      switch (card.getRank()) {
         case 0:
            return computeRemainingRank0();
         case 1:
            return computeRemainingRank1();
         case 2:
            return computeRemainingRank2();
         case 3:
         // fallthrough case
         case 4:
            return computeRemainingRank3Or4();
         case 5:
            return computeRemainingRank5();
         default:
            throw new InvalidRankException();
      }
   }

   private int computeRemainingRank0() throws InvalidPlayerCountException {
      switch (playerCount) {
         case 2:
         // fallthrough
         case 3:
         // fallthrough
         case 4:
            return INFINITE;
         default:
            throw new InvalidPlayerCountException();
      }
   }

   private int computeRemainingRank1() throws InvalidPlayerCountException {
      switch (playerCount) {
         case 2:
         // fallthrough
         case 3:
            return 2;
         case 4:
            return 3;
         default:
            throw new InvalidPlayerCountException();
      }
   }

   private int computeRemainingRank2() throws InvalidPlayerCountException {
      switch (playerCount) {
         case 2:
            return 1;
         case 3:
            return 2;
         case 4:
            return 3;
         default:
            throw new InvalidPlayerCountException();
      }
   }

   private int computeRemainingRank3Or4() throws InvalidPlayerCountException {
      switch (playerCount) {
         case 2:
            return 1;
         case 3:
         // fallthrough
         case 4:
            return 2;
         default:
            throw new InvalidPlayerCountException();
      }
   }

   private int computeRemainingRank5() throws InvalidPlayerCountException {
      switch (playerCount) {
         case 2:
         // fallthrough
         case 3:
         // fallthrough
         case 4:
            return 1;
         default:
            throw new InvalidPlayerCountException();
      }
   }

   public void decreaseCount(Card card) {
      int remaining = getRemaining(card);
      if (remaining == INFINITE)
         ;// infinite, so no need to decrease.
      else {
         remainingCards.remove(card);
         remainingCards.put(card, remaining - 1);
      }
   }

   /**
    * Checks if there are still remaining copies of the card.
    * @param card The queried card.
    * @return True if there are still remaining copies, false otherwise.
    */
   public boolean isAvailable(Card card) {
      int remaining = getRemaining(card);
      boolean isUnlimited = (remaining == INFINITE);
      boolean hasRemainingCopies = (remaining > 0);
      return (isUnlimited || hasRemainingCopies);
   }

   public int getRemaining(Card card) {
      return remainingCards.get(card);
   }

   @Override
   public String toString() {
      StringBuilder string = new StringBuilder();

      for (Map.Entry<Card, Integer> cardEntry :
             remainingCards.entrySet()) {
         Card card = cardEntry.getKey();

         string.append(String.format("%11s : ", card.getName()));
         if (cardEntry.getValue() == INFINITE)
            string.append("infinite");
         else
            string.append(cardEntry.getValue());
         string.append("\r\n");
      }

      return string.toString();
   }
}
