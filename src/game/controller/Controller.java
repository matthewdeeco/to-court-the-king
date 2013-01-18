package game.controller;

import game.model.*;
import game.model.card.Card;
import game.model.player.*;
import game.utility.*;
import game.view.*;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Controller
{
   private Model model;
   private AudioHandler audioHandler;
   private SaveLoadHandler gameSaveHandler;
   private Player currentPlayer;

   public Controller(Model model) {
      this.model = model;

      audioHandler = new AudioHandler(model);
      gameSaveHandler = new SaveLoadHandler();
   }

   public void newGame() {
      if (shouldStartNewGame())
         tryToStartNewGame();
   }

   private void tryToStartNewGame() {
      try {
         audioHandler.playTheme();
         startNewGame();
      }
      catch (Exception ex) {
         audioHandler.stopTheme();
      }
   }

   private void startNewGame() {
      model.clearGameData();
      model.notifyObservers();

      Integer playerCount = inputPlayerCount();
      addNewPlayers(playerCount);

      model.initializeGame();
      currentPlayer = model.getCurrentPlayer();

      model.notifyObservers();
   }

   /**
    * If the game has already started, asks the user first before starting a new one.
    * @return true if a new game should start.
    */
   private boolean shouldStartNewGame() {
      if (model.isGameStarted())
         return ConfirmHandler.confirmNewGame();
      else
         return true;
   }

   private Integer inputPlayerCount() {
      return Dialog.inputInteger("Enter number of players: ", 2, 4);
   }

   private void addNewPlayers(int playerCount) {
      for (int i = 0; i < playerCount; i++)
         model.addPlayer(PlayerFactory.createPlayer());
   }

   public void saveGame() {
      gameSaveHandler.saveState(model);
   }

   public void loadGame() {
      Model newGameModel = gameSaveHandler.retrieveState();
      if (newGameModel == null)
         return;
      else if (shouldLoadGame()) {
         audioHandler.playTheme();
         model.loadModel(newGameModel);

         currentPlayer = model.getCurrentPlayer();
         model.notifyObservers();
      }
   }

   /**
    * If the game has already started, asks the user first before loading a game.
    * @return true if a new game should start.
    */
   private boolean shouldLoadGame() {
      if (model.isGameStarted())
         return ConfirmHandler.confirmLoadGame();
      else
         return true;
   }

   public void exitGame() {
      if (shouldExitGame()) {
         audioHandler.stopTheme();
         System.exit(0);
      }
   }

   /**
    * If the game has already started, asks the user first before exiting the game.
    * @return true if the game should exit.
    */
   private boolean shouldExitGame() {
      boolean isGameStarted = model.isGameStarted();
      if (isGameStarted)
         return ConfirmHandler.confirmExitGame();
      return true;
   }

   public void rerollActiveDice() {
      if (ConfirmHandler.confirmRerollActiveDice()) {
         currentPlayer.rollActiveDice();
         model.setNoNewDice();
         model.notifyObservers();
      }
   }

   public void acquireCard() {
      CardSelectionDialog cardAcquireDialog = new CardSelectionDialog(model.getAcquirableCardNames());

      Card selectedCard = cardAcquireDialog.getSelectedCard();
      if (selectedCard == null)
         return;

      if (ConfirmHandler.confirmEndTurn()) {
         currentPlayer.acquireCard(selectedCard);

         model.decreaseCount(selectedCard);
         notifyIfNoMore(selectedCard);

         if (selectedCard == Card.CHARLATAN)
            charlatanAcquired();
         else if (selectedCard == Card.KING)
            kingAcquired();

         endTurn();
      }
   }

   /**
    * Checks if the card taken was the last of its kind.
    * @param card the card to be checked.
    */
   private void notifyIfNoMore(Card card) {
      boolean noMoreCards = !model.hasRemainingCard(card);
      boolean isNotKing = card != Card.KING;

      if (noMoreCards && isNotKing)
         Dialog.message("Well, that was the last\n" + card.getName()
                + " in the game!", card.getSmallIcon());
   }

   private void charlatanAcquired() {
      currentPlayer.removeCard(Card.FOOL);
      Dialog.infoMessage("Replaced the Fool with a Charlatan.");
   }

   private void kingAcquired() {
      model.initiateFinalRound();
      Dialog.message(model.getCurrentPlayerName()
             + " got the King Card.\nThat means the next round...\n"
             + "will be the final one!",
             Card.KING.getLargeIcon());

      for (Card card : Card.values())
         if (card.comesWithKing()) {
            currentPlayer.acquireCard(card);
            Dialog.message(currentPlayer.getName() + " got the "
                   + card.getName() + " Card\nalong with the King card.",
                   card.getLargeIcon());
            model.decreaseCount(card);
         }
   }

   public void endTurn() {
      model.setNoNewDice();

      if (model.isFinalRoundThisRound())
         notifyIfWinningPlayerChanged();
      else
         resetTurnData();

      boolean isFinalRoundLastRound = model.isFinalRoundThisRound();

      model.nextPlayer();
      model.notifyObservers();

      boolean isFinalRoundThisRound = model.isFinalRoundThisRound();

      currentPlayer = model.getCurrentPlayer();

      if (!isFinalRoundLastRound && isFinalRoundThisRound)
         notifyStartOfFinalRound();

      if (model.isFinalRoundThisRound())
         notifyPlayerOnFinalRound();
      else if (model.isGameOver())
         endGame();
   }

   /**
    * Notifies the user if the winning player has changed from last time.
    */
   private void notifyIfWinningPlayerChanged() {
      Player previousWinningPlayer = model.getWinningPlayer();
      model.computeGameWinner();
      Player currentWinningPlayer = model.getWinningPlayer();

      if (previousWinningPlayer == currentWinningPlayer)
         notifyWinningPlayerUnchanged();
      else
         notifyWinningPlayerChanged(previousWinningPlayer, currentWinningPlayer);
   }

   /**
    * Resets the player's end turn data such as temporary dice and re-rolling dice.
    */
   private void resetTurnData() {
      currentPlayer.activateAllDice();
      currentPlayer.resetAllCards();
      currentPlayer.clearTemporaryDice();
      currentPlayer.rollActiveDice();
   }

   private void notifyStartOfFinalRound() {
      Player winningPlayer = model.getWinningPlayer();
      Dialog.message("It's the start of the final round.\nEverybody wants to "
             + "beat " + winningPlayer.getName() + "'s roll!",
             winningPlayer.getAvatarIcon());
   }

   private void notifyPlayerOnFinalRound() {
      Player winningPlayer = model.getWinningPlayer();

      if (winningPlayer == currentPlayer)
         Dialog.message("You are winning the game so far.\n"
                + "Try to do even better than your last roll!",
                currentPlayer.getAvatarIcon());
      else {
         String winningPlayerName = winningPlayer.getName();
         String currentPlayerName = currentPlayer.getName();

         Dialog.message(winningPlayerName + " currently has the King card.\n"
                + currentPlayerName + ", you have to do your best to steal it!",
                winningPlayer.getAvatarIcon());
      }
   }

   private void endGame() {
      audioHandler.stopTheme();
      Dialog.message("And so, the winner is...");
      Player winningPlayer = model.getWinningPlayer();
      suspend(700);
      audioHandler.playVictoryFanfare();
      Dialog.message(winningPlayer.getName().toUpperCase()
             + "!!\n\nCongratulations!", winningPlayer.getAvatarIcon());
      model.clearGameData();
      model.notifyObservers();
   }

   /**
    * Suspends the program for a given time.
    * @param millis the number of milliseconds to wait.
    */
   private void suspend(long millis) {
      try {
         Thread.sleep(millis);
      }
      catch (InterruptedException ex) {
      }
   }

   /**
    * Notifies the user that the winning player has NOT changed.
    */
   private void notifyWinningPlayerUnchanged() {
      Player winningPlayer = model.getWinningPlayer();

      String winningPlayerName = winningPlayer.getName();
      String currentPlayerName = currentPlayer.getName();

      if (currentPlayer != winningPlayer)
         Dialog.message(currentPlayerName + " failed to beat "
                + winningPlayerName + "'s roll...\n"
                + currentPlayerName + ", don't cry.. *sob*",
                currentPlayer.getAvatarIcon());
   }

   /**
    * Notifies the user that the winning player has changed.
    * @param previousWinningPlayer the previous player who was winning.
    * @param currentWinningPlayer the current player winning.
    */
   private void notifyWinningPlayerChanged(Player previousWinningPlayer, Player currentWinningPlayer) {
      String previousWinningPlayerName = previousWinningPlayer.getName();
      String currentWinningPlayerName = currentWinningPlayer.getName();

      previousWinningPlayer.removeCard(Card.KING);
      currentWinningPlayer.acquireCard(Card.KING);

      Dialog.message(currentWinningPlayerName + " beat "
             + previousWinningPlayerName + "'s roll!\n"
             + currentWinningPlayerName + " now has the King!",
             currentWinningPlayer.getAvatarIcon());
   }

   public void useCard() {
      CardSelectionDialog cardAcquireDialog = new CardSelectionDialog(model.getUsableCardNames());
      Card selectedCard = cardAcquireDialog.getSelectedCard();

      if (selectedCard != null)
         currentPlayer.useCard(selectedCard);
      model.notifyObservers();
   }

   /**
    * The event when the user presses a die button.
    * @param dieIndex the index of the die button pressed.
    */
   public void setDie(int dieIndex) {
      if (ConfirmHandler.confirmSetActiveDie(currentPlayer.getDie(dieIndex))) {
         currentPlayer.setDie(dieIndex);
         model.setNewDice();
         model.notifyObservers();
      }
   }

   public void turnSoundOn() {
      audioHandler.playTheme();
   }

   public void turnSoundOff() {
      audioHandler.stopTheme();
   }

   public String[] getTrackTitles() {
      return audioHandler.getTrackTitles();
   }

   public void setTrack(int trackIndex) {
      audioHandler.setTheme(trackIndex);
   }

   public void displayHelp() {
      HelpManual.display();
   }

   public void displayAbout() {
      String message = "Version '" + model.getGameVersion()
             + "' of " + model.getGameTitle() + "\nAuthor: Matthew Co ";
      String title = "About";
      ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource(
             "images/versions/" + model.getGameVersion() + ".gif"));

      JOptionPane.showMessageDialog(null, message, title,
             JOptionPane.PLAIN_MESSAGE, icon);
   }

   public void confirmFirst() {
      ConfirmHandler.useDialogConfirmer();
   }

   public void doNotConfirm() {
      ConfirmHandler.useAutomaticConfirmer();
   }
}
