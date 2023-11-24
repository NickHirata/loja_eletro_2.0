/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fatec.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

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
    private ComboBox<String> cb_cidade;
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
    private ImageView btn_voltar;
    @FXML
    private Button btn_inserir;

    
    
    @FXML
    public void initialize() {
        preencherComboBoxCidades();
    }

    private void preencherComboBoxCidades() {
        // Cidades da capital São Paulo e principais do estado
        ObservableList<String> cidadesSP = FXCollections.observableArrayList(
                "São Paulo - Capital",
                "Santo André", "São Bernardo do Campo", "São Caetano do Sul", "Diadema", "Guarulhos", "Osasco",
                "Mauá", "Barueri", "Itaquaquecetuba", "Suzano", "Taboão da Serra", "Cotia", "Franco da Rocha",
                "Itapevi", "Embu das Artes", "Embu-Guaçu", "Ferraz de Vasconcelos", "Francisco Morato", "Guararema",
                "Itapecerica da Serra", "Jandira", "Juquitiba", "Mairiporã", "Ribeirão Pires", "Rio Grande da Serra",
                "Salesópolis", "São Lourenço da Serra", "Vargem Grande Paulista"

        );



        // Preencher o ComboBox com as cidades organizadas de São Paulo
        cb_cidade.setItems(cidadesSP);
    }

    @FXML
    private void btn_inserir_Click(ActionEvent event) {
    }
    
}
