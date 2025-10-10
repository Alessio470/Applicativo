package DAO;


import model.Prenotazione;
import model.Volo;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO per l’accesso alle prenotazioni.
 * <p>Gestisce inserimento e lettura delle prenotazioni, con eventuale join al volo associato.</p>
 */
public class PrenotazioneDAO {

    /** Connessione JDBC attiva verso il database. */
    private final Connection conn;

    /**
     * Crea un {@code PrenotazioneDAO} con la connessione fornita.
     *
     * @param conn connessione JDBC da utilizzare
     */
    public PrenotazioneDAO( Connection conn ) { this.conn=conn; }


    /**
     * Inserisce una nuova prenotazione.
     *
     * <p>Scrive i campi principali della prenotazione nella tabella {@code prenotazione}.
     * Lo stato viene serializzato tramite {@code ordinal()} dell’enum.</p>
     *
     * @param prenotazione oggetto da inserire
     * @return intero restituito dalla query (ad es. chiave generata, se la query prevede RETURNING)
     * @throws SQLException in caso di errore SQL o se il {@code RETURNING} non produce risultati
     */
    public int InserisciPrenotazione(Prenotazione prenotazione) throws SQLException {

    final String sql = "INSERT INTO prenotazione( numeroposto, statoprenotazione, username, codvolo, nomepasseggero, cognomepasseggero, codicefiscalepasseggero)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?);";

        try(PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setString(1,prenotazione.getPosto());
            ps.setInt(2, prenotazione.getStato().ordinal());
            ps.setString(3,prenotazione.getUsernameUtente());
            ps.setString(4,prenotazione.getCodiceVolo());
            ps.setString(5,prenotazione.getNomePasseggero());
            ps.setString(6,prenotazione.getCognomePasseggero());
            ps.setString(7,prenotazione.getCodicefiscalepasseggero());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                throw new SQLException("Insert volo fallita (RETURNING vuoto).");
            }
        }
    }//Parentesi InserisciPrenotazione


    /**
     * Restituisce le prenotazioni di un utente, includendo i dettagli del volo associato.
     *
     * <p>Esegue una join tra {@code prenotazione} e {@code volo} filtrando per username.
     * <strong>Nota:</strong> la query costruita con concatenazione di stringhe è suscettibile a SQL injection
     * se {@code username} non è sanificato; si raccomanda l’uso di {@link PreparedStatement} (indicazione generale).</p>
     *
     * @param username username proprietario delle prenotazioni
     * @return lista (eventualmente vuota) di prenotazioni con volo associato
     * @throws SQLException in caso di errori durante l’esecuzione o la lettura del result set
     */
    public List<Prenotazione> getPrenotazioniUtente(String username) throws SQLException {

        List<Prenotazione> prenotazioni = new ArrayList<>();

        final String query = "SELECT v.*, p.* FROM prenotazione as p join volo as v on p.codvolo=v.codicevolo WHERE p.username='"+username+"'";

        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {
            Prenotazione p = new Prenotazione(
                    rs.getString("numerobiglietto"),
                    username,
                    rs.getString("codvolo"),
                    rs.getString("nomepasseggero"),
                    rs.getString("cognomepasseggero"),
                    rs.getString("numeroposto"),
                    rs.getInt("statoprenotazione"),
                    rs.getString("codicefiscalepasseggero")
            );

            p.setVoloassociato(new Volo(
                    rs.getString("codvolo"),
                    rs.getString("compagniaaerea"),
                    rs.getString("aeroportoorigine"),
                    rs.getString("aeroportodestinazione"),
                    LocalDate.parse(rs.getString("datavolo"), DateTimeFormatter.ofPattern("yyyy-MM-dd")).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    rs.getString("orarioprevisto").substring(0, 5),
                    rs.getInt("ritardo"),
                    rs.getInt("statovolo"),
                    rs.getString("numeroGate")
            ));

            prenotazioni.add(p);
        }

        return prenotazioni;

    }//Fine getPrenotazioniUtente


}
