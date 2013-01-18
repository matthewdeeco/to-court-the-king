package game;

import game.controller.Controller;
import game.model.Model;
import game.view.View;

public class PlayEngine
{
   private PlayEngine() {
   }

   /** Instantiates the game's model and view. */
   public static void main(String[] args) {
      Model model = new Model();
      Controller controller = new Controller(model);
      View view = new View(model, controller);
   }
}
