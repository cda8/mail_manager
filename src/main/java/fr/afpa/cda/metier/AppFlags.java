package fr.afpa.cda.metier;

public enum AppFlags {
  READ("R"), UNREAD("U"), DELETED("D");

  private String abbr;

  AppFlags(String abbr) {
    this.abbr = abbr;
  }
}
