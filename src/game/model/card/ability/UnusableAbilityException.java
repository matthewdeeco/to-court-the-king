package game.model.card.ability;

/**
 * Thrown when the ability is attempted to be used without the valid conditions.
 */
public class UnusableAbilityException extends IllegalStateException
{
   public UnusableAbilityException() {
      super("The ability cannot be used in the current conditions!");
   }

   public UnusableAbilityException(String message) {
      super(message);
   }
}
