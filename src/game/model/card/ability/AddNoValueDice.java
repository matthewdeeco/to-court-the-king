package game.model.card.ability;

import game.model.card.CardAbility;
import game.model.player.Player;

/**
 * The ability to add additional permanent dice.
 */
public class AddNoValueDice extends CardAbility
{
   private int nDiceToAdd;

   public AddNoValueDice(int nDiceToAdd) {
      this.nDiceToAdd = nDiceToAdd;
      setDescription(createDescription());
   }

   @Override
   public boolean isUsable(Player player) {
      return false;
   }

   @Override
   public void use(Player player) {
   }

   @Override
   public void effectOnAcquire(Player player) {
      for (int i = 0; i < nDiceToAdd; i++)
         player.addPermanentDie();
   }

   private String createDescription() {
      StringBuilder string = new StringBuilder();

      string.append("Start the turn with \n").
             append(nDiceToAdd).
             append(" additional ").
             append(nDiceToAdd == 1 ? "die." : "dice.");
      return string.toString();
   }
   
   @Override
   public boolean hasUsableEffect(){
      return false;
   }
}
