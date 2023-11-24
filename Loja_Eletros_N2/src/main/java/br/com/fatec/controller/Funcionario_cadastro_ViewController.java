/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fatec.controller;

import br.com.fatec.DAO.FuncionarioDAO;
import br.com.fatec.DAO.UnidadeDAO;
import br.com.fatec.Principal;
import br.com.fatec.model.Funcionario;
import br.com.fatec.model.Unidade;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author nicol
 */
public class Funcionario_cadastro_ViewController implements Initializable {

    @FXML
    private Label lbl_nome;
    @FXML
    private Label lbl_cargo;
    @FXML
    private Label lbl_salario;
    @FXML
    private TextField txt_nome;
    @FXML
    private ComboBox<String> cb_unidade;
    @FXML
    private Label lbl_unidade;
    @FXML
    private TextField txt_salario;
    @FXML
    private Label lbl_cpf;
    @FXML
    private TextField txt_cpf;
    @FXML
    private TextField txt_cargo;
    @FXML
    private Button btn_inserir;

    private UnidadeDAO unidadeDAO;
    private FuncionarioDAO funcionarioDAO;
    @FXML
    private Label lbl_nome1;
    @FXML
    private TextField txt_telefone;
    /**
     * Initializes the controller class.
     */
    

    
@Override
public void initialize(URL url, ResourceBundle rb) {
    funcionarioDAO = new FuncionarioDAO();
    unidadeDAO = new UnidadeDAO();
    preencherComboBox();
    
    btn_inserir.setOnMouseClicked(event -> {
        try {
            btn_inserir_Click();
        } catch (IOException ex) {
            mostrarMensagemErro("Erro: " + ex.getMessage());
        }
    });

}
  


    @FXML
    private void btn_inserir_Click() throws IOException {
            if (camposEstaoAtivados()) {
                
                cadastrarFuncionario(); // Modo de inserção

            } else {

                atualizarFuncionario(); // Modo de edição

            }
    }
    
    private boolean camposEstaoAtivados() {
        return !txt_nome.isDisabled();
    }

    private void atualizarFuncionario() throws IOException {


        // Verifica se todos os campos obrigatórios estão preenchidos
            if (txt_cargo.getText().isEmpty() ||
                    txt_salario.getText().isEmpty() ||
                    txt_telefone.getText().isEmpty()){
                mostrarMensagemErro("Por favor, preencha todos os campos.");
                return;
            }


            // Cria um objeto Funcionario com os dados atualizados
            Funcionario funcionarioAtualizado = new Funcionario();
            funcionarioAtualizado.setNome(txt_nome.getText());
            funcionarioAtualizado.setCpf(txt_cpf.getText());
            funcionarioAtualizado.setCargo(txt_cargo.getText());
            funcionarioAtualizado.setTelefone(txt_telefone.getText());

            double salario = Double.parseDouble(txt_salario.getText());
            funcionarioAtualizado.setSalario(salario);

            String nomeUnidadeSelecionada = cb_unidade.getValue();
            int unidadeID = unidadeDAO.buscarIDUnidadePorNome(nomeUnidadeSelecionada);
            funcionarioAtualizado.setLojaID(unidadeID);
             // Obtenha o ID do funcionário com base no CPF
            String cpfFuncionario = txt_cpf.getText();
            try {
                int funcionarioID = funcionarioDAO.buscarIDFuncionarioPorCPF(cpfFuncionario);
                if (funcionarioID != 0) {
                    funcionarioAtualizado.setFuncionarioID(funcionarioID);

                    // Realize a atualização do funcionário no banco de dados
                    funcionarioDAO.atualizarDadosFuncionario(funcionarioAtualizado.getFuncionarioID(),
                            funcionarioAtualizado.getCargo(), funcionarioAtualizado.getSalario(),
                            funcionarioAtualizado.getTelefone());

                    limparCampos();
                    mostrarMensagemSucesso("Funcionário atualizado com sucesso!");
                    mostrarDadosFuncionario(funcionarioAtualizado);
                    // Fechar a janela de cadastro
                    Stage stageCadastro = (Stage) btn_inserir.getScene().getWindow();
                    stageCadastro.close();

                    // Abrir a janela de listagem
                    FXMLLoader loader = new FXMLLoader(Principal.class.getResource("view/Funcionario_listagem.fxml"));
                    Parent root = loader.load();

                    Stage stageListagem = new Stage();
                    stageListagem.setScene(new Scene(root));
                    stageListagem.show();
                } else {
                    mostrarMensagemErro("Funcionário não encontrado.");
                }
            } catch (SQLException e) {
                mostrarMensagemErro("Erro: " + e.getMessage());
            }
           
    }
    
