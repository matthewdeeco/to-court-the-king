package game.model.card;

import game.model.player.Player;

public abstract class CardAbility
{
   private String description;

   /**
    * Uses the ability.
    * @param player the player who is going to use the card.
    */
   public abstract void use(Player player);

   /**
    * Checks if the current player can use the card. The default is true.
    * @param player the player who is attempting to use the card.
    * @return true if the card can be used, false otherwise.
    */
   public abstract boolean isUsable(Player player);

   /**
    * The effect the card has immediately upon the player acquiring it.
    * A hook.
    * @param player the player who just acquired the card.
    */
   public void effectOnAcquire(Player player) {
   }

   public boolean hasUsableEffect() {
      return true;
   }

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
