/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fatec.DAO;

import br.com.fatec.model.Unidade;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nicol
 */
public class UnidadeDAO {
        public List<Unidade> pesquisarLoja(String nomeLoja) {
        List<Unidade> unidadesEncontradas = new ArrayList<>();
        Connection conexao = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Obtenha a conexão do método conectar na classe ConexaoBanco
            conexao = ConexaoBanco.conectar();

            // Operações com o banco
            String sql = "SELECT * FROM loja WHERE nome LIKE ?";
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, "%" + nomeLoja + "%"); // Utilizando o operador LIKE para pesquisar parte do nome
            rs = stmt.executeQuery();

            // Processar os resultados da consulta
            while (rs.next()) {
                Unidade unidade = new Unidade();
                unidade.setLojaID(rs.getInt("lojaID"));
                unidade.setNome(rs.getString("nome"));
                unidade.setTelefone(rs.getInt("telefone"));
                unidade.setEndereco(rs.getString("endereco"));
                unidade.setCidade(rs.getString("cidade"));
                unidade.setEmail(rs.getString("email"));

                unidadesEncontradas.add(unidade);
            }

        } catch (Exception ex) {
            System.out.println("Erro:" + ex.getMessage());
            System.out.println("Falha na conexão com o banco de dados.");
        } finally {
            // Fechando a conexão após a conclusão das operações
            ConexaoBanco.desconectar(conexao);
        }

        return unidadesEncontradas;
    }
}
