/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fatec.model;

import br.com.fatec.Principal;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Aluno
 */
public class Produto {
        private int produtoID;
        private String nome;
        private String descricao;
        private boolean voltagem;

    @Override
    public String toString() {
        return "Produto{" + "produtoID=" + produtoID + ", nome=" + nome + ", descricao=" + descricao + ", voltagem=" + voltagem + ", preco=" + preco + '}';
    }
        private double preco;

    public int getProdutoID() {
        return produtoID;
    }

    public Produto() {
    }

    public Produto(int produtoID, String nome, String descricao, boolean voltagem, double preco) {
        this.produtoID = produtoID;
        this.nome = nome;
        this.descricao = descricao;
        this.voltagem = voltagem;
        this.preco = preco;
    }

    public void setProdutoID(int produtoID) {
        this.produtoID = produtoID;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isVoltagem() {
        return voltagem;
    }

    public void setVoltagem(boolean voltagem) {
        this.voltagem = voltagem;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
    public static Stage tela;
    
    public void start(Stage tela) throws IOException {
        setStage(tela);
        
        FXMLLoader fxmlLoader = new FXMLLoader(Principal.class.getResource("view/Produtos_cadastro.fxml"));
        Parent root = fxmlLoader.load();
        
        Scene scene = new Scene(root);
        
        tela.setScene(scene);
        tela.show();        

    }
    
    public static void setStage(Stage t) {
        tela = t;
    }
    
    
    public void start_r(Stage tela) throws IOException {
        setStage(tela);
        
        var fxmlLoader = new FXMLLoader(Principal.class.getResource("view/Produto_listagem.fxml"));
        Parent root = fxmlLoader.load();
        
        Scene scene = new Scene(root);
        
        tela.setScene(scene);
        tela.show();        

    }
    
        public void start_esto(Stage tela) throws IOException {
        setStage(tela);
        
        var fxmlLoader = new FXMLLoader(Principal.class.getResource("view/Produtos_estoque.fxml"));
        Parent root = fxmlLoader.load();
        
        Scene scene = new Scene(root);
        
        tela.setScene(scene);
        tela.show();        

    }

}
