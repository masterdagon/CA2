/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import entity.Company;
import entity.Hobby;
import entity.Person;
import facade.Facade;
import java.util.List;
import rest.exception.EntityNotFoundException;

/**
 *
 * @author Muggi
 */
public class TestPopulate {
    
    public static void main(String[] args) throws EntityNotFoundException {
        Facade f = new Facade();
        
        Person p1 = f.createPerson("Dennis", "Jensen","den@email.dk");
        p1 = f.createAddressForPerson(p1, "Jernbanegade", "5B", 3390);
        p1 = f.addPhonePerson(p1, "Home", 22334455);
        Hobby h = f.createHobbies("Haandbold", "spiller med en bold");
        p1 = f.addHobbyToPerson(p1, h);
        
        Person p2 = f.createPerson("Lars", "Larsen","lars@mail.dk");
        p2 = f.createAddressForPerson(p2, "Larsensgade", "5", 3300);
        p2 = f.addPhonePerson(p2, "Home", 33222233);
        p2 = f.addPhonePerson(p2, "Work", 44556677);
        Hobby h1 = f.createHobbies("Fodbold", "spiller med en bold");
        p2 = f.addHobbyToPerson(p2, h1);
        
        Person p3 = f.createPerson("Kurt", "Kurtsen","kurt@mail.dk");
        p3 = f.createAddressForPerson(p3, "kurtsen Allé", "20", 2800);
        p3 = f.addPhonePerson(p3, "Home", 44335544);
        p3 = f.addHobbyToPerson(p3, h1);
        
        Person p4 = f.createPerson("Peter", "Pedersen","Peter@mail.dk");
        p4 = f.createAddressForPerson(p4, "Larsensgade", "20", 3300);
        p4 = f.addPhonePerson(p4, "Home", 55667788);
        p4 = f.addHobbyToPerson(p4, h1);
        
        
        Company c1 = f.createCompany("Lincoln lawyer", "Legal", 11112233, 1, 1, "Lincolm@mail.com");
        c1 = f.createAddressForCompany(c1, "Vejen", "2", 2000);
        c1 = f.addPhoneCompany(c1, "Reception", 22334455);
        c1 = f.addPhoneCompany(c1, "Office", 44334433);
        
        
        Company c2 = f.createCompany("Well Mert", "SuperMarket", 11113322, 10, 90, "email2");
        c2 = f.createAddressForCompany(c2, "storvej", "8", 2400);
        c2 = f.addPhoneCompany(c2, "Office", 44443333);
        
        Company c3 = f.createCompany("Roland MacDanold", "FastFood", 11114545, 2, 10, "email1");
        c3 = f.createAddressForCompany(c3, "dumvej", "7", 8000);
        c3 = f.addPhoneCompany(c3, "Reception", 66556644);
        c3 = f.addPhoneCompany(c3, "Office", 23456789);
    }
}
