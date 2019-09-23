package uff.ic.lleme.blog.entidades;

import java.util.Date;

public class Blog {

    private Date dataCriacao;
    private String titulo;
    private Usuario dono;

    public Blog(String titulo, Usuario dono) {
        this.titulo = titulo;
        this.dono = dono;
        this.dono.getBlogs().add(this);
    }

    public Usuario getDono() {
        return dono;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
