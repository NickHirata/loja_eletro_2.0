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
import br.com.fatec.model.Unidade;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

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
    private ComboBox<String> cb_produto;
    @FXML
    private Label lbl_qtde;
    @FXML
    private ComboBox<String> cb_loja;
    @FXML
    private TextField txt_qtde;
    @FXML
    private Button btn_inserir;
    @FXML
    private Button btn_inserirTemp;
    
    

    /**
     * Initializes the controller class.
     */
    
    private UnidadeDAO unidadeDAO = new UnidadeDAO();
    private ClienteDAO clienteDAO = new ClienteDAO();
    private FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
    private ProdutoDAO produtoDAO = new ProdutoDAO();
    
    private Map<String, Map<String, Integer>> estoqueTemporario = new HashMap<>();
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        preencherComboBoxes();
        btn_inserir.setOnAction(event -> btn_inserir_Click());
        btn_inserirTemp.setOnAction(event -> btn_inserirTemp_Click());
    }    

    @FXML
    private void btn_inserir_Click() {
        try {
            String nomeProduto = cb_produto.getValue();
            String nomeLoja = cb_loja.getValue();
            int quantidade = Integer.parseInt(txt_qtde.getText());

            if (nomeProduto != null && nomeLoja != null && !nomeProduto.isEmpty() && !nomeLoja.isEmpty()) {
                Produto produto = produtoDAO.obterProdutoPorNome(nomeProduto);
                Unidade unidade = unidadeDAO.obterUnidadePorNome(nomeLoja);

                if (produto != null && unidade != null) {
                    if (quantidade >= 0) { // Verifica se a quantidade é não negativa
                        produto.setLojaID(unidade.getLojaID());
                        produto.setQtdNaLoja(quantidade);

                        boolean atualizadoOuInserido = produtoDAO.atualizarOuInserirProduto(produto);

                        if (atualizadoOuInserido) {
                            exibirAlerta("Sucesso", "Produto atualizado ou inserido com sucesso.");


                            // Limpa os campos após a inserção ou atualização bem-sucedida
                            cb_produto.setValue(null);
                            cb_loja.setValue(null);
                            txt_qtde.clear();
                            
                        } else {
                            exibirAlerta("Erro", "Ocorreu um erro ao atualizar ou inserir o produto.");
                        }
                    } else {
                        exibirAlerta("Erro", "A quantidade não pode ser negativa.");
                    }
                } else {
                    exibirAlerta("Erro", "Produto ou loja não encontrados.");
                }
            } else {
                exibirAlerta("Erro", "Por favor, selecione um produto e uma loja e informe a quantidade.");
            }
        } catch (NumberFormatException e) {
            exibirAlerta("Erro", "Digite um número válido para a quantidade.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void exibirAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
}

    private void preencherComboBoxes() {
    preencherComboBoxProduto();
    preencherComboBoxLoja();
}

    private void preencherComboBoxProduto() {
        List<String> produtos = produtoDAO.obterNomesProdutos();

        // Limpa a ComboBox antes de preencher
        cb_produto.getItems().clear();

        if (produtos != null && !produtos.isEmpty()) {
            cb_produto.getItems().addAll(produtos);
        }
    }

    private void preencherComboBoxLoja() {
        List<String> lojas = unidadeDAO.obterNomesUnidades();

        // Limpa a ComboBox antes de preencher
        cb_loja.getItems().clear();

        if (lojas != null && !lojas.isEmpty()) {
            cb_loja.getItems().addAll(lojas);
        }
    }   
    
    
    

    private void btn_inserirTemp_Click() {
        try {
            String nomeProduto = cb_produto.getValue();
            int quantidade = Integer.parseInt(txt_qtde.getText());

            if (nomeProduto != null && !nomeProduto.isEmpty()) {
                // Verifica se a quantidade é não negativa
                if (quantidade >= 0) {
                    String nomeLoja = cb_loja.getValue();
                    if (nomeLoja != null && !nomeLoja.isEmpty()) {
                        if (estoqueTemporario.containsKey(nomeLoja)) {
                            Map<String, Integer> produtosNaLoja = estoqueTemporario.get(nomeLoja);
                            if (produtosNaLoja.containsKey(nomeProduto)) {
                                int quantidadeAtual = produtosNaLoja.get(nomeProduto);
                                produtosNaLoja.put(nomeProduto, quantidadeAtual + quantidade);
                            } else {
                                produtosNaLoja.put(nomeProduto, quantidade);
                            }
                        } else {
                            Map<String, Integer> novoProduto = new HashMap<>();
                            novoProduto.put(nomeProduto, quantidade);
                            estoqueTemporario.put(nomeLoja, novoProduto);
                        }

                        // Limpa os campos após a inserção ou atualização bem-sucedida
                        cb_produto.setValue(null);
                        cb_loja.setValue(null);
                        txt_qtde.clear();

                        // Construir a mensagem para exibição no pop-up
                        StringBuilder message = new StringBuilder("Coleção adicionada:\n");
                        for (Map.Entry<String, Map<String, Integer>> loja : estoqueTemporario.entrySet()) {
                            message.append("Loja: ").append(loja.getKey()).append("\n");
                            for (Map.Entry<String, Integer> produto : loja.getValue().entrySet()) {
                                message.append("- Produto: ").append(produto.getKey()).append("\n- Quantidade: ").append(produto.getValue()).append("\n");
                            }
                        }

                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Informações do Estoque Temporário");
                        alert.setHeaderText(null);

                        // Usando Text para formatar o texto dentro do Alert
                        Text textArea = new Text(message.toString());
                        textArea.setFont(Font.font("Arial", FontWeight.BOLD, 12));
                        alert.getDialogPane().setContent(textArea);
                        alert.showAndWait();

                    } else {
                        exibirAlerta("Erro", "Por favor, selecione uma loja.");
                    }
                } else {
                    exibirAlerta("Erro", "A quantidade não pode ser negativa.");
                }
            } else {
                exibirAlerta("Erro", "Por favor, selecione um produto e informe a quantidade.");
            }
        } catch (NumberFormatException e) {
            exibirAlerta("Erro", "Digite um número válido para a quantidade.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }






}
