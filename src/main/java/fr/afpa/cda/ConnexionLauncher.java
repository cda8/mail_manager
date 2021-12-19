package fr.afpa.cda;

import fr.afpa.cda.entities.MailEntity;
import fr.afpa.cda.metier.AppFlags;
import fr.afpa.cda.metier.ConnexionManager;
import fr.afpa.cda.metier.Mail;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnexionLauncher {

    public static void main(String[] args) {
        ConnexionManager connexion = new ConnexionManager();

        final Logger fLOGGER = LoggerFactory.getLogger(ConnexionLauncher.class);
        try {
            connexion.connexionMail();

            List<Mail> listMailsRead = connexion.getReadMails();
            fLOGGER.info("=> Liste des mails lus : ");
            for (Mail mail : listMailsRead) {
                fLOGGER.info("Sujet : " + mail.getSujet());
                fLOGGER.info("Expediteur : " + mail.getExpediteur());
                fLOGGER.info("Date de réception : " + mail.getDateReception());
                fLOGGER.info("Flag : " + mail.getFlag());
            }
            List<Mail> listMailsUnread = connexion.getUnreadMails();
            fLOGGER.info("=> Liste des mails non lus : ");
            for (Mail mail : listMailsUnread) {
                fLOGGER.info("Sujet : " + mail.getSujet());
                fLOGGER.info("Expediteur : " + mail.getExpediteur());
                fLOGGER.info("Date de réception : " + mail.getDateReception());
                fLOGGER.info("Flag : " + mail.getFlag());
            }
            List<Mail> listMailsDeleted = connexion.getDeletedMails();
            fLOGGER.info("=> Liste des mails supprimés : ");
            for (Mail mail : listMailsDeleted) {
                fLOGGER.info("Sujet : " + mail.getSujet());
                fLOGGER.info("Expediteur : " + mail.getExpediteur());
                fLOGGER.info("Date de réception : " + mail.getDateReception());
                fLOGGER.info("Flag : " + mail.getFlag());
            }

            connexion.getFolderNames();

        } catch (final Exception e) {
            fLOGGER.error(e.getMessage(), e);
        }
    }
}