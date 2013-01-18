package game.model.card;

public class InvalidRankException extends Exception
{
   public InvalidRankException() {
      super("The rank is invalid!");
   }

   public InvalidRankException(String message) {
      super(message);
   }
}
