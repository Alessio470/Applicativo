package model.dao.implementations;

import model.dao.interfaces.VoloDAO;
import database.ConnessioneDatabase;
import model.Gate;
import model.Volo;
import model.enums.StatoVolo;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class VoloImplDAO implements VoloDAO {
	private final Connection connection;

	public VoloImplDAO() throws SQLException {
		this.connection = ConnessioneDatabase.getInstance().getConnection();
	}

	@Override
	public void inserisciVolo(Volo v) throws SQLException {
		final String sql = "INSERT INTO \"Volo\" " +
				"(\"codice\", \"compagnia\", \"data\", \"orario\", \"ritardo\", \"stato\", \"origine\", \"destinazione\", \"gate\") " +
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			// data + orario da LocalDateTime
			LocalDate data = v.getDataOra().toLocalDate();
			LocalTime orario = v.getDataOra().toLocalTime();

			ps.setString(1, v.getCodice());
			ps.setString(2, v.getCompagnia());
			ps.setDate(3, Date.valueOf(data));
			ps.setTime(4, Time.valueOf(orario));
			ps.setInt(5, v.getRitardoMinuti());
			ps.setString(6, v.getStato().name());
			ps.setString(7, v.getAeroportoOrigine());
			ps.setString(8, v.getAeroportoDestinazione());

			Integer gateNumero = (v.getGate() != null) ? v.getGate().getNumero() : null;
			if (gateNumero == null) {
				ps.setNull(9, Types.INTEGER);
			} else {
				ps.setInt(9, gateNumero);
			}

			ps.executeUpdate();
		}
	}

	@Override
	public List<Volo> leggiTuttiIVoli() throws SQLException {
		final String sql = "SELECT \"codice\", \"compagnia\", \"data\", \"orario\", \"ritardo\", \"stato\", " +
				"\"origine\", \"destinazione\", \"gate\" FROM \"Volo\" ORDER BY \"data\", \"orario\"";

		List<Volo> lista = new ArrayList<>();

		try (Statement st = connection.createStatement();
			 ResultSet rs = st.executeQuery(sql)) {

			while (rs.next()) {
				String codice = rs.getString("codice");
				String compagnia = rs.getString("compagnia");
				LocalDate data = rs.getDate("data").toLocalDate();
				LocalTime orario = rs.getTime("orario").toLocalTime();
				int ritardo = rs.getInt("ritardo");
				StatoVolo stato = StatoVolo.valueOf(rs.getString("stato"));
				String origine = rs.getString("origine");
				String destinazione = rs.getString("destinazione");
				Integer gateNum = rs.getObject("gate") != null ? rs.getInt("gate") : null;

				Gate gate = (gateNum != null) ? new Gate(gateNum) : null;
				Volo v = new Volo(
						codice,
						compagnia,
						origine,
						destinazione,
						LocalDateTime.of(data, orario),
						ritardo,
						stato,
						gate
				);

				// Mantieni coerenza bidirezionale se gate non è null
				if (gate != null) {
					gate.assegnaVolo(v);
				}

				lista.add(v);
			}
		}

		return lista;
	}

	@Override
	public void aggiornaVolo(Volo v) throws SQLException {
		final String sql = "UPDATE \"Volo\" SET " +
				"\"compagnia\" = ?, " +
				"\"data\" = ?, " +
				"\"orario\" = ?, " +
				"\"ritardo\" = ?, " +
				"\"stato\" = ?, " +
				"\"origine\" = ?, " +
				"\"destinazione\" = ?, " +
				"\"gate\" = ? " +
				"WHERE \"codice\" = ?";

		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			LocalDate data = v.getDataOra().toLocalDate();
			LocalTime orario = v.getDataOra().toLocalTime();

			ps.setString(1, v.getCompagnia());
			ps.setDate(2, Date.valueOf(data));
			ps.setTime(3, Time.valueOf(orario));
			ps.setInt(4, v.getRitardoMinuti());
			ps.setString(5, v.getStato().name());
			ps.setString(6, v.getAeroportoOrigine());
			ps.setString(7, v.getAeroportoDestinazione());

			Integer gateNumero = (v.getGate() != null) ? v.getGate().getNumero() : null;
			if (gateNumero == null) ps.setNull(8, Types.INTEGER);
			else ps.setInt(8, gateNumero);

			ps.setString(9, v.getCodice());

			ps.executeUpdate();
		}
	}
}
