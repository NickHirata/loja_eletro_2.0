/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fatec.controller;


import br.com.fatec.model.Produto;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


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
    private TextField txt_nome;
    @FXML
    private TextField txtDescricao;
    @FXML
    private ComboBox<String> cbVoltagem;
    @FXML
    private Label lblDescricao1;
    @FXML
    private TextField txtPreco;
    @FXML
    private Button btn_inserir;
    
    private ObservableList<Produto> listaDeProdutos = FXCollections.observableArrayList();
    
    
    
    
    
     @Override
    public void initialize(URL url, ResourceBundle rb) {
        preencherComboBoxVoltagem();
        
        btn_inserir.setOnMouseClicked(event -> {
          btn_inserir_click();
        });
        
    }
    
    
    
    
    @FXML
    private void btn_inserir_click() {
        if (camposPreenchidos()) {
            // Obtém os valores dos campos
            String nome = txt_nome.getText();
            String descricao = txtDescricao.getText();
            String voltagem = cbVoltagem.getValue();
            double preco = Double.parseDouble(txtPreco.getText());

            // Cria um novo objeto Produto com os dados coletados
            Produto novoProduto = new Produto(nome, descricao, voltagem, preco);

            // Adiciona o novo produto à lista de produtos
            listaDeProdutos.add(novoProduto);

            mostrarMensagemSucesso("Produto inserido com sucesso!");
            mostrarTodosProdutos();
        } else {
            mostrarMensagemErro("Por favor, preencha todos os campos.");
        }
    }

    private boolean camposPreenchidos() {
        return !txt_nome.getText().isEmpty() &&
                !txtDescricao.getText().isEmpty() &&
                cbVoltagem.getValue() != null &&
                !txtPreco.getText().isEmpty();
    }

    private void mostrarMensagemSucesso(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void mostrarMensagemErro(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void mostrarTodosProdutos() {
        StringBuilder produtos = new StringBuilder("Lista de Produtos:\n");

        for (Produto produto : listaDeProdutos) {
            produtos.append(produto.toString()).append("\n");
        }

        mostrarMensagemSucesso(produtos.toString());
    }



    private void preencherComboBoxVoltagem() {
        // Cria uma lista de strings com os valores desejados
        ObservableList<String> voltagens = FXCollections.observableArrayList("110V", "220V");

        // Define a lista de strings como itens da ComboBox
        cbVoltagem.setItems(voltagens);

        // Define um valor inicial para a ComboBox, se desejar
        cbVoltagem.setValue("110V"); // Ou outra voltagem que desejar ser exibida inicialmente
    }

    
}
