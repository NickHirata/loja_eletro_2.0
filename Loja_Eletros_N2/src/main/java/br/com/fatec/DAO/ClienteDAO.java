/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fatec.DAO;


import br.com.fatec.model.Cliente;
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
public class ClienteDAO {
    
     public static void testarConexao() {
        Connection conexao = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Obtenha a conexão do método conectar na classe ConexaoBanco
            conexao = ConexaoBanco.conectar();

            // Operações com o banco
            String sql = "SELECT * FROM cadastrocliente";
            stmt = conexao.prepareStatement(sql);
            rs = stmt.executeQuery();

            // Processar os resultados da consulta
            while (rs.next()) {
                int id = rs.getInt("clienteID");
                String nome = rs.getString("nome");
                String endereco = rs.getString("endereco");
                String cpf = rs.getString("cpf");
                int lojaid = rs.getInt("lojaID");
                int funcionarioid = rs.getInt("funcionarioID");

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
     
     
     
 // Método para inserir um novo cliente no banco de dados
    public void inserirCliente(Cliente cliente) {
        Connection conexao = null;
        PreparedStatement stmt = null;

        try {
            conexao = ConexaoBanco.conectar();

            String sql = "INSERT INTO cadastrocliente (nome, endereco, cpf, lojaID, funcionarioID) VALUES (?, ?, ?, ?, ?)";
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEndereco());
            stmt.setString(3, cliente.getCpf());
            stmt.setInt(4, cliente.getUnidadeID());
            stmt.setInt(5, cliente.getFuncionarioID());

            stmt.executeUpdate();
            System.out.println("Cliente inserido com sucesso!");

        } catch (Exception ex) {
            System.out.println("Erro: " + ex.getMessage());
            System.out.println("Falha ao inserir o cliente no banco de dados.");
        } finally {
            ConexaoBanco.desconectar(conexao);
        }
    }

    // Método para excluir um cliente do banco de dados pelo CPF
    public void excluirCliente(String cpf) {
        Connection conexao = null;
        PreparedStatement stmt = null;

        try {
            conexao = ConexaoBanco.conectar();

            String sql = "DELETE FROM cadastrocliente WHERE cpf = ?";
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, cpf);

            stmt.executeUpdate();
            System.out.println("Cliente excluído com sucesso!");

        } catch (Exception ex) {
            System.out.println("Erro: " + ex.getMessage());
            System.out.println("Falha ao excluir o cliente do banco de dados.");
        } finally {
            ConexaoBanco.desconectar(conexao);
        }
    }

    // Método para buscar todos os clientes no banco de dados e retornar uma lista de Clientes
    public List<Cliente> buscarTodosClientes() {
        List<Cliente> listaClientes = new ArrayList<>();
        Connection conexao = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conexao = ConexaoBanco.conectar();

            String sql = "SELECT * FROM cadastrocliente";
            stmt = conexao.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setNome(rs.getString("nome"));
                cliente.setEndereco(rs.getString("endereco"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setUnidadeID(rs.getInt("lojaID"));
                cliente.setFuncionarioID(rs.getInt("funcionarioID"));

                listaClientes.add(cliente);
            }

        } catch (Exception ex) {
            System.out.println("Erro: " + ex.getMessage());
            System.out.println("Falha ao buscar clientes no banco de dados.");
        } finally {
            ConexaoBanco.desconectar(conexao);
        }

        return listaClientes;
    }
    
    // Método para buscar o cpf do funcionario para verfifcar existencia
    public boolean clienteExiste(String cpf) {
        boolean existe = false;

        try {
            Connection conexao = ConexaoBanco.conectar(); // Obter a conexão com o banco de dados

            // Consulta SQL para verificar a existência do cliente pelo CPF
            String query = "SELECT clienteID FROM cadastrocliente WHERE cpf = ?";
            PreparedStatement ps = conexao.prepareStatement(query);
            ps.setString(1, cpf);
            ResultSet rs = ps.executeQuery();

            // Verificar se há algum resultado
            if (rs.next()) {
                // Se encontrar algum cliente com o CPF, significa que ele existe
                existe = true;
            }

            // Fechar as conexões
            rs.close();
            ps.close();
            ConexaoBanco.desconectar(conexao);
        } catch (SQLException e) {
            e.printStackTrace();
            // Lógica de tratamento de exceção conforme necessário
        }

        return existe;
    }



    
    public Cliente obterClientePorCPF(String cpf) {
        Cliente cliente = null;

        try {
            Connection conexao = ConexaoBanco.conectar();

            String query = "SELECT * FROM cadastrocliente WHERE cpf = ?";
            PreparedStatement ps = conexao.prepareStatement(query);
            ps.setString(1, cpf);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                cliente = new Cliente();
                cliente.setNome(rs.getString("nome"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setEmail(rs.getString("email"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setEndereco(rs.getString("endereco"));
                cliente.setUnidadeID(rs.getInt("unidade_id"));
                cliente.setFuncionarioID(rs.getInt("funcionario_id"));
                // Defina outros campos conforme necessário
            }

            rs.close();
            ps.close();
            ConexaoBanco.desconectar(conexao);
        } catch (SQLException e) {
            e.printStackTrace();
            // Lógica de tratamento de exceção conforme necessário
        }

        return cliente;
    }

    

    public void atualizarCliente(Cliente cliente) throws SQLException {
    Connection conexao = null;
    PreparedStatement ps = null;

    try {
        conexao = ConexaoBanco.conectar();
        if (conexao != null) {
            String sql = "UPDATE cadastrocliente SET nome = ?, email = ?, telefone = ?, endereco = ? WHERE cpf = ?";
            ps = conexao.prepareStatement(sql);

            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getEmail());
            ps.setString(3, cliente.getTelefone());
            ps.setString(4, cliente.getEndereco());
            ps.setString(5, cliente.getCpf());

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Cliente atualizado com sucesso!");
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw new SQLException("Erro ao atualizar o cliente: " + e.getMessage());
    } finally {
        if (ps != null) {
            ps.close();
        }
        ConexaoBanco.desconectar(conexao);
    }
}

    
}
