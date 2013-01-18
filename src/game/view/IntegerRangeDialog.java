package game.view;

import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Dialog prompting the user to enter an integer in a given range.
 */
public class IntegerRangeDialog extends JDialog implements ActionListener, WindowListener
{
   private String message;
   private int lowerBound;
   private int upperBound;
   private Integer selectedInteger;
   private ArrayList<JButton> integerButtons;
   private JPanel integersPanel;

   /**
    * Constructor for IntegerRangeDialog.
    * @param message the message to be displayed.
    * @param lowerBound the lower bound
    * @param upperBound the upper bound
    */
   public IntegerRangeDialog(String message, int lowerBound, int upperBound) {
      this.message = message;
      this.lowerBound = lowerBound;
      this.upperBound = upperBound;

      if (lowerBound > upperBound)
         throw new IllegalArgumentException("Lower bound was bigger than upper bound!");

      initializeDialog();
      initializePanels();
      pack();
      setLocationRelativeTo(null);

      setVisible(true);
   }

   public Integer getSelectedInteger() {
      return selectedInteger;
   }

   private void initializeDialog() {
      setTitle("Input integer");
      setModal(true);
      setLayout(new BorderLayout());
      setResizable(false);
      setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
      addWindowListener(this);
   }

   private void initializePanels() {
      integersPanel = new JPanel();
      final int COLUMN_COUNT = Math.min(6, (upperBound - lowerBound + 1));
      integersPanel.setLayout(new GridLayout(0, COLUMN_COUNT));
      integersPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
      integerButtons = new ArrayList<JButton>();

      for (int i = lowerBound; i <= upperBound; i++) {
         JButton integerButton = createIntegerButton(i);
         integerButtons.add(integerButton);
         integersPanel.add(integerButton);
      }

      add(createMessageLabel(), BorderLayout.PAGE_START);
      add(integersPanel, BorderLayout.CENTER);
   }

   private JButton createIntegerButton(Integer integer) {
      JButton integerButton = new JButton(integer.toString());
      integerButton.addActionListener(this);
      return integerButton;
   }

   private JLabel createMessageLabel() {
      JLabel messageLabel = new JLabel(message);
      messageLabel.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));
      return messageLabel;
   }

   private void cancelDialog() {
      selectedInteger = null;
      dispose();
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      JButton source = (JButton) e.getSource();
      selectedInteger = lowerBound + integerButtons.indexOf(source);
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
