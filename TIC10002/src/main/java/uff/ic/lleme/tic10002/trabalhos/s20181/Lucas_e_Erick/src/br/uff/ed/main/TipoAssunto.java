package br.uff.ed.main;

import java.io.Serializable;

public class TipoAssunto implements Serializable {

    private static final long serialVersionUID = -3749703337774016424L;
    private int tipoid;
    private String tipo;
    private String titulo;
    private Urgencia urgencia;

    public TipoAssunto(int tipoid, String tipo, String titulo, Urgencia urgencia) {
        this.tipoid = tipoid;
        this.tipo = tipo;
        this.titulo = titulo;
        this.urgencia = urgencia;
    }

    public String getTipo() {
        return tipo;
    }

    public int getTipoid() {
        return tipoid;
    }

    public void setTipoid(int tipoid) {
        this.tipoid = tipoid;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Urgencia getUrgencia() {
        return urgencia;
    }

    public void setUrgencia(Urgencia urgencia) {
        this.urgencia = urgencia;
    }

}
