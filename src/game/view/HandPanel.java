package game.view;

import game.model.Model;
import game.model.ModelObserver;
import game.model.card.Card;
import java.awt.*;
import javax.swing.*;

public class HandPanel extends JPanel implements ModelObserver
{
   private Model model;

   public HandPanel(Model model) {
      this.model = model;

      initializePanel();
      addCardPanels();
      registerAsObserver();
   }

   private void initializePanel() {
      setBorder(BorderFactory.createEmptyBorder());
      setLayout(new FlowLayout(FlowLayout.CENTER, 5, 3));
      setPreferredSize(new Dimension(210, 0));
   }

   private void addCardPanels() {
      for (Card card : Card.values())
         if (shouldCreateHandPanel(card))
            add(createSingleCardPanel(card));
   }

   private boolean shouldCreateHandPanel(Card card) {
      boolean gameHasStarted = model != null && model.isGameStarted();

      if (gameHasStarted) {
         boolean currentPlayerHasCard = model.getCurrentPlayer().hasCard(card);
         boolean cardHasUsableEffect = card.hasUsableEffect();
         return currentPlayerHasCard && cardHasUsableEffect;
      }
      else
         return false;
   }

   private JPanel createSingleCardPanel(Card card) {
      JPanel singleCardPanel = new JPanel();
      singleCardPanel.setLayout(new BorderLayout());
      singleCardPanel.add(createCardHelpLabelWithTooltip(card), BorderLayout.LINE_START);
      singleCardPanel.add(createHandLabel(card), BorderLayout.LINE_END);
      singleCardPanel.setPreferredSize(new Dimension(180, 22));
      return singleCardPanel;
   }

   private JLabel createCardHelpLabelWithTooltip(Card card) {
      JLabel cardHelpLabel = new JLabel();
      cardHelpLabel.setIcon(card.getSmallIcon());
      cardHelpLabel.setText(card.getName());
      cardHelpLabel.setToolTipText(card.getTooltipText());
      cardHelpLabel.setPreferredSize(new Dimension(150, 20));
      return cardHelpLabel;
   }

   private JLabel createHandLabel(Card card) {
      JLabel handLabel = new JLabel();
      if (!model.isGameStarted())
         handLabel.setText("---");
      else if (model.getCurrentPlayer().isCardUnused(card))
         handLabel.setText("unused");
      else
         handLabel.setText("used");

      return handLabel;
   }

   @Override
   public void modelChanged() {
      updateHandLabels();
   }

   private void updateHandLabels() {
      if (model.isGameStarted())
         setBorder(BorderFactory.createTitledBorder(model.getCurrentPlayerName() + "'s Hand"));
      else
         setBorder(BorderFactory.createEmptyBorder());
      removeAll();
      revalidate();
      repaint();
      addCardPanels();
   }

   private void registerAsObserver() {
      model.registerObserver(this);
   }
}
