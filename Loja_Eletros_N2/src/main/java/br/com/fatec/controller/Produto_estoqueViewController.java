/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fatec.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author nicol
 */
public class Produto_estoqueViewController implements Initializable {

    @FXML
    private Label lbl_loja;
    @FXML
    private Label lbl_produto;
    @FXML
    private ComboBox<?> cb_produto;
    @FXML
    private Label lbl_qtde;
    @FXML
    private ComboBox<?> cb_loja;
    @FXML
    private TextField txt_qtde;
    @FXML
    private Button btn_voltar;
    @FXML
    private Button btn_inserir;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btn_voltar_Click(ActionEvent event) {
    }

    @FXML
    private void btn_inserir_Click(ActionEvent event) {
    }
    
}
