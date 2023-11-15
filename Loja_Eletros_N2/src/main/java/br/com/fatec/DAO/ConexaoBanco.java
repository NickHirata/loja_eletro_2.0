/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fatec.DAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author nicol
 */

public class ConexaoBanco {

    private static final String URL = "jdbc:mysql://localhost:3306/loja_eletro"; // URL correta para o banco de dados MySQL
    private static final String USUARIO = "root";
    private static final String SENHA = ""; 

    public static Connection conectar() {
        Connection conexao = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Carrega o driver JDBC
            conexao = DriverManager.getConnection(URL, USUARIO, SENHA); // Estabelece a conexão
            System.out.println("Conexão bem-sucedida!");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("Erro na conexão com o banco de dados. (Classe ConexãoBanco)");
        }
        return conexao;
    }

    public static void desconectar(Connection conexao) {
        try {
            if (conexao != null && !conexao.isClosed()) {
                conexao.close(); // Fecha a conexão
                System.out.println("Conexão fechada.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


