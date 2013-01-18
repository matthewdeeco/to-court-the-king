package game.view;

import java.util.ArrayList;
import game.controller.ConfirmHandler;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;

/**
 * Dialog prompting the user to choose his/her avatar.
 * @author Matthew Co
 */
public class AvatarChoiceDialog extends JDialog implements ActionListener
{
   private static final int AVATAR_COUNT = 36;
   private int selectedAvatar;
   private ArrayList<JButton> avatarButtons;

   public AvatarChoiceDialog() {
      avatarButtons = new ArrayList<JButton>();

      setTitle("Choose your avatar");
      setModal(true);
      setResizable(false);

      setLayout(new GridLayout(4, 9));
      addAvatarButtons();
      pack();
      setLocationRelativeTo(null);

      setVisible(true);
   }

   private void addAvatarButtons() {
      for (int i = 1; i <= AVATAR_COUNT; i++)
         addAvatarButton(i);
   }

   private void addAvatarButton(int avatarIndex) {
      JButton avatarButton = createAvatarButton(avatarIndex);
      add(avatarButton);
      avatarButtons.add(avatarButton);
   }

   private JButton createAvatarButton(int avatarIndex) {
      final int BUTTON_LENGTH = 66;
      final int BUTTON_WIDTH = 66;

      final Dimension BUTTON_DIMENSION =
             new Dimension(BUTTON_LENGTH, BUTTON_WIDTH);

      JButton avatarButton = new JButton(new ImageIcon(
             getClass().getClassLoader().getResource(
             "images/avatars/" + avatarIndex + ".png")));
      avatarButton.addActionListener(this);
      avatarButton.setPreferredSize(BUTTON_DIMENSION);

      return avatarButton;
   }

   private void confirmSelection() {
      if (ConfirmHandler.confirmAvatarChoice(selectedAvatar))
         dispose();
   }

   public int getSelectedAvatar() {
      return selectedAvatar;
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      JButton avatarButtonPressed = (JButton) e.getSource();
      selectedAvatar = avatarButtons.indexOf(avatarButtonPressed) + 1;
      confirmSelection();
   }
}
