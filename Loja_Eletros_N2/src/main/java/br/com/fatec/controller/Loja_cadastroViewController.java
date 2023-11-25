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
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
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
    
    private UnidadeDAO unidadeDAO = new UnidadeDAO();
    private ClienteDAO clienteDAO = new ClienteDAO();
    private FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
    private ProdutoDAO produtoDAO = new ProdutoDAO();
    
    
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

        try {
            String nome = txt_nome.getText();
            String endereco = cb_endereco.getText();
            String telefone = cb_telefone.getText();
            String email = txt_email.getText();
            String cidadeSelecionada = cb_cidade.getValue();

            if (nome.isEmpty() || endereco.isEmpty() || telefone.isEmpty() || email.isEmpty() || cidadeSelecionada == null) {
                exibirAlerta("Erro", "Preencha todos os campos!", Alert.AlertType.ERROR);
                return;
            }

            String novoNome = unidadeDAO.sugerirNomeAlternativo(nome);
            boolean nomeExiste = unidadeDAO.buscarPorNome(nome);

            if (nomeExiste) {
                // Nome já existe, exibe uma mensagem de confirmação
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Nome já existente");
                alert.setHeaderText("O nome já existe. Deseja usar o nome sugerido: \"" + novoNome + "\"?");
                alert.setContentText("Clique em OK para usar o nome sugerido ou em Cancelar para alterar.");

                ButtonType btnOK = new ButtonType("OK");
                ButtonType btnCancel = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(btnOK, btnCancel);

                Optional<ButtonType> resultado = alert.showAndWait();
                if (resultado.isPresent() && resultado.get() == btnOK) {
                    // Usuário aceitou o nome sugerido
                    nome = novoNome;
                    txt_nome.setText(novoNome); // Define o texto no campo
                } else {
                    // Usuário escolheu cancelar, então limpa o campo para permitir uma nova entrada
                    txt_nome.clear();
                    return;
                }
            }

                Unidade novaUnidade = new Unidade(nome, telefone, endereco, cidadeSelecionada, email);
                boolean inseridoComSucesso = unidadeDAO.inserirUnidade(novaUnidade);

                if (inseridoComSucesso) {
                    exibirAlerta("Sucesso", "Unidade cadastrada com sucesso!", Alert.AlertType.INFORMATION);
                    limparCampos(); // Limpa os campos após o sucesso
                } else {
                    exibirAlerta("Erro", "Erro ao cadastrar a unidade!", Alert.AlertType.ERROR);
                    // Tratamento de erro
                }
            } catch (Exception ex) {
                exibirAlerta("Erro", "Ocorreu um erro: " + ex.getMessage(), Alert.AlertType.ERROR);
                ex.printStackTrace(); // Isso imprime detalhes do erro no console para depuração
            }
    }

        
        private void exibirAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
            Alert alert = new Alert(tipo);
            alert.setTitle(titulo);
            alert.setHeaderText(null);
            alert.setContentText(mensagem);
            alert.showAndWait();
        }
        private void limparCampos() {
        txt_nome.clear();
        cb_endereco.clear();
        cb_telefone.clear();
        txt_email.clear();
        cb_cidade.getSelectionModel().clearSelection();
    }

}
