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
 *   utente(username TEXT PK, password TEXT NOT NULL, ruoloutente INTEGER NOT NULL REFERENCES enumruoloutente(id))
 *   enumruoloutente(id INTEGER PK, nomeruolo TEXT)
 *
 * Nota: nel DB NON esiste una colonna "id" in utente.
 * Per compatibilità con la GUI di Registrazione, registraUtente(...) restituisce l'ID del RUOLO associato.
 */
public class UtenteDAO {
    private final Connection conn;

    public UtenteDAO(Connection conn) {
        this.conn = conn;
    }

    /** true se esiste già uno username uguale. */
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
     * @return UtenteAmministratore / UtenteGenerico se le credenziali sono valide, altrimenti null.
     */
    public Utente login(String username, String passwordPlain) throws SQLException {
        final String sql =
                "SELECT u.username, u.password, r.nomeruolo " +
                        "FROM public.utente u " +
                        "JOIN public.enumruoloutente r ON r.id = u.ruoloutente " +
                        "WHERE u.username = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;

                final String pwd = rs.getString("password");
                if (pwd == null || !pwd.equals(passwordPlain)) return null;

                final String ruoloNome = rs.getString("nomeruolo");
                return buildUtente(username, pwd, ruoloNome);
            }
        }
    }

    /**
     * Registrazione nuovo utente.
     * Ritorna l'ID del ruolo (enumruoloutente.id) assegnato all'utente inserito.
     * Lancia SQLException se ruolo inesistente o username duplicato.
     */
    public int registraUtente(String username, String passwordPlain, String ruoloNome) throws SQLException {
        // 1) Risolvi ID del ruolo (case-insensitive)
        final Integer ruoloId = resolveRuoloId(ruoloNome);
        if (ruoloId == null) {
            throw new SQLException("Ruolo non valido: " + ruoloNome + " (ammessi: AMMINISTRATORE, GENERICO)");
        }

        // 2) Inserisci l'utente
        final String sql =
                "INSERT INTO public.utente (username, password, ruoloutente) " +
                        "VALUES (?, ?, ?) RETURNING ruoloutente";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, passwordPlain); // TODO: valuta hashing sicuro
            ps.setInt(3, ruoloId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1); // restituisce l'ID RUOLO
                }
                throw new SQLException("Insert utente fallita (RETURNING vuoto).");
            }
        }
    }

    // ===== Helpers =====

    private Utente buildUtente(String username, String password, String ruoloNome) {
        RuoloUtente ruolo = mapRuolo(ruoloNome);
        if (ruolo == RuoloUtente.AMMINISTRATORE) {
            return new UtenteAmministratore(username, password);
        } else {
            return new UtenteGenerico(username, password);
        }
    }

    private RuoloUtente mapRuolo(String ruoloNome) {
        if (ruoloNome == null) return RuoloUtente.GENERICO;
        String r = ruoloNome.trim().toUpperCase();
        if ("AMMINISTRATORE".equals(r)) return RuoloUtente.AMMINISTRATORE;
        if ("GENERICO".equals(r)) return RuoloUtente.GENERICO;
        // fallback robusto
        for (RuoloUtente ru : RuoloUtente.values()) {
            if (ru.name().equalsIgnoreCase(r)) return ru;
        }
        return RuoloUtente.GENERICO;
    }

    private Integer resolveRuoloId(String ruoloNome) throws SQLException {
        final String sql = "SELECT id FROM public.enumruoloutente WHERE UPPER(nomeruolo) = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ruoloNome == null ? null : ruoloNome.trim().toUpperCase());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("id");
                return null;
            }
        }
    }
}
