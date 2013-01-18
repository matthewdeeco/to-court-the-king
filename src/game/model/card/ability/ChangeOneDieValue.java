package game.model.card.ability;

import game.model.card.CardAbility;
import game.view.ActiveUnflaggedDieSelectionDialog;
import game.controller.ConfirmHandler;
import game.model.dice.Die;
import game.model.player.Player;
import game.utility.Dialog;

/**
 * The ability to freely change the value of 1 active die.
 */
public class ChangeOneDieValue extends CardAbility
{
   private Player usingPlayer;
   private Die chosenDie;

   public ChangeOneDieValue() {
      setDescription("Freely change the value \nof 1 active die.");
   }

   @Override
   public boolean isUsable(Player player) {
      return player.hasActiveDice();
   }

   @Override
   public void use(Player player) {
      if (!isUsable(player))
         throw new UnusableAbilityException();

      usingPlayer = player;
      chooseDice();
      inputNewValue();
   }

   private void chooseDice() {
      do {
         usingPlayer.unflagCardAllDice();
         askForDie();
      }
      while (!(ConfirmHandler.confirmCardEffect()));
   }

   /** Ask the user to choose a die. */
   private void askForDie() {
      ActiveUnflaggedDieSelectionDialog dieDialog =
             new ActiveUnflaggedDieSelectionDialog(usingPlayer.getDice());
      chosenDie = dieDialog.getSelectedDie();

      chosenDie.setCardFlag(true);
   }

   /** Ask the user to input the new value for the die. */
   private void inputNewValue() {
      int newValue = Dialog.inputInteger("Enter the die's new value: ",
             Die.MIN_VALUE, Die.MAX_VALUE);
      chosenDie.setValue(newValue);
   }
}
