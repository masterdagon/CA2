/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import entity.Address;
import entity.Company;
import entity.Hobby;
import entity.Person;
import entity.Phone;
import facade.Facade;
import java.lang.reflect.Type;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;

/**
 * REST Web Service
 *
 * @author Dennnis
 */
@Path("person")
public class PersonResource {

    @Context
    private UriInfo context;
    private Facade f;
    private Gson gson;

    public PersonResource() {
        f = new Facade();
        gson = new Gson();
    }

    @GET
    @Produces("application/json")
    public String allPersons() {
        List<Person> listP = f.getAllPersons();
        JsonArray ja = new JsonArray();
        for (Person p : listP) {
            JsonObject jo = createJsonObjectfromPerson(p);
            ja.add(jo);
        }

        String jsonString = gson.toJson(ja);
        return jsonString;

    }

    @POST
    @Consumes("application/json")
    @Path("create")
    public void createPersonAndAddress(String content) { //json: firstname, lastname, email, street, additional info, zipcode
        JsonObject jo = new JsonParser().parse(content).getAsJsonObject();
        Person p = f.createPerson(jo.get("firstname").getAsString(), jo.get("lastname").getAsString(), jo.get("email").getAsString());
        p = f.createAddressForPerson(p, jo.get("street").getAsString(), jo.get("additionalinfo").getAsString(), jo.get("zipcode").getAsInt());
    }

    @DELETE
    @Consumes("application/json")
    @Path("delete")
    public void deletePerson(String content) {
        Type type = new TypeToken<List<Integer>>() {
        }.getType();
        List<Integer> iList = gson.fromJson(content, type);
        Integer[] intArray = iList.toArray(new Integer[0]);
        int id = intArray[0];
        f.deletePerson(id);

    }

    @GET
    @Produces("application/json")
    @Path("{id}")
    public String getPersonFromId(@PathParam("id") int id) {
        Person p = f.getPerson(id);
        JsonObject jo = createJsonObjectfromPerson(p);
        String jsonString = gson.toJson(jo);
        return jsonString;
    }

    @PUT
    @Consumes("application/json")
    @Path("address")
    public void changeAddress(String content) { //json: id, street, additionalinfo, zipcode
        JsonObject jo = new JsonParser().parse(content).getAsJsonObject();
        Person p = f.changeAddressFromPerson(jo.get("id").getAsInt(), jo.get("street").getAsString(), jo.get("additionalinfo").getAsString(), jo.get("zipcode").getAsInt());
    }

    @POST
    @Consumes("application/json")
    @Path("phone")
    public void addPhoneToPerson(String content) { //json: id, number, description
        JsonObject jo = new JsonParser().parse(content).getAsJsonObject();
        Person p = f.getPerson(jo.get("id").getAsInt());
        f.addPhonePerson(p, jo.get("description").getAsString(), jo.get("number").getAsInt());

    }

    @DELETE
    @Consumes("application/json")
    @Path("phone")
    public void deletePhone(String content) {
        Type type = new TypeToken<List<Integer>>() {
        }.getType();
        List<Integer> iList = gson.fromJson(content, type);
        Integer[] intArray = iList.toArray(new Integer[0]);
        int phoneNumber = intArray[0];
        f.deletePersonPhone(phoneNumber);

    }

    public JsonObject createJsonObjectfromPerson(Person p) {
        JsonObject jo = new JsonObject();
        jo.addProperty("id", p.getId());
        jo.addProperty("firstname", p.getFirstName());
        jo.addProperty("lastname", p.getLastName());
        jo.addProperty("email", p.getEmail());

        JsonObject address = new JsonObject();
        address.addProperty("id", p.getAddress().getId());
        address.addProperty("street", p.getAddress().getStreet());
        address.addProperty("additionalinfo", p.getAddress().getAdditionalinfo());

        JsonObject city = new JsonObject();
        city.addProperty("zipcode", p.getAddress().getCityInfo().getZipCode());
        city.addProperty("city", p.getAddress().getCityInfo().getCity());
        address.add("cityinfo", city);
        jo.add("address", address);

        JsonArray phones = new JsonArray();
        for (Phone ph : p.getPhones()) {
            JsonObject phone = new JsonObject();
            phone.addProperty("number", ph.getNumber());
            phone.addProperty("description", ph.getDescription());
            phones.add(phone);
        }
        jo.add("phones", phones);

        JsonArray hobbies = new JsonArray();
        for (Hobby h : p.getHobbies()) {
            JsonObject hobby = new JsonObject();
            hobby.addProperty("id", h.getId());
            hobby.addProperty("name", h.getName());
            hobby.addProperty("description", h.getDescription());
            hobbies.add(hobby);
        }
        jo.add("hobbies", hobbies);
        return jo;
    }

    @GET
    @Produces("application/json")
    @Path("phone/{id}")
    public String getPersonFromPhone(@PathParam("id") int phonenumber) {
        Person p = f.getPersonFromPhone(phonenumber);
        JsonObject jo = createJsonObjectfromPerson(p);
        String jsonString = gson.toJson(jo);
        return jsonString;
    }

    @POST
    @Consumes("application/json")
    @Path("hobby/create")
    public void createHobby(String content) { //json: name, description
        JsonObject jo = new JsonParser().parse(content).getAsJsonObject();
        f.createHobbies(jo.get("name").getAsString(), jo.get("description").getAsString());

    }

    @GET
    @Produces("application/json")
    @Path("hobby/count/{id}")
    public String getCountOfPeopleWithHobby(@PathParam("id") int id) {
        Hobby h = f.getHobbiesFromID(id);
        int count = f.getCountOfPeopleWithHobby(h);
        String json = String.valueOf(count);
        return gson.toJson(json);

    }

    @POST
    @Consumes("application/json")
    @Path("hobby/add")
    public void addHobbyToPerson(String content) { // json: personid, hobbyid
        JsonObject jo = new JsonParser().parse(content).getAsJsonObject();
        Person p = f.getPerson(jo.get("personid").getAsInt());
        Hobby h = f.getHobbiesFromID(jo.get("hobbyid").getAsInt());
        f.addHobbyToPerson(p, h);
    }
    
    @DELETE
    @Consumes("application/json")
    @Path("hobby/delete")
    public void deleteHobbyFromDB(String content) { // json: id
        JsonObject jo = new JsonParser().parse(content).getAsJsonObject();
        f.deleteHobbyFromDB(jo.get("id").getAsInt());
        
    }
    
    @GET
    @Produces("application/json")
    @Path("hobby/{id}")
    public String getAllPersonsFromHobby(@PathParam("id") int id){
        List<Person> plist = f.getAllPersonsWithHobby(id);
        JsonArray persons = new JsonArray();
        for (Person person : plist) {
            JsonObject po = createJsonObjectfromPerson(person);
            persons.add(po);
        }
        return gson.toJson(persons);
        
    }
    
    

}
