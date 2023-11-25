/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fatec.DAO;


import br.com.fatec.model.Produto;
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
    
    
    
    
    
    public List<Produto> obterProdutosQtdTotal() {
        List<Produto> produtos = new ArrayList<>();
        Connection conexao = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conexao = ConexaoBanco.conectar(); // Obter a conexão com o banco de dados

            String query = "SELECT p.produtoID, p.nome, p.preco, p.descricao, p.voltagem, SUM(e.quantidade) AS quantidadeTotal " +
                            "FROM produtos p " +
                            "JOIN estoque e ON p.produtoID = e.produtoID " +
                            "GROUP BY p.produtoID, p.nome, p.descricao, p.voltagem, p.preco";


            stmt = conexao.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Produto produto = new Produto();
                produto.setProdutoID(rs.getInt("produtoID"));
                produto.setNome(rs.getString("nome"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setVoltagem(rs.getString("voltagem"));
                produto.setPreco(rs.getDouble("preco"));
                produto.setQuantidadeTotal(rs.getInt("quantidadeTotal"));

                produtos.add(produto);
                
            }
            
            
        } catch (Exception e) {
            e.printStackTrace(); // Trate adequadamente a exceção
        } finally {
            ConexaoBanco.desconectar(conexao); // Fechar as conexões
        }

        return produtos;
    }
    
    

    public List<Produto> obterProdutosPorUnidade(String nomeUnidade) {
        List<Produto> produtos = new ArrayList<>();
        Connection conexao = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conexao = ConexaoBanco.conectar(); // Obter a conexão com o banco de dados

            // Busca o ID da unidade pelo nome
            UnidadeDAO unidadeDAO = new UnidadeDAO();
            int lojaID = unidadeDAO.buscarIDUnidadePorNome(nomeUnidade);

            String query = "SELECT p.produtoID, p.nome, p.preco, p.descricao, p.voltagem, e.quantidade AS quantidadeNaLoja, e.lojaID " +
                    "FROM produtos p " +
                    "JOIN estoque e ON p.produtoID = e.produtoID " +
                    "WHERE e.lojaID = ?";

            stmt = conexao.prepareStatement(query);
            stmt.setInt(1, lojaID);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Produto produto = new Produto();
                produto.setProdutoID(rs.getInt("produtoID"));
                produto.setNome(rs.getString("nome"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setVoltagem(rs.getString("voltagem"));
                produto.setPreco(rs.getDouble("preco"));
                produto.setQtdNaLoja(rs.getInt("quantidadeNaLoja"));
                produto.setLojaID(rs.getInt("lojaID"));

                produtos.add(produto);
                
            }
            
            
        } catch (Exception e) {
            e.printStackTrace(); // Trate adequadamente a exceção
        } finally {
            ConexaoBanco.desconectar(conexao); // Fechar as conexões
        }

        return produtos;
    }
    
    
    
    
        // Método para obter apenas os nomes dos produtos do banco de dados
    public List<String> obterNomesProdutos() {
        List<String> nomesProdutos = new ArrayList<>();
        Connection conexao = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conexao = ConexaoBanco.conectar(); // Obter a conexão com o banco de dados

            String query = "SELECT DISTINCT nome FROM produtos";
            stmt = conexao.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                String nomeProduto = rs.getString("nome");
                nomesProdutos.add(nomeProduto);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Trate adequadamente a exceção
        } finally {
            ConexaoBanco.desconectar(conexao); // Fechar as conexões
        }

        return nomesProdutos;
    }
    
    
    
    
    public boolean atualizarOuInserirProduto(Produto produto) {
        boolean atualizadoOuInserido = false;
        Connection conexao = null;
        PreparedStatement stmtVerificar = null;
        PreparedStatement stmtAtualizar = null;
        PreparedStatement stmtInserir = null;

        try {
            conexao = ConexaoBanco.conectar(); // Obter a conexão com o banco de dados
            conexao.setAutoCommit(false); // Inicia a transação

            // Verificar se o produto já está no estoque da loja
            String queryVerificar = "SELECT COUNT(*) AS total FROM estoque WHERE produtoID = ? AND lojaID = ?";
            stmtVerificar = conexao.prepareStatement(queryVerificar);
            stmtVerificar.setInt(1, produto.getProdutoID());
            stmtVerificar.setInt(2, produto.getLojaID());

            ResultSet rs = stmtVerificar.executeQuery();
            rs.next();
            int total = rs.getInt("total");

            if (total > 0) {
                // Se o produto já existe, atualiza a quantidade
                String queryAtualizar = "UPDATE estoque SET quantidade = quantidade + ? WHERE produtoID = ? AND lojaID = ?";
                stmtAtualizar = conexao.prepareStatement(queryAtualizar);
                stmtAtualizar.setInt(1, produto.getQtdNaLoja());
                stmtAtualizar.setInt(2, produto.getProdutoID());
                stmtAtualizar.setInt(3, produto.getLojaID());

                int linhasAfetadas = stmtAtualizar.executeUpdate();
                if (linhasAfetadas > 0) {
                    atualizadoOuInserido = true;
                }
            } else {
                // Se o produto não existe, insere um novo registro
                String queryInserir = "INSERT INTO estoque (produtoID, lojaID, quantidade) VALUES (?, ?, ?)";
                stmtInserir = conexao.prepareStatement(queryInserir);
                stmtInserir.setInt(1, produto.getProdutoID());
                stmtInserir.setInt(2, produto.getLojaID());
                stmtInserir.setInt(3, produto.getQtdNaLoja());

                int linhasAfetadas = stmtInserir.executeUpdate();
                if (linhasAfetadas > 0) {
                    atualizadoOuInserido = true;
                }
            }

            conexao.commit(); // Commit da transação
        } catch (SQLException e) {
            try {
                if (conexao != null) {
                    conexao.rollback(); // Rollback em caso de erro
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace(); // Trate adequadamente a exceção
        } finally {
            try {
                if (stmtVerificar != null) {
                    stmtVerificar.close();
                }
                if (stmtAtualizar != null) {
                    stmtAtualizar.close();
                }
                if (stmtInserir != null) {
                    stmtInserir.close();
                }
                if (conexao != null) {
                    conexao.setAutoCommit(true); // Restaura o modo de autocommit
                    ConexaoBanco.desconectar(conexao); // Fecha a conexão
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return atualizadoOuInserido;
    }


    public boolean inserirProdutoTemporario(Produto produto, List<Produto> listaProdutosTemp) {
        return listaProdutosTemp.add(produto); // Adiciona o produto à lista temporária
    }
    
    
    
    // Método para obter um produto por nome no ProdutoDAO
        public Produto obterProdutoPorNome(String nomeProduto) {
            Produto produto = null;
            Connection conexao = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;

            try {
                conexao = ConexaoBanco.conectar(); // Obter a conexão com o banco de dados

                String query = "SELECT * FROM produtos WHERE nome = ?";
                stmt = conexao.prepareStatement(query);
                stmt.setString(1, nomeProduto);
                rs = stmt.executeQuery();

                if (rs.next()) {
                    produto = new Produto();
                    produto.setProdutoID(rs.getInt("produtoID"));
                    produto.setNome(rs.getString("nome"));
                    produto.setDescricao(rs.getString("descricao"));
                    // Definir outras propriedades do produto conforme necessário
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Trate adequadamente a exceção
            } finally {
                ConexaoBanco.desconectar(conexao); // Fechar as conexões
            }

            return produto;
        }


}
