package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO per l’accesso ai gate.
 * <p>Espone operazioni di lettura dei numeri/etichette dei gate dal database.</p>
 */
public class GateDAO {
    /** Connessione JDBC attiva verso il database. */
    private final Connection conn;

    /**
     * Crea un {@code GateDAO} con la connessione fornita.
     *
     * @param conn connessione JDBC da utilizzare
     */
    public GateDAO( Connection conn ) { this.conn=conn; }

    /**
     * Restituisce la lista dei gate presenti a database.
     * <p>L’ordinamento è basato sulla parte numerica del codice (es. {@code G2}, {@code G10} → 2 &lt; 10),
     * ottenuta con {@code substring} dal secondo carattere e conversione a intero.</p>
     *
     * @return lista (eventualmente vuota) di etichette di gate, ordinate per valore numerico
     * @throws SQLException in caso di errore durante l’esecuzione della query
     */
    public List<String> getGates() throws SQLException {

         final String query= "SELECT * FROM gate AS g ORDER BY substring(g.numerogate from 2)::int";
         List<String> resultDB = new ArrayList<>();


        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(query);


            while ( rs.next() ) {
                resultDB.add(rs.getString("numerogate"));
            }
        }
        return resultDB;
    }//Parentesi getGates
}//Parentesi Finale
