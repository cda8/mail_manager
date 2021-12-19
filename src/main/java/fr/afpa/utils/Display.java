package fr.afpa.utils;

import java.util.List;

public class Display {
  public <T> String displayArray(List<T> items) {
    StringBuilder sb = new StringBuilder();
    for (Object item : items) {
      sb.append(item.toString()).append("\n");
    }
    return sb.toString();
  }

}
