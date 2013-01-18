package game.model.dice;

/**
 * Gives a score for dice rolls.
 */
public class RollScorer
{
   /**
    * Returns the rating of the dice passed.
    * @param diceToScore the dice collection to score.
    * @return the dice rating.
    */
   public int score(Dice diceToScore) {
      return computeRating(diceToScore);
   }

   private int computeRating(Dice diceToScore) {
      int maxOfAKind = diceToScore.getNumberOf(Die.MIN_VALUE);
      int nMaxOfAKind = Die.MIN_VALUE;

      for (int i = Die.MIN_VALUE; i <= Die.MAX_VALUE; i++)
         // we are only concerned with the maximum n-of-a-kinds, not the other dice.
         if (diceToScore.getNumberOf(i) >= maxOfAKind) {
            maxOfAKind = diceToScore.getNumberOf(i);
            nMaxOfAKind = i;
         }

      int rating = nMaxOfAKind + (maxOfAKind * (Die.MAX_VALUE + 1));
      return rating;
   }
}
