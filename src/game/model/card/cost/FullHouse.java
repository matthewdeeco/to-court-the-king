package game.model.card.cost;

import game.model.card.CardCost;
import java.util.ArrayList;
import game.model.dice.Dice;
import game.model.dice.Die;
import game.model.player.Player;

/**
 * The cost is that the dice must form a full house.
 * (one three-of-a-kind and one two-of-a-kind)
 */
public class FullHouse extends CardCost
{
   public FullHouse() {
      setDescription("a full house (one \n2-of-a-kind and one 3-of-a-kind)");
   }

   @Override
   public boolean isFulfilled(Player player) {
      Dice dice = player.getDice();
      ArrayList<Integer> counts = dice.getCountForEachValue();

      boolean hasThreeOfAKind = false;
      boolean hasTwoOfAKind = false;

      for (int i = Die.MIN_VALUE; i <= Die.MAX_VALUE; i++) {
         boolean hasThreeOfAKindOfThisValue = hasThreeOfAKind(counts, i);
         boolean hasTwoOfAKindOfThisValue = hasTwoOfAKind(counts, i);

         while (hasThreeOfAKindOfThisValue || hasTwoOfAKindOfThisValue) {
            int previousCount = counts.get(i - Die.MIN_VALUE);
            if (hasThreeOfAKind(counts, i)) {
               counts.set(i - Die.MIN_VALUE, previousCount - 3);
               hasThreeOfAKind = true;
            }
            else {
               counts.set(i - Die.MIN_VALUE, previousCount - 2);
               hasTwoOfAKind = true;
            }
            hasThreeOfAKindOfThisValue = hasThreeOfAKind(counts, i);
            hasTwoOfAKindOfThisValue = hasTwoOfAKind(counts, i);
         }
      }
      return hasThreeOfAKind && hasTwoOfAKind;
   }

   /**
    * Checks if the list has the 2-of-a-kind required.
    * @param counts the list to traverse.
    * @param valueToCount the value to count.
    * @return true if the list has the 2-of-a-kind required of the value to count.
    */
   private boolean hasTwoOfAKind(ArrayList<Integer> counts, int valueToCount) {
      return counts.get(valueToCount - Die.MIN_VALUE) >= 2;
   }

   /**
    * Checks if the list has the 3-of-a-kind required.
    * @param counts the list to traverse.
    * @param valueToCount the value to count.
    * @return true if the list has the 3-of-a-kind required of the value to count.
    */
   private boolean hasThreeOfAKind(ArrayList<Integer> counts, int valueToCount) {
      return counts.get(valueToCount - Die.MIN_VALUE) >= 3;
   }
}
