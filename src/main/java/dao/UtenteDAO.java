package dao;

import model.Utente;

import java.sql.SQLException;

/**
 * Interfaccia DAO per la gestione degli utenti.
 * Offre operazioni di:
 * - controllo esistenza username
 * - login
 * - registrazione
 */
public interface UtenteDAO {

    /**
     * Verifica se esiste già un utente con lo username indicato.
     *
     * @param username username da cercare
     * @return true se esiste almeno una corrispondenza, altrimenti false
     * @throws SQLException in caso di errore SQL
     */
    boolean usernameExists(String username) throws SQLException;

    /**
     * Esegue il login e restituisce l’utente corrispondente alle credenziali.
     *
     * @param username username fornito
     * @param password password fornita
     * @return l’utente autenticato, oppure null se le credenziali non corrispondono
     * @throws SQLException in caso di errore SQL
     */
    Utente login(String username, String password) throws SQLException;

    /**
     * Registra un nuovo utente nel database.
     *
     * @param utenteRegistrato istanza da salvare
     * @return valore restituito dal RETURNING (di solito l’ID/indice del ruolo)
     * @throws SQLException in caso di errore SQL
     */
    int registraUtente(Utente utenteRegistrato) throws SQLException;
}
