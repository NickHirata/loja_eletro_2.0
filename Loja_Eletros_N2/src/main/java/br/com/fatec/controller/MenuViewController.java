    package br.com.fatec.controller;

    /*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
     */
    import br.com.fatec.DAO.ClienteDAO;
    import br.com.fatec.DAO.FuncionarioDAO;
    import br.com.fatec.DAO.ProdutoDAO;
    import br.com.fatec.DAO.UnidadeDAO;
    import br.com.fatec.Principal;
    import br.com.fatec.model.Cliente;
    import br.com.fatec.model.Funcionario;
    import br.com.fatec.model.Produto;
    import br.com.fatec.model.Unidade;
    import java.io.IOException;
    import java.net.URL;
import java.util.List;
    import java.util.ResourceBundle;
    import java.util.concurrent.Executors;
    import java.util.concurrent.ScheduledExecutorService;
    import java.util.concurrent.TimeUnit;
    import javafx.animation.RotateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
    import javafx.event.ActionEvent;
    import javafx.fxml.FXML;
    import javafx.fxml.FXMLLoader;
    import javafx.fxml.Initializable;
    import javafx.scene.Parent;
    import javafx.scene.Scene;
    import javafx.scene.control.Alert;
    import javafx.scene.control.Alert.AlertType;
    import javafx.scene.control.Label;
    import javafx.scene.control.Menu;
    import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
    import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
    import javafx.scene.image.ImageView;
    import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
    import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
    import javafx.stage.Stage;
    import javafx.util.Duration;

    /**
     * FXML Controller class
     *
     * @author nicol
     */
    public class MenuViewController implements Initializable {

        @FXML
        private MenuItem mi_funcionario;
        @FXML
        private MenuItem mi_loja;
        @FXML
        private MenuItem mi_cliente;
        @FXML
        private MenuItem mi_produto;
        @FXML
        private Menu m_unidade;
        @FXML
        private MenuItem mi_rel_unidade;
        @FXML
        private MenuItem mi_rel_produto;
        @FXML
        private MenuItem mi_rel_funcionario;
        @FXML
        private Label lbl_unidades;
        @FXML
        private Label lbl_produtos;
        @FXML
        private Label lbl_clientes;
        @FXML
        private Label lbl_funcionarios;
        @FXML
        private ImageView btn_reload;
        @FXML
        private Rectangle fundo_produto;
        @FXML
        private Rectangle fundo_estoque;
        @FXML
        private Label lbl_estoque;

        /**
         * Initializes the controller class.
         */

        private UnidadeDAO unidadeDAO = new UnidadeDAO();
        private ClienteDAO clienteDAO = new ClienteDAO();
        private FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        private ProdutoDAO produtoDAO = new ProdutoDAO();
    @FXML
    private ImageView btn_soma_Estoque;
    @FXML
    private ImageView btn_mostrar_clientes;



   
        @Override
        public void initialize(URL url, ResourceBundle rb) {
            btn_mostrar_clientes.setOnMouseClicked((MouseEvent event) -> {
                btn_mostrar_clientes_Click();
            });

            iniciarAtualizacaoAutomatica();
            btn_reload.setOnMouseClicked((MouseEvent event) -> {
                RotateTransition rotateTransition = new RotateTransition(Duration.seconds(1), btn_reload);
                rotateTransition.setByAngle(360); // Girar 360 graus
                rotateTransition.setCycleCount(1); // Uma vez
                rotateTransition.play();
               atualizarTotais();
            });
            btn_soma_Estoque.setOnMouseClicked((MouseEvent event) -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(Principal.class.getResource("view/Produtos_estoque.fxml"));
                        Parent root = loader.load();
                        // Cria um novo Stage (janela)
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));

                        // Exibe a tela de cadastro
                        stage.show();
      
                    } catch (IOException e) {
                        // Em caso de erro ao carregar o arquivo FXML, exibe uma mensagem de erro
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Erro ao abrir janela");
                        alert.setHeaderText(null);
                        alert.setContentText("Ocorreu um erro ao abrir a janela de produtos do estoque.");
                        alert.showAndWait();

                        e.printStackTrace();
                    }
            });
            // Criar tooltips
            Tooltip tooltipProduto = new Tooltip("Este é o total de produtos");
            Tooltip tooltipEstoque = new Tooltip("Este é o total do estoque");

            // Evento para exibir Tooltip ao passar o mouse sobre o retângulo de produtos
            fundo_produto.setOnMouseEntered((MouseEvent event) -> {
                Tooltip.install(fundo_produto, tooltipProduto);
            });

            // Evento para remover o Tooltip ao retirar o mouse do retângulo de produtos
            fundo_produto.setOnMouseExited((MouseEvent event) -> {
                Tooltip.uninstall(fundo_produto, tooltipProduto);
            });

            // Evento para exibir Tooltip ao passar o mouse sobre o retângulo de estoque
            fundo_estoque.setOnMouseEntered((MouseEvent event) -> {
                Tooltip.install(fundo_estoque, tooltipEstoque);
            });

            // Evento para remover o Tooltip ao retirar o mouse do retângulo de estoque
            fundo_estoque.setOnMouseExited((MouseEvent event) -> {
                Tooltip.uninstall(fundo_estoque, tooltipEstoque);
            });
        }    
        
        

        @FXML
        private void mi_funcionario_Click(ActionEvent event) throws IOException{
            Funcionario fun = new Funcionario();
            fun.start(new Stage());

        }

        @FXML
        private void mi_loja_Click(ActionEvent event) throws IOException {
            Unidade x = new Unidade();
            x.start(new Stage());

        }



        @FXML
        private void mi_cliente_click(ActionEvent event) throws IOException {
            Cliente x = new Cliente();
            x.start(new Stage());
        }


        @FXML
        private void mi_produto_Click(ActionEvent event) throws IOException {
            Produto prod = new Produto();
            prod.start(new Stage());
        }


        @FXML
        private void mi_rel_unidade_click(ActionEvent event) throws IOException {
            Unidade x = new Unidade();
            x.start_lista(new Stage());
        }

        @FXML
        private void mi_rel_produto_click(ActionEvent event) throws IOException {
            Produto prod = new Produto();
            prod.start_r(new Stage());
        }

        @FXML
        private void mi_rel_funcionario_click(ActionEvent event) throws IOException {
            Funcionario fun = new Funcionario();
            fun.start_r(new Stage());
        }

        @FXML
        private void m_unidade_click(ActionEvent event) {
        }

        private void iniciarAtualizacaoAutomatica() {
            RotateTransition rotateTransition = new RotateTransition(Duration.seconds(1), btn_reload);
            rotateTransition.setByAngle(360); // Girar 360 graus
            rotateTransition.setCycleCount(1); // Uma vez
            rotateTransition.play();
            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

            // Atualiza os totais a cada 5 segundos
            scheduler.scheduleAtFixedRate(this::atualizarTotais, 0, 240 , TimeUnit.SECONDS);

        }



         // Método para atualizar os totais
        private void atualizarTotais() {
            ClienteDAO clienteDAO = new ClienteDAO();
            FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
            ProdutoDAO produtoDAO = new ProdutoDAO();
            UnidadeDAO unidadeDAO = new UnidadeDAO();

            // Obter e definir o total de clientes no Label
            int totalClientes = clienteDAO.obterTotalClientes();
            lbl_clientes.setText(String.valueOf(totalClientes));

            // Obter e definir o total de funcionários no Label
            int totalFuncionarios = funcionarioDAO.obterTotalFuncionarios();
            lbl_funcionarios.setText(String.valueOf(totalFuncionarios));

            // Obter e definir o total de produtos no Label
            int totalProdutos = produtoDAO.obterTotalProdutos();
            lbl_produtos.setText(String.valueOf(totalProdutos));

            // Obter e definir o total de unidades no Label
            int totalUnidades = unidadeDAO.obterTotalUnidades();
            lbl_unidades.setText(String.valueOf(totalUnidades));
            
            int totalEstoque = produtoDAO.obterTotalEstoque();
            lbl_estoque.setText(String.valueOf(totalEstoque));

            
        }

        // Métodos para exibir e esconder o tooltip do produto
        public void exibirToolTipProduto() {

        }

        public void esconderToolTipProduto() {

        }

        // Métodos para exibir e esconder o tooltip do estoque
        @FXML
        public void exibirToolTipEstoque() {

        }

        @FXML
        public void esconderToolTipEstoque() {
   
    
        }


   
    @FXML
    private void btn_mostrar_clientes_Click() {
            exibirTabelaClientes();
    }



    @FXML
    private void exibirTabelaClientes() {
        List<Cliente> listaClientes = clienteDAO.buscarTodosClientes(); // Obtém os clientes do banco de dados
        if (!listaClientes.isEmpty()) {
            // Criando uma área de texto para exibir os detalhes dos clientes
            TextArea textArea = new TextArea();
            textArea.setEditable(false);
            textArea.setWrapText(true);
            textArea.setPrefColumnCount(100); // Defina o número de colunas conforme necessário
            textArea.setPrefRowCount(50); // Defina o número de linhas conforme necessário

            // Adicionando os detalhes dos clientes à área de texto
            StringBuilder detalhesClientes = new StringBuilder();
            detalhesClientes.append("Lista de Clientes:\n");
            for (Cliente cliente : listaClientes) {
                detalhesClientes.append("Nome: ").append(cliente.getNome())
                                .append(" | CPF: ").append(cliente.getCpf())
                                .append(" | Endereço: ").append(cliente.getEndereco())
                                .append(" | ID Unidade: ").append(cliente.getUnidadeID())
                                .append(" | ID Funcionário: ").append(cliente.getFuncionarioID())
                                .append("\n\n");
            }
            textArea.setText(detalhesClientes.toString());

            // Criando um layout VBox para conter a área de texto com uma barra de rolagem
            VBox vBox = new VBox(textArea);
            ScrollPane scrollPane = new ScrollPane(vBox);

            // Criando um novo Stage
            Stage stage = new Stage();
            stage.setTitle("Detalhes dos Clientes");
            stage.initModality(Modality.APPLICATION_MODAL); // Para bloquear interações com outras janelas
            stage.setScene(new Scene(scrollPane, 800, 300)); // Defina o tamanho conforme necessário
            stage.show();
        } else {
            // Se não houver clientes, mostrar uma mensagem ou realizar alguma ação
            System.out.println("Não há clientes cadastrados.");
        }
    }
    
 }
