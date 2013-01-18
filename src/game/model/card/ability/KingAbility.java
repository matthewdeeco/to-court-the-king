package game.model.card.ability;

import game.model.card.CardAbility;
import game.model.player.Player;

/**
 * The ability to win the game for the player who possesses it after the final round.
 * @author Matthew Co
 */
public class KingAbility extends CardAbility
{
   /**
    * Constructor for GameWinner.
    */
   public KingAbility() {
      setDescription("Wins the game for the \nplayer who "
             + "possesses it after the \nfinal round.");
   }

   @Override
   public boolean isUsable(Player player) {
      return false;
   }

   @Override
   public void use(Player player) {
   }
}
