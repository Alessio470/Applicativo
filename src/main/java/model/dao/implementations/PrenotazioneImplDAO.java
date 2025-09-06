package model.dao.implementations;

import model.dao.interfaces.PrenotazioneDAO;
import database.ConnessioneDatabase;
import model.Prenotazione;
import model.enums.StatoPrenotazione;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrenotazioneImplDAO implements PrenotazioneDAO {
    private final Connection conn;

    public PrenotazioneImplDAO() throws SQLException {
        this.conn = ConnessioneDatabase.getInstance().getConnection();
    }

    @Override
    public void inserisciPrenotazione(Prenotazione p) throws Exception {
        // Inserisce la prenotazione e restituisce l'id generato (PostgreSQL)
        final String sql =
                "INSERT INTO \"Prenotazione\" " +
                        "(\"username_utente\", \"codice_volo\", \"nome_passeggero\", \"biglietto\", \"posto\", \"stato\") " +
                        "VALUES (?, ?, ?, ?, ?, ?) RETURNING id";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            // username_utente può essere null
            if (p.getUsernameUtente() != null) ps.setString(1, p.getUsernameUtente());
            else ps.setNull(1, Types.VARCHAR);

            ps.setString(2, p.getCodiceVolo());
            ps.setString(3, p.getNomePasseggero());
            ps.setString(4, p.getNumeroBiglietto());
            ps.setString(5, p.getPosto());
            ps.setString(6, p.getStato().name());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    p.setId(rs.getInt(1)); // setta l'id generato nel model
                }
            }
        }
    }

    @Override
    public List<Prenotazione> getPrenotazioniPerUtente(String usernameUtente) throws Exception {
        // Restituisce le prenotazioni filtrando per username_utente
        final String sql =
                "SELECT p.\"id\", p.\"username_utente\", p.\"codice_volo\", p.\"nome_passeggero\", " +
                        "       p.\"biglietto\", p.\"posto\", p.\"stato\" " +
                        "FROM \"Prenotazione\" p " +
                        "WHERE p.\"username_utente\" = ? " +
                        "ORDER BY p.\"id\"";

        List<Prenotazione> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usernameUtente);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Prenotazione p = new Prenotazione(
                            rs.getInt("id"),
                            rs.getString("username_utente"),
                            rs.getString("codice_volo"),
                            rs.getString("nome_passeggero"),
                            rs.getString("biglietto"),
                            rs.getString("posto"),
                            StatoPrenotazione.valueOf(rs.getString("stato"))
                    );
                    lista.add(p);
                }
            }
        }
        return lista;
    }

    @Override
    public void eliminaPrenotazione(int idPrenotazione) throws Exception {
        final String sql = "DELETE FROM \"Prenotazione\" WHERE \"id\" = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPrenotazione);
            ps.executeUpdate();
        }
    }
}
