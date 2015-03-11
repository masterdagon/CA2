/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 *
 * @author Dennnis
 */
public class ErrorMessage {

    public ErrorMessage(Throwable ex, int code, boolean debug) {
        this.code = code;
        this.message = ex.getMessage();
        this.description = ex.getMessage();
        if (debug) {
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));
            this.stackTrace = sw.toString();
        }
    }
    private int code;
    private String message;
    private String description;
    private String stackTrace;

    public void setDescription(String description) {
        this.description = description;
    }
    
}
