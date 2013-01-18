package game.view;

import game.model.Model;
import game.model.ModelObserver;
import game.model.card.Card;
import game.model.card.CardCounter;
import java.awt.*;
import javax.swing.*;

/**
 * Contains the card counter information.
 */
public class CardCounterPanel extends JPanel implements ModelObserver
{
   private Model model;

   public CardCounterPanel(Model model) {
      this.model = model;

      initializePanel();
      addCardPanels();
      registerAsObserver();
   }

   private void initializePanel() {
      setBorder(BorderFactory.createTitledBorder("Card Counter"));
      setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
      setPreferredSize(new Dimension(210, 0));
   }

   private void addCardPanels() {
      for (Card card : Card.values())
         add(createCardCounterPanel(card));
   }

   private JPanel createCardCounterPanel(Card card) {
      JPanel cardCounterPanel = new JPanel();
      cardCounterPanel.setLayout(new BorderLayout());
      cardCounterPanel.add(createCardHelpLabelWithTooltip(card), BorderLayout.LINE_START);
      cardCounterPanel.add(createCardCounterLabel(card), BorderLayout.CENTER);
      cardCounterPanel.setPreferredSize(new Dimension(180, 22));
      return cardCounterPanel;
   }

   private JLabel createCardHelpLabelWithTooltip(Card card) {
      JLabel cardHelpLabel = new JLabel();
      cardHelpLabel.setIcon(card.getSmallIcon());
      cardHelpLabel.setText(card.getName());
      cardHelpLabel.setToolTipText(card.getTooltipText());
      cardHelpLabel.setPreferredSize(new Dimension(150, 20));
      return cardHelpLabel;
   }

   private JLabel createCardCounterLabel(Card card) {
      JLabel cardCounterLabel = new JLabel();

      if (model == null)
         cardCounterLabel.setText("---");
      else if (model.isGameStarted()) {
         int remaining = model.getRemainingCount(card);
         String remainingText;
         if (remaining == CardCounter.INFINITE)
            remainingText = "";
         else if (remaining == 0)
            remainingText = "---";
         else
            remainingText = Integer.toString(remaining);

         cardCounterLabel.setText(remainingText);
      }
      else
         cardCounterLabel.setText("---");

      return cardCounterLabel;
   }

   @Override
   public void modelChanged() {
      updateCardCounterLabels();
   }

   private void updateCardCounterLabels() {
      removeAll();
      revalidate();
      repaint();
      addCardPanels();
   }

   private void registerAsObserver() {
      model.registerObserver(this);
   }
}
