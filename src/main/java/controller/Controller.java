package controller;

import model.*;


public class Controller {
    private String nome;

    public Controller() {

    }
    public Controller(String nome) {
        this.nome = nome;
    }
    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome = nome;}


    public static boolean loginValido(String username, String password) {
        return username.equals("a") && password.equals("a");
    }
}


