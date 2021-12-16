package fr.afpa.cda.metier;


import fr.afpa.cda.exception.InvalidMDPException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;





public class ConnexionLauncher {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConnexionLauncher.class);

    public static void main(String[] args)  {


        ConnexionManager connexionManager = new ConnexionManager();





//        try{
//            connexionManager.connexionMail();
//
//
//        }catch (InvalidMDPException ex){
//           LOGGER.error(ex.getMessage());
//        }


    }

}
