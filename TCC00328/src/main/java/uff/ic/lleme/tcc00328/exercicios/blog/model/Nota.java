package uff.ic.lleme.tcc00328.exercicios.blog.model;

public class Nota extends Conteudo {

    public String titulo = null;

    public Comentario[] comentario = new Comentario[10];

    public Nota(String titulo, String texto) {
        this.titulo = titulo;
        this.texto = texto;
    }

}
