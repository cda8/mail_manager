package fr.afpa.cda.metier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Properties;

import fr.afpa.cda.exception.InvalidMDPException;
import fr.afpa.utils.LoadProperties;
import jakarta.mail.*;
import jakarta.mail.Flags;
import jakarta.mail.Flags.Flag;
import jakarta.mail.search.FlagTerm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnexionManager {
  private String mail;
  private String userPassword;
  protected static Store store;
  private List<LocalMail> listeMails;
  protected static Folder inbox;
  final Logger fLOGGER = LoggerFactory.getLogger(ConnexionManager.class);

  public ConnexionManager() {
    listeMails = new ArrayList<>();
  }

  public String getUserMail() {
    return mail;
  }

  public void setUserMail(String userMail) {
    this.mail = userMail;
  }

  public String getUserMDP() {
    return userPassword;
  }

  public void setUserMDP(String userMDP) {
    this.userPassword = userMDP;
  }

  /**
   *
   * @param mdp
   * @return
   */
  public boolean validationMdp(String mdp) {
    return mdp.trim().length() >= 8;

  }

  public void connexionMail() throws InvalidMDPException, IOException {
    LoadProperties loadProp = new LoadProperties();
    Properties props = loadProp.load("/mailConfig.properties");

    String userMdpEncode = props.getProperty("password");
    byte[] userMdpDecode = Base64.getDecoder().decode(userMdpEncode);

    mail = props.getProperty("mail");
    userPassword = new String(userMdpDecode);
    String server = props.getProperty("server");

    try {

      if (!this.validationMdp(userPassword)) {
        throw new InvalidMDPException("Le mot de passe doit contenir au moins 8 caractères");
      }

      Session session = Session.getDefaultInstance(props, null);
      store = session.getStore("imaps");

      store.connect(server, mail, userPassword);
      inbox = store.getFolder("inbox");
      inbox.open(Folder.READ_ONLY);

      Message[] liste = inbox.getMessages();
      for (Message m : liste) {
        fLOGGER.info("From: " + m.getFrom()[0].toString());
        fLOGGER.info("Subject: " + m.getSubject());
        fLOGGER.info("Content: " + m.getContent());
      }
      fLOGGER.info(server, Flags.Flag.SEEN);
      fLOGGER.info("Total mails : " + inbox.getMessageCount());

    } catch (NoSuchProviderException nspe) {
      nspe.getMessage();
      nspe.printStackTrace();
    } catch (MessagingException me) {
      me.getMessage();
      me.printStackTrace();
    }

  }

  public void getReadMails() {
    Flags seenFlag = new Flags(Flags.Flag.SEEN);
    FlagTerm seenFlagTerm = new FlagTerm(seenFlag, true);
    try {
      Message[] data = inbox.search(seenFlagTerm);
      for (Message m : data) {
        LocalMail localMail = new LocalMail();
        localMail.setSujet(m.getSubject());
        localMail.setExpediteur(m.getFrom()[0].toString());
        localMail.setDateReception(m.getReceivedDate());
        localMail.setFlag(AppFlags.READ);
        listeMails.add(localMail);
      }
    } catch (MessagingException me) {
      me.printStackTrace();
    }
  }

  public void getUnreadMails() {
    Flags seenFlag = new Flags(Flags.Flag.SEEN);
    FlagTerm seenFlagTerm = new FlagTerm(seenFlag, false);
    try {
      Message[] data = inbox.search(seenFlagTerm);
      for (Message m : data) {
        LocalMail localMail = new LocalMail();
        localMail.setSujet(m.getSubject());
        localMail.setExpediteur(m.getFrom()[0].toString());
        localMail.setDateReception(m.getReceivedDate());
        localMail.setFlag(AppFlags.UNREAD);
        listeMails.add(localMail);
      }
    } catch (MessagingException me) {
      me.printStackTrace();
    }
  }

  public void getDeletedMails() {
    Flags seenFlag = new Flags(Flags.Flag.DELETED);
    FlagTerm seenFlagTerm = new FlagTerm(seenFlag, true);
    try {
      Message[] data = inbox.search(seenFlagTerm);
      for (Message m : data) {
        LocalMail localMail = new LocalMail();
        localMail.setSujet(m.getSubject());
        localMail.setExpediteur(m.getFrom()[0].toString());
        localMail.setDateReception(m.getReceivedDate());
        localMail.setFlag(AppFlags.DELETED);
        listeMails.add(localMail);
      }
    } catch (MessagingException me) {
      me.printStackTrace();
    }
  }
}
