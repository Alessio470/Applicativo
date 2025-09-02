package model.dao.implementations;

import model.dao.interfaces.PrenotazioneDAO;
import database.ConnessioneDatabase;
import model.Prenotazione;
import model.Volo;
import model.enums.StatoPrenotazione;
import model.enums.StatoVolo;
import model.VoloPartenzaDaNapoli;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrenotazionePostgresDAO implements PrenotazioneDAO {
    private final Connection conn;

    public PrenotazionePostgresDAO() throws SQLException {
        this.conn = ConnessioneDatabase.getInstance().getConnection();
    }

    @Override
    public void inserisciPrenotazione(Prenotazione p) throws Exception {
        String sql = "INSERT INTO \"Prenotazione\" (\"biglietto\", \"codice_fiscale\", \"nome\", \"cognome\", \"posto\", \"stato\", \"codice_volo\") " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNumeroBiglietto());
            ps.setString(2, p.getCodiceFiscalePasseggero());
            ps.setString(3, p.getNomePasseggero());
            ps.setString(4, p.getCognomePasseggero());
            ps.setString(5, p.getNumeroPosto());
            ps.setString(6, p.getStatoPrenotazione().toString());
            ps.setString(7, p.getVolo().getCodiceVolo());

            ps.executeUpdate();
        }
    }

    @Override
    public List<Prenotazione> getPrenotazioniPerUtente(String codiceFiscale) throws Exception {
        List<Prenotazione> lista = new ArrayList<>();
        String sql = "SELECT p.*, v.* FROM \"Prenotazione\" p JOIN \"Volo\" v ON p.\"codice_volo\" = v.\"codice\" WHERE p.\"codice_fiscale\" = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, codiceFiscale);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Volo volo = new VoloPartenzaDaNapoli(
                        rs.getString("codice"),
                        rs.getString("compagnia"),
                        rs.getString("data"),
                        rs.getString("orario"),
                        rs.getString("ritardo"),
                        StatoVolo.valueOf(rs.getString("stato")),
                        rs.getString("destinazione")
                );

                Prenotazione p = new Prenotazione(
                        rs.getString("biglietto"),
                        rs.getString("codice_fiscale"),
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getString("posto"),
                        StatoPrenotazione.valueOf(rs.getString("stato")),
                        volo
                );

                lista.add(p);
            }
        }

        return lista;
    }
}
