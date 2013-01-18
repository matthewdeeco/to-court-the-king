package game.model.card.ability;

import game.view.ActiveUnflaggedDieSelectionDialog;
import game.model.card.CardAbility;
import game.model.dice.*;
import game.controller.ConfirmHandler;
import game.model.player.Player;
import game.utility.Dialog;

/**
 * The ability to add 1, 2, or 3 to one active die.
 */
public class AddOneTwoOrThreeToOneDie extends CardAbility
{
   private Player usingPlayer;
   private Die chosenDie;

   public AddOneTwoOrThreeToOneDie() {
      setDescription("Add 1, 2, or 3 to one \nactive die.");
   }

   @Override
   public boolean isUsable(Player player) {
      return player.hasActiveDieWithValueLessThan(Die.MAX_VALUE);
   }

   @Override
   public void use(Player player) {
      if (!isUsable(player))
         throw new UnusableAbilityException();

      usingPlayer = player;
      chooseDice();
      addValueToDie();
   }

   private void chooseDice() {
      do {
         usingPlayer.unflagCardAllDice();

         if (hasOnlyOneValidDie())
            chooseTheOnlyValidDie();
         else
            askForDie();
      }
      while (!(ConfirmHandler.confirmCardEffect()));
   }

   /**
    * Checks if the using player only has one valid die that can be added.
    * @return true if there is only 1 valid die, else false.
    */
   private boolean hasOnlyOneValidDie() {
      return usingPlayer.getNumberOfActiveDiceWithValueLessThan(Die.MAX_VALUE) == 1;
   }

   /** Sets the only valid die as the chosen die. */
   private void chooseTheOnlyValidDie() {
      for (Die die : usingPlayer.getDice())
         if (die.isActive() && canBeAdded(die)) {
            chosenDie = die;
            Dialog.message("You will add values to this die.", die.getImageIcon());
            break;
         }
   }

   /** Ask to choose a die. */
   private void askForDie() {
      flagAllInvalidDice();
      ActiveUnflaggedDieSelectionDialog dieDialog =
             new ActiveUnflaggedDieSelectionDialog(usingPlayer.getDice());
      chosenDie = dieDialog.getSelectedDie();
      chosenDie.setCardFlag(true);
   }

   /** Checks all of the dice for validity and flags the invalid ones. */
   private void flagAllInvalidDice() {
      for (Die die : usingPlayer.getDice())
         if (!canBeAdded(die))
            die.setCardFlag(true);
   }

   /**
    * Checks if the die can be added at least 1.
    * @param die the die that will be checked.
    */
   private boolean canBeAdded(Die die) {
      int maxValueToAdd = computeMaxValueToAdd(die);
      return maxValueToAdd >= 1;
   }

   /** Compute the maximum value that can be added to the die. */
   private int computeMaxValueToAdd(Die die) {
      return Math.min(3, Die.MAX_VALUE - die.getValue());
   }

   /** Add the value to add to the chosen die. */
   private void addValueToDie() {
      int valueToAdd = inputValueToAdd();

      int newValue = chosenDie.getValue() + valueToAdd;
      chosenDie.setValue(newValue);
   }

   /** Input the value to add to the chosen die. */
   private int inputValueToAdd() {
      int minValueToAdd = 0;
      int maxValueToAdd = computeMaxValueToAdd(chosenDie);
      return Dialog.inputInteger("Enter the amount to add: ", minValueToAdd, maxValueToAdd);
   }
}
