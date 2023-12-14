package hobby_garden.hobby_garden_server.common.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.mapper.Mapper;
import org.bson.json.JsonObject;

import java.util.HashMap;
import java.io.BufferedReader;
import java.io.IOException;

public class LogMapper  {

    public Object stringToObject(String body) {
        try {
            //* object mapper, convert object to hashmap
            ObjectMapper mapper = new ObjectMapper();
            //* mapper.readValue(requestBody.toString(), HashMap.class) - convert requestBody to HashMap
            //* mapper.convertValue(mapper.readValue(requestBody.toString(), HashMap.class), Object.class) - convert HashMap to
            return mapper.convertValue(mapper.readValue(body, HashMap.class), Object.class);
        }
        catch (Exception e) {
            System.out.println("Error: " + e);
            return new Object();
        }
    }
}
