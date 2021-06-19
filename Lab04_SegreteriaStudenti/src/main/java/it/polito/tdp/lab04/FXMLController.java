package it.polito.tdp.lab04;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Model;
import it.polito.tdp.lab04.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Corso> boxCorsi;

    @FXML
    private TextField txtMatricola;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtCognome;

    @FXML
    private TextArea txtRisultato;

	@FXML
	void doCercaCorsi(ActionEvent event) {

		String matricola = this.txtMatricola.getText();
		Integer matrix = 0;

		try {
			matrix = Integer.parseInt(matricola);
		} catch (NumberFormatException e) {
			txtRisultato.setText("Inserire un numero");
			return;
		}
		

		if (this.model.getStudente(matrix)== null) {
			this.txtRisultato.setText("Studente non esiste");
			this.txtMatricola.clear();
			return;
		}
		
		txtRisultato.clear();
    	for(Corso c : this.model.getCorsiStudente(matrix)) {
    		txtRisultato.appendText(c.toString() + "\n");
    	}
    }

	

    @FXML
    void doCercaIscritti(ActionEvent event) {
    	
    	
    	Corso corso = this.boxCorsi.getValue();
    	
    	if(corso == null || corso.getNome().compareTo("")==0) {
    		txtRisultato.setText("Selezionare un corso");
    		return;
    	}
    	
    	this.txtRisultato.clear();
    	
    	
    	
    	List<Studente> studentiIscritti = new LinkedList<>();
    	
    	studentiIscritti = model.getStudentiIscrittiAlCorso(corso);
    	
    	String output ="";
    	
    	for(Studente s : studentiIscritti) {
    		output+=s.toString()+"\n";
    	}
    	
    	this.txtRisultato.setText(output);
    	
    	

    }

    @FXML
    void doCompletamento(ActionEvent event) {
    	
    	String matricola = this.txtMatricola.getText();
		Integer matrix = 0;

		try {
			matrix = Integer.parseInt(matricola);
		} catch (NumberFormatException e) {
			txtRisultato.setText("Inserire un numero");
			return;
		}
		
		String nome = this.model.getStudente(matrix).getNome();
		String cognome = this.model.getStudente(matrix).getCognome();
		
		this.txtNome.setText(nome);
		this.txtCognome.setText(cognome);
   

    }

	@FXML
	void doIscrivi(ActionEvent event) {

		String matricola = this.txtMatricola.getText();
		Integer matrix = 0;
		Corso corso = this.boxCorsi.getValue();

		try {
			matrix = Integer.parseInt(matricola);
		} catch (NumberFormatException e) {
			txtRisultato.setText("Inserire un numero");
			return;
		}

		if (corso == null || corso.getNome().compareTo("") == 0) {
			txtRisultato.setText("Selezionare un corso");
			return;
		}

		if (model.isIscritto(matrix, corso)) {
			this.txtRisultato.setText("Studente già iscritto");
			return;
		} else {
			Studente s = new Studente(matrix, this.txtCognome.getText(), this.txtNome.getText());
			this.model.iscriviStudenteACorso(s, corso);
			this.txtRisultato.setText(s.toString() + " iscritto al corso " + corso.getNome());
		}

	}

    @FXML
    void doReset(ActionEvent event) {
    	
    	boxCorsi.getSelectionModel().clearSelection();
    	this.txtCognome.clear();
    	this.txtMatricola.clear();
    	this.txtNome.clear();
    	this.txtRisultato.clear();

    }

    @FXML
    void initialize() {
        assert boxCorsi != null : "fx:id=\"boxCorsi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtRisultato != null : "fx:id=\"txtRisultato\" was not injected: check your FXML file 'Scene.fxml'.";        

    }



	void setModel(Model model) {
		this.model = model;
		this.boxCorsi.getItems().addAll(model.getCorsi());
	}
}

