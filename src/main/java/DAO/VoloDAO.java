package DAO;

import java.sql.*;
import java.time.LocalTime;
import java.util.Date;

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

    public int registraVolo(String codiceVolo, String compagniaaerea, String data, String orario, String aeroportoorigine, String aeroportodestinazione, String numerogate) throws SQLException {

        // Inserimento Volo
        final String sql =
                "INSERT INTO volo(codicevolo, compagniaaerea, datavolo, orarioprevisto, ritardo, statovolo, aeroportoorigine, aeroportodestinazione, 'numeroGate')"+
"VALUES (?,?,?,?,?,?,?,?,?);";


        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codiceVolo); //codicevolo
            ps.setString(2, compagniaaerea); // compagniaaerea
            ps.setDate(3, java.sql.Date.valueOf(data)); //datavolo
            ps.setTime(4, Time.valueOf(orario)); //orarioprevisto
            ps.setInt(5, 0); //ritardo
            ps.setInt(6, 1); //statovolo
            ps.setString(7, aeroportoorigine); //aeroportoorigine
            ps.setString(8, aeroportodestinazione); //aeroportodestinazione
            ps.setString(9, numerogate); //'numeroGate'


            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                throw new SQLException("Insert volo fallita (RETURNING vuoto).");
            }
        }
    }




}
