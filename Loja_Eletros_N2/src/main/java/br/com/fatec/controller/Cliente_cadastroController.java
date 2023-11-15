/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fatec.controller;


import br.com.fatec.model.Cliente;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
public class Cliente_cadastroController implements Initializable {

    @FXML
    private ComboBox<String> cb_funcionario;
    @FXML
    private TextField txtNome;
    @FXML
    private ComboBox<String> cb_unidade;
    @FXML
    private TextField txt_cpf;
    @FXML
    private TextField txt_email;
    @FXML
    private TextField txt_endereco;
    @FXML
    private TextField txt_telefone;
    @FXML
    private Button btn_voltar;

    /**
     * Initializes the controller class.
     */
    private ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();
    @FXML
    private Label lblNome;
    @FXML
    private Label lblDescricao;
    @FXML
    private Label lblPreco;
    @FXML
    private Label lblDescricao1;
    @FXML
    private Label lblCodigo1;
    @FXML
    private Label lbl_cpf;
    @FXML
    private Label lblCodigo111;
    @FXML
    private Button btn_inserir;
    @FXML
    private Button btn_excluir;
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          // Crie uma lista de itens para a ComboBox
        ObservableList<String> funcionarios = FXCollections.observableArrayList(
                "Funcionário 1",
                "Funcionário 2",
                "Funcionário 3"
                // Adicione mais funcionários conforme necessário
        );

        // Associe a lista à ComboBox
        cb_funcionario.setItems(funcionarios);
        
         ObservableList<String> unidade = FXCollections.observableArrayList(
                "unidade 1",
                "unidade 2",
                "unidade 3"
                // Adicione mais funcionários conforme necessário
        );

        // Associe a lista à ComboBox
        cb_unidade.setItems(unidade);
    }    

    @FXML
    private void btn_voltar_Click(ActionEvent event) {
            // Lógica para voltar para a tela de menu
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/com/fatec/view/MenuView.fxml"));
            Parent menuParent = null;
            try {
                menuParent = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
                // Trate a exceção conforme necessário
            }
            Scene menuScene = new Scene(menuParent);

            // Carrega a cena da tela de menu
            stage.setScene(menuScene);
            stage.show();
    }


    private void cadastrarCliente() {
        // Captura os dados do formulário
        String nome = txtNome.getText();
        String cpfStr = txt_cpf.getText();
        String email = txt_email.getText();
        String telefoneStr = txt_telefone.getText();
        String endereco = txt_endereco.getText();
        String unidade = cb_unidade.getValue();
        String funcionario = cb_funcionario.getValue();

        // Verifica se os campos obrigatórios foram preenchidos
        if (nome.isEmpty() || cpfStr.isEmpty() || email.isEmpty() || telefoneStr.isEmpty() || endereco.isEmpty() || unidade == null || funcionario == null) {
            mostrarMensagemErro("Todos os campos são obrigatórios!");
            return;
        }

        // Verifica se o CPF é um número válido
        int cpf;
        try {
            cpf = Integer.parseInt(cpfStr);
        } catch (NumberFormatException e) {
            mostrarMensagemErro("CPF deve ser um número válido!");
            return;
        }

        // Verifica se o telefone é um número válido
        int telefone;
        try {
            telefone = Integer.parseInt(telefoneStr);
        } catch (NumberFormatException e) {
            mostrarMensagemErro("Telefone deve ser um número válido!");
            return;
        }

        // Verifica se o CPF já existe na lista de clientes
        if (buscarClientePorCPF(cpf) != null) {
            mostrarMensagemErro("Cliente com CPF " + cpf + " já está cadastrado!");
            return;
        }

        // Cria um novo objeto Cliente
        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setCpf(cpf);
        cliente.setEmail(email);
        cliente.setTelefone(telefone);
        cliente.setEndereco(endereco);
        cliente.setUnidade(unidade);
        cliente.setFuncionario(funcionario);

        // Adiciona o cliente à lista de clientes
        listaClientes.add(cliente);

        // Limpa os campos do formulário
        limparCampos();

        // Mostra mensagem de sucesso
        mostrarMensagemSucesso("Cliente cadastrado com sucesso!");

        mostrarClientesCadastrados();
       
    }
    
    
    private void mostrarClientesCadastrados() {
        StringBuilder clientesStr = new StringBuilder("Clientes Cadastrados:\n");

        // Itera sobre a lista de clientes e adiciona suas representações de string ao StringBuilder
        for (Cliente cliente : listaClientes) {
            clientesStr.append(cliente.toString()).append("\n");
        }

        // Exibe os clientes em uma caixa de diálogo
        mostrarMensagemSucesso(clientesStr.toString());
    }
   

    private Cliente buscarClientePorCPF(int cpf) {
        for (Cliente cliente : listaClientes) {
            if (cliente.getCpf() == cpf) {
                return cliente;
            }
        }
        return null;
    }

        private void limparCampos() {
            txtNome.clear();
            txt_cpf.clear();
            txt_email.clear();
            txt_telefone.clear();
            txt_endereco.clear();
            cb_unidade.setValue(null);
            cb_funcionario.setValue(null);
        }
    

        private void mostrarMensagemErro(String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
        }

        private void mostrarMensagemSucesso(String mensagem) {
            JOptionPane.showMessageDialog(null, mensagem, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
         }
    
    
    
    

    @FXML
        private void btn_inserir_Click(ActionEvent event) {
            cadastrarCliente();

        }

    @FXML
    private void btn_excluir_click(ActionEvent event) {
    }
    
}
