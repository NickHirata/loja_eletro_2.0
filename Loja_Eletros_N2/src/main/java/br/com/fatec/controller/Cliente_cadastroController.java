    /*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
     */
    package br.com.fatec.controller;


    import br.com.fatec.DAO.ClienteDAO;
import br.com.fatec.DAO.FuncionarioDAO;
    import br.com.fatec.DAO.UnidadeDAO;
    import br.com.fatec.model.Cliente;
import br.com.fatec.model.Funcionario;
    import br.com.fatec.model.Unidade;
    
    
    import java.io.IOException;
    import java.net.URL;
    import java.util.List;
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
        

        private boolean atualizarCliente = false; // Variável para verificar se estamos atualizando um cliente

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

        
        
        private UnidadeDAO unidadeDAO = new UnidadeDAO();
        private ClienteDAO clienteDAO = new ClienteDAO();
        private FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        

        @Override
        public void initialize(URL url, ResourceBundle rb) {
            cb_unidade.setOnAction(event -> preencherComboBoxFuncionario());
            preencherComboBoxFuncionario();
            preencherComboBoxUnidade();
            
         
                // Verifica se o CPF existe no momento em que é digitado
               txt_cpf.textProperty().addListener((observable, oldValue, newValue) -> {
                   if (clienteDAO != null && clienteDAO.clienteExiste(newValue)) {
                       Cliente clienteExistente = clienteDAO.obterClientePorCPF(newValue);
                       if (clienteExistente != null) {
                           preencherCamposCliente(clienteExistente);
                           mostrarMensagemErro("Cliente com este CPF já existe. Não é possivel fazer alteraçoes!");
                           atualizarCliente = true;
                       }
                   } else {
                       btn_inserir.setText("Salvar");
                       atualizarCliente = false;
                   }
               });


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
                // Verifica se todos os campos obrigatórios estão preenchidos
                if (txtNome.getText().isEmpty() || txt_cpf.getText().isEmpty() || txt_telefone.getText().isEmpty() ||
                        txt_endereco.getText().isEmpty() || cb_unidade.getValue() == null || cb_funcionario.getValue() == null) {
                    mostrarMensagemErro("Por favor, preencha todos os campos obrigatórios.");
                    return;
                }

                // Verifica se o CPF contém apenas números
                String cpf = txt_cpf.getText().trim();
                if (!cpf.matches("\\d+")) {
                    mostrarMensagemErro("O CPF deve conter apenas números.");
                    return;
                }

                // Verifica se o telefone contém apenas números
                String telefone = txt_telefone.getText().trim();
                if (!telefone.matches("\\d+")) {
                    mostrarMensagemErro("O telefone deve conter apenas números.");
                    return;
                }

                // Verifica se o cliente já está cadastrado pelo CPF
                if (clienteDAO != null && clienteDAO.clienteExiste(cpf)) {
                    mostrarMensagemErro("Cliente com este CPF já existe.");
                    return;
                }

                // Cria um novo cliente
                Cliente novoCliente = new Cliente();
                novoCliente.setNome(txtNome.getText());
                novoCliente.setCpf(txt_cpf.getText());
                novoCliente.setEmail(txt_email.getText());
                novoCliente.setTelefone(txt_telefone.getText());
                novoCliente.setEndereco(txt_endereco.getText());

                String nomeUnidadeSelecionada = cb_unidade.getValue();
                int unidadeID = -1; // Valor padrão se não encontrar

                String nomeFuncionarioSelecionado = cb_funcionario.getValue();
                int funcionarioID = -1; // Valor padrão se não encontrar

                try {
                    if (clienteDAO != null) {
                        unidadeID = unidadeDAO.buscarIDUnidadePorNome(nomeUnidadeSelecionada);
                        funcionarioID = funcionarioDAO.buscarIDFuncionarioPorNome(nomeFuncionarioSelecionado);
                        novoCliente.setUnidadeID(unidadeID);
                        novoCliente.setFuncionarioID(funcionarioID);
                        clienteDAO.inserirCliente(novoCliente);
                        limparCampos();

                    } else {
                        throw new Exception("ClienteDAO não inicializado.");
                    }
                } catch (Exception e) {
                    mostrarMensagemErro("Erro ao acessar o ClienteDAO: " + e.getMessage());
                }
            }



        


        private void preencherCamposCliente(Cliente cliente) {
            txtNome.setText(cliente.getNome());
            txt_cpf.setText(cliente.getCpf());
            txt_email.setText(cliente.getEmail());
            txt_endereco.setText(cliente.getEndereco());
            // Busca e define o nome da unidade com base no ID
            String nomeUnidade = unidadeDAO.buscarNomeUnidadePorID(cliente.getUnidadeID());
            if (nomeUnidade != null) {
                cb_unidade.setValue(nomeUnidade);
            }

            // Busca e define o nome do funcionário com base no ID
            String nomeFuncionario = funcionarioDAO.buscarNomeFuncionarioPorID(cliente.getFuncionarioID());
            if (nomeFuncionario != null) {
                cb_funcionario.setValue(nomeFuncionario);
            }
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


        private Cliente buscarClientePorCPF(String cpf) {
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

        private void preencherComboBoxFuncionario() {
            String nomeUnidadeSelecionada = cb_unidade.getValue();

            if (nomeUnidadeSelecionada != null) {
                try {
                    // Chame o método do FuncionarioDAO para obter os funcionários da unidade selecionada
                    List<Funcionario> funcionarios = funcionarioDAO.obterFuncionariosPorUnidade(nomeUnidadeSelecionada);

                    if (funcionarios != null) {
                        ObservableList<String> nomesFuncionarios = FXCollections.observableArrayList();

                        for (Funcionario funcionario : funcionarios) {
                            nomesFuncionarios.add(funcionario.getNome()); // Suponha que você tenha um método getNome() em Funcionario
                        }

                        cb_funcionario.setItems(nomesFuncionarios);
                    } else {
                        mostrarMensagemErro("Erro ao carregar os funcionários da unidade.");
                    }
                } catch (Exception e) {
                    mostrarMensagemErro("Erro ao obter funcionários da unidade: " + e.getMessage());
                }
            }
        }




        

        
        
        private void preencherComboBoxUnidade() {
                try {
                if (unidadeDAO != null) {
                    List<Unidade> unidades = unidadeDAO.buscarTodasUnidades();
                    ObservableList<String> nomesUnidades = FXCollections.observableArrayList();

                    for (Unidade unidade : unidades) {
                        nomesUnidades.add(unidade.getNome());
                    }

                    cb_unidade.setItems(nomesUnidades);
                } else {
                    // Se o DAO não estiver inicializado, exibe uma mensagem de erro
                    mostrarMensagemErro("Erro ao acessar o UnidadeDAO.");
                }
            } catch (Exception e) {
                mostrarMensagemErro("Erro ao preencher a ComboBox de unidades: " + e.getMessage());
            }
        }

    }
