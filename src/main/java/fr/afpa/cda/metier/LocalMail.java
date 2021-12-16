package fr.afpa.cda.metier;

import java.util.Date;

public class LocalMail {

    private String sujet;
    private String expediteur;
    private Date dateReception;
    private char flag;
    private int idMail;

    public LocalMail() {
    }

    public LocalMail(String sujet, String expediteur, Date dateReception, char flag, int idMail) {
        this.sujet = sujet;
        this.expediteur = expediteur;
        this.dateReception = dateReception;
        this.flag = flag;
        this.idMail = idMail;
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

    public String getFlag() {
        return String.valueOf(flag);

    }

    public void setFlag(char flag) {
        this.flag = flag;
    }

    public int getIdMail() {
        return idMail;
    }

    public void setIdMail(int idMail) {
        this.idMail = idMail;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("-----------------------------------------")
                .append("\n")
                .append("de : ")
                .append(this.expediteur)
                .append("\n")
                .append("Flag ==> ")
                .append(this.flag)
                .append("\n")
                .append("Titre : ")
                .append(this.sujet)
                .append("\n")
                .append("-----------------------------------------")
                .append("\n");
        return sb.toString();
    }
}
