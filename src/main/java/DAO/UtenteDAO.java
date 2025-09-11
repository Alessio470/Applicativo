package DAO;

import model.Utente;
import model.UtenteAmministratore;
import model.UtenteGenerico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/** DAO per autenticazione utenti. Ritorna la sottoclasse corretta in base al ruolo. */
public class UtenteDAO {
    private final Connection conn;

    public UtenteDAO(Connection conn) {
        this.conn = conn;
    }

    /** @return Utente (Amministratore o Generico) se credenziali valide, altrimenti null */
    public Utente login(String username, String passwordPlain) throws Exception {
        String sql = "SELECT username, password_plain, ruolo " +
                "FROM utente WHERE username = ? AND password_plain = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, passwordPlain);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;

                String ruolo = rs.getString("ruolo"); // 'AMMINISTRATORE' | 'GENERICO'

                if ("AMMINISTRATORE".equalsIgnoreCase(ruolo)) {
                    return new UtenteAmministratore(username, passwordPlain);
                } else {
                    return new UtenteGenerico(username, passwordPlain);
                }
            }
        }
    }
}
