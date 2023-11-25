/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fatec.controller;

import br.com.fatec.DAO.ClienteDAO;
import br.com.fatec.DAO.FuncionarioDAO;
import br.com.fatec.DAO.ProdutoDAO;
import br.com.fatec.DAO.UnidadeDAO;
import br.com.fatec.model.Unidade;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
    private TableColumn<Unidade, Integer> tb_codigo;
    @FXML
    private TableColumn<Unidade, String> tb_unidade;
    @FXML
    private TableColumn<Unidade, Void> tb_telefone;
        @FXML
    private TableColumn<Unidade, String> tb_cidade;
    @FXML
    private TableView<Unidade> tb_unidade_lista;
    @FXML
    private ComboBox<String> cmb_cidade;
    @FXML
    private TextField txtF_pesquisa;
    @FXML
    private ImageView btn_lixo;
    
    @FXML
    private Button btn_moreInfo;
    
    private UnidadeDAO unidadeDAO = new UnidadeDAO();
    private ClienteDAO clienteDAO = new ClienteDAO();
    private FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
    private ProdutoDAO produtoDAO = new ProdutoDAO();



    public void initialize() {
        // Configuração das colunas com os atributos da classe Unidade
        tb_codigo.setCellValueFactory(new PropertyValueFactory<>("lojaID"));
        tb_cidade.setCellValueFactory(new PropertyValueFactory<>("cidade"));
        tb_unidade.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tb_telefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        
        preencherComboBox();
        preencherTableView();
        
        btn_lixo.setOnMouseClicked(event -> {
            try {
                btn_lixo_Click();
            } catch (SQLException ex) {
                Logger.getLogger(Unidade_listagemController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        btn_moreInfo.setOnMouseClicked(event -> {
          
           btn_moreInfo_Click();
        
        });
    }
    @FXML
    private void btn_lixo_Click() throws SQLException {
        Unidade unidadeSelecionada = tb_unidade_lista.getSelectionModel().getSelectedItem();

        if (unidadeSelecionada != null) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmação");
            alert.setHeaderText("Excluir Unidade");
            alert.setContentText("Tem certeza de que deseja excluir esta unidade?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    unidadeDAO.excluirUnidade(unidadeSelecionada.getLojaID());

                    tb_unidade_lista.getItems().remove(unidadeSelecionada);

                    // Exibir mensagem de sucesso
                    Alert sucesso = new Alert(AlertType.INFORMATION);
                    sucesso.setTitle("Unidade Excluída");
                    sucesso.setHeaderText(null);
                    sucesso.setContentText("Unidade excluída com sucesso: " + unidadeSelecionada.getNome());
                    sucesso.showAndWait();
                } catch (SQLException e) {
                    e.printStackTrace();
                    Alert erro = new Alert(AlertType.ERROR);
                    erro.setTitle("Erro ao excluir");
                    erro.setHeaderText(null);
                    erro.setContentText("Ocorreu um erro ao excluir a unidade: " + e.getMessage());
                    erro.showAndWait();
                }
        }
        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Nenhuma seleção");
            alert.setHeaderText(null);
            alert.setContentText("Nenhuma unidade selecionada para excluir.");

            alert.showAndWait();
        }
    }




    private void preencherComboBox() {
        List<String> cidades = unidadeDAO.obterCidades();
        cidades.add(0, ""); // Adicionar um item vazio na lista

        cmb_cidade.getItems().addAll(cidades);

        cmb_cidade.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty()) {
                List<Unidade> unidades = unidadeDAO.obterUnidadePorCidade(newValue);

                tb_unidade_lista.getItems().clear();
                tb_unidade_lista.getItems().addAll(unidades);
            } else {
                preencherTableView();
            }
        });
}
    
    private void preencherTableView() {
        try {
            // Chame o método do DAO para obter os dados das unidades
            UnidadeDAO unidadeDAO = new UnidadeDAO();
            List<Unidade> unidades = unidadeDAO.buscarTodasUnidades();

            // Atualize a TableView na thread da plataforma JavaFX
            Platform.runLater(() -> {
                // Limpe os itens existentes na TableView de Unidades
                tb_unidade_lista.getItems().clear();

                // Adicione os novos itens à TableView de Unidades
                tb_unidade_lista.getItems().addAll(unidades);
            });
        } catch (Exception e) {
            // Trate a exceção e exiba uma mensagem para o usuário
            mostrarErro("Erro ao carregar dados", "Ocorreu um erro ao obter dados das unidades.\n" + e.getMessage());
        }
    }

    private void mostrarErro(String titulo, String mensagem) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
    
    
       
    private void btn_moreInfo_Click() {
        Unidade unidadeSelecionada = tb_unidade_lista.getSelectionModel().getSelectedItem();

        if (unidadeSelecionada != null) {
            exibirDetalhesUnidade(unidadeSelecionada);
        } else {
            Alert nenhumSelecionado = new Alert(AlertType.INFORMATION);
            nenhumSelecionado.setTitle("Nenhuma Seleção");
            nenhumSelecionado.setHeaderText(null);
            nenhumSelecionado.setContentText("Nenhuma unidade selecionada para mostrar detalhes.");
            nenhumSelecionado.showAndWait();
        }
    }

    private void exibirDetalhesUnidade(Unidade unidade) {
        Alert detalhes = new Alert(AlertType.INFORMATION);
        detalhes.setTitle("Detalhes da Unidade");
        detalhes.setHeaderText("Informações da Unidade");

        String mensagem = "Nome: " + unidade.getNome() + "\n"
                        + "Código: " + unidade.getLojaID() + "\n"
                        + "Telefone: " + unidade.getTelefone() + "\n"
                        + "Endereço: " + unidade.getEndereco() + "\n"
                        + "Cidade: " + unidade.getCidade() + "\n"
                        + "Email: " + unidade.getEmail();

        detalhes.setContentText(mensagem);
        detalhes.showAndWait();
    }

}
    

