package fr.afpa.utils;

public class Validate {
  
  public static boolean validationMdp(String mdp) {
    return mdp.trim().length() >= 8;

  }
}
