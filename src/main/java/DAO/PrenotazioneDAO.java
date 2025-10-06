package DAO;


import model.Prenotazione;
import model.Volo;
import model.enums.StatoVolo;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Prenotazione dao.
 */
public class PrenotazioneDAO {

    private final Connection conn;

    /**
     * Instantiates a new Prenotazione dao.
     *
     * @param conn the conn
     */
    public PrenotazioneDAO( Connection conn ) { this.conn=conn; }


    /**
     * Inserisci prenotazione int.
     *
     * @param prenotazione the prenotazione
     * @return the int
     * @throws SQLException the sql exception
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
     * Gets prenotazioni utente.
     *
     * @param username the username
     * @return the prenotazioni utente
     * @throws SQLException the sql exception
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
