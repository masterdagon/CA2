/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Address;
import entity.CityInfo;
import entity.Company;
import entity.Hobby;
import entity.Person;
import entity.Phone;
import java.util.ArrayList;
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

    public Person getPersonFromPhone(int phoneNumber) {//Finnish
        EntityManager em = null;
        try {
            em = getEntityManager();
            Phone phone = em.find(Phone.class, phoneNumber);
            Person p = phone.getPerson();
            return p;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Company getCompanyFromPhone(int PhoneNumber) {//finnish
        EntityManager em = null;
        try {
            em = getEntityManager();
            Phone phone = em.find(Phone.class, PhoneNumber);
            Company c = phone.getCompany();
            return c;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Company getCompanyFromcvr(int CVR) {//not ready teacher
        EntityManager em = null;
        try {
            em = getEntityManager();
            Company c = em.find(Company.class, CVR);
            return c;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Person> getAllPersonsWithHobby(int hobbyId) {//kind of strange, since the hobby parameter should already contain the list of persons. oh well :P
        EntityManager em = null;
        try {
            em = getEntityManager();
            TypedQuery<Hobby> q = em.createQuery("select p from Hobby p", Hobby.class);
            List<Hobby> hobbies = q.getResultList();
            List<Person> listP = null;
            for (Hobby h : hobbies) {
                if (h.getId()==hobbyId) {
                    listP = h.getPersons();
                }
            }
            return listP;

        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Person> getAllPersonsInCity(int zipcode) {//finnish
        EntityManager em = null;
        try {
            em = getEntityManager();
            CityInfo ci = em.find(CityInfo.class, zipcode);
            List<Person> persons = new ArrayList();
            for (Address address : ci.getAddresses()) {
                for (Person person : address.getPersons()) {
                    persons.add(person);
                }
            }
            return persons;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public int getCountOfPeopleWithHobby(Hobby hobby) { //finished in rest, not tested
        EntityManager em = null;
        try {
            em = getEntityManager();
            Hobby h = em.find(Hobby.class, hobby.getId());
            return h.getPersons().size();
        } finally {
            if (em != null) {
                em.close();
            }
        }

    }

    public List<CityInfo> getListOfZipCodes() {//finnish
        EntityManager em = null;
        try {
            em = getEntityManager();
            TypedQuery<CityInfo> q = em.createQuery("select c from CityInfo c", CityInfo.class);
            return q.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Company> getListOfCompaniesWithXEmployes(int empCount) {//not ready WHERE
        EntityManager em = null;
        try {
            em = getEntityManager();
            TypedQuery<Company> q = em.createQuery("select c from Company c where c.numemployees = :p", Company.class);
            q.setParameter("p", empCount);
            return q.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Person createPerson(String fName, String lName, String email) {//finnish
        EntityManager em = null;
        try {
            em = getEntityManager();
            Person p = new Person(fName, lName);
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

    public Company createCompany(String name, String description, int cvr, int NumEmployees, int marketValue, String email) {//finnish
        EntityManager em = null;
        try {
            em = getEntityManager();
            Company c = new Company(name, description, cvr, NumEmployees, marketValue);
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

    public Person addPhonePerson(Person person, String description, int number) {//finnish
        EntityManager em = null;
        try {
            em = getEntityManager();
            Phone phone = new Phone(person, number, description);
            person.addPhone(phone);
            em.getTransaction().begin();
            em.merge(person);
            em.getTransaction().commit();
            return person;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Company addPhoneCompany(Company company, String description, int number) {//finnish
        EntityManager em = null;
        try {
            em = getEntityManager();
            Phone phone = new Phone(company, number, description);
            company.addPhone(phone);
            em.getTransaction().begin();
            em.merge(company);
            em.getTransaction().commit();
            return company;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Person createAddressForPerson(Person p, String street, String info, int zip) {//finnish
        EntityManager em = null;
        try {
            em = getEntityManager();
            CityInfo cityInfo = em.find(CityInfo.class, zip);
            Address address = new Address(street, info, cityInfo);
            em.getTransaction().begin();
            em.persist(address);
            cityInfo.addAddress(address);
            address.addPerson(p);
            p.setAddress(address);
            em.merge(cityInfo);
            em.merge(address);
            em.merge(p);
            em.getTransaction().commit();
            return p;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Company createAddressForCompany(Company c, String street, String info, int zip) {//finnish
        EntityManager em = null;
        try {
            em = getEntityManager();
            CityInfo cityInfo = em.find(CityInfo.class, zip);
            Address address = new Address(street, info, em.find(CityInfo.class, zip));
            em.getTransaction().begin();
            em.persist(address);
            cityInfo.addAddress(address);
            c.setAddress(address);
            address.addCompany(c);
            em.merge(cityInfo);
            em.merge(address);
            em.merge(c);
            em.getTransaction().commit();
            return c;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Hobby createHobbies(String name, String description) { //finished not tested
        EntityManager em = null;
        try {
            em = getEntityManager();
            Hobby hobby = new Hobby(name, description);
            em.getTransaction().begin();
            em.persist(hobby);
            em.getTransaction().commit();
            return hobby;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public boolean deletePerson(int personId) {//finnish
        EntityManager em = null;
        try {
            em = getEntityManager();
            Person p = em.find(Person.class, personId);
            List<Phone> phones = p.getPhones();
            List<Hobby> hobbies = p.getHobbies();
            if (p.getAddress() != null) {
                if (!p.getAddress().getPersons().isEmpty()) {
                    if (p.getAddress().getPersons().contains(p)) {
                        p.getAddress().removePerson(p);
                    }
                } else {
                    System.out.println("addres emty");
                }
            }

            em.getTransaction().begin();
            for (Hobby hb : hobbies) {
                hb.removePerson(p);
                em.merge(hb);
            }
            System.out.println(phones.size());
            for (Phone ph : phones) {
                em.remove(ph);
            }
            p.getPhones().clear();
            p.getHobbies().clear();
            em.merge(p);
            em.remove(p);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public boolean deleteCompany(int companyId) {//finnish
        EntityManager em = null;
        try {
            em = getEntityManager();
            Company c = em.find(Company.class, companyId);
            System.out.println(c.getPhones().isEmpty());
            List<Phone> phones = c.getPhones();
            if (c.getAddress() != null) {
                if (c.getAddress().getCompanies().isEmpty()) {
                    System.out.println("addres empty");
                } else {
                    if (c.getAddress().getCompanies().contains(c)) {
                        c.getAddress().removeCompany(c);
                    }
                }
            }
            em.getTransaction().begin();

            System.out.println(phones.size());
            for (Phone ph : phones) {
                em.remove(ph);
            }

            c.getPhones().clear();
            em.merge(c);
            em.remove(c);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Person deletePersonPhone(int PhoneNumber) {//finnish
        EntityManager em = null;
        Person p = null;
        try {
            em = getEntityManager();
            Phone phone = em.find(Phone.class, PhoneNumber);
            if (phone.getPerson() != null) {
                if (phone.getPerson().getPhones().contains(phone)) {
                    p = phone.getPerson();
                    phone.getPerson().removePhone(phone);
                }
            }
            em.getTransaction().begin();
            em.remove(phone);
            em.merge(p);
            em.getTransaction().commit();
            return p;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Company deleteCompanyPhone(int PhoneNumber) {//finnish
        EntityManager em = null;
        Company c = null;
        try {
            em = getEntityManager();
            Phone phone = em.find(Phone.class, PhoneNumber);
            if (phone.getCompany() != null) {
                if (phone.getCompany().getPhones().contains(phone)) {
                    c = phone.getCompany();
                    phone.getCompany().removePhone(phone);
                }
            }
            em.getTransaction().begin();
            em.remove(phone);
            em.merge(c);
            em.getTransaction().commit();
            return c;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Person changeAddressFromPerson(int personID, String street, String info, int zip) { //finished and tested
        EntityManager em = null;
        try {
            em = getEntityManager();
            Person p = em.find(Person.class, personID);
            p.getAddress().getPersons().remove(p);
            p = createAddressForPerson(p, street, info, zip);
            em.getTransaction().begin();
            em.merge(p);
            em.getTransaction().commit();
            return p;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Company changeAddressFromCompany(int companyID, String street, String info, int zip) { //finished, not tested
        EntityManager em = null;
        try {
            em = getEntityManager();
            Company c = em.find(Company.class, companyID);
            c.getAddress().getPersons().remove(c);
            c = createAddressForCompany(c, street, info, zip);
            em.getTransaction().begin();
            em.merge(c);
            em.getTransaction().commit();
            return c;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public boolean deleteAddress(int addressId) { //finished but not tested
        EntityManager em = null;
        try {
            em = getEntityManager();
            Address address = em.find(Address.class, addressId);
            em.getTransaction().begin();
            for (Person p : address.getPersons()) {
                p.setAddress(null);
                em.merge(p);
            }
            for (Company c : address.getCompanies()){
                c.setAddress(null);
                em.merge(c);
            }
            em.remove(address);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public boolean removeHobbyFromPerson(int hobbyId, int personId) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            Person person= em.find(Person.class, personId);
            Hobby hobby= em.find(Hobby.class, hobbyId);
            person.removeHobby(hobby);
            em.merge(person);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public boolean deleteHobbyFromDB(int hobbyId) { //finished, not tested with postedman
        EntityManager em = null;
        try {
            em = getEntityManager();
            Hobby hobby = em.find(Hobby.class, hobbyId);

            em.getTransaction().begin();
            for (Person p : hobby.getPersons()) {
                p.removeHobby(hobby);
                em.merge(p);
            }
            hobby.getPersons().clear();
            em.merge(hobby);
            em.remove(hobby);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Person addHobbyToPerson(Person person, Hobby hobby) { //finished, not tested with postman
        EntityManager em = null;
        try {
            em = getEntityManager();
            Person p = em.find(Person.class, person.getId());
            Hobby h = em.find(Hobby.class, hobby.getId());
            em.getTransaction().begin();
            p.addHobby(hobby);
            h.addPerson(person);
            em.merge(p);
            em.merge(h);
            em.getTransaction().commit();
            return p;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public List<Hobby> getAllHobbies() { //not junit tested, not in rest yet
        EntityManager em = null;
        try {
            em = getEntityManager();
            TypedQuery<Hobby> q = em.createQuery("select p from Hobby p", Hobby.class);
            return q.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public Hobby getHobbiesFromID(int id) { //not junit tested, not in rest yet
        EntityManager em = null;
        try {
            em = getEntityManager();
            Hobby h = em.find(Hobby.class, id);
            return h;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    
    

    //--------------------Andre metoder------------------------------------//
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

    public List<Company> getAllCompanies() {
        EntityManager em = null;
        try {
            em = getEntityManager();
            TypedQuery<Company> q = em.createQuery("select c from Company c", Company.class);
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

}
