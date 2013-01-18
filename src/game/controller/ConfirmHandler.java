package game.controller;

import game.controller.confirmer.*;
import game.model.card.Card;
import game.model.dice.Die;

/**
 * Handles confirming user actions.
 */
public class ConfirmHandler
{
   private static final Confirmer AUTOMATIC_CONFIRMER = new AutomaticConfirmer();
   private static final Confirmer DIALOG_CONFIRMER = new DialogConfirmer();
   private static Confirmer confirmer = AUTOMATIC_CONFIRMER;

   private ConfirmHandler() {
   }

   public static boolean confirmNewGame() {
      return confirmer.confirmNewGame();
   }

   public static boolean confirmLoadGame() {
      return confirmer.confirmLoadGame();
   }

   public static boolean confirmExitGame() {
      return confirmer.confirmExitGame();
   }

   public static boolean confirmSelectCard(Card card) {
      return confirmer.confirmSelectCard(card);
   }

   public static boolean confirmEndTurn() {
      return confirmer.confirmEndTurn();
   }

   public static boolean confirmRerollActiveDice() {
      return confirmer.confirmRerollActiveDice();
   }

   public static boolean confirmSetActiveDie(Die die) {
      return confirmer.confirmSetActiveDie(die);
   }

   public static boolean confirmAvatarChoice(int avatar) {
      return confirmer.confirmAvatarChoice(avatar);
   }

   public static boolean confirmCardEffect() {
      return confirmer.confirmCardEffect();
   }

   public static void useAutomaticConfirmer() {
      confirmer = AUTOMATIC_CONFIRMER;
   }

   public static void useDialogConfirmer() {
      confirmer = DIALOG_CONFIRMER;
   }
}
