/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fatec.controller;

import br.com.fatec.DAO.UnidadeDAO;
import br.com.fatec.model.Unidade;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *
 * @author nicol
 */
public class Unidade_listagemController {

    
    @FXML
    private Button btn_voltar;
    
    @FXML
    private TableColumn<Unidade, Integer> tb_codigo;
    @FXML
    private TableColumn<Unidade, String> tb_unidade;
    @FXML
    private TableColumn<Unidade, Void> tb_moreInfo;
    @FXML
    private TableView<Unidade> tb_unidade_lista;

    @FXML
    private void btn_voltar_Click(ActionEvent event) {
    }
    
    

    public void initialize() {

    }




        
}
    

