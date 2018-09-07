package com.wu.immortal.half.jsons;

import com.sun.istack.internal.NotNull;

public interface JsonWorkInterface {

    String toJsonString(@NotNull Object object);
    <T> T jsonToBean(@NotNull String jsonString);

}
