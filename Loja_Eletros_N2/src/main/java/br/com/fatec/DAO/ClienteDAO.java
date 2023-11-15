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
public class ClienteDAO {
    
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
    
}
