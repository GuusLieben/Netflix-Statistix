package com.netflix.objects;

public class Language {

  private String langCode;
  private String language;

  public Language(String langCode, String language) {
    this.langCode = langCode;
    this.language = language;
  }

  public String getLangCode() {
    return langCode;
  }

  public String getLanguage() {
    return language;
  }
}
