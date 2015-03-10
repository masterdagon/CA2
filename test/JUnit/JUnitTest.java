/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JUnit;

import entity.Company;
import entity.Person;
import facade.Facade;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import test.CreateTables;

/**
 *
 * @author Muggi
 */
public class JUnitTest {

    private Facade f;
    private EntityManagerFactory emf;
    private EntityManager em;
    private Person p;
    private Company c;

    public JUnitTest() {
        f = new Facade();
        emf = Persistence.createEntityManagerFactory("CA2PU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Before
    public void setup() {
        String[] args = new String[0];
        CreateTables.main(args);
        p = f.CreatePerson("Test", "Test", "Test");
        c = f.createCompany("Test", "Test", 1, 1, 1, "Test");
        try {
            em = getEntityManager();
        } catch (Exception e) {
            System.out.println("Error in creating entity manager");

        }
    }

    @After
    public void teardown() {
        try {
            em.close();
        } catch (Exception e) {
            System.out.println("Error in closing entity manager");
        }

    }

    @Test
    public void createPerson() {
        p = f.CreatePerson("Test2", "Test2", "Test2");
        assertTrue(2 == p.getId());
    }

    @Test
    public void createCompany() {
        c = f.createCompany("Test2", "Test2", 1, 1, 1, "Test2");
        assertTrue(2 == c.getId());
    }

    @Test
    public void addPhonePerson() {
        f.addPhonePerson(p, "Test", 5);
        p = em.find(Person.class, 1);
        assertTrue(5==p.getPhones().get(0).getNumber());
    }

    @Test
    public void getPersonFromPhone() {
        f.addPhonePerson(p, "Test", 5);
        Person pp = f.getPersonFromPhone(1);
        assertTrue(1==pp.getId());
    }

}
