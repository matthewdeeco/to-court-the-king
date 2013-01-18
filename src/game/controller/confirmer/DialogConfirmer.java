package game.controller.confirmer;

import game.model.card.Card;
import game.model.dice.Die;
import javax.swing.ImageIcon;
import static game.utility.Dialog.confirmYesNo;

/**
 * Asks the user to confirm with a dialog.
 */
public class DialogConfirmer implements Confirmer
{
   @Override
   public boolean confirmNewGame() {
      return confirmYesNo("Start new game?\nYou will lose any unsaved data.");
   }

   @Override
   public boolean confirmLoadGame() {
      return confirmYesNo("Load this game?\nYou will lose any unsaved data.");
   }

   @Override
   public boolean confirmExitGame() {
      return confirmYesNo("Exit the game?\nYou will lose any unsaved data.");
   }

   @Override
   public boolean confirmEndTurn() {
      return confirmYesNo("This will end your turn.\nIs that all right?");
   }

   @Override
   public boolean confirmSelectCard(Card card) {
      String message = "Select the " + card.getName() + "?";
      ImageIcon icon = card.getLargeIcon();
      return confirmYesNo(message, icon);
   }

   @Override
   public boolean confirmRerollActiveDice() {
      return confirmYesNo("Re-roll all your active dice? ");
   }

   @Override
   public boolean confirmSetActiveDie(Die die) {
      return confirmYesNo("Are you sure you want to set this die?",
             die.getImageIcon());
   }

   @Override
   public boolean confirmAvatarChoice(int avatar) {
      String message = "Do you choose this avatar?";
      ImageIcon icon = new ImageIcon(
             getClass().getClassLoader().getResource(
             "images/avatars/" + avatar + ".png"));

      return confirmYesNo(message, icon);
   }

   @Override
   public boolean confirmCardEffect() {
      return confirmYesNo("Are you sure about the dice you have chosen to modify?\n");
   }
}
