package com.wu.immortal.half.jsons;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wu.immortal.half.utils.LogUtil;

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
        LogUtil.i("JsonWorkImpl初始化完成");

        return jsonWork;
    }

    @Override
    public String toJsonString(Object object) {
        synchronized (this) {
            String jsonString = gson.toJson(object);
            LogUtil.i(jsonString);
            return jsonString;
        }
    }

    @Override
    public <T> T jsonToBean(String jsonString) {
        synchronized (this) {
            T o = gson.fromJson(jsonString, new TypeToken<T>() {
            }.getType());
            LogUtil.i(o);
            return o;
        }
    }
}
