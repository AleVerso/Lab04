package it.polito.tdp.lab04.model;

public class Studente {

	private Integer matricola;
	private String nome;
	private String cognome;

	public Studente(Integer matricola, String nome, String cognome) {
		super();
		this.matricola = matricola;
		this.nome = nome;
		this.cognome = cognome;
	}

	
	public Integer getMatricola() {
		return matricola;
	}

	public void setMatricola(Integer matricola) {
		this.matricola = matricola;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	@Override
	public String toString() {
		return matricola + " " + cognome + " " + nome;
	}

}
