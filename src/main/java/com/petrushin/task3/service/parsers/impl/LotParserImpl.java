package com.petrushin.task3.service.parsers.impl;

import com.petrushin.task3.domain.Lot;
import com.petrushin.task3.service.parsers.Parser;
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

public class LotParserImpl implements Parser<Lot> {

    private static final Logger LOGGER = LogManager.getLogger(LotParserImpl.class);

    /**
     * Parses the JSON file by Path and
     * fills the list with Lot instances
     *
     * @param path - Path to JSON File
     * @return {@link List<Lot>} - the List of Lots
     */
    public List<Lot> parse(String path) {
        JSONParser parser = new JSONParser();
        List<Lot> lots = null;
        try {
            Reader reader = new FileReader(path);
            JSONArray array = (JSONArray) parser.parse(reader);
            lots = createLotList(array);
        } catch (ParseException | IOException e) {
            LOGGER.error("Lot parse ERROR - {}", e);
        }
        return lots;
    }

    private List<Lot> createLotList(JSONArray array) {

        List<Lot> lots = new ArrayList<>();

        for (Object object : array) {
            JSONObject jsonObject = (JSONObject) object;

            Object objId = jsonObject.get("id");
            String idValue = objId.toString();
            int id = Integer.valueOf(idValue);

            Object objPrice = jsonObject.get("price");
            String priceValue = objPrice.toString();
            int price = Integer.valueOf(priceValue);

            Object objLifeTime = jsonObject.get("lifeTime");
            Lot lot;
            if (objLifeTime != null) {
                String lifeTimeValue = objLifeTime.toString();
                int lifeTime = Integer.valueOf(lifeTimeValue);
                lot = new Lot(id, price, lifeTime);
            } else {
                lot = new Lot(id, price);
            }
            lots.add(lot);
        }
        return lots;
    }
}
