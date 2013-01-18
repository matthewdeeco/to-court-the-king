package game.view;

import game.model.card.Card;
import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyEvent;

public class HelpManual extends JFrame
{
   private static HelpManual instance;

   /**
    * Initializes the help manual.
    * Runs on a separate thread to prevent program lagging.
    */
   private static void initializeWindow() {
      new Thread()
      {
         @Override
         public void run() {
            instance = new HelpManual();
            instance.setVisible(true);
         }
      }.start();
   }

   private HelpManual() {
      ToolTipManager.sharedInstance().setInitialDelay(0);

      final int WINDOW_WIDTH = 350;
      final int WINDOW_HEIGHT = 500;

      this.setLocationRelativeTo(null);

      final Point CENTER = this.getLocation();
      final int WINDOW_X = CENTER.x + 200;
      final int WINDOW_Y = CENTER.y - 250;

      final FlowLayout LAYOUT = new FlowLayout();

      this.setTitle("Help Manual");
      this.setLayout(LAYOUT);
      this.setIconImage(new ImageIcon(this.getClass().getClassLoader().
             getResource("images/icons/help-icon.png")).getImage());
      this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
      this.setLocation(WINDOW_X, WINDOW_Y);
      this.setResizable(false);
      this.setContentPane(createTabbedPane());
      this.setDefaultCloseOperation(HIDE_ON_CLOSE);
      this.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
   }

   private JTabbedPane createTabbedPane() {
      JTabbedPane tabbedPane = new JTabbedPane();
      tabbedPane.addTab("Objective", this.createTextHelpPanel(this.getObjectiveHelpText()));
      tabbedPane.addTab("Mechanics", this.createTextHelpPanel(this.getMechanicsHelpText()));
      tabbedPane.addTab("Cards", this.createCharacterCardHelpPanel());
      tabbedPane.addTab("Credits", this.createTextHelpPanel(this.getCreditsHelpText()));

      tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
      tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
      tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
      tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);

