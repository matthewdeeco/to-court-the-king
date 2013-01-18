package game.model.card.cost;

import game.model.card.CardCost;
import game.model.player.Player;

/**
 * No cost.
 */
public class Free extends CardCost
{
   public Free() {
      setDescription("free");
   }

   @Override
   public boolean isFulfilled(Player player) {
      return true;
   }
}
