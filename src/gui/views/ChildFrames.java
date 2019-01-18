package com.netflix.gui.views;

import com.netflix.commons.*;
import com.netflix.entities.*;
import com.netflix.gui.*;
import com.netflix.gui.views.subpanels.*;
import lu.tudor.santec.jtimechooser.*;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.time.*;
import java.util.*;
import java.util.stream.*;

public class ChildFrames {

  static class RatingCreation extends CreationFrame {
    RatingCreation() {
      super("Nieuwe classificatie");
      addTextField("Code"); // [A-Z]{1,2}[0-9]{1,2}
      JSpinner age = addSpinner("Minimum leeftijd"); // [0-9]{1,2}
      Commons.NButton addEntity = new Commons.NButton("Toevoegen");
      addButton(addEntity);
    }

    private void actionListenerRatingPress(JButton button, JSpinner ageSpinner) {
      button.addActionListener(
          e -> {
            String code = values.get("Code").getText();
            int age = (int) ageSpinner.getValue();

            if (!code.equals("") && age > 3) new CreateOnDatabase().createRating(code, age);
          });
    }
  }

  // DONE //

  static class GenreCreation extends CreationFrame {

    GenreCreation() {
      super("Nieuw genre");
      addTextField("Naam");
      Commons.NButton addEntity = new Commons.NButton("Toevoegen");
      addButton(addEntity);
      actionListenerGenrePress(addEntity);
    }

    private void actionListenerGenrePress(JButton button) {
      button.addActionListener(
          e -> {
            String name = values.get("Naam").getText();

            if (!name.equals("")) new CreateOnDatabase().createGenre(name);

            GenreCreation.this.dispatchEvent(
                new WindowEvent(GenreCreation.this, WindowEvent.WINDOW_CLOSING));
          });
    }
  }

  static class LanguageCreation extends CreationFrame {
    LanguageCreation() {
      super("Nieuwe taal");
      addTextField("Taal");
      addTextField("Code (ISO)");
      Commons.NButton addEntity = new Commons.NButton("Toevoegen");
      addButton(addEntity);
      actionListenerLanguagePress(addEntity);
    }

    private void actionListenerLanguagePress(JButton button) {
      button.addActionListener(
          e -> {
            String language = values.get("Taal").getText();
            String langCode = values.get("Code (ISO)").getText();

            if (!language.equals("")
                && !langCode.equals("")
                && langCode.matches("[a-z]{2}[_]{1}[A-Z]{2}"))
              new CreateOnDatabase().createLanguage(language, langCode);

            LanguageCreation.this.dispatchEvent(
                new WindowEvent(LanguageCreation.this, WindowEvent.WINDOW_CLOSING));
          });
    }
  }

  public static Object[] getMediaCommons(Map<String, JComboBox> map) {
    String[] langs = map.get("Taal").getSelectedItem().toString().split(" ");

    MediaCommons.Genre genre =
        MediaCommons.Genre.getByName(map.get("Genre").getSelectedItem().toString());
    MediaCommons.Language lang =
        MediaCommons.Language.getByCode(
            langs[langs.length - 1]); // Keep in mind languages can have spaces in them as well, so
    // only select the last part of the String which is always the
    // code
    MediaCommons.AgeRating rating =
        MediaCommons.AgeRating.getByCode(map.get("Classificatie").getSelectedItem().toString());

    return new Object[] {genre, lang, rating};
  }

  static class FilmCreation extends CreationFrame {
    FilmCreation() {
      super("Nieuwe film");

      addTextField("Titel");
      JTimeChooser durationPicker = addTimePicker("Duratie");
      addTextField("Regisseur");

      mediaDropdowns(this);

      List<String> titles = Film.filmTitles;
      generateDropDown("Vergelijkbaar", titles);

      Commons.NButton addEntity = new Commons.NButton("Toevoegen");
      addButton(addEntity);
      actionListenerFilmPress(addEntity, durationPicker);
    }

    private void actionListenerFilmPress(JButton button, JTimeChooser durationPicker) {
      button.addActionListener(
          e -> {
            boolean originalName = true;
            boolean similarExists = false;

            String title = values.get("Titel").getText();
            Time duration =
                Time.valueOf(
                    LocalTime.of(
                        durationPicker.getHours(),
                        durationPicker.getMinutes(),
                        durationPicker.getSeconds()));
            String director = values.get("Regisseur").getText();

            Object[] mediaCommons = getMediaCommons(comboValues);

            MediaCommons.Genre genre = (MediaCommons.Genre) mediaCommons[0];
            MediaCommons.Language lang = (MediaCommons.Language) mediaCommons[1];
            MediaCommons.AgeRating rating = (MediaCommons.AgeRating) mediaCommons[2];

            String similarMedia = comboValues.get("Vergelijkbaar").getSelectedItem().toString();

            for (String filmTitle : Film.filmTitles) {
              if (title.equals(filmTitle)) originalName = false;
              if (similarMedia.equals(filmTitle)) similarExists = true;
            }
            if (!title.equals("")
                && director.matches("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$")
                && similarExists
                && originalName) {
              new CreateOnDatabase()
                  .createFilm(title, duration, director, genre, lang, rating, similarMedia);
            }

            FilmCreation.this.dispatchEvent(
                new WindowEvent(FilmCreation.this, WindowEvent.WINDOW_CLOSING));
          });
    }
  }

