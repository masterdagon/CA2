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
import static org.junit.Assert.assertEquals;
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
//    private Person p;
//    private Company c;

    public JUnitTest() {
        String[] args = new String[0];
        CreateTables.main(args);
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Before
    public void setup() {
        try {
            f = new Facade();
            emf = Persistence.createEntityManagerFactory("CA2PU");
            em = getEntityManager();
            System.out.println("setup running");
        } catch (Exception e) {
            System.out.println("Error in setup");

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
    public void createPerson() throws InterruptedException {
        Person p = f.CreatePerson("createPerson", "Test", "Test");
        Person p1 = em.find(Person.class, p.getId());
        assertEquals(p.getId(),p1.getId());
    }

    @Test
    public void createCompany() throws InterruptedException {
        Company c = f.createCompany("createCompany", "Test", 1, 1, 1, "Test");
        Company c1 = em.find(Company.class, c.getId());
        assertEquals(c.getId(),c1.getId());
    }

    @Test
    public void addPhonePerson() {
        Person p = f.CreatePerson("addPhonePerson", "Test", "Test");
        Person p1 = f.addPhonePerson(p, "Test", 5);
        assertEquals(5,p1.getPhones().get(0).getNumber());
    }

    @Test
    public void getPersonFromPhone() {
        Person p = f.CreatePerson("getPersonFromPhone", "Test", "Test");
        f.addPhonePerson(p, "Test", 555);
        Person pp = f.getPersonFromPhone(555);
        assertEquals(pp.getPhones().get(0).getNumber(),555);
    }
}
