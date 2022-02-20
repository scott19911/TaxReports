package com.example.taxreports.util;

import com.example.taxreports.DAO.UserDAO;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionPool {
    private static final Logger log = Logger.getLogger(ConnectionPool.class);
    private ConnectionPool(){
        //private constructor
    }

    private static ConnectionPool instance = null;

    public static ConnectionPool getInstance(){
        if (instance==null)
            instance = new ConnectionPool();
        return instance;
    }

    public Connection getConnection(){
        Context ctx;
        Connection c = null;
        try {
            ctx = new InitialContext();
            DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/mydatabase");
            c = ds.getConnection();
        } catch (NamingException | SQLException e) {
            log.error("not initializade ds ", e);
           throw new IllegalStateException("not initializade ds ", e);
        }
        return c;
    }
}