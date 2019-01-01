package com.netflix.entities;

import com.netflix.*;
import com.netflix.entities.abstracts.*;

import java.util.*;

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
    for (HashMap<String, Object> map :
        Netflix.database.executeSql("SELECT LanguageCode, Language FROM Language")) {
      new Language((String) map.get("LanguageCode"), (String) map.get("Language"));
    }
  }
}
