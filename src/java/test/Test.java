/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import entity.Address;
import entity.Company;
import entity.Person;
import facade.Facade;
import java.util.List;

/**
 *
 * @author Muggi
 */
public class Test {
    
    public static void main(String[] args) {
        Facade facade = new Facade();
        
        Person p = facade.CreatePerson("Muggi","Dagon","666@gmail.com");
        System.out.println(p.getId());
        List<Person> listP = facade.getAllPersons();
        System.out.println(listP.get(0).getLastName());
        p = facade.addPhonePerson(p,"home", 12345678);
        p = facade.getPersonFromPhone(12345678);
        System.out.println("Phone number: " + p.getPhones().get(0).getNumber());
        
        facade.createCompany("Hansens Hardware", "Billigt hardware og andet",221866, 23, 500000,"hh@hh.dk");
        List<Company> listC = facade.getAllCompanies();
        System.out.println(listC.get(0).getName());
        Company c = facade.getCompany(2);
        System.out.println(c.getDescription());
        
        p = facade.createAddressForPerson(p, "Landevej", "26,1.mf");
        System.out.println(p.getAddress().getStreet());
        
        
    }
    
}
