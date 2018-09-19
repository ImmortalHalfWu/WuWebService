package com.wu.immortal.half.sql.dao.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.sun.istack.internal.NotNull;
import com.wu.immortal.half.sql.bean.SqlQueryBean;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DaoUtil {

    public static Map<String, Object> object2Map(@NotNull Object object) {

        PropertyFilter propertyFilter = (o, s, o1) -> o1 != null && !o1.getClass().isEnum();
        String objectJson = JSON.toJSONString(object, propertyFilter);

        JSONObject jsonObject =  (JSONObject) JSON.parse(objectJson);
        Set<Map.Entry<String,Object>> entrySet = jsonObject.entrySet();
        Map<String, Object> map = new HashMap<>();
        for (Map.Entry<String, Object> entry : entrySet) {
            map.put(entry.getKey(), entry.getValue());
        }

        return map;

    }

    /**
     * Object 封装为SQLbean
     * @param tableName 表名
     * @param object boject
     * @return
     */
    public static SqlQueryBean object2SqlBean(@NotNull String tableName, @NotNull Object object) {

        Map<String, Object> stringStringMap = object2Map(object);

        return new SqlQueryBean(object.getClass(), tableName, stringStringMap);

    }
}
