package game.model.dice;

import javax.swing.ImageIcon;

public class DieImageHandler extends ImageIcon
{
   private static final ImageIcon[] DIE_IMAGE_ICONS;

   static {
      int dieRange = Die.MAX_VALUE - Die.MIN_VALUE + 1;
      DIE_IMAGE_ICONS = new ImageIcon[dieRange];

      for (int i = 0; i < dieRange; i++)
         DIE_IMAGE_ICONS[i] = new ImageIcon(DieImageHandler.class.getClassLoader().
                getResource("images/dice/" + (i + Die.MIN_VALUE) + ".png"));
   }

   private DieImageHandler() {
   }

   public static ImageIcon getImageIcon(Die die) {
      int index = die.getValue() - Die.MIN_VALUE;
      return DIE_IMAGE_ICONS[index];
   }
}
