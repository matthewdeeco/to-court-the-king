package game.model.card.cost;

import game.model.card.CardCost;
import game.model.card.Card;
import game.model.player.Player;

/**
 * Must have the Fool card.
 */
public class MustHaveFool extends CardCost
{
   public MustHaveFool() {
      setDescription("must have the Fool card");
   }

   @Override
   public boolean isFulfilled(Player player) {
      return player.hasCard(Card.FOOL);
   }
}
