package br.com.fatec.controller;



import br.com.fatec.DAO.FuncionarioDAO;
import br.com.fatec.DAO.UnidadeDAO;
import br.com.fatec.model.Funcionario;
import java.net.URL;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;



public class Funcionario_listagemController implements Initializable {

    @FXML
    private Button btn_voltar;

    @FXML
    private ComboBox<String> cmb_unidades;
    @FXML
    private TableColumn<Funcionario, String> tb_cpf;
    @FXML
    private TableColumn<Funcionario, String> tb_nome;
    @FXML
    private TableColumn<Funcionario, Double> tb_salario;
    @FXML
    private TableColumn<Funcionario, String> tb_telefone;
    @FXML
    private TableColumn<Funcionario, Integer> tb_loja;
    @FXML
    private TableColumn<Funcionario, String> tb_cargo;
    @FXML
    private TableView<Funcionario> tb_funcionario_lista;
    @FXML
    private TextField txtF_pesquisa;
    @FXML
    private ImageView btn_lixo;
    
    private FuncionarioDAO funcionarioDAO;
    private FilteredList<Funcionario> filteredData;
 
  
    
    public void initialize(URL url, ResourceBundle rb) {
        funcionarioDAO = new FuncionarioDAO();
        
        // Configurar associação entre colunas e atributos da classe Funcionario
        tb_cargo.setCellValueFactory(new PropertyValueFactory<>("cargo"));
        tb_loja.setCellValueFactory(new PropertyValueFactory<>("lojaID"));
        tb_nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tb_cpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        tb_salario.setCellValueFactory(new PropertyValueFactory<>("salario"));
        tb_telefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));

        // Preencher a ComboBox
        preencherComboBox();
        //preencher table view
        preencherTableView();
        
        btn_lixo.setOnMouseClicked(event -> {
            excluirFuncionarioSelecionado();
        });
       


        /*try {
            filteredData = new FilteredList<>(tb_funcionario_lista.getItems(), p -> true);

            txtF_pesquisa.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(funcionario -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (funcionario.getNome().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }

                    return false;
                });
            });

            SortedList<Funcionario> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(tb_funcionario_lista.comparatorProperty());
            tb_funcionario_lista.setItems(sortedData);
        } catch (Exception e) {
            // Aqui você pode tratar a exceção
            e.printStackTrace(); // Isso irá imprimir informações sobre a exceção no console
            // Ou pode exibir uma mensagem de erro para o usuário
            mostrarErro("Erro ao filtrar dados", "Ocorreu um erro ao filtrar os funcionários.\n" + e.getMessage());
        }
*/

    }

    @FXML
    private void btn_voltar_Click(ActionEvent event) {
        // Lógica para voltar
    }

private void preencherComboBox() {
    List<String> unidades = funcionarioDAO.obterUnidadesFuncionarios();

    // Adicione um item vazio na lista de unidades
    unidades.add(0, "");

    // Configurar a ComboBox
    cmb_unidades.getItems().addAll(unidades);

    // Adicione um listener para lidar com a seleção da ComboBox
    cmb_unidades.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue != null && !newValue.isEmpty()) {
            // Buscar os funcionários da unidade pelo nome da unidade
            List<Funcionario> funcionariosUnidade = funcionarioDAO.obterFuncionariosPorUnidade(newValue);

            // Atualizar a TableView com os funcionários da unidade selecionada
            tb_funcionario_lista.getItems().clear();
            tb_funcionario_lista.getItems().addAll(funcionariosUnidade);
        } else {
            // Se a seleção estiver vazia, mostre todos os funcionários de todas as unidades
            List<Funcionario> todosFuncionarios = funcionarioDAO.obterDadosFuncionarios();

            // Atualizar a TableView com todos os funcionários
            tb_funcionario_lista.getItems().clear();
            tb_funcionario_lista.getItems().addAll(todosFuncionarios);
        }
    });
}


    private void preencherTableView() {
        try {
            // Chame o método do DAO para obter os dados dos funcionários
            FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
            List<Funcionario> funcionarios = funcionarioDAO.obterDadosFuncionarios();

            // Atualize a TableView na thread da plataforma JavaFX
            Platform.runLater(() -> {
                // Limpe os itens existentes na TableView
                tb_funcionario_lista.getItems().clear();

                // Adicione os novos itens à TableView
                tb_funcionario_lista.getItems().addAll(funcionarios);
            });
        } catch (Exception e) {
            // Trate a exceção e exiba uma mensagem para o usuário
            mostrarErro("Erro ao carregar dados", "Ocorreu um erro ao obter dados dos funcionários.\n" + e.getMessage());
        }
    }

    private void mostrarErro(String titulo, String mensagem) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    @FXML
    private void btn_lixo_Click(MouseEvent event) {
    }
    
    

    private void excluirFuncionarioSelecionado() {
        Funcionario funcionarioSelecionado = tb_funcionario_lista.getSelectionModel().getSelectedItem();

        if (funcionarioSelecionado != null) {
            mostrarConfirmacaoExclusao(funcionarioSelecionado);
        } else {
            mostrarAlerta("Nenhum funcionário selecionado", "Por favor, selecione um funcionário para excluir.");
        }
    }

    private void mostrarConfirmacaoExclusao(Funcionario funcionario) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação de Exclusão");
        alert.setHeaderText("Excluir Funcionário");
        alert.setContentText("Ao excluir este funcionário, os clientes associados\n"
                + "ficarão sem vínculo com qualquer funcionário.\n "
                + "Deseja prosseguir com a exclusão?");

        ButtonType btnExcluir = new ButtonType("Excluir");
        ButtonType btnCancel = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(btnExcluir, btnCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == btnExcluir) {
            try {
                funcionarioDAO.atualizarClientesSemFuncionario(funcionario.getFuncionarioID()); // Aqui utilizei funcionario.getFuncionarioID()
                
                funcionarioDAO.excluirFuncionarioPorCPF(funcionario.getCpf());
                

                preencherTableView();
                mostrarAlerta("Excluído com sucesso", "O funcionário foi excluído com sucesso.");
            } catch (SQLIntegrityConstraintViolationException e) {
                mostrarAlerta("Erro ao excluir", "Não é possível excluir o funcionário e alterar clientes.");
            } catch (Exception e) {
                mostrarAlerta("Erro ao excluir", "Ocorreu um erro ao excluir o funcionário.");
                e.printStackTrace(); // Imprimir a exceção no console para depuração
            }
        }
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

        
}