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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author mathe
 */
public class Produto_listagemController implements Initializable {

    @FXML
    private Button btn_voltar;
    @FXML
    private TableView<?> tb_unidade_lista;
    @FXML
    private TableColumn<?, ?> tb_codigo;
    @FXML
    private TableColumn<?, ?> tb_unidade;
    @FXML
    private TableColumn<?, ?> tb_moreInfo;
    @FXML
    private TableColumn<?, ?> tb_codigo1;
    @FXML
    private TableColumn<?, ?> tb_moreInfo1;

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
    
}
