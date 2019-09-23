package uff.ic.lleme.tcc00328.exercicios.blog.model;

import uff.ic.lleme.tcc00328.exercicios.blog.StringVazio;

public class Blog {

    public String nome = null;
    public String descricao = null;

    public Nota[] notas = new Nota[100];
    public Usuario usuario = null;

    private Blog() {

    }

    public Blog(String nome, String descricao, Usuario usuario) throws NullPointerException, StringVazio {
        if (nome == null || descricao == null || usuario == null)
            throw new NullPointerException();
        if (nome.equals("") || descricao.equals(""))
            throw new StringVazio("parametro string vazio");
        this.nome = nome;
        this.descricao = descricao;
        this.usuario = usuario;
        usuario.incluirBlog(this);
    }

    public void incluirNota(Nota nota) {
        for (int i = 0; i < notas.length; i++)
            if (notas[i] == null) {
                notas[i] = nota;
                break;
            }
    }
}
