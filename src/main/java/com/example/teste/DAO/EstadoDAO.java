package com.example.teste.DAO;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.util.List;

import com.example.teste.Entidade.Estado;

import org.springframework.beans.factory.annotation.Autowired;

public class EstadoDAO implements Serializable{

    private static final long serialVersionUID = 1L;
    private static EstadoDAO instance;
     
    @PersistenceContext
    private EntityManager entityManager;

    public static EstadoDAO getInstance(){
      if (instance == null){
         instance = new EstadoDAO();
      }
       
      return instance;
    }

    @Transactional
    protected EntityManager getEntityManager() {
        return entityManager;
    }


    public Estado getById(final int id) {
      return getEntityManager().find(Estado.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<Estado> findAll() {
      return getEntityManager().createQuery("FROM " + 
      Estado.class.getName()).getResultList();
    }

    public void persist(Estado estado) {
      try {
         getEntityManager().getTransaction().begin();
         getEntityManager().persist(estado);
         getEntityManager().getTransaction().commit();
      } catch (Exception ex) {
         ex.printStackTrace();
         getEntityManager().getTransaction().rollback();
      }
    }

    public void merge(Estado estado) {
      try {
         getEntityManager().getTransaction().begin();
         getEntityManager().merge(estado);
         getEntityManager().getTransaction().commit();
      } catch (Exception ex) {
         ex.printStackTrace();
         getEntityManager().getTransaction().rollback();
      }
    }

    public void remove(Estado estado) {
      try {
         getEntityManager().getTransaction().begin();
         estado = getEntityManager().find(Estado.class, estado.getId());
         getEntityManager().remove(estado);
         getEntityManager().getTransaction().commit();
      } catch (Exception ex) {
         ex.printStackTrace();
         getEntityManager().getTransaction().rollback();
      }
    }

    public void removeById(final int id) {
      try {
        Estado estado = getById(id);
         remove(estado);
      } catch (Exception ex) {
         ex.printStackTrace();
      }
    }
}