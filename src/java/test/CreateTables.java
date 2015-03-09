/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import javax.persistence.Persistence;

/**
 *
 * @author Muggi
 */
public class CreateTables {
    
    public static void main(String[] args) {
        Persistence.generateSchema("CA2PU", null);
    }
    
}
