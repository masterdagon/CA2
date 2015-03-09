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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author Muggi
 */
@Entity
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class InfoEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    private String email;
    @OneToMany(mappedBy = "infoEntity")
    private List<Phone> phones;
    @ManyToOne
    private Address address;

    public InfoEntity() {
        this.phones = new ArrayList();
    }

    public InfoEntity(String email) {
        this.phones = new ArrayList();
        this.email = email;
    }
    
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
    
    public List<Phone> getPhones(){
        return phones;
    }
    
    public void addPhone(Phone phone){
        phones.add(phone);
    }
    
    public void removePhone(Phone phone){
        phones.remove(phone);
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
    
}
