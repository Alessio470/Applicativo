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
 * The type Utente dao.
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
     * Username exists boolean.
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
     * Login utente.
     *
     * @param username the username
     * @param password the password
     * @return the utente
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
     * Registra utente int.
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
