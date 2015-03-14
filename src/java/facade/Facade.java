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
import static entity.InfoEntity_.address;
import entity.Person;
import entity.Phone;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import rest.exception.EntityNotFoundException;

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

    public Person getPersonFromPhone(int phoneNumber) throws EntityNotFoundException {//Finnish
        EntityManager em = null;
        try {
            em = getEntityManager();
            Phone phone = em.find(Phone.class, phoneNumber);
            if (phone == null) {
                throw new EntityNotFoundException("The PhoneNumber: " + phoneNumber + " does not exist");
            }
            Person p = phone.getPerson();
            return p;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Company getCompanyFromPhone(int phoneNumber) throws EntityNotFoundException {//finnish
        EntityManager em = null;
        try {
            em = getEntityManager();
            Phone phone = em.find(Phone.class, phoneNumber);
            if (phone == null) {
                throw new EntityNotFoundException("The PhoneNumber: " + phoneNumber + " does not exist");
            }
            Company c = phone.getCompany();
            return c;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Company getCompanyFromcvr(int CVR) throws EntityNotFoundException {//ready
        EntityManager em = null;
        try {
            em = getEntityManager();
            Company c = (Company) em.createQuery("select c From Company c where c.cvr=:cvr").setParameter("cvr", CVR).getSingleResult();
            if (c == null) {
                throw new EntityNotFoundException("The Company with cvr: " + CVR + " does not exist");
            }
            return c;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Person> getAllPersonsWithHobby(int hobbyId) throws EntityNotFoundException {//kind of strange, since the hobby parameter should already contain the list of persons. oh well :P
        EntityManager em = null;
        try {
            em = getEntityManager();
            TypedQuery<Hobby> q = em.createQuery("select p from Hobby p where p.id=:id", Hobby.class).setParameter("id", hobbyId);
            Hobby hobby = q.getSingleResult();
            if (hobby == null) {
                throw new EntityNotFoundException("The hobby does not exist");
            }
            List<Person> listP = hobby.getPersons();
            return listP;

        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Person> getAllPersonsInCity(int zipcode) throws EntityNotFoundException {//finnish
        EntityManager em = null;
        try {
            em = getEntityManager();
            CityInfo ci = em.find(CityInfo.class, zipcode);
            if (ci == null) {
                throw new EntityNotFoundException("The zipcode does not exist in database");
            }
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

    public int getCountOfPeopleWithHobby(Hobby hobby) throws EntityNotFoundException { //finished in rest, not tested
        EntityManager em = null;
        try {
            em = getEntityManager();
            Hobby h = em.find(Hobby.class, hobby.getId());
            if (h == null) {
                throw new EntityNotFoundException("The hobby does not exist");
            }
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

    public List<Company> getListOfCompaniesWithXEmployes(int empCount) {//ready
        EntityManager em = null;
        try {
            em = getEntityManager();
            TypedQuery<Company> q = em.createQuery("select c from Company c where c.NumEmployees>:NumEmployees", Company.class);
            q.setParameter("NumEmployees", empCount);
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

    public Person createAddressForPerson(Person p, String street, String info, int zip) throws EntityNotFoundException {//finnish
        EntityManager em = null;
        try {
            em = getEntityManager();
            CityInfo cityInfo = em.find(CityInfo.class, zip);
            if (cityInfo == null) {
                throw new EntityNotFoundException("The zipcode does not exist in database");
            }
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

    public Company createAddressForCompany(Company c, String street, String info, int zip) throws EntityNotFoundException {//finnish
        EntityManager em = null;
        try {
            em = getEntityManager();
            CityInfo cityInfo = em.find(CityInfo.class, zip);
            if (cityInfo == null) {
                throw new EntityNotFoundException("The zipcode does not exist in database");
            }
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

    public void deletePerson(int personId) throws EntityNotFoundException {//finnish
        EntityManager em = null;
        try {
            em = getEntityManager();
            Person p = em.find(Person.class, personId);
            if (p == null) {
                throw new EntityNotFoundException("The person does not exist in database");
            }
            int aId = 0;
            if (p.getAddress() != null) {
                aId = p.getAddress().getId();
            }
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
            if (p.getAddress() != null) {
                deleteAddress(aId);
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void deleteCompany(int companyId) throws EntityNotFoundException {//finnish
        EntityManager em = null;
        int aId = 0;
        try {
            em = getEntityManager();
            Company c = em.find(Company.class, companyId);
            if (c == null) {
                throw new EntityNotFoundException("The company does not exist in database");
            }
            if (c.getAddress() != null) {
                aId = c.getAddress().getId();
            }
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
            if (c.getAddress() != null) {
                deleteAddress(aId);
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Person deletePersonPhone(int phoneNumber) throws EntityNotFoundException {//finnish
        EntityManager em = null;
        Person p = null;
        try {
            em = getEntityManager();
            Phone phone = em.find(Phone.class, phoneNumber);
            if (phone == null) {
                throw new EntityNotFoundException("The phonenumber: " + phoneNumber + " does not exist in database");
            }
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

    public Company deleteCompanyPhone(int phoneNumber) throws EntityNotFoundException {//finnish
        EntityManager em = null;
        Company c = null;
        try {
            em = getEntityManager();
            Phone phone = em.find(Phone.class, phoneNumber);
            if (phone == null) {
                throw new EntityNotFoundException("The phonenumber: " + phoneNumber + " does not exist in database");
            }
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

    public Person changeAddressFromPerson(int personID, String street, String info, int zip) throws EntityNotFoundException { //finished and tested
        EntityManager em = null;
        try {
            em = getEntityManager();
            Person p = em.find(Person.class, personID);
            int adID = p.getAddress().getId();
            if (p == null) {
                throw new EntityNotFoundException("The person does not exist in database");
            }
            p.getAddress().getPersons().remove(p);
            p = createAddressForPerson(p, street, info, zip);
            em.getTransaction().begin();
            em.merge(p);
            em.getTransaction().commit();
            deleteAddress(adID);
            return p;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Company changeAddressFromCompany(int companyID, String street, String info, int zip) throws EntityNotFoundException { //finished, not tested
        EntityManager em = null;
        try {
            em = getEntityManager();
            Company c = em.find(Company.class, companyID);
            if (c == null) {
                throw new EntityNotFoundException("The Company does not exist in database");
            }
            int aId = c.getAddress().getId();
            c.getAddress().getPersons().remove(c);
            c = createAddressForCompany(c, street, info, zip);
            em.getTransaction().begin();
            em.merge(c);
            em.getTransaction().commit();
            deleteAddress(aId);
            return c;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void deleteAddress(int addressId) throws EntityNotFoundException { //finished but not tested
        EntityManager em = null;
        try {
            em = getEntityManager();
            Address address = em.find(Address.class, addressId);
            if (address == null) {
                throw new EntityNotFoundException("The address does not exist in database");
            }
            if (address.getPersons().isEmpty() && address.getCompanies().isEmpty()) {
                em.getTransaction().begin();
                em.remove(address);
                em.getTransaction().commit();
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void removeHobbyFromPerson(int hobbyId, int personId) throws EntityNotFoundException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            Person person = em.find(Person.class, personId);
            if (person == null) {
                throw new EntityNotFoundException("The person does not exist in database");
            }
            Hobby hobby = em.find(Hobby.class, hobbyId);
            if (hobby == null) {
                throw new EntityNotFoundException("The hobby does not exist in database");
            }
            person.removeHobby(hobby);
            em.getTransaction().begin();
            em.merge(person);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void deleteHobbyFromDB(int hobbyId) throws EntityNotFoundException { //finished, not tested with postedman
        EntityManager em = null;
        try {
            em = getEntityManager();
            Hobby hobby = em.find(Hobby.class, hobbyId);
            if (hobby == null) {
                throw new EntityNotFoundException("The hobby does not exist in database");
            }
            em.getTransaction().begin();
            for (Person p : hobby.getPersons()) {
                p.removeHobby(hobby);
                em.merge(p);
            }
            hobby.getPersons().clear();
            em.merge(hobby);
            em.remove(hobby);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Person addHobbyToPerson(Person person, Hobby hobby) throws EntityNotFoundException { //finished, not tested with postman
        EntityManager em = null;
        try {
            em = getEntityManager();
            Person p = em.find(Person.class, person.getId());
            if (p == null) {
                throw new EntityNotFoundException("The person does not exist in database");
            }
            Hobby h = em.find(Hobby.class, hobby.getId());
            if (h == null) {
                throw new EntityNotFoundException("The hobby does not exist in database");
            }
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

    public List<Hobby> getAllHobbies() { //finished
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

    public Hobby getHobbiesFromID(int id) throws EntityNotFoundException { //finished
        EntityManager em = null;
        try {
            em = getEntityManager();
            Hobby h = em.find(Hobby.class, id);
            if (h == null) {
                throw new EntityNotFoundException("The hobby does not exist in database");
            }
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

    public Company getCompany(int id) throws EntityNotFoundException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            Company c = em.find(Company.class, id);
            if (c == null) {
                throw new EntityNotFoundException("The company does not exist in database");
            }
            return c;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Person getPerson(int id) throws EntityNotFoundException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            Person p = em.find(Person.class, id);
            if (p == null) {
                throw new EntityNotFoundException("The person does not exist in database");
            }
            return p;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public CityInfo getCityInfo(int zip) throws EntityNotFoundException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            CityInfo ci = em.find(CityInfo.class, zip);
            if (ci == null) {
                throw new EntityNotFoundException("The zipcode does not exist in database");
            }
            return ci;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

}
