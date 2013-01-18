package game.model.card;

import game.model.card.ability.*;
import game.model.card.cost.*;
import game.model.player.Player;
import game.utility.StringHelper;

import javax.swing.ImageIcon;

public enum Card
{
   FOOL(0, 1, new RerollDice(1), new Free()),
   CHARLATAN(0, -1, new AddNoValueDice(1), new MustHaveFool()),
   FARMER(1, 1, new AddNoValueDice(1), new OfAKind(1, 2)),
   MAID(1, 1, new AddOneTwoOrThreeToOneDie(), new AllOddOrEven(AllOddOrEven.Type.ODD)),
   PHILOSOPHER(1, 1, new RedistributeDice(2), new AllOddOrEven(AllOddOrEven.Type.EVEN)),
   LABORER(1, 1, new AddInitialValueDie(1), new MinimumTotal(15)),
   GUARD(1, 1, new AddInitialValueDie(2), new OfAKind(1, 3)),
   HUNTER(2, 1, new AddInitialValueDie(3), new OfAKind(1, 4)),
   ASTRONOMER(2, 1, new CopyAnotherDie(), new OfAKind(2, 2)),
   MERCHANT(2, 1, new RerollDice(RerollDice.ANY_NUMBER), new MinimumTotal(20)),
   NOBLEWOMAN(3, 1, new AddToAnyDice(1), new FullHouse()),
   SHYLOCK(3, 1, new AddInitialValueDie(4), new MinimumTotal(30)),
   KNIGHT(3, 1, new AddInitialValueDie(5), new OfAKind(1, 5)),
   MAGICIAN(3, 1, new ChangeOneDieValue(), new StraightDice(5)),
   ALCHEMIST(4, 1, new RedistributeDice(3), new StraightDice(6)),
   BISHOP(4, 1, new AddInitialValueDie(6), new OfAKind(3, 2)),
   NOBLEMAN(4, 1, new AddToAnyDice(2), new OfAKind(2, 3)),
   GENERAL(4, 1, new AddNoValueDice(2), new OfAKind(1, 6)),
   QUEEN(5, 1, new AddInitialValueDie(AddInitialValueDie.ANY_VALUE), new ComesWithKing()),
   KING(5, 1, new KingAbility(), new OfAKind(1, 7));
   /** Signifies that a player can have an unlimited number of this card. */
   private static final int INFINITE_COUNT = -1;
   private String name;
   private int rank;
   /** Determines the maximum number of this card a player can hold. */
   private int maxCount;
   private CardAbility ability;
   private CardCost cost;
   private ImageIcon largeIcon;
   private ImageIcon smallIcon;

   private Card(int newRank, int newMaxCount, CardAbility newAbility, CardCost newCost) {
      name = StringHelper.convertToTitleCase(name());
      maxCount = newMaxCount;
      rank = newRank;
      ability = newAbility;
      cost = newCost;
      largeIcon = new ImageIcon(getClass().getClassLoader().
             getResource("images/cards/" + getName() + ".png"));
      smallIcon = new ImageIcon(getClass().getClassLoader().
             getResource("images/cards-small/" + getName() + ".png"));
   }

   /**
    * Uses the card's ability.
    * @param player the player who will use the card ability.
    */
   public void useAbility(Player player) {
      ability.use(player);
   }

   /**
    * Uses the card's immediate effect upon acquiring.
    * @param player the player who just acquired the card.
    */
   public void useImmediateEffect(Player player) {
      ability.effectOnAcquire(player);
   }

   /**
    * Checks if the card's ability is usable. The default is true.
    * @param player the player who will be checked.
    * @return true if the card's ability is usable by the player, false otherwise.
    */
   public boolean isUsable(Player player) {
      return ability.isUsable(player);
   }

   /**
    * Returns true if the card has a usable effect, else false.
    * @return true if the card has a usable effect, else false. 
    */
   public boolean hasUsableEffect() {
      return ability.hasUsableEffect();
   }

   /**
    * Checks if the card comes with the King card.
    * @return true if the card comes with the King card.
    */
   public boolean comesWithKing() {
      return cost instanceof ComesWithKing;
   }

   /**
    * Checks if the player can acquire this card.
    * @param player the player that is trying to acquire the card.
    * @return true if the player can acquire this card, false otherwise.
    */
   public boolean isAcquirable(Player player) {
      boolean costIsFulfilled = cost.isFulfilled(player);
      if (maxCount == INFINITE_COUNT)
         return costIsFulfilled;
      else {
         boolean doesNotHaveYet = !player.hasCard(this);
         return (costIsFulfilled && doesNotHaveYet);
      }
   }

   @Override
   public String toString() {
      StringBuilder string = new StringBuilder();
      string.append("Name : ").
             append(getName()).
             append("\r\nRank : ").
             append(getRank()).
             append("\r\nAbility : ").
             append(getAbility()).
             append("\r\nCost : ").
             append(getCost());
      return string.toString();
   }

   public String getName() {
      return name;
   }

   public int getRank() {
      return rank;
   }

   public CardCost getCost() {
      return cost;
   }

   public CardAbility getAbility() {
      return ability;
   }

   public String getTooltipText() {
      StringBuilder string = new StringBuilder();
      string.append("<html>").
             append(name.toUpperCase()).
             append("<hr>").
             append(ability.getDescription()).
             append("<br>Requires: ").
             append(cost.getDescription()).
             append("</html>");
      return string.toString();
   }

   public String getAbilityDescription() {
      return ability.getDescription();
   }

   public String getCostDescription() {
      return cost.getDescription();
   }

   public ImageIcon getLargeIcon() {
      return largeIcon;
   }

   public ImageIcon getSmallIcon() {
      return smallIcon;
   }
}
