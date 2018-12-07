package com.netflix.objects;

public class Season {

  Serie serie;
  String title;
  int seaonNumber;
  int amountOfEpisodes;

  public Season(Serie serie, String title, int seaonNumber, int amountOfEpisodes) {
    this.serie = serie;
    this.title = title;
    this.seaonNumber = seaonNumber;
    this.amountOfEpisodes = 0;
    serie.setSeasons(serie.getSeasons() + 1);
  }

  public Serie getSerie() {
    return serie;
  }

  public String getTitle() {
    return title;
  }

  public int getSeaonNumber() {
    return seaonNumber;
  }

  public int getAmountOfEpisodes() {
    return amountOfEpisodes;
  }
}
