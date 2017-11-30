package com.petrushin.task3.service.parsers;

import com.petrushin.task3.domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class UserParser {


    private static final Logger LOGGER = LogManager.getLogger(UserParser.class);

    /**
     * Parses the JSON file by Path and
     * fills the list with User instances
     *
     * @param path - Path to JSON File
     * @return {@link List<User>} - the List of Users
     */
    public List<User> parse(String path) {
        JSONParser parser = new JSONParser();
        List<User> users = null;
        try {
            Reader reader = new FileReader(path);
            JSONArray array = (JSONArray) parser.parse(reader);
            users = createUserList(array);

        } catch (ParseException | IOException e) {
            LOGGER.error("User parse ERROR {}", e);
        }
        return users;
    }

    private List<User> createUserList(JSONArray array) {
        List<User> users = new ArrayList<>();
        for (Object object : array) {
            JSONObject jsonObject = (JSONObject) object;

            Object objName = jsonObject.get("name");
            String userName = objName.toString();

            Object objCash = jsonObject.get("cash");
            String userCashValue = objCash.toString();
            int cash = Integer.valueOf(userCashValue);

            User user = new User(cash, userName);
            users.add(user);
        }
        return users;
    }

}
