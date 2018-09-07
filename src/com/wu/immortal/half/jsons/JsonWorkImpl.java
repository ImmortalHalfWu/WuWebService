package com.wu.immortal.half.jsons;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JsonWorkImpl implements JsonWorkInterface {

    private volatile static JsonWorkInterface jsonWork;
    private Gson gson;

    private JsonWorkImpl(){
        gson = new Gson();
    }

    public static JsonWorkInterface newInstance() {
        if (jsonWork == null) {
            synchronized (JsonWorkImpl.class) {
                jsonWork = new JsonWorkImpl();
            }
        }
        return jsonWork;
    }

    @Override
    public String toJsonString(Object object) {
        synchronized (this) {
            return gson.toJson(object);
        }
    }

    @Override
    public <T> T jsonToBean(String jsonString) {
        synchronized (this) {
            return gson.fromJson(jsonString, new TypeToken<T>(){}.getType());
        }
    }
}
