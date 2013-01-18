package game.view;

import game.controller.ConfirmHandler;
import game.controller.Controller;
import game.model.Model;
import game.model.ModelObserver;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ActionPanel extends JPanel implements ActionListener, ModelObserver
{
   private JButton rerollActiveDiceButton;
   private JButton useCardButton;
   private JButton acquireCardButton;
   private Model model;
   private Controller controller;

   public ActionPanel(Model model, Controller controller) {
      this.model = model;
      this.controller = controller;

      initializePanel();
      addControlButtons();
      registerAsObserver();
   }

   public void enableRerollDiceButton() {
      rerollActiveDiceButton.setEnabled(true);
   }

   public void disableRerollDiceButton() {
      rerollActiveDiceButton.setEnabled(false);
   }

   public void enableUseCardButton() {
      useCardButton.setEnabled(true);
   }

   public void disableUseCardButton() {
      useCardButton.setEnabled(false);
   }

   private void initializePanel() {
      setLayout(new GridLayout(0, 3));
      setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
   }

   private void addControlButtons() {
      rerollActiveDiceButton = createControlButton("Reroll Active");
      add(rerollActiveDiceButton);

      useCardButton = createControlButton("Use Card");
      add(useCardButton);

      acquireCardButton = createControlButton("Acquire Card");
      add(acquireCardButton);
   }

   private JButton createControlButton(String title) {
      final Dimension BUTTON_DIMENSION = new Dimension(0, 35);
      JButton controlButton = new JButton(title);
      controlButton.setPreferredSize(BUTTON_DIMENSION);
      controlButton.setFocusable(false);
      controlButton.addActionListener(this);
      return controlButton;
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == rerollActiveDiceButton)
         controller.rerollActiveDice();
      else if (e.getSource() == useCardButton)
         controller.useCard();
      else if (e.getSource() == acquireCardButton)
         if (model.isFinalRoundThisRound()) {
            if (ConfirmHandler.confirmEndTurn())
               controller.endTurn();
         }
         else
            controller.acquireCard();
   }

   private void registerAsObserver() {
      model.registerObserver(this);
   }

   @Override
   public void modelChanged() {
      if (model.isFinalRoundThisRound())
         acquireCardButton.setText("End Turn");
      else
         acquireCardButton.setText("Acquire Card");

      if (model.canRerollActiveDice())
         rerollActiveDiceButton.setEnabled(true);
      else
         rerollActiveDiceButton.setEnabled(false);

      if (model.hasUsableCards())
         useCardButton.setEnabled(true);
      else
         useCardButton.setEnabled(false);
   }
}
