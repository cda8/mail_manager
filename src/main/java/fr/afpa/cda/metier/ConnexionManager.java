package fr.afpa.cda.metier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Properties;

import fr.afpa.cda.entities.MailEntity;
import fr.afpa.cda.exception.InvalidMDPException;
import fr.afpa.utils.DecodeBase64;
import fr.afpa.utils.LoadProperties;
import fr.afpa.utils.Validate;
import jakarta.mail.*;
import jakarta.mail.Flags;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.search.FlagTerm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnexionManager {
  private String mail;
  private String userPassword;
  protected static Store store;
  protected static Folder inbox;

  public ConnexionManager() {
  }

  public String getMail() {
    return mail;
  }

  public void setMail(String mail) {
    this.mail = mail;
  }

  public String getUserPassword() {
    return userPassword;
  }

  public void setUserPassword(String userPassword) {
    this.userPassword = userPassword;
  }

  public static Store getStore() {
    return store;
  }

  public static void setStore(Store store) {
    ConnexionManager.store = store;
  }

  public static Folder getInbox() {
    return inbox;
  }

  public static void setInbox(Folder inbox) {
    ConnexionManager.inbox = inbox;
  }

  public void connect() throws InvalidMDPException, IOException {
    LoadProperties loadProp = new LoadProperties();
    Properties props = loadProp.load("/mailConfig.properties");

    String userMdpEncode = props.getProperty("password");

    mail = props.getProperty("mail");
    userPassword = DecodeBase64.decodeString(userMdpEncode);
    String server = props.getProperty("server");

    try {

      if (!Validate.validationMdp(userPassword)) {
        throw new InvalidMDPException("Le mot de passe doit contenir au moins 8 caractères");
      }
      Session session = Session.getDefaultInstance(props, null);
      store = session.getStore("imaps");
      store.connect(server, mail, userPassword);

      inbox = store.getFolder("INBOX");
      inbox.open(Folder.READ_ONLY);

    } catch (NoSuchProviderException nspe) {
      nspe.getMessage();
      nspe.printStackTrace();
    } catch (MessagingException me) {
      me.getMessage();
      me.printStackTrace();
    }

  }

}
