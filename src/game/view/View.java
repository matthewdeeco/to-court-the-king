package game.view;

import game.controller.Controller;
import game.model.Model;
import game.model.ModelObserver;
import game.utility.Dialog;

import javax.swing.ToolTipManager;
import javax.swing.UIManager;

/**
 * Displays the model data to the players.
 */
public class View implements ModelObserver
{
   private GameWindow gameWindow;
   private Model model;

   public View(Model model, Controller controller) {
      this.model = model;

      ToolTipManager.sharedInstance().setInitialDelay(0);
      tryToInitializeLookAndFeel();

      gameWindow = new GameWindow(model, controller);

      gameWindow.setVisible(true);
      registerAsObserver();
   }

   /** Try to initialize the look and feel. Exit if it failed. */
   private void tryToInitializeLookAndFeel() {
      try {
         initializeLookAndFeel();
      }
      catch (Exception ex) {
         Dialog.errorMessage("Failed in loading the look and feel!\nExiting...");
         System.exit(-1);
      }
   }

   /** 
    * Sets the look and feel to the system's look and feel. 
    * If that fails, then sets the look and feel to Java's.
    */
   private void initializeLookAndFeel() throws Exception {
      try {
         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      }
      catch (Exception ex) {
         UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
      }
   }

   @Override
   public void modelChanged() {
   }

   private void registerAsObserver() {
      model.registerObserver(this);
   }
}
