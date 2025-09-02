package model.dao.implementations;

import model.dao.VoloDAO;
import database.ConnessioneDatabase;
import model.Volo;
import model.enums.StatoVolo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VoloPostgresDAO implements VoloDAO {
	private final Connection connection;

	public VoloPostgresDAO() throws SQLException {
		this.connection = ConnessioneDatabase.getInstance().getConnection();
	}

	@Override
	public void inserisciVolo(Volo v) throws SQLException {
		PreparedStatement ps = connection.prepareStatement(
				"INSERT INTO \"Volo\" (\"codice\", \"compagnia\", \"data\", \"orario\", \"ritardo\", \"stato\", \"origine\", \"destinazione\", \"gate\") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
		);

		ps.setString(1, v.getCodiceVolo());
		ps.setString(2, v.getCompagniaAerea());
		ps.setString(3, v.getDataVolo());
		ps.setString(4, v.getOrarioPrevisto());
		ps.setString(5, v.getRitardo());
		ps.setString(6, v.getStato().toString());
		ps.setString(7, "Napoli"); // o v.getAeroportoOrigine()
		ps.setString(8, (v instanceof VoloPartenzaDaNapoli vp) ? vp.getAeroportoDestinazione() : "Roma");
		ps.setString(9, (v instanceof VoloPartenzaDaNapoli vp && vp.getGate() != null) ? vp.getGate().getNumeroGate() : null);

		ps.executeUpdate();

		System.out.println("✅ Sto salvando sul DB il volo: " + v.getCodiceVolo());

		ps.close();
	}

	@Override
	public List<Volo> leggiTuttiIVoli() throws SQLException {
		List<Volo> lista = new ArrayList<>();
		Statement st = connection.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM \"Volo\"");

		while (rs.next()) {
			Volo v = new VoloPartenzaDaNapoli(
					rs.getString("codice"),
					rs.getString("compagnia"),
					rs.getString("data"),
					rs.getString("orario"),
					rs.getString("ritardo"),
					StatoVolo.valueOf(rs.getString("stato")),
					rs.getString("destinazione")
			);
			lista.add(v);
		}

		rs.close();
		st.close();
		return lista;
	}
}
