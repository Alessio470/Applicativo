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

/**
 * The type Volo dao.
 */
public class VoloDAO {

    private final Connection conn;

    /**
     * Instantiates a new Volo dao.
     *
     * @param conn the conn
     */
    public VoloDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Registra volo int.
     *
     * @param v the v
     * @return the int
     * @throws SQLException the sql exception
     */
    public int registraVolo(Volo v) throws SQLException {

        // Query inserimento Volo
        final String sql =
                "INSERT INTO volo(codicevolo, compagniaaerea, datavolo, orarioprevisto, ritardo, statovolo, aeroportoorigine, aeroportodestinazione, \"numeroGate\") " +
                        "VALUES (?,?,?,?,?,?,?,?,?)";


        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, v.getCodiceV()); //codicevolo
            ps.setString(2, v.getCompagnia()); // compagniaaerea
            ps.setDate(3, v.getDatasql()); //datavolo
            ps.setTime(4, v.getOrarioSql()); //orarioprevisto
            ps.setInt(5, v.getRitardoMinuti()); //ritardo
            ps.setInt(6, v.getStatoToInt() + 1); //statovolo
            ps.setString(7, v.getAeroportoOrigine()); //aeroportoorigine
            ps.setString(8, v.getAeroportoDestinazione()); //aeroportodestinazione
            ps.setString(9, v.getGate()); //'numeroGate'


            return ps.executeUpdate();
        }
    }//Fine registraVolo


    /**
     * Gets voli prenotabili.
     *
     * @return the voli prenotabili
     * @throws SQLException the sql exception
     */
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

    /**
     * Gets voli.
     *
     * @return the voli
     * @throws SQLException the sql exception
     */
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

    /**
     * Gets voli da per napoli.
     *
     * @return the voli da per napoli
     * @throws SQLException the sql exception
     */
    public List<Volo> getVoliDaPerNapoli() throws SQLException {


        List<Volo> resultDB = new ArrayList<>();

        final String sql =
                "SELECT * FROM volo ORDER BY substring(codicevolo from 3)::int;";

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

    /**
     * Update volo.
     *
     * @param v the v
     * @throws SQLException the sql exception
     */
    public void updateVolo(Volo v) throws SQLException {

        try {
            final String sql =
                    "UPDATE volo " +
                            "SET compagniaaerea=?, aeroportoorigine=?, aeroportodestinazione=?, " +
                            "    datavolo=?, orarioprevisto=?, statovolo=?, \"numeroGate\"=?, ritardo=? " +
                            "WHERE codicevolo=?";

            try (Connection cn = ConnessioneDatabase.getInstance().getConnection();
                 PreparedStatement ps = cn.prepareStatement(sql)) {

                ps.setString(1, v.getCompagnia());
                ps.setString(2, v.getAeroportoOrigine());
                ps.setString(3, v.getAeroportoDestinazione());
                ps.setDate(4, v.getDatasql());
                ps.setTime(5, v.getOrarioSql());
                ps.setInt(6, v.getStatoToInt() + 1);
                ps.setString(7, v.getGate());
                ps.setInt(8, v.getRitardoMinuti());
                ps.setString(9, v.getCodiceV());

                ps.executeUpdate();

            }
        } catch (SQLException ex) {
            throw new SQLException("Errore query di update"+ ex);
        }
    }//parentesi updateVolo

}//Parentesi finale
