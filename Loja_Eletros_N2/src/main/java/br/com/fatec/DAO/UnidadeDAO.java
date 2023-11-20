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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nicol
 */
public class UnidadeDAO {
    
    
        // pesquisa o nome da loja e mostra os dados com base no nome
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
        
        
        
        // Método para buscar o ID da unidade pelo nome na tabela loja, recebe o nome e retorna o id
        public int buscarIDUnidadePorNome(String nomeUnidade) {
            int unidadeID = -1; // Valor padrão se não encontrar

            try {
                Connection conexao = ConexaoBanco.conectar(); // Obter a conexão com o banco de dados

                // Consulta SQL para buscar o ID da unidade pelo nome
                String query = "SELECT lojaID FROM loja WHERE nome = ?";
                PreparedStatement ps = conexao.prepareStatement(query);
                ps.setString(1, nomeUnidade);
                ResultSet rs = ps.executeQuery();

                // Verificar se há resultados e obter o ID da unidade
                if (rs.next()) {
                    unidadeID = rs.getInt("lojaID");
                }

                // Fechar as conexões
                rs.close();
                ps.close();
                ConexaoBanco.desconectar(conexao);
            } catch (SQLException e) {
                e.printStackTrace();
                // Lógica de tratamento de exceção conforme necessário
            }

            return unidadeID;
        }
        
        public String buscarNomeUnidadePorID(int idUnidade) {
            String nomeUnidade = null; // Valor padrão se não encontrar

            try {
                Connection conexao = ConexaoBanco.conectar(); // Obter a conexão com o banco de dados

                // Consulta SQL para buscar o nome da unidade pelo ID
                String query = "SELECT nome FROM loja WHERE lojaID = ?";
                PreparedStatement ps = conexao.prepareStatement(query);
                ps.setInt(1, idUnidade);
                ResultSet rs = ps.executeQuery();

                // Verificar se há resultados e obter o nome da unidade
                if (rs.next()) {
                    nomeUnidade = rs.getString("nome");
                }

                // Fechar as conexões
                rs.close();
                ps.close();
                ConexaoBanco.desconectar(conexao);
            } catch (SQLException e) {
                e.printStackTrace();
                // Lógica de tratamento de exceção conforme necessário
            }

            return nomeUnidade;
        }

        public List<Unidade> buscarTodasUnidades() {
            List<Unidade> todasUnidades = new ArrayList<>();
            Connection conexao = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;

            try {
                // Obtenha a conexão do método conectar na classe ConexaoBanco
                conexao = ConexaoBanco.conectar();

                // Operações com o banco para buscar todas as unidades
                String sql = "SELECT * FROM loja";
                stmt = conexao.prepareStatement(sql);
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

                    todasUnidades.add(unidade);
                }

            } catch (Exception ex) {
                System.out.println("Erro:" + ex.getMessage());
                System.out.println("Falha na conexão com o banco de dados.");
            } finally {
                // Fechando a conexão após a conclusão das operações
                ConexaoBanco.desconectar(conexao);
            }

            return todasUnidades;
    }
        
        
        // Método para obter os nomes das unidades sem repetições
        public List<String> obterNomesUnidades() {
            List<String> nomesUnidades = new ArrayList<>();
            Connection conexao = null;

            try {
                conexao = ConexaoBanco.conectar();

                if (conexao != null) {
                    String sql = "SELECT DISTINCT nome FROM loja";

                    try (PreparedStatement preparedStatement = conexao.prepareStatement(sql);
                         ResultSet resultSet = preparedStatement.executeQuery()) {

                        while (resultSet.next()) {
                            String nomeUnidade = resultSet.getString("nome");
                            nomesUnidades.add(nomeUnidade);
                        }
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                ConexaoBanco.desconectar(conexao);
            }

            return nomesUnidades;
        }
        
        
        
        
        public int obterTotalUnidades() {
            int totalUnidades = 0;
            Connection conexao = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;

            try {
                conexao = ConexaoBanco.conectar(); // Obter a conexão com o banco de dados

                // Consulta SQL para contar o total de unidades
                String query = "SELECT COUNT(*) FROM loja";
                stmt = conexao.prepareStatement(query);
                rs = stmt.executeQuery();

                // Se houver resultado na consulta, obter o total de unidades
                if (rs.next()) {
                    totalUnidades = rs.getInt(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Lógica de tratamento de exceção conforme necessário
            } finally {
                // Fechar as conexões
                ConexaoBanco.desconectar(conexao);
            }

            return totalUnidades;
        }

}
