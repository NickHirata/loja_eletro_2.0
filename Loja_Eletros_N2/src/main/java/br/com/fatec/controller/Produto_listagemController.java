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
import br.com.fatec.model.Produto;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author mathe
 */
public class Produto_listagemController implements Initializable {

    @FXML
    private TableView<Produto> tb_unidade_lista;
    @FXML
    private TableColumn<Produto, Integer> tb_codigo;
    @FXML
    private TableColumn<Produto, String> tb_nome;
    @FXML
    private TableColumn<Produto, String> tb_descricao;
    @FXML
    private TableColumn<Produto, Integer> tb_quantidade;
    @FXML
    private TableColumn<Produto, Double> tb_preco;
    @FXML
    private ComboBox<String> cb_unidade;
    @FXML
    private ImageView btn_lixo;
    
    
    
    private UnidadeDAO unidadeDAO = new UnidadeDAO();
    private ClienteDAO clienteDAO = new ClienteDAO();
    private FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
    private ProdutoDAO produtoDAO = new ProdutoDAO();
    @FXML
    private Label lbl_info;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        preencherComboBox();
        configurarTableViewFiltro();
        
        // Adiciona um ouvinte de evento à ComboBox para filtrar os produtos por unidade
        cb_unidade.setOnAction(this::selecionarUnidade);
    }    

    @FXML
    private void btn_lixo_Click(MouseEvent event) {
    }
    
    
    private void preencherComboBox() {
        List<String> unidades = unidadeDAO.obterNomesUnidades(); 
        unidades.add(0, ""); // Adicionar um item vazio na lista

        // Limpe a ComboBox antes de preenchê-la
        cb_unidade.getItems().clear();

        // Verifique se a lista de unidades não está vazia antes de adicioná-las à ComboBox
        if (unidades != null && !unidades.isEmpty()) {
            cb_unidade.getItems().addAll(unidades); // Adicione as unidades à ComboBox

        }
    }


    private void configurarTableViewFiltro() {
        tb_codigo.setCellValueFactory(new PropertyValueFactory<>("produtoID"));
        tb_nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tb_descricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        tb_preco.setCellValueFactory(new PropertyValueFactory<>("preco"));

        String unidadeSelecionada = cb_unidade.getValue();
        if (unidadeSelecionada != null && !unidadeSelecionada.isEmpty()) {
            tb_quantidade.setCellValueFactory(new PropertyValueFactory<>("qtdNaLoja"));
            lbl_info.setText("As quantidades mostradas são da unidade: " + unidadeSelecionada);
        } else {
            tb_quantidade.setCellValueFactory(new PropertyValueFactory<>("quantidadeTotal"));
            lbl_info.setText("Os valores de quantidade são a somatória de todos os produtos.");
        }

        // Limpa a TableView antes de adicionar os novos dados
        tb_unidade_lista.getItems().clear();

        // Verifica se uma unidade foi selecionada ou não
        if (unidadeSelecionada != null && !unidadeSelecionada.isEmpty()) {
            // Chama o método do DAO para obter os produtos da unidade selecionada
            List<Produto> produtosDaUnidade = produtoDAO.obterProdutosPorUnidade(unidadeSelecionada);

            // Adiciona os produtos filtrados à tabela
            tb_unidade_lista.getItems().addAll(produtosDaUnidade);
        } else {
            // Caso nenhum item seja selecionado na ComboBox, carregar todos os produtos
            List<Produto> todosProdutos = produtoDAO.obterProdutosQtdTotal();
            tb_unidade_lista.getItems().addAll(todosProdutos);
        }
    }




        
        private void selecionarUnidade(ActionEvent event) {
            configurarTableViewFiltro();

        }
}
