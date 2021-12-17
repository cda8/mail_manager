package fr.afpa.cda.metier;

import javax.swing.JOptionPane;

public class MailReader {

  public static void main(String[] args) {

    String response = JOptionPane.showInputDialog("Quels mails voulez-vous consulter ? \n" +
        "1 : mails lus\n" + "2 : mails non lus\n" + "3 : mails éffacés\n" + "4 : tous les mails");
    System.out.println(response);
  }
}
