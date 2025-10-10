package DAO;

import model.Utente;
import model.UtenteAmministratore;
import model.UtenteGenerico;
import model.enums.RuoloUtente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO per la gestione degli utenti.
 * <p>Offre operazioni di verifica esistenza, login e registrazione.</p>
 */
public class UtenteDAO {
    /** Connessione JDBC attiva verso il database. */
    private final Connection conn;

    /**
     * Crea un {@code UtenteDAO} con la connessione fornita.
     *
     * @param conn connessione JDBC da utilizzare
     */
    public UtenteDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Verifica se esiste già un utente con lo username indicato.
     *
     * @param username username da cercare
     * @return {@code true} se esiste almeno una corrispondenza, altrimenti {@code false}
     * @throws SQLException in caso di errore nell’esecuzione della query
     */
    public boolean usernameExists(String username) throws SQLException {
        final String sql = "SELECT 1 FROM public.utente WHERE username = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    /**
     * Esegue l’autenticazione restituendo l’utente corrispondente alle credenziali.
     *
     * <p>In base al ruolo letto dal DB restituisce un’istanza di
     * {@link UtenteAmministratore} oppure {@link UtenteGenerico}.</p>
     *
     * @param username username fornito
     * @param password password fornita
     * @return l’utente autenticato, oppure {@code null} se le credenziali non corrispondono
     * @throws SQLException in caso di errore nell’esecuzione della query
     */
    public Utente login(String username, String password) throws SQLException {
        String sql = "SELECT * FROM utente WHERE username = ? AND password = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int ruolo = rs.getInt("ruoloutente");
                if (ruolo==2) {
                    return new UtenteAmministratore(rs.getString("username"), rs.getString("password"));
                } else {
                    return new UtenteGenerico(rs.getString("username"), rs.getString("password"));
                }
            }
        }
        return null; // login fallito
    }


    /**
     * Registra un nuovo utente a database.
     *
     * <p>Inserisce username, password e ruolo (serializzato come {@code ordinal()} dell’enum {@link RuoloUtente}).</p>
     *
     * @param utenteRegistrato istanza da salvare
     * @return valore restituito dal {@code RETURNING} (ID/indice del ruolo)
     * @throws SQLException se il ruolo è assente/non valido o in caso di errore SQL
     */
    public int registraUtente(Utente utenteRegistrato) throws SQLException {
        // 1) Risolvi ID del ruolo (case-insensitive)
        if (utenteRegistrato.getRuolo() == null) {
            throw new SQLException("Ruolo non valido: " + utenteRegistrato.getRuolo().toString() + " (ammessi: AMMINISTRATORE, GENERICO)");
        }

        // 2) Inserisci l'utente
        final String sql =
                "INSERT INTO public.utente (username, password, ruoloutente) " +
                        "VALUES (?, ?, ?) RETURNING ruoloutente";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, utenteRegistrato.getUsername());
            ps.setString(2, utenteRegistrato.getPassword());
            ps.setInt(3, utenteRegistrato.getRuolo().ordinal());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1); // restituisce l'ID RUOLO
                }
                throw new SQLException("Insert utente fallita (RETURNING vuoto).");
            }
        }
    }

}
