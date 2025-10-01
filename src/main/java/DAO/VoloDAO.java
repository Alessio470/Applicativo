package DAO;

import database.ConnessioneDatabase;
import model.Prenotazione;
import model.Volo;
import model.enums.StatoVolo;

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
                    rs.getString("orarioprevisto").substring(0, 5),//Adesso è di tipo time //TODO usare il formato hh:mm (adesso è in formato hh:mm:ss)
                    rs.getInt("ritardo"),
                    rs.getInt("statovolo"),
                    rs.getString("numeroGate")
            );
            voli.add(v);
        }
        return voli;

    }//Fine getVoli

    public static DefaultTableModel getTableModelVoliDaPerNapoli() {
        String[] colonne = {
                "Codice volo", "Compagnia", "Origine", "Destinazione",
                "Data", "Orario", "Ritardo (min)", "Stato", "Gate"
        };

        DefaultTableModel model = new DefaultTableModel(colonne, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        final String sql =
                "SELECT codicevolo, compagniaaerea, aeroportoorigine, aeroportodestinazione, " +
                        "       datavolo, orarioprevisto, ritardo, statovolo, \"numeroGate\" " +
                        "FROM   volo " +
                        "WHERE  UPPER(aeroportoorigine) IN ('NAPOLI','NAP','NA') " +
                        "   OR  UPPER(aeroportodestinazione) IN ('NAPOLI','NAP','NA') " +
                        "ORDER BY datavolo, orarioprevisto";

        try (Connection cn = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String codice   = rs.getString("codicevolo");
                String comp     = rs.getString("compagniaaerea");
                String orig     = rs.getString("aeroportoorigine");
                String dest     = rs.getString("aeroportodestinazione");

                // Date/Time: mostriamo "yyyy-MM-dd" e "HH:mm"
                Date data       = rs.getDate("datavolo");
                Time orario     = rs.getTime("orarioprevisto");
                String dataStr  = (data   != null) ? data.toString() : "";
                String oraStr   = (orario != null) ? orario.toString().substring(0,5) : "";

                Integer ritardo = (Integer) rs.getObject("ritardo");        // può essere null
                Object statoObj = rs.getObject("statovolo");                 // int o text
                String statoStr = (statoObj == null) ? "" : String.valueOf(statoObj);
                String gate     = rs.getString("numeroGate");

                model.addRow(new Object[]{ codice, comp, orig, dest, dataStr, oraStr, ritardo, statoStr, gate });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            model.addRow(new Object[]{ "ERRORE DB: " + ex.getMessage(), "", "", "", "", "", "", "", ""});
        }
        return model;
    }

}//Parentesi finale
