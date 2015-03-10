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
        System.out.println("CreatePerson : Person ID = " + p.getId());
        List<Person> listP = facade.getAllPersons();
        System.out.println("getAllPersons: Person last name = " + listP.get(0).getLastName());
        p = facade.addPhonePerson(p,"home", 12345678);
        System.out.println("addPhonePerson : PhoneList = " + p.getPhones().size());
        p = facade.getPersonFromPhone(12345678);
        System.out.println("getPersonFromPhone : Person number = " + p.getPhones().get(0).getNumber());
        
        System.out.println("");
        System.out.println("------------------------------------------");
        System.out.println("");
        
        Company c = facade.createCompany("Hansens Hardware", "Billigt hardware og andet",221866, 23, 500000,"hh@hh.dk");
        c = facade.addPhoneCompany(c, "home", 87654321);
        c = facade.getCompanyFromPhone(87654321);
        System.out.println("addPhoneCompany + get CompanyFromPhone : should be Hanses Harware = "+c.getName());
        List<Company> listC = facade.getAllCompanies();
        System.out.println("getAllCompanies: All companies below");
        for (int i = 0; i < listC.size(); i++) {
            System.out.println("       " + listC.get(i).getName());
        }
        
        System.out.println(facade.deletePerson(1));
    }
    
}
