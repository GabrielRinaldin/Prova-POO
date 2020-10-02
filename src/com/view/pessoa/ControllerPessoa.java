package com.view.pessoa;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import com.dao.PessoaDAO;
import com.entity.Pessoa;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.time.LocalDate;


public class ControllerPessoa extends Application implements Initializable{

	@FXML
    private TextField textFildAltura;

    @FXML
    private TextField textFildNome;

    @FXML
    private Button Inserir;

    @FXML
    private TextArea textAreaListPessoas;

    @FXML
    private TextField textFildID;

    @FXML
    private Label labelLabelID;

    @FXML
    private Label labelID;

    @FXML
    private Button buttonAlterar;

    @FXML
    private Button buttonDeleta;

    @FXML
    private DatePicker datePickerID;
    
    
    public void execute()
    {
    	launch();
    }
    
    @Override
    public void start(Stage stage)
    {
    	try 
    	{
    	AnchorPane pane = (AnchorPane)FXMLLoader.load(getClass().getResource("Pessoa.fxml"));
    	stage.setTitle("Primeira janela");
    	Scene sc = new Scene(pane);
    	stage.setScene(sc);
    	stage.show();
    	}
    	
    	catch (IOException e)
    	{
    		e.printStackTrace();
    	}
    }

    @FXML
    void InserirPessoa(ActionEvent event) {
    	
    	Pessoa pessoa = pegaDados();
     	limpaCampos();
    	int qtde = new PessoaDAO().inserir(pessoa);
    	listarPessoas();
    	

    }
    
    @FXML
    private void buscarPessoa(ActionEvent event)
    {
    	String idString = textFildID.getText();
    	Pessoa pessoa = null;
    	if(!idString.equals(""))
    	{
    		try
    		{
    			int id = Integer.valueOf(idString);
    			pessoa = new PessoaDAO().findByID(id);
    		}
    		catch (Exception e)
    		{
    			
    		}
    		if (pessoa != null)
    		{
    			labelLabelID.setVisible(true);
    			labelID.setVisible(true);
    			labelID.setText(pessoa.getId()+"");
    			textFildNome.setText(pessoa.getNome());
    			datePickerID.setValue(pessoa.getData().toLocalDate());
    			textFildAltura.setText(pessoa.getAltura()+"");
    		}
    	}
    	
    	textFildID.clear();
    }
    
    
    @FXML
    void alterarPessoa(ActionEvent event) {
    	
    	Pessoa pessoa = pegaDadoID();
    	limpaCampos();
    	int qtde = new PessoaDAO().alterar(pessoa);
    	listarPessoas();
    	
    }
    

    private void limpaCampos()
    {
    	textFildNome.clear();
    	textFildAltura.clear();
    	textFildNome.requestFocus();
    	labelID.setText("");
    	datePickerID.setValue(null);
    	labelID.setVisible(false);
    	labelLabelID.setVisible(false);
    }
    
    private Pessoa pegaDados()
    {
    	return new Pessoa(textFildNome.getText(), Date.valueOf(datePickerID.getValue()), Float.valueOf(textFildAltura.getText()));
    }
    
    private Pessoa pegaDadoID()
    {
    	return new Pessoa(Integer.valueOf(labelID.getText()),textFildNome.getText(), Date.valueOf(datePickerID.getValue()), Float.valueOf(textFildAltura.getText()));
    }
    
    private void listarPessoas()
    {
    	textAreaListPessoas.clear();
    	List<Pessoa> listaPessoa = new PessoaDAO().listALL();
    	listaPessoa.forEach(pessoa -> 
    	{
    		textAreaListPessoas.appendText(pessoa.toString()+"\n");
    	});
    	textAreaListPessoas.appendText("\n " + listaPessoa.size() + " Registro(s) cadastrado(s).");
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		listarPessoas();
		
	}
	
	@FXML
    void deletaPessoa(ActionEvent event) 
  {
	  int opt = JOptionPane.showConfirmDialog(null, "Quem mesmo deletar esse registro?" , "Delete", JOptionPane.YES_NO_OPTION);
	  if(opt ==0 )
	  {
		  Pessoa pessoa = pegaDadoID();
		  limpaCampos(); 	
	      int qtde = new PessoaDAO().deleta(pessoa);
	      listarPessoas();
	  }
	  
   }
   
}
