/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest.exception;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Muggi
 */
@Provider
public class NotNumericExceptionMapper implements ExceptionMapper<NotNumericException>{
    
    static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    @Context
    ServletContext context;  
    
    @Override
    public Response toResponse(NotNumericException e) {
        boolean isDebug = context.getInitParameter("debug").equals("true");
        ErrorMessage err = new ErrorMessage(e, 404, isDebug);
        err.setDescription("You tried to call ...");
        return Response.status(404)
                .entity(gson.toJson(err))
                .type(MediaType.APPLICATION_JSON).
                build();
    }
    
}
