/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fatec.model;


import br.com.fatec.Principal;

import static br.com.fatec.model.Unidade.setStage;
import java.io.IOException;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author nicol
 */
public class Unidade {
    private int lojaID;
    private String nome;
    private String telefone;
    private String endereco; 
    private String cidade; 
    private String email;

    @Override
    public String toString() {
        return "Unidade{" + "lojaID=" + lojaID + ", nome=" + nome + ", telefone=" + telefone + ", endereco=" + endereco + ", cidade=" + cidade + ", email=" + email + '}';
    }

    
    
    
    
    // para os campos das consulta
    public IntegerProperty lojaIDProperty() {
        return new SimpleIntegerProperty(lojaID);
    }

    public SimpleStringProperty nomeProperty() {
        return new SimpleStringProperty(nome);
    }

    public SimpleStringProperty telefoneProperty() {
        return new SimpleStringProperty(telefone);
    }
    
    
    
    

    public Unidade(int lojaID, String nome) {
        this.lojaID = lojaID;
        this.nome = nome;
    }

    public Unidade(String nome, String telefone, String endereco, String cidade, String email) {
        this.nome = nome;
        this.telefone = telefone;
        this.endereco = endereco;
        this.cidade = cidade;
        this.email = email;
    }

    public Unidade() {
    }



    
    
    public int getLojaID() {
        return lojaID;
    }

    public void setLojaID(int lojaID) {
        this.lojaID = lojaID;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
    
    public static Stage tela;
    public void start(Stage tela) throws IOException {
        setStage(tela);
        
        FXMLLoader fxmlLoader = new FXMLLoader(Principal.class.getResource("view/Loja_cadastro.fxml"));
        Parent root = fxmlLoader.load();
        
        Scene scene = new Scene(root);
        
        tela.setScene(scene);
        tela.show();        

    }
    
        public void start_lista(Stage tela) throws IOException {
        setStage(tela);
        
        FXMLLoader fxmlLoader = new FXMLLoader(Principal.class.getResource("view/Unidade_listagem.fxml"));
        Parent root = fxmlLoader.load();
        
        Scene scene = new Scene(root);
        
        tela.setScene(scene);
        tela.show(); 






    }
    
    public static void setStage(Stage t) {
        tela = t;
    }
}
