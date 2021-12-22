package fr.afpa.cda.mapper;

import java.util.ArrayList;
import java.util.List;

import fr.afpa.cda.entities.MailEntity;
import fr.afpa.cda.metier.EFlags;
import fr.afpa.cda.metier.Mail;

public class MailMapper {

  public static Mail mapMailEntityToMail(MailEntity mailEntity) {
    Mail mail = new Mail();
    mail.setId(mailEntity.getIdmail());
    mail.setSujet(mailEntity.getSujet());
    mail.setCorps(mailEntity.getCorps());
    mail.setExpediteur(mailEntity.getExpediteur());
    mail.setDateReception(mailEntity.getDateReception());
    switch (mailEntity.getFlag()) {
      case "DELETED":
        mail.setFlag(EFlags.DELETED);
        break;
      case "READ":
        mail.setFlag(EFlags.READ);
        break;
      default:
        mail.setFlag(EFlags.UNREAD);
        break;
    }
    return mail;
  }

  public static List<Mail> mapMailEntitiesToMails(List<MailEntity> mailEntities) {
    List<Mail> listMails = new ArrayList<>();
    for (MailEntity mailEntity : mailEntities) {
      listMails.add(mapMailEntityToMail(mailEntity));
    }
    return listMails;
  }

  public static MailEntity mapMailToMailEntity(Mail mail) {
    MailEntity mailEntity = new MailEntity();
    mailEntity.setSujet(mail.getSujet());
    mailEntity.setCorps(mail.getCorps());
    mailEntity.setExpediteur(mail.getExpediteur());
    mailEntity.setDateReception(mail.getDateReception());
    switch (mail.getFlag()) {
      case DELETED:
        mailEntity.setFlag(EFlags.DELETED);
        break;
      case READ:
        mailEntity.setFlag(EFlags.READ);
        break;
      default:
        mailEntity.setFlag(EFlags.UNREAD);
        break;
    }
    return mailEntity;
  }

  public static List<MailEntity> mapMailsToMailEntities(List<Mail> mails) {
    List<MailEntity> listMailEntities = new ArrayList<>();
    for (Mail mail : mails) {
      listMailEntities.add(mapMailToMailEntity(mail));
    }
    return listMailEntities;
  }

}
