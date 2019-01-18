package com.netflix.gui.views;

import com.netflix.*;
import com.netflix.commons.*;
import com.netflix.entities.*;
import com.netflix.gui.*;
import com.netflix.gui.views.subpanels.*;
import lu.tudor.santec.jtimechooser.*;

import java.sql.*;
import java.time.*;
import java.util.List;

import static com.netflix.gui.views.ChildFrames.mediaDropdowns;

public class AlterView extends CreationFrame {

  MediaObject type;
  JTimeChooser durationPicker;

  public AlterView(MediaObject type) {
    super(String.format("%s bewerken", type.getClass().getSimpleName()));
    this.type = type;

    constructFields();
  }

  private void constructFields() {
    addTextField("Titel");

    if (type.getClass() == Film.class) {
      durationPicker = addTimePicker("Duratie");
      addTextField("Regisseur");
    }

    mediaDropdowns(this);

    List<String> titles = Film.filmTitles;
    generateDropDown("Vergelijkbaar", titles);
    Commons.NButton addEntity = new Commons.NButton("Opslaan");
    addButton(addEntity);
    addEntity.addActionListener(e -> alterDatabase());

    fillData();
  }

  private void fillData() {
    values.get("Titel").setText(type.title);
    comboValues.get("Genre").setSelectedItem(type.getGenre().getGenre());
    comboValues
        .get("Taal")
        .setSelectedItem(
            String.format("%s %s", type.getLang().getLanguageName(), type.getLang().getLangCode()));
    comboValues.get("Classificatie").setSelectedItem(type.getRating().getAgeCode());
    comboValues.get("Vergelijkbaar").setSelectedItem(type.getSimilarMedia());

    if (type.getClass() == Film.class) {
      Film film = (Film) type;
      durationPicker.setTime(film.getDuration());
      values.get("Regisseur").setText(film.getDirector());
    }
  }

  private void alterDatabase() {
    String title = values.get("Titel").getText();
    Object[] mediaCommons = ChildFrames.getMediaCommons(comboValues);
    MediaCommons.Genre genre = (MediaCommons.Genre) mediaCommons[0];
    MediaCommons.Language lang = (MediaCommons.Language) mediaCommons[1];
    MediaCommons.AgeRating rating = (MediaCommons.AgeRating) mediaCommons[2];
    String similar = comboValues.get("Vergelijkbaar").getSelectedItem().toString();

    if (type.getClass() == Film.class) {
      Time duration =
          Time.valueOf(
              LocalTime.of(
                  durationPicker.getHours(),
                  durationPicker.getMinutes(),
                  durationPicker.getSeconds()));
      String director = values.get("Regisseur").getText();

      String qr =
          "UPDATE Film SET Rating=?, LijktOp=?, LanguageCode=?, Title=?, Duration=?, Director=? WHERE FilmId=?";
      Object[] arr = {
        rating.getMinimumAge(),
        similar,
        lang.getLangCode(),
        title,
        duration,
        director,
        type.databaseId
      };

      Netflix.database.executeSqlNoResult(qr, arr);

      String qr2 = "UPDATE Koppeltabel_GenreId_Film SET GenreId=? WHERE FilmId=?";
      Object[] arr2 = {genre.databaseId, type.databaseId};

      Netflix.database.executeSqlNoResult(qr2, arr2);

      Film film = (Film) type;
      film.setSimilarMedia(similar);
      film.setDuration(duration);

    } else if (type.getClass() == Serie.class) {
      String qr = "UPDATE Serie SET Title=?, LijktOp=?, LanguageCode=?, Rating=? WHERE SerieId=?";
      Object[] arr = {title, similar, lang.getLangCode(), rating.getMinimumAge(), type.databaseId};

      Netflix.database.executeSqlNoResult(qr, arr);

      String qr2 = "UPDATE Koppeltabel_Serie_Genre SET GenreId=? WHERE SerieId=?";
      Object[] arr2 = {genre.databaseId, type.databaseId};

      Netflix.database.executeSqlNoResult(qr2, arr2);
    }

    // This is common for both Serie and Film
    type.setTitle(title);
    type.setGenre(genre);
    type.setLang(lang);
    type.setRating(rating);

    dispose();

    Commons.clearPane(NetflixFrame.lpane);
    if (type.getClass() == Serie.class) NetflixFrame.lpane.add(SerieReadPanel.pane());
    else if (type.getClass() == Film.class) NetflixFrame.lpane.add(FilmReadPanel.pane());
  }
}
