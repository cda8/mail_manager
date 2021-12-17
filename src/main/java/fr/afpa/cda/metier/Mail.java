package fr.afpa.cda.metier;

import java.util.Date;

public class Mail {

  private static int idCount = 0;
  private int id;
  private String sujet;
  private String expediteur;
  private Date dateReception;
  private AppFlags flag;

  public Mail() {
    idCount++;
    this.id = idCount;
  }

  public Mail(String sujet, String expediteur, Date dateReception) {
    idCount++;
    this.id = idCount;
    this.sujet = sujet;
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

  public String getExpediteur() {
    return expediteur;
  }

  public void setExpediteur(String expediteur) {
    this.expediteur = expediteur;
  }

  public Date getDateReception() {
    return dateReception;
  }

  public void setDateReception(Date dateReception) {
    this.dateReception = dateReception;
  }

  public AppFlags getFlag() {
    return flag;
  }

  public void setFlag(AppFlags flag) {
    this.flag = flag;
  }


}
