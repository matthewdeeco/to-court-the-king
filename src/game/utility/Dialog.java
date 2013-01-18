package game.utility;

import game.view.IntegerRangeDialog;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import static javax.swing.JOptionPane.*;

/**
 * Manages user dialogs to input data into the program.
 */
public class Dialog
{
   private Dialog() {
   }

   /**
    * Asks the user to input a string with the default message.
    * @return a non-empty string.
    */
   public static String inputString() {
      return inputString("Enter a sequence of characters: ");
   }

   /**
    * Asks the user to input a string.
    * @param message the desired message to be displayed.
    * @return a non-empty string.
    */
   public static String inputString(String message) {
      while (true) {
         try {
            String currentString = showInputDialog(null, message,
                   "Input", QUESTION_MESSAGE);
            if (!currentString.isEmpty()) {
               return currentString;
            }
            errorMessage("Your input was invalid!");
         }
         catch (NullPointerException ex) {
            return null;
         }
      }
   }

   /**
    * Asks the user to input a string with a maximum length.
    * @param message the desired message to be displayed.
    * @param maxLength the maximum number of characters in the string.
    * @return a string with length less than or equal to the maxLength.
    */
   public static String inputString(String message, int maxLength) {
      while (true) {
         String currentString = inputString(message + " (" + maxLength
                + " letters max)");

         if (currentString == null)
            return null;
         else if (currentString.length() <= maxLength) {
            return currentString;
         }
         else
            errorMessage("Length exceeded " + maxLength + " letters!");
      }
   }

   /**
    * Asks the user to input an integer with the default message.
    * @return an integer.
    */
   public static int inputInteger() {
      return inputInteger("Enter an integer: ");
   }

   /**
    * Asks the user to input an integer.
    * @param message the desired message to be displayed.
    * @return an integer.
    */
   public static Integer inputInteger(String message) {
      while (true) {
         try {
            String input = showInputDialog(null, message, "Input", QUESTION_MESSAGE);
            if (input == null)
               return null;
            else {
               int currentInteger = Integer.parseInt(input);
               return currentInteger;
            }
         }
         catch (NumberFormatException ex) {
            errorMessage("Please enter an integer!");
         }
      }
   }

   /**
    * Asks the user to input an integer in a specified range.
    * If the upper bound is equal to the lower bound, the choice is automatically made.
    * @param message the desired message to be displayed.
    * @param min the lower bound in the range.
    * @param max the upper bound in the range.
    * @return an integer in the specified range.
    */
   public static Integer inputInteger(String message, int min, int max) {
      if (min == max)
         return min;
      else if (max < min)
         throw new IllegalArgumentException(max + " was less than " + min + "!");
      else { // max > min
         IntegerRangeDialog integerDialog = new IntegerRangeDialog(message, min, max);
         return integerDialog.getSelectedInteger();
      }
   }

   /**
    * Prompts the user with a yes / no option.
    * @param message the message to be displayed.
    * @return true if yes, false if no.
    */
   public static boolean confirmYesNo(String message) {
      return showConfirmDialog(null, message, "Confirm", YES_NO_OPTION, WARNING_MESSAGE) == YES_OPTION;
   }

   /**
    * Prompts the user with a yes / no option.
    * @param message the message to be displayed.
    * @param icon the icon to be displayed.
    * @return true if yes, false if no.
    */
   public static boolean confirmYesNo(String message, Icon icon) {
      return showConfirmDialog(null, message, "Confirm", YES_NO_OPTION, PLAIN_MESSAGE, icon) == YES_OPTION;
   }

   /**
    * Gets user input as save file path.
    * @param description the save file description.
    * @param format the save file format.
    * @return the save file path string, null if the operation was aborted.
    */
   public static String inputSaveFilePath(String description, String format) {
      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setFileFilter(
             new FileNameExtensionFilter(description, format));
      int userChoice = fileChooser.showSaveDialog(null);

      if (fileChooser.getSelectedFile() == null || userChoice == JFileChooser.CANCEL_OPTION)
         return null;
      else
         return fileChooser.getSelectedFile().getAbsolutePath();
   }

   /**
    * Gets user input as load file path.
    * @param description the save file description.
    * @param format the save file format.
    * @return the save file path string, null if the operation was aborted.
    */
   public static String inputLoadFilePath(String description, String format) {
      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setFileFilter(
             new FileNameExtensionFilter(description, format));
      int userChoice = fileChooser.showOpenDialog(null);

      if (fileChooser.getSelectedFile() == null || userChoice == JFileChooser.CANCEL_OPTION)
         return null;
      else
         return fileChooser.getSelectedFile().getAbsolutePath();
   }

   /**
    * Displays a plain message dialog.
    * @param message the message to be displayed.
    */
   public static void message(String message) {
      showMessageDialog(null, message, "Message", PLAIN_MESSAGE);
   }

   /**
    * Displays a plain message dialog.
    * @param message the message to be displayed.
    * @param icon the icon to be displayed.
    */
   public static void message(String message, ImageIcon icon) {
      showMessageDialog(null, message, "Message", PLAIN_MESSAGE, icon);
   }

   /**
    * Displays an information dialog.
    * @param message the message to be displayed.
    */
   public static void infoMessage(String message) {
      showMessageDialog(null, message, "Info", INFORMATION_MESSAGE);
   }

   /**
    * Displays an error dialog.
    * @param message the message to be displayed.
    */
   public static void errorMessage(String message) {
      showMessageDialog(null, message, "Error", ERROR_MESSAGE);
   }
}
