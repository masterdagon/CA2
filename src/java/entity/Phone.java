/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Muggi
 */
@Entity
@XmlRootElement
public class Phone implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private int phoneNumber;
    private String description;
    @ManyToOne
    private Person person;
    @ManyToOne
    private Company company;
    
    public Phone() {
    }

    public Phone(Person person, int number, String description) {
        this.person = person;
        this.phoneNumber = number;
        this.description = description;
    }
    
    public Phone(Company company, int number, String description) {
        this.company = company;
        this.phoneNumber = number;
        this.description = description;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
   
    public int getNumber() {
        return phoneNumber;
    }

    public void setNumber(int number) {
        this.phoneNumber = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
