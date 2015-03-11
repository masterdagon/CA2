/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import entity.CityInfo;
import facade.Facade;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;

/**
 * REST Web Service
 *
 * @author Dennnis
 */
@Path("zip")
public class AddressResource {

    @Context
    private UriInfo context;
     private Facade f;
    private Gson gson;
    /**
     * Creates a new instance of ZipResource
     */
    public AddressResource() {
        f = new Facade();
        gson = new Gson();
    }

    /**
     * Retrieves representation of an instance of rest.AddressResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getallZipcodes() {
        List<CityInfo> cities = f.getListOfZipCodes();
        JsonArray zipCodes = new JsonArray();
        for (CityInfo zip : cities) {
            JsonObject city = new JsonObject();
            city.addProperty("zipcode", zip.getZipCode());
            city.addProperty("city", zip.getCity());
            zipCodes.add(city);
        }
        String projectasJason =  new Gson().toJson(new Gson().toJsonTree(zipCodes, JsonObject.class));
        return projectasJason;
    }

}
