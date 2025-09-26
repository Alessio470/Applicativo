package DAO;

import model.Volo;

import java.sql.*;

//TODO MODOFICARE registraVolo
/*
INSERT INTO volo(
	      codicevolo, compagniaaerea, datavolo, orarioprevisto, ritardo, statovolo, aeroportoorigine, aeroportodestinazione, "numeroGate")
VALUES (DA GENERARE,     ComboBox,         ?,         ?,             0,        1,          ?,                ?,                  ComboBox);

 */

public class VoloDAO {

    private final Connection conn;

    public VoloDAO(Connection conn) {
        this.conn = conn;
    }

    public int registraVolo(Volo v) throws SQLException {

        // Query inserimento Volo
        final String sql =
                "INSERT INTO volo(codicevolo, compagniaaerea, datavolo, orarioprevisto, ritardo, statovolo, aeroportoorigine, aeroportodestinazione, numeroGate) " +
                        "VALUES (?,?,?,?,?,?,?,?,?)";


        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, v.getCodiceV()); //codicevolo
            ps.setString(2, v.getCompagnia()); // compagniaaerea
            ps.setDate(3, v.getDatasql()); //datavolo
            ps.setTime(4, v.getOrarioSql()); //orarioprevisto
            ps.setInt(5, v.getRitardoMinuti()); //ritardo
            ps.setInt(6, v.getStatoToInt()); //statovolo
            ps.setString(7, v.getAeroportoOrigine()); //aeroportoorigine
            ps.setString(8, v.getAeroportoDestinazione()); //aeroportodestinazione
            ps.setString(9, v.getGate()); //'numeroGate'


            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                throw new SQLException("Insert volo fallita (RETURNING vuoto).");
            }
        }
    }




}
