package fr.afpa.cda.metier;

import java.util.List;

import javax.swing.JOptionPane;

import fr.afpa.cda.entities.MailEntity;
import fr.afpa.cda.jpa.MailDAOJPA;
import fr.afpa.utils.Display;

public class MailReader {

  public static void main(String[] args) {
    ConnexionManager connexionManager = new ConnexionManager();
    MailDAOJPA mailDAOJPA = new MailDAOJPA();
    
    try {
      connexionManager.connexionMail();
      List<MailEntity> listDeletedMails = connexionManager.getDeletedMails();
      for (MailEntity mail : listDeletedMails) {
        System.out.println("=> " + mail.getIdmail() + " " + mail.getSujet() + " " + mail.getExpediteur() + " "
            + mail.getDateReception() + " " + mail.getFlag());
        mailDAOJPA.create(mail);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    Display display = new Display();
    String responseMessage = "";
    String response = JOptionPane.showInputDialog("Quels mails voulez-vous consulter ? \n" +
        "1 : mails lus\n" + "2 : mails non lus\n" + "3 : mails éffacés\n" + "4 : tous les mails");

    switch (response) {
      case "1":
        List<MailEntity> listMailsRead = mailDAOJPA.findByFlag("READ");
        responseMessage = display.displayArray(listMailsRead);
        JOptionPane.showMessageDialog(null, responseMessage, "Vous avez choisi de consulter les mails lus",
            JOptionPane.INFORMATION_MESSAGE);
        break;
      case "2":
        List<MailEntity> listMailsUnread = mailDAOJPA.findByFlag("UNREAD");
        responseMessage = display.displayArray(listMailsUnread);
        JOptionPane.showMessageDialog(null, responseMessage, "Vous avez choisi de consulter les mails non lus",
            JOptionPane.INFORMATION_MESSAGE);
        break;
      case "3":
        List<MailEntity> listMailsDeleted = mailDAOJPA.findByFlag("DELETED");
        responseMessage = display.displayArray(listMailsDeleted);
        JOptionPane.showMessageDialog(null, responseMessage, "Vous avez choisi de consulter les mails éffacés",
            JOptionPane.INFORMATION_MESSAGE);
        break;
      default:
        List<MailEntity> listMailsAll = mailDAOJPA.findAll();
        responseMessage = display.displayArray(listMailsAll);
        JOptionPane.showMessageDialog(null, responseMessage, "Vous avez choisi de consulter les touts les mails ",
            JOptionPane.INFORMATION_MESSAGE);
        break;

    }

  }
}
