package game.model.card.ability;

import game.view.ActiveUnflaggedDieSelectionDialog;
import game.model.card.CardAbility;
import game.model.dice.*;
import game.controller.ConfirmHandler;
import game.model.player.Player;
import game.utility.Dialog;

/**
 * The ability to reroll active dice.
 */
public class RerollDice extends CardAbility
{
   /** Number of dice to reroll for when the user can reroll any number of dice. */
   public static final int ANY_NUMBER = -1;
   /** The number of dice that can be rerolled by the ability. */
   private int nDiceThatCanBeRerolled;
   private Player usingPlayer;
   private Dice chosenDice;

   public RerollDice(int nDiceThatCanBeRerolled) {
      this.nDiceThatCanBeRerolled = nDiceThatCanBeRerolled;
      setDescription(createDescription());
   }

   @Override
   public boolean isUsable(Player player) {
      int diceCount = player.getActiveDiceCount();

      if (canRollAnyNumberOfDice())
         return player.hasActiveDice();
      else
         return diceCount >= nDiceThatCanBeRerolled;
   }

   @Override
   public void use(Player player) {
      if (!isUsable(player))
         throw new UnusableAbilityException();

      usingPlayer = player;
      chooseDice();
      rerollChosenDice();
   }

   private void chooseDice() {
      do {
         usingPlayer.unflagCardAllDice();
         chosenDice = new Dice();

         int playerDiceCount = usingPlayer.getActiveDiceCount();
         int nDicePlayerWillReroll = getDiceToReroll(playerDiceCount);

         if (nDicePlayerWillReroll == playerDiceCount)
            addAllDice();
         else
            askForDice(nDicePlayerWillReroll);
      }
      while (!ConfirmHandler.confirmCardEffect());
   }

   /** Adds all possible dice to be used by the card instead of asking the player. */
   private void addAllDice() {
      for (Die die : usingPlayer.getDice())
         if (die.isActive())
            chosenDice.addDie(die);
      Dialog.infoMessage("All active dice will be re-rolled.");
   }

   /** Gets the dice desired by the player. */
   private void askForDice(int nDicePlayerWillReroll) {
      for (int i = 0; i < nDicePlayerWillReroll; i++) {
         ActiveUnflaggedDieSelectionDialog dieDialog =
                new ActiveUnflaggedDieSelectionDialog(
                usingPlayer.getDice(),
                "Choose die #" + (i + 1) + " to re-roll");
         Die selectedDie = dieDialog.getSelectedDie();
         selectedDie.setCardFlag(true);
         chosenDice.addDie(selectedDie);
      }
   }

   /** Rerolls all of the dice in the chosen dice list. */
   private void rerollChosenDice() {
      for (Die die : chosenDice)
         die.roll();
   }

   /**
    * @param playerDiceCount the number of dice the player has.
    * @return the number of dice to be re-rolled.
    */
   private int getDiceToReroll(int playerDiceCount) {
      if (canRollAnyNumberOfDice())
         return Dialog.inputInteger("How many dice would you want to re-roll?", 0, playerDiceCount);
      else
         return nDiceThatCanBeRerolled;
   }

   /** @return true if the ability can reroll any number of dice, else false. */
   private boolean canRollAnyNumberOfDice() {
      return nDiceThatCanBeRerolled == ANY_NUMBER;
   }

   private String createDescription() {
      StringBuilder string = new StringBuilder();
      string.append("Re-roll ").
             append(canRollAnyNumberOfDice()
             ? "any number of \n" : nDiceThatCanBeRerolled).
             append(" active ").
             append(nDiceThatCanBeRerolled == 1 ? "die." : "dice.");
      return string.toString();
   }
}
