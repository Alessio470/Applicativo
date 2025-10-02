package DAO;

import database.ConnessioneDatabase;
import model.Prenotazione;
import model.Volo;
import model.enums.StatoVolo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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

    public List<Volo> getVoli() throws SQLException {

        List<Volo> voli = new ArrayList<>();

        String query = "SELECT * FROM volo"; // nome tabella nel db

        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {
            Volo v = new Volo(
                    rs.getString("codicevolo"),
                    rs.getString("compagniaaerea"),
                    rs.getString("aeroportoorigine"),
                    rs.getString("aeroportodestinazione"),
                    LocalDate.parse(rs.getString("datavolo"), DateTimeFormatter.ofPattern("yyyy-MM-dd")).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),//Data adesso è di tipo date //TODO inverire la data in formato gg/mm/aaaa (adesso è in formato aaaa-mm-gg)
                    rs.getString("orarioprevisto").substring(0, 5),//Adesso è di tipo time
                    rs.getInt("ritardo"),
                    rs.getInt("statovolo"),
                    rs.getString("numeroGate")
            );
            voli.add(v);
        }
        return voli;

    }//Fine getVoli

    public List<Volo> getVoliDaPerNapoli() throws SQLException {


        List<Volo> resultDB = new ArrayList<>();

        final String sql =
                "SELECT v.* FROM volo AS v WHERE v.aeroportoorigine LIKE '%Napoli%'  OR v.aeroportodestinazione LIKE '%Napoli%' ORDER BY v.datavolo, v.orarioprevisto, v.codicevolo";

        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);


        while (rs.next()) {
            Volo v = new Volo(
                    rs.getString("codicevolo"),
                    rs.getString("compagniaaerea"),
                    rs.getString("aeroportoorigine"),
                    rs.getString("aeroportodestinazione"),
                    LocalDate.parse(rs.getString("datavolo"), DateTimeFormatter.ofPattern("yyyy-MM-dd")).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),//Data adesso è di tipo date //TODO inverire la data in formato gg/mm/aaaa (adesso è in formato aaaa-mm-gg)
                    rs.getString("orarioprevisto").substring(0, 5),//Adesso è di tipo time
                    rs.getInt("ritardo"),
                    rs.getInt("statovolo"),
                    rs.getString("numeroGate")
            );
            resultDB.add(v);
        }

return resultDB;
    }//Parentesi finale getVoliDaPerNapoli

    public void updateVolo(Volo v) throws SQLException {

        try {
            final String sql =
                    "UPDATE volo " +
                            "SET compagniaaerea=?, aeroportoorigine=?, aeroportodestinazione=?, " +
                            "    datavolo=?, orarioprevisto=?, statovolo=?,numeroGate=?, ritardo=?" + //TODO FIXARE STA ROBA DI NUMEROGATE
                            "WHERE codicevolo=?";

            try (Connection cn = ConnessioneDatabase.getInstance().getConnection();
                 PreparedStatement ps = cn.prepareStatement(sql)) {

                ps.setString(1, v.getCompagnia());
                ps.setString(2, v.getAeroportoOrigine());
                ps.setString(3, v.getAeroportoDestinazione());
                ps.setDate(4, v.getDatasql());
                ps.setTime(5, v.getOrarioSql());
                ps.setInt(6, v.getStatoToInt()+1);
                ps.setString(7, v.getCodiceV());
                ps.setString(8,v.getGate());
                ps.setInt(9,v.getRitardoMinuti());

                ps.executeUpdate();

            }
        } catch (SQLException ex) {
            throw new SQLException("Errore query di update"+ ex);
        }
    }//parentesi updateVolo

}//Parentesi finale
