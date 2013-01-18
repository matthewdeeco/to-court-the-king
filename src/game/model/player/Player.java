package game.model.player;

import game.model.card.Card;
import game.model.card.Hand;
import game.model.dice.Dice;
import game.model.dice.Die;
import java.io.Serializable;
import javax.swing.ImageIcon;

public class Player implements Serializable
{
   /** The player's initial dice count. */
   private static final int INITIAL_DICE_COUNT = 3;
   /** The player's name. */
   private String name;
   /** The player's avatar number. */
   private int avatar;
   /** The player's hand made of cards. */
   private Hand hand;
   /** The player's dice collection. */
   private Dice dice;
   /** The player's avatar icon. */
   private ImageIcon avatarIcon;

   Player(String name, int avatar) {
      this.name = name;
      this.avatar = avatar;

      hand = new Hand();
      dice = new Dice(INITIAL_DICE_COUNT);

      avatarIcon = new ImageIcon(Player.class.getClassLoader().
             getResource("images/avatars/" + avatar + ".png"));
   }

   public void acquireCard(Card card) {
      hand.add(card);
      card.useImmediateEffect(this);
   }

   public void removeCard(Card card) {
      hand.remove(card);
   }

   public boolean hasCard(Card card) {
      return hand.hasCard(card);
   }

   public void addPermanentDie() {
      dice.addPermanentDie();
   }

   public void addTemporaryDie(int initialValue) {
      dice.addTemporaryDie(initialValue);
   }

   public void activateDie(int dieIndex) {
      dice.activate(dieIndex);
   }

   public void setDie(int dieIndex) {
      dice.set(dieIndex);
   }

   public Die getDie(int dieIndex) {
      return dice.getDie(dieIndex);
   }

   public void clearTemporaryDice() {
      dice.clearTemporaryDice();
   }

   public void rollActiveDice() {
      dice.rollActiveDice();
   }

   public void activateAllDice() {
      dice.activateAll();
   }

   public void setAllDice() {
      dice.setAll();
   }

   public boolean hasActiveDice() {
      return dice.hasActiveDice();
   }

   public boolean hasActiveDieWithValueLessThan(int value) {
      return dice.hasActiveDieWithValueLessThan(value);
   }

   /**
    * Gives the number of active dice the player has with a value that is less
    *    than the value given by the user.
    * @param value the value to be checked.
    * @return the number of active dice the player has with a value that is less
    *    than the value given by the user.
    */
   public int getNumberOfActiveDiceWithValueLessThan(int value) {
      return dice.getNumberOfActiveDiceWithValueLessThan(value);
   }

   /**
    * Checks if the player can acquire this card.
    * @param card the card to be checked.
    * @return true if the player can acquire the card, else false.
    */
   public boolean canAcquire(Card card) {
      return card.isAcquirable(this);
   }

   /**
    * Checks if the player can use this card.
    * @param card the card to be checked.
    * @return true if the player can use the card, else false.
    */
   public boolean canUse(Card card) {
      boolean cardIsUnused = isCardUnused(card);
      boolean playerCanUse = (card.isUsable(this));
      return cardIsUnused && playerCanUse;
   }

   /**
    * Checks if the player has the card unused.
    * @param card the card to be checked.
    * @return true if the player has not used the card, else false.
    */
   public boolean isCardUnused(Card card) {
      return hand.canUse(card);
   }

   public void useCard(Card card) {
      try {
         card.useAbility(this);
         setCardAsUsed(card);
      }
      catch (NullPointerException ex) {
      }
      finally {
         unflagCardAllDice();
      }
   }

   public void setCardAsUsed(Card card) {
      hand.setCardAsUsed(card);
   }

   public boolean hasUsableCards() {
      for (Card card : Card.values())
         if (canUse(card))
            return true;
      return false;
   }

   /**
    * Sets all of the player's cards to unused.
    */
   public void resetAllCards() {
      hand.resetAllCards();
   }

   public void unflagCardAllDice() {
      dice.unflagCardAllDice();
   }

   public String getName() {
      return name;
   }

   public int getAvatar() {
      return avatar;
   }

   public int getCardCount() {
      return hand.getCardCount();
   }

   public int getCountOfCardsWithUsableEffects() {
      return hand.getCountOfCardsWithUsableEffects();
   }

   public Dice getDice() {
      return dice;
   }

   public int getActiveDiceCount() {
      return dice.getActiveDiceCount();
   }

   public void setName(String name) {
      this.name = name;
   }

   public void setAvatar(int avatar) {
      this.avatar = avatar;
   }

   public ImageIcon getAvatarIcon() {
      return avatarIcon;
   }

   @Override
   public String toString() {
      StringBuilder string = new StringBuilder();
      string.append(String.format("%-6s : %s", "Name", getName())).
             append("\r\n").
             append(String.format("%-6s : %d", "Avatar", getAvatar())).
             append("\r\n").
             append("------------------------------").
             append("------------------------------").
             append("--------------------\r\n").
             append("Dice:\r\n").
             append(dice).
             append("------------------------------").
             append("------------------------------").
             append("--------------------\r\n").
             append("Hand:\r\n").
             append(hand);
      return string.toString();
   }
}
