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
import entity.Person;
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
import javax.ws.rs.PUT;
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

    public JsonObject createCompanyObject(Company c){
        JsonObject company = new JsonObject();
            company.addProperty("id", c.getId());
            company.addProperty("name", c.getName());
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
            return company;
    }

    @GET
    @Produces("application/json")
    public String getAllCompanies() {
        List<Company> clist = f.getAllCompanies();
        JsonArray comp = new JsonArray();
        for (Company c : clist) {
            JsonObject co = createCompanyObject(c);
            comp.add(co);
        }
        String jasonCompany =  gson.toJson(comp);
        return jasonCompany;    
    }
    
    @GET
    @Produces("application/json")
    @Path("/high/{id}")
    public String getCompaniesHxEmp(@PathParam("id") int id) {
        List<Company> clist = f.getListOfCompaniesWithXEmployes(id);
        JsonArray comp = new JsonArray();
        for (Company c : clist) {
            JsonObject co = createCompanyObject(c);
            comp.add(co);
        }
        String jasonCompany =  gson.toJson(comp);
        return jasonCompany;    
    }
    
     @GET
    @Produces("application/json")
    @Path("/{id}")
    public String getCompany(@PathParam("id") int id) {
        Company c = f.getCompany(id);
        JsonObject co = createCompanyObject(c);    
        String jasonCompany =  gson.toJson(co, JsonObject.class);
        return jasonCompany;
    }
    
    @GET
    @Produces("application/json")
    @Path("/phone/{phoneNumber}")
    public String getCompanyFromOPhone(@PathParam("phoneNumber") int phoneNumber) {
        Company c = f.getCompanyFromPhone(phoneNumber);
        JsonObject co = createCompanyObject(c);    
        String jasonCompany =  gson.toJson(co, JsonObject.class);
        return jasonCompany;
    }
    
    @POST
    @Consumes("application/json")
    @Path("/create")
    public void createCompanyAndAddress(String content){//json: name, description, cvr, numemployees, marketvalue, email, street, additionalinfo, zipcode
        JsonObject jo = new JsonParser().parse(content).getAsJsonObject();
        Company c = f.createCompany(jo.get("name").getAsString(), jo.get("description").getAsString(),jo.get("cvr").getAsInt(),jo.get("numemployees").getAsInt(),jo.get("marketvalue").getAsInt(), jo.get("email").getAsString());
        c = f.createAddressForCompany(c, jo.get("street").getAsString(), jo.get("additionalinfo").getAsString(),jo.get("zipcode").getAsInt());
    }
    
     @GET
    @Produces("application/json")
    @Path("cvr/{cvr}")
    public String getCompanyFromCVR(@PathParam("cvr") int cvr) {
        Company c = f.getCompanyFromcvr(cvr);
        JsonObject co = createCompanyObject(c);    
        String jasonCompany =  gson.toJson(co, JsonObject.class);
        return jasonCompany;
    }
    
    @POST
    @Consumes("application/json")
    @Path("phone")
    public void addPhoneToCompany(String content) { //json: id, number, description
        JsonObject jo = new JsonParser().parse(content).getAsJsonObject();
        Company c = f.getCompany(jo.get("id").getAsInt());
        f.addPhoneCompany(c,jo.get("description").getAsString() , jo.get("number").getAsInt());
    }
    
    @DELETE
    @Consumes("application/json")
    @Path("phone")
    public void deletePhone(String content) {
         Type type = new TypeToken<List<Integer>>() {
        }.getType();
        List<Integer> iList = gson.fromJson(content, type);
        f.deleteCompanyPhone(iList.get(0).intValue());
    }
    
    @DELETE
    @Consumes("application/json")
    @Path("/delete")
    public void deleteCompany(String content) {
         Type type = new TypeToken<List<Integer>>() {
        }.getType();
        List<Integer> iList = gson.fromJson(content, type);
        f.deleteCompany(iList.get(0).intValue());
    }
    
    @PUT
    @Consumes("application/json")
    @Path("address")
    public void changeAddressForCompany(String content) { //json: id, street, additionalinfo, zipcode
        JsonObject jo = new JsonParser().parse(content).getAsJsonObject();
        f.changeAddressFromCompany(jo.get("id").getAsInt(), jo.get("street").getAsString(), jo.get("additionalinfo").getAsString(), jo.get("zipcode").getAsInt());
    }
}
