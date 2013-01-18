package game.model.dice;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class Dice implements Serializable, Iterable<Die>
{
   private ArrayList<Die> dice;

   /** Constructs dice collection with no initial dice. */
   public Dice() {
      this(0);
   }

   /**
    * Constructs dice collection with a given number of initial dice.
    * @param initialCount the initial number of dice.
    */
   public Dice(int initialCount) {
      dice = new ArrayList<Die>();
      for (int i = 0; i < initialCount; i++)
         dice.add(DieFactory.createPermanentDie());
   }

   public void addDie(Die die) {
      dice.add(die);
   }

   public int getNumberOf(int value) {
      int count = 0;
      for (Die die : dice)
         if (die.getValue() == value)
            count++;
      return count;
   }

   public void clearTemporaryDice() {
      try {
         for (Die die : dice)
            if (die.isTemporary())
               dice.remove(die);
      } catch (ConcurrentModificationException e) {
      }
   }

   public void rollActiveDice() {
      for (Die die : dice)
         if (die.isActive())
            die.roll();
   }

   public boolean hasActiveDice() {
      for (Die die : dice)
         if (die.isActive())
            return true;
      return false;
   }

   public void unflagCardAllDice() {
      for (Die die : dice)
         die.setCardFlag(false);
   }

   public boolean hasDieWithValue(int value) {
      for (Die die : dice)
         if (die.getValue() == value)
            return true;
      return false;
   }

   public boolean hasActiveDieWithValueLessThan(int value) {
      return getNumberOfActiveDiceWithValueLessThan(value) > 0;
   }

   public int getNumberOfActiveDiceWithValueLessThan(int value) {
      int count = 0;
      for (Die die : dice) {
         boolean isActive = die.isActive();
         boolean isLessThan = (die.getValue() < value);
         if (isActive && isLessThan)
            count++;
      }
      return count;
   }

   public boolean isAllOdd() {
      for (Die die : dice)
         if (die.isEven())
            return false;
      return true;
   }

   public boolean isAllEven() {
      for (Die die : dice)
         if (die.isOdd())
            return false;
      return true;
   }

   /** @return the list containing the number of dice for each dice value. */
   public ArrayList<Integer> getCountForEachValue() {
      ArrayList<Integer> counts = new ArrayList<Integer>();
      for (int i = Die.MIN_VALUE; i <= Die.MAX_VALUE; i++)
         counts.add(count(i));

      return counts;
   }

   /**
    * Counts the number of dice with the value given.
    * @param value the value of the dice to be counted.
    * @return the number of dice with the value given.
    */
   private int count(int value) {
      int count = 0;
      for (Die die : dice)
         if (die.getValue() == value)
            count++;
      return count;
   }

   public int getSum() {
      int sum = 0;
      for (Die die : dice)
         sum += die.getValue();
      return sum;
   }

   public void addPermanentDie() {
      dice.add(DieFactory.createPermanentDie());
   }

   public void addTemporaryDie(int initialValue) {
      dice.add(DieFactory.createTemporaryDie(initialValue));
   }

   public Die getDie(int dieIndex) {
      return dice.get(dieIndex);
   }

   public int getDiceCount() {
      return dice.size();
   }

   public int getActiveDiceCount() {
      int counter = 0;
      for (Die die : dice)
         if (die.isActive())
            counter++;
      return counter;
   }

   public void set(int dieIndex) {
      Die dieToSet = dice.get(dieIndex);
      dieToSet.set();
   }

   public void activate(int dieIndex) {
      Die dieToActivate = dice.get(dieIndex);
      dieToActivate.activate();
   }

   public void setAll() {
      for (Die die : dice)
         die.set();
   }

   public void activateAll() {
      for (Die die : dice)
         die.activate();
   }

   @Override
   public Iterator<Die> iterator() {
      return dice.iterator();
   }

   @Override
   public String toString() {
      StringBuilder string = new StringBuilder();
      string.append("Dice count : ").
             append(dice.size()).
             append("\r\n");

      int counter = 1;
      for (Die die : dice) {
         string.append(String.format("Dice %-2d : ", counter)).
                append(die);
         counter++;
      }

      return string.toString();
   }
}
