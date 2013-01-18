package game.view;

import game.controller.Controller;
import game.model.Model;
import game.model.ModelObserver;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.*;

public class GamePanel extends JPanel implements ModelObserver
{
   private PlayerInfoPanel playerInfoPanel;
   private DicePanel dicePanel;
   private ActionPanel actionPanel;
   private Model model;
   private Controller controller;

   public GamePanel(Model model, Controller controller) {
      this.model = model;
      this.controller = controller;

      setLayout(new BorderLayout());
      setPreferredSize(new Dimension(380, 510));

      initializePanels();
      registerAsObserver();
   }

   private void initializePanels() {
      playerInfoPanel = new PlayerInfoPanel(model);
      dicePanel = new DicePanel(model, controller);
      actionPanel = new ActionPanel(model, controller);

      add(playerInfoPanel, BorderLayout.PAGE_START);
      add(dicePanel, BorderLayout.CENTER);
      add(actionPanel, BorderLayout.PAGE_END);
   }

   @Override
   public void modelChanged() {
   }

   private void registerAsObserver() {
      model.registerObserver(this);
   }
}
