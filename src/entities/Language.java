package com.netflix.entities;

import com.netflix.entities.abstracts.Entity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static com.netflix.Netflix.database;

public class Language extends Entity {

  public static Set<Language> langs = new HashSet<>();
  private String langCode;
  private String languageName;

  public Language(String langCode, String languageName) {
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
