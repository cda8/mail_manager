package fr.afpa.cda.hibernate;

import fr.afpa.cda.metier.LocalMail;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class MailDAOJPA {

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
    EntityManager entityManager = entityManagerFactory.createEntityManager();



    public List<LocalMail> selectAll(){

        return entityManager.createQuery("from mail", LocalMail.class).getResultList();


    }



}
