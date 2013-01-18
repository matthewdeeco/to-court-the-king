package game.model.card.cost;

import game.model.card.CardCost;
import game.model.dice.Dice;
import game.model.player.Player;

/**
 * The cost is that the dice must all be odd or all be even.
 */
public class AllOddOrEven extends CardCost
{
   /** The type of dice needed by the cost. */
   public enum Type
   {
      ODD, EVEN
   };
   private Type type;

   public AllOddOrEven(Type type) {
      this.type = type;
      setDescription("all dice must be " + type.toString().toLowerCase());
   }

   @Override
   public boolean isFulfilled(Player player) {
      Dice dice = player.getDice();

      if (type == type.ODD)
         return dice.isAllOdd();
      else
         return dice.isAllEven();
   }
}
