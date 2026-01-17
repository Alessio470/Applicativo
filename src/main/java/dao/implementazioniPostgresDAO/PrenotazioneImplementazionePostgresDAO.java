package dao.implementazioniPostgresDAO;

import dao.PrenotazioneDAO;
import model.Prenotazione;
import model.Volo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementazione PostgreSQL di {@link PrenotazioneDAO}.
 * <p>Utilizza una {@link Connection} JDBC per eseguire le query sul DB.</p>
 */
public class PrenotazioneImplementazionePostgresDAO implements PrenotazioneDAO {

    /** Connessione JDBC attiva verso il database. */
    private final Connection conn;

    /**
     * Crea un {@code PrenotazioneImplementazionePostgresDAO} con la connessione fornita.
     *
     * @param conn connessione JDBC da utilizzare
     */
    public PrenotazioneImplementazionePostgresDAO(Connection conn) {
        this.conn = conn;
    }

    // ------------------------------------------------------------
    // INSERT
    // ------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public int inserisciPrenotazione(Prenotazione prenotazione) throws SQLException {
        final String sql =
                "INSERT INTO prenotazione(" +
                        "numeroposto, statoprenotazione, username, codvolo, " +
                        "nomepasseggero, cognomepasseggero, codicefiscalepasseggero" +
                        ") VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, prenotazione.getPosto());
            // Nel codice originale veniva usato ordinal(), quindi manteniamo il comportamento
            ps.setInt(2, prenotazione.getStato().ordinal());
            ps.setString(3, prenotazione.getUsernameUtente());
            ps.setString(4, prenotazione.getCodiceVolo());
            ps.setString(5, prenotazione.getNomePasseggero());
            ps.setString(6, prenotazione.getCognomePasseggero());
            ps.setString(7, prenotazione.getCodicefiscalepasseggero());

