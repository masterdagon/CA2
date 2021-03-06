/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Muggi
 */
@Entity
@XmlRootElement
public class Address implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String Street;
    private String additionalinfo;

    @OneToMany(mappedBy = "address")
    private List<Person> persons;

    @OneToMany(mappedBy = "address")
    private List<Company> companies;

    @ManyToOne
    private CityInfo cityInfo;

    public Address() {
    }

    public Address(String Street, String additionalinfo, CityInfo cityInfo) {
        this.companies = new ArrayList();
        this.persons = new ArrayList();
        this.Street = Street;
        this.additionalinfo = additionalinfo;
        this.cityInfo = cityInfo;
    }

    @XmlTransient
    public List<Company> getCompanies() {
        return companies;
    }

    @XmlTransient
    public List<Person> getPersons() {
        return persons;
    }

    public CityInfo getCityInfo() {
        return cityInfo;
    }

    public void setCityInfo(CityInfo cityInfo) {
        this.cityInfo = cityInfo;
    }

    public void addPerson(Person ie) {
        persons.add(ie);
    }

    public void removePerson(Person ie) {
        persons.remove(ie);
    }
    
    public void addCompany(Company ie) {
        companies.add(ie);
    }

    public void removeCompany(Company ie) {
        companies.remove(ie);
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String Street) {
        this.Street = Street;
    }

    public String getAdditionalinfo() {
        return additionalinfo;
    }

    public void setAdditionalinfo(String additionalinfo) {
        this.additionalinfo = additionalinfo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
