package fr.afpa.utils;

import java.io.IOException;
import java.util.Properties;

public class LoadProperties {

  public LoadProperties() {
  }

  public Properties load(String fileName) throws IOException {
    Properties prop = new Properties();

    prop.load(getClass().getResourceAsStream(fileName));
    return prop;

  }
}
