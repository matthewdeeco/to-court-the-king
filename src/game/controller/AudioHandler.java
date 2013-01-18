package game.controller;

import java.util.ArrayList;
import game.model.Model;
import game.model.ModelObserver;

/**
 * Handles audio playback, looping and stopping.
 */
public class AudioHandler implements ModelObserver
{
   private static final String[] THEME_TITLES = {"Random", "Carried on Rippling Waves", "Confrontation With Monsters, Again", "Dancing with Karen", "Gothic Neclord", "Imprisoned Town", "Let's Climb That Hill", "Rescue", "The Even More Glorious, Beautiful Golden City", "The Time For Confrontation", "Those Who Work Must Eat", "Young Heroes"};
   private ArrayList<Theme> THEMES = new ArrayList<Theme>();
   private Model model;
   /** Represents a random theme. */
   private final Theme RANDOM_THEME = null;
   /** The current theme, which can be random or set. */
   private Theme currentTheme;
   /** A random audio clip that the random theme is playing. */
   private Theme randomTheme;
   /** The theme that plays during the final round. */
   private Theme finalRoundTheme;
   /** The fanfare that plays when the winner is declared. */
   private Theme victoryFanfare;
   /** True if the theme is currently playing, false if it isn't. */
   private boolean isThemePlaying;
   /** True if the final round theme is currently playing, false if it isn't. */
   private boolean isFinalRoundThemePlaying;

   public AudioHandler(Model model) {
      this.model = model;
      currentTheme = RANDOM_THEME;

      initializeThemes();
      registerAsObserver();
   }

   private void initializeThemes() {
      for (String themeTitle : THEME_TITLES)
         if (themeTitle.equalsIgnoreCase("RANDOM"))
            THEMES.add(RANDOM_THEME);
         else {
            Theme theme = new Theme(themeTitle);
            THEMES.add(theme);
         }
      finalRoundTheme = new Theme("Victory");
      victoryFanfare = new Theme("Victory Fanfare");
   }

   private Theme getRandomTrack() {
      int randomTrackIndex = createRandomThemeIndex();
      return THEMES.get(randomTrackIndex);
   }

   public void playTheme() {
      if (model.isFinalRoundThisRound()) {
         isFinalRoundThemePlaying = true;
         finalRoundTheme.loop();
      }
      else if (!isThemePlaying)
         if (currentTheme == RANDOM_THEME) {
            randomTheme = getRandomTrack();
            randomTheme.loop();
            isThemePlaying = true;
         }
         else {
            currentTheme.loop();
            isThemePlaying = true;
         }
   }

   public void stopTheme() {
      if (isFinalRoundThemePlaying) {
         finalRoundTheme.stop();
         isFinalRoundThemePlaying = false;
      }
      else if (isThemePlaying)
         if (currentTheme == RANDOM_THEME) {
            randomTheme.stop();
            isThemePlaying = false;
         }
         else {
            currentTheme.stop();
            isThemePlaying = false;
         }
   }

   public void setTheme(int nextThemeIndex) {
      boolean isTrackPlayingBefore = isThemePlaying;
      Theme nextTheme = THEMES.get(nextThemeIndex);

      if (!model.isFinalRoundThisRound()) {
         stopTheme();
         if (currentTheme != nextTheme || currentTheme == RANDOM_THEME) {
            currentTheme = nextTheme;
            if (isTrackPlayingBefore)
               playTheme();
         }
      }
      currentTheme = nextTheme;
   }

   public void setRandomTheme() {
      int randomTrackIndex = createRandomThemeIndex();
      setTheme(randomTrackIndex);
   }

   private int createRandomThemeIndex() {
      int trackCount = THEMES.size() - 1; // random track is not included.
      int randomTrackIndex = 1 + (int) (trackCount * Math.random());

      return randomTrackIndex;
   }

   public String[] getTrackTitles() {
      return THEME_TITLES;
   }

   public void playVictoryFanfare() {
      victoryFanfare.play();
   }

   @Override
   public void modelChanged() {
      if (model.isFinalRoundThisRound() && isThemePlaying && !isFinalRoundThemePlaying) {
         if (currentTheme == RANDOM_THEME)
            randomTheme.stop();
         else
            currentTheme.stop();
         isFinalRoundThemePlaying = true;
         finalRoundTheme.loop();
      }
      else if (!model.isFinalRoundThisRound() && isFinalRoundThemePlaying) {
         finalRoundTheme.stop();
         isFinalRoundThemePlaying = false;
         if (isThemePlaying)
            if (currentTheme == RANDOM_THEME) {
               randomTheme = getRandomTrack();
               randomTheme.loop();
            }
            else
               currentTheme.loop();
      }
   }

   private void registerAsObserver() {
      model.registerObserver(this);
   }
}
