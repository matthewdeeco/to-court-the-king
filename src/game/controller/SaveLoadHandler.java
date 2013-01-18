package game.controller;

import game.model.Model;
import game.utility.Dialog;

import java.io.*;

public class SaveLoadHandler
{
   private static final String FILE_EXTENSION = "sav";

   public void saveState(Model model) {
      String gameSaveDescription = "Game save (*." + FILE_EXTENSION + ")";
      String filePath = Dialog.inputSaveFilePath(
             gameSaveDescription, FILE_EXTENSION);
      if (filePath != null)
         saveState(model, filePath);
   }

   private void saveState(Model model, String filePath) {
      ObjectOutputStream writer = null;
      try {
         filePath = addFileExtensionIfNotThere(filePath);
         writer = new ObjectOutputStream(new FileOutputStream(filePath));
         writer.writeObject(model);
      }
      catch (Exception ex) {
         String message = "Error saving the game!\n"
                + "Make sure you have the privilege "
                + "to save to that folder.";
         Dialog.errorMessage(message);
      }
      finally {
         tryToClose(writer);
      }
   }

   private String addFileExtensionIfNotThere(String filePath) {
      if (!filePath.endsWith("." + FILE_EXTENSION))
         filePath += "." + FILE_EXTENSION;
      return filePath;
   }

   private void tryToClose(Closeable closeable) {
      try {
         closeable.close();
      }
      catch (IOException ex) {
      }
   }

   public Model retrieveState() {
      String gameSaveDescription = "Game save (*." + FILE_EXTENSION + ")";
      String filePath = Dialog.inputLoadFilePath(gameSaveDescription, FILE_EXTENSION);

      if (filePath == null)
         return null;
      else
         return retrieveState(filePath);
   }

   private Model retrieveState(String filePath) {
      ObjectInputStream reader = null;
      Model loadedGameModel = null;
      try {
         reader = new ObjectInputStream(new FileInputStream(filePath));
         loadedGameModel = (Model) reader.readObject();
         return loadedGameModel;
      }
      catch (Exception ex) {
         String message = "Error loading your save file!\n"
                + "Make sure that your save is for this \n"
                + "game version and that the file exists.";
         Dialog.errorMessage(message);
      }
      finally {
         tryToClose(reader);
      }
      return loadedGameModel;
   }
}
