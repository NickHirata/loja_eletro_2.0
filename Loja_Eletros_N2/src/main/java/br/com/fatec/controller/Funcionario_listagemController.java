package br.com.fatec.controller;



import br.com.fatec.DAO.FuncionarioDAO;
import br.com.fatec.model.Funcionario;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class Funcionario_listagemController implements Initializable {

    @FXML
    private Button btn_voltar;
    @FXML
    private TableColumn<Funcionario, Integer> tb_codigo;

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

    private FuncionarioDAO funcionarioDAO;
    @FXML
    private TableView<Funcionario> tb_funcionario_lista;

    public void initialize(URL url, ResourceBundle rb) {
        funcionarioDAO = new FuncionarioDAO();

        // Preencher a ComboBox
        preencherComboBox();
        preencherTableView();
    }

    @FXML
    private void btn_voltar_Click(ActionEvent event) {
        // Lógica para voltar
    }

    private void preencherComboBox() {
        List<String> unidades = funcionarioDAO.obterUnidadesFuncionarios();

        // Configurar a ComboBox
        cmb_unidades.getItems().addAll(unidades);

        // Adicione um listener para lidar com a seleção da ComboBox, se necessário
        cmb_unidades.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Lógica para preencher a TableView com os dados da unidade selecionada
            // preencherTabela(newValue);
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
}