    private void mostrarDadosFuncionario(Funcionario funcionario) {
    String dados = "ID: " + funcionario.getFuncionarioID() + "\n" +
                   "Nome: " + funcionario.getNome() + "\n" +
                   "CPF: " + funcionario.getCpf() + "\n" +
                   "Cargo: " + funcionario.getCargo() + "\n" +
                   "Telefone: " + funcionario.getTelefone() + "\n" +
                   "Salário: " + funcionario.getSalario() + "\n" +
                   "ID da Loja: " + funcionario.getLojaID();

    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Dados do Funcionário");
    alert.setHeaderText(null);
    alert.setContentText(dados);

    alert.showAndWait();
}


    private void preencherComboBox() {
        List<String> unidades = unidadeDAO.obterNomesUnidades();

        // Configurar a ComboBox
        cb_unidade.getItems().addAll(unidades);
    }

    
    
    private void cadastrarFuncionario() {
        mostrarMensagemErro("Cadastrar");
        // Verifica se todos os campos obrigatórios estão preenchidos
        if (txt_nome.getText().isEmpty() || txt_cpf.getText().isEmpty() || txt_cargo.getText().isEmpty() ||
                txt_salario.getText().isEmpty() || cb_unidade.getValue() == null) {
            mostrarMensagemErro("Por favor, preencha todos os campos obrigatórios.");
            return;
        }

        // Verifica se o CPF contém apenas números
        String cpf = txt_cpf.getText().trim();
        if (!cpf.matches("\\d+")) {
            mostrarMensagemErro("O CPF deve conter apenas números.");
            return;
        }
        

        if (funcionarioDAO.funcionarioExiste(cpf)) {
            mostrarMensagemErro("Funcionário com este CPF já existe.");
            return;
        }
            




        // Cria um novo funcionário
        Funcionario novoFuncionario = new Funcionario();
        novoFuncionario.setNome(txt_nome.getText());
        novoFuncionario.setCpf(txt_cpf.getText());
        novoFuncionario.setCargo(txt_cargo.getText());
        novoFuncionario.setTelefone(txt_telefone.getText());

        double salario = Double.parseDouble(txt_salario.getText());
        novoFuncionario.setSalario(salario);

        String nomeUnidadeSelecionada = cb_unidade.getValue();
        int unidadeID = -1; // Valor padrão se não encontrar

        try {
            if (funcionarioDAO != null) {
                unidadeID = unidadeDAO.buscarIDUnidadePorNome(nomeUnidadeSelecionada);
                novoFuncionario.setLojaID(unidadeID);
                funcionarioDAO.inserirFuncionario(novoFuncionario);
                limparCampos();
                mostrarMensagemSucesso("Funcionário cadastrado com sucesso!");
                // mostrarFuncionariosCadastrados(); // Mostra a lista de funcionários cadastrados (opcional)
            } else {
                throw new Exception("FuncionarioDAO não inicializado.");
            }
        } catch (Exception e) {
            mostrarMensagemErro("Erro ao acessar o FuncionarioDAO: " + e.getMessage());
        }
    }
    
    
    private void mostrarMensagemErro(String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    private void mostrarMensagemSucesso(String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
     }
    private void limparCampos() {
        txt_nome.clear();
        txt_cpf.clear();
        txt_cargo.clear();
        txt_salario.clear();
        txt_telefone.clear();
        cb_unidade.getSelectionModel().clearSelection();
    }
    
    
    
    public void configurarCamposEdicao(Funcionario funcionario) {
        
        btn_inserir.setText("Atualizar");
        String unidade = unidadeDAO.buscarNomeUnidadePorID(funcionario.getLojaID());
        // Preenche os campos com os dados do funcionário
        txt_nome.setText(funcionario.getNome());
        txt_cpf.setText(funcionario.getCpf());
        txt_telefone.setText(funcionario.getTelefone());
        txt_cargo.setText(funcionario.getCargo());
        txt_salario.setText(String.valueOf(funcionario.getSalario()));
        // Define a unidade do funcionário como selecionada no ComboBox
        cb_unidade.setValue(unidade);

        
        // Desativa o ComboBox para edição
        cb_unidade.setDisable(true);
        // Desativa os campos de nome e CPF
        txt_nome.setDisable(true);
        txt_cpf.setDisable(true);

    }

}
