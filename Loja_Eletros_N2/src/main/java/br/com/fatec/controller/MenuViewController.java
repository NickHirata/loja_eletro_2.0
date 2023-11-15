package br.com.fatec.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import br.com.fatec.model.Cliente;
import br.com.fatec.model.Funcionario;
import br.com.fatec.model.Produto;
import br.com.fatec.model.Unidade;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author nicol
 */
public class MenuViewController implements Initializable {

    @FXML
    private MenuItem mi_funcionario;
    @FXML
    private MenuItem mi_loja;
    @FXML
    private MenuItem mi_cliente;
    @FXML
    private MenuItem mi_produto;
    @FXML
    private Menu m_unidade;
    @FXML
    private MenuItem mi_rel_unidade;
    @FXML
    private MenuItem mi_rel_produto;
    @FXML
    private MenuItem mi_rel_funcionario;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void mi_funcionario_Click(ActionEvent event) throws IOException{
        Funcionario fun = new Funcionario();
        fun.start(new Stage());
            
    }

    @FXML
    private void mi_loja_Click(ActionEvent event) throws IOException {
        Unidade x = new Unidade();
        x.start(new Stage());

    }



    @FXML
    private void mi_cliente_click(ActionEvent event) throws IOException {
        Cliente x = new Cliente();
        x.start(new Stage());
    }


    @FXML
    private void mi_produto_Click(ActionEvent event) throws IOException {
        Produto prod = new Produto();
        prod.start(new Stage());
    }


    @FXML
    private void mi_rel_unidade_click(ActionEvent event) throws IOException {
        Unidade x = new Unidade();
        x.start_lista(new Stage());
    }

    @FXML
    private void mi_rel_produto_click(ActionEvent event) throws IOException {
        Produto prod = new Produto();
        prod.start_r(new Stage());
    }

    @FXML
    private void mi_rel_funcionario_click(ActionEvent event) throws IOException {
        Funcionario fun = new Funcionario();
        fun.start_r(new Stage());
    }

    @FXML
    private void m_unidade_click(ActionEvent event) {
    }
    
    
}
