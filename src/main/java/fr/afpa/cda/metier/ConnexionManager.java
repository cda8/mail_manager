package fr.afpa.cda.metier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Properties;

import fr.afpa.cda.entities.MailEntity;
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
  private List<Mail> listeMails;
  protected static Folder inbox;
  final Logger fLOGGER = LoggerFactory.getLogger(ConnexionManager.class);

  public ConnexionManager() {
    listeMails = new ArrayList<>();
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

  public List<MailEntity> getAllMails() throws MessagingException {
    Message messages[] = inbox.getMessages();
    List<MailEntity> listeMailEntity = new ArrayList<>();
    for (Message message : messages) {
      MailEntity mail = new MailEntity();
      mail.setSujet(message.getSubject());
      mail.setExpediteur(message.getFrom()[0].toString());
      mail.setDateReception(message.getSentDate().toString());
      if (message.getFlags().contains(Flags.Flag.DELETED)) {
        mail.setFlag(AppFlags.DELETED);
      } else {
        mail.setFlag(message.getFlags().contains(Flags.Flag.SEEN) ? AppFlags.READ : AppFlags.UNREAD);
      }
      listeMailEntity.add(mail);
    }
    return listeMailEntity;
  }

  public List<Mail> getReadMails() {
    Flags seenFlag = new Flags(Flags.Flag.SEEN);
    FlagTerm seenFlagTerm = new FlagTerm(seenFlag, true);
    List<Mail> listeMailsRead = new ArrayList<>();
    try {
      Message[] data = inbox.search(seenFlagTerm);
      for (Message m : data) {
        Mail localMail = new Mail();
        localMail.setSujet(m.getSubject());
        localMail.setExpediteur(m.getFrom()[0].toString());
        localMail.setDateReception(m.getReceivedDate());
        localMail.setFlag(AppFlags.READ);
        listeMailsRead.add(localMail);
      }
    } catch (MessagingException me) {
      me.printStackTrace();
    }
    return listeMailsRead;
  }

  public List<Mail> getUnreadMails() {
    Flags seenFlag = new Flags(Flags.Flag.SEEN);
    FlagTerm seenFlagTerm = new FlagTerm(seenFlag, false);
    List<Mail> listeMailsUnread = new ArrayList<>();
    try {
      Message[] data = inbox.search(seenFlagTerm);
      for (Message m : data) {
        Mail localMail = new Mail();
        localMail.setSujet(m.getSubject());
        localMail.setExpediteur(m.getFrom()[0].toString());
        localMail.setDateReception(m.getReceivedDate());
        localMail.setFlag(AppFlags.UNREAD);
        listeMailsUnread.add(localMail);
      }
    } catch (MessagingException me) {
      me.printStackTrace();
    }
    return listeMailsUnread;
  }

  public List<MailEntity> getDeletedMails() {
    List<MailEntity> listeMailsDeleted = new ArrayList<>();
    try {
      inbox = store.getFolder("INBOX/TRASH");
      inbox.open(Folder.READ_ONLY);
      Message[] data = inbox.getMessages();
      for (Message m : data) {
        MailEntity localMail = new MailEntity();
        localMail.setSujet(m.getSubject());
        localMail.setExpediteur(m.getFrom()[0].toString());
        localMail.setDateReception(m.getReceivedDate().toString());
        localMail.setFlag(AppFlags.DELETED);
        listeMailsDeleted.add(localMail);
      }
    } catch (MessagingException me) {
      me.printStackTrace();
    }
    return listeMailsDeleted;
  }

  public void getFolderNames() throws MessagingException {
    Folder[] folders = store.getDefaultFolder().list("*");
    for (Folder folder : folders) {
      if ((folder.getType() & Folder.HOLDS_MESSAGES) != 0) {
        System.out.println(folder.getFullName() + ": " + folder.getMessageCount());
      }
    }
  }

}
