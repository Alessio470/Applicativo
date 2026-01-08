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
    // DELETE
    // ------------------------------------------------------------

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

    List<Prenotazione> getPrenotazioniUtenteSearch(Prenotazione p){
        List<Prenotazione> prenotazioni = new ArrayList<>();
        //TODO: DA FINIRE
    }


}
