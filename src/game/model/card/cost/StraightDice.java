package game.model.card.cost;

import game.model.card.CardCost;
import game.model.dice.Dice;
import game.model.dice.Die;
import game.model.player.Player;

/**
 * The cost is to have n number of straight dice.
 */
public class StraightDice extends CardCost
{
   /** The number of straight dice required. */
   private int nStraightDice;

   public StraightDice(int nStraightDice) {
      this.nStraightDice = nStraightDice;
      setDescription(nStraightDice + " straight dice");
   }

   @Override
   public boolean isFulfilled(Player player) {
      Dice dice = player.getDice();

      int chainSoFar = 0;
      for (int i = Die.MIN_VALUE; i <= Die.MAX_VALUE; i++) {
         if (dice.hasDieWithValue(i))
            chainSoFar++;
         else
            chainSoFar = 0;
         if (chainSoFar >= nStraightDice)
            return true;
      }
      return false;
   }
}
