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
 * DAO per la tabella public.utente (PostgreSQL).
 * Schema:
 * utente(username TEXT PK, password TEXT NOT NULL, ruoloutente INTEGER NOT NULL REFERENCES enumruoloutente(id))
 * enumruoloutente(id INTEGER PK, nomeruolo TEXT)
 * <p>
 * Nota: nel DB NON esiste una colonna "id" in utente.
 * Per compatibilità con la GUI di Registrazione, registraUtente(...) restituisce l'ID del RUOLO associato.
 */
public class UtenteDAO {
    private final Connection conn;

    /**
     * Instantiates a new Utente dao.
     *
     * @param conn the conn
     */
    public UtenteDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * true se esiste già uno username uguale.  @param username the username
     *
     * @param username the username
     * @return the boolean
     * @throws SQLException the sql exception
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
     * Autenticazione.
     *
     * @param username the username
     * @param password the password
     * @return UtenteAmministratore / UtenteGenerico se le credenziali sono valide, altrimenti null.
     * @throws SQLException the sql exception
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
     * Registrazione nuovo utente.
     * Ritorna l'ID del ruolo (enumruoloutente.id) assegnato all'utente inserito.
     * Lancia SQLException se ruolo inesistente o username duplicato.
     *
     * @param utenteRegistrato the utente registrato
     * @return the int
     * @throws SQLException the sql exception
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
            ps.setString(2, utenteRegistrato.getPassword()); // TODO: valuta hashing sicuro
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
