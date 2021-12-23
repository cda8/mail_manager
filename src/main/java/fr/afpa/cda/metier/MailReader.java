package fr.afpa.cda.metier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailReader {

  public static void main(String[] args) {
    // final Logger LOGGER = LoggerFactory.getLogger(MailReader.class);
    // CheckDB.checkDBAndUpdateIfNeed();
    CreateMail.sendMail();

    
    // Display display = new Display();
    // String responseMessage = "";
    // String response = JOptionPane.showInputDialog("Quels mails voulez-vous
    // consulter ? \n" +
    // "1 : mails lus\n" + "2 : mails non lus\n" + "3 : mails éffacés\n" + "4 : tous
    // les mails");

    // switch (response) {
    // case "1":
    // List<MailEntity> listMailsRead = mailDAOJPA.findByFlag("READ");
    // responseMessage = display.displayArray(listMailsRead);
    // JOptionPane.showMessageDialog(null, responseMessage, "Vous avez choisi de
    // consulter les mails lus",
    // JOptionPane.INFORMATION_MESSAGE);
    // break;
    // case "2":
    // List<MailEntity> listMailsUnread = mailDAOJPA.findByFlag("UNREAD");
    // responseMessage = display.displayArray(listMailsUnread);
    // JOptionPane.showMessageDialog(null, responseMessage, "Vous avez choisi de
    // consulter les mails non lus",
    // JOptionPane.INFORMATION_MESSAGE);
    // break;
    // case "3":
    // List<MailEntity> listMailsDeleted = mailDAOJPA.findByFlag("DELETED");
    // responseMessage = display.displayArray(listMailsDeleted);
    // JOptionPane.showMessageDialog(null, responseMessage, "Vous avez choisi de
    // consulter les mails éffacés",
    // JOptionPane.INFORMATION_MESSAGE);
    // break;
    // default:
    // List<MailEntity> listMailsAll = mailDAOJPA.findAll();
    // responseMessage = display.displayArray(listMailsAll);
    // JOptionPane.showMessageDialog(null, responseMessage, "Vous avez choisi de
    // consulter les touts les mails ",
    // JOptionPane.INFORMATION_MESSAGE);
    // break;

    // }

  }
}
