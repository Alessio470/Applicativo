package dao.implementazioniPostgresDAO;

import dao.UtenteDAO;
import model.Utente;
import model.UtenteAmministratore;
import model.UtenteGenerico;
import model.enums.RuoloUtente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implementazione PostgreSQL di {@link UtenteDAO}.
 * <p>Fornisce le implementazioni concrete delle operazioni di accesso ai dati
 * per la gestione degli utenti, utilizzando un database PostgreSQL.</p>
 */
public class UtenteImplementazionePostgresDAO implements UtenteDAO {

    private final Connection conn;

    /**
     * Costruisce un'istanza di UtenteImplementazionePostgresDAO.
     *
     * @param conn la connessione al database.
     */
    public UtenteImplementazionePostgresDAO(Connection conn) {
        this.conn = conn;
    }

    // ------------------------------------------------------------
    //  usernameExists
    // ------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean usernameExists(String username) throws SQLException {
        final String sql = "SELECT 1 FROM public.utente WHERE username = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    // ------------------------------------------------------------
    //  login
    // ------------------------------------------------------------
    /**
     * Esegue il login di un utente nel database.
     *
     * @param username l'username dell'utente.
     * @param password la password dell'utente.
     * @return {@code Utente} l'oggetto utente se il login ha successo.
     * @throws SQLException in caso di errore di accesso al database.
     * @throws NullPointerException se le credenziali non sono valide.
     */
    @Override
    public Utente login(String username, String password) throws SQLException {
        final String sql = "SELECT * FROM public.utente WHERE username = ? AND password = ?";
        Utente utenteTrovato = null;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int ruolo = rs.getInt("ruoloutente");

                    if (ruolo == 2) {
                        utenteTrovato = new UtenteAmministratore(
                                rs.getString("username"),
                                rs.getString("password")
                        );
                    } else {
                        utenteTrovato = new UtenteGenerico(
                                rs.getString("username"),
                                rs.getString("password")
                        );
                    }
                }
            }
        }

        // Se utenteTrovato è ancora null, sollevo l'eccezione manualmente
        if (utenteTrovato == null) {
            throw new NullPointerException("Login fallito: Credenziali non valide.");
        }

        return utenteTrovato;
    }//Parentesi doLogin

    // ------------------------------------------------------------
    //  registraUtente
    // ------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    @Override
    public int registraUtente(Utente utenteRegistrato) throws SQLException {

        if (utenteRegistrato.getRuolo() == null) {
            throw new SQLException(
                    "Ruolo non valido: " + utenteRegistrato.getRuolo()
                            + " (ammessi: AMMINISTRATORE, GENERICO)"
            );
        }

        final String sql =
                "INSERT INTO public.utente (username, password, ruoloutente) " +
                        "VALUES (?, ?, ?) RETURNING ruoloutente";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, utenteRegistrato.getUsername());
            ps.setString(2, utenteRegistrato.getPassword());
            ps.setInt(3, utenteRegistrato.getRuolo().ordinal());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1); // ID/indice ruolo
                }
                throw new SQLException("Insert utente fallita (RETURNING vuoto).");
            }
        }
    }
}
