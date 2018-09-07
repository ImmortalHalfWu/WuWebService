package com.wu.immortal.half.sql.dao.interfaces;

import com.sun.istack.internal.NotNull;

import java.sql.Connection;
import java.util.List;

public interface SQLDao<T> {
    void addToSQL(@NotNull Connection connection, @NotNull String tableName, @NotNull T bean) throws Exception;
    void deleteFromSQL(@NotNull Connection connection, @NotNull String tableName, @NotNull T bean) throws Exception;
    void updataToSQL(@NotNull Connection connection, @NotNull String tableName, @NotNull T newBean, @NotNull T oldBean) throws Exception;
    List<T> selectFromSQL(@NotNull Connection connection, @NotNull String tableName, @NotNull T bean) throws Exception;
}
