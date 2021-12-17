package fr.afpa.cda.hibernate;

import fr.afpa.cda.metier.LocalMail;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class MailDAOJPA {

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
    EntityManager entityManager = entityManagerFactory.createEntityManager();



    public List<LocalMail> selectAll(){

        return entityManager.createQuery("from mail", LocalMail.class).getResultList();


    }

    public void createOrUpdate(LocalMail mail){

        entityManager.persist(mail);

    }

    public void remove(LocalMail mail){

        entityManager.remove(mail);
    }

    public List<LocalMail> selectByFlag(String flag){
        List<LocalMail> localMailstemp = new ArrayList<>();
        List<LocalMail> resultList = new ArrayList<>();
        localMailstemp = selectAll();
        for (LocalMail m : localMailstemp){
            if(m.getFlag().equalsIgnoreCase(flag)){
                resultList.add(m);
            }
        }

        return resultList;
    }




}
