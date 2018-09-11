package com.wu.immortal.half.jsons;

import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.sun.istack.internal.NotNull;

import java.lang.reflect.Type;

public interface JsonWorkInterface {

    String toJsonString(@NotNull Object object);
    JsonElement toJsonElement(@NotNull Object object);
    <T> T jsonToBean(@NotNull String jsonString, Class<T> tClass);

}
