package com.netflix.objects;

public class Season {

  private Serie serie;
  private String title;
  private int seaonNumber;
  private int amountOfEpisodes;

  public Season(Serie serie, String title, int seaonNumber) {
    this.serie = serie;
    this.title = title;
    this.seaonNumber = seaonNumber;
    amountOfEpisodes = 0;
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
