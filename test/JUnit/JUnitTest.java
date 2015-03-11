/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JUnit;

import entity.Company;
import entity.Hobby;
import entity.Person;
import facade.Facade;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
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
        //method not made yet
    }

    @Test
    public void getAllPersonsWithHobby() {
        Person p = f.CreatePerson("getAllPersonsWithHobby", "test", "test");
        Hobby h = f.createHobbies("getAllPersonsWithHobby", "test");
        p = f.addHobbyToPerson(p, h);
        List<Person> listp = f.getAllPersonsWithHobby(h);
        p = em.find(Person.class, p.getId());
        assertEquals(p.getId(),listp.get(0).getId());
    }

    @Test
    public void addHobbyToPerson(){
        Person p = f.CreatePerson("addHobbyToPerson", "test", "test");
        Hobby h = f.createHobbies("addHobbyToPerson", "test");
        p = f.addHobbyToPerson(p, h);
        assertEquals(p.getHobbies().get(0).getId(),h.getId());
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
        Person p = f.CreatePerson("getCountOfPeopleWithHobby", "test", "test");
        Hobby h = f.createHobbies("getCountOfPeopleWithHobby", "test");
        p = f.addHobbyToPerson(p, h);
        int count = f.getCountOfPeopleWithHobby(h);
        assertEquals(1,count);
    }

    @Test
    public void getListOfZipCodes() {
        int excp = 1348;
        int size = f.getListOfZipCodes().size();
        assertEquals(excp,size);
    }

    @Test
    public void getListOfCompaniesWithXEmployes() {
        Company c = f.createCompany("getListOfCompaniesWithXEmployes", "test", 25, 25, 25, "Test");
        List<Company> clist = f.getListOfCompaniesWithXEmployes(25);
        assertEquals(c.getNumEmployees(),clist.get(0).getNumEmployees());
    }

    @Test
    public void addPhoneCompany() {
        Company c = f.createCompany("addPhoneCompany", "test", 22, 22, 22, "Test");
        Company c1 = f.addPhoneCompany(c, "addPhoneCompany", 75);
        assertEquals(75, c1.getPhones().get(0).getNumber());
    }

    @Test
    public void createAddressForPerson() {
        Person p = f.CreatePerson("createAddressForPerson", "test", "test");
        p = f.createAddressForPerson(p, "street", "a", 3300);
        Person p1 = em.find(Person.class, p.getId());
        assertEquals(p.getAddress().getId(),p1.getAddress().getId());
    }

    @Test
    public void createAddressForCompany() {
        Company c = f.createCompany("createAddressForCompany", "test",0,0,0, "test");
        c = f.createAddressForCompany(c, "street", "a", 3300);
        Company c1 = em.find(Company.class, c.getId());
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
        Person p = f.CreatePerson("changeAddressFromPerson", "test", "test");
        p = f.createAddressForPerson(p, "street1", "no", 3000);
        Person p1 = p;
        p1 = f.changeAddressFromPerson(p1.getId(), "Street2", "no", 3000);
        assertThat(p.getAddress(), is(not(p1.getAddress())));
    }

    @Test
    public void changeAddressFromCompany() {
        Company c = f.createCompany("changeAddressFromPerson", "test",0,0,0, "test");
        c = f.createAddressForCompany(c, "street1", "no", 3000);
        Company c1 = c;
        c1 = f.changeAddressFromCompany(c1.getId(), "Street2", "no", 3000);
        assertThat(c.getAddress(), is(not(c1.getAddress())));
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

    private void assertFalse() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void assertNotSame(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