            // Ritorno il numero di righe inserite (in pratica 1 se va bene)
            return ps.executeUpdate();
        }
    }

    // ------------------------------------------------------------
    // SELECT
    // ------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Prenotazione> getPrenotazioniUtente(String username) throws SQLException {
        List<Prenotazione> prenotazioni = new ArrayList<>();

        final String query =
                "SELECT v.*, p.* " +
                        "FROM prenotazione AS p " +
                        "JOIN volo AS v ON p.codvolo = v.codicevolo " +
                        "WHERE p.username = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {

                    // Costruisco l'oggetto Prenotazione
                    Prenotazione p = new Prenotazione(
                            rs.getString("numerobiglietto"),  // PK del biglietto
                            username,
                            rs.getString("codvolo"),
                            rs.getString("nomepasseggero"),
                            rs.getString("cognomepasseggero"),
                            rs.getString("numeroposto"),
                            rs.getInt("statoprenotazione"),
                            rs.getString("codicefiscalepasseggero")
                    );

                    // Formatto la data del volo come dd/MM/yyyy
                    Date dataSql = rs.getDate("datavolo");
                    String dataFormattata = null;
                    if (dataSql != null) {
                        dataFormattata = dataSql.toLocalDate()
                                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    }

                    // Formatto l'orario previsto (prime 5 cifre HH:MM)
                    String orarioPrevisto = rs.getString("orarioprevisto");
                    if (orarioPrevisto != null && orarioPrevisto.length() >= 5) {
                        orarioPrevisto = orarioPrevisto.substring(0, 5);
                    }

                    // Creo il Volo associato
                    Volo v = new Volo(
                            rs.getString("codvolo"),
                            rs.getString("compagniaaerea"),
                            rs.getString("aeroportoorigine"),
                            rs.getString("aeroportodestinazione"),
                            dataFormattata,
                            orarioPrevisto,
                            rs.getInt("ritardo"),
                            rs.getInt("statovolo"),
                            rs.getString("numeroGate")
                    );

                    p.setVoloassociato(v);
                    prenotazioni.add(p);
                }
            }
        }

        return prenotazioni;
    }

    // ------------------------------------------------------------
    // SELECT
    // ------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public int cancellaPrenotazione(String numeroBiglietto, String username) throws SQLException {
        final String sql =
                "DELETE FROM prenotazione " +
                        "WHERE numerobiglietto = ? AND username = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, numeroBiglietto);
            ps.setString(2, username);

            return ps.executeUpdate(); // 1 se ok, 0 se nessuna riga cancellata
        }
    }

    // ------------------------------------------------------------
    // DELETE
    // ------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Prenotazione> getPrenotazioniUtenteSearch(Prenotazione p) throws SQLException {
        List<Prenotazione> prenotazioni = new ArrayList<>();

        // Base della query: usiamo StringBuilder per comporre la stringa SQL
        StringBuilder sql = new StringBuilder(
                "SELECT v.*, p.* " +
                        "FROM prenotazione AS p " +
                        "JOIN volo AS v ON p.codvolo = v.codicevolo " +
                        "WHERE 1=1" // Punto di partenza
        );

        // Lista per gestire i valori dei parametri del PreparedStatement
        List<Object> parameters = new ArrayList<>();

        // --- Inizio costruzione dinamica ---

        if (p.getUsernameUtente() != null && !p.getUsernameUtente().isEmpty()) {
            sql.append(" AND p.username LIKE ?");
            parameters.add(p.getUsernameUtente());
        }

        if (p.getNumeroBiglietto() != null && !p.getNumeroBiglietto().isEmpty()) {
            sql.append(" AND p.numerobiglietto LIKE ?");
            parameters.add("%" + p.getNumeroBiglietto() + "%");
        }

        if (p.getCodiceVolo() != null && !p.getCodiceVolo().isEmpty()) {
            sql.append(" AND p.codvolo LIKE ?");
            parameters.add("%" + p.getCodiceVolo() + "%");
        }

        if (p.getNomePasseggero() != null && !p.getNomePasseggero().isEmpty()) {
            sql.append(" AND p.nomepasseggero LIKE ?");
            parameters.add("%" + p.getNomePasseggero() + "%");
        }

        if (p.getCognomePasseggero() != null && !p.getCognomePasseggero().isEmpty()) {
            sql.append(" AND p.cognomepasseggero LIKE ?");
            parameters.add("%" + p.getCognomePasseggero() + "%");
        }

        if (p.getCodicefiscalepasseggero() != null && !p.getCodicefiscalepasseggero().isEmpty()) {
            sql.append(" AND p.codicefiscalepasseggero LIKE ?");
            parameters.add("%" + p.getCodicefiscalepasseggero() + "%");
        }
        // --- Fine costruzione dinamica ---

        try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            // Inserimento dinamico dei parametri
            for (int i = 0; i < parameters.size(); i++) {
                ps.setObject(i + 1, parameters.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // Creazione dell'oggetto Prenotazione dai risultati del DB

                    Prenotazione resP = new Prenotazione(
                            rs.getString("numerobiglietto"),
                            rs.getString("username"),
                            rs.getString("codvolo"),
                            rs.getString("nomepasseggero"),
                            rs.getString("cognomepasseggero"),
                            rs.getString("numeroposto"),
                            rs.getInt("statoprenotazione"),
                            rs.getString("codicefiscalepasseggero")
                    );

                    // Formattazione Data
                    java.sql.Date dataSql = rs.getDate("datavolo");
                    String dataFormattata = null;
                    if (dataSql != null) {
                        dataFormattata = dataSql.toLocalDate()
                                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    }

                    // Formattazione Orario
                    String orarioPrevisto = rs.getString("orarioprevisto");
                    if (orarioPrevisto != null && orarioPrevisto.length() >= 5) {
                        orarioPrevisto = orarioPrevisto.substring(0, 5);
                    }

                    // Creazione Volo associato
                    Volo v = new Volo(
                            rs.getString("codvolo"),
                            rs.getString("compagniaaerea"),
                            rs.getString("aeroportoorigine"),
                            rs.getString("aeroportodestinazione"),
                            dataFormattata,
                            orarioPrevisto,
                            rs.getInt("ritardo"),
                            rs.getInt("statovolo"),
                            rs.getString("numeroGate")
                    );

                    resP.setVoloassociato(v);
                    prenotazioni.add(resP);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Errore nella ricerca dinamica delle prenotazioni", e);
        }


        return prenotazioni;
    }



}
