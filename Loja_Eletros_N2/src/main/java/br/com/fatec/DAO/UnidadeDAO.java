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
import java.text.Normalizer;
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
                unidade.setTelefone(rs.getString("telefone"));
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
                    unidade.setTelefone(rs.getString("telefone"));
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
        
        
            public String sugerirNomeAlternativo(String nome) {
                String novoNome = nome;
                List<String> nomesNoBanco = obterNomesUnidades();

                // Normaliza e coloca tudo em caixa baixa para comparar
                String nomeNormalizado = normalizarString(nome.toLowerCase());

                if (nomesNoBanco.stream().anyMatch(unidade -> normalizarString(unidade.toLowerCase()).equals(nomeNormalizado))) {
                    int i = 1;
                    while (nomesNoBanco.contains(novoNome)) {
                        novoNome = nome + " " + i;
                        i++;
                    }
                }

                return novoNome;
            }

            private String normalizarString(String input) {
                return Normalizer.normalize(input, Normalizer.Form.NFD)
                        .replaceAll("\\p{M}", "");
            }
            
            
    public boolean inserirUnidade(Unidade novaUnidade) {
        Connection conexao = null;
        PreparedStatement stmt = null;
        boolean inserido = false;

        try {
            conexao = ConexaoBanco.conectar(); // Obtém a conexão com o banco de dados

            // SQL para inserir uma nova unidade na tabela
            String sql = "INSERT INTO loja (nome, telefone, endereco, cidade, email) VALUES (?, ?, ?, ?, ?)";
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, novaUnidade.getNome());
            stmt.setString(2, novaUnidade.getTelefone());
            stmt.setString(3, novaUnidade.getEndereco());
            stmt.setString(4, novaUnidade.getCidade());
            stmt.setString(5, novaUnidade.getEmail());

            int linhasAfetadas = stmt.executeUpdate(); // Executa a inserção

            // Se a inserção for bem-sucedida, retorna verdadeiro
            if (linhasAfetadas > 0) {
                inserido = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConexaoBanco.desconectar(conexao); // Fecha a conexão
        }

        return inserido;
    }

    public boolean buscarPorNome(String nome) {
        Connection conexao = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        boolean nomeExiste = false;

        try {
            conexao = ConexaoBanco.conectar();

            if (conexao != null) {
                String query = "SELECT COUNT(*) FROM loja WHERE nome = ?";
                stmt = conexao.prepareStatement(query);
                stmt.setString(1, nome);

                resultSet = stmt.executeQuery(); 

                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    nomeExiste = count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConexaoBanco.desconectar(conexao);
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return nomeExiste;
    }
    
    
        public List<String> obterCidades() {
            List<String> cidades = new ArrayList<>();
            Connection conexao = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;

            try {
                conexao = ConexaoBanco.conectar(); // Obter a conexão com o banco de dados

                String query = "SELECT DISTINCT cidade FROM loja"; // Consulta SQL para obter cidades únicas das unidades
                stmt = conexao.prepareStatement(query);
                rs = stmt.executeQuery();

                while (rs.next()) {
                    String cidade = rs.getString("cidade");
                    cidades.add(cidade);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Lógica de tratamento de exceção conforme necessário
            } finally {
                // Fechar as conexões
                ConexaoBanco.desconectar(conexao);
            }

            return cidades;
    }
        
        
        
        public List<Unidade> obterUnidadePorCidade(String cidadeSelecionada) {
            List<Unidade> unidades = new ArrayList<>();
            Connection conexao = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;

            try {
                conexao = ConexaoBanco.conectar(); // Obter a conexão com o banco de dados

                String query = "SELECT * FROM loja WHERE cidade = ?";
                stmt = conexao.prepareStatement(query);
                stmt.setString(1, cidadeSelecionada); // Parâmetro para a cidade selecionada na ComboBox
                rs = stmt.executeQuery();

                while (rs.next()) {
                    // Construir objetos Unidade com base nos dados do ResultSet
                    Unidade unidade = new Unidade();
                    unidade.setLojaID(rs.getInt("lojaID"));
                    unidade.setNome(rs.getString("nome"));
                    unidade.setTelefone(rs.getString("telefone"));
                    unidade.setEndereco(rs.getString("endereco"));
                    unidade.setCidade(rs.getString("cidade"));
                    unidade.setEmail(rs.getString("email"));

                    unidades.add(unidade);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Lógica de tratamento de exceção conforme necessário
            } finally {
                // Fechar as conexões
                ConexaoBanco.desconectar(conexao);
            }

            return unidades;
        }

    public void excluirUnidade(int id) throws SQLException {
        Connection conexao = null;
        PreparedStatement stmt = null;

        try {
            conexao = ConexaoBanco.conectar(); // Estabelecer conexão com o banco de dados

            // Comando SQL para excluir a unidade com base no ID
            String sql = "DELETE FROM loja WHERE lojaID = ?";
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id);

            // Executar a instrução DELETE
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Unidade excluída com sucesso!");
            } else {
                throw new SQLException("Falha ao excluir a unidade. Nenhum registro afetado.");
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao excluir a unidade: " + e.getMessage());
        } finally {
            // Fechar as conexões e recursos
            if (stmt != null) {
                stmt.close();
            }
            ConexaoBanco.desconectar(conexao);
        }
    }



}
