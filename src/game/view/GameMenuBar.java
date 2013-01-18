package game.view;

import java.util.ArrayList;
import game.controller.Controller;
import game.model.Model;
import game.model.ModelObserver;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.*;

/**
 * The game's menu bar.
 * @author Matthew Co
 */
public class GameMenuBar extends JMenuBar implements ActionListener, ModelObserver
{
   /** A part of the main menu entitled "Game". */
   private JMenu gameMenu;
   /** A part of the main menu entitled "Settings". */
   private JMenu settingsMenu;
   /** A part of the main menu entitled "Help". */
   private JMenu helpMenu;
   /** A part of the settings menu entitled "Confirm". */
   private JMenu confirmMenu;
   /** A part of the settings menu entitled "Sound". */
   private JMenu soundMenu;
   /** A part of the settings menu entitled "Soundtrack". */
   private JMenu soundTrackMenu;
   /** A sub-menu of the "Game" menu entitled "New Game". */
   private JMenuItem newGameMenuItem;
   /** A sub-menu of the "Game" menu entitled "Save Game". */
   private JMenuItem saveGameMenuItem;
   /** A sub-menu of the "Game" menu entitled "Load Game". */
   private JMenuItem loadGameMenuItem;
   /** A sub-menu of the "Game" menu entitled "Exit". */
   private JMenuItem exitMenuItem;
   /** A sub-menu of the "Confirm" menu entitled "Confirm First". */
   private JMenuItem confirmFirstMenuItem;
   /** A sub-menu of the "Confirm" menu entitled "Do not Confirm". */
   private JMenuItem doNotConfirmMenuItem;
   /** A sub-menu of the "Sound" menu entitled "On". */
   private JMenuItem soundOnMenuItem;
   /** A sub-menu of the "Sound" menu entitled "Off". */
   private JMenuItem soundOffMenuItem;
   /** A sub-menu of the "Soundtrack" menu with the tracks available. */
   private ArrayList<Object> trackMenuItems;
   /** A sub-menu of the "Help" menu entitled "Help". */
   private JMenuItem helpMenuItem;
   /** A sub-menu of the "Help" menu entitled "About". */
   private JMenuItem aboutMenuItem;
   /** The game model observed by the view. */
   private Model gameModel;
   /** The game controller used by the view. */
   private Controller gameController;

   /**
    * Constructor for GameMenuBar.
    * @param newGameModel the game model to be observed.
    * @param newFindTheKingController the game controller to be used.
    */
   public GameMenuBar(Model newGameModel,
          Controller newFindTheKingController) {
      gameModel = newGameModel;
      gameController = newFindTheKingController;

      initializeMenus();
      initializeMenuItems();
      addKeyboardShortcuts();
      registerAsObserver();
   }

   /**
    * Adds the menus to the menu bar.
    */
   private void initializeMenus() {
      gameMenu = createMenu("Game", this);
      settingsMenu = createMenu("Settings", this);
      helpMenu = createMenu("Help", this);

      confirmMenu = createMenu("Confirm", settingsMenu);
      soundMenu = createMenu("Sound", settingsMenu);
      soundTrackMenu = createMenu("Soundtrack", settingsMenu);
   }

   /**
    * Adds the menu items to their corresponding menus.
    */
   private void initializeMenuItems() {
      newGameMenuItem = createMenuItem("New Game", gameMenu);
      saveGameMenuItem = createMenuItem("Save Game", gameMenu);
      saveGameMenuItem.setEnabled(false);
      loadGameMenuItem = createMenuItem("Load Game", gameMenu);
      exitMenuItem = createMenuItem("Quit", gameMenu);

      confirmFirstMenuItem = createMenuItem("Confirm first", confirmMenu);
      doNotConfirmMenuItem = createMenuItem("Don't confirm", confirmMenu);

      soundOnMenuItem = createMenuItem("On", soundMenu);
      soundOffMenuItem = createMenuItem("Off", soundMenu);

      trackMenuItems = new ArrayList<Object>();
      for (String trackTitle : gameController.getTrackTitles())
         trackMenuItems.add(createMenuItem(trackTitle, soundTrackMenu));

      helpMenuItem = createMenuItem("Help Manual", helpMenu);
      aboutMenuItem = createMenuItem("About", helpMenu);
   }

   /**
    * Adds the keyboard shortcuts to the menu items.
    */
   private void addKeyboardShortcuts() {
      newGameMenuItem.setAccelerator(
             KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.CTRL_MASK));
      saveGameMenuItem.setAccelerator(
             KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK));
      loadGameMenuItem.setAccelerator(
             KeyStroke.getKeyStroke(KeyEvent.VK_O, Event.CTRL_MASK));
      exitMenuItem.setAccelerator(
             KeyStroke.getKeyStroke(KeyEvent.VK_Q, Event.CTRL_MASK));
      helpMenuItem.setAccelerator(
             KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
   }

   /**
    * Creates a JMenu.
    * @param text the text to be displayed in the menu.
    * @param menu the menu / menubar this jmenu.
    * @return a JMenu with the text description and a part of the menu / menubar.
    */
   private JMenu createMenu(String text, JComponent menu) {
      JMenu subMenu = new JMenu(text);
      menu.add(subMenu);

      return subMenu;
   }

   /**
    * Creates a JMenuItem.
    * @param text the text to be displayed in the menu item.
    * @param menu the menu this jmenuitem is a part of.
    * @return a JMenuItem with the text description and a part of the menu.
    */
   private JMenuItem createMenuItem(String text, JMenu menu) {
      JMenuItem menuItem = new JMenuItem(text);
      menu.add(menuItem);
      menuItem.addActionListener(this);

      return menuItem;
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      Object menuPressed = e.getSource();

      if (menuPressed == newGameMenuItem)
         gameController.newGame();
      else if (menuPressed == saveGameMenuItem)
         gameController.saveGame();
      else if (menuPressed == loadGameMenuItem)
         gameController.loadGame();
      else if (menuPressed == exitMenuItem)
         gameController.exitGame();
      else if (menuPressed == confirmFirstMenuItem)
         gameController.confirmFirst();
      else if (menuPressed == doNotConfirmMenuItem)
         gameController.doNotConfirm();
      else if (menuPressed == soundOnMenuItem)
         gameController.turnSoundOn();
      else if (menuPressed == soundOffMenuItem)
         gameController.turnSoundOff();
      else if (menuPressed == helpMenuItem)
         gameController.displayHelp();
      else if (menuPressed == aboutMenuItem)
         gameController.displayAbout();
      else // it's one of the sound tracks
         gameController.setTrack(trackMenuItems.indexOf(menuPressed));
   }

   @Override
   public void modelChanged() {
      if (gameModel.isGameStarted())
         saveGameMenuItem.setEnabled(true);
      else
         saveGameMenuItem.setEnabled(false);
   }

   /**
    * Adds this object as an observer to the model.
    */
   private void registerAsObserver() {
      gameModel.registerObserver(this);
   }
}
