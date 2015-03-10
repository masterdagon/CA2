/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import entity.Address;
import entity.CityInfo;
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
        p = facade.createAddressForPerson(p, "Thisway", "no Info", 3390);
        System.out.println("createAddressForPerson: Zipcode= " + p.getAddress().getCityInfo().getZipCode());
        System.out.println("getAllpersontoAddres: All persons below size = "+p.getAddress().getPersons().size());
        for (int i = 0; i < p.getAddress().getPersons().size(); i++) {
            System.out.println("       " + p.getAddress().getPersons().get(i).getLastName());
        }
        
        System.out.println("");
        System.out.println("------------------------------------------");
        System.out.println("");
        
        List<Person> PCityList = facade.getAllPersonsInCity(3390);
        System.out.println("getAllpersoninCity: All persons in city = 3390");
        for (int i = 0; i < PCityList.size(); i++) {
            System.out.println("       " + PCityList.get(i).getLastName());
        }
        
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
        
        System.out.println("");
        System.out.println("------------------------------------------");
        System.out.println("");
        
        System.out.println(facade.deletePerson(p.getId()));
        List<Person> pers = facade.getAllPersons();
        System.out.println("Persons size after delete ="+pers.size());
        for (Person per : pers) {
            System.out.println("         "+per.getLastName());
        }
        System.out.println("");
        System.out.println("------------------------------------------");
        System.out.println("");
        
        c = facade.deleteCompanyPhone(87654321);
        System.out.println("getAllCompaniesphones: All phones below");
        for (int i = 0; i < c.getPhones().size(); i++) {
            System.out.println("       " + c.getPhones().get(i).getNumber());
        }
        
        System.out.println("");
        System.out.println("------------------------------------------");
        System.out.println("REMOVE COMPANY:");
        Company c1 = facade.createCompany("McRonalds", "Dette er ikke McDonalds", 13597562, 10, 999, "mcronald@funfun.com");
        System.out.println(c1.getId());
        System.out.println(facade.deleteCompany(c1.getId()));
        
        System.out.println("");
        System.out.println("------------------------------------------");
        System.out.println("");
        
        List<CityInfo> cityList = facade.getListOfZipCodes();
        System.out.println("getAllCitys: All cities List Size ="+cityList.size());
        for (int i = 0; i < 3; i++) {
            System.out.println("       " + cityList.get(i).getCity());
        }
        
    }
    
}
