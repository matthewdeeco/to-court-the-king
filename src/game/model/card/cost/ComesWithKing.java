package game.model.card.cost;

import game.model.card.CardCost;
import game.model.player.Player;

/**
 * The cost is free, but it comes with the King.
 */
public class ComesWithKing extends CardCost
{
   public ComesWithKing() {
      setDescription("initially comes with the King");
   }

   @Override
   public boolean isFulfilled(Player player) {
      return false;
   }
}
