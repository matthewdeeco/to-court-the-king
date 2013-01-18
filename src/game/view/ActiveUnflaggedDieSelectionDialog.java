package game.view;

import game.model.dice.Dice;
import game.model.dice.Die;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class ActiveUnflaggedDieSelectionDialog extends JDialog implements ActionListener
{
   /** The dice from which the user will pick dice. */
   private Dice dice;
   private Die selectedDie;
   private JPanel dicePanel;
   private ArrayList<JButton> diceButtons;

   /** Creates the dialog with the default title. */
   public ActiveUnflaggedDieSelectionDialog(Dice dice) {
      this(dice, "Choose a die");
   }

   public ActiveUnflaggedDieSelectionDialog(Dice dice, String title) {
      this.dice = dice;
      setTitle(title);

      initializeDialog();
      initializePanel();
      addDiceButtons();

      setVisible(true);
   }

   private void initializeDialog() {
      setModal(true);
      setSize(260, 260);
      setResizable(false);
      setLocationRelativeTo(null);
   }

   private void initializePanel() {
      dicePanel = new JPanel();

      dicePanel.setBorder(
             BorderFactory.createEmptyBorder(10, 10, 10, 10));
      dicePanel.setLayout(new GridLayout(0, 4, 5, 10));

      JScrollPane scrollPane = new JScrollPane(dicePanel);
      scrollPane.setPreferredSize(new Dimension(240, 240));
      scrollPane.setBorder(BorderFactory.createEmptyBorder());
      scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

      add(scrollPane);
   }

   private void addDiceButtons() {
      diceButtons = new ArrayList<JButton>();
      for (Die die : dice) {
         JButton dieButton = createDieButton(die);
         diceButtons.add(dieButton);
         if (die.isActive() && !die.isCardFlagged())
            dicePanel.add(dieButton);
      }
   }

   public Die getSelectedDie() {
      return selectedDie;
   }

   private JButton createDieButton(Die die) {
      JButton resultButton = new JButton(die.getImageIcon());

      resultButton.setPreferredSize(new Dimension(40, 40));
      resultButton.setFocusable(false);
      resultButton.setContentAreaFilled(false);
      resultButton.setBorderPainted(false);
      resultButton.addActionListener(this);

      return resultButton;
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      for (JButton dieButton : diceButtons)
         if (e.getSource() == dieButton) {
            int selectedDieIndex = diceButtons.indexOf(dieButton);
            selectedDie = dice.getDie(selectedDieIndex);
            dispose();
            break;
         }
   }
}
