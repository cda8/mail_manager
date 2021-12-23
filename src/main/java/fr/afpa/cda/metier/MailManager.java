package fr.afpa.cda.metier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.afpa.cda.entities.MailEntity;

import jakarta.mail.BodyPart;
import jakarta.mail.Flags;
import jakarta.mail.Folder;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Store;

import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.search.FlagTerm;

public class MailManager {
  private Folder inbox;
  private Store store;
  final Logger fLOGGER = LoggerFactory.getLogger(MailManager.class);

  public MailManager() {
    inbox = ConnexionManager.getInbox();
    store = ConnexionManager.getStore();
  }

  public List<MailEntity> getAllMails() {
    List<MailEntity> listeMailEntity = new ArrayList<>();
    Message messages[];
    try {
      messages = inbox.getMessages();
      for (Message message : messages) {
        MailEntity mail = new MailEntity();
        mail.setSujet(message.getSubject());
        mail.setExpediteur(message.getFrom()[0].toString());
        mail.setDateReception(message.getSentDate().toString());
        mail.setFlag(message.getFlags().contains(Flags.Flag.SEEN) ? EFlags.READ : EFlags.UNREAD);
        listeMailEntity.add(mail);
      }
    } catch (MessagingException me) {
      fLOGGER.error(me.getMessage());
      fLOGGER.error(Arrays.toString(me.getStackTrace()));
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
        Mail mail = new Mail();
        mail.setSujet(m.getSubject());
        mail.setCorps(getTextFromMessage(m));
        mail.setExpediteur(m.getFrom()[0].toString());
        mail.setDateReception(m.getReceivedDate().toString());
        mail.setFlag(EFlags.READ);
        listeMailsRead.add(mail);
      }
    } catch (MessagingException me) {
      fLOGGER.error(me.getMessage());
      fLOGGER.error(Arrays.toString(me.getStackTrace()));
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
        Mail mail = new Mail();
        mail.setSujet(m.getSubject());
        mail.setCorps(getTextFromMessage(m));
        mail.setExpediteur(m.getFrom()[0].toString());
        mail.setDateReception(m.getReceivedDate().toString());
        mail.setFlag(EFlags.UNREAD);
        listeMailsUnread.add(mail);
      }
    } catch (MessagingException me) {
      fLOGGER.error(me.getMessage());
      fLOGGER.error(Arrays.toString(me.getStackTrace()));
    }
    return listeMailsUnread;
  }

  public List<Mail> getDeletedMails() {
    List<Mail> listeMailsDeleted = new ArrayList<>();
    try {
      inbox = store.getFolder("INBOX/TRASH");
      inbox.open(Folder.READ_ONLY);
      Message[] data = inbox.getMessages();
      for (Message m : data) {
        Mail mail = new Mail();
        mail.setSujet(m.getSubject());
        mail.setCorps(getTextFromMessage(m));
        mail.setExpediteur(m.getFrom()[0].toString());
        mail.setDateReception(m.getReceivedDate().toString());
        mail.setFlag(EFlags.DELETED);
        listeMailsDeleted.add(mail);
      }
    } catch (MessagingException me) {
      fLOGGER.error(me.getMessage());
      fLOGGER.error(Arrays.toString(me.getStackTrace()));
    }
    return listeMailsDeleted;
  }

  public void getFolderNames() {
    Folder[] folders;
    try {
      folders = store.getDefaultFolder().list("*");
      for (Folder folder : folders) {
        if ((folder.getType() & Folder.HOLDS_MESSAGES) != 0) {
          System.out.println(folder.getFullName() + ": " + folder.getMessageCount());
        }
      }
    } catch (MessagingException me) {
      fLOGGER.error(me.getMessage());
      fLOGGER.error(Arrays.toString(me.getStackTrace()));
    }
  }

  private String getTextFromMessage(Message message) {
    StringBuilder result = new StringBuilder();
    try {
      if (message.isMimeType("text/plain")) {
        result.append(message.getContent().toString());
      } else if (message.isMimeType("multipart/*")) {
        MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
        result.append(getTextFromMimeMultipart(mimeMultipart));
      }
    } catch (MessagingException | IOException e) {
      fLOGGER.error(e.getMessage());
      fLOGGER.error(Arrays.toString(e.getStackTrace()));
    }
    return result.toString();
  }

  private String getTextFromMimeMultipart(
      MimeMultipart mimeMultipart) {
    StringBuilder result = new StringBuilder();

    int count;
    try {
      count = mimeMultipart.getCount();
      for (int i = 0; i < count; i++) {
        BodyPart bodyPart = mimeMultipart.getBodyPart(i);
        if (bodyPart.isMimeType("text/plain")) {
          result.append("\n" + bodyPart.getContent());
          break; // with out break same text appears twice in my tests
        } else if (bodyPart.isMimeType("text/html")) {
          String html = (String) bodyPart.getContent();
          result.append("\n" + org.jsoup.Jsoup.parse(html).text());
        } else if (bodyPart.getContent() instanceof MimeMultipart) {
          result.append(getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent()));
        }
      }
    } catch (MessagingException | IOException e) {
      fLOGGER.error(e.getMessage());
      fLOGGER.error(Arrays.toString(e.getStackTrace()));
    }
    return result.toString();
  }

}
