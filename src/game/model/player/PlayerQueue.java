package game.model.player;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Iterator;

public class PlayerQueue implements Iterable<Player>, Serializable
{
   private ArrayDeque<Player> players;

   public PlayerQueue() {
      players = new ArrayDeque<Player>();
   }

   @Override
   public Iterator<Player> iterator() {
      return players.iterator();
   }

   /**
    * Adds a new player to the player queue.
    * The player is placed in a random position.
    * @param newPlayer the player to add.
    */
   public void addPlayer(Player newPlayer) {
      if (Math.random() < 0.5)
         players.addFirst(newPlayer);
      else
         players.addLast(newPlayer);
   }

   public Player getCurrentPlayer() {
      return players.peek();
   }

   public Player getNextPlayer() {
      Player currentPlayer = players.poll();
      Player nextPlayer = players.peek();
      players.addFirst(currentPlayer);
      return nextPlayer;
   }

   public void nextPlayer() {
      Player currentPlayer = players.poll();
      players.addLast(currentPlayer);
   }

   public int getPlayerCount() {
      return players.size();
   }

   @Override
   public String toString() {
      StringBuilder string = new StringBuilder();

      int counter = 1;
      for (Player player : players) {
         string.append(player);
         if (counter < getPlayerCount())
            string.append("=============================="
                   + "=============================="
                   + "====================\r\n");
         counter++;
      }

      return string.toString();
   }
}