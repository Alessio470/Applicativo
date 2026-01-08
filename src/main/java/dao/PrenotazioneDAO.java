package dao;

import model.Prenotazione;

import java.sql.SQLException;
import java.util.List;

/**
 * Interfaccia DAO per l’accesso alle prenotazioni.
 * <p>Definisce le operazioni di base per inserire, leggere e cancellare
 * le prenotazioni dal database.</p>
 */
public interface PrenotazioneDAO {

    /**
     * Inserisce una nuova prenotazione nel database.
     *
     * @param prenotazione oggetto da inserire
     * @return numero di righe interessate (ad es. 1 se l’inserimento è andato a buon fine)
     * @throws SQLException in caso di errore SQL
     */
    int inserisciPrenotazione(Prenotazione prenotazione) throws SQLException;

    /**
     * Restituisce tutte le prenotazioni associate a uno username.
     *
     * @param username username proprietario delle prenotazioni
     * @return lista di prenotazioni trovate (eventualmente vuota)
     * @throws SQLException in caso di errore SQL
     */
    List<Prenotazione> getPrenotazioniUtente(String username) throws SQLException;

    /**
     * Cancella una prenotazione dato il numero di biglietto e lo username.
     *
     * @param numeroBiglietto numero del biglietto (PK della tabella prenotazione)
     * @param username username proprietario della prenotazione
     * @return numero di righe cancellate (0 se nessuna trovata)
     * @throws SQLException in caso di errore SQL
     */
    int cancellaPrenotazione(String numeroBiglietto, String username) throws SQLException;

    List<Prenotazione> getPrenotazioniUtenteSearch(Prenotazione p);
}
