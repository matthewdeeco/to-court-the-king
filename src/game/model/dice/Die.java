package game.model.dice;

import java.io.Serializable;
import javax.swing.ImageIcon;

/**
 * Responsible for single die rolling, setting and checking die value.
 */
public class Die implements Serializable
{
   /** The die's minimum value. */
   public static final int MIN_VALUE = 1;
   /** The die's maximum value. */
   public static final int MAX_VALUE = 6;
   /** Is true if the die cannot be used by cards. */
   private transient boolean cardFlagged;
   /** The die's value. Ranges from 1-6. */
   private int value;
   /** Is true when the die is active, false if it is set. */
   private boolean active;
   /** Is true if the die does not disappear after a turn, false if it does. */
   private boolean permanent;

   public final void roll() {
      setValue(MIN_VALUE + (int) (Math.floor(MAX_VALUE * Math.random())));
   }

   public int getValue() {
      return value;
   }

   public boolean isActive() {
      return active;
   }

   public boolean isPermanent() {
      return permanent;
   }

   public boolean isTemporary() {
      return !permanent;
   }

   public boolean isCardFlagged() {
      return cardFlagged;
   }

   public boolean isEven() {
      return value % 2 == 0;
   }

   public boolean isOdd() {
      return !isEven();
   }

   public void set() {
      active = false;
   }

   public void activate() {
      active = true;
   }

   public void setCardFlag(boolean cardFlagged) {
      this.cardFlagged = cardFlagged;
   }

   public void setValue(int value) {
      if (value < Die.MIN_VALUE || value > Die.MAX_VALUE)
         throw new IllegalArgumentException();
      this.value = value;
   }

   public void makePermanent() {
      permanent = true;
   }

   public void makeTemporary() {
      permanent = false;
   }

   public ImageIcon getImageIcon() {
      return DieImageHandler.getImageIcon(this);
   }

   @Override
   public String toString() {
      return new StringBuilder().append(getValue()).
             append(" - ").
             append(isActive() ? "active" : "set   ").
             append(" | ").
             append(isPermanent() ? "permanent" : "temporary").
             append("\r\n").
             toString();
   }
}
