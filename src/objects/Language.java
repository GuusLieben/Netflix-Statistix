package com.netflix.objects;

public class Language {

  private String langCode;
  private String languageName;

  public Language(String langCode, String languageName) {
    this.langCode = langCode;
    this.languageName = languageName;
  }

  public String getLangCode() {
    return langCode;
  }

  public String getLanguageName() {
    return languageName;
  }
}
