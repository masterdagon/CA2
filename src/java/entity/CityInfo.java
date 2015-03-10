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
import javax.persistence.OneToMany;

/**
 *
 * @author Muggi
 */
@Entity
public class CityInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String zipCode;
    private String city;
    @OneToMany(mappedBy = "cityInfo")
    private List<Address> addresses = new ArrayList();

    public CityInfo(){}

    public CityInfo(String zipCode, String city) {
        this.zipCode = zipCode;
        this.city = city;
    }

    public List<Address> getAddresses() {
        return addresses;
    }  
    
    public void addAddress(Address address){
        addresses.add(address);
    }
    
    public void removeAddress(Address address){
        addresses.remove(address);
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
