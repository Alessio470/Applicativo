package dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Interfaccia DAO per l’accesso ai gate.
 * <p>Espone operazioni di lettura dei numeri/etichette dei gate dal database.</p>
 */
public interface GateDAO {

    /**
     * Restituisce la lista dei gate presenti a database.
     * <p>L’ordinamento è basato sulla parte numerica del codice,
     * ottenuta con substring dal secondo carattere e conversione a intero.</p>
     *
     * @return lista (eventualmente vuota) di etichette di gate, ordinate per valore numerico
     * @throws SQLException in caso di errore durante l’esecuzione della query
     */
    List<String> getGates() throws SQLException;
}
