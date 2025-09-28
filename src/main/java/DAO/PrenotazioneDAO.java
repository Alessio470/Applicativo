package DAO;


import model.Prenotazione;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PrenotazioneDAO {

    private final Connection conn;

    public PrenotazioneDAO( Connection conn ) { this.conn=conn; }



    public int InserisciPrenotazione(Prenotazione prenotazione) throws SQLException {

    final String sql = "INSERT INTO prenotazione(numerobiglietto, numeroposto, statoprenotazione, username, codvolo, nomepasseggero, cognomepasseggero, codicefiscalepasseggero)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

        try(PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setString(1,prenotazione.getNumeroBiglietto());
            ps.setString(2,prenotazione.getPosto());
            ps.setInt(3, prenotazione.getStato().ordinal());
            ps.setString(4,prenotazione.getUsernameUtente());
            ps.setString(5,prenotazione.getCodiceVolo());
            ps.setString(6,prenotazione.getNomePasseggero());
            ps.setString(7,prenotazione.getCognomePasseggero());
            ps.setString(8,prenotazione.getCodicefiscalepasseggero());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                throw new SQLException("Insert volo fallita (RETURNING vuoto).");
            }


        }
    }//Parentesi InserisciPrenotazione


}
