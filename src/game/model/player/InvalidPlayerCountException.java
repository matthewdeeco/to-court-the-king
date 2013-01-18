package game.model.player;

public class InvalidPlayerCountException extends IllegalArgumentException
{
   public InvalidPlayerCountException() {
      super("The player count is invalid!");
   }

   public InvalidPlayerCountException(String message) {
      super(message);
   }
}
