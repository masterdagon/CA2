/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import entity.Company;
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
        
        Company c1 = f.createCompany("kiosk", "lille", 11111111, 2, 10, "email1");
        c1 = f.createAddressForCompany(c1, "street1", "info1", 3390);
        c1 = f.addPhoneCompany(c1, "Home", 22334455);
        c1 = f.addPhoneCompany(c1, "Work", 44334433);
        Company c2 = f.createCompany("Marked", "stor", 11111112, 10, 90, "email2");
        c2 = f.createAddressForCompany(c2, "street1", "info1", 3300);
        c2 = f.addPhoneCompany(c1, "Work", 44443333);
    }
    
}