  static class SeasonCreation extends CreationFrame {
    SeasonCreation() {
      super("Nieuw seizoen");

      generateDropDown("Serie", Serie.serieTitles);

      addTextField("Titel");
      JSpinner seasonNum = addSpinner("Seizoensnummer"); // [0-9]*
      Commons.NButton addEntity = new Commons.NButton("Toevoegen");
      addButton(addEntity);
      actionListenerSeasonPress(addEntity, seasonNum);
    }

    private void actionListenerSeasonPress(JButton button, JSpinner seasonNum) {
      button.addActionListener(
          e -> {
            String serie = comboValues.get("Serie").getSelectedItem().toString();
            String title = values.get("Titel").getText();
            int seasonNumber = (int) seasonNum.getValue();

            if (!title.equals("") && seasonNumber > 0) {
              new CreateOnDatabase().createSeason(serie, title, seasonNumber);
            }

            SeasonCreation.this.dispatchEvent(
                new WindowEvent(SeasonCreation.this, WindowEvent.WINDOW_CLOSING));
          });
    }
  }

  static class EpisodeCreation extends CreationFrame {
    EpisodeCreation() {
      super("Nieuwe aflevering");

      addTextField("Titel");

      JComboBox serieBox = generateDropDown("Serie", Serie.serieTitles);

      String[] arr =
          new String[Serie.getSerieByName(serieBox.getSelectedItem().toString()).getSeasonCount()];
      for (int i = 0;
          i < Serie.getSerieByName(serieBox.getSelectedItem().toString()).getSeasonCount();
          i++)
        arr[i] =
            Serie.getSerieByName(serieBox.getSelectedItem().toString())
                .getSeasons()
                .get(i)
                .getTitle();
      JComboBox seasonBox = addDropDown("Seizoen", arr);

      JTimeChooser timePicker = addTimePicker("Duratie");
      JSpinner episodeNumber = addSpinner("Afleveringsnummer");

      Commons.NButton addEntity = new Commons.NButton("Toevoegen");
      addButton(addEntity);
      actionListenerEpisodePress(addEntity, timePicker, episodeNumber);

      serieBox.addActionListener(
          e -> {
            seasonBox.removeAllItems();
            for (int i = 0;
                i < Serie.getSerieByName(serieBox.getSelectedItem().toString()).getSeasonCount();
                i++)
              seasonBox.addItem(
                  Serie.getSerieByName(serieBox.getSelectedItem().toString())
                      .getSeasons()
                      .get(i)
                      .getTitle());
          });
    }

    private void actionListenerEpisodePress(
        JButton button, JTimeChooser timePicker, JSpinner episodeNumber) {
      button.addActionListener(
          e -> {
            Map<String, JTextField> map = values;
            boolean validNumber = false;
            try {
              String title = map.get("Titel").getText();
              String serie = comboValues.get("Serie").getSelectedItem().toString();
              String season = comboValues.get("Seizoen").getSelectedItem().toString();
              Time duration =
                  Time.valueOf(
                      LocalTime.of(
                          timePicker.getHours(), timePicker.getMinutes(), timePicker.getSeconds()));
              int episodeNum = (int) episodeNumber.getValue();

              for (Serie.Episode epi :
                  Serie.Season.getSeason(Serie.getSerieByName(serie), season).getEpisodes())
                if (episodeNum == epi.getEpisodeNumber())
                  Commons.showError("Er bestaat al een aflevering met dit nummer.");
                else validNumber = true;

              if (validNumber && !title.equals("")) {
                new CreateOnDatabase().createEpisode(title, serie, season, duration, episodeNum);
              }

              EpisodeCreation.this.dispatchEvent(
                  new WindowEvent(EpisodeCreation.this, WindowEvent.WINDOW_CLOSING));

            } catch (Exception ex) {
              Commons.exception(ex);
              Commons.showError("Incorrecte gegevens");
            }
          });
    }
  }

  static class SerieCreation extends CreationFrame {

