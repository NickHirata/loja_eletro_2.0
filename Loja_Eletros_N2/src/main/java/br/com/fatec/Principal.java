package br.com.fatec;
import br.com.fatec.DAO.ClienteDAO;
import br.com.fatec.DAO.FuncionarioDAO;
import br.com.fatec.DAO.UnidadeDAO;
import br.com.fatec.model.Funcionario;
import br.com.fatec.model.Unidade;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;
import javafx.scene.layout.VBox;

/**
 * JavaFX Principal
 */
public class Principal extends Application {

    private static Scene scene;
    private static Stage stage;

     @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("view/MenuView"));
        this.stage = stage;
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Principal.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void fechar() {
        stage.close();
    }
    

    public static void main(String[] args) {
        
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        List<Funcionario> funcionarios = funcionarioDAO.obterDadosFuncionarios();
        System.out.println(funcionarios);
        
 
       launch();
      


       /*ClienteDAO clienteDAO = new ClienteDAO();
       ClienteDAO.testarConexao();
       
       UnidadeDAO unidadeDAO = new UnidadeDAO();

        // Teste de pesquisa por nome da loja
        String nomeLojaPesquisa = "Unidade São Caetano 1";  
        List<Unidade> unidadesEncontradas = unidadeDAO.pesquisarLoja(nomeLojaPesquisa);

        // Exibindo os resultados
        for (Unidade unidade : unidadesEncontradas) {
            System.out.println("ID: " + unidade.getLojaID() +
                    ", Nome: " + unidade.getNome() +
                    ", Telefone: " + unidade.getTelefone() +
                    ", Endereço: " + unidade.getEndereco() +
                    ", Cidade: " + unidade.getCidade() +
                    ", Email: " + unidade.getEmail());
        }*/
        
    }

}