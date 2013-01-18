package game.controller;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.MalformedURLException;
import java.net.URL;

public class Theme
{
   private AudioClip audioClip;

   public Theme(String title) {
      try {
         audioClip = Applet.newAudioClip(createUrl(title));
      }
      catch (Exception ex) {
      }
   }

   private URL createUrl(String title) throws MalformedURLException {
      String themeTitleWithExtension = title + ".au";
      URL url = new URL(Theme.class.getClassLoader().
             getResource("audio/" + themeTitleWithExtension).toString());
      return url;
   }

   public void play() {
      audioClip.play();
   }

   public void loop() {
      audioClip.loop();
   }

   public void stop() {
      audioClip.stop();
   }
}
