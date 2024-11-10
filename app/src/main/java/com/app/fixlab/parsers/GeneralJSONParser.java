package com.app.fixlab.parsers;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * A utility class to parse JSON data from resources in an Android application.
 * Provides methods to read JSON data and convert it into different structures.
 */
public class GeneralJSONParser {

    /**
     * Reads a JSON array from a resource file and returns it as a {@link JSONArray}.
     *
     * @param context The context to access the resources.
     * @param resourceID The resource ID of the JSON file.
     * @return A {@link JSONArray} containing the JSON data from the resource file.
     * @throws JSONException If the JSON data is malformed or cannot be parsed.
     */
    public static JSONArray readJSONArray(Context context, int resourceID) throws JSONException {
        String jsonContent = readJSONFileFromResources(context, resourceID);
        return new JSONArray(new JSONTokener(jsonContent));
    }

    /**
     * Reads a JSON array from a resource file and converts it into a list of maps
     * where each map represents an object in the JSON array with key-value pairs.
     *
     * @param context The context to access the resources.
     * @param resourceID The resource ID of the JSON file.
     * @return A list of {@link HashMap} objects, where each map contains key-value pairs
     *         representing an object in the JSON array.
     * @throws JSONException If the JSON data is malformed or cannot be parsed.
     */
    public static List<HashMap<String, Object>> readData(Context context, int resourceID) throws JSONException {
        String jsonContent = readJSONFileFromResources(context, resourceID);
        JSONArray jsonArray = new JSONArray(new JSONTokener(jsonContent));

        List<HashMap<String, Object>> dataList = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            // HashMap to store key-value pairs from the JSON object
            HashMap<String, Object> dataMap = new HashMap<>();

            // Iterate through the keys of the JSON object and store key-value pairs in the map
            Iterator<String> keys = jsonObject.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                dataMap.put(key, jsonObject.get(key));
            }

            dataList.add(dataMap);
        }
        return dataList;
    }

    /**
     * Reads the content of a JSON file from the app's resources and returns it as a string.
     *
     * @param context The context to access the resources.
     * @param resourceID The resource ID of the JSON file.
     * @return A string containing the JSON data from the resource file.
     * @throws RuntimeException If there is an error reading the file.
     */
    public static String readJSONFileFromResources(Context context, int resourceID) {
        String json;
        InputStream inputStream = context.getResources().openRawResource(resourceID);
        try {
            int size = inputStream.available();

            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ioe) {
            Log.e(GeneralJSONParser.class.getSimpleName(), ioe.getMessage());
            throw new RuntimeException("Error reading JSON file from resources");
        }
        return json;
    }
}
