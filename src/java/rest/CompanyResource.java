/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import entity.Company;
import entity.Phone;
import facade.Facade;
import java.lang.reflect.Type;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * REST Web Service
 *
 * @author Dennnis
 */
@Path("company")
public class CompanyResource {

    @Context
    private UriInfo context;
    private Facade f;
    private Gson gson;
    /**
     * Creates a new instance of CopmanyResource
     */
    public CompanyResource() {
        f = new Facade();
        gson = new Gson();
    }


    @GET
    @Produces("application/json")
    public String getAllCompanies() {
        List<Company> clist = f.getAllCompanies();
        JsonArray comp = new JsonArray();
        for (Company c : clist) {
            JsonObject company = new JsonObject();
            company.addProperty("id", c.getId());
            company.addProperty("description", c.getDescription());
            company.addProperty("cvr", c.getCvr());
            company.addProperty("email", c.getEmail());
            company.addProperty("street", c.getAddress().getStreet());
            company.addProperty("additionalinfo", c.getAddress().getAdditionalinfo());
            company.addProperty("zipcode", c.getAddress().getCityInfo().getZipCode());
            company.addProperty("city", c.getAddress().getCityInfo().getCity());
            
            JsonArray phones = new JsonArray();
            List<Phone> phs = c.getPhones();
            for (Phone ph : phs) {
                JsonObject phone = new JsonObject();
                phone.addProperty("number", ph.getNumber());
                phone.addProperty("description", ph.getDescription());
                phones.add(phone); 
            }
            company.add("phones", phones);
            company.addProperty("numemployees", c.getNumEmployees());
            company.addProperty("marketvalue", c.getMarketValue());
            
            comp.add(company);
        }
        String jasonCompany =  gson.toJson(comp);
        return jasonCompany;    
    }
    
    @GET
    @Produces("application/json")
    @Path("/{phoneNumber}")
    public String getCompany(@PathParam("phoneNumber") int phoneNumber) {
        Company c = f.getCompanyFromPhone(phoneNumber);
            JsonObject company = new JsonObject();
            company.addProperty("id", c.getId());
            company.addProperty("description", c.getDescription());
            company.addProperty("cvr", c.getCvr());
            company.addProperty("email", c.getEmail());
            company.addProperty("street", c.getAddress().getStreet());
            company.addProperty("additionalinfo", c.getAddress().getAdditionalinfo());
            company.addProperty("zipcode", c.getAddress().getCityInfo().getZipCode());
            company.addProperty("city", c.getAddress().getCityInfo().getCity());
            
            JsonArray phones = new JsonArray();
            List<Phone> phs = c.getPhones();
            for (Phone ph : phs) {
                JsonObject phone = new JsonObject();
                phone.addProperty("number", ph.getNumber());
                phone.addProperty("description", ph.getDescription());
                phones.add(phone); 
            }
            company.add("phones", phones);
            company.addProperty("numemployees", c.getNumEmployees());
            company.addProperty("marketvalue", c.getMarketValue());

        String jasonCompany =  gson.toJson(company, JsonObject.class);
        return jasonCompany;
    }
    
    @POST
    @Consumes("application/json")
    @Path("/create")
    public void createCompanyAndAddress(String content){
        JsonObject jo = new JsonParser().parse(content).getAsJsonObject();
        Company c = f.createCompany(jo.get("name").getAsString(), jo.get("description").getAsString(),jo.get("cvr").getAsInt(),jo.get("numemployees").getAsInt(),jo.get("marketvalue").getAsInt(), jo.get("email").getAsString());
        c = f.createAddressForCompany(c, jo.get("street").getAsString(), jo.get("additionalinfo").getAsString(),jo.get("zipcode").getAsInt() );
    }
    
    
    @DELETE
    @Consumes("application/json")
    @Path("/delete")
    public void deletePerson(String content) {
         Type type = new TypeToken<List<Integer>>() {
        }.getType();
        List<Integer> iList = gson.fromJson(content, type);
        f.deleteCompany(iList.get(0).intValue());
    }
}
