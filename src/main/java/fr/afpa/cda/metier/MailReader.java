package fr.afpa.cda.metier;

import fr.afpa.cda.exception.InvalidMDPException;

import java.util.Scanner;

public class MailReader {

    public static void main(String[] args) throws InvalidMDPException {
    ConnexionManager connexionManager = new ConnexionManager();
//        int userChoise = makeYourChoise();

        connexionManager.connexionMail(0);

    }

    private static int makeYourChoise() {
        Scanner scanner = new Scanner(System.in);
       System.out.println("Quels mails voulez-vous consulter ? \n" +
               "1 : mails lus, 2 : mails non lus, 3  mails effacés, 4 tous les mails");
        return  scanner.nextInt();
    }

}
