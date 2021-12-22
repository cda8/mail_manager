package fr.afpa.cda.metier;

import java.util.Date;

public class Mail {

  private static int idCount = 0;
  private int id;
  private String sujet;
  private String corps;
  private String expediteur;
  private String dateReception;
  private EFlags flag;

  public Mail() {
    idCount++;
    this.id = idCount;
  }

  public Mail(String sujet, String corps, String expediteur, String dateReception) {
    idCount++;
    this.id = idCount;
    this.sujet = sujet;
    this.corps = corps;
    this.expediteur = expediteur;
    this.dateReception = dateReception;

  }

  public static int getIdCount() {
    return idCount;
  }

  public static void setIdCount(int idCount) {
    Mail.idCount = idCount;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getSujet() {
    return sujet;
  }

  public void setSujet(String sujet) {
    this.sujet = sujet;
  }

  public String getCorps() {
    return corps;
  }

  public void setCorps(String corps) {
    this.corps = corps;
  }

  public String getExpediteur() {
    return expediteur;
  }

  public void setExpediteur(String expediteur) {
    this.expediteur = expediteur;
  }

  public String getDateReception() {
    return dateReception;
  }

  public void setDateReception(String string) {
    this.dateReception = string;
  }

  public EFlags getFlag() {
    return flag;
  }

  public void setFlag(EFlags flag) {
    this.flag = flag;
  }

  @Override
  public String toString() {
    return "Mail [corps=" + corps + ", dateReception=" + dateReception + ", expediteur=" + expediteur + ", flag=" + flag
        + ", id=" + id + ", sujet=" + sujet + "]";
  }

}
