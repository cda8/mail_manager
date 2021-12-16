package fr.afpa.cda.exception;

public class InvalidMDPException extends Exception{


    public InvalidMDPException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return " Le mot de passe est invalide ";
    }
}
