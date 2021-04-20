package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class CorsoDAO {


	/*
	 * Ottengo tutti i corsi salvati nel Db
	 */
	public List<Corso> getTuttiICorsi() {

		final String sql = "SELECT * FROM corso";

		 List<Corso>corsi = new LinkedList<Corso>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");

				System.out.println(codins + " " + numeroCrediti + " " + nome + " " + periodoDidattico);

				Corso corso = new Corso(codins, numeroCrediti, nome, periodoDidattico);
				corsi.add(corso);
			}

			conn.close();
			
			return corsi;
			

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}
	
	
	/*
	 * Ottengo tutti gli studenti iscritti al Corso
	 */
	public List<Studente> getStudentiIscrittiAlCorso(Corso corso){
		
		List<Studente> studenti = new LinkedList<>();
		
		final String sql = "SELECT s.matricola, s.nome, s.cognome, s.CDS  "
				+ "FROM studente s, iscrizione i "
				+ "WHERE s.matricola = i.matricola  "
				+ "AND i.codins = ? "
				+ "ORDER BY s.matricola";

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			st.setString(1, corso.getCodins());
			ResultSet rs = st.executeQuery();
			

			while (rs.next()) {

				Integer matricola = rs.getInt("s.matricola");
				String nome = rs.getString("s.nome");
				String cognome = rs.getString("s.cognome");

				Studente s = new Studente(matricola, nome, cognome);
				studenti.add(s);
				
				System.out.println(s.toString());
			}

			conn.close();
			
			return studenti;
			

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}



	/*
	 * Data una matricola ed il codice insegnamento, iscrivi lo studente al corso.
	 */
	public boolean iscriviStudenteACorso(Studente studente, Corso corso) {
		
		final String sql = "INSERT INTO iscrizione (matricola, codins) " + "VALUES (?, ?)";

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, studente.getMatricola());
			st.setString(2, corso.getCodins());
			
			int rs = st.executeUpdate();
			
			conn.close();
			
			// ritorna true se l'iscrizione e' avvenuta con successo
			if(rs == 1) {
				return true;
			}else {
				return false;
			}

			

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
		
	
	}
	
	public boolean isIscritto(Integer matricola, Corso corso) {
		final String sql = "SELECT * "
				+ "FROM iscrizione "
				+ "WHERE codins = ? "
				+ "AND matricola = ?";
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, corso.getCodins());
			st.setInt(2, matricola);
			
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				return true;
			}

			conn.close();
			
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
		return false;
	}

}
