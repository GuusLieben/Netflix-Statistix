package com.netflix.objects;

import com.netflix.commons.Commons;

public class Language {

  private String langCode;
  private String languageName;

  public Language(String langCode, String languageName) {
    this.langCode = langCode;
    this.languageName = languageName;
    Commons.langs.add(this);
  }

  public String getLangCode() {
    return langCode;
  }

  public String getLanguageName() {
    return languageName;
  }
}
