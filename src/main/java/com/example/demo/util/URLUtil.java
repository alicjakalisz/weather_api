package com.example.demo.util;

import com.example.demo.Exception.SearchException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


// zczytujemy content z URL do stringa a potem jak juz mamy json stringa to do json object
public class URLUtil {

    public static JsonObject convertURLIntoJsonObject(String URLString) throws IOException, SearchException {
        URL url = new URL(URLString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        if(conn.getResponseCode()!=200){
            throw new SearchException("No results for search of these parameters");
        }
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        StringBuilder result = new StringBuilder();
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(result.toString()).getAsJsonObject();
        return jsonObject;

    }
}
