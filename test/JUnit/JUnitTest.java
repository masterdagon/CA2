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
        assertEquals(p.getId(), p1.getId());
    }

    @Test
    public void createCompany() throws InterruptedException {
        Company c = f.createCompany("createCompany", "Test", 1, 1, 1, "Test");
        Company c1 = em.find(Company.class, c.getId());
        assertEquals(c.getId(), c1.getId());
    }

    @Test
    public void addPhonePerson() {
        Person p = f.CreatePerson("addPhonePerson", "Test", "Test");
        Person p1 = f.addPhonePerson(p, "addPhonePerson", 5);
        assertEquals(5, p1.getPhones().get(0).getNumber());
    }

    @Test
    public void getPersonFromPhone() {
        Person p = f.CreatePerson("getPersonFromPhone", "Test", "Test");
        f.addPhonePerson(p, "getPersonFromPhone", 555);
        Person pp = f.getPersonFromPhone(555);
        assertEquals(pp.getPhones().get(0).getNumber(), 555);
    }

    @Test
    public void getCompanyFromPhone() {
        Company c = f.createCompany("getCompanyFromPhone", "test", 1, 1, 1, "Test");
        f.addPhoneCompany(c, "getCompanyFromPhone", 77);
        Company cc = f.getCompanyFromPhone(77);
        assertEquals(c.getPhones().get(0).getNumber(), cc.getPhones().get(0).getNumber());
    }

    @Test
    public void getCompanyFromcvr() {
    }

    @Test
    public void getAllPersonsWithHobby() {
    }

    @Test
    public void getAllPersonsInCity() {
        Person p = f.CreatePerson("getAllPersonsInCity", "test", "test");
        p = f.createAddressForPerson(p, "test", "test", 3390);
        Person p1 = f.CreatePerson("getAllPersonsInCity", "test", "test");
        p1 = f.createAddressForPerson(p1, "test", "test", 3390);
        Person p2 = f.CreatePerson("getAllPersonsInCity", "test", "test");
        p2 = f.createAddressForPerson(p2, "test", "test", 3390);
        int exp = 3;
        int result = f.getAllPersonsInCity(3390).size();
        assertEquals(exp,result);
    }

    @Test
    public void getCountOfPeopleWithHobby() {
    }

    @Test
    public void getListOfZipCodes() {
        int excp = 1348;
        int size = f.getListOfZipCodes().size();
        assertEquals(excp,size);
    }

    @Test
    public void getListOfCompaniesWithXEmployes() {
    }

    @Test
    public void addPhoneCompany() {
    }

    @Test
    public void createAddressForPerson() {
        Person p = f.CreatePerson("createAddressForPerson", "test", "test");
        p = f.createAddressForPerson(p, "street", "a", 3300);
        Person p1 = em.find(Person.class, p.getId());
        System.out.println("er null ="+p.getAddress()==null);
        assertEquals(p.getAddress().getId(),p1.getAddress().getId());
    }

    @Test
    public void createAddressForCompany() {
        Company c = f.createCompany("createAddressForCompany", "test",0,0,0, "test");
        c = f.createAddressForCompany(c, "street", "a", 3300);
        Person c1 = em.find(Person.class, c.getId());
        assertEquals(c.getAddress().getId(),c1.getAddress().getId());
    }

    @Test
    public void createHobbies() {
    }

    @Test
    public void deletePerson() {
    }

    @Test
    public void deleteCompany() {
    }

    @Test
    public void deletePersonPhone() {
    }

    @Test
    public void deleteCompanyPhone() {
    }

    @Test
    public void changeAddressFromPerson() {
    }

    @Test
    public void changeAddressFromCompany() {
    }

    @Test
    public void deleteAddress() {
    }

    @Test
    public void removeHobbyFromPerson() {
    }

    @Test
    public void deleteHobbyFromDB() {
    }

    @Test
    public void getAllPersons() {
    }

    @Test
    public void getAllCompanies() {
    }

    @Test
    public void getCompany() {
    }
}
