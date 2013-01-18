package game.controller.confirmer;

import game.model.card.Card;
import game.model.dice.Die;

/**
 * Automatically confirms everything without asking the user.
 */
public class AutomaticConfirmer implements Confirmer
{
   @Override
   public boolean confirmNewGame() {
      return true;
   }

   @Override
   public boolean confirmLoadGame() {
      return true;
   }

   @Override
   public boolean confirmExitGame() {
      return true;
   }

   @Override
   public boolean confirmEndTurn() {
      return true;
   }

   @Override
   public boolean confirmSelectCard(Card card) {
      return true;
   }

   @Override
   public boolean confirmRerollActiveDice() {
      return true;
   }

   @Override
   public boolean confirmSetActiveDie(Die die) {
      return true;
   }

   @Override
   public boolean confirmAvatarChoice(int avatar) {
      return true;
   }

   @Override
   public boolean confirmCardEffect() {
      return true;
   }
}
