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
import entity.Company;
import entity.Phone;
import facade.Facade;
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
import rest.exception.EntityNotFoundException;
import rest.exception.NotNumericException;

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
    public String getCompany(@PathParam("id") int id) throws EntityNotFoundException {
        Company c = f.getCompany(id);
        JsonObject co = createCompanyObject(c);    
        String jasonCompany =  gson.toJson(co, JsonObject.class);
        return jasonCompany;
    }
    
    @GET
    @Produces("application/json")
    @Path("/phone/{phoneNumber}")
    public String getCompanyFromOPhone(@PathParam("phoneNumber") int phoneNumber) throws EntityNotFoundException {
        Company c = f.getCompanyFromPhone(phoneNumber);
        JsonObject co = createCompanyObject(c);    
        String jasonCompany =  gson.toJson(co, JsonObject.class);
        return jasonCompany;
    }
    
    @POST
    @Consumes("application/json")
    @Path("/create")
    public void createCompanyAndAddress(String content) throws EntityNotFoundException{//json: name, description, cvr, numemployees, marketvalue, email, street, additionalinfo, zipcode, number, phonedescript
        JsonObject jo = new JsonParser().parse(content).getAsJsonObject();
        Company c = f.createCompany(jo.get("name").getAsString(), jo.get("description").getAsString(),jo.get("cvr").getAsInt(),jo.get("numemployees").getAsInt(),jo.get("marketvalue").getAsInt(), jo.get("email").getAsString());
        c = f.createAddressForCompany(c, jo.get("street").getAsString(), jo.get("additionalinfo").getAsString(),jo.get("zipcode").getAsInt());
        c = f.addPhoneCompany(c, jo.get("phonedescript").getAsString(), jo.get("number").getAsInt());
    }
    
     @GET
    @Produces("application/json")
    @Path("cvr/{cvr}")
    public String getCompanyFromCVR(@PathParam("cvr") int cvr) throws EntityNotFoundException {
        Company c = f.getCompanyFromcvr(cvr);
        JsonObject co = createCompanyObject(c);    
        String jasonCompany =  gson.toJson(co, JsonObject.class);
        return jasonCompany;
    }
    
    @POST
    @Consumes("application/json")
    @Path("phone")
    public void addPhoneToCompany(String content) throws EntityNotFoundException { //json: id, number, description
        JsonObject jo = new JsonParser().parse(content).getAsJsonObject();
        Company c = f.getCompany(jo.get("id").getAsInt());
        f.addPhoneCompany(c,jo.get("description").getAsString() , jo.get("number").getAsInt());
    }
    
    @DELETE
    @Consumes("application/json")
    @Path("phone")
    public void deletePhone(String content) throws EntityNotFoundException, NotNumericException {
        JsonObject jo = new JsonParser().parse(content).getAsJsonObject();
        int phoneNumber;
        boolean isNumeric = true;
        try {
            String number = jo.get("id").getAsString();
            phoneNumber = Integer.parseInt(number);
        } catch (NumberFormatException nfe) {
            isNumeric = false;
            throw new NotNumericException("Phonenumber not an integer.");
        }

        if (isNumeric) {
            f.deleteCompanyPhone(phoneNumber);
        }
    }
    
    @DELETE
    @Consumes("application/json")
    @Path("/delete")
    public void deleteCompany(String content) throws EntityNotFoundException, NotNumericException {
        JsonObject jo = new JsonParser().parse(content).getAsJsonObject();
        int id;
        boolean isNumeric = true;
        try {
            String companyId = jo.get("id").getAsString();
            id = Integer.parseInt(companyId);
        } catch (NumberFormatException nfe) {
            isNumeric = false;
            throw new NotNumericException("Phonenumber not an integer.");
        }

        if (isNumeric) {
            f.deleteCompany(id);
        }
    }
    
    @POST
    @Consumes("application/json")
    @Path("address")
    public void changeAddressForCompany(String content) throws EntityNotFoundException { //json: id, street, additionalinfo, zipcode
        JsonObject jo = new JsonParser().parse(content).getAsJsonObject();
        f.changeAddressFromCompany(jo.get("id").getAsInt(), jo.get("street").getAsString(), jo.get("additionalinfo").getAsString(), jo.get("zipcode").getAsInt());
    }
}
