package game.utility;

public class StringHelper
{
   private StringHelper() {
   }

   /**
    * Converts a string to title case.
    * Title case means first letter is capitalized, the rest are lower case.
    * @param original the original string.
    * @return The new string, in title case.
    */
   public static String convertToTitleCase(String original) {
      String firstLetter = original.substring(0, 1);
      String otherLetters = original.substring(1);

      String firstLetterUpper = firstLetter.toUpperCase();
      String otherLettersLower = otherLetters.toLowerCase();

      return firstLetterUpper + otherLettersLower;
   }
}
