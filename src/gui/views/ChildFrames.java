package com.netflix.gui.views;

import com.netflix.commons.*;
import com.netflix.entities.*;
import com.netflix.gui.*;
import com.netflix.gui.views.subpanels.*;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.util.stream.*;

public class ChildFrames {
  public static class EpisodeCreation extends CreationFrame {
    public EpisodeCreation() {
      super("Nieuwe aflevering");

      addTextField("Titel");

      JComboBox serieBox = generateDropDown("Serie", Serie.serieTitles);

      String[] arr = new String[Serie.getSerieByName(serieBox.getSelectedItem().toString()).getSeasonCount()];
      for (int i = 0;
          i < Serie.getSerieByName(serieBox.getSelectedItem().toString()).getSeasonCount();
          i++)
        arr[i] =
            Serie.getSerieByName(serieBox.getSelectedItem().toString())
                .getSeasons()
                .get(i)
                .getTitle();
      JComboBox seasonBox = addDropDown("Seizoen", arr);

      addTextField("Duratie");
      addTextField("Afleveringsnummer");

      Common.NButton addEntity = new Common.NButton("Toevoegen");
      addButton(addEntity);

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
  }

  public static class LanguageCreation extends CreationFrame {
    public LanguageCreation() {
      super("Nieuwe taal");
      addTextField("Taal");
      addTextField("Code (ISO)"); // [a-z]{2}[_]{1}[A-Z]{2}
      Common.NButton addEntity = new Common.NButton("Toevoegen");
      addButton(addEntity);
    }
  }

  public static class SeasonCreation extends CreationFrame {
    public SeasonCreation() {
      super("Nieuw seizoen");

      generateDropDown("Serie", Serie.serieTitles);

      addTextField("Titel");
      addTextField("Seizoensnummer"); // [0-9]*
      Common.NButton addEntity = new Common.NButton("Toevoegen");
      addButton(addEntity);
    }
  }

  public static class RatingCreation extends CreationFrame {
    public RatingCreation() {
      super("Nieuwe classificatie");
      addTextField("Code"); // [A-Z]{1,2}[0-9]{1,2}
      addTextField("Minimum leeftijd"); // [0-9]{1,2}
      Common.NButton addEntity = new Common.NButton("Toevoegen");
      addButton(addEntity);
    }
  }

  public static class FilmCreation extends CreationFrame {
    public FilmCreation() {
      super("Nieuwe film");

      addTextField("Titel");
      addTextField("Duratie");
      addTextField("Regisseur");

      mediaDropdowns(this);

      List<String> titles = Film.filmTitles;
      generateDropDown("Vergelijkbaar", titles);

      Common.NButton addEntity = new Common.NButton("Toevoegen");
      addButton(addEntity);
    }
  }

  public static class GenreCreation extends CreationFrame {

    public GenreCreation() {
      super("Nieuw genre");
      addTextField("Naam");
      Common.NButton addEntity = new Common.NButton("Toevoegen");
      addButton(addEntity);
    }
  }

  public static class SerieCreation extends CreationFrame {

    public SerieCreation() {
      super("Nieuwe serie");

      addTextField("Titel");

      mediaDropdowns(this);

      List<String> titles = Serie.serieTitles;
      generateDropDown("Vergelijkbaar", titles);

      Common.NButton addEntity = new Common.NButton("Toevoegen");
      addButton(addEntity);
      actionListenerSeriePress(addEntity);
    }

    private void actionListenerSeriePress(JButton button) {
      button.addActionListener(
          e -> {
            Map<String, JTextField> map = values;

            try {
              String[] langs = map.get("Taal").getText().split(" ");

              String title = map.get("Titel").getText();
              MediaCommons.Genre genre = MediaCommons.Genre.getByName(map.get("Genre").getText());
              MediaCommons.Language lang =
                  MediaCommons.Language.getByCode(
                      langs[
                          langs.length
                              - 1]); // Keep in mind languages can have spaces in them as well, so
              // only select the last part of the String which is always the
              // code
              MediaCommons.AgeRating rating =
                  MediaCommons.AgeRating.getByCode(
                      comboValues.get("Classificatie").getSelectedItem().toString());

              String similarMedia = map.get("Vergelijkbaar").getText();

              if (!title.equals("")) {
                new CreateOnDatabase().createSerie(genre, lang, title, rating, similarMedia);
              }

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

  public static class AccountCreation extends CreationFrame {

    public AccountCreation() {
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

      Common.NButton addEntity = new Common.NButton("Toevoegen");
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
