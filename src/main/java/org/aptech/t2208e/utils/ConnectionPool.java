package org.aptech.t2208e.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {
    private static ConnectionPool instance;
    private List<Connection> connections;
    private static final int POOL_SIZE = 20;

    // db config
    private static final String DB_URL = "jdbc:mysql://localhost:3307/t2208e_sem4";
    private static final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "admin@123";

    private ConnectionPool() {
        connections = new ArrayList<>();
        initializeConnectionPool();
    }

    public static synchronized ConnectionPool getInstance() {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }

    private void initializeConnectionPool() {
        try {
            Class.forName(DRIVER_CLASS_NAME);
            for (int i = 0; i < POOL_SIZE; i++) {
                Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                connections.add(connection);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println("Error initializing connection pool: " + ex.getMessage());
            // Consider throwing an exception or handling the error appropriately
        }
    }

    public synchronized Connection getConnection() {
        Connection connection = null;
        if (!connections.isEmpty()) {
            connection = connections.remove(connections.size() - 1);
            try {
                if (connection.isClosed() || !connection.isValid(5)) {
                    connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                }
            } catch (SQLException ex) {
                System.err.println("Error checking or recreating connection: " + ex.getMessage());
                connection = null;
            }
        }
        return connection;
    }

    public synchronized void releaseConnection(Connection connection) {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connections.add(connection);
                }
            } catch (SQLException ex) {
                System.err.println("Error releasing connection: " + ex.getMessage());
            }
        }
    }

    public synchronized void shutdown() {
        for (Connection connection : connections) {
            try {
                connection.close();
            } catch (SQLException ex) {
                System.err.println("Error closing connection: " + ex.getMessage());
            }
        }
        connections.clear();
    }
}
