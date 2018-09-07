package com.wu.immortal.half.sql.dao.utils;

import com.alibaba.fastjson.JSONArray;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import com.wu.immortal.half.sql.bean.SqlQueryBean;

import java.sql.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class SqlUtil {

    private static void sqlClose(@Nullable Statement statement, @Nullable ResultSet resultSet) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    // =================================== =增= ================================================
    public static void insertObjectToSQL(@NotNull Connection connection, @NotNull SqlQueryBean sqlQueryBean)throws SQLException {
        String queryString = createInsertQueryString(QUERY_STRING_ADD, sqlQueryBean);
        System.out.println(
                queryString
        );

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        if (connection != null) {

            try {
                statement = connection.prepareStatement(
                        queryString
                );
                setInsertQueryValueData(statement, sqlQueryBean.getValues()).executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            } finally {
                sqlClose(statement, resultSet);
            }

        }
    }

    private static String createInsertQueryString(@NotNull String queryTemp, @NotNull SqlQueryBean sqlQueryBean) {
        // 表名
        String tableName = sqlQueryBean.getTableName();
        // 列名
        Set<String> keys = sqlQueryBean.getKeys();
        // 值
        Collection<Object> values = sqlQueryBean.getValues();

        if (queryTemp.contains(QUERY_PLEACE_HOLD_KEY)) {
            String queryKey = keys.toString().replaceAll("[\\[\\]]", "");
            queryTemp = queryTemp.replace(QUERY_PLEACE_HOLD_KEY, queryKey);
        }

        if (queryTemp.contains(QUERY_PLEACE_HOLD_VALUE)) {
            String[] strings1 = new String[values.size()];
            Arrays.fill(strings1, "?");
            String queryValue = Arrays.toString(strings1).replaceAll("[\\[\\]]", "");
            queryTemp = queryTemp.replace(QUERY_PLEACE_HOLD_VALUE, queryValue);
        }

        return queryTemp.replace(QUERY_PLEACE_HOLD_TABLE, tableName);
    }

    private static PreparedStatement setInsertQueryValueData(@NotNull PreparedStatement statement, @NotNull Collection<Object> values) throws SQLException {
        int parameterIndex = 0;
        for (Object next : values) {
            parameterIndex ++;
            if (next instanceof Integer) {
                statement.setInt(parameterIndex, (Integer) next);
            }
            if (next instanceof String) {
                statement.setString(parameterIndex, (String) next);
            }
            if (next instanceof Boolean) {
                statement.setBoolean(parameterIndex, (Boolean) next);
            }

        }
        return statement;
    }


    // =================================== =删= ================================================
    public static void delObjectToSQL(@NotNull Connection connection, @NotNull SqlQueryBean sqlQueryBean) throws SQLException {
        // 生成删除语句
        String queryString = QUERY_STRING_DELETE
                .replace(QUERY_PLEACE_HOLD_TABLE, sqlQueryBean.getTableName());

        Set<String> keys = sqlQueryBean.getKeys();
        Collection<Object> values = sqlQueryBean.getValues();

        // 如果有约束则添加约束
        if (keys.size() != 0 && values.size() != 0 && values.size() == keys.size()) {
            queryString = queryString
                    .replace(QUERT_PLEACE_HOLD_WHERE, createWhereQueryString(sqlQueryBean.getKeys(), sqlQueryBean.getValues(), QUERY_STRING_JOIN_AND));
        } else {
            // 如果没有约束条件则移除where
            queryString = queryString.replace(QUERY_STRING_WHERE, "").replace(QUERT_PLEACE_HOLD_WHERE, "");
        }


        System.out.println(queryString);

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        if (connection != null) {

            try {
                statement = connection.prepareStatement(
                        queryString
                );
                statement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            } finally {
                sqlClose(statement, resultSet);
            }
        }

    }

    // =================================== =改= ================================================
    public static void updataObjectToSQL(
            @NotNull Connection connection,
            @NotNull SqlQueryBean sqlQueryBeanNewValue,
            @NotNull SqlQueryBean sqlQueryBeanOldValue
    ) throws IllegalArgumentException, SQLException{

        // 生成删除语句
        String queryString = QUERY_STRING_UPADTA
                .replace(QUERY_PLEACE_HOLD_TABLE, sqlQueryBeanNewValue.getTableName());

        Set<String> newKeys = sqlQueryBeanNewValue.getKeys();
        Set<String> oldKeys = sqlQueryBeanOldValue.getKeys();
        Collection<Object> newValues = sqlQueryBeanNewValue.getValues();
        Collection<Object> oldValues = sqlQueryBeanOldValue.getValues();

        if (oldKeys == null || oldKeys.size() == 0) {
            throw new IllegalArgumentException("更新指定列失败，指定列信息为null");
        }

        if (newKeys == null || newValues.size() == 0) {
            throw new IllegalArgumentException("更新指定列失败，新数据为null");
        }

        queryString = queryString
                .replace(QUERY_STRING_NEW_VALUE, createWhereQueryString(newKeys, newValues, QUERY_STRING_JOIN))
                .replace(QUERY_STRING_OLD_VALUE, createWhereQueryString(oldKeys, oldValues, QUERY_STRING_JOIN_AND));


        System.out.println(queryString);

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        if (connection != null) {

            try {
                statement = connection.prepareStatement(
                        queryString
                );
                statement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            } finally {
                sqlClose(statement, resultSet);
            }
        }
    }


    // =================================== =查= ================================================
    public static JSONArray selectObjectToSQL(@NotNull Connection connection, @NotNull SqlQueryBean sqlQueryBean) throws SQLException{

        // 生成查表语句
        String queryString = QUERY_STRING_SELECT
                .replace(QUERY_PLEACE_HOLD_TABLE, sqlQueryBean.getTableName());

        Set<String> keys = sqlQueryBean.getKeys();
        Collection<Object> values = sqlQueryBean.getValues();

        // 如果有约束则添加约束
        if (keys.size() != 0 && values.size() != 0 && values.size() == keys.size()) {
            queryString = queryString
                    .replace(QUERT_PLEACE_HOLD_WHERE, createWhereQueryString(sqlQueryBean.getKeys(), sqlQueryBean.getValues(), QUERY_STRING_JOIN_AND));
        } else {
            // 如果没有约束条件则移除where
            queryString = queryString.replace(QUERY_STRING_WHERE, "").replace(QUERT_PLEACE_HOLD_WHERE, "");
        }

        System.out.println(queryString);

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        if (connection != null) {

            try {
                statement = connection.prepareStatement(
                        queryString
                );

                resultSet = statement.executeQuery();

                // 查询结果转换为JsonArray
                JSONArray beansJsonArry = ResultSetUtil.getBeansJsonArray(sqlQueryBean.getBeanClass(), resultSet);
                System.out.println(beansJsonArry);
                return beansJsonArry;
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            } finally {
                sqlClose(statement, resultSet);
            }
        }
        return new JSONArray();
    }

    /**
     * 通过键值对生成约束语句
     * @param keys
     * @param values
     * @return
     */
    private static String createWhereQueryString(@NotNull Set<String> keys, @NotNull Collection<Object> values, @NotNull String joinString) {

        if (keys.size() == 0 || values.size() == 0 || keys.size() != values.size()) {
            return "";
        }
        StringBuilder whereQueryString;
        /*
            [orderType, id, tagName, userId, canUser, frequency]
            [2, 1, tag, 1, false, 1]
         */
        Iterator<String> keyIterator = keys.iterator();
        Iterator<Object> valueIterator = values.iterator();

        /*
            orderType
            2
         */
        String firstKey = keyIterator.next();
        Object firstValue = valueIterator.next();

        /*
            "orderType = 2"
         */
        whereQueryString = new StringBuilder(firstKey).append(" = ").append(getWhereValueString(firstValue));

        while (keyIterator.hasNext() && valueIterator.hasNext()) {
            // "orderType = 2 AND"
            whereQueryString.append(joinString);
            // key == id, value == 1
            String key = keyIterator.next();
            Object value = getWhereValueString(valueIterator.next());
            // "orderType = 2 AND id = 1"
            whereQueryString.append(key).append(" = ").append(value);
        }

        return whereQueryString.toString();
    }

    /**
     * 获取约束条件中值得字符串， 区分String，bool， int
     * @param value
     * @return
     */
    private static String getWhereValueString(@NotNull Object value) {
        if (value instanceof Integer) {
            return String.valueOf(value);
        }
        if (value instanceof Boolean) {
            return String.valueOf(value).toUpperCase();
        }

        return ("'" + value + "'");
    }





    // 占位符相关
    private static final String QUERY_PLEACE_HOLD_TABLE = "&TABLE&";
    private static final String QUERY_PLEACE_HOLD_KEY = "&KEY&";
    private static final String QUERY_PLEACE_HOLD_VALUE = "&VALUE&";
    private static final String QUERT_PLEACE_HOLD_WHERE = "&WHERE&";

    private static final String QUERY_STRING_WHERE = " WHERE ";
    private static final String QUERY_STRING_SET = " SET ";
    private static final String QUERY_STRING_NEW_VALUE = "&NEW_VALUE&";
    private static final String QUERY_STRING_OLD_VALUE = "&OLD_VALUE&";

    private static final String QUERY_STRING_JOIN_AND = " AND ";
    private static final String QUERY_STRING_JOIN = ", ";



    /**
     * "insert into login_user (id, name, password)"
     *                                 + "values (?,?,?)"
     */
    private static final String QUERY_STRING_ADD =
            "INSERT INTO "
                    + QUERY_PLEACE_HOLD_TABLE
                    +" ("
                    + QUERY_PLEACE_HOLD_KEY
                    +") "
                    + "VALUES ("
                    + QUERY_PLEACE_HOLD_VALUE
                    + ")";

     /**
     * SELECT * FROM Persons WHERE FirstName='Bush'
      * * SELECT * FROM Persons WHERE FirstName= 1
      * where p = 1 AND b = 'asd' AND c = 1
     */
    private static final String QUERY_STRING_SELECT =
            "SELECT * FROM "
                    + QUERY_PLEACE_HOLD_TABLE
                    + QUERY_STRING_WHERE
                    + QUERT_PLEACE_HOLD_WHERE;


    /**
     * DELETE FROM 表名称 WHERE 列名称 = 值
     */
    private static final String QUERY_STRING_DELETE =
            "DELETE FROM "
                    + QUERY_PLEACE_HOLD_TABLE
                    + QUERY_STRING_WHERE
                    + QUERT_PLEACE_HOLD_WHERE;

    /**
     * UPDATE 表名称 SET 列名称 = 新值 WHERE 列名称 = 某值
     */
    private static final String QUERY_STRING_UPADTA =
            "UPDATE "
                    + QUERY_PLEACE_HOLD_TABLE
                    + QUERY_STRING_SET
                    + QUERY_STRING_NEW_VALUE
                    + QUERY_STRING_WHERE
                    + QUERY_STRING_OLD_VALUE;
}
