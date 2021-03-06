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
import javax.ws.rs.PathParam;
import rest.exception.EntityNotFoundException;
import rest.exception.NotNumericException;

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

     @GET
    @Produces("application/json")
    @Path("/zip/{zip}")
    public String getAllPersonsFromZip(@PathParam("zip")int zip) throws EntityNotFoundException {
        List<Person> listP = f.getAllPersonsInCity(zip);
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
    public void createPersonAndAddressPhone(String content) throws EntityNotFoundException, NotNumericException { //json: firstname, lastname, email, street, additional info, zipcode
        JsonObject jo = new JsonParser().parse(content).getAsJsonObject();
        boolean isNumeric = true;
        int z;
        int ph;
        try {
            String zip = jo.get("zipcode").getAsString();
            z = Integer.parseInt(zip);
        } catch (NumberFormatException nfe) {
            isNumeric = false;
            throw new NotNumericException("Zipcode must be a number");
        }
        try {
            String phone = jo.get("phone").getAsString();
            ph = Integer.parseInt(phone);
        } catch (NumberFormatException nfe) {
            isNumeric = false;
            throw new NotNumericException("Phonenumber must be a number");
        }

        if (isNumeric && f.getCityInfo(z) != null) {
            Person p = f.createPerson(jo.get("firstname").getAsString(), jo.get("lastname").getAsString(), jo.get("email").getAsString());
            p = f.createAddressForPerson(p, jo.get("street").getAsString(), jo.get("additionalinfo").getAsString(), jo.get("zipcode").getAsInt());
            f.addPhonePerson(p, jo.get("phoneinfo").getAsString(), jo.get("phone").getAsInt());
        }
    }

    @DELETE
    @Consumes("application/json")
    @Path("delete")
    public void deletePerson(String content) throws EntityNotFoundException, NotNumericException {
//        Type type = new TypeToken<List<Integer>>() {
//        }.getType();
//        List<Integer> iList = gson.fromJson(content, type);
//        Integer[] intArray = iList.toArray(new Integer[0]);
//        int id = intArray[0];
        JsonObject jo = new JsonParser().parse(content).getAsJsonObject();
        int id;
        boolean isNumeric = true;
        try {
            String personid = jo.get("id").getAsString();
            id = Integer.parseInt(personid);
        } catch (NumberFormatException nfe) {
            isNumeric = false;
            throw new NotNumericException("Person ID not an integer.");
        }

        if (isNumeric) {
            f.deletePerson(id);
        }
    }

    @GET
    @Produces("application/json")
    @Path("{id}")
    public String getPersonFromId(@PathParam("id") int id) throws EntityNotFoundException {
        Person p = f.getPerson(id);
        JsonObject jo = createJsonObjectfromPerson(p);
        String jsonString = gson.toJson(jo);
        return jsonString;
    }

    @POST
    @Consumes("application/json")
    @Path("address")
    public void changeAddress(String content) throws EntityNotFoundException, NotNumericException { //json: id, street, additionalinfo, zipcode
        JsonObject jo = new JsonParser().parse(content).getAsJsonObject();
        int z;
        int ph;
        boolean isNumeric = true;
        try {
            String phone = jo.get("id").getAsString();
            ph = Integer.parseInt(phone);
        } catch (NumberFormatException nfe) {
            isNumeric = false;
            throw new NotNumericException("PersonID must be a number");
        }
        try {
            String zip = jo.get("zipcode").getAsString();
            z = Integer.parseInt(zip);
        } catch (NumberFormatException nfe) {
            isNumeric = false;
            throw new NotNumericException("Zipcode must be a number");
        }

        if (isNumeric && f.getCityInfo(z) != null) {
            
            Person p = f.changeAddressFromPerson(jo.get("id").getAsInt(), jo.get("street").getAsString(), jo.get("additionalinfo").getAsString(), jo.get("zipcode").getAsInt());
        }
    }

    @POST
    @Consumes("application/json")
    @Path("phone")
    public void addPhoneToPerson(String content) throws EntityNotFoundException { //json: id, number, description
        JsonObject jo = new JsonParser().parse(content).getAsJsonObject();
        Person p = f.getPerson(jo.get("id").getAsInt());
        f.addPhonePerson(p, jo.get("description").getAsString(), jo.get("number").getAsInt());

    }

    @DELETE
    @Consumes("application/json")
    @Path("phone")
    public void deletePhone(String content) throws EntityNotFoundException {
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
    public String getPersonFromPhone(@PathParam("id") int phonenumber) throws EntityNotFoundException {
        Person p = f.getPersonFromPhone(phonenumber);
        JsonObject jo = createJsonObjectfromPerson(p);
        String jsonString = gson.toJson(jo);
        return jsonString;
    }

    @POST
    @Consumes("application/json")
    @Path("hobby/create")
    public void createHobby(String content) throws EntityNotFoundException { //json: name, description
        JsonObject jo = new JsonParser().parse(content).getAsJsonObject();
        f.createHobbies(jo.get("name").getAsString(), jo.get("description").getAsString());
        }

    @GET
    @Produces("application/json")
    @Path("hobby/count/{name}")
    public String getCountOfPeopleWithHobby(@PathParam("name") String name) throws EntityNotFoundException {
        Hobby h = f.getHobbiesFromID(name);
        int count = f.getCountOfPeopleWithHobby(h);
        String json = String.valueOf(count);
        return gson.toJson(json);

    }

    @POST
    @Consumes("application/json")
    @Path("hobby/add")
    public void addHobbyToPerson(String content) throws EntityNotFoundException { // json: personid, hobbyName
        JsonObject jo = new JsonParser().parse(content).getAsJsonObject();
        Person p = f.getPerson(jo.get("personid").getAsInt());
        Hobby h = f.getHobbiesFromID(jo.get("hobbyName").getAsString());
        if (h == null){
        h = f.createHobbies(jo.get("hobbyName").getAsString(), "not set");
        }
        f.addHobbyToPerson(p, h);
    }

    @DELETE
    @Consumes("application/json")
    @Path("hobby/delete")
    public void deleteHobbyFromDB(String content) throws EntityNotFoundException { // json: name
        JsonObject jo = new JsonParser().parse(content).getAsJsonObject();
        f.deleteHobbyFromDB(jo.get("name").getAsString());

    }

    @GET
    @Produces("application/json")
    @Path("hobby/person/{name}")
    public String getAllPersonfromhobby(@PathParam("name") String name) throws EntityNotFoundException {
        List<Person> plist = f.getAllPersonsWithHobby(name);
        JsonArray persons = new JsonArray();
        for (Person person : plist) {
            JsonObject po = createJsonObjectfromPerson(person);
            persons.add(po);
        }
        return gson.toJson(persons);

    }

    @POST
    @Consumes("application/json")
    @Path("/hobby")
    public void removehobbyFromPerson(String content) throws EntityNotFoundException { //json: personid, hobbyName
        JsonObject jo = new JsonParser().parse(content).getAsJsonObject();
        f.removeHobbyFromPerson(jo.get("hobbyName").getAsString(),jo.get("personid").getAsInt());
    }

    @GET
    @Produces("application/json")
    @Path("hobby")
    public String getAllhobby() {
        List<Hobby> hlist = f.getAllHobbies();
        JsonArray hobbies = new JsonArray();
        for (Hobby hobby : hlist) {
            JsonObject ho = new JsonObject();
            ho.addProperty("name", hobby.getName());
            ho.addProperty("description", hobby.getDescription());
            hobbies.add(ho);
        }
        return gson.toJson(hobbies);
    }

    @GET
    @Produces("application/json")
    @Path("hobby/{name}")
    public String getHobbyFromId(@PathParam("name") String name) throws EntityNotFoundException {
        Hobby hobby = f.getHobbiesFromID(name);
        JsonObject ho = new JsonObject();
        ho.addProperty("name", hobby.getName());
        ho.addProperty("description", hobby.getDescription());
        JsonArray persons = new JsonArray();
        List<Person> pers = hobby.getPersons();
        for (Person p : pers) {
            JsonObject po = new JsonObject();
            po.addProperty("id", p.getId());
            po.addProperty("firstname", p.getFirstName());
            po.addProperty("lastname", p.getLastName());
            po.addProperty("email", p.getEmail());

            JsonObject address = new JsonObject();
            address.addProperty("id", p.getAddress().getId());
            address.addProperty("street", p.getAddress().getStreet());
            address.addProperty("additionalinfo", p.getAddress().getAdditionalinfo());

            JsonObject city = new JsonObject();
            city.addProperty("zipcode", p.getAddress().getCityInfo().getZipCode());
            city.addProperty("city", p.getAddress().getCityInfo().getCity());
            address.add("cityinfo", city);
            po.add("address", address);

            JsonArray phones = new JsonArray();
            for (Phone ph : p.getPhones()) {
                JsonObject phone = new JsonObject();
                phone.addProperty("number", ph.getNumber());
                phone.addProperty("description", ph.getDescription());
                phones.add(phone);
            }
            po.add("phones", phones);
            persons.add(po);
        }
        ho.add("persons", persons);
        return gson.toJson(ho);
    }

}
