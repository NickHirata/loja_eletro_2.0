/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fatec.DAO;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author nicol
 */
public class ProdutoDAO {
    
     public static void testarConexao() {
        Connection conexao = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Obtenha a conexão do método conectar na classe ConexaoBanco
            conexao = ConexaoBanco.conectar();

            // Operações com o banco
            String sql = "SELECT * FROM loja";
            stmt = conexao.prepareStatement(sql);
            rs = stmt.executeQuery();

            // Processar os resultados da consulta
            while (rs.next()) {
                int id = rs.getInt("lojaID");
                String nome = rs.getString("nome");
                String endereco = rs.getString("endereco");

                System.out.println("ID: " + id + ", Nome: " + nome + ", Endereço: " + endereco);
            }

        } catch (Exception ex) {
            System.out.println("Erro:" + ex.getMessage());
            System.out.println("Falha na conexão com o banco de dados.");
        } finally {
            // Fechando a conexão após a conclusão das operações
            ConexaoBanco.desconectar(conexao);
        }
     }
     
     
     
     
         // Método para obter o total de produtos no banco de dados
    public int obterTotalProdutos() {
        int totalProdutos = 0;
        Connection conexao = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conexao = ConexaoBanco.conectar(); // Obter a conexão com o banco de dados

            // Consulta SQL para contar o total de produtos
            String query = "SELECT COUNT(*) FROM produtos";
            stmt = conexao.prepareStatement(query);
            rs = stmt.executeQuery();

            // Se houver resultado na consulta, obter o total de produtos
            if (rs.next()) {
                totalProdutos = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Lógica de tratamento de exceção conforme necessário
        } finally {
            // Fechar as conexões
            ConexaoBanco.desconectar(conexao);
        }

        return totalProdutos;
    }
  
    public int obterTotalEstoque() {
        int totalEstoque = 0;
        Connection conexao = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conexao = ConexaoBanco.conectar(); // Obter a conexão com o banco de dados

            // Preparando a consulta SQL
            String query = "SELECT SUM(quantidade) AS total_estoque FROM estoque";
            stmt = conexao.prepareStatement(query);

            // Executando a consulta e obtendo o resultado
            rs = stmt.executeQuery();

            // Verificando se há resultados e obtendo o total do estoque
            if (rs.next()) {
                totalEstoque = rs.getInt("total_estoque");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Trate adequadamente a exceção
        } finally {
            // Fechar as conexões
            ConexaoBanco.desconectar(conexao);
        }

        // Retornando o total do estoque
        return totalEstoque;
    }

}
