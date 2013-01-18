package game.view;

import java.util.ArrayList;
import game.controller.ConfirmHandler;
import game.model.card.Card;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;

public class CardSelectionDialog extends JDialog implements ActionListener, WindowListener
{
   private Card selectedCard;
   private JPanel selectedCardImagePanel;
   private JPanel centerPanel;
   private JPanel cardListPanel;
   private JPanel cardInfoPanel;
   private JPanel buttonPanel;
   private JLabel selectedCardImage;
   private JTextArea selectedCardAbility;
   private JTextArea selectedCardCost;
   private JComboBox cardList;
   private JButton selectButton;
   private JButton cancelButton;

   /** @param acquirableCardNamesList the names of the cards that can be acquired. */
   @SuppressWarnings({"unchecked"})
   public CardSelectionDialog(ArrayList<String> acquirableCardNamesList) {
      String[] acquirableCardNames = Arrays.copyOf(
             acquirableCardNamesList.toArray(),
             acquirableCardNamesList.size(), String[].class);
      cardList = new JComboBox(acquirableCardNames);

      initializeDialog();
      initializePanels();
      initializeOtherComponents();
      updateSelectedCard();

      setVisible(true);
   }

   private void initializeDialog() {
      setTitle("Choose a card");
      setModal(true);
      setLayout(new BorderLayout(10, 10));
      setSize(new Dimension(285, 380));
      setResizable(false);
      setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
      setLocationRelativeTo(null);
      addWindowListener(this);
   }

   private void initializePanels() {
      selectedCardImagePanel = new JPanel();
      centerPanel = new JPanel();
      buttonPanel = new JPanel();

      cardListPanel = new JPanel();
      cardInfoPanel = new JPanel();

      selectedCardImagePanel.setBorder(BorderFactory.createEmptyBorder(5, 0, -5, 0));
      centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
      buttonPanel.setBorder(BorderFactory.createEmptyBorder(-5, 0, 5, 0));

      centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
      cardInfoPanel.setLayout(new BoxLayout(cardInfoPanel, BoxLayout.Y_AXIS));

      centerPanel.add(cardListPanel);
      centerPanel.add(cardInfoPanel);

      add(selectedCardImagePanel, BorderLayout.PAGE_START);
      add(centerPanel, BorderLayout.CENTER);
      add(buttonPanel, BorderLayout.PAGE_END);
   }

   private void initializeOtherComponents() {
      selectedCardImage = new JLabel();

      cardList.addActionListener(this);
      cardList.setPreferredSize(new Dimension(160, 25));
      cardList.setFont(new Font("Serif", Font.PLAIN, 14));
      cardList.setMaximumRowCount(7);

      selectedCardAbility = createCardInfoTextArea();
      selectedCardCost = createCardInfoTextArea();

      selectButton = new JButton("Select");
      selectButton.addActionListener(this);
      cancelButton = new JButton("Cancel");
      cancelButton.addActionListener(this);

      selectedCardImagePanel.add(selectedCardImage);
      cardListPanel.add(cardList);
      cardInfoPanel.add(selectedCardAbility);
      cardInfoPanel.add(selectedCardCost);
      buttonPanel.add(selectButton);
      buttonPanel.add(cancelButton);
   }

   private JTextArea createCardInfoTextArea() {
      JTextArea infoTextArea = new JTextArea();
      infoTextArea.setEditable(false);
      infoTextArea.setLineWrap(true);
      infoTextArea.setWrapStyleWord(true);
      infoTextArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
      infoTextArea.setOpaque(false);
      return infoTextArea;
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == cardList)
         updateSelectedCard();
      else if (e.getSource() == selectButton)
         confirmSelection();
      else if (e.getSource() == cancelButton)
         cancelDialog();
   }

   private void confirmSelection() {
      if (ConfirmHandler.confirmSelectCard(selectedCard))
         dispose();
   }

   /**
    * Update the selected card in the list.
    */
   private void updateSelectedCard() {
      selectedCard = Card.valueOf(
             cardList.getSelectedItem().toString().toUpperCase());
      selectedCardImage.setIcon(selectedCard.getLargeIcon());
      selectedCardAbility.setText("Ability : "
             + selectedCard.getAbilityDescription());
      selectedCardCost.setText("Cost    : "
             + selectedCard.getCostDescription());
   }

   public Card getSelectedCard() {
      return selectedCard;
   }

   /**
    * Cancels the dialog.
    */
   private void cancelDialog() {
      selectedCard = null;
      dispose();
   }

   @Override
   public void windowClosing(WindowEvent e) {
      cancelDialog();
   }

   @Override
   public void windowOpened(WindowEvent e) {
   }

   @Override
   public void windowClosed(WindowEvent e) {
   }

   @Override
   public void windowIconified(WindowEvent e) {
   }

   @Override
   public void windowDeiconified(WindowEvent e) {
   }

   @Override
   public void windowActivated(WindowEvent e) {
   }

   @Override
   public void windowDeactivated(WindowEvent e) {
   }
}
