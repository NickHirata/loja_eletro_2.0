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
    
          
     public String buscarNomeFuncionarioPorID(int idFuncionario) {
        String nomeFuncionario = null; // Valor padrão se não encontrar

        try {
            Connection conexao = ConexaoBanco.conectar(); // Obter a conexão com o banco de dados

            // Consulta SQL para buscar o nome do funcionário pelo ID
            String query = "SELECT nome FROM funcionarios WHERE funcionarioID = ?";
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

     
        // Método para buscar o ID do funcionário pelo nome na tabela funcionarios, recebe o nome e retorna o id
    public int buscarIDFuncionarioPorNome(String nomeFuncionario) {
        int funcionarioID = -1; // Valor padrão se não encontrar

        try {
            Connection conexao = ConexaoBanco.conectar(); // Obter a conexão com o banco de dados

            // Consulta SQL para buscar o ID do funcionário pelo nome
            String query = "SELECT funcionarioID FROM funcionarios WHERE nome = ?";
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
     
     //para combobox, mostra a nome da unidade do funcionario
    public List<String> obterUnidadesFuncionarios() {
        List<String> unidades = new ArrayList<>();
        Connection conexao = null;

        try {
            conexao = ConexaoBanco.conectar();

            if (conexao != null) {
                String sql = "SELECT DISTINCT loja.nome AS nome_loja " +
                             "FROM funcionarios " +
                             "JOIN loja ON funcionarios.lojaID = loja.lojaID";

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
                String sql = "SELECT * FROM funcionarios " +
                             "JOIN loja ON funcionarios.lojaID = loja.lojaID " +
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
                            funcionario.setCargo(resultSet.getString("cargo"));
                            
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
                    String sql = "SELECT DISTINCT * FROM funcionarios " +
                                 "JOIN loja ON funcionarios.lojaID = loja.lojaID";

                    try (PreparedStatement preparedStatement = conexao.prepareStatement(sql);
                         ResultSet resultSet = preparedStatement.executeQuery()) {

                        while (resultSet.next()) {
                            Funcionario funcionario = new Funcionario();
                            funcionario.setFuncionarioID(resultSet.getInt("funcionarioID"));
                            funcionario.setNome(resultSet.getString("nome"));
                            funcionario.setCpf(resultSet.getString("cpf"));
                            funcionario.setSalario(resultSet.getDouble("salario"));
                            funcionario.setCargo(resultSet.getString("cargo"));
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

    public void excluirFuncionarioPorCPF(String cpf) throws SQLException {
        Connection conexao = null;
        PreparedStatement stmt = null;

        try {
            conexao = ConexaoBanco.conectar(); // Obter conexão com o banco de dados

            // Preparar a consulta SQL para excluir o funcionário pelo CPF
            String sql = "DELETE FROM funcionarios WHERE cpf = ?";
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, cpf);

            // Executar a consulta para excluir o funcionário
            stmt.executeUpdate();
        } catch (SQLException e) {
            // Lidar com exceções (por exemplo, imprimir o erro)
            e.printStackTrace();
            throw e; // Lançar a exceção para tratamento na camada superior
        } finally {
            // Fechar os recursos (statement e conexão)
            if (stmt != null) {
                stmt.close();
            }
            if (conexao != null) {
                conexao.close();
            }
        }
    }
    
    
    public void atualizarClientesSemFuncionario(int funcionarioID) throws SQLException {
    Connection conexao = null;
    PreparedStatement stmt = null;

    try {
        conexao = ConexaoBanco.conectar();

        String sql = "UPDATE clientes SET funcionarioID = NULL WHERE funcionarioID = ?";
        stmt = conexao.prepareStatement(sql);
        stmt.setInt(1, funcionarioID);
        stmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
        throw e;
    } finally {
        if (stmt != null) {
            stmt.close();
        }
        if (conexao != null) {
            conexao.close();
        }
    }
}

    
    public boolean funcionarioExiste(String cpf) {
       Connection conexao = null;
       PreparedStatement stmt = null;
       ResultSet rs = null;

       try {
           conexao = ConexaoBanco.conectar();
           String sql = "SELECT * FROM funcionarios WHERE cpf = ?";
           stmt = conexao.prepareStatement(sql);
           stmt.setString(1, cpf);
           rs = stmt.executeQuery();

           return rs.next(); // Retorna true se encontrou um funcionário com o CPF, false caso contrário

       } catch (SQLException ex) {
           System.out.println("Erro ao verificar funcionário: " + ex.getMessage());
           return false;
       } finally {
           ConexaoBanco.desconectar(conexao);
       }
   }

    public boolean inserirFuncionario(Funcionario novoFuncionario) {
        Connection conexao = null;
        PreparedStatement stmt = null;

        try {
            conexao = ConexaoBanco.conectar();
            String sql = "INSERT INTO funcionarios (nome, CPF, cargo, salario, lojaID, telefone) VALUES (?, ?, ?, ?, ?, ?)";
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, novoFuncionario.getNome());
            stmt.setString(2, novoFuncionario.getCpf());
            stmt.setString(3, novoFuncionario.getCargo());
            stmt.setDouble(4, novoFuncionario.getSalario());
            stmt.setInt(5, novoFuncionario.getLojaID());
            stmt.setString(6, novoFuncionario.getTelefone());

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0; // Retorna true se a inserção foi bem-sucedida, false caso contrário

        } catch (SQLException ex) {
            System.out.println("Erro ao inserir funcionário: " + ex.getMessage());
            return false;
        } finally {
            ConexaoBanco.desconectar(conexao);
        }
    }

    public int obterTotalFuncionarios() {
            int totalFuncionarios = 0;
            Connection conexao = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;

            try {
                conexao = ConexaoBanco.conectar(); // Obter a conexão com o banco de dados

                // Consulta SQL para contar o total de funcionários
                String query = "SELECT COUNT(*) FROM funcionarios";
                stmt = conexao.prepareStatement(query);
                rs = stmt.executeQuery();

                // Se houver resultado na consulta, obter o total de funcionários
                if (rs.next()) {
                    totalFuncionarios = rs.getInt(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Lógica de tratamento de exceção conforme necessário
            } finally {
                // Fechar as conexões
                ConexaoBanco.desconectar(conexao);
            }

            return totalFuncionarios;
        }
    
}
