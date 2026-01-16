package dao.implementazioniPostgresDAO;

import dao.VoloDAO;
import database.ConnessioneDatabase;
import model.Volo;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementazione PostgreSQL di VoloDAO.
 * <p>È sostanzialmente il vecchio VoloDAO, solo rinominato e fatto
 * implementare l’interfaccia.</p>
 */
public class VoloImplementazionePostgresDAO implements VoloDAO {

    //Dichiarazioni delle colonne del db
    private static final String COL_CODICE_VOLO = "codicevolo";
    private static final String COL_COMPAGNIA_AEREA = "compagniaaerea";
    private static final String COL_AEROPORTO_ORIGINE = "aeroportoorigine";
    private static final String COL_AEROPORTO_DESTINAZIONE = "aeroportodestinazione";
    private static final String COL_DATA_VOLO = "datavolo";
    private static final String COL_ORARIO_PREVISTO = "orarioprevisto";
    private static final String COL_RITARDO = "ritardo";
    private static final String COL_STATO_VOLO = "statovolo";
    private static final String COL_NUMERO_GATE = "numeroGate";

    private Connection conn() throws SQLException {
        return ConnessioneDatabase.getInstance().getConnection();
    }

    @Override
    public int registraVolo(Volo v) throws SQLException {

        final String sql =
                "INSERT INTO volo(codicevolo, compagniaaerea, datavolo, orarioprevisto, ritardo, statovolo, aeroportoorigine, aeroportodestinazione, \"numeroGate\") " +
                        "VALUES (?,?,?,?,?,?,?,?,?)";

        Connection c = conn();
        try (PreparedStatement ps = c.prepareStatement(sql)) {

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
    }

    @Override
    public List<Volo> getVoliPrenotabili() throws SQLException {

        List<Volo> voli = new ArrayList<>();

        String query = "SELECT * FROM volo as v WHERE v.statovolo= 1";

        Connection c = conn();
        try (Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                Volo v = new Volo(
                        rs.getString(COL_CODICE_VOLO),
                        rs.getString(COL_COMPAGNIA_AEREA),
                        rs.getString(COL_AEROPORTO_ORIGINE),
                        rs.getString(COL_AEROPORTO_DESTINAZIONE),
                        LocalDate.parse(
                                rs.getString(COL_DATA_VOLO),
                                DateTimeFormatter.ofPattern("yyyy-MM-dd")
                        ).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        rs.getString(COL_ORARIO_PREVISTO).substring(0, 5),
                        rs.getInt(COL_RITARDO),
                        rs.getInt(COL_STATO_VOLO),
                        rs.getString(COL_NUMERO_GATE)
                );
                voli.add(v);
            }
        }
        return voli;
    }

    @Override
    public List<Volo> getVoli() throws SQLException {

        List<Volo> voli = new ArrayList<>();

        String query = "SELECT * FROM volo";

        Connection c = conn();
        try (Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                Volo v = new Volo(
                        rs.getString(COL_CODICE_VOLO),
                        rs.getString(COL_COMPAGNIA_AEREA),
                        rs.getString(COL_AEROPORTO_ORIGINE),
                        rs.getString(COL_AEROPORTO_DESTINAZIONE),
                        LocalDate.parse(
                                rs.getString(COL_DATA_VOLO),
                                DateTimeFormatter.ofPattern("yyyy-MM-dd")
                        ).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        rs.getString(COL_ORARIO_PREVISTO).substring(0, 5),
                        rs.getInt(COL_RITARDO),
                        rs.getInt(COL_STATO_VOLO),
                        rs.getString(COL_NUMERO_GATE)
                );
                voli.add(v);
                //System.out.println(v.toString());
            }
        }

