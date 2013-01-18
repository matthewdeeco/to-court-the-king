package game.view;

import game.controller.Controller;
import game.model.Model;
import game.model.ModelObserver;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.*;

/**
 * The player info panel.
 * @author Matthew Co
 */
public class PlayerInfoPanel extends JPanel implements ModelObserver
{
   private JLabel currentPlayerLabel;
   private JLabel nextPlayerLabel;
   private Model model;

   public PlayerInfoPanel(Model model) {
      this.model = model;

      initalizePanel();
      initalizePlayerLabels();

      registerAsObserver();
   }

   private void initalizePanel() {
      setLayout(new BorderLayout());
      setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
   }

   private void initalizePlayerLabels() {
      currentPlayerLabel = new JLabel();
      currentPlayerLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
      currentPlayerLabel.setIconTextGap(7);
      currentPlayerLabel.setPreferredSize(new Dimension(250, 70));

      nextPlayerLabel = new JLabel();
      nextPlayerLabel.setHorizontalTextPosition(JLabel.LEFT);
      nextPlayerLabel.setText("next: ");

      add(currentPlayerLabel, BorderLayout.LINE_START);
      add(nextPlayerLabel, BorderLayout.LINE_END);
   }

   private void updateCurrentPlayer() {
      ImageIcon avatar = model.getCurrentPlayerAvatar();
      String name = model.getCurrentPlayerName();

      currentPlayerLabel.setIcon(avatar);
      currentPlayerLabel.setText(name);
   }

   private void updateNextPlayer() {
      if (model.hasNextPlayer()) {
         ImageIcon avatar = model.getNextPlayerAvatar();
         nextPlayerLabel.setIcon(avatar);
      }
      else
         nextPlayerLabel.setIcon(new ImageIcon(getClass().
                getClassLoader().getResource("images/game-over.png")));
   }

   @Override
   public void modelChanged() {
      updateCurrentPlayer();
      updateNextPlayer();
   }

   private void registerAsObserver() {
      model.registerObserver(this);
   }
}
