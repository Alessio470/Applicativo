package DAO;


import model.Prenotazione;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrenotazioneDAO {

    private final Connection conn;

    public PrenotazioneDAO( Connection conn ) { this.conn=conn; }



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


    //TODO se non erro da finire o almeno da controllare
    public List<Prenotazione> getPrenotazioniUtente(String username) throws SQLException {

        List<Prenotazione> prenotazioni = new ArrayList<>();

        final String query = "SELECT * FROM prenotazioni as v where v.username="+username; // nome tabella nel db

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
            prenotazioni.add(p);
        }
        return prenotazioni;

    }//Fine getPrenotazioniUtente


}
