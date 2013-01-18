package game.model.card.ability;

import game.view.ActiveUnflaggedDieSelectionDialog;
import game.model.card.CardAbility;
import game.model.dice.*;
import game.model.player.Player;
import game.controller.ConfirmHandler;
import game.utility.Dialog;

/**
 * The ability to add to the values of any number of active dice.
 */
public class AddToAnyDice extends CardAbility
{
   private int addedValue;
   private Player usingPlayer;
   private Dice chosenDice;

   public AddToAnyDice(int addedValue) {
      this.addedValue = addedValue;
      setDescription("Add exactly " + addedValue
             + " to the \nvalues of any number of active dice.");
   }

   @Override
   public boolean isUsable(Player player) {
      return player.hasActiveDieWithValueLessThan(Die.MAX_VALUE - addedValue + 1);
   }

   @Override
   public void use(Player player) {
      if (!isUsable(player))
         throw new UnusableAbilityException();

      usingPlayer = player;
      chooseDice();
      addValueToChosenDice();
   }

   private void chooseDice() {
      do {
         usingPlayer.unflagCardAllDice();

         chosenDice = new Dice();

         int maxDiceToAdd = computeMaxDiceToAdd();
         int diceToAdd = inputDiceToAdd(maxDiceToAdd);

         if (diceToAdd == maxDiceToAdd)
            addAllDice();
         else
            askForDice(diceToAdd);
      }
      while (!(ConfirmHandler.confirmCardEffect()));
   }

   /** Computes the maximum number of dice that can be added with the value to be added. */
   private int computeMaxDiceToAdd() {
      return usingPlayer.getNumberOfActiveDiceWithValueLessThan(
             Die.MAX_VALUE - addedValue + 1);
   }

   /** @return the value to be added to the dice. */
   private int inputDiceToAdd(int maxDiceToAdd) {
      return Dialog.inputInteger(
             "How many dice would you want to add " + addedValue + " to?",
             0, maxDiceToAdd);
   }

   /**
    * Gets the dice desired by the user.
    * @param diceToAdd the number of dice that the user will add.
    */
   private void askForDice(int diceToAdd) {
      flagAllInvalidDice();

      ActiveUnflaggedDieSelectionDialog dieDialog;
      for (int i = 0; i < diceToAdd; i++) {
         dieDialog = new ActiveUnflaggedDieSelectionDialog(
                usingPlayer.getDice(), "Choose die #" + (i + 1)
                + " to add " + addedValue + " to");
         Die chosenDie = dieDialog.getSelectedDie();
         chosenDie.setCardFlag(true);
         chosenDice.addDie(chosenDie);
      }
   }

   /** Checks all of the dice for validity and flags the invalid ones. */
   private void flagAllInvalidDice() {
      for (Die die : usingPlayer.getDice())
         if (!canBeAdded(die))
            die.setCardFlag(true);
   }

   /** Adds all possible dice to be used by the card instead of asking the user. */
   private void addAllDice() {
      for (Die die : usingPlayer.getDice())
         if (die.isActive() && canBeAdded(die))
            chosenDice.addDie(die);
      Dialog.infoMessage(addedValue + " will be added to all of your active dice.");
   }

   /**
    * Checks if the die can be added.
    * @param die the die to be added.
    * @return true if the die can be added, false if it cannot.
    */
   private boolean canBeAdded(Die die) {
      int dieValue = die.getValue();
      int result = dieValue + addedValue;
      return result <= Die.MAX_VALUE;
   }

   /** Adds the addedValue to the chosen dice. */
   private void addValueToChosenDice() {
      for (Die die : chosenDice) {
         int newValue = die.getValue() + addedValue;
         die.setValue(newValue);
      }
   }
}
