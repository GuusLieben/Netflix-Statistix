package com.netflix.entities;

import com.netflix.commons.Commons;

public class Language {

  private String langCode;
  private String languageName;

  public Language(String langCode, String languageName) {
    this.langCode = langCode;
    this.languageName = languageName;
    Commons.langs.add(this);
  }

  public static Language getLang(String langCode, String langName) {
    for (Language lang : Commons.langs) if (lang.getLangCode().equals(langCode)) return lang;
    return new Language(langCode, langName);
  }

  public String getLangCode() {
    return langCode;
  }

  public String getLanguageName() {
    return languageName;
  }
}
