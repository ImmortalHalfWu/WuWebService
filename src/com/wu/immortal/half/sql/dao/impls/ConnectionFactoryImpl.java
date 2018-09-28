package com.wu.immortal.half.sql.dao.impls;

import com.wu.immortal.half.sql.dao.interfaces.ConnectionFactory;
import org.logicalcobwebs.proxool.ProxoolFacade;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactoryImpl implements ConnectionFactory {

    static {
        try {
            Class.forName("org.logicalcobwebs.proxool.ProxoolDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static ConnectionFactoryImpl connectionFactory;

    private ConnectionFactoryImpl() { }

    public static ConnectionFactory instance() {
        if (connectionFactory == null) {
            synchronized (ConnectionFactoryImpl.class) {
                connectionFactory = new ConnectionFactoryImpl();
            }
        }
        return connectionFactory;
    }

    @Override
    public Connection createConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("proxool.Develop");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    @Override
    public void release() {
        ProxoolFacade.shutdown();
    }
}
