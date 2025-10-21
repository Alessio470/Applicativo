package DAO;

import database.ConnessioneDatabase;
import model.Volo;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO per l’accesso ai dati dei voli.
 * <p>Gestisce inserimento, lettura elenco/filtrata e aggiornamento dei voli.</p>
 */
public class VoloDAO {

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


    /** Connessione JDBC attiva verso il database. */
    private final Connection conn;

    /**
     * Crea un {@code VoloDAO} con la connessione fornita.
     *
     * @param conn connessione JDBC da utilizzare
     */
    public VoloDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserisce un nuovo volo a database.
     *
     * <p>Scrive i campi del modello {@link Volo} nella tabella {@code volo};
     * lo stato è serializzato con {@code getStatoToInt() + 1} per allinearlo alla codifica DB.</p>
     *
     * @param v istanza di volo da registrare
     * @return numero di righe inserite (0 o 1)
     * @throws SQLException in caso di errore durante l’esecuzione
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
     * Restituisce i voli prenotabili (stato = 1).
     *
     * <p>Seleziona dalla tabella {@code volo} i record con {@code statovolo = 1}
     * e costruisce le istanze {@link Volo} convertendo data e orario in stringhe formattate.</p>
     *
     * @return lista (eventualmente vuota) di voli prenotabili
     * @throws SQLException in caso di errore durante l’esecuzione
     */
    public List<Volo> getVoliPrenotabili() throws SQLException {

        List<Volo> voli = new ArrayList<>();

        String query = "SELECT * FROM volo as v WHERE v.statovolo= 1"; // nome tabella nel db

        ResultSet rs;
        try (Statement st = conn.createStatement()) {
            rs = st.executeQuery(query);
        }
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
                    rs.getString(COL_ORARIO_PREVISTO).substring(0, 5), // se vuoi, puoi usare rs.getTime() come suggerito prima
                    rs.getInt(COL_RITARDO),
                    rs.getInt(COL_STATO_VOLO),
                    rs.getString(COL_NUMERO_GATE)
            );
            voli.add(v);
        }
        return voli;
    }//Fine getVolidaNapoli

    /**
     * Restituisce tutti i voli presenti a database.
     *
     * @return lista (eventualmente vuota) di voli
     * @throws SQLException in caso di errore durante l’esecuzione
     */
    public List<Volo> getVoli() throws SQLException {

        List<Volo> voli = new ArrayList<>();

        String query = "SELECT * FROM volo"; // nome tabella nel db

        ResultSet rs;
        try (Statement st = conn.createStatement()) {
            rs = st.executeQuery(query);
        }

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
                    rs.getString(COL_ORARIO_PREVISTO).substring(0, 5), // se preferisci puoi leggere con rs.getTime() per un TIME nativo
                    rs.getInt(COL_RITARDO),
                    rs.getInt(COL_STATO_VOLO),
                    rs.getString(COL_NUMERO_GATE)
            );
            voli.add(v);
        }
        return voli;

    }//Fine getVoli

    /**
     * Restituisce i voli con ordinamento numerico sul suffisso del codice.
     *
     * <p>Esegue {@code ORDER BY substring(codicevolo from 3)::int} per ottenere
     * l’ordinamento naturale di codici come {@code CV3}, {@code CV10}.</p>
     *
     * @return lista (eventualmente vuota) di voli ordinati per codice numerico
     * @throws SQLException in caso di errore durante l’esecuzione
     */
    public List<Volo> getVoliDaPerNapoli() throws SQLException {


        List<Volo> resultDB = new ArrayList<>();

        final String sql =
                "SELECT * FROM volo ORDER BY substring(codicevolo from 3)::int;";

        ResultSet rs;
        try (Statement st = conn.createStatement()) {
            rs = st.executeQuery(sql);
        }


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
                    rs.getString(COL_ORARIO_PREVISTO).substring(0, 5), // se preferisci puoi leggere con rs.getTime() per un TIME nativo
                    rs.getInt(COL_RITARDO),
                    rs.getInt(COL_STATO_VOLO),
                    rs.getString(COL_NUMERO_GATE)
            );
            resultDB.add(v);
        }

        return resultDB;
    }//Parentesi finale getVoliDaPerNapoli

    /**
     * Aggiorna i dati di un volo esistente, individuato dal codice.
     *
     * <p>Imposta compagnia, aeroporti, data/orario, stato, gate e ritardo
     * in base ai valori presenti nell’oggetto {@link Volo} passato.</p>
     *
     * @param v istanza con i nuovi valori
     * @throws SQLException in caso di errore durante l’aggiornamento
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
