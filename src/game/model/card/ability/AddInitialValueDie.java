package game.model.card.ability;

import game.model.card.CardAbility;
import game.utility.Dialog;
import game.model.player.Player;

/**
 * The ability to add new dice with a given initial value.
 */
public class AddInitialValueDie extends CardAbility
{
   /** Initial value for when the die's initial value is the user's choice. */
   public static final int ANY_VALUE = -1;
   private int initialValue;

   public AddInitialValueDie(int initialValue) {
      this.initialValue = initialValue;
      setDescription(createDescription());
   }

   @Override
   public boolean isUsable(Player player) {
      return true;
   }

   @Override
   public void use(Player player) {
      if (canAddWithAnyInitialValue()) {
         int desiredInitialValue = Dialog.inputInteger("Enter the value of the die you want to add: ", 1, 6);
         player.addTemporaryDie(desiredInitialValue);
      }
      else
         player.addTemporaryDie(initialValue);
   }

   private boolean canAddWithAnyInitialValue() {
      return initialValue == ANY_VALUE;
   }

   private String createDescription() {
      StringBuilder string = new StringBuilder();
      string.append("Add an active die with ").
             append(canAddWithAnyInitialValue() ? "\nany initial value." : "an \ninitial value of " + initialValue + ".");
      return string.toString();
   }
}