    SerieCreation() {
      super("Nieuwe serie");

      addTextField("Titel");

      mediaDropdowns(this);

      List<String> titles = Serie.serieTitles;
      generateDropDown("Vergelijkbaar", titles);

      Commons.NButton addEntity = new Commons.NButton("Toevoegen");
      addButton(addEntity);
      actionListenerSeriePress(addEntity);
    }

    private void actionListenerSeriePress(JButton button) {
      button.addActionListener(
          e -> {
            Map<String, JTextField> map = values;

            try {
              String title = map.get("Titel").getText();
              Object[] mediaCommons = getMediaCommons(comboValues);

              MediaCommons.Language lang = (MediaCommons.Language) mediaCommons[1];
              MediaCommons.Genre genre = (MediaCommons.Genre) mediaCommons[0];
              MediaCommons.AgeRating rating = (MediaCommons.AgeRating) mediaCommons[2];

              String similarMedia = map.get("Vergelijkbaar").getText();

              if (!title.equals("")) {
                new CreateOnDatabase().createSerie(genre, lang, title, rating, similarMedia);
              }

              SerieCreation.this.dispatchEvent(
                  new WindowEvent(SerieCreation.this, WindowEvent.WINDOW_CLOSING));

            } catch (NullPointerException ex) {
              Commons.exception(ex);
              Commons.showError("Incorrecte gegevens");
            }
          });
    }
  }

  static void mediaDropdowns(CreationFrame cf) {
    String[] genres = new String[MediaCommons.Genre.genres.size()];
    for (int i = 0; i < MediaCommons.Genre.genres.size(); i++)
      genres[i] = MediaCommons.Genre.genres.get(i).getGenre();
    cf.addDropDown("Genre", genres);

    String[] languages = new String[MediaCommons.Language.langs.size()];
    for (int i = 0; i < MediaCommons.Language.langs.size(); i++)
      languages[i] =
          String.format(
              "%s %s",
              MediaCommons.Language.langs.get(i).getLanguageName(),
              MediaCommons.Language.langs.get(i).getLangCode());
    cf.addDropDown("Taal", languages);

    String[] ratings = new String[MediaCommons.AgeRating.ratings.size()];
    for (int i = 0; i < MediaCommons.AgeRating.ratings.size(); i++)
      ratings[i] = MediaCommons.AgeRating.ratings.get(i).getAgeCode();
    cf.addDropDown("Classificatie", ratings);
  }

  static class AccountCreation extends CreationFrame {

    AccountCreation() {
      super("Nieuw account");

      String[] fields = {
        "E-mail",
        "Bevestig e-mail",
        "Wachtwoord",
        "Bevestig wachtwoord",
        "Straat",
        "Nummer",
        "Toevoeging",
        "Woonplaats"
      };

      for (String str : fields) addTextField(str);

      Commons.NButton addEntity = new Commons.NButton("Toevoegen");
      addButton(addEntity);
      actionListenerAccountPress(addEntity);
    }

    private void actionListenerAccountPress(JButton button) {
      button.addActionListener(
          e -> {
            boolean matchingEmail = false;
            boolean matchingPassword = false;
            boolean validLocation = false;

            Map<String, JTextField> map = values;

            try {
              String email = map.get("E-mail").getText();
              String confirmEmail = map.get("Bevestig e-mail").getText();
              String password = map.get("Wachtwoord").getText();
              String confirmPassword = map.get("Bevestig wachtwoord").getText();
              String street = map.get("Straat").getText();
              int houseNumber = Integer.parseInt(map.get("Nummer").getText());

              String addition;
              try {
                addition = map.get("Toevoeging").getText();
              } catch (NullPointerException ex) {
                addition = ""; // Addition can be null, "" is replaced with SQL type NULL
              }

              String city = map.get("Woonplaats").getText();

              if (!email.equals("") && email.equals(confirmEmail)) {
                matchingEmail = true;
              }

              if (!password.equals("") && password.equals(confirmPassword)) {
                matchingPassword = true;
              }

              if (Stream.of(street, houseNumber, city).noneMatch(s -> s.equals(""))
                  && (addition.equals("") || addition.matches("^[a-zA-Z]$"))) {
                validLocation = true;
              }

              if (matchingEmail && matchingPassword && validLocation) {
                new CreateOnDatabase()
                    .createAccount(false, email, street, houseNumber, addition, city, password);

                AccountCreation.this.dispatchEvent(
                    new WindowEvent(AccountCreation.this, WindowEvent.WINDOW_CLOSING));

                Commons.clearPane(NetflixFrame.lpane);
                Commons.clearPane(AdminView.accountListTable.tablePanel);
                Commons.clearPane(AdminView.panel());
                NetflixFrame.lpane.add(AdminView.panel());
              }

            } catch (NullPointerException | NumberFormatException ex) {
              Commons.exception(ex);
              Commons.showError("Incorrecte gegevens");
            }
          });
    }
  }
}
