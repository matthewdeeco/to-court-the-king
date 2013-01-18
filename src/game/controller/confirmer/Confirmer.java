package game.controller.confirmer;

import game.model.card.Card;
import game.model.dice.Die;

/**
 * Interface for the confirmer which handles confirming user actions.
 */
public interface Confirmer
{
   /**
    * Confirms the user for starting a new game.
    * @return true if the action is confirmed, false if it is denied.
    */
   public boolean confirmNewGame();

   /**
    * Confirms the user for loading a game.
    * @return true if the action is confirmed, false if it is denied.
    */
   public boolean confirmLoadGame();

   /**
    * Confirms the user for exiting the game.
    * @return true if the action is confirmed, false if it is denied.
    */
   public boolean confirmExitGame();

   /**
    * Confirms the user for selecting a card.
    * @param card the card to be selected.
    * @return true if the action is confirmed, false if it is denied.
    */
   public boolean confirmSelectCard(Card card);

   /**
    * Confirms the user for ending his/her turn.
    * @return true if the action is confirmed, false if it is denied.
    */
   public boolean confirmEndTurn();

   /**
    * Confirms the user for re-rolling active dice.
    * @return true if the action is confirmed, false if it is denied.
    */
   public boolean confirmRerollActiveDice();

   /**
    * Confirms the user for setting an active die.
    * @param die the die to be set.
    * @return true if the action is confirmed, false if it is denied.
    */
   public boolean confirmSetActiveDie(Die die);

   /**
    * Confirms the user for choosing an avatar.
    * @param avatar the number of the avatar.
    * @return true if the action is confirmed, false if it is denied.
    */
   public boolean confirmAvatarChoice(int avatar);

   /**
    * Confirms the user's card effect.
    * @return true if the action is confirmed, false if it is denied.
    */
   public boolean confirmCardEffect();
}