      tabbedPane.setSelectedIndex(0);
      return tabbedPane;
   }

   private JPanel createTextHelpPanel(String helpText) {
      JPanel insidePanel = new JPanel();

      JTextPane helpTextPane = new JTextPane();
      helpTextPane.setContentType("text/html");
      helpTextPane.setEditable(false);
      helpTextPane.setFont(new Font(Font.DIALOG, Font.PLAIN, 12));
      helpTextPane.setOpaque(false);
      helpTextPane.setText(helpText);
      helpTextPane.setCaretPosition(0);

      JScrollPane scrollPane = new JScrollPane(helpTextPane);
      scrollPane.setPreferredSize(new Dimension(335, 437));
      scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

      JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
      verticalScrollBar.setUnitIncrement(17);

      insidePanel.add(scrollPane);

      return insidePanel;
   }

   private String getObjectiveHelpText() {
      return "<p align=\"justify\">"
             + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
             + "Players play as petitioners to the royal Court. In order to "
             + "carry out their interests, they must use their powers of persuasion "
             + "to obtain the support of ever more influential members of the Court. "
             + "From the Maid to the royal couple themselves, all have their "
             + "ways of helping you, provided you can convince them to do so.<br><br>"
             + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
             + "Your powers of persuasion are represented by dice in this game. "
             + "Should the dice results match certain requirements, various "
             + "characters can be claimed – characters which may add extra dice "
             + "or other special abilities.<br><br>"
             + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
             + "The goal is to obtain cards for manipulating dice. "
             + "The first player to have dice of <b>seven of a kind</b> can claim the "
             + "royal couple’s support (the King and Queen cards). The player who has the King "
             + "card at the end of the final round wins the game!";
   }

   private String getMechanicsHelpText() {
      return "<u>Active and Set Dice</u>"
             + "<p align=\"justify\">"
             + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
             + "At the start of a player’s turn, all of his dice are active. "
             + "He rolls all of his dice. <br>"
             + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
             + "The player must now set aside at least one of these active dice. "
             + "The player sets aside the dice with desired values, because he "
             + "cannot change the values of those again. If there are any dice "
             + "left after setting aside those he wishes, he rolls those left "
             + "again (the active dice). These rolled dice are now the active "
             + "dice and he must, again, set aside at least one of them. The "
             + "player continues in this way until he has set aside all dice "
             + "he is allowed to roll. <b>Set dice cannot be modified by any card.</b><br><br>"
             + "<u>Using the Cards</u>"
             + "<p align=\"justify\">"
             + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
             + "After his initial roll, which must include all extra dice, "
             + "a player may use his other cards at any time during his turn to "
             + "modify active dice or to bring new dice into play, either before, "
             + "after, or in between setting aside one or more active dice. "
             + "A player may only use each of his cards once per turn!<br>"
             + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
             + "For all card abilities, players should remember one "
             + "<b>General Rule: only active dice can be modified by the ability "
             + "of any card. Once a die has been set aside, it may not be "
             + "modified or re-rolled.</b><br><br>"
             + "<u>End of Player Turn, Acquiring Cards</u>"
             + "<p align=\"justify\">"
             + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
             + "With his dice result, the player may acquire a card from the display. "
             + "Per turn, the player may acquire only <b>one card, that is still available, "
             + "and that he does not already have</b> (the only exception to this "
             + "is the Fool / Charlatan). The dice result "
             + "must match the cost for the card desired.<br>"
             + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
             + "When all the copies of a certain card have been acquired, "
             + "no player may acquire such a card for the rest of the game.<br><br>"
             + "<i>Example</i>: Anna’s dice result is 5-5-5. With this dice result, "
             + "she has several possibilities. She can acquire either a Fool (cost: free), "
             + "a Farmer (cost: 2-of-a-kind), a Laborer (cost: dice totals of 15 or more), "
             + "or a Guard (cost: 3-of-a-kind). She chooses the Guard, acquires it, and her turn ends.<br><br>"
             + "<u>Final Round</u>"
             + "<p align=\"justify\">"
             + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
             + "When a player achieves a dice result of seven of a kind, he may, "
             + "but need not, acquire the King card and, thereby, initiate the "
             + "end of the game. If he chooses another card that his dice result "
             + "allows, the game continues normally (endgame does not begin). "
             + "If the player chooses to get the King, he must also take the Queen."
             + "When a player acquires the King, the current round continues "
             + "until its normal end. Of course, no other player, regardless "
             + "of roll, can acquire the King and Queen (as they are gone from "
             + "the display). Then, the final round begins.<br>"
             + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
             + "In the final round, each player tries, on his turn, to roll as "
             + "high a dice result as he can, always trying to roll better than "
             + "the player holding the King card.<br><br>"
             + "<i>Note 1</i>: The more dice of a kind the better. (e.g. eight 1’s "
             + "is better than seven 6’s) With the same number of dice of a kind, "
             + "the higher the number the better. (e.g. seven 6’s is better than "
             + "seven 5’s).<br><br>"
             + "<i>Note 2</i>: When two players have rolled the same number of a "
             + "kind with the same number on the dice, the player who rolled "
             + "the dice result first takes precedence.<br><br>"
             + "<i>Note 3</i>: The player with the current highest dice result "
             + "takes the King card (to show he has the highest), but not the "
             + "Queen card.";
   }

   private String getCreditsHelpText() {
      return "<p align=\"justify\">"
             + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
             + "All audio in this game and the rights to it are owned by Konami."
             + "<p align=\"justify\">"
             + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
             + "The images used for avatars and cards are owned by Intelligent "
             + "Systems and Nintendo. The graphic used for version information "
             + "is owned by Konami."
             + "<p align=\"justify\">"
             + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
             + "I <i>do not</i> claim to own any of the images or audio used "
             + "in this game."
             + "<p align=\"justify\">"
             + "<u>Special Thanks to</u>: <br>"
             + "Audacity, WavePad Sound Editor, serenesforest.net, "
             + "feplanet.net, suikosource.com, iconarchive.com, gamemp3s.net";
   }

   private JPanel createCharacterCardHelpPanel() {
      JPanel cardHelpPanel = new JPanel();
      cardHelpPanel.setBorder(BorderFactory.createEmptyBorder(5, 40, 5, 20));
      cardHelpPanel.setLayout(new GridLayout(0, 2));
      for (Card card : Card.values())
         cardHelpPanel.add(this.createCardHelpLabelWithTooltip(card));
      return cardHelpPanel;
   }

   private JLabel createCardHelpLabelWithTooltip(Card card) {
      JLabel cardHelpLabel = new JLabel();
      cardHelpLabel.setIcon(card.getSmallIcon());
      cardHelpLabel.setText(card.getName());
      cardHelpLabel.setToolTipText(card.getTooltipText());
      return cardHelpLabel;
   }

   public static void display() {
      if (instance == null)
         initializeWindow();
      else
         instance.setVisible(true);
   }

   public static void close() {
      instance.setVisible(false);
   }
}
