package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
/*
    public int registraVolo(String username, String passwordPlain, String ruoloNome) throws SQLException {

        // Inserimento Volo
        final String sql =
                "INSERT INTO volo(
	      codicevolo, compagniaaerea, datavolo, orarioprevisto, ritardo, statovolo, aeroportoorigine, aeroportodestinazione, 'numeroGate')"+
VALUES (DA GENERARE,     ComboBox,         ?,         ?,             0,        1,          ?,                ?,                  ComboBox);";



        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, passwordPlain);
            ps.setInt(3, ruoloId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                throw new SQLException("Insert volo fallita (RETURNING vuoto).");
            }
        }
    }*/




}
