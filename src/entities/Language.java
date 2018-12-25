package com.netflix.entities;

import com.netflix.entities.abstracts.Entity;

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

  public String getLangCode() {
    return langCode;
  }

  public String getLanguageName() {
    return languageName;
  }
}
