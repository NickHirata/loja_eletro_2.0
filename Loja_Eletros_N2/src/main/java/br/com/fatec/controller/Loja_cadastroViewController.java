/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fatec.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author nicol
 */
public class Loja_cadastroViewController {

    @FXML
    private Label lbl_nome;
    @FXML
    private Label lblDescricao;
    @FXML
    private Label lbl_endereco;
    @FXML
    private TextField txt_nome;
    @FXML
    private ComboBox<?> cb_cidade;
    @FXML
    private Label lbl_cidade;
    @FXML
    private TextField cb_endereco;
    @FXML
    private Label lbl_telefone;
    @FXML
    private TextField cb_telefone;
    @FXML
    private TextField txt_email;
    @FXML
    private Button btn_voltar;
    @FXML
    private Button btn_inserir;
    @FXML
    private Button btn_excluir;

    @FXML
    private void btn_voltar_Click(ActionEvent event) {
    }

    @FXML
    private void btn_inserir_Click(ActionEvent event) {
    }

    @FXML
    private void btn_excluir_click(ActionEvent event) {
    }
    
}
