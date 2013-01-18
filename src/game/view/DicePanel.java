package game.view;

import game.controller.Controller;
import game.model.Model;
import game.model.dice.Die;
import java.util.ArrayList;
import game.model.ModelObserver;
import game.model.player.Player;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class DicePanel extends JPanel implements ActionListener, ModelObserver
{
   private JPanel activeDicePanel;
   private JPanel setDicePanel;
   private ArrayList<JButton> diceButtons;
   private Model model;
   private Controller controller;

   public DicePanel(Model model, Controller controller) {
      this.model = model;
      this.controller = controller;

      initializeDicePanels();

      registerAsObserver();
   }

   private void initializeDicePanels() {
      activeDicePanel = createDicePanel("Active Dice");
      setDicePanel = createDicePanel("Set Dice");
   }

   private void updateDicePanels() {
      clearAllDice();

      Player player = model.getCurrentPlayer();
      for (Die die : player.getDice()) {
         JButton dieButton = createDieButton(die);
         if (die.isActive()) {
            activeDicePanel.add(dieButton);
            dieButton.addActionListener(this);
         }
         else
            setDicePanel.add(dieButton);
         diceButtons.add(dieButton);
      }
   }

   private JButton createDieButton(Die die) {
      JButton resultButton = new JButton(die.getImageIcon());
      resultButton.setFocusable(false);
      resultButton.setContentAreaFilled(false);
      resultButton.setBorderPainted(false);
      return resultButton;
   }

   /**
    * Removes the contents of the dice panels.
    */
   private void clearAllDice() {
      diceButtons = new ArrayList<JButton>();

      activeDicePanel.removeAll();
      activeDicePanel.revalidate();
      activeDicePanel.repaint();

      setDicePanel.removeAll();
      setDicePanel.revalidate();
      setDicePanel.repaint();
   }

   private JPanel createDicePanel(String title) {
      JPanel newPanel = new JPanel();
      newPanel.setLayout(new GridLayout(0, 2));
      JScrollPane scrollPane = new JScrollPane(newPanel);
      scrollPane.setPreferredSize(new Dimension(165, 295));
      scrollPane.setBorder(BorderFactory.createEmptyBorder());
      scrollPane.setHorizontalScrollBarPolicy(
             JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
      JPanel borderPanel = createBorderPanel(title);
      borderPanel.add(scrollPane);
      add(borderPanel);
      return newPanel;
   }

   private JPanel createBorderPanel(String title) {
      JPanel newPanel = new JPanel();
      newPanel.setPreferredSize(new Dimension(175, 335));
      newPanel.setBorder(BorderFactory.createTitledBorder(title));
      return newPanel;
   }

   @Override
   public void modelChanged() {
      updateDicePanels();
   }

   private void registerAsObserver() {
      model.registerObserver(this);
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      for (JButton dieButton : diceButtons)
         if (e.getSource() == dieButton)
            controller.setDie(diceButtons.indexOf(dieButton));
   }
}
