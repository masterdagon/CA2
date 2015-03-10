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
import entity.InfoEntity;
import entity.Person;
import entity.Phone;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
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

    public Company getCompanyFromcvr(int CVR) {//not ready
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

    public List<Person> getAllPersonsWithHobby(Hobby hobby) {//not ready
                EntityManager em = null;
        try {
            em = getEntityManager();
            TypedQuery<Hobby> q = em.createQuery("select p from Hobby p", Hobby.class);
            
            return null;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Person> getAllPersonsInCity(int zipcode) {//ready
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

    public int getCountOfPeopleWithHobby(Hobby hobby) {
        return 0;
    }

    public List<CityInfo> getListOfZipCodes() {
        return null;
    }

    public List<Company> getListOfCompaniesWithXEmployes(int EmpCount) {//not ready
        EntityManager em = null;
        try {
            em = getEntityManager();
            TypedQuery<Company> q = em.createQuery("select c from Company c", Company.class);
            q.setParameter("p", "Company");
            return q.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Person CreatePerson(String fName, String lName, String email) {
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

    public Person addPhonePerson(Person person, String description, int number) {
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
    
    public Company addPhoneCompany(Company company, String description, int number) {
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

    public Person createAddressForPerson(Person p, String street, String info, int zip) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            Address address = new Address(street,info,em.find(CityInfo.class, zip));
            p.setAddress(address);
            address.addPerson(p);
            em.getTransaction().begin();
            em.persist(address);
            em.merge(p);
            em.getTransaction().commit();
            return p;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public Company createAddressForCompany(Company c, String street, String info, int zip) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            Address address = new Address(street,info,em.find(CityInfo.class, zip));
            c.setAddress(address);
            address.addCompany(c);
            em.getTransaction().begin();
            em.persist(address);
            em.merge(c);
            em.getTransaction().commit();
            return c;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Hobby createHobbies() {
        return null;
    }

    public void deletePerson(int PersonId) {
    }

    public void deleteCompany(int CompanyId) {
    }

    public void deletePhone(int PhoneNumber) {
    }

    public void removeAddress(int addressId) {
    }

    public void deleteAddress(int addressId) {
    }

    public void removeHobbies(int hobbyId) {
    }

    public void deleteHobbies(int hobbyId) {
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
}