        return voli;
    }

    @Override
    public List<Volo> getVoliDaPerNapoli() throws SQLException {

        List<Volo> resultDB = new ArrayList<>();

        final String sql =
                "SELECT * FROM volo ORDER BY substring(codicevolo from 3)::int;";

        Connection c = conn();
        try (Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Volo v = new Volo(
                        rs.getString(COL_CODICE_VOLO),
                        rs.getString(COL_COMPAGNIA_AEREA),
                        rs.getString(COL_AEROPORTO_ORIGINE),
                        rs.getString(COL_AEROPORTO_DESTINAZIONE),
                        LocalDate.parse(
                                rs.getString(COL_DATA_VOLO),
                                DateTimeFormatter.ofPattern("yyyy-MM-dd")
                        ).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        rs.getString(COL_ORARIO_PREVISTO).substring(0, 5),
                        rs.getInt(COL_RITARDO),
                        rs.getInt(COL_STATO_VOLO),
                        rs.getString(COL_NUMERO_GATE)
                );
                resultDB.add(v);
            }
        }

        return resultDB;
    }

    @Override
    public void updateVolo(Volo v) throws SQLException {

        try {
            final String sql =
                    "UPDATE volo " +
                            "SET compagniaaerea=?, aeroportoorigine=?, aeroportodestinazione=?, " +
                            "    datavolo=?, orarioprevisto=?, statovolo=?, \"numeroGate\"=?, ritardo=? " +
                            "WHERE codicevolo=?";

            Connection c = conn();
            try (PreparedStatement ps = c.prepareStatement(sql)) {

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
            throw new SQLException("Errore query di update: " + ex.getMessage(), ex);
        }
    }

    @Override
    public List<Volo> cercaVoli(Volo v) throws SQLException {
        List<Volo> voli = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM volo WHERE 1=1");
        if (v.getCodiceV() != null && !v.getCodiceV().isEmpty()) {
            sql.append(" AND ").append(COL_CODICE_VOLO).append(" = ?");
        }
        if (v.getCompagnia() != null && !v.getCompagnia().isEmpty()) {
            sql.append(" AND ").append(COL_COMPAGNIA_AEREA).append(" = ?");
        }
        if (v.getAeroportoOrigine() != null && !v.getAeroportoOrigine().isEmpty()) {
            sql.append(" AND ").append(COL_AEROPORTO_ORIGINE).append(" = ?");
        }
        if (v.getAeroportoDestinazione() != null && !v.getAeroportoDestinazione().isEmpty()) {
            sql.append(" AND ").append(COL_AEROPORTO_DESTINAZIONE).append(" = ?");
        }
        if (v.getDatasql() != null) {
            sql.append(" AND ").append(COL_DATA_VOLO).append(" = ?");
        }
        if (v.getOrarioSql() != null) {
            sql.append(" AND ").append(COL_ORARIO_PREVISTO).append(" = ?");
        }
        if (v.getStato() != null) {
            sql.append(" AND ").append(COL_STATO_VOLO).append(" = ?");
        }
        if (v.getGate() != null && !v.getGate().isEmpty()) {
            sql.append(" AND ").append(COL_NUMERO_GATE).append(" = ?");
        }

        Connection c = conn();
        try (PreparedStatement ps = c.prepareStatement(sql.toString())) {
            int i = 1;
            if (v.getCodiceV() != null && !v.getCodiceV().isEmpty()) {
                ps.setString(i++, v.getCodiceV());
            }
            if (v.getCompagnia() != null && !v.getCompagnia().isEmpty()) {
                ps.setString(i++, v.getCompagnia());
            }
            if (v.getAeroportoOrigine() != null && !v.getAeroportoOrigine().isEmpty()) {
                ps.setString(i++, v.getAeroportoOrigine());
            }
            if (v.getAeroportoDestinazione() != null && !v.getAeroportoDestinazione().isEmpty()) {
                ps.setString(i++, v.getAeroportoDestinazione());
            }
            if (v.getDatasql() != null) {
                ps.setDate(i++, v.getDatasql());
            }
            if (v.getOrarioSql() != null) {
                ps.setTime(i++, v.getOrarioSql());
            }
            if (v.getStato() != null) {
                ps.setInt(i++, v.getStato().ordinal() + 1);
            }
            if (v.getGate() != null && !v.getGate().isEmpty()) {
                ps.setString(i++, v.getGate());
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Volo volo = new Volo(
                            rs.getString(COL_CODICE_VOLO),
                            rs.getString(COL_COMPAGNIA_AEREA),
                            rs.getString(COL_AEROPORTO_ORIGINE),
                            rs.getString(COL_AEROPORTO_DESTINAZIONE),
                            LocalDate.parse(
                                    rs.getString(COL_DATA_VOLO),
                                    DateTimeFormatter.ofPattern("yyyy-MM-dd")
                            ).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                            rs.getString(COL_ORARIO_PREVISTO).substring(0, 5),
                            rs.getInt(COL_RITARDO),
                            rs.getInt(COL_STATO_VOLO),
                            rs.getString(COL_NUMERO_GATE)
                    );
                    voli.add(volo);
                }
            }
        }
        return voli;
    }
}
