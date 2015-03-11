/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.exception;

/**
 *
 * @author Dennnis
 */
public class PersonNotFound extends Exception {

    /**
     * Creates a new instance of <code>PersonNotFound</code> without detail
     * message.
     */
    public PersonNotFound() {
    }

    /**
     * Constructs an instance of <code>PersonNotFound</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public PersonNotFound(String msg) {
        super(msg);
    }
}
