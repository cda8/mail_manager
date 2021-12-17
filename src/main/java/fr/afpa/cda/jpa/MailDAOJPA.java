package fr.afpa.cda.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import fr.afpa.cda.entities.MailEntity;

public class MailDAOJPA {
  private EntityManagerFactory factory = Persistence.createEntityManagerFactory("persistence");
  private EntityManager entityManager = factory.createEntityManager();
  private EntityTransaction transaction = entityManager.getTransaction();

  public void closeEntityManager() {
    entityManager.close();
  }

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

  // consulter le corps d’un mail selon son id
  public MailEntity findById(int id) {
    // manque le corps de mail ?
    return entityManager.find(MailEntity.class, id);
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
}
