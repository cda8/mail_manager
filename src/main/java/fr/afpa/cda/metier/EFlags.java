package fr.afpa.cda.metier;

public enum EFlags {
  READ("R"), UNREAD("U"), DELETED("D");

  private String abbr;

  private EFlags(String abbr) {
    this.abbr = abbr;
  }

  public String getAbbr() {
    return abbr;
  }

 
}
