package dao.pool;

import dao.exception.DAOException;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ConnectionPool {
    private String JDBC_URL;
    private static final ConnectionPool instance;

    static {
        try {
            instance = new ConnectionPool();
        } catch (DAOException e) {
            throw new RuntimeException("Pool error", e);
        }
    }

    private String USERNAME;
    private String PASSWORD;
    private static final int INITIAL_POOL_SIZE = 7;

    public static ConnectionPool getInstance() {
        return instance;
    }

    private void assignStartValues() throws DAOException {
        Properties props = new Properties();
        try(InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties")) {
            Class.forName("com.mysql.jdbc.Driver");
            props.load(input);
            USERNAME = props.getProperty("db.username");
            PASSWORD = props.getProperty("db.password");
            JDBC_URL = props.getProperty("db.url");
        } catch (IOException ignored) {
        } catch (ClassNotFoundException e) {
            throw new DAOException(e);
        }
    }

    private List<Connection> connections;

    private ConnectionPool() throws DAOException {
        connections = new ArrayList<>();
        assignStartValues();
        initializeConnections();
    }

    private void initializeConnections() throws DAOException {
        try {
            for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
                Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                connections.add(connection);
            }
        } catch (SQLException e) {
            throw new DAOException("Pool initialize error", e);
        }
    }

    public synchronized Connection getConnection() throws DAOException {
        while (connections.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new DAOException("Something really strange in con wait", e);
            }
        }

        return connections.remove(connections.size() - 1);
    }

    public synchronized void releaseConnection(Connection connection) {
        connections.add(connection);
        notifyAll();
    }

    public synchronized void closeAllConnections() throws DAOException {
        for (Connection connection : connections) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DAOException("Error closing all connections", e);
            }
        }
        connections.clear();
    }
}
