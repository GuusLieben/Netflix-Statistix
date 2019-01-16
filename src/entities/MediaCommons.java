package com.netflix.entities;

import java.util.*;

import static com.netflix.Netflix.database;

public class MediaCommons {
  public static class Language extends Entity {

    public static List<Language> langs = new ArrayList<>();
    private String langCode;
    private String languageName;

    public Language(String langCode, String languageName) {
      super(langCode, languageName);
      this.langCode = langCode;
      this.languageName = languageName;
      langs.add(this);
    }

    // get a specific language by the langCode
    public static Language getByCode(String languageCode) {
      for (Language lang : langs) if (lang.getLangCode().equals(languageCode)) return lang;
      return null;
    }

    // Get languages from database
    public static void getFromDatabase() {
      for (HashMap<String, Object> map :
          database.executeSql("SELECT LanguageCode, Language FROM Language")) {
        new Language((String) map.get("LanguageCode"), (String) map.get("Language"));
      }
    }

    // Getters
    public String getLangCode() {
      return langCode;
    }

    public String getLanguageName() {
      return languageName;
    }
  }

  public static class Genre extends Entity {

    public static List<Genre> genres = new ArrayList<>();
    private String genreName;

    public Genre(String genre, int databaseId) {
      super(genre, null);
      this.genreName = genre;
      this.databaseId = databaseId;
      genres.add(this);
    }

    // Find the genre with a specific name
    public static Genre getByName(String name) {
      for (Genre genre : genres) if (genre.getGenre().equals(name)) return genre;
      return null;
    }

    // Get Genre from database
    public static void getFromDatabase() {
      for (HashMap<String, Object> map : database.executeSql("SELECT GenreId, Genre FROM Genre")) {
        new Genre((String) map.get("Genre"), (int) map.get("GenreId"));
      }
    }

    // Getter
    public String getGenre() {
      return genreName;
    }

    @Override
    public String toString() {
      return genreName;
    }
  }

  public static class AgeRating extends Entity {

    public static List<AgeRating> ratings = new ArrayList<>();
    private String ageCode;
    private int minimumAge;

    public AgeRating(String ageCode, int minimumAge) {
      super(ageCode, minimumAge);
      this.ageCode = ageCode;
      this.minimumAge = minimumAge;
      ratings.add(this);
    }

    // Get a rating by the age, used in Database handles
    public static AgeRating getByAge(int age) {
      for (AgeRating rating : ratings) if (rating.getMinimumAge() == age) return rating;
      return null;
    }

    // Get all ratings from the database
    public static void getFromDatabase() {
      for (HashMap<String, Object> map : database.executeSql("SELECT MPAA, Rating FROM Rating")) {
        new AgeRating((String) map.get("MPAA"), (int) map.get("Rating"));
      }
    }

    public static AgeRating getByCode(String code) {
      for (AgeRating rating : ratings) if (rating.getAgeCode().equals(code)) return rating;
      return null;
    }

    // Getters
    public String getAgeCode() {
      return ageCode;
    }

    public int getMinimumAge() {
      return minimumAge;
    }

    // toString used for the MediaObject ReadObject
    @Override
    public String toString() {
      // Dutch translations
      return String.format("%s (%d jaar en ouder)", getAgeCode(), getMinimumAge());
    }
  }
}
