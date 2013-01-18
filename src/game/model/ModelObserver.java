package game.model;

/**
 * Interface for objects that observe when the game model changes.
 */
public interface ModelObserver
{
   /**
    * Called when the game model changes.
    */
   public void modelChanged();
}
