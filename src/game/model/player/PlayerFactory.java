package game.model.player;

import game.view.AvatarChoiceDialog;
import game.utility.Dialog;

public class PlayerFactory
{
   private PlayerFactory() {
   }

   public static Player createPlayer() {
      String name = Dialog.inputString("Enter player name: ");
      if (name == null)
         throw new NullPointerException();

      AvatarChoiceDialog avatarChoiceDialog = new AvatarChoiceDialog();
      Integer avatar = avatarChoiceDialog.getSelectedAvatar();
      
      if (avatar == null)
         throw new NullPointerException();

      Player player = new Player(name, avatar);

      return player;
   }

   public static Player createRandomPlayer() {
      int testAvatar = (1 + (int) (36 * Math.random()));
      String name;

      if (testAvatar == 1)
         name = "Rolf";
      else if (testAvatar == 2)
         name = "Gatrie";
      else if (testAvatar == 3)
         name = "Geoffrey";
      else if (testAvatar == 4)
         name = "Tauroneo";
      else if (testAvatar == 5)
         name = "Makalov";
      else if (testAvatar == 6)
         name = "Stefan";
      else if (testAvatar == 7)
         name = "Reyson";
      else if (testAvatar == 8)
         name = "Haar";
      else if (testAvatar == 9)
         name = "Tormod";
      else if (testAvatar == 10)
         name = "Oscar";
      else if (testAvatar == 11)
         name = "Kurth";
      else if (testAvatar == 12)
         name = "Aran";
      else if (testAvatar == 13)
         name = "Shinon";
      else if (testAvatar == 14)
         name = "Nolan";
      else if (testAvatar == 15)
         name = "Renning";
      else if (testAvatar == 16)
         name = "Kieran";
      else if (testAvatar == 17)
         name = "Leonardo";
      else if (testAvatar == 18)
         name = "Gareth";
      else if (testAvatar == 19)
         name = "Mist";
      else if (testAvatar == 20)
         name = "Jill";
      else if (testAvatar == 21)
         name = "Ena";
      else if (testAvatar == 22)
         name = "Calill";
      else if (testAvatar == 23)
         name = "Nephenee";
      else if (testAvatar == 24)
         name = "Astrid";
      else if (testAvatar == 25)
         name = "Leanne";
      else if (testAvatar == 26)
         name = "Tanith";
      else if (testAvatar == 27)
         name = "Fiona";
      else if (testAvatar == 28)
         name = "Elena";
      else if (testAvatar == 29)
         name = "Vika";
      else if (testAvatar == 30)
         name = "Meg";
      else if (testAvatar == 31)
         name = "Sanaki";
      else if (testAvatar == 32)
         name = "Marcia";
      else if (testAvatar == 33)
         name = "Laura";
      else if (testAvatar == 34)
         name = "Heather";
      else if (testAvatar == 35)
         name = "Mia";
      else if (testAvatar == 36)
         name = "Sigrun";
      else
         name = "Anonymous";

      Player player = new Player(name, testAvatar);

      return player;
   }
}
