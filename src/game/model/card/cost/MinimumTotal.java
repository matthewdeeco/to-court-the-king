package game.model.card.cost;

import game.model.card.CardCost;
import game.model.dice.Dice;
import game.model.player.Player;

/**
 * The cost is to have a minimum total value of dice.
 */
public class MinimumTotal extends CardCost
{
   /** The minimum total required. */
   private int minimumTotal;

   public MinimumTotal(int mininumTotal) {
      this.minimumTotal = mininumTotal;
      setDescription("total value of all dice \nmust be "
             + minimumTotal + " or higher");
   }

   @Override
   public boolean isFulfilled(Player player) {
      Dice dice = player.getDice();
      int diceTotal = dice.getSum();
      return diceTotal >= minimumTotal;
   }
}
