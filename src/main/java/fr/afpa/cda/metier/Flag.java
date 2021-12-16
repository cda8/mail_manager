package fr.afpa.cda.metier;

public enum Flag {
  DELETE("D"), READ("R"), UNREAD("U");

  private String abreviation;

  private Flag(String abreviation) {
    this.abreviation = abreviation;
  }

  public String getAbreviation() {
    return this.abreviation;
  }
}
