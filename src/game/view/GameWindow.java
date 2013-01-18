package game.view;

import game.controller.Controller;
import game.model.Model;
import game.model.ModelObserver;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.*;

public class GameWindow extends JFrame implements ModelObserver, WindowListener
{
   private GameMenuBar mainMenuBar;
   private CardCounterPanel cardCounterPanel;
   private GamePanel gamePanel;
   private HandPanel handPanel;
   private Model model;
   private Controller controller;

   public GameWindow(Model model, Controller controller) {
      this.model = model;
      this.controller = controller;

      initializeWindow();
      initializePanels();
      registerAsObserver();
   }

   @Override
   public void modelChanged() {
      if (!model.isGameStarted())
         setTitle(model.getGameTitle() + " - No Game Started");
      else if (model.isFinalRoundThisRound())
         setTitle(model.getGameTitle() + " - Final Round");
      else
         setTitle(model.getGameTitle() + " - Round " + model.getRoundCount());

      if (model.isGameStarted())
         getContentPane().setVisible(true);
      else
         getContentPane().setVisible(false);
   }

   private void initializeWindow() {
      setTitle(model.getGameTitle() + " - No Game Started");
      setLayout(new BorderLayout());
      setIconImage(new ImageIcon(getClass().getClassLoader().
             getResource("images/icons/main-icon.png")).getImage());
      setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
      setSize(800, 520);
      setResizable(false);
      setLocationRelativeTo(null);
      addWindowListener(this);
      getContentPane().setVisible(false);
      getContentPane().setBackground(new JFrame().getBackground());
      initializeMenuBar();
   }

   private void initializePanels() {
      cardCounterPanel = new CardCounterPanel(model);
      gamePanel = new GamePanel(model, controller);
      handPanel = new HandPanel(model);

      add(cardCounterPanel, BorderLayout.LINE_START);
      add(gamePanel, BorderLayout.CENTER);
      add(handPanel, BorderLayout.LINE_END);
   }

   private void initializeMenuBar() {
      mainMenuBar = new GameMenuBar(model, controller);
      setJMenuBar(mainMenuBar);
   }

   @Override
   public void windowClosing(WindowEvent e) {
      controller.exitGame();
   }

   private void registerAsObserver() {
      model.registerObserver(this);
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
