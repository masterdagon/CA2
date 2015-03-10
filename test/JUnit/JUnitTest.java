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
import org.junit.AfterClass;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;
import test.CreateTables;

/**
 *
 * @author Muggi
 */
public class JUnitTest {

    private Facade f;
    static private EntityManagerFactory emf;
    static private EntityManager em;
    private Person p;
    private Company c;

    public JUnitTest() {
        f = new Facade();
        emf = Persistence.createEntityManagerFactory("CA2PU");  
    }

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @BeforeClass
    public static void setup() {
        String[] args = new String[0];
        CreateTables.main(args);
        try {
            em = getEntityManager();
        } catch (Exception e) {
            System.out.println("Error in creating entity manager");

        }
    }

    @AfterClass
    public static void teardown() {
        try {
            em.close();
        } catch (Exception e) {
            System.out.println("Error in closing entity manager");
        }

    }

    @Test
    public void createPerson() {
        Person p = f.CreatePerson("Test", "Test", "Test");
        assertTrue(1 == p.getId());
    }

    @Test
    public void createCompany() {
        Company c = f.createCompany("Test", "Test", 1, 1, 1, "Test");
        assertTrue(1 == c.getId());
    }
//
//    @Test
//    public void addPhonePerson() {
//        f.addPhonePerson(p, "Test", 5);
//        p = em.find(Person.class, 1);
//        assertTrue(5==p.getPhones().get(0).getNumber());
//    }
//
//    @Test
//    public void getPersonFromPhone() {
//        f.addPhonePerson(p, "Test", 5);
//        Person pp = f.getPersonFromPhone(1);
//        assertTrue(1==pp.getId());
//    }

}
