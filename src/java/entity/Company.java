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
public class Company extends InfoEntity implements Serializable {
    private String name;
    private String description;
    private int cvr;
    private int NumEmployees;
    private int marketValue;
    @OneToMany(mappedBy = "company")
    private List<Phone> phones;

    public Company() {
    }

    public Company(String name, String description, int cvr, int NumEmployees, int marketValue) {
        this.name = name;
        this.description = description;
        this.cvr = cvr;
        this.NumEmployees = NumEmployees;
        this.marketValue = marketValue;
        this.phones = new ArrayList();
    }
    
    @XmlTransient
        public List<Phone> getPhones() {
        return phones;
    }

    public void addPhone(Phone phone){
        phones.add(phone);
    }
    
    public void removePhone(Phone phone){
        phones.remove(phone);
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumEmployees() {
        return NumEmployees;
    }

    public void setNumEmployees(int NumEmployees) {
        this.NumEmployees = NumEmployees;
    }

    public int getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(int marketValue) {
        this.marketValue = marketValue;
    }

    public int getCvr() {
        return cvr;
    }

    public void setCvr(int cvr) {
        this.cvr = cvr;
    }
    
    
    
    
    
}
