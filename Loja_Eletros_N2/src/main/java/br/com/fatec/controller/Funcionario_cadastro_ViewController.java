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
public class Funcionario_cadastro_ViewController implements Initializable {

    @FXML
    private Label lbl_nome;
    @FXML
    private Label lbl_cargo;
    @FXML
    private Label lbl_salario;
    @FXML
    private TextField txt_nome;
    @FXML
    private ComboBox<?> cb_unidade;
    @FXML
    private Label lbl_unidade;
    @FXML
    private TextField txt_salario;
    @FXML
    private Label lbl_cpf;
    @FXML
    private TextField txt_cpf;
    @FXML
    private TextField txt_cargo;
    @FXML
    private Button btn_voltar;
    @FXML
    private Button btn_inserir;
    @FXML
    private Button btn_excluir;

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

    @FXML
    private void btn_excluir_Click(ActionEvent event) {
    }
    
}
