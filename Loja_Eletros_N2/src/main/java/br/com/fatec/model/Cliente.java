/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fatec.model;

import br.com.fatec.Principal;
import br.com.fatec.controller.Cliente_cadastroController;
import java.io.IOException;
import java.util.Objects;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author nicol
 */
public class Cliente extends Application {
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private String endereco; 
    private int unidadeID;
    private int funcionarioID;
 
    
    public static Stage tela;
    
    
    //alterar tela do prof
    
    @Override
    
    public void start(Stage tela) throws IOException {
        setStage(tela);
        
        FXMLLoader fxmlLoader = new FXMLLoader(Principal.class.getResource("view/Cliente_cadastro.fxml"));
        Parent root = fxmlLoader.load();
        Cliente_cadastroController controler = fxmlLoader.getController();
        
        Scene scene = new Scene(root);
        
        tela.setScene(scene);
        tela.show();        

    }
    
    public static void setStage(Stage t) {
        tela = t;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public int getUnidadeID() {
        return unidadeID;
    }

    public void setUnidadeID(int unidadeID) {
        this.unidadeID = unidadeID;
    }

    public int getFuncionarioID() {
        return funcionarioID;
    }

    public void setFuncionarioID(int funcionarioID) {
        this.funcionarioID = funcionarioID;

    }

    public static Stage getTela() {
        return tela;
    }

    public static void setTela(Stage tela) {
        Cliente.tela = tela;
    }


    
    
    
    
    
    
    @Override
    public String toString() {
        return "Cliente{" + "nome=" + nome + ", cpf=" + cpf + ", unidade=" + unidadeID + '}';
    }

    public Cliente(String nome, String cpf, String email, String telefone, String endereco, int unidadeID, int funcionarioID) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
        this.unidadeID = unidadeID;
        this.funcionarioID = funcionarioID;
    }



    public Cliente() {
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.cpf);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cliente other = (Cliente) obj;
        if (!Objects.equals(this.cpf, other.cpf)) {
            return false;
        }
        return true;
    }




    
    


    
    
    
}
