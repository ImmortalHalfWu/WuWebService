package com.wu.immortal.half.sql.bean;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class SqlQueryBean {

    private final Class<?> beanClass;
    private final String tableName;
    // 列名
    private final Set<String> keys;
    // 值
    private final Collection<Object> values;

    public SqlQueryBean(Class<?> beanClass, String tableName, Map<String, Object> keyValue) {
        this.beanClass = beanClass;
        this.tableName = tableName;
        keys = keyValue.keySet();
        values = keyValue.values();
    }

    public String getTableName() {
        return tableName;
    }

    public Set<String> getKeys() {
        return keys;
    }

    public Collection<Object> getValues() {
        return values;
    }

    public Class<?> getBeanClass() {
        return beanClass;
    }

    @Override
    public String toString() {
        return "SqlQueryBean{" +
                "beanClass=" + beanClass +
                ", tableName='" + tableName + '\'' +
                ", keys=" + keys +
                ", values=" + values +
                '}';
    }
}
