package com.wu.immortal.half.configs;

import com.google.gson.JsonObject;

import java.io.*;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class PropertieUtil {

    static String propertieToJson(String propertiePath) throws IOException {

        Properties properties = new Properties();
        InputStream resourceAsStream = new FileInputStream(new File(propertiePath));
        properties.load(resourceAsStream);
        Enumeration enumeration = properties.propertyNames();
        JsonObject jsonObject = new JsonObject();
        while (enumeration.hasMoreElements()) {
            String key = (String) enumeration.nextElement();
            String value = properties.getProperty(key);
            jsonObject.addProperty(key, value);
        }

        properties.clear();

        try {
            if (resourceAsStream.available() == 0) {
                resourceAsStream.close();
            }
        } catch (IOException ignored) {}

        return jsonObject.toString();

    }

    static void mapToPropertie(Map<String, Object> map, String propertiePath) {

        Properties properties = new Properties();
        Set<String> strings = map.keySet();
        for(String key : strings) {
            String value = map.get(key).toString();
            properties.setProperty(key, value);
        }

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(propertiePath);
            properties.store(fileOutputStream, "config info");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            properties.clear();
        }
    }

}
