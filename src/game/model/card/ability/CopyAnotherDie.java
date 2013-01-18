package game.model.card.ability;

import game.view.ActiveUnflaggedDieSelectionDialog;
import game.model.card.CardAbility;
import game.model.dice.*;
import game.controller.ConfirmHandler;
import game.model.player.Player;
import game.utility.Dialog;

/**
 * The ability to copy another die's value.
 */
public class CopyAnotherDie extends CardAbility
{
   /** The die that is copying the value of another die. */
   private Die copyingDie;
   /** The die whose value will be copied by the copying die. */
   private Die dieToCopy;
   private Player usingPlayer;

   public CopyAnotherDie() {
      setDescription("Change the value of 1 \nactive die to be equal "
             + "to the value \nof another active die.");
   }

   @Override
   public boolean isUsable(Player player) {
      return player.getActiveDiceCount() >= 2;
   }

   @Override
   public void use(Player player) {
      if (!isUsable(player))
         throw new UnusableAbilityException();

      usingPlayer = player;
      chooseDice();
      changeDieValue();
   }

   private void chooseDice() {
      do {
         usingPlayer.unflagCardAllDice();
         askForDice();
      }
      while (!(ConfirmHandler.confirmCardEffect()));
   }

   /** Ask to choose the copying die and die to copy. */
   private void askForDice() {
      ActiveUnflaggedDieSelectionDialog dieDialog;
      dieDialog = new ActiveUnflaggedDieSelectionDialog(
             usingPlayer.getDice(), "Choose the die to change");
      copyingDie = dieDialog.getSelectedDie();
      copyingDie.setCardFlag(true);

      dieDialog = new ActiveUnflaggedDieSelectionDialog(
             usingPlayer.getDice(), "Choose the die to copy");
      dieToCopy = dieDialog.getSelectedDie();
      dieToCopy.setCardFlag(true);

      Dialog.infoMessage(createMessage());
   }

   private void changeDieValue() {
      int valueToCopy = dieToCopy.getValue();
      copyingDie.setValue(valueToCopy);
   }

   private String createMessage() {
      int copyingValue = copyingDie.getValue();
      int valueToCopy = dieToCopy.getValue();

      if (copyingValue == valueToCopy)
         return "The value will not change.";
      else
         return "The die with the value " + copyingValue
                + "\nwill now have the value of " + valueToCopy + ".";
   }
}