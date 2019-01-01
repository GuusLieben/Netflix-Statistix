package com.netflix.entities;

import com.netflix.*;
import com.netflix.commons.*;
import com.netflix.entities.abstracts.Entity;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class Language extends Entity {

  public static Set<Language> langs = new HashSet<>();
  private String langCode;
  private String languageName;

  public Language(String langCode, String languageName) {
    this.langCode = langCode;
    this.languageName = languageName;
    langs.add(this);
  }

  public static Language getLang(String langCode, String langName) {
    // Used for the DatabaseHandle to check if one already exists
    for (Language lang : langs) if (lang.getLangCode().equals(langCode)) return lang;
    return new Language(langCode, langName);
  }

  public static Language getByCode(String languageCode) {
    for (Language lang : langs) if (lang.getLangCode().equals(languageCode)) return lang;
    return null;
  }

  public String getLangCode() {
    return langCode;
  }

  public String getLanguageName() {
    return languageName;
  }

  public static void getFromDatabase() {
    if (Netflix.database.connectDatabase()) {
      String sqlQuery = "SELECT LanguageCode, Language FROM Language";
      ResultSet results = null;
      try (Statement statement = Netflix.database.connection.createStatement()) {
        // Make sure the results are passed
        results = statement.executeQuery(sqlQuery);
        System.out.println("Query passed : " + results.toString());
        while (results.next())
          new Language(results.getString("LanguageCode"), results.getString("Language"));

      } catch (SQLException ex) {
        Commons.exception(ex);
        System.out.println("Query did not pass");
      }
      Netflix.database.disconnectDatabase();
    }
  }
}
