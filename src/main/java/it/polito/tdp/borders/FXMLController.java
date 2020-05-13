
package it.polito.tdp.borders;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtAnno"
    private TextField txtAnno; // Value injected by FXMLLoader
    
    @FXML // fx:id="cmbBox"
    private ComboBox<Country> cmbBox; // Value injected by FXMLLoader

    @FXML // fx:id="btnSitiRaggiungibili"
    private Button btnSitiRaggiungibili; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader
    
    @FXML
    void doCercaSitiRaggiungibili(ActionEvent event) {
    	Country source=this.cmbBox.getValue();
    	Set<Country> raggiungibili=model.cercaSitiRaggiungibili(source);
    	this.txtResult.appendText("Le città raggiungibili sono:\n"+raggiungibili.toString());
    }
    
    @FXML
    void doCalcolaConfini(ActionEvent event) {
    	this.txtResult.clear();
    	String anno=txtAnno.getText();
    	Integer year;
    	try {
    		year=Integer.parseInt(anno);
    	}catch(NumberFormatException ne) {
    		txtResult.appendText("L'anno inserito non ha un formato corretto");
    		ne.printStackTrace();
    		throw new NumberFormatException();
    	}
    	//creo il grafo
    	model.createGraph(year);
    	//stampo gli stati con il relativo numero di stati confinanti
    	txtResult.appendText(""+model.statiConfinanti());
    	
    }
    void loadData() {
    	List<Country> country=model.listaStati();
    	
    	this.cmbBox.getItems().addAll(country);
    }
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbBox != null : "fx:id=\"cmbBox\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSitiRaggiungibili != null : "fx:id=\"btnSitiRaggiungibili\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	loadData();
    }
}