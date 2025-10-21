package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Gestore singleton della connessione JDBC a PostgreSQL.
 * <p>Carica il driver e mantiene una singola {@link Connection} riutilizzabile.</p>
 */
public class ConnessioneDatabase {

    private static ConnessioneDatabase instance;
    private Connection connection;

    /** URL JDBC del database. */
    private final String url = "jdbc:postgresql://localhost:5433/db_aeroporto";
    /** Username del database. */
    private final String user = "postgres";
    /** Password del database. */
    private final String password = "password";
    /** Nome del driver JDBC PostgreSQL. */
    private final String driver = "org.postgresql.Driver";

    /**
     * Inizializza la connessione caricando il driver e aprendo la {@link Connection}.
     *
     * @throws SQLException se il driver non è disponibile o la connessione fallisce
     */
    private ConnessioneDatabase() throws SQLException {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException ex) {
            throw new SQLException("Driver non trovato: " + ex.getMessage());
        }
    }

    /**
     * Restituisce l’istanza singleton, creandola se assente o se la connessione è chiusa.
     *
     * @return istanza di {@code ConnessioneDatabase}
     * @throws SQLException se l’inizializzazione fallisce
     */
    public static ConnessioneDatabase getInstance() throws SQLException {
        if (instance == null || instance.connection.isClosed()) {
            instance = new ConnessioneDatabase();
        }
        return instance;
    }

    /**
     * Restituisce la connessione JDBC attiva.
     *
     * @return connessione al database
     */
    public Connection getConnection() {
        return connection;
    }
}
