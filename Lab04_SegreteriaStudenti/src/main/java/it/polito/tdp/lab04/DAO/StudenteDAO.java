package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class StudenteDAO {

	public Studente getStudente(Integer matricola) {

		final String sql = "SELECT nome, cognome FROM studente WHERE matricola = ?";
		Studente studente = null;
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, matricola);
			
			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String nome = rs.getString("nome");
				String cognome = rs.getString("cognome");
				
				studente = new Studente(matricola, nome, cognome);
				
				System.out.println(studente.toString());
			}

			conn.close();
			
			return studente;
			

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}

	// trova tutti i corsi a cui è iscritto uno studente tramite matricola

	public List<Corso> getCorsiStudente(Integer matricola) {

		List<Corso> corsi = new LinkedList<Corso>();

		final String sql = "SELECT c.codins, c.crediti, c.nome, c.pd "
				+ "FROM iscrizione i, corso c "
				+ "WHERE i.codins = c.codins "
				+ "AND i.matricola = ? "
				+ "ORDER BY i.codins";

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			st.setInt(1, matricola);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String codins = rs.getString("codins");
				Integer crediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				Integer periodoDidattico = rs.getInt("pd");

				Corso corso = new Corso(codins, crediti, nome, periodoDidattico);

				corsi.add(corso);

				System.out.println(codins + " " + crediti + " " + nome + " " + periodoDidattico);
			}

			conn.close();
			return corsi;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}

	}
}