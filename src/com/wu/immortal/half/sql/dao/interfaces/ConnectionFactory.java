package com.wu.immortal.half.sql.dao.interfaces;

import java.sql.Connection;

public interface ConnectionFactory {

    Connection createConnection();
    void release();

}
