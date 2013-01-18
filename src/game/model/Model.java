package game.model;

import game.model.card.*;
import game.model.dice.RollScorer;
import game.model.player.*;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Model implements Serializable
{
   private enum TurnState
   {
      NO_SET_DIE, ONE_OR_MORE_SET_DIE
   }

   private enum GameState
   {
      GAME_NOT_STARTED, NORMAL_ROUND, FINAL_ROUND_NEXT_ROUND, FINAL_ROUND_THIS_ROUND
   }
   private transient final String GAME_TITLE = "To Court the King";
   private transient final String GAME_VERSION = "Clone";
   private transient ArrayList<ModelObserver> observers;
   private transient RollScorer rollScorer;
   private int turnCount;
   private int roundCount;
   private PlayerQueue players;
   private Player currentPlayer;
   private Player nextPlayer;
   private Player winningPlayer;
   private int winningScore;
   private CardCounter cardCounter;
   private TurnState turnState;
   private GameState gameState;

   public Model() {
      observers = new ArrayList<ModelObserver>();

      players = new PlayerQueue();
      gameState = GameState.GAME_NOT_STARTED;
      rollScorer = new RollScorer();
   }

   public void initializeGame() {
      turnState = TurnState.NO_SET_DIE;
      gameState = GameState.NORMAL_ROUND;
      roundCount = 1;
      turnCount = 0;
      cardCounter = new CardCounter(players.getPlayerCount());
      currentPlayer = players.getCurrentPlayer();
      nextPlayer = players.getNextPlayer();
   }

   public void clearGameData() {
      players = new PlayerQueue();
      turnState = TurnState.NO_SET_DIE;
      gameState = GameState.GAME_NOT_STARTED;
      roundCount = 1;
      turnCount = 0;
   }

   public void addPlayer(Player newPlayer) {
      players.addPlayer(newPlayer);
   }

   public void nextPlayer() {
      players.nextPlayer();
      currentPlayer = players.getCurrentPlayer();
      turnState = TurnState.NO_SET_DIE;
      turnCount++;
      if (isFinalRoundThisRound() && turnCount == getPlayerCount() - 1)
         nextPlayer = null;
      else
         nextPlayer = players.getNextPlayer();
      if (isStartOfNewRound()) {
         turnCount = 0;
         roundCount++;
         if (isFinalRoundNextRound())
            gameState = GameState.FINAL_ROUND_THIS_ROUND;
         else if (isFinalRoundThisRound())
            gameOver();
      }
   }

   private boolean isStartOfNewRound() {
      int playerCount = players.getPlayerCount();
      return turnCount == playerCount;
   }

   public void computeGameWinner() {
      int currentScore = rollScorer.score(currentPlayer.getDice());
      if (currentScore > winningScore) {
         winningScore = currentScore;
         winningPlayer = currentPlayer;
      }
   }

   /**  Initiates the final round (final round next round). */
   public void initiateFinalRound() {
      gameState = GameState.FINAL_ROUND_NEXT_ROUND;
      winningPlayer = currentPlayer;
      winningScore = rollScorer.score(currentPlayer.getDice());
   }

   public void gameOver() {
      gameState = GameState.GAME_NOT_STARTED;
      computeGameWinner();
   }

   /** Called when there has already been one or more set dice. */
   public void setNewDice() {
      turnState = TurnState.ONE_OR_MORE_SET_DIE;
   }

   /** Called if there is now no additional set dice. */
   public void setNoNewDice() {
      turnState = TurnState.NO_SET_DIE;
   }

   public void decreaseCount(Card card) {
      cardCounter.decreaseCount(card);
   }

   public int getRemainingCount(Card card) {
      return cardCounter.getRemaining(card);
   }

   /** @return true if the current player can re-roll his/her active dice. */
   public boolean canRerollActiveDice() {
      boolean hasSetAtLeastOneDie =
             (turnState == TurnState.ONE_OR_MORE_SET_DIE);
      boolean hasActiveDice =
             (currentPlayer.hasActiveDice());

      return (hasSetAtLeastOneDie && hasActiveDice);
   }

   public boolean hasUsableCards() {
      return currentPlayer.hasUsableCards();
   }

   public boolean hasNextPlayer() {
      return nextPlayer != null;
   }

   public boolean hasRemainingCard(Card card) {
      return cardCounter.isAvailable(card);
   }

   public boolean isGameStarted() {
      return gameState != GameState.GAME_NOT_STARTED;
   }

   public boolean isFinalRoundNextRound() {
      return gameState == GameState.FINAL_ROUND_NEXT_ROUND;
   }

   public boolean isFinalRoundThisRound() {
      return gameState == GameState.FINAL_ROUND_THIS_ROUND;
   }

   public boolean isGameOver() {
      return gameState == GameState.GAME_NOT_STARTED;
   }

   public String getCurrentPlayerName() {
      return currentPlayer.getName();
   }

   public String getNextPlayerName() {
      return nextPlayer.getName();
   }

   public ImageIcon getCurrentPlayerAvatar() {
      return currentPlayer.getAvatarIcon();
   }

   public ImageIcon getNextPlayerAvatar() {
      return nextPlayer.getAvatarIcon();
   }

   public int getPlayerCount() {
      return players.getPlayerCount();
   }

   public ArrayList<String> getAcquirableCardNames() {
      ArrayList<String> acquirableCardNames = new ArrayList<String>();
      for (Card card : Card.values()) {
         boolean currentPlayerCanAcquire = currentPlayer.canAcquire(card);
         boolean hasRemainingCopies = cardCounter.isAvailable(card);

         if (currentPlayerCanAcquire && hasRemainingCopies)
            acquirableCardNames.add(card.getName());
      }
      return acquirableCardNames;
   }

   public ArrayList<String> getUsableCardNames() {
      ArrayList<String> usableCardNames = new ArrayList<String>();
      for (Card card : Card.values())
         if (currentPlayer.canUse(card))
            usableCardNames.add(card.getName());
      return usableCardNames;
   }

   public int getRoundCount() {
      return roundCount;
   }

   public Player getCurrentPlayer() {
      return currentPlayer;
   }

   public Player getNextPlayer() {
      return nextPlayer;
   }

   public Player getWinningPlayer() {
      return winningPlayer;
   }

   public String getGameTitle() {
      return GAME_TITLE;
   }

   public String getGameVersion() {
      return GAME_VERSION;
   }

   /** Notifies the observers that the game model has changed. */
   public void notifyObservers() {
      for (ModelObserver observer : observers)
         try {
            observer.modelChanged();
         }
         catch (Exception ex) {
         }
   }

   public void registerObserver(ModelObserver observer) {
      observers.add(observer);
   }

   /** Changes this model's data to the loaded model data. */
   public void loadModel(Model modelToLoad) {
      if (modelToLoad == null)
         return;
      players = modelToLoad.players;
      currentPlayer = modelToLoad.currentPlayer;
      nextPlayer = modelToLoad.nextPlayer;
      cardCounter = modelToLoad.cardCounter;
      turnState = modelToLoad.turnState;
      gameState = modelToLoad.gameState;
      roundCount = modelToLoad.roundCount;
      turnCount = modelToLoad.turnCount;
      winningPlayer = modelToLoad.winningPlayer;
      winningScore = modelToLoad.winningScore;
   }

   @Override
   public String toString() {
      StringBuilder string = new StringBuilder();
      string.append("==============================").
             append("==============================").
             append("====================\r\n").
             append(getGameTitle()).
             append(" Version '").
             append(getGameVersion()).
             append("'\r\n").
             append("==============================").
             append("==============================").
             append("====================\r\n").
             append("Current Game State : ").
             append(gameState).
             append("\r\n").
             append("Current Turn State : ").
             append(turnState).
             append("\r\n").
             append("Round Number       : ").
             append(roundCount).
             append("\r\n").
             append("Turn Number        : ").
             append(turnCount).
             append("\r\n").
             append("==============================").
             append("==============================").
             append("====================\r\n").
             append("Winning score      : ").
             append(winningScore).
             append("\r\n").
             append("==============================").
             append("==============================").
             append("====================\r\n").
             append("Remaining cards\r\n").
             append(cardCounter).
             append("==============================").
             append("==============================").
             append("====================\r\n").
             append("Number of players  : ").
             append(players.getPlayerCount()).
             append("\r\n").
             append("==============================").
             append("==============================").
             append("====================\r\n").
             append(players).
             append("==============================").
             append("==============================").
             append("====================\r\n");
      return string.toString();
   }
}
