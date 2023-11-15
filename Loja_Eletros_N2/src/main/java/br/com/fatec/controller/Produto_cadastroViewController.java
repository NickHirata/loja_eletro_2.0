/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fatec.controller;

import br.com.fatec.Principal;
import br.com.fatec.model.Produto;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mathe
 */
public class Produto_cadastroViewController implements Initializable {

    @FXML
    private Label lblCodigo;
    @FXML
    private Label lblDescricao;
    @FXML
    private Label lblPreco;
    @FXML
    private TextField txtCodigo;
    @FXML
    private TextField txtDescricao;
    @FXML
    private ComboBox<?> cbProduto;
    @FXML
    private Label lblDescricao1;
    @FXML
    private TextField txtCodigo1;
    private Button btnInserirCombo;
    @FXML
    private Button btnFechar;

    private Stage stage;
    @FXML
    private Button btn_excluir;

    public Label getLblCodigo() {
        return lblCodigo;
    }

    public void setLblCodigo(Label lblCodigo) {
        this.lblCodigo = lblCodigo;
    }

    public Label getLblDescricao() {
        return lblDescricao;
    }

    public void setLblDescricao(Label lblDescricao) {
        this.lblDescricao = lblDescricao;
    }

    public Label getLblPreco() {
        return lblPreco;
    }

    public void setLblPreco(Label lblPreco) {
        this.lblPreco = lblPreco;
    }

    public TextField getTxtCodigo() {
        return txtCodigo;
    }

    public void setTxtCodigo(TextField txtCodigo) {
        this.txtCodigo = txtCodigo;
    }

    public TextField getTxtDescricao() {
        return txtDescricao;
    }

    public void setTxtDescricao(TextField txtDescricao) {
        this.txtDescricao = txtDescricao;
    }

    public ComboBox<?> getCbProduto() {
        return cbProduto;
    }

    public void setCbProduto(ComboBox<?> cbProduto) {
        this.cbProduto = cbProduto;
    }

    public Label getLblDescricao1() {
        return lblDescricao1;
    }

    public void setLblDescricao1(Label lblDescricao1) {
        this.lblDescricao1 = lblDescricao1;
    }

    public TextField getTxtCodigo1() {
        return txtCodigo1;
    }

    public void setTxtCodigo1(TextField txtCodigo1) {
        this.txtCodigo1 = txtCodigo1;
    }

    public Button getBtnInserirCombo() {
        return btnInserirCombo;
    }

    public void setBtnInserirCombo(Button btnInserirCombo) {
        this.btnInserirCombo = btnInserirCombo;
    }

    public Button getBtnFechar() {
        return btnFechar;
    }

    public void setBtnFechar(Button btnFechar) {
        this.btnFechar = btnFechar;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    


    @FXML
    private void btnFechar_Click(ActionEvent event) {


        
    }

    @FXML
    private void btn_excluir_click(ActionEvent event) {
    }
    
}
