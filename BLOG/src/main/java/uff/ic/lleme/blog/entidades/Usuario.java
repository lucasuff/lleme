package uff.ic.lleme.blog.entidades;

import java.util.ArrayList;

public class Usuario {

    private String nome;
    private String email;
    private ArrayList<Blog> blogs = new ArrayList<>();
    private ArrayList<Blog> favoritos = new ArrayList<>();

    private Usuario() {

    }

    public Usuario(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) throws Exception {
        if (nome == null)
            throw new Exception("Valor Nulo");
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(ArrayList<Blog> blogs) {
        this.blogs = blogs;
    }

    public ArrayList<Blog> getFavoritos() {
        return favoritos;
    }

}
