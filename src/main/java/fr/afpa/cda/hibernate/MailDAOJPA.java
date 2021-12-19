package fr.afpa.cda.hibernate;

import fr.afpa.cda.metier.LocalMail;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class MailDAOJPA {

    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
    private static EntityManager entityManager = entityManagerFactory.createEntityManager();

    public void beginTransaction(){
        entityManager.getTransaction().begin();
    }

    public void commitTransaction(){
        entityManager.getTransaction().commit();
    }


    public List<LocalMail> selectAll(){

        return entityManager.createQuery("from mail", LocalMail.class).getResultList();


    }

    public void createOrUpdate(LocalMail mail){
        beginTransaction();
        entityManager.persist(mail);
        commitTransaction();
    }

    public void remove(LocalMail mail){

        entityManager.remove(mail);
    }

    public List<LocalMail> selectByFlag(String flag){
        List<LocalMail> resultList = new ArrayList<>();
        List<LocalMail> localMailstemp = selectAll();
        for (LocalMail m : localMailstemp){
            if(m.getFlag().equalsIgnoreCase(flag)){
                resultList.add(m);
            }
        }

        return resultList;
    }




}
