package game.model.card.cost;

import game.model.card.CardCost;
import java.util.ArrayList;
import game.model.dice.Dice;
import game.model.dice.Die;
import game.model.player.Player;

/**
 * The cost is that the dice must have an n number of n-of-a-kinds.
 */
public class OfAKind extends CardCost
{
   /** The number of n-of-a-kinds needed. */
   private int nOfAKindsNeeded;
   /** The n-of-a-kind needed. */
   private int nOfAKind;

   /**
    * ex: Cost is three 2-of-a-kinds
    * @param nOfAKindsNeeded the "3"
    * @param nOfAKind the "2" in the example.
    */
   public OfAKind(int nOfAKindsNeeded, int nOfAKind) {
      this.nOfAKindsNeeded = nOfAKindsNeeded;
      this.nOfAKind = nOfAKind;
      setDescription(createDescription());
   }

   @Override
   public boolean isFulfilled(Player player) {
      Dice dice = player.getDice();
      ArrayList<Integer> counts = dice.getCountForEachValue();

      int nOfAKindsSoFar = 0;
      for (int i = Die.MIN_VALUE; i <= Die.MAX_VALUE; i++) {
         while (hasNOfAKind(counts, i) && nOfAKindsSoFar < nOfAKindsNeeded) {
            int previousCount = counts.get(i - Die.MIN_VALUE);
            counts.set(i - Die.MIN_VALUE, previousCount - nOfAKind);
            nOfAKindsSoFar++;
         }
      }
      return nOfAKindsSoFar >= nOfAKindsNeeded;
   }

   /**
    * Checks if the list has the n-of-a-kind required.
    * @param counts the list to traverse.
    * @param valueToCount the value to count.
    * @return true if the list has the n-of-a-kind required of the value to count.
    */
   private boolean hasNOfAKind(ArrayList<Integer> counts, int valueToCount) {
      return counts.get(valueToCount - Die.MIN_VALUE) >= nOfAKind;
   }

   private String createDescription() {
      StringBuilder string = new StringBuilder();
      string.append(nOfAKindsNeeded).
             append(" ").
             append(nOfAKind).
             append("-of-a-kind").
             append(nOfAKindsNeeded == 1 ? "" : "s");
      return string.toString();
   }
}
