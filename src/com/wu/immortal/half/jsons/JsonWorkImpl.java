package com.wu.immortal.half.jsons;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.wu.immortal.half.utils.LogUtil;

import java.lang.reflect.Type;

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
                LogUtil.i("JsonWorkImpl初始化完成");
            }
        }

        return jsonWork;
    }

    @Override
    public String toJsonString(Object object)  throws JsonSyntaxException {
        synchronized (this) {
            return gson.toJson(object);
        }
    }

    @Override
    public JsonElement toJsonElement(Object object) throws JsonSyntaxException {
        synchronized (this) {
            return gson.toJsonTree(object);
        }
    }

    @Override
    public <T> T jsonToBean(String jsonString, Class<T> tClass) throws JsonSyntaxException{
        synchronized (this) {
            return gson.fromJson(jsonString, tClass);
        }
    }
}
