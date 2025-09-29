package DAO;

import model.Volo;
import model.enums.StatoVolo;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
    }//Fine registraVolo


    public List<Volo> getVoliPrenotabili() throws SQLException {

        List<Volo> voli = new ArrayList<>();

        String query = "SELECT * FROM volo as v WHERE v.statovolo= 1"; // nome tabella nel db

        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {
            Volo v = new Volo(
                    rs.getString("codicevolo"),
                    rs.getString("compagniaaerea"),
                    rs.getString("aeroportoorigine"),
                    rs.getString("aeroportodestinazione"),
                    LocalDate.parse(rs.getString("datavolo"), DateTimeFormatter.ofPattern("yyyy-MM-dd")).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),//Data adesso è di tipo date //TODO inverire la data in formato gg/mm/aaaa (adesso è in formato aaaa-mm-gg)
                    rs.getString("orarioprevisto").substring(0, 5),//Adesso è di tipo time //TODO usare il formato hh:mm (adesso è in formato hh:mm:ss)
                    rs.getInt("ritardo"),
                    rs.getInt("statovolo"),
                    rs.getString("numeroGate")
            );
            voli.add(v);
        }
        return voli;
    }//Fine getVolidaNapoli

}//Parentesi finale
