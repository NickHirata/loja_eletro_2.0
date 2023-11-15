/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fatec.DAO;


import br.com.fatec.model.Funcionario;
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
public class FuncionarioDAO {
    
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
     
     public String buscarNomeFuncionarioPorID(int idFuncionario) {
        String nomeFuncionario = null; // Valor padrão se não encontrar

        try {
            Connection conexao = ConexaoBanco.conectar(); // Obter a conexão com o banco de dados

            // Consulta SQL para buscar o nome do funcionário pelo ID
            String query = "SELECT nome FROM cadastrofuncionario WHERE funcionarioID = ?";
            PreparedStatement ps = conexao.prepareStatement(query);
            ps.setInt(1, idFuncionario);
            ResultSet rs = ps.executeQuery();

            // Verificar se há resultados e obter o nome do funcionário
            if (rs.next()) {
                nomeFuncionario = rs.getString("nome");
            }

            // Fechar as conexões
            rs.close();
            ps.close();
            ConexaoBanco.desconectar(conexao);
        } catch (SQLException e) {
            e.printStackTrace();
            // Lógica de tratamento de exceção conforme necessário
        }

        return nomeFuncionario;
    }

     
        // Método para buscar o ID do funcionário pelo nome na tabela cadastrofuncionario, recebe o nome e retorna o id
    public int buscarIDFuncionarioPorNome(String nomeFuncionario) {
        int funcionarioID = -1; // Valor padrão se não encontrar

        try {
            Connection conexao = ConexaoBanco.conectar(); // Obter a conexão com o banco de dados

            // Consulta SQL para buscar o ID do funcionário pelo nome
            String query = "SELECT funcionarioID FROM cadastrofuncionario WHERE nome = ?";
            PreparedStatement ps = conexao.prepareStatement(query);
            ps.setString(1, nomeFuncionario);
            ResultSet rs = ps.executeQuery();

            // Verificar se há resultados e obter o ID do funcionário
            if (rs.next()) {
                funcionarioID = rs.getInt("funcionarioID");
            }

            // Fechar as conexões
            rs.close();
            ps.close();
            ConexaoBanco.desconectar(conexao);
        } catch (SQLException e) {
            e.printStackTrace();
            // Lógica de tratamento de exceção conforme necessário
        }

        return funcionarioID;
    }
     
     //para combobox, no funcionario so tem o id entao essa funcao mostra ao inves do id o nome da unidade pela tb loja
    public List<String> obterUnidadesFuncionarios() {
        List<String> unidades = new ArrayList<>();
        Connection conexao = null;

        try {
            conexao = ConexaoBanco.conectar();

            if (conexao != null) {
                String sql = "SELECT DISTINCT loja.nome AS nome_loja " +
                             "FROM cadastrofuncionario " +
                             "JOIN loja ON cadastrofuncionario.lojaID = loja.lojaID";

                try (PreparedStatement preparedStatement = conexao.prepareStatement(sql);
                     ResultSet resultSet = preparedStatement.executeQuery()) {

                    while (resultSet.next()) {
                        String nomeLoja = resultSet.getString("nome_loja");
                        unidades.add(nomeLoja);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConexaoBanco.desconectar(conexao);
        }

    return unidades;
}
     
    // mostra os funcionarios que estao na unidade digitada
    public List<Funcionario> obterFuncionariosPorUnidade(String nomeUnidade) {
        List<Funcionario> funcionarios = new ArrayList<>();
        Connection conexao = null;

        try {
            conexao = ConexaoBanco.conectar();

            if (conexao != null) {
                String sql = "SELECT * FROM cadastrofuncionario " +
                             "JOIN loja ON cadastrofuncionario.lojaID = loja.lojaID " +
                             "WHERE loja.nome = ?";

                try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
                    preparedStatement.setString(1, nomeUnidade);

                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        while (resultSet.next()) {
                            // Supondo que você tenha uma classe Funcionario, ajuste conforme necessário
                            Funcionario funcionario = new Funcionario();
                            funcionario.setNome(resultSet.getString("nome"));
                            funcionario.setCpf(resultSet.getString("cpf"));
                            funcionario.setSalario(resultSet.getDouble("salario"));
                            funcionario.setTelefone(resultSet.getString("telefone"));
                            funcionario.setLojaID(resultSet.getInt("lojaID"));
                            
                            funcionarios.add(funcionario);
                        }
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConexaoBanco.desconectar(conexao);
        }

        return funcionarios;
    }
    
    

    public List<Funcionario> obterDadosFuncionarios() {
            List<Funcionario> funcionarios = new ArrayList<>();
            Connection conexao = null;

            try {
                conexao = ConexaoBanco.conectar();

                if (conexao != null) {
                    String sql = "SELECT DISTINCT * FROM cadastrofuncionario " +
                                 "JOIN loja ON cadastrofuncionario.lojaID = loja.lojaID";

                    try (PreparedStatement preparedStatement = conexao.prepareStatement(sql);
                         ResultSet resultSet = preparedStatement.executeQuery()) {

                        while (resultSet.next()) {
                            Funcionario funcionario = new Funcionario();
                            funcionario.setFuncionarioID(resultSet.getInt("funcionarioID"));
                            funcionario.setNome(resultSet.getString("nome"));
                            funcionario.setCpf(resultSet.getString("cpf"));
                            funcionario.setSalario(resultSet.getDouble("salario"));
                            funcionario.setTelefone(resultSet.getString("telefone"));
                            funcionario.setLojaID(resultSet.getInt("lojaID"));

                            funcionarios.add(funcionario);
                        }
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                ConexaoBanco.desconectar(conexao);
            }

            return funcionarios;
        }

    
    
    
    
    
}
