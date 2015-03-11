/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import entity.Person;
import facade.Facade;

/**
 *
 * @author Muggi
 */
public class TestPopulate {
    
    public static void main(String[] args) {
        Facade f = new Facade();
        
        Person p1 = f.createPerson("firstname1", "lastname1","email1");
        p1 = f.createAddressForPerson(p1, "street1", "info1", 2980);
        p1 = f.addPhonePerson(p1, "phone1", 999);
        
        Person p2 = f.createPerson("firstname2", "lastname2","email2");
        p2 = f.createAddressForPerson(p2, "street2", "info2", 2990);
        p2 = f.addPhonePerson(p2, "phone2", 888);
    }
    
}
