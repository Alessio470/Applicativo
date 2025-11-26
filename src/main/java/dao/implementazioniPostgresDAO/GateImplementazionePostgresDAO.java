package dao.implementazioniPostgresDAO;

import dao.GateDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementazione Postgres di {@link dao.GateDAO}.
 * <p>Utilizza una connessione JDBC verso un database PostgreSQL.</p>
 */
public class GateImplementazionePostgresDAO implements GateDAO {

    /** Connessione JDBC attiva verso il database. */
    private final Connection conn;

    /**
     * Crea un {@code GateImplementazionePostgresDAO} con la connessione fornita.
     *
     * @param conn connessione JDBC da utilizzare
     */
    public GateImplementazionePostgresDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getGates() throws SQLException {

        final String query =
                "SELECT * FROM gate AS g " +
                        "ORDER BY substring(g.numerogate from 2)::int";

        List<String> resultDB = new ArrayList<>();

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                resultDB.add(rs.getString("numerogate"));
            }
        }

        return resultDB;
    }
}
