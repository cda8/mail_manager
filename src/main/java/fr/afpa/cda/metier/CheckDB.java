package fr.afpa.cda.metier;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.afpa.cda.entities.MailEntity;
import fr.afpa.cda.exception.InvalidMDPException;
import fr.afpa.cda.jpa.DaoFactory;
import fr.afpa.cda.jpa.MailDAOJPA;
import fr.afpa.cda.mapper.MailMapper;

public class CheckDB {

  public static void checkDBAndUpdateIfNeed() {
    ConnexionManager connexionManager = new ConnexionManager();
    DaoFactory daoFactory = DaoFactory.getInstance();
    MailDAOJPA mailDAOJPA = daoFactory.getMailDAO();
    final Logger fLOGGER = LoggerFactory.getLogger(MailReader.class);

    try {
      connexionManager.connect();
    } catch (InvalidMDPException | IOException e) {
      fLOGGER.error(null, e);
      fLOGGER.error(Array.get(e.getStackTrace(), 0).toString());
    }

    MailManager mailManager = new MailManager();
    List<MailEntity> listDBReadMails = mailDAOJPA.findByFlag("read");
    List<MailEntity> listDBUnreadMails = mailDAOJPA.findByFlag("unread");
    List<MailEntity> listDBDeletedMails = mailDAOJPA.findByFlag("deleted");
    List<Mail> listReadMails = mailManager.getReadMails();
    List<Mail> listUnreadMails = mailManager.getUnreadMails();
    List<Mail> listDeletedMails = mailManager.getDeletedMails();

    if (listReadMails.size() != listDBReadMails.size()) {
      for (Mail mailRead : listReadMails) {
        MailEntity mailEntity = MailMapper.mapMailToMailEntity(mailRead);
        mailDAOJPA.create(mailEntity);
      }
    }

    if (listUnreadMails.size() != listDBUnreadMails.size()) {
      for (Mail mailUnread : listUnreadMails) {
        MailEntity mailEntity = MailMapper.mapMailToMailEntity(mailUnread);
        mailDAOJPA.create(mailEntity);
      }
    }

    if (listDeletedMails.size() != listDBDeletedMails.size()) {
      for (Mail mailDeleted : listDeletedMails) {
        MailEntity mailEntity = MailMapper.mapMailToMailEntity(mailDeleted);
        mailDAOJPA.create(mailEntity);
      }
    }

  }
}
