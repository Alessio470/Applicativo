package dao;

import model.Volo;

import java.sql.SQLException;
import java.util.List;

/**
 * Interfaccia DAO per l’accesso ai dati dei voli.
 * <p>Gestisce inserimento, lettura elenco/filtrata e aggiornamento dei voli.</p>
 */
public interface VoloDAO {

    /**
     * Inserisce un nuovo volo a database.
     *
     * @param v volo da inserire
     * @return numero di righe interessate (tipicamente 1)
     * @throws SQLException in caso di errore SQL
     */
    int registraVolo(Volo v) throws SQLException;

    /**
     * Restituisce tutti i voli presenti a database.
     *
     * @return lista di voli (eventualmente vuota)
     * @throws SQLException in caso di errore SQL
     */
    List<Volo> getVoli() throws SQLException;

    /**
     * Restituisce i voli prenotabili (stato = 1, cioè programmato).
     *
     * @return lista di voli prenotabili (eventualmente vuota)
     * @throws SQLException in caso di errore SQL
     */
    List<Volo> getVoliPrenotabili() throws SQLException;

    /**
     * Restituisce i voli (da/per Napoli) ordinati per suffisso numerico del codice.
     *
     * @return lista di voli ordinati (eventualmente vuota)
     * @throws SQLException in caso di errore SQL
     */
    List<Volo> getVoliDaPerNapoli() throws SQLException;

    /**
     * Restituisce i voli che corrispondono ai criteri di ricerca.
     *
     * @param v volo con i criteri di ricerca
     * @return lista di voli (eventualmente vuota)
     * @throws SQLException in caso di errore SQL
     */
    List<Volo> cercaVoli(Volo v) throws SQLException;

    /**
     * Aggiorna i dati di un volo esistente, individuato dal codice.
     *
     * @param v volo con i nuovi valori
     * @throws SQLException in caso di errore SQL
     */
    void updateVolo(Volo v) throws SQLException;
}
