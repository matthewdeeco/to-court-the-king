package game.model.dice;

public class DieFactory
{
   private DieFactory() {
   }

   public static Die createPermanentDie() {
      Die resultDie = new Die();
      resultDie.roll();
      resultDie.activate();
      resultDie.makePermanent();
      return resultDie;
   }

   public static Die createTemporaryDie(int initialValue) {
      Die resultDie = new Die();
      resultDie.setValue(initialValue);
      resultDie.activate();
      resultDie.makeTemporary();
      return resultDie;
   }
}
