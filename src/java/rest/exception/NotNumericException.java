/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.exception;

/**
 *
 * @author Muggi
 */
public class NotNumericException extends Exception {

    /**
     * Creates a new instance of <code>NotNumeric</code> without detail message.
     */
    public NotNumericException() {
    }

    /**
     * Constructs an instance of <code>NotNumeric</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public NotNumericException(String msg) {
        super(msg);
    }
}
