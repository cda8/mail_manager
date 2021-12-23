package fr.afpa.utils;

import java.util.Base64;

public class DecodeBase64 {
  public static String decodeString(String encodedString) {
    byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
    return new String(decodedBytes);
  }
}
