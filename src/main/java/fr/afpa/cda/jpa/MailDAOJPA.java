package fr.afpa.cda.jpa;

import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.transaction.Transactional;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

import fr.afpa.cda.entities.MailEntity;

public class MailDAOJPA {

  private EntityManager entityManager;
  private EntityTransaction transaction;

  public MailDAOJPA(EntityManager entityManager) {
    this.entityManager = entityManager;
    this.transaction = entityManager.getTransaction();
  }

  public void closeEntityManager() {
    entityManager.close();
  }

  // @Transactional(dontRollbackOn=Exception.class)
  public void create(MailEntity mail) {
    transaction.begin();
    entityManager.persist(mail);
    transaction.commit();
  }

  // save les mails
  public void saveListMails(List<MailEntity> mails) {
    transaction.begin();
    for (MailEntity mail : mails) {
      entityManager.persist(mail);
    }
    transaction.commit();
  }

  // return le corps d’un mail selon son id
  public String getCorpsMail(int idmail) {
    MailEntity mail = entityManager.find(MailEntity.class, idmail);
    return mail.getCorps();
  }

  // consulter tous les mails
  public List<MailEntity> findAll() {
    return entityManager.createQuery("SELECT m FROM mails m", MailEntity.class).getResultList();
  }

  // consulter les mails selon leur flag
  public List<MailEntity> findByFlag(String flag) {
    return entityManager.createQuery("SELECT m FROM mails m WHERE m.flag = :flag", MailEntity.class)
        .setParameter("flag", flag).getResultList();
  }

  // supprimer tous les mails
  public void deleteAll() {
    transaction.begin();
    entityManager.createQuery("DELETE FROM mails m").executeUpdate();
    transaction.commit();
  }

  // send mail with jakarta mail
  public void sendMail() {
    String hostname = "smtp.laposte.net";
    final String username = "formationcda@laposte.net";
    final String password = "FormationAfpa2021!";

    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", hostname);
    props.put("mail.smtp.port", "587");
    props.put("mail.debug", "true");

    javax.mail.Authenticator auth = new javax.mail.Authenticator() {
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
      }
    };

    Session session = Session.getInstance(props, auth);

    try {
      MimeMessage msg = new MimeMessage(session);
      msg.setFrom(new InternetAddress("from@example.com"));
      InternetAddress[] address = { new InternetAddress("to@example.com") };
      msg.setRecipients(Message.RecipientType.TO, address);
      msg.setSubject("Jakarta Mail APIs Test");
      msg.addHeader("x-cloudmta-class", "standard");
      msg.addHeader("x-cloudmta-tags", "demo, example");
      msg.setText("Test Message Content");

      Transport.send(msg);

      System.out.println("Message Sent.");
    } catch (javax.mail.MessagingException ex) {
      throw new RuntimeException(ex);
    }
  }

}
