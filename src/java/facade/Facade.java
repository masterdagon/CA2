/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Company;
import entity.Person;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author Muggi
 */
public class Facade {

    private EntityManagerFactory emf;

    public Facade() {
        emf = Persistence.createEntityManagerFactory("CA2PU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Person createPerson(String fName, String lName, String email) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            Person p = new Person(fName,lName);
            p.setEmail(email);
            em.getTransaction().begin();
            em.persist(p);
            em.getTransaction().commit();
            return p;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Person getPerson(int id) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            Person p = em.find(Person.class, id);
            return p;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public List<Person> getAllPersons() {
        EntityManager em = null;
        try {
            em = getEntityManager();
            TypedQuery<Person> q = em.createQuery("select p from Person p", Person.class);
            return q.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public Company createCompany(String name, String description, int cvr, int NumEmployees, int marketValue, String email) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            Company c = new Company(name,description,cvr,NumEmployees,marketValue);
            c.setEmail(email);
            em.getTransaction().begin();
            em.persist(c);
            em.getTransaction().commit();
            return c;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public List<Company> getAllCompanies() {
        EntityManager em = null;
        try {
            em = getEntityManager();
            TypedQuery<Company> q = em.createQuery("select c from Company c", Company.class);
//            q.setParameter("p", "Company");
            return q.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public Company getCompany(int id) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            Company c = em.find(Company.class, id);
            return c;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

}
