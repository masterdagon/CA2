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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/**
 *
 * @author Muggi
 */
@Entity
public class Person extends InfoEntity implements Serializable {
    
    private String firstName;
    private String lastName;
    @ManyToMany
    private List<Hobby> hobbies = new ArrayList();
    @OneToMany(mappedBy = "person")
    private List<Phone> phones;
    
    public Person (String fName, String lName){
        this.firstName = fName;
        this.lastName = lName;
        this.phones = new ArrayList();
    }

    public Person() {
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void addPhone(Phone phone){
        phones.add(phone);
    }
    
    public void removePhone(Phone phone){
        phones.remove(phone);
    }
    
    public List<Hobby> getHobbies(){
        return hobbies;
    }
    
    public void addHobby(Hobby hobby){
        hobbies.add(hobby);
    }
    
    public void removeHobby(Hobby hobby){
        hobbies.remove(hobby);
    }
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    

    

    

    
    
}
