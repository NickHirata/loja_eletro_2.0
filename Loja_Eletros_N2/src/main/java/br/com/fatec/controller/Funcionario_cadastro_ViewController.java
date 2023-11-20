/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fatec.controller;

import br.com.fatec.DAO.FuncionarioDAO;
import br.com.fatec.DAO.UnidadeDAO;
import br.com.fatec.model.Funcionario;
import br.com.fatec.model.Unidade;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

}
  


    @FXML
    private void btn_inserir_Click(ActionEvent event) {
        cadastrarFuncionario();
    }
   

    private void preencherComboBox() {
        List<String> unidades = unidadeDAO.obterNomesUnidades();

        // Configurar a ComboBox
        cb_unidade.getItems().addAll(unidades);
    }

    
    
    private void cadastrarFuncionario() {
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

}
