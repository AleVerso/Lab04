package it.polito.tdp.lab04.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.lab04.DAO.CorsoDAO;
import it.polito.tdp.lab04.DAO.StudenteDAO;

public class Model {

	private CorsoDAO corsoDao;
	private List<Corso> c;
	private StudenteDAO studenteDao;

	public Model() {

		corsoDao = new CorsoDAO();
		c = new ArrayList<>();
		studenteDao = new StudenteDAO();

	}

	public List<Corso> getCorsi() {
		c.add(new Corso("", 0, "", 0));
		c.addAll(corsoDao.getTuttiICorsi());
		return c;
	}

	public List<Studente> getStudentiIscrittiAlCorso(Corso corso) {
		return corsoDao.getStudentiIscrittiAlCorso(corso);
	}

	public boolean iscriviStudenteACorso(Studente studente, Corso corso) {
		return corsoDao.iscriviStudenteACorso(studente, corso);
	}

	public boolean isIscritto(Integer matricola, Corso corso) {
		return corsoDao.isIscritto(matricola, corso);
	}

	public Studente getStudente(Integer matricola) {
		return studenteDao.getStudente(matricola);
	}

	public List<Corso> getCorsiStudente(Integer matricola) {
		return studenteDao.getCorsiStudente(matricola);
	}

}
