package fr.afpa.cda;

import fr.afpa.cda.metier.ConnexionManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnexionLauncher {

    public static void main(String[] args) {
        ConnexionManager connexion = new ConnexionManager();

        final Logger fLOGGER = LoggerFactory.getLogger(ConnexionLauncher.class);
        try {
            connexion.connexionMail();
        } catch (final Exception e) {
            fLOGGER.error(e.getMessage(), e);
        }
    }
}