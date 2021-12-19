package fr.afpa.cda.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import fr.afpa.cda.metier.AppFlags;

@Entity(name = "mails")
@Table(name = "mails")
public class MailEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int idmails;
  private String sujet;
  private String expediteur;
  private String dateReception;
  private String flag;

  public MailEntity() {
    super();
  }

  public int getIdmail() {
    return idmails;
  }

  public void setIdmail(int idmail) {
    this.idmails = idmail;
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

  public String getDateReception() {
    return dateReception;
  }

  public void setDateReception(String dateReception) {
    this.dateReception = dateReception;
  }

  public String getFlag() {
    return flag;
  }

  public void setFlag(AppFlags flag) {
    this.flag = flag.toString();
  }

  @Override
  public String toString() {
    return "MailEntity [dateReception=" + dateReception + ", expediteur=" + expediteur + ", flag=" + flag + ", idmails="
        + idmails + ", sujet=" + sujet + "]";
  }

}
