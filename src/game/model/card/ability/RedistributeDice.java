package game.model.card.ability;

import game.view.ActiveUnflaggedDieSelectionDialog;
import game.model.card.CardAbility;
import game.model.dice.*;
import game.controller.ConfirmHandler;
import game.model.player.Player;
import game.utility.Dialog;

/**
 * The ability to redistribute the total value of active dice among themselves.
 */
public class RedistributeDice extends CardAbility
{
   /** The number of dice the card will redistribute. */
   private int nDiceToRedistribute;
   private Player usingPlayer;
   private Dice chosenDice;

   public RedistributeDice(int nDiceToRedistribute) {
      this.nDiceToRedistribute = nDiceToRedistribute;
      setDescription("Redistribute the total \nvalue of "
             + nDiceToRedistribute + " active dice among \nthemselves.");
   }

   @Override
   public boolean isUsable(Player player) {
      return player.getActiveDiceCount() >= nDiceToRedistribute;
   }

   @Override
   public void use(Player player) {
      if (!isUsable(player))
         throw new UnusableAbilityException();

      usingPlayer = player;
      chooseDice();
      inputNewDiceValues();
   }

   private void chooseDice() {
      do {
         usingPlayer.unflagCardAllDice();
         chosenDice = new Dice();
         if (allDiceShouldBeRedistributed())
            addAllDice();
         else
            askForDice();
      }
      while (!(ConfirmHandler.confirmCardEffect()));
   }

   /**
    * Checks if all dice should be redistributed.
    * @return true if all dice should be redistributed, false if the user should choose.
    */
   private boolean allDiceShouldBeRedistributed() {
      return usingPlayer.getActiveDiceCount() == nDiceToRedistribute;
   }

   /** Adds all of the dice to the chosen dice without asking the user. */
   private void addAllDice() {
      for (Die die : usingPlayer.getDice())
         if (die.isActive())
            chosenDice.addDie(die);
   }

   /** Ask the user to choose dice. */
   private void askForDice() {
      ActiveUnflaggedDieSelectionDialog dieDialog;
      for (int i = 0; i < nDiceToRedistribute; i++) {
         dieDialog = new ActiveUnflaggedDieSelectionDialog(
                usingPlayer.getDice(),
                "Choose die #" + (i + 1) + " to redistribute");
         Die chosenDie = dieDialog.getSelectedDie();
         chosenDie.setCardFlag(true);
         chosenDice.addDie(chosenDie);
      }
   }

   /** Makes the user input new dice values. */
   private void inputNewDiceValues() {
      int requiredDiceSum = computeRequiredSum();
      int currentDiceSum = 0;
      int remainingDiceSum = requiredDiceSum;
      int currentIndex = 0;

      for (Die die : chosenDice) {
         String message = "Enter the new value for die #" + (currentIndex + 1);
         Integer newValue;
         if (currentIndex < chosenDice.getDiceCount() - 1) {
            int remainingDiceCount = chosenDice.getDiceCount() - currentIndex - 1;

            int remainingDiceMaxTotal = remainingDiceCount * Die.MAX_VALUE;
            int rangeMin = Math.max(Die.MIN_VALUE,
                   remainingDiceSum - remainingDiceMaxTotal);

            int remainingDiceMinTotal = remainingDiceCount * Die.MIN_VALUE;
            int rangeMax = Math.min(Die.MAX_VALUE,
                   remainingDiceSum - remainingDiceMinTotal);

            newValue = Dialog.inputInteger(message, rangeMin, rangeMax);
         }
         else
            newValue = requiredDiceSum - currentDiceSum;

         if (newValue == null)
            throw new NullPointerException();

         remainingDiceSum -= newValue;
         currentDiceSum += newValue;

         currentIndex++;
         Dialog.infoMessage("The value of die #" + currentIndex
                + " will now be " + newValue + ".");
         die.setValue(newValue);
      }
   }

   /** Computes the sum needed. */
   private int computeRequiredSum() {
      int requiredDiceSum = 0;
      for (Die die : chosenDice)
         requiredDiceSum += die.getValue();

      return requiredDiceSum;
   }
}
