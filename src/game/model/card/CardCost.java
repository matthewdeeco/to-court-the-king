package game.model.card;

import game.model.player.Player;

public abstract class CardCost
{
   private String description;

   /**
    * Checks if the card is acquirable.
    * @param player the player that is trying to acquire the card.
    * @return True if the card is acquirable, false if it is not.
    */
   public abstract boolean isFulfilled(Player player);

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   @Override
   public String toString() {
      return description;
   }
}
